package cn.daycode.fatalism.service;

import cn.daycode.fatalism.domain.WithdrawRequest;
import cn.daycode.fatalism.domain.WithdrawResponse;
import cn.daycode.fatalism.entity.WithdrawDetails;
import com.baomidou.mybatisplus.extension.service.IService;

public interface WithdrawDetailsService extends IService<WithdrawDetails> {


    WithdrawResponse withDraw(WithdrawRequest withdrawRequest);

}
