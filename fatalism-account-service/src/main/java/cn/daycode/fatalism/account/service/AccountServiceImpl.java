package cn.daycode.fatalism.account.service;

import cn.daycode.fatalism.account.common.AccountErrorCode;
import cn.daycode.fatalism.account.entity.Account;
import cn.daycode.fatalism.account.mapper.AccountMapper;
import cn.daycode.fatalism.api.account.model.AccountDTO;
import cn.daycode.fatalism.api.account.model.AccountLoginDTO;
import cn.daycode.fatalism.api.account.model.AccountRegisterDTO;
import cn.daycode.fatalism.common.domain.BusinessException;
import cn.daycode.fatalism.common.domain.RestResponse;
import cn.daycode.fatalism.common.util.PasswordUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;
import lombok.extern.slf4j.Slf4j;import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
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
    @TwoPhaseBusinessAction(name = "accountLogin", commitMethod = "confirmRegister", rollbackMethod = "cancelRegister")
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
            account = getAccountByMobile(accountLoginDTO.getMobile());
        } else {
            account = getAccountByUsername(accountLoginDTO.getUsername());
        }
        if (null == account){
            throw new BusinessException(AccountErrorCode.E_130104);
        }
        AccountDTO accountDTO = convertAccountEntityToDTO(account);
        if (smsEnable){
            return accountDTO;
        }
        if (PasswordUtil.verify(accountLoginDTO.getPassword(), account.getPassword())){
            return accountDTO;
        }
        throw new BusinessException(AccountErrorCode.E_130105);
    }

    public void confirmRegister(AccountRegisterDTO registerDTO) {
        log.info("execute confirmRegister");
    }

    public void cancelRegister(AccountRegisterDTO registerDTO) {
        log.info("execute cancelRegister");
        remove(Wrappers.<Account>lambdaQuery().eq(Account::getUsername,
                registerDTO.getUsername()));
    }

    private Account getAccountByMobile(String mobile){
        return getOne(new QueryWrapper<Account>().lambda().eq(Account::getMobile,mobile));
    }

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
