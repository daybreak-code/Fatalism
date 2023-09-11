package cn.daycode.fatalism.service;

import cn.daycode.fatalism.domain.ConfirmLoanResponse;
import cn.daycode.fatalism.domain.UserAutoPreTransactionRequest;
import cn.daycode.fatalism.domain.UserAutoPreTransactionResponse;
import cn.daycode.fatalism.entity.Tender;
import com.baomidou.mybatisplus.extension.service.IService;


public interface TenderService extends IService<Tender> {


	UserAutoPreTransactionResponse autoPreTransactionForTender(UserAutoPreTransactionRequest preTransactionRequest);


	ConfirmLoanResponse confirmLoan(String reqData);

}
