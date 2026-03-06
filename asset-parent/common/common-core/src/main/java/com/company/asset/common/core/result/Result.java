package com.company.asset.common.core.result;

import com.company.asset.common.core.constant.GlobalConstants;
import lombok.Data;

import java.io.Serializable;

/**
 * Unified API response result
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Status code
     */
    private int code;
    
    /**
     * Message
     */
    private String message;
    
    /**
     * Response data
     */
    private T data;
    
    /**
     * Success flag
     */
    private boolean success;
    
    /**
     * Timestamp
     */
    private long timestamp;
    
    /**
     * Request ID for tracking
     */
    private String requestId;

    public Result() {
        this.timestamp = System.currentTimeMillis();
    }

    public Result(int code, String message, T data, boolean success) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.success = success;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * Success result
     */
    public static <T> Result<T> success() {
        return new Result<>(GlobalConstants.SUCCESS_CODE, "Success", null, true);
    }

    /**
     * Success result with data
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(GlobalConstants.SUCCESS_CODE, "Success", data, true);
    }

    /**
     * Success result with message
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(GlobalConstants.SUCCESS_CODE, message, data, true);
    }

    /**
     * Error result
     */
    public static <T> Result<T> error() {
        return new Result<>(GlobalConstants.ERROR_CODE, "Internal Server Error", null, false);
    }

    /**
     * Error result with message
     */
    public static <T> Result<T> error(String message) {
        return new Result<>(GlobalConstants.ERROR_CODE, message, null, false);
    }

    /**
     * Error result with code and message
     */
    public static <T> Result<T> error(int code, String message) {
        return new Result<>(code, message, null, false);
    }

    /**
     * Business error result
     */
    public static <T> Result<T> businessError(String message) {
        return new Result<>(GlobalConstants.BUSINESS_ERROR_CODE, message, null, false);
    }

    /**
     * Unauthorized result
     */
    public static <T> Result<T> unauthorized() {
        return new Result<>(GlobalConstants.UNAUTHORIZED_CODE, "Unauthorized", null, false);
    }

    /**
     * Unauthorized result with message
     */
    public static <T> Result<T> unauthorized(String message) {
        return new Result<>(GlobalConstants.UNAUTHORIZED_CODE, message, null, false);
    }

    /**
     * Forbidden result
     */
    public static <T> Result<T> forbidden() {
        return new Result<>(GlobalConstants.FORBIDDEN_CODE, "Forbidden", null, false);
    }

    /**
     * Not found result
     */
    public static <T> Result<T> notFound() {
        return new Result<>(GlobalConstants.NOT_FOUND_CODE, "Not Found", null, false);
    }

    /**
     * Not found result with message
     */
    public static <T> Result<T> notFound(String message) {
        return new Result<>(GlobalConstants.NOT_FOUND_CODE, message, null, false);
    }

    /**
     * Set request ID
     */
    public Result<T> requestId(String requestId) {
        this.requestId = requestId;
        return this;
    }
}
