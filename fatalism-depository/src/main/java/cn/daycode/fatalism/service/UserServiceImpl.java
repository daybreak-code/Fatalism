package cn.daycode.fatalism.service;

import cn.daycode.fatalism.common.domain.BusinessException;
import cn.daycode.fatalism.common.domain.LocalReturnCode;
import cn.daycode.fatalism.common.domain.RemoteReturnCode;
import cn.daycode.fatalism.domain.PersonalRegisterRequest;
import cn.daycode.fatalism.domain.PersonalRegisterResponse;
import cn.daycode.fatalism.entity.*;
import cn.daycode.fatalism.mapper.DepositoryBankCardMapper;
import cn.daycode.fatalism.mapper.UserMapper;
import cn.daycode.fatalism.message.GatewayMessageProducer;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

	@Autowired
	private BankUserService bankUserService;

	@Autowired
	private BankCardService bankCardService;

	@Autowired
	private DepositoryBankCardMapper depositoryBankCardMapper;

	@Autowired
	private BalanceDetailsService balanceDetailsService;

	@Autowired
	private GatewayMessageProducer producer;

	@Autowired
	private RequestDetailsService requestDetailsService;

	@Override
	@Transactional
	public PersonalRegisterResponse createUser(PersonalRegisterRequest personalRegisterRequest) {
		String requestNo = personalRegisterRequest.getRequestNo();
		PersonalRegisterResponse response = new PersonalRegisterResponse();
		response.setRequestNo(requestNo);
		response.setBankCode(personalRegisterRequest.getBankCode());
		response.setBankName(personalRegisterRequest.getBankName());

		BankUser bankUser = bankUserService
				.getUser(personalRegisterRequest.getFullname(), personalRegisterRequest.getIdNumber());
		if (bankUser == null) {
			throw new BusinessException(LocalReturnCode.E_200301.getDesc());
		}

		BankCard bankCard = new BankCard();
		bankCard.setUserId(bankUser.getId());
		bankCard.setBankCode(personalRegisterRequest.getBankCode());
		bankCard.setCardNumber(personalRegisterRequest.getCardNumber());
		if (!bankCardService.verify(bankCard)) {
			throw new BusinessException(LocalReturnCode.E_200103);
		}

		try {
			User user = new User();
			BeanUtils.copyProperties(personalRegisterRequest, user);
			user.setIsBindCard(1);
			user.setAuthList("ALL");
			save(user);

			bankCard = bankCardService.getByCardNumber(personalRegisterRequest.getCardNumber());
			DepositoryBankCard depositoryBankCard = new DepositoryBankCard();
			depositoryBankCard.setAppCode(personalRegisterRequest.getAppCode());
			BeanUtils.copyProperties(bankCard, depositoryBankCard);
			depositoryBankCard.setMobile(user.getMobile());
			depositoryBankCard.setUserId(user.getId());
			depositoryBankCard.setRequestNo(user.getRequestNo());
			depositoryBankCardMapper.insert(depositoryBankCard);

			balanceDetailsService.addForPersonalRegister(
					new BalanceDetails().setUserNo(user.getUserNO()).setAppCode(personalRegisterRequest.getAppCode())
							.setRequestContent(JSON.toJSONString(personalRegisterRequest)));

			response.setSuccess();
			requestDetailsService.modifyGatewayByRequestNo(response);

			//producer.personalRegister(personalRegisterRequest.getAppCode(), response);
		} catch (Exception e) {
			log.error(e.getMessage());

			response.setFailure();
			requestDetailsService.modifyGatewayByRequestNo(response);

			//producer.personalRegister(personalRegisterRequest.getAppCode(), response);
			throw new BusinessException(personalRegisterRequest.getRequestNo(), RemoteReturnCode.EXCEPTION);
		}

		return response;
	}

	@Override
	public DepositoryBankCard getDepositoryBankCardByUserNo(String userNo) {
		User user = getByUserNo(userNo);
		return depositoryBankCardMapper.selectOne(
				Wrappers.<DepositoryBankCard>query().lambda().eq(DepositoryBankCard::getUserId, user.getId()));
	}

	@Override
	public Boolean verifyPassword(String userNo, String password) {
		User user = getByUserNo(userNo);
		if (user != null) {
			return user.getPassword().equals(password);
		}
		return false;
	}


	private User getByUserNo(String userNo) {
		return getOne(Wrappers.<User>query().lambda().eq(User::getUserNO, userNo), false);
	}
}
