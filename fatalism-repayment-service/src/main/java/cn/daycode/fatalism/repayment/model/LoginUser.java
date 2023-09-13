package cn.daycode.fatalism.repayment.model;

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
