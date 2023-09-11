package cn.daycode.fatalism.service;

import cn.daycode.fatalism.common.constant.StatusCode;
import cn.daycode.fatalism.common.domain.BusinessException;
import cn.daycode.fatalism.common.domain.LocalReturnCode;
import cn.daycode.fatalism.common.domain.RemoteReturnCode;
import cn.daycode.fatalism.domain.RechargeRequest;
import cn.daycode.fatalism.domain.RechargeResponse;
import cn.daycode.fatalism.entity.DepositoryBankCard;
import cn.daycode.fatalism.entity.RechargeDetails;
import cn.daycode.fatalism.mapper.RechargeDetailsMapper;
import cn.daycode.fatalism.message.GatewayMessageProducer;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class RechargeDetailsServiceImpl extends ServiceImpl<RechargeDetailsMapper, RechargeDetails>
		implements RechargeDetailsService {

	@Autowired
	private BalanceDetailsService balanceDetailsService;

	@Autowired
	private UserService userService;

	@Autowired
	private BankCardService bankCardService;

	@Autowired
	private GatewayMessageProducer producer;

	@Autowired
	private RequestDetailsService requestDetailsService;

	@Override
	public RechargeResponse recharge(RechargeRequest rechargeRequest) {
		String requestNo = rechargeRequest.getRequestNo();
		RechargeResponse response = new RechargeResponse();
		response.setRequestNo(requestNo);

		if (!userService.verifyPassword(rechargeRequest.getUserNo(), rechargeRequest.getPassword())) {
			throw new BusinessException(LocalReturnCode.E_200302.getDesc());
		}

		RechargeDetails rechargeDetails = new RechargeDetails();
		BeanUtils.copyProperties(rechargeRequest, rechargeDetails);
		rechargeDetails.setStatus(StatusCode.STATUS_OUT.getCode());
		save(rechargeDetails);

		try {
			DepositoryBankCard depositoryBankCard = userService.getDepositoryBankCardByUserNo(rechargeRequest.getUserNo());
			bankCardService.decreaseBalance(depositoryBankCard.getCardNumber(), rechargeRequest.getAmount());

			balanceDetailsService.recharge(rechargeRequest);

			rechargeDetails.setStatus(StatusCode.STATUS_IN.getCode());
			updateById(rechargeDetails);

			response.setSuccess();
			requestDetailsService.modifyGatewayByRequestNo(response);

			//producer.recharge(rechargeRequest.getAppCode(), response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);

			rechargeDetails.setStatus(StatusCode.STATUS_FAIL.getCode());
			updateById(rechargeDetails);

			response.setFailure();
			response.setRespMsg(e.getMessage());
			requestDetailsService.modifyGatewayByRequestNo(response);

			//producer.recharge(rechargeRequest.getAppCode(), response);
			throw new BusinessException(response.getRequestNo(), RemoteReturnCode.EXCEPTION, e.getMessage(), e);
		}
		return response;
	}
}
