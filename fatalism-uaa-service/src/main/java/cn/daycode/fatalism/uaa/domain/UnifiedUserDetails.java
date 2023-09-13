package cn.daycode.fatalism.uaa.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class UnifiedUserDetails implements UserDetails {

    private static final long serialVersionUID = 3957586021470480642L;


    protected List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

    private String username;

    private String password;


    private String mobile;

    private String tenantId;


    private String departmentId;


    private Map<String,List<String>> userAuthorities = new HashMap<>();


    private Map<String, Object> payload = new HashMap<>();


    public UnifiedUserDetails(String username, String password ) {
        this.username = username;
        this.password = password;
    }

    public UnifiedUserDetails(String username, String password , List<GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        grantedAuthorities.addAll(authorities);
    }


    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public Map<String, List<String>> getUserAuthorities() {
        return userAuthorities;
    }

    public void setUserAuthorities(Map<String, List<String>> userAuthorities) {
        this.userAuthorities = userAuthorities;
    }
}
