package com.company.asset.modules.user.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * User query DTO
 */
@Data
public class UserQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Username (fuzzy)
     */
    private String username;

    /**
     * Real name (fuzzy)
     */
    private String realName;

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

    /**
     * Current page
     */
    private Long current = 1L;

    /**
     * Page size
     */
    private Long size = 10L;
}
