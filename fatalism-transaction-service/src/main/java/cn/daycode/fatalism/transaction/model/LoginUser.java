package cn.daycode.fatalism.transaction.model;

import lombok.Data;

@Data
public class LoginUser {
    private String tenantId;
    private String departmentId;
    private String payload;
    private String username;
    private String mobile;
    private String userAuthorities;
    private String clientId;

}
