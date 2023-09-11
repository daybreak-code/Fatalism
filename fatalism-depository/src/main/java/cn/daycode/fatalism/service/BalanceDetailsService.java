package cn.daycode.fatalism.service;

import cn.daycode.fatalism.domain.*;
import cn.daycode.fatalism.entity.BalanceDetails;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;


public interface BalanceDetailsService extends IService<BalanceDetails> {


	BalanceDetailsDTO getP2PBalanceDetails(String userNo);


	UserAutoPreTransactionResponse verifyEnough(String userNo, BigDecimal freezeAmount, UserAutoPreTransactionResponse response);


	Boolean freezeBalance(UserAutoPreTransactionRequest preTransactionRequest);


	Boolean increaseBalance(BalanceDetails balanceDetails, BigDecimal amount);


	Boolean decreaseBalance(BalanceDetails balanceDetails, BigDecimal amount);


	Boolean recharge(RechargeRequest rechargeRequest);


	Boolean withDraw(WithdrawRequest withDrawRequest);



	BalanceDetails getByUserNo(String userNo);


	Boolean addForPersonalRegister(BalanceDetails balanceDetails);


	UserAutoPreTransactionResponse autoPreTransactionForRepayment(UserAutoPreTransactionRequest preTransactionRequest);


	ConfirmRepaymentResponse confirmRepayment(String reqData);

}
