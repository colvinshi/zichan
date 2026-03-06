package com.company.asset.modules.inventory.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.company.asset.common.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * Inventory task entity
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("inventory_task")
public class InventoryTask extends BaseEntity {

    /**
     * Task name
     */
    private String taskName;

    /**
     * Task code
     */
    private String taskCode;

    /**
     * Start time
     */
    private LocalDateTime startTime;

    /**
     * End time
     */
    private LocalDateTime endTime;

    /**
     * Task scope (JSON - departments and users)
     */
    private String taskScope;

    /**
     * Task type (1: full, 2: partial)
     */
    private Integer taskType;

    /**
     * Status (1: pending, 2: in progress, 3: completed, 4: cancelled)
     */
    private Integer status;

    /**
     * Remark
     */
    private String remark;

    /**
     * Creator ID
     */
    private Long creatorId;
}
