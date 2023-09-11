package cn.daycode.fatalism.service;

import cn.daycode.fatalism.common.constant.BalanceChangeCode;
import cn.daycode.fatalism.common.domain.BusinessException;
import cn.daycode.fatalism.common.domain.RemoteReturnCode;
import cn.daycode.fatalism.common.util.EncryptUtil;
import cn.daycode.fatalism.domain.*;
import cn.daycode.fatalism.entity.BalanceDetails;
import cn.daycode.fatalism.entity.Project;
import cn.daycode.fatalism.mapper.BalanceDetailsMapper;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


@Slf4j
@Service
public class BalanceDetailsServiceImpl extends ServiceImpl<BalanceDetailsMapper, BalanceDetails>
		implements BalanceDetailsService {

	@Autowired
	private ProjectService projectService;

	@Override
	public BalanceDetailsDTO getP2PBalanceDetails(String userNo) {
		BalanceDetails balanceDetails = getByUserNo(userNo);
		if (balanceDetails != null) {
			BalanceDetailsDTO dto = new BalanceDetailsDTO();
			BeanUtils.copyProperties(balanceDetails, dto);
			return dto;
		}

		return new BalanceDetailsDTO().setBalance(new BigDecimal(0));
	}

	@Override
	public UserAutoPreTransactionResponse verifyEnough(String userNo, BigDecimal needAmount,
			UserAutoPreTransactionResponse response) {
		if (!isEnough(userNo, needAmount)) {
			response.setResp(RemoteReturnCode.BALANCE_NOT_ENOUGH);
			return response;
		}
		return null;
	}

	@Override
	public Boolean freezeBalance(UserAutoPreTransactionRequest preTransactionRequest) {
		BalanceDetails prevBalanceDetails = getByUserNo(preTransactionRequest.getUserNo());

		BalanceDetails newBalanceDetails = new BalanceDetails();
		newBalanceDetails.setUserNo(preTransactionRequest.getUserNo());
		newBalanceDetails.setAmount(preTransactionRequest.getAmount());
		newBalanceDetails.setRequestContent(JSON.toJSONString(preTransactionRequest));
		newBalanceDetails.setAppCode(preTransactionRequest.getAppCode());
		newBalanceDetails.setChangeType(BalanceChangeCode.FREEZE.getCode());

		BigDecimal freezeAmount = prevBalanceDetails.getFreezeAmount().add(preTransactionRequest.getAmount());
		newBalanceDetails.setFreezeAmount(freezeAmount);

		BigDecimal balance = prevBalanceDetails.getBalance().subtract(preTransactionRequest.getAmount());
		newBalanceDetails.setBalance(balance);
		return save(newBalanceDetails);
	}

	@Override
	public Boolean increaseBalance(BalanceDetails balanceDetails, BigDecimal amount) {
		balanceDetails.setId(null);
		balanceDetails.setChangeType(BalanceChangeCode.INCREASE.getCode());
		balanceDetails.setAmount(amount);

		BigDecimal balance = balanceDetails.getBalance().add(amount);
		balanceDetails.setBalance(balance);
		return save(balanceDetails);
	}

	@Override
	public Boolean decreaseBalance(BalanceDetails balanceDetails, BigDecimal amount) {
		balanceDetails.setId(null);
		balanceDetails.setChangeType(BalanceChangeCode.DECREASE.getCode());
		balanceDetails.setAmount(amount);

		BigDecimal freezeAmount = balanceDetails.getFreezeAmount().subtract(amount);
		balanceDetails.setFreezeAmount(freezeAmount);
		return save(balanceDetails);
	}

	@Override
	public Boolean recharge(RechargeRequest rechargeRequest) {
		String userNo = rechargeRequest.getUserNo();
		BalanceDetails balanceDetails = getByUserNo(userNo);

		BalanceDetails newBalanceDetails = new BalanceDetails();
		newBalanceDetails.setUserNo(userNo);
		newBalanceDetails.setAppCode(rechargeRequest.getAppCode());
		newBalanceDetails.setChangeType(BalanceChangeCode.INCREASE.getCode());
		newBalanceDetails.setRequestContent(JSON.toJSONString(rechargeRequest));
		newBalanceDetails.setAmount(rechargeRequest.getAmount());
		newBalanceDetails.setFreezeAmount(balanceDetails.getFreezeAmount());
		newBalanceDetails.setBalance(balanceDetails.getBalance().add(rechargeRequest.getAmount()));
		return save(newBalanceDetails);
	}

	@Override
	public Boolean withDraw(WithdrawRequest withDrawRequest) {
		String userNo = withDrawRequest.getUserNo();
		BalanceDetails balanceDetails = getByUserNo(userNo);

		BalanceDetails newBalanceDetails = new BalanceDetails();
		newBalanceDetails.setUserNo(userNo);
		newBalanceDetails.setAppCode(withDrawRequest.getAppCode());
		newBalanceDetails.setChangeType(BalanceChangeCode.DECREASE.getCode());
		newBalanceDetails.setRequestContent(JSON.toJSONString(withDrawRequest));
		newBalanceDetails.setAmount(withDrawRequest.getAmount());
		newBalanceDetails.setFreezeAmount(balanceDetails.getFreezeAmount());
		newBalanceDetails.setBalance(balanceDetails.getBalance().subtract(withDrawRequest.getAmount()));
		return save(newBalanceDetails);
	}

	@Override
	public Boolean addForPersonalRegister(BalanceDetails balanceDetails) {
		BigDecimal defaultBalance = new BigDecimal("0");
		balanceDetails.setChangeType(BalanceChangeCode.NEW.getCode());
		balanceDetails.setAmount(defaultBalance);
		balanceDetails.setFreezeAmount(defaultBalance);
		balanceDetails.setBalance(defaultBalance);
		return save(balanceDetails);
	}

	@Override
	public UserAutoPreTransactionResponse autoPreTransactionForRepayment(
			UserAutoPreTransactionRequest preTransactionRequest) {
		String requestNo = preTransactionRequest.getRequestNo();
		UserAutoPreTransactionResponse response = new UserAutoPreTransactionResponse();
		response.setRequestNo(requestNo);
		response.setBizType(preTransactionRequest.getBizType());

		UserAutoPreTransactionResponse verifyResponse = verifyEnough(preTransactionRequest.getUserNo(),
				preTransactionRequest.getAmount(), response);
		if (verifyResponse != null) {
			return verifyResponse;
		}

		freezeBalance(preTransactionRequest);
		response.setSuccess();
		return response;

	}

	@Override
	@Transactional
	public ConfirmRepaymentResponse confirmRepayment(String reqData) {
		String decodeReqData = EncryptUtil.decodeUTF8StringBase64(reqData);
		ConfirmRepaymentRequest request = JSON.parseObject(decodeReqData, ConfirmRepaymentRequest.class);
		String requestNo = request.getRequestNo();

		ConfirmRepaymentResponse response = new ConfirmRepaymentResponse();
		response.setRequestNo(requestNo);

		List<ConfirmRepaymentDetailRequest> repaymentDetailList = request.getDetails();
		try {
			Project project = projectService.getByProjectNo(request.getProjectNo());
			BalanceDetails loanBalanceDetails = getByUserNo(project.getUserNo());
			loanBalanceDetails.setAppCode(request.getAppCode());
			loanBalanceDetails.setRequestContent(decodeReqData);
			decreaseBalance(loanBalanceDetails, request.getAmount());

			for (ConfirmRepaymentDetailRequest repayment : repaymentDetailList) {
				BalanceDetails investBalanceDetails = getByUserNo(repayment.getUserNo());
				investBalanceDetails.setAppCode(request.getAppCode());
				investBalanceDetails.setRequestContent(decodeReqData);
				increaseBalance(investBalanceDetails, repayment.getAmount().add(repayment.getInterest()));
			}
			response.setSuccess();
		} catch (Exception e) {
			log.error(e.getMessage());
			response.setFailure();
			throw new BusinessException(requestNo, RemoteReturnCode.EXCEPTION);
		}
		return response;
	}

	@Override
	public BalanceDetails getByUserNo(String userNo) {
		return getOne(new QueryWrapper<BalanceDetails>().lambda().eq(BalanceDetails::getUserNo, userNo)
				.orderByDesc(BalanceDetails::getId).last("LIMIT 1"));
	}


	private Boolean isEnough(String userNo, BigDecimal needAmount) {
		BalanceDetails details = getByUserNo(userNo);
		if (details == null || details.getBalance().compareTo(needAmount) < 0) {
			return false;
		}
		return true;
	}

}
