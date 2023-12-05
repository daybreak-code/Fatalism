package cn.daycode.fatalism.controller;

import cn.daycode.fatalism.domain.*;
import cn.daycode.fatalism.service.BalanceDetailsService;
import cn.daycode.fatalism.common.constant.PreTransactionCode;
import cn.daycode.fatalism.common.util.EncryptUtil;
import cn.daycode.fatalism.service.ProjectService;
import cn.daycode.fatalism.service.TenderService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "Bank Depository API", tags = "Service")
public class ServiceController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TenderService tenderService;

    @Autowired
    private BalanceDetailsService balanceDetailsService;


    @ApiOperation("Create Tender")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceName", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "platformNo", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "signature",  required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "reqData",  required = true, dataType = "String", paramType = "query"),})
    @PostMapping(value = "/service", params = "serviceName=CREATE_PROJECT")
    public DepositoryResponse<CreateProjectResponse> createProject(@RequestParam String serviceName,
                                                                   @RequestParam String platformNo, @RequestParam String signature, @RequestParam String reqData) {
        CreateProjectResponse projectResponse = projectService.createProject(reqData);
        return new DepositoryResponse<>(projectResponse);
    }


    @ApiOperation("Change Tender Status")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceName",required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "platformNo", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "signature",  required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "reqData", required = true, dataType = "String", paramType = "query"),})
    @GetMapping(value = "/modify", params = "serviceName=MODIFY_PROJECT")
    public DepositoryResponse<ModifyProjectResponse> modifyProject(@RequestParam String serviceName,
                                                                   @RequestParam String platformNo, @RequestParam String signature, @RequestParam String reqData) {
        ModifyProjectResponse projectResponse = projectService.modifyProject(reqData);
        return new DepositoryResponse<>(projectResponse);
    }

    @ApiOperation("Pre-authorization")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceName", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "platformNo", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "signature",  required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "reqData",  required = true, dataType = "String", paramType = "query"),})
    @GetMapping(value = "/service", params = "serviceName=USER_AUTO_PRE_TRANSACTION")
    public DepositoryResponse<UserAutoPreTransactionResponse> authorizeAutoPreTransaction(
            @RequestParam String serviceName, @RequestParam String platformNo, @RequestParam String signature,
            @RequestParam String reqData) {
        UserAutoPreTransactionResponse transactionResponse = new UserAutoPreTransactionResponse();

        String decodeReqData = EncryptUtil.decodeUTF8StringBase64(reqData);
        UserAutoPreTransactionRequest request;
        request = JSON.parseObject(decodeReqData, UserAutoPreTransactionRequest.class);
        request.setAppCode(platformNo);

        if (PreTransactionCode.TENDER.getCode().equals(request.getBizType())) {
            transactionResponse = tenderService.autoPreTransactionForTender(request);
        } else if (PreTransactionCode.REPAYMENT.getCode().equals(request.getBizType())) {
            balanceDetailsService.autoPreTransactionForRepayment(request);
        }
        return new DepositoryResponse<>(transactionResponse);
    }

    @ApiOperation(value = "Pre-handler Cancel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "requestNo", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "preRequestNo",  required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "amount", required = true, dataType = "String", paramType = "query")})
    @PostMapping("/l/cancel-pre-freeze")
    public DepositoryResponse<String> cancelPreFreeze(@RequestParam String requestNo, @RequestParam String preRequestNo,
                                                      @RequestParam String amount) {
        return null;
    }

    @ApiOperation(value = "Tender Confirm")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceName", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "platformNo", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "signature",  required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "reqData", required = true, dataType = "String", paramType = "query"),})
    @GetMapping(value = "/service", params = "serviceName=CONFIRM_LOAN")
    public DepositoryResponse<ConfirmLoanResponse> confirmLoan(@RequestParam String serviceName,
                                                               @RequestParam String platformNo, @RequestParam String signature, @RequestParam String reqData) {
        ConfirmLoanResponse confirmLoanResponse = tenderService.confirmLoan(reqData);
        return new DepositoryResponse<>(confirmLoanResponse);
    }

    @ApiOperation(value = "recharge confirm")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceName", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "platformNo", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "signature",  required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "reqData",  required = true, dataType = "String", paramType = "query"),})
    @GetMapping(value = "/service", params = "serviceName=CONFIRM_REPAYMENT")
    public DepositoryResponse<ConfirmRepaymentResponse> confirmRepayment(@RequestParam String serviceName,
                                                                         @RequestParam String platformNo, @RequestParam String signature, @RequestParam String reqData) {
        ConfirmRepaymentResponse repaymentResponse = new ConfirmRepaymentResponse();
        return new DepositoryResponse<>(repaymentResponse);
    }

}
