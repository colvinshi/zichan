package com.company.asset.modules.user.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.company.asset.common.core.exception.BusinessException;
import com.company.asset.common.core.result.PageResult;
import com.company.asset.modules.user.dto.UserQueryDTO;
import com.company.asset.modules.user.dto.UserCreateDTO;
import com.company.asset.modules.user.dto.UserUpdateDTO;
import com.company.asset.modules.user.entity.SysUser;

import java.util.List;

/**
 * User service interface
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * Query user by username
     */
    SysUser getByUsername(String username);

    /**
     * Query user page
     */
    PageResult<SysUser> queryPage(UserQueryDTO queryDTO);

    /**
     * Create user
     */
    Long createUser(UserCreateDTO createDTO);

    /**
     * Update user
     */
    void updateUser(UserUpdateDTO updateDTO);

    /**
     * Delete user
     */
    void deleteUser(Long id);

    /**
     * Reset password
     */
    void resetPassword(Long id, String newPassword);

    /**
     * Change password
     */
    void changePassword(Long userId, String oldPassword, String newPassword);

    /**
     * Get user list by department ID
     */
    List<SysUser> listByDeptId(Long deptId);

    /**
     * Enable user
     */
    void enableUser(Long id);

    /**
     * Disable user
     */
    void disableUser(Long id);
}
