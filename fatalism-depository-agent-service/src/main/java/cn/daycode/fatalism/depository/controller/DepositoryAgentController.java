package cn.daycode.fatalism.depository.controller;

import cn.daycode.fatalism.api.consumer.model.ConsumerRequest;
import cn.daycode.fatalism.api.depository.DepositoryAgentApi;
import cn.daycode.fatalism.api.depository.GatewayRequest;
import cn.daycode.fatalism.api.depository.model.*;
import cn.daycode.fatalism.api.transaction.model.ModifyProjectStatusDTO;
import cn.daycode.fatalism.api.transaction.model.ProjectDTO;
import cn.daycode.fatalism.common.domain.RestResponse;
import cn.daycode.fatalism.depository.service.DepositoryRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "depository agent service", tags = "depository-agent")
@RestController
public class DepositoryAgentController implements DepositoryAgentApi {

    @Autowired
    private DepositoryRecordService depositoryRecordService;

    @Override
    @ApiOperation("createConsumer")
    @ApiImplicitParam(name = "consumerRequest", value = "open account information", required = true,
            dataType = "ConsumerRequest", paramType = "body")
    @PostMapping("/l/consumers")
    public RestResponse<GatewayRequest> createConsumer(@RequestBody ConsumerRequest  consumerRequest) {
        return RestResponse.success(depositoryRecordService.createConsumer(consumerRequest));
    }

    @Override
    @ApiOperation(value = "createProject")
    @ApiImplicitParam(name = "projectDTO", value = "createProject to depository system",
            required = true, dataType = "ProjectDTO", paramType = "body")
    @PostMapping("/l/createProject")
    public RestResponse<String> createProject(@RequestBody ProjectDTO projectDTO) {
        DepositoryResponseDTO<DepositoryBaseResponse> depositoryResponse =
                depositoryRecordService.createProject(projectDTO);
        RestResponse<String> restResponse=new RestResponse<String>();
        restResponse.setResult(depositoryResponse.getRespData().getRespCode());
        restResponse.setMsg(depositoryResponse.getRespData().getRespMsg());
        return restResponse;
    }

    @Override
    @ApiOperation(value = "userAutoPreTransaction")
    @ApiImplicitParam(name = "userAutoPreTransactionRequest",
            value = "platform send msg to depository system", required = true,
            dataType = "UserAutoPreTransactionRequest", paramType =
            "body")
    @PostMapping("/l/user-auto-pre-transaction")
    public RestResponse<String> userAutoPreTransaction(@RequestBody UserAutoPreTransactionRequest userAutoPreTransactionRequest) {
        DepositoryResponseDTO<DepositoryBaseResponse> depositoryResponse = depositoryRecordService.userAutoPreTransaction(userAutoPreTransactionRequest);
        return getRestResponse(depositoryResponse);
    }

    @Override
    @ApiOperation(value = "confirmLoan")
    @ApiImplicitParam(name = "loanRequest", value = "loanRequest", required =
            true, dataType = "LoanRequest", paramType = "body")
    @PostMapping("l/confirm-loan")
    public RestResponse<String> confirmLoan(@RequestBody LoanRequest loanRequest) {
        DepositoryResponseDTO<DepositoryBaseResponse> depositoryResponse =depositoryRecordService.confirmLoan(loanRequest);
        return getRestResponse(depositoryResponse);
    }

    @Override
    @ApiOperation(value = "modifyProjectStatus")
    @ApiImplicitParam(name = "modifyProjectStatusDTO", value = "modifyProjectStatusDTO",
            required = true, dataType = "ModifyProjectStatusDTO",
            paramType = "body")
    @PostMapping("l/modify-project-status")
    public RestResponse<String> modifyProjectStatus(@RequestBody ModifyProjectStatusDTO modifyProjectStatusDTO) {
        DepositoryResponseDTO<DepositoryBaseResponse> depositoryResponseDTO=depositoryRecordService.modifyProjectStatus(modifyProjectStatusDTO);
        return getRestResponse(depositoryResponseDTO);
    }

    @Override
    @ApiOperation(value = "confirmRepayment")
    @ApiImplicitParam(name = "repaymentRequest", value = "Repayment information",
            required = true, dataType = "RepaymentRequest", paramType =
            "body")
    @PostMapping("l/confirm-repayment")
    public RestResponse<String> confirmRepayment(@RequestBody RepaymentRequest repaymentRequest) {
        DepositoryResponseDTO<DepositoryBaseResponse> depositoryResponseDTO=depositoryRecordService.confirmRepayment(repaymentRequest);
        return getRestResponse(depositoryResponseDTO);
    }


    private RestResponse<String>  getRestResponse(DepositoryResponseDTO<DepositoryBaseResponse> depositoryResponse) {
        final RestResponse<String> restResponse = new RestResponse<>();
        restResponse.setResult(depositoryResponse.getRespData().getRespCode());
        restResponse.setMsg(depositoryResponse.getRespData().getRespMsg());
        return restResponse;
    }
}
