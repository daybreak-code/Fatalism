package cn.daycode.fatalism.controller;

import cn.daycode.fatalism.common.constant.AuditStatusCode;
import cn.daycode.fatalism.common.constant.TransactionStatusCode;
import cn.daycode.fatalism.common.domain.BusinessException;
import cn.daycode.fatalism.common.domain.PageVO;
import cn.daycode.fatalism.common.util.EncryptUtil;
import cn.daycode.fatalism.domain.*;
import cn.daycode.fatalism.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;

@Slf4j
@RestController
@Api(value = "Bank Depository System API", tags = "Depository", description = "Bank Depository System API")
public class DepositoryController {

    @Autowired
    private BankCardService bankCardService;

    @Autowired
    private UserService userService;

    @Autowired
    private BalanceDetailsService balanceDetailsService;

    @Autowired
    private WithdrawDetailsService withdrawDetailsService;

    @Autowired
    private RechargeDetailsService rechargeDetailsService;


    @ApiOperation("getSMSCode")
    @ApiImplicitParam(name = "mobile", required = true, dataType = "String")
    @GetMapping("/smscode/{mobile}")
    public LocalResponse<String> getSMSCode(@PathVariable String mobile) {
        return LocalResponse.success();
    }

    @ApiOperation("createBankCard")
    @ApiImplicitParam(name = "bankCardRequest", required = true, dataType = "BankCardDTO", paramType = "body")
    @PostMapping("/bank-cards")
    public LocalResponse<String> createBankCard(@RequestBody BankCardRequest bankCardRequest) {
        bankCardService.createBankCard(bankCardRequest);
        return LocalResponse.success();
    }

    @ApiOperation("getBalance")
    @ApiImplicitParam(name = "cardNumber",required = true, dataType = "String")
    @GetMapping("/bank-cards/card-number/{cardNumber}")
    public LocalResponse<BigDecimal> getBalance(@PathVariable String cardNumber) {
        return LocalResponse.success(bankCardService.getBalance(cardNumber));
    }

    @ApiOperation("getP2PBalanceDetails")
    @ApiImplicitParam(name = "userNo", value = "user encode", required = true, dataType = "String")
    @GetMapping("/balance-details/{userNo}")
    public BalanceDetailsDTO getP2PBalanceDetails(@PathVariable String userNo) {
        BalanceDetailsDTO balanceDetailsDTO = balanceDetailsService.getP2PBalanceDetails(userNo);
        return balanceDetailsDTO;
    }

    @ApiOperation("queryBankCards")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bankCardQuery", dataType = "WithdrawRecordQueryDTO", paramType = "body"),
            @ApiImplicitParam(name = "pageNo", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "sortBy", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "order", dataType = "String", paramType = "query")})
    @PostMapping("/bank-cards/q")
    public LocalResponse<PageVO<BankCardDTO>> queryBankCards(@RequestBody BankCardQuery bankCardQuery,
                                                             @RequestParam Integer pageNo, @RequestParam Integer pageSize, @RequestParam String sortBy,
                                                             @RequestParam String order) {
        PageVO<BankCardDTO> pageVO = bankCardService.queryBankCards(bankCardQuery, pageNo, pageSize, sortBy, order);
        return LocalResponse.success(pageVO);
    }


    @ApiOperation("verifyPassword")
    @ApiImplicitParam(name = "userDTO",required = true, dataType = "UserDTO", paramType = "body")
    @PostMapping("/users/password")
    public LocalResponse<Integer> verifyPassword(@RequestBody UserDTO userDTO) {
        return LocalResponse.success(userService.verifyPassword(userDTO.getUserNo(), userDTO.getPassword()) ? 1 : 0);
    }

    @ApiOperation("createUser")
    @ApiImplicitParam(name = "personalRegisterRequest", required = true, dataType = "PersonalRegisterRequest", paramType = "form")
    @PostMapping(value = "/trans/users")
    public ModelAndView createUser(PersonalRegisterRequest personalRegisterRequest) {
        int code;
        String msg = "";
        try {
            PersonalRegisterResponse response = userService.createUser(personalRegisterRequest);
            code = response.getAuditStatus().equals(AuditStatusCode.PASSED.getCode()) ? 0 : 1;
        } catch (BusinessException e) {
            code = 2;
            msg = e.getMessage();
        }
        return new ModelAndView(
                "redirect:" + addRedirectAttributes(personalRegisterRequest.getCallbackUrl(), code, msg));
    }

    @ApiOperation("recharge")
    @ApiImplicitParam(name = "rechargeRequest", required = true, dataType = "RechargeRequest", paramType = "form")
    @PostMapping(value = "/trans/recharge-details")
    public ModelAndView recharge(RechargeRequest rechargeRequest) {
        int code;
        String msg = "";
        try {
            RechargeResponse response = rechargeDetailsService.recharge(rechargeRequest);
            code = response.getTransactionStatus().equals(TransactionStatusCode.SUCCESS.getCode()) ? 0 : 1;
        } catch (BusinessException e) {
            code = 2;
            msg = e.getMessage();
        }
        return new ModelAndView("redirect:" + addRedirectAttributes(rechargeRequest.getCallbackUrl(), code, msg));
    }

    @ApiOperation("用户提现")
    @ApiImplicitParam(name = "withdrawRequest",required = true, dataType = "WithdrawRequest", paramType = "form")
    @PostMapping(value = "/trans/withdraw-details")
    public ModelAndView withdraw(WithdrawRequest withdrawRequest) {
        int code;
        String msg = "";
        try {
            WithdrawResponse response = withdrawDetailsService.withDraw(withdrawRequest);
            code = response.getTransactionStatus().equals(TransactionStatusCode.SUCCESS.getCode()) ? 0 : 1;
        } catch (BusinessException e) {
            code = 2;
            msg = e.getMessage();
        }
        return new ModelAndView("redirect:" + addRedirectAttributes(withdrawRequest.getCallbackUrl(), code, msg));
    }


    private String addRedirectAttributes(String url, int code, String msg) {
        StringBuilder callbackUrl = new StringBuilder(url);
        callbackUrl.append("&code=").append(code);
        if (StringUtils.isNotBlank(msg)) {
            callbackUrl.append("&msg=").append(EncryptUtil.encodeURL(msg));
        }
        return callbackUrl.toString();
    }

}
