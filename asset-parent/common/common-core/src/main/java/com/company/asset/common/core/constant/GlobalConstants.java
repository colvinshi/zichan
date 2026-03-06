package com.company.asset.common.core.constant;

/**
 * Global constants
 */
public class GlobalConstants {

    /**
     * Success code
     */
    public static final int SUCCESS_CODE = 200;
    
    /**
     * Error code
     */
    public static final int ERROR_CODE = 500;
    
    /**
     * Unauthorized code
     */
    public static final int UNAUTHORIZED_CODE = 401;
    
    /**
     * Forbidden code
     */
    public static final int FORBIDDEN_CODE = 403;
    
    /**
     * Not found code
     */
    public static final int NOT_FOUND_CODE = 404;
    
    /**
     * Business error code
     */
    public static final int BUSINESS_ERROR_CODE = 400;
    
    /**
     * Default page size
     */
    public static final int DEFAULT_PAGE_SIZE = 10;
    
    /**
     * Max page size
     */
    public static final int MAX_PAGE_SIZE = 100;
    
    /**
     * JWT secret key
     */
    public static final String JWT_SECRET_KEY = "asset-management-system-jwt-secret-key-2024";
    
    /**
     * Access token expiration time (2 hours)
     */
    public static final long ACCESS_TOKEN_EXPIRATION = 7200000L;
    
    /**
     * Refresh token expiration time (7 days)
     */
    public static final long REFRESH_TOKEN_EXPIRATION = 604800000L;
    
    /**
     * Token header
     */
    public static final String TOKEN_HEADER = "Authorization";
    
    /**
     * Token prefix
     */
    public static final String TOKEN_PREFIX = "Bearer ";
    
    /**
     * Login fail max attempts
     */
    public static final int LOGIN_FAIL_MAX_ATTEMPTS = 5;
    
    /**
     * Account lock duration (30 minutes)
     */
    public static final long ACCOUNT_LOCK_DURATION = 1800000L;
}
