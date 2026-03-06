package com.company.asset.modules.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.company.asset.common.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * System user entity
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class SysUser extends BaseEntity {

    /**
     * Username
     */
    private String username;

    /**
     * Password
     */
    private String password;

    /**
     * Real name
     */
    private String realName;

    /**
     * Phone number (encrypted)
     */
    private String phone;

    /**
     * ID card number (encrypted)
     */
    private String idCard;

    /**
     * Email
     */
    private String email;

    /**
     * Department ID
     */
    private Long deptId;

    /**
     * Role code (admin/asset_manager/employee)
     */
    private String roleCode;

    /**
     * Status (0: disabled, 1: normal)
     */
    private Integer status;
}
