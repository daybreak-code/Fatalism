package cn.daycode.fatalism.account.controller;

import cn.daycode.fatalism.account.service.AccountService;
import cn.daycode.fatalism.api.account.AccountApi;
import cn.daycode.fatalism.api.account.model.AccountDTO;
import cn.daycode.fatalism.api.account.model.AccountLoginDTO;
import cn.daycode.fatalism.api.account.model.AccountRegisterDTO;
import cn.daycode.fatalism.common.domain.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "AccountController")
@RequestMapping("/account")
public class AccountController implements AccountApi {

    @Autowired
    private AccountService accountService;

    @ApiOperation("health")
    @GetMapping("/health")
    public String health(){
        return "OK";
    }

    @Override
    @ApiOperation("Got phone verification code")
    @ApiImplicitParam(name = "mobile", dataType = "String")
    @GetMapping("/sms/{mobile}")
    public RestResponse getSMSCode(@PathVariable String mobile){
        return accountService.getSMSCode(mobile);
    }


    @ApiOperation("verify phone num and verification code")
    @ApiImplicitParams({@ApiImplicitParam(name = "mobile", value = "phone num", required = true, dataType = "String"),
            @ApiImplicitParam(name = "key", value = "verify id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "code", value = "verify id", required = true, dataType = "String")
    })
    @Override
    @GetMapping("/mobiles/{mobile}/key/{key}/code/{code}")
    public RestResponse<Integer> checkMobile(String mobile, String key, String code) {
        return RestResponse.success(accountService.checkMobile(mobile, key, code));
    }

    @Override
    @ApiOperation("user register")
    @ApiImplicitParam(name = "accountRegisterDTO", value = "account register information", required = true,
            dataType = "AccountRegisterDTO", paramType = "body")
    @PostMapping(value = "/l/accounts")
    public RestResponse<AccountDTO> register(@RequestBody AccountRegisterDTO accountRegisterDTO) {
        return  RestResponse.success(accountService.register(accountRegisterDTO));
    }

    @ApiOperation("user login")
    @ApiImplicitParam(name = "accountLoginDTO", value = "login information", required = true,
            dataType = "AccountLoginDTO", paramType = "body")
    @PostMapping(value = "/l/accounts/session")
    @Override
    public RestResponse<AccountDTO> login(@RequestBody AccountLoginDTO accountLoginDTO) {
        return RestResponse.success(accountService.login(accountLoginDTO));
    }
}
