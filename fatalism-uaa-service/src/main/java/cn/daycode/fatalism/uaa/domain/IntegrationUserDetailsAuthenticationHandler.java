package cn.daycode.fatalism.uaa.domain;

import cn.daycode.fatalism.api.account.model.AccountDTO;
import cn.daycode.fatalism.api.account.model.AccountLoginDTO;
import cn.daycode.fatalism.common.domain.RestResponse;
import cn.daycode.fatalism.common.util.StringUtil;
import cn.daycode.fatalism.uaa.agent.AccountApiAgent;
import cn.daycode.fatalism.uaa.common.utils.ApplicationContextHelper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntegrationUserDetailsAuthenticationHandler {

    public UnifiedUserDetails authentication(String domain, String authenticationType,
                                             UsernamePasswordAuthenticationToken token) {

        String username=token.getName();
        if(StringUtil.isBlank(username)){
            throw  new BadCredentialsException("Account is null");
        }
        if(token.getCredentials()==null){
            throw  new BadCredentialsException("Password is null");
        }
        String presentedPassword=token.getCredentials().toString();

        AccountLoginDTO accountLoginDTO=new AccountLoginDTO();
        accountLoginDTO.setDomain(domain);
        accountLoginDTO.setUsername(username);
        accountLoginDTO.setMobile(username);
        accountLoginDTO.setPassword(presentedPassword);
        AccountApiAgent accountApiAgent=(AccountApiAgent) ApplicationContextHelper.getBean(AccountApiAgent.class);
        RestResponse<AccountDTO> restResponse=accountApiAgent.login(accountLoginDTO);

        if(restResponse.getCode()!=0){
            throw new BadCredentialsException("Login Failure");
        }

        UnifiedUserDetails unifiedUserDetails=new UnifiedUserDetails(restResponse.getResult().getUsername(),presentedPassword, AuthorityUtils.createAuthorityList());
        unifiedUserDetails.setMobile(restResponse.getResult().getMobile());
        return unifiedUserDetails;

    }

    private UnifiedUserDetails getUserDetails(String username) {
        Map<String, UnifiedUserDetails> userDetailsMap = new HashMap<>();
        userDetailsMap.put("admin",
                new UnifiedUserDetails("admin", "111111", AuthorityUtils.createAuthorityList("ROLE_PAGE_A", "PAGE_B")));
        userDetailsMap.put("xufan",
                new UnifiedUserDetails("xufan", "111111", AuthorityUtils.createAuthorityList("ROLE_PAGE_A", "PAGE_B")));

        userDetailsMap.get("admin").setDepartmentId("1");
        userDetailsMap.get("admin").setMobile("18611106983");
        userDetailsMap.get("admin").setTenantId("1");
        Map<String, List<String>> au1 = new HashMap<>();
        au1.put("ROLE1", new ArrayList<>());
        au1.get("ROLE1").add("p1");
        au1.get("ROLE1").add("p2");
        userDetailsMap.get("admin").setUserAuthorities(au1);
        Map<String, Object> payload1 = new HashMap<>();
        payload1.put("res", "res1111111");
        userDetailsMap.get("admin").setPayload(payload1);


        userDetailsMap.get("xufan").setDepartmentId("2");
        userDetailsMap.get("xufan").setMobile("18611106984");
        userDetailsMap.get("xufan").setTenantId("1");
        Map<String, List<String>> au2 = new HashMap<>();
        au2.put("ROLE2", new ArrayList<>());
        au2.get("ROLE2").add("p3");
        au2.get("ROLE2").add("p4");
        userDetailsMap.get("xufan").setUserAuthorities(au2);

        Map<String, Object> payload2 = new HashMap<>();
        payload2.put("res", "res222222");
        userDetailsMap.get("xufan").setPayload(payload2);

        return userDetailsMap.get(username);

    }
}
