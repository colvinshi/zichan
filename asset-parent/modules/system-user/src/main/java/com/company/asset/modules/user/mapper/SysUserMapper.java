package com.company.asset.modules.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.company.asset.modules.user.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * User mapper
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}
