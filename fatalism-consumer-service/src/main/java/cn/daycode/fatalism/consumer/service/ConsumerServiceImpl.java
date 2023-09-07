package cn.daycode.fatalism.consumer.service;

import cn.daycode.fatalism.api.account.model.AccountDTO;
import cn.daycode.fatalism.api.account.model.AccountRegisterDTO;
import cn.daycode.fatalism.api.consumer.model.*;
import cn.daycode.fatalism.api.depository.GatewayRequest;
import cn.daycode.fatalism.api.depository.model.DepositoryConsumerResponse;
import cn.daycode.fatalism.common.domain.*;
import cn.daycode.fatalism.common.util.CodeNoUtil;
import cn.daycode.fatalism.consumer.agent.AccountApiAgent;
import cn.daycode.fatalism.consumer.agent.DepositoryAgentApiAgent;
import cn.daycode.fatalism.consumer.common.ConsumerErrorCode;
import cn.daycode.fatalism.consumer.entity.BankCard;
import cn.daycode.fatalism.consumer.entity.Consumer;
import cn.daycode.fatalism.consumer.mapper.ConsumerMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumerServiceImpl extends ServiceImpl<ConsumerMapper, Consumer> implements ConsumerService{

    @Autowired
    private AccountApiAgent accountApiAgent;

    @Autowired
    private BankCardService bankCardService;

    @Autowired
    private DepositoryAgentApiAgent depositoryAgentApiAgent;

    @Override
    public Integer checkMobile(String mobile) {
        return getByMobile(mobile) != null ? 1 : 0;
    }

    @Override
    public void register(ConsumerRegisterDTO consumerRegisterDTO) {
        if (checkMobile(consumerRegisterDTO.getMobile()) == 1){
            throw new BusinessException(ConsumerErrorCode.E_140107);
        }
        Consumer consumer = new Consumer();
        BeanUtils.copyProperties(consumerRegisterDTO, consumer);
        consumer.setUsername(CodeNoUtil.getNo(CodePrefixCode.CODE_NO_PREFIX));
        consumerRegisterDTO.setUsername(consumer.getUsername());
        consumer.setUserNo(CodeNoUtil.getNo(CodePrefixCode.CODE_REQUEST_PREFIX));
        consumer.setIsBindCard(0);
        save(consumer);

        AccountRegisterDTO accountRegisterDTO = new AccountRegisterDTO();
        BeanUtils.copyProperties(consumerRegisterDTO, accountRegisterDTO);
        RestResponse<AccountDTO> restResponse = accountApiAgent.register(accountRegisterDTO);
        if (restResponse.getCode() != CommonErrorCode.SUCCESS.getCode()){
            throw new BusinessException(ConsumerErrorCode.E_140106);
        }

    }

    @Override
    public RestResponse<GatewayRequest> createConsumer(ConsumerRequest consumerRequest) {
        ConsumerDTO consumerDTO = getByMobile(consumerRequest.getMobile());
        if (consumerDTO.getIsBindCard() == 1){
            throw new BusinessException(ConsumerErrorCode.E_140105);
        }
        BankCardDTO bankCardDTO = bankCardService.getByCardNumber(consumerRequest.getCardNumber());
        if (bankCardDTO != null && bankCardDTO.getStatus() == StatusCode.STATUS_IN.getCode()) {
            throw new BusinessException(ConsumerErrorCode.E_140151);
        }
        consumerRequest.setId(consumerDTO.getId());
        consumerRequest.setUserNo(CodeNoUtil.getNo(CodePrefixCode.CODE_CONSUMER_PREFIX));
        consumerRequest.setRequestNo(CodeNoUtil.getNo(CodePrefixCode.CODE_REQUEST_PREFIX));

        consumerRequest.setId(consumerDTO.getId());
        consumerRequest.setUserNo(CodeNoUtil.getNo(CodePrefixCode.CODE_CONSUMER_PREFIX));
        consumerRequest.setRequestNo(CodeNoUtil.getNo(CodePrefixCode.CODE_REQUEST_PREFIX));
        UpdateWrapper<Consumer> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(Consumer::getMobile,consumerDTO.getMobile());
        updateWrapper.lambda().set(Consumer::getUserNo, consumerRequest.getUserNo());
        updateWrapper.lambda().set(Consumer::getRequestNo, consumerRequest.getRequestNo());
        //updateWrapper.lambda().set(Consumer::getFullname, consumerRequest.getFullname());
        //updateWrapper.lambda().set(Consumer::getIdNumber, consumerRequest.getIdNumber());
        updateWrapper.lambda().set(Consumer::getAuthList, "ALL");
        update(updateWrapper);

        BankCard bankCard=new BankCard();
        bankCard.setConsumerId(consumerDTO.getId());
        //bankCard.setBankCode(consumerRequest.getBankCode());
        bankCard.setCardNumber(consumerRequest.getCardNumber());
        bankCard.setMobile(consumerRequest.getMobile());
        bankCard.setStatus(StatusCode.STATUS_OUT.getCode());
        BankCardDTO existBankCard = bankCardService.getByConsumerId(bankCard.getConsumerId());
        if (existBankCard != null) {
            bankCard.setId(existBankCard.getId());
        }
        bankCardService.saveOrUpdate(bankCard);

        return depositoryAgentApiAgent.createConsumer(consumerRequest);
    }

    @Override
    public Boolean modifyResult(DepositoryConsumerResponse response) {
        Consumer consumer = getByRequestNo(response.getRequestNo());
        int status = DepositoryReturnCode.RETURN_CODE_00000.getCode()
                .equals(response.getRespCode()) ? StatusCode.STATUS_IN.getCode()
                :StatusCode.STATUS_FAIL.getCode();
        update(Wrappers.<Consumer>lambdaUpdate().eq(Consumer::getId, consumer.getId())
                .set(Consumer::getIsBindCard, status).set(Consumer::getStatus, status));
        return bankCardService.update(Wrappers.<BankCard>lambdaUpdate()
                .eq(BankCard::getConsumerId, consumer.getId())
                .set(BankCard::getStatus, status).set(BankCard::getBankCode, response.getBankCode())
                .set(BankCard::getBankName, response.getBankName()));
    }

    @Override
    public ConsumerDTO getByMobile(String mobile) {
        Consumer consumer = getOne(new QueryWrapper<Consumer>().lambda().eq(Consumer::getMobile, mobile));
        return null;
    }

    @Override
    public BorrowerDTO getBorrower(Long id) {
        return null;
    }

    private Consumer getByRequestNo(String requestNo){
        return getOne(Wrappers.<Consumer>lambdaQuery().eq(Consumer::getRequestNo,requestNo));
    }

    public void confirmRegister(ConsumerRegisterDTO consumerRegisterDTO){
        log.info("execute confirmRegister");
    }

    public void cancelRegister(ConsumerRegisterDTO consumerRegisterDTO){
        log.info("execute cancelRegister");
        //remove(Wrappers.lambdaQuery().eq(Consumer::getMobile, consumerRegisterDTO.getMobile()));
    }

    private ConsumerDTO convertConsumerEntityToDTO(Consumer entity){
        if (entity == null){
            return null;
        }
        ConsumerDTO dto = new ConsumerDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}
