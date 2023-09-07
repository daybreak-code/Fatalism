package cn.daycode.fatalism.consumer.service;

import cn.daycode.fatalism.api.consumer.model.BankCardDTO;
import cn.daycode.fatalism.consumer.entity.BankCard;
import com.baomidou.mybatisplus.extension.service.IService;

public interface BankCardService extends IService<BankCard> {

    BankCardDTO getByConsumerId(Long consumerId);

    BankCardDTO getByCardNumber(String cardNumber);

}
