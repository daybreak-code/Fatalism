package cn.daycode.fatalism.account.service;

import cn.daycode.fatalism.account.entity.Account;
import cn.daycode.fatalism.account.mapper.AccountMapper;
import cn.daycode.fatalism.api.account.model.AccountDTO;
import cn.daycode.fatalism.api.account.model.AccountLoginDTO;
import cn.daycode.fatalism.api.account.model.AccountRegisterDTO;
import cn.daycode.fatalism.common.domain.RestResponse;
import cn.daycode.fatalism.common.util.PasswordUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import org.dromara.hmily.annotation.Hmily;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService{

    @Autowired
    private SmsService smsService;

    @Value("${sms.enable}")
    private Boolean smsEnable;

    @Override
    public RestResponse getSMSCode(String mobile) {
        return smsService.getSMSCode(mobile);
    }

    @Override
    public Integer checkMobile(String mobile, String key, String code) {
        smsService.verifySmsCode(key, code);
        QueryWrapper<Account> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Account::getMobile, mobile);
        long count = count(wrapper);
        return count > 0 ? 1 : 0;
    }

    @Override
    //@Hmily//(confirmMethod = "confirmRegister",cancelMethod = "cancelRegister")
    public AccountDTO register(AccountRegisterDTO accountRegisterDTO) {
        Account account = new Account();
        account.setUsername(accountRegisterDTO.getUsername());
        account.setMobile(accountRegisterDTO.getMobile());;
        account.setPassword(PasswordUtil.generate(accountRegisterDTO.getPassword()));
        if (smsEnable){
            account.setPassword(PasswordUtil.generate(accountRegisterDTO.getMobile()));
        }
        account.setDomain("c");
        if (accountRegisterDTO.getMobile().equals("110")){
            throw new RuntimeException("I did it on purpose");
        }
        save(account);
        return convertAccountEntityToDTO(account);
    }

    @Override
    public AccountDTO login(AccountLoginDTO accountLoginDTO) {
        Account account = null;
        if (accountLoginDTO.getDomain().equalsIgnoreCase("c")){

        }
        return null;
    }

//    private Account getAccountByMobile(String mobile){
//        return getOne(new QueryWrapper<Account>())
//    }

    private Account getAccountByUsername(String username){
        return getOne(new QueryWrapper<Account>().lambda().eq(Account::getUsername, username));
    }

    private AccountDTO convertAccountEntityToDTO(Account entity){
        if (entity == null){
            return null;
        }
        AccountDTO dto = new AccountDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}
