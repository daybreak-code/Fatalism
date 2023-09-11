package cn.daycode.fatalism.service;

import cn.daycode.fatalism.common.constant.TenderStatusCode;
import cn.daycode.fatalism.common.domain.BusinessException;
import cn.daycode.fatalism.common.domain.RemoteReturnCode;
import cn.daycode.fatalism.common.util.EncryptUtil;
import cn.daycode.fatalism.domain.*;
import cn.daycode.fatalism.entity.BalanceDetails;
import cn.daycode.fatalism.entity.Project;
import cn.daycode.fatalism.entity.Tender;
import cn.daycode.fatalism.mapper.TenderMapper;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class TenderServiceImpl extends ServiceImpl<TenderMapper, Tender> implements TenderService {

	@Autowired
	private BalanceDetailsService balanceDetailsService;

	@Autowired
	private ProjectService projectService;

	@Override
	@Transactional
	public UserAutoPreTransactionResponse autoPreTransactionForTender(
			UserAutoPreTransactionRequest preTransactionRequest) {
		UserAutoPreTransactionResponse response = new UserAutoPreTransactionResponse();
		response.setRequestNo(preTransactionRequest.getRequestNo());
		response.setBizType(preTransactionRequest.getBizType());
		String requestNo = preTransactionRequest.getRequestNo();

		try {
			UserAutoPreTransactionResponse verifyResponse = balanceDetailsService
					.verifyEnough(preTransactionRequest.getUserNo(), preTransactionRequest.getAmount(), response);
			if (verifyResponse != null) {
				return verifyResponse;
			}

			Tender tender = new Tender();
			BeanUtils.copyProperties(preTransactionRequest, tender);
			tender.setTenderStatus(TenderStatusCode.FROZEN.getCode());
			save(tender);

			balanceDetailsService.freezeBalance(preTransactionRequest);
			response.setSuccess();
		} catch (Exception e) {
			log.error(e.getMessage());
			response.setFailure();
			throw new BusinessException(requestNo, RemoteReturnCode.EXCEPTION);
		}
		return response;
	}

	@Override
	@Transactional
	public ConfirmLoanResponse confirmLoan(String reqData) {
		String decodeReqData = EncryptUtil.decodeUTF8StringBase64(reqData);
		ConfirmLoanRequest request = JSON.parseObject(decodeReqData, ConfirmLoanRequest.class);
		String requestNo = request.getRequestNo();

		ConfirmLoanResponse response = new ConfirmLoanResponse();
		response.setRequestNo(requestNo);

		List<ConfirmLoanDetailRequest> loanDetailList = request.getDetails();
		List<Tender> tenderList = new ArrayList<>();
		for (ConfirmLoanDetailRequest loan : loanDetailList) {
			Tender tender = getByRequestNo(loan.getPreRequestNo());

			if (tender == null) {
				log.warn("Tender Record{}Not Exist", loan.getPreRequestNo());
				response.setResp(RemoteReturnCode.TENDER_NOT_EXIST);
				return response;
			}
			tenderList.add(tender);
		}

		try {
			Project project = projectService.getByProjectNo(tenderList.get(0).getProjectNo());
			BalanceDetails loanBalanceDetails = balanceDetailsService.getByUserNo(project.getUserNo());
			BalanceDetails investBalanceDetails;
			for (Tender tender : tenderList) {
				investBalanceDetails = balanceDetailsService.getByUserNo(tender.getUserNo());
				investBalanceDetails.setAppCode(request.getAppCode());
				investBalanceDetails.setRequestContent(decodeReqData);
				balanceDetailsService.decreaseBalance(investBalanceDetails, tender.getAmount());

				loanBalanceDetails.setAppCode(request.getAppCode());
				loanBalanceDetails.setRequestContent(decodeReqData);
				balanceDetailsService.increaseBalance(loanBalanceDetails, tender.getAmount());

				modifyTenderStatus(tender.getId(), TenderStatusCode.LOAN.getCode());
				response.setSuccess();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			response.setFailure();
			throw new BusinessException(requestNo, RemoteReturnCode.EXCEPTION);
		}
		return response;
	}


	private Tender getByRequestNo(String requestNo) {
		return getOne(Wrappers.<Tender>lambdaQuery().eq(Tender::getRequestNo, requestNo), false);
	}


	private Boolean modifyTenderStatus(Long id, String tenderStatus) {
		return update(Wrappers.<Tender>lambdaUpdate().eq(Tender::getId, id).set(Tender::getTenderStatus, tenderStatus));
	}

}
