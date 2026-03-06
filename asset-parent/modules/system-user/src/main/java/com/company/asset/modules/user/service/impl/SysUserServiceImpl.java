package com.company.asset.modules.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.asset.common.core.exception.BusinessException;
import com.company.asset.common.core.result.PageResult;
import com.company.asset.modules.user.dto.UserCreateDTO;
import com.company.asset.modules.user.dto.UserQueryDTO;
import com.company.asset.modules.user.dto.UserUpdateDTO;
import com.company.asset.modules.user.entity.SysUser;
import com.company.asset.modules.user.mapper.SysUserMapper;
import com.company.asset.modules.user.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * User service implementation
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final PasswordEncoder passwordEncoder;

    @Override
    public SysUser getByUsername(String username) {
        return this.getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, username));
    }

    @Override
    public PageResult<SysUser> queryPage(UserQueryDTO queryDTO) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        
        if (StringUtils.hasText(queryDTO.getUsername())) {
            wrapper.like(SysUser::getUsername, queryDTO.getUsername());
        }
        if (StringUtils.hasText(queryDTO.getRealName())) {
            wrapper.like(SysUser::getRealName, queryDTO.getRealName());
        }
        if (queryDTO.getDeptId() != null) {
            wrapper.eq(SysUser::getDeptId, queryDTO.getDeptId());
        }
        if (StringUtils.hasText(queryDTO.getRoleCode())) {
            wrapper.eq(SysUser::getRoleCode, queryDTO.getRoleCode());
        }
        if (queryDTO.getStatus() != null) {
            wrapper.eq(SysUser::getStatus, queryDTO.getStatus());
        }
        
        wrapper.orderByDesc(SysUser::getCreateTime);
        
        IPage<SysUser> page = this.page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()), wrapper);
        
        // Mask sensitive fields
        page.getRecords().forEach(user -> {
            if (StringUtils.hasText(user.getIdCard())) {
                user.setIdCard(maskIdCard(user.getIdCard()));
            }
            if (StringUtils.hasText(user.getPhone())) {
                user.setPhone(maskPhone(user.getPhone()));
            }
        });
        
        return PageResult.of(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createUser(UserCreateDTO createDTO) {
        // Check username uniqueness
        SysUser existingUser = this.getByUsername(createDTO.getUsername());
        if (existingUser != null) {
            throw new BusinessException("Username already exists");
        }
        
        SysUser user = new SysUser();
        user.setUsername(createDTO.getUsername());
        user.setPassword(passwordEncoder.encode(createDTO.getPassword()));
        user.setRealName(createDTO.getRealName());
        user.setPhone(createDTO.getPhone());
        user.setEmail(createDTO.getEmail());
        user.setDeptId(createDTO.getDeptId());
        user.setRoleCode(createDTO.getRoleCode());
        user.setStatus(1);
        
        this.save(user);
        log.info("User created: {}", user.getUsername());
        
        return user.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(UserUpdateDTO updateDTO) {
        SysUser user = this.getById(updateDTO.getId());
        if (user == null) {
            throw new BusinessException("User not found");
        }
        
        if (StringUtils.hasText(updateDTO.getRealName())) {
            user.setRealName(updateDTO.getRealName());
        }
        if (StringUtils.hasText(updateDTO.getPhone())) {
            user.setPhone(updateDTO.getPhone());
        }
        if (StringUtils.hasText(updateDTO.getEmail())) {
            user.setEmail(updateDTO.getEmail());
        }
        if (updateDTO.getDeptId() != null) {
            user.setDeptId(updateDTO.getDeptId());
        }
        if (StringUtils.hasText(updateDTO.getRoleCode())) {
            user.setRoleCode(updateDTO.getRoleCode());
        }
        if (updateDTO.getStatus() != null) {
            user.setStatus(updateDTO.getStatus());
        }
        
        this.updateById(user);
        log.info("User updated: {}", user.getUsername());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long id) {
        SysUser user = this.getById(id);
        if (user == null) {
            throw new BusinessException("User not found");
        }
        
        this.removeById(id);
        log.info("User deleted: {}", user.getUsername());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(Long id, String newPassword) {
        SysUser user = this.getById(id);
        if (user == null) {
            throw new BusinessException("User not found");
        }
        
        user.setPassword(passwordEncoder.encode(newPassword));
        this.updateById(user);
        log.info("User password reset: {}", user.getUsername());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        SysUser user = this.getById(userId);
        if (user == null) {
            throw new BusinessException("User not found");
        }
        
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new BusinessException("Incorrect original password");
        }
        
        user.setPassword(passwordEncoder.encode(newPassword));
        this.updateById(user);
        log.info("User password changed: {}", user.getUsername());
    }

    @Override
    public List<SysUser> listByDeptId(Long deptId) {
        return this.list(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getDeptId, deptId)
                .eq(SysUser::getStatus, 1));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void enableUser(Long id) {
        SysUser user = this.getById(id);
        if (user == null) {
            throw new BusinessException("User not found");
        }
        
        user.setStatus(1);
        this.updateById(user);
        log.info("User enabled: {}", user.getUsername());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void disableUser(Long id) {
        SysUser user = this.getById(id);
        if (user == null) {
            throw new BusinessException("User not found");
        }
        
        user.setStatus(0);
        this.updateById(user);
        log.info("User disabled: {}", user.getUsername());
    }

    /**
     * Mask ID card number
     */
    private String maskIdCard(String idCard) {
        if (idCard == null || idCard.length() < 8) {
            return "****";
        }
        return "**************" + idCard.substring(idCard.length() - 4);
    }

    /**
     * Mask phone number
     */
    private String maskPhone(String phone) {
        if (phone == null || phone.length() < 7) {
            return "****";
        }
        return phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4);
    }
}
