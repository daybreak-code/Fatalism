package cn.daycode.fatalism.service;

import cn.daycode.fatalism.common.constant.StatusCode;
import cn.daycode.fatalism.common.domain.BusinessException;
import cn.daycode.fatalism.common.domain.RemoteReturnCode;
import cn.daycode.fatalism.domain.WithdrawRequest;
import cn.daycode.fatalism.domain.WithdrawResponse;
import cn.daycode.fatalism.entity.DepositoryBankCard;
import cn.daycode.fatalism.entity.WithdrawDetails;
import cn.daycode.fatalism.mapper.WithdrawDetailsMapper;
import cn.daycode.fatalism.message.GatewayMessageProducer;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class WithdrawDetailsServiceImpl extends ServiceImpl<WithdrawDetailsMapper, WithdrawDetails>
		implements WithdrawDetailsService {

	@Autowired
	private BalanceDetailsService balanceDetailsService;

	@Autowired
	private UserService userService;

	@Autowired
	private BankCardService bankCardService;

	@Autowired
	private GatewayMessageProducer producer;


	@Override
	@Transactional
	public WithdrawResponse withDraw(WithdrawRequest withdrawRequest) {

		WithdrawResponse response = new WithdrawResponse();
		response.setRequestNo(withdrawRequest.getRequestNo());

		WithdrawDetails withdrawDetails = new WithdrawDetails();
		BeanUtils.copyProperties(withdrawRequest, withdrawDetails);
		withdrawDetails.setStatus(StatusCode.STATUS_OUT.getCode());
		save(withdrawDetails);

		try {
			DepositoryBankCard depositoryBankCard = userService
					.getDepositoryBankCardByUserNo(withdrawRequest.getUserNo());
			bankCardService.increaseBalance(depositoryBankCard.getCardNumber(), withdrawRequest.getAmount());

			balanceDetailsService.withDraw(withdrawRequest);

			withdrawDetails.setStatus(StatusCode.STATUS_IN.getCode());
			updateById(withdrawDetails);

			response.setSuccess();
			//producer.withdraw(withdrawRequest.getAppCode(), response);
		} catch (Exception e) {
			log.error(e.getMessage());

			withdrawDetails.setStatus(StatusCode.STATUS_FAIL.getCode());
			updateById(withdrawDetails);

			response.setFailure();
			//producer.withdraw(withdrawRequest.getAppCode(), response);
			throw new BusinessException(response.getRequestNo(), RemoteReturnCode.EXCEPTION);
		}
		return response;

	}
}
