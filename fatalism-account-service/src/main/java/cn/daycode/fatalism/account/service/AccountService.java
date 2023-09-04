package cn.daycode.fatalism.account.service;

import cn.daycode.fatalism.account.entity.Account;
import cn.daycode.fatalism.api.account.model.AccountDTO;
import cn.daycode.fatalism.api.account.model.AccountLoginDTO;
import cn.daycode.fatalism.api.account.model.AccountRegisterDTO;
import cn.daycode.fatalism.common.domain.RestResponse;
import com.baomidou.mybatisplus.extension.service.IService;

public interface AccountService extends IService<Account> {

    RestResponse getSMSCode(String mobile);

    Integer checkMobile(String mobile, String key, String code);

    AccountDTO register(AccountRegisterDTO accountRegisterDTO);

    AccountDTO login(AccountLoginDTO accountLoginDTO);

}
