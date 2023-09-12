package cn.daycode.fatalism.controller;

import cn.daycode.fatalism.common.util.CommonUtil;
import cn.daycode.fatalism.common.util.EncryptUtil;
import cn.daycode.fatalism.domain.PersonalRegisterRequest;
import cn.daycode.fatalism.domain.RechargeRequest;
import cn.daycode.fatalism.domain.WithdrawRequest;
import cn.daycode.fatalism.entity.BankCard;
import cn.daycode.fatalism.entity.DepositoryBankCard;
import cn.daycode.fatalism.service.BankCardService;
import cn.daycode.fatalism.service.UserService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@Api(value = "GatewayController", tags = "Gateway")
public class GatewayController {

    @Autowired
    private BankCardService bankCardService;

    @Autowired
    private UserService userService;

    @ApiOperation("Open Account")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceName", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "platformNo", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "signature",required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "reqData", required = true, dataType = "String", paramType = "query"),})
    @RequestMapping(value = "/gateway", method = RequestMethod.GET, params = "serviceName=PERSONAL_REGISTER")
    public ModelAndView create(@RequestParam("serviceName") String serviceName,
                               @RequestParam("platformNo") String platformNo, @RequestParam("signature") String signature,
                               @RequestParam("reqData") String reqData) {
        String decodeReqData = EncryptUtil.decodeUTF8StringBase64(reqData);
        PersonalRegisterRequest registerRequest = JSON.parseObject(decodeReqData, PersonalRegisterRequest.class);
        registerRequest.setAppCode(platformNo);
        registerRequest.setRole("B");
        BankCard bankCard = bankCardService.getByCardNumber(registerRequest.getCardNumber());
        if (bankCard != null) {
            registerRequest.setBankName(bankCard.getBankName());
            registerRequest.setBankCode(bankCard.getBankCode());
        }
        log.debug("Open Account Data：{}", JSON.toJSONString(registerRequest));

        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("consumer", registerRequest);
        return modelAndView;
    }

    @ApiOperation("Recharge")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceName",required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "platformNo",required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "signature",required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "reqData", required = true, dataType = "String", paramType = "query"),})
    @RequestMapping(value = "/gateway", method = RequestMethod.GET, params = "serviceName=RECHARGE")
    public ModelAndView recharge(@RequestParam("serviceName") String serviceName,
                                 @RequestParam("platformNo") String platformNo, @RequestParam("signature") String signature,
                                 @RequestParam("reqData") String reqData) {
        String decodeReqData = EncryptUtil.decodeUTF8StringBase64(reqData);
        RechargeRequest rechargeRequest = JSON.parseObject(decodeReqData, RechargeRequest.class);
        rechargeRequest.setAppCode(platformNo);

        DepositoryBankCard bankCard = userService.getDepositoryBankCardByUserNo(rechargeRequest.getUserNo());
        rechargeRequest.setBankCode(bankCard.getBankCode());
        rechargeRequest.setBankName(bankCard.getBankName());
        rechargeRequest.setCardNumber(bankCard.getCardNumber());
        rechargeRequest.setMobile(bankCard.getMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
        rechargeRequest.setMobile(CommonUtil.hiddenMobile(bankCard.getMobile()));
        log.debug("Recharge Data：{}", JSON.toJSONString(rechargeRequest));

        ModelAndView modelAndView = new ModelAndView("recharge");
        modelAndView.addObject("recharge", rechargeRequest);
        return modelAndView;
    }

    @ApiOperation("withdraw")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceName", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "platformNo", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "signature", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "reqData",  required = true, dataType = "String", paramType = "query"),})
    @RequestMapping(value = "/gateway", method = RequestMethod.GET, params = "serviceName=WITHDRAW")
    public ModelAndView withdraw(@RequestParam("serviceName") String serviceName,
                                 @RequestParam("platformNo") String platformNo, @RequestParam("signature") String signature,
                                 @RequestParam("reqData") String reqData) {

        String decodeReqData = EncryptUtil.decodeUTF8StringBase64(reqData);
        WithdrawRequest withdrawRequest = JSON.parseObject(decodeReqData, WithdrawRequest.class);
        withdrawRequest.setAppCode(platformNo);

        DepositoryBankCard bankCard = userService.getDepositoryBankCardByUserNo(withdrawRequest.getUserNo());
        withdrawRequest.setBankCode(bankCard.getBankCode());
        withdrawRequest.setBankName(bankCard.getBankName());
        withdrawRequest.setCardNumber(bankCard.getCardNumber());
        withdrawRequest.setMobile(bankCard.getMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));

        log.debug("Withdraw Data：{}", JSON.toJSONString(withdrawRequest));

        ModelAndView modelAndView = new ModelAndView("withdraw");
        modelAndView.addObject("withdraw", withdrawRequest);
        return modelAndView;
    }
}
