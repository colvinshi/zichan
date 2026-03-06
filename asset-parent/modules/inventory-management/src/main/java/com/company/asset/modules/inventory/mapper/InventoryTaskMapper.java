package com.company.asset.modules.inventory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.company.asset.modules.inventory.entity.InventoryTask;
import org.apache.ibatis.annotations.Mapper;

/**
 * Inventory task mapper
 */
@Mapper
public interface InventoryTaskMapper extends BaseMapper<InventoryTask> {
}
