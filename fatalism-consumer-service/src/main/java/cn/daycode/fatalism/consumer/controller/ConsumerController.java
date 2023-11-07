package cn.daycode.fatalism.consumer.controller;

import cn.daycode.fatalism.api.consumer.ConsumerAPI;
import cn.daycode.fatalism.api.consumer.model.*;
import cn.daycode.fatalism.api.depository.GatewayRequest;
import cn.daycode.fatalism.common.domain.RestResponse;
import cn.daycode.fatalism.common.util.EncryptUtil;
import cn.daycode.fatalism.consumer.common.SecurityUtil;
import cn.daycode.fatalism.consumer.service.ConsumerService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Slf4j
@Api(value = "ConsumerController", tags = "Consumer", description = "Consumer Service API")
public class ConsumerController implements ConsumerAPI {

    @Value("${depository.url}")
    private String depositoryURL;

    private OkHttpClient okHttpClient = new OkHttpClient();

    @Autowired
    private ConsumerService consumerService;

    @ApiOperation("test")
    @GetMapping(path = "/hello")
    public String hello(){
        return "hello";
    }

    @Override
    @ApiOperation("user register")
    @ApiImplicitParam(name = "consumerRegisterDTO",value = "register information",
            required = true, dataType = "ConsumerRegisterDTO", paramType = "body")
    public RestResponse register(ConsumerRegisterDTO consumerRegisterDTO) {
        consumerService.register(consumerRegisterDTO);
        return RestResponse.success();
    }


    @ApiOperation("generate open account request data")
    @ApiImplicitParam(name = "consumerRequest", value = "open account information", required = true, dataType = "ConsumerRequest", paramType = "body")
    @PostMapping("/my/consumers")
    public RestResponse<GatewayRequest> createConsumer(@RequestBody ConsumerRequest consumerRequest) {
        consumerRequest.setMobile(SecurityUtil.getUser().getMobile());
        return consumerService.createConsumer(consumerRequest);
    }

    @Override
    @ApiOperation("got login user information")
    @GetMapping("/l/currConsumer/{mobile}")
    public RestResponse<ConsumerDTO> getCurrConsumer(@PathVariable("mobile") String mobile) {
        ConsumerDTO consumerDTO = consumerService.getByMobile(mobile);
        return RestResponse.success(consumerDTO);
    }

    @Override
    @ApiOperation("got login user information")
    @GetMapping("/my/consumers")
    public RestResponse<ConsumerDTO> getMyConsumer() {
        ConsumerDTO consumerDTO = consumerService.getByMobile(SecurityUtil.getUser().getMobile());
        return RestResponse.success(consumerDTO);
    }

    @Override
    @ApiOperation("got borrower user information")
    @ApiImplicitParam(name = "id", value = "用户标识", required = true,
            dataType = "Long", paramType = "path")
    @GetMapping("/my/borrowers/{id}")
    public RestResponse<BorrowerDTO> getBorrower(@PathVariable Long id) {
        return RestResponse.success(consumerService.getBorrower(id));
    }

    @Override
    @ApiOperation("got borrower user information - for service")
    @ApiImplicitParam(name = "id", value = "id", required = true,
            dataType = "Long", paramType = "path")
    @GetMapping("/l/borrowers/{id}")
    public RestResponse<BorrowerDTO> getBorrowerMobile(@PathVariable Long id) {
        return RestResponse.success(consumerService.getBorrower(id));
    }


    @Override
    @ApiOperation("got borrower user balance")
    @ApiImplicitParam(name = "userNo", value = "user encode", required = true,
            dataType = "String")
    @GetMapping("/l/balances/{userNo}")
    public RestResponse<BalanceDetailsDTO> getBalance(@PathVariable String userNo) {
        return getBalanceFromDepository(userNo);
    }

    @Override
    @GetMapping("/my/balances")
    public RestResponse<BalanceDetailsDTO> getMyBalance() {
        ConsumerDTO consumerDTO=consumerService.getByMobile(SecurityUtil.getUser().getMobile());
        return getBalanceFromDepository(consumerDTO.getUserNo());
    }


    private RestResponse<BalanceDetailsDTO> getBalanceFromDepository(String userNo)
    {
        String url = depositoryURL + "/balance-details/" + userNo;
        BalanceDetailsDTO balanceDetailsDTO;
        Request request = new Request.Builder().url(url).build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                balanceDetailsDTO = JSON.parseObject(responseBody,
                        BalanceDetailsDTO.class);
                return RestResponse.success(balanceDetailsDTO);
            }
        } catch (IOException e) {
            log.warn("call depository system {} got balance failure ", url, e);
        } return RestResponse.validfail("got failure");
    }

    @ApiOperation("pass gateway resource been protected，start authentication interceptor test")
    @ApiImplicitParam(name = "jsonToken", value = "token", required = true,
            dataType = "String")
    @GetMapping(value = "/m/consumers/test")
    public RestResponse<String> testResources(String jsonToken) {
        return RestResponse.success(EncryptUtil.decodeUTF8StringBase64(jsonToken));
    }
}
