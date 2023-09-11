package cn.daycode.fatalism.service;

import cn.daycode.fatalism.common.constant.StatusCode;
import cn.daycode.fatalism.domain.PersonalRegisterResponse;
import cn.daycode.fatalism.entity.RequestDetails;
import cn.daycode.fatalism.mapper.RequestDetailsMapper;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class RequestDetailsServiceImpl extends ServiceImpl<RequestDetailsMapper, RequestDetails>
		implements RequestDetailsService {

	@Override
	public RequestDetails create(RequestDetails requestDetails) {
		requestDetails.setStatus(StatusCode.STATUS_OUT.getCode());
		save(requestDetails);
		return requestDetails;
	}

	@Override
	public Boolean modifyByRequestNo(RequestDetails requestDetails) {
		return update(requestDetails, new QueryWrapper<RequestDetails>().lambda()
				.eq(RequestDetails::getRequestNo, requestDetails.getRequestNo()));
	}

	@Override
	public Boolean modifyGatewayByRequestNo(PersonalRegisterResponse response) {
		RequestDetails requestDetails = new RequestDetails();
		requestDetails.setRequestNo(response.getRequestNo());
		requestDetails.setStatus(response.getStatus());
		requestDetails.setResponseData(JSON.toJSONString(response));
		return modifyByRequestNo(requestDetails);
	}

	@Override
	public RequestDetails getByRequestNo(String requestNo) {
		return getOne(new QueryWrapper<RequestDetails>().lambda().eq(RequestDetails::getRequestNo, requestNo), false);
	}
}
