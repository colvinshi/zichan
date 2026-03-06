package com.company.asset.modules.user.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * User update DTO
 */
@Data
public class UserUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * User ID
     */
    @NotNull(message = "User ID cannot be empty")
    private Long id;

    /**
     * Real name
     */
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
    private String roleCode;

    /**
     * Status
     */
    private Integer status;
}
