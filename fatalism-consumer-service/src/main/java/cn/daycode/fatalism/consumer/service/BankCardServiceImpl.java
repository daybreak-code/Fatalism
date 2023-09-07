package cn.daycode.fatalism.consumer.service;

import cn.daycode.fatalism.api.consumer.model.BankCardDTO;
import cn.daycode.fatalism.consumer.entity.BankCard;
import cn.daycode.fatalism.consumer.mapper.BankCardMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankCardServiceImpl extends ServiceImpl<BankCardMapper, BankCard> implements BankCardService {

    @Override
    public BankCardDTO getByConsumerId(Long consumerId) {
        BankCard one = getOne(new QueryWrapper<BankCard>().lambda().eq(BankCard::getConsumerId, consumerId));
        return convertBankCardEntityToDTO(one);
    }

    @Override
    public BankCardDTO getByCardNumber(String cardNumber) {
        //@todo
        //BankCard bankCard = getOne(new QueryWrapper<>().lambda().eq(BankCard::getCardNumber, cardNumber));
        return null;
    }

    private BankCardDTO convertBankCardEntityToDTO(BankCard bankCard){
        if (bankCard == null){
            return null;
        }
        BankCardDTO bankCardDTO = new BankCardDTO();
        BeanUtils.copyProperties(bankCard, bankCardDTO);
        return bankCardDTO;
    }
}
