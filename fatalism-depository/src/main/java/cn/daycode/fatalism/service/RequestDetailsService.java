package cn.daycode.fatalism.service;

import cn.daycode.fatalism.domain.PersonalRegisterResponse;
import cn.daycode.fatalism.entity.RequestDetails;
import com.baomidou.mybatisplus.extension.service.IService;


public interface RequestDetailsService extends IService<RequestDetails> {


	RequestDetails create(RequestDetails requestDetails);


	Boolean modifyByRequestNo(RequestDetails requestDetails);


	Boolean modifyGatewayByRequestNo(PersonalRegisterResponse response);


	RequestDetails getByRequestNo(String requestNo);

}
