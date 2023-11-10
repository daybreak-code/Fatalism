package cn.daycode.fatalism.mapper;

import cn.daycode.fatalism.entity.balance.BankCard;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;


@Repository
public interface BankCardMapper extends BaseMapper<BankCard> {

//	Page<BankCard> queryBankCards(Page page, @Param(value = "card") BankCard bankCard);

}
