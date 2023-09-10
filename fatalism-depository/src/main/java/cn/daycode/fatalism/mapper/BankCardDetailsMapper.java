package cn.daycode.fatalism.mapper;

import cn.daycode.fatalism.entity.BankCardDetails;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;


@Repository
public interface BankCardDetailsMapper extends BaseMapper<BankCardDetails> {

	BankCardDetails selectByBankCardId(Long bankCardId);
}
