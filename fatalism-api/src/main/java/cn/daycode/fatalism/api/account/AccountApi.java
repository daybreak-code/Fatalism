package cn.daycode.fatalism.api.account;

import cn.daycode.fatalism.api.account.model.AccountDTO;
import cn.daycode.fatalism.api.account.model.AccountLoginDTO;
import cn.daycode.fatalism.api.account.model.AccountRegisterDTO;
import cn.daycode.fatalism.common.domain.RestResponse;

public interface AccountApi {

    RestResponse getSMSCode(String mobile);

    RestResponse<Integer> checkMobile(String mobile, String key, String code);

    RestResponse<AccountDTO> register(AccountRegisterDTO accountRegisterDTO);

    RestResponse<AccountDTO> login(AccountLoginDTO accountLoginDTO);
}
