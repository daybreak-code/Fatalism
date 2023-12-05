package cn.daycode.fatalism.depository.controller;

import cn.daycode.fatalism.api.depository.model.DepositoryConsumerResponse;
import cn.daycode.fatalism.common.util.EncryptUtil;
import cn.daycode.fatalism.depository.message.GatewayMessageProducer;
import cn.daycode.fatalism.depository.service.DepositoryRecordService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Bank DepositoryNotify", tags = "depository-agent")
@RestController
@RequestMapping("/notify")
public class DepositoryNotifyController {

    @Autowired
    private DepositoryRecordService depositoryRecordService;

    @Autowired
    private GatewayMessageProducer gatewayMessageProducer;


    @ApiOperation("Bank reconciliation review")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceName", value = "serviceName",
                    required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "platformNo", value = "platformNo",
                    required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "signature", value = "signature",
                    required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "reqData", value = "reqData，json format",
                    required = true, dataType = "String", paramType = "query"),})
    @RequestMapping(value = "/gateway", method = RequestMethod.GET,params = "serviceName=PERSONAL_REGISTER")
    public String receiveDepositoryCreateConsumerResult(@RequestParam("serviceName") String serviceName,
                                                        @RequestParam("platformNo") String platformNo,
                                                        @RequestParam("signature") String signature,
                                                        @RequestParam("reqData") String reqData){
        DepositoryConsumerResponse depositoryConsumerResponse=JSON.parseObject(EncryptUtil.decodeUTF8StringBase64(reqData),DepositoryConsumerResponse.class);
        depositoryRecordService.modifyRequestStatus(depositoryConsumerResponse.getRequestNo(),depositoryConsumerResponse.getStatus());

        gatewayMessageProducer.personalRegister(depositoryConsumerResponse);

        return "OK";
    }
}
