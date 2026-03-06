package com.company.asset.common.security.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * JWT token payload
 */
@Data
public class JwtPayload implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * User ID
     */
    private Long userId;

    /**
     * Username
     */
    private String username;

    /**
     * Department ID
     */
    private Long deptId;

    /**
     * Role codes (comma separated)
     */
    private String roles;

    /**
     * Token type (access/refresh)
     */
    private String tokenType;

    /**
     * Issue time
     */
    private Long iat;

    /**
     * Expiration time
     */
    private Long exp;
}
