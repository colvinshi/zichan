package com.company.asset.modules.user.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * User create DTO
 */
@Data
public class UserCreateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Username
     */
    @NotBlank(message = "Username cannot be empty")
    @Size(min = 3, max = 50, message = "Username length must be between 3 and 50")
    private String username;

    /**
     * Password
     */
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, max = 20, message = "Password length must be between 8 and 20")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).*$", 
              message = "Password must contain uppercase, lowercase, number and special character")
    private String password;

    /**
     * Real name
     */
    @NotBlank(message = "Real name cannot be empty")
    private String realName;

    /**
     * Phone number
     */
    private String phone;

    /**
     * Email
     */
    private String email;

    /**
     * Department ID
     */
    private Long deptId;

    /**
     * Role code
     */
    @NotBlank(message = "Role cannot be empty")
    private String roleCode;
}
