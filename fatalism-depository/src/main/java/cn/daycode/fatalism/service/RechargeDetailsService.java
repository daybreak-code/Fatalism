package cn.daycode.fatalism.service;

import cn.daycode.fatalism.domain.RechargeRequest;
import cn.daycode.fatalism.domain.RechargeResponse;
import cn.daycode.fatalism.entity.RechargeDetails;
import com.baomidou.mybatisplus.extension.service.IService;


public interface RechargeDetailsService extends IService<RechargeDetails> {


	RechargeResponse recharge(RechargeRequest rechargeRequest);

}
