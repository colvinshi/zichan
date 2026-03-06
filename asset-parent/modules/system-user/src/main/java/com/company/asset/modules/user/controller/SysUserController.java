package com.company.asset.modules.user.controller;

import com.company.asset.common.core.result.PageResult;
import com.company.asset.common.core.result.Result;
import com.company.asset.modules.user.dto.UserCreateDTO;
import com.company.asset.modules.user.dto.UserQueryDTO;
import com.company.asset.modules.user.dto.UserUpdateDTO;
import com.company.asset.modules.user.entity.SysUser;
import com.company.asset.modules.user.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User controller
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService sysUserService;

    /**
     * Query user page
     */
    @GetMapping
    @PreAuthorize("hasRole('admin') or hasRole('asset_manager')")
    public Result<PageResult<SysUser>> queryPage(UserQueryDTO queryDTO) {
        return Result.success(sysUserService.queryPage(queryDTO));
    }

    /**
     * Get user detail
     */
    @GetMapping("/{id}")
    public Result<SysUser> getDetail(@PathVariable Long id) {
        SysUser user = sysUserService.getById(id);
        if (user == null) {
            return Result.notFound("User not found");
        }
        return Result.success(user);
    }

    /**
     * Create user
     */
    @PostMapping
    @PreAuthorize("hasRole('admin')")
    public Result<Long> create(@Validated @RequestBody UserCreateDTO createDTO) {
        Long userId = sysUserService.createUser(createDTO);
        return Result.success(userId);
    }

    /**
     * Update user
     */
    @PutMapping
    @PreAuthorize("hasRole('admin')")
    public Result<Void> update(@Validated @RequestBody UserUpdateDTO updateDTO) {
        sysUserService.updateUser(updateDTO);
        return Result.success();
    }

    /**
     * Delete user
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public Result<Void> delete(@PathVariable Long id) {
        sysUserService.deleteUser(id);
        return Result.success();
    }

    /**
     * Reset password
     */
    @PostMapping("/{id}/reset-password")
    @PreAuthorize("hasRole('admin')")
    public Result<Void> resetPassword(@PathVariable Long id, @RequestParam String newPassword) {
        sysUserService.resetPassword(id, newPassword);
        return Result.success();
    }

    /**
     * Enable user
     */
    @PostMapping("/{id}/enable")
    @PreAuthorize("hasRole('admin')")
    public Result<Void> enable(@PathVariable Long id) {
        sysUserService.enableUser(id);
        return Result.success();
    }

    /**
     * Disable user
     */
    @PostMapping("/{id}/disable")
    @PreAuthorize("hasRole('admin')")
    public Result<Void> disable(@PathVariable Long id) {
        sysUserService.disableUser(id);
        return Result.success();
    }

    /**
     * Get user list by department
     */
    @GetMapping("/dept/{deptId}")
    public Result<List<SysUser>> listByDept(@PathVariable Long deptId) {
        return Result.success(sysUserService.listByDeptId(deptId));
    }

    /**
     * Get current user info
     */
    @GetMapping("/current")
    public Result<SysUser> getCurrentUser() {
        // This will be implemented with Spring Security context
        return Result.success();
    }
}
