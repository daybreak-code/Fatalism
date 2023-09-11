package cn.daycode.fatalism.service;

import cn.daycode.fatalism.common.domain.PageVO;
import cn.daycode.fatalism.domain.BankCardDTO;
import cn.daycode.fatalism.domain.BankCardQuery;
import cn.daycode.fatalism.domain.BankCardRequest;
import cn.daycode.fatalism.entity.BankCard;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;


public interface BankCardService extends IService<BankCard> {


	String createBankCard(BankCardRequest bankCardRequest);


	BigDecimal getBalance(String cardNumber);


	PageVO<BankCardDTO> queryBankCards(BankCardQuery bankCardQuery, Integer pageNo, Integer pageSize,
									   String sortBy, String order);


	Boolean verify(BankCard bankCard);


	Boolean increaseBalance(String cardNumber, BigDecimal amount);


	Boolean decreaseBalance(String cardNumber, BigDecimal amount);

	BankCard getByCardNumber(String cardNumber);

}
