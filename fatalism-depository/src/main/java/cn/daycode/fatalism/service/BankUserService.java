package cn.daycode.fatalism.service;

import cn.daycode.fatalism.entity.balance.BankUser;
import com.baomidou.mybatisplus.extension.service.IService;


public interface BankUserService extends IService<BankUser> {


	BankUser getUser(String fullname, String idNumber);

}
