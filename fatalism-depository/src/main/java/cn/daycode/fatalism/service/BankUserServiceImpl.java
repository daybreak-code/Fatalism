package cn.daycode.fatalism.service;

import cn.daycode.fatalism.entity.balance.BankUser;
import cn.daycode.fatalism.mapper.BankUserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class BankUserServiceImpl extends ServiceImpl<BankUserMapper, BankUser> implements BankUserService {

	@Override
	public BankUser getUser(String fullname, String idNumber) {
		return getOne(new QueryWrapper<BankUser>().lambda().eq(BankUser::getFullName, fullname)
				.eq(BankUser::getIdNumber, idNumber), false);
	}
}
