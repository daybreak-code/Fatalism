package cn.daycode.fatalism.service;

import cn.daycode.fatalism.domain.PersonalRegisterRequest;
import cn.daycode.fatalism.domain.PersonalRegisterResponse;
import cn.daycode.fatalism.entity.balance.DepositoryBankCard;
import cn.daycode.fatalism.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;


public interface UserService extends IService<User> {


	PersonalRegisterResponse createUser(PersonalRegisterRequest personalRegisterRequest);


	DepositoryBankCard getDepositoryBankCardByUserNo(String userNo);


	Boolean verifyPassword(String userNo, String password);

}
