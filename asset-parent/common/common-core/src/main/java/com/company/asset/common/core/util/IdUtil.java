package com.company.asset.common.core.util;

import cn.hutool.core.util.StrUtil;

import java.util.UUID;

/**
 * ID generator utility
 */
public class IdUtil {

    /**
     * Generate UUID (no dash)
     */
    public static String generateUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * Generate simple UUID (8 characters)
     */
    public static String generateShortUuid() {
        return generateUuid().substring(0, 8);
    }

    /**
     * Generate request ID
     */
    public static String generateRequestId() {
        return "REQ-" + System.currentTimeMillis() + "-" + generateShortUuid();
    }

    /**
     * Check if ID is valid
     */
    public static boolean isValidId(Long id) {
        return id != null && id > 0;
    }

    /**
     * Check if string is not blank
     */
    public static boolean isNotBlank(String str) {
        return StrUtil.isNotBlank(str);
    }

    /**
     * Check if string is blank
     */
    public static boolean isBlank(String str) {
        return StrUtil.isBlank(str);
    }
}
