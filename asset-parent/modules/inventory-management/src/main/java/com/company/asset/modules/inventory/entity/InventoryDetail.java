package com.company.asset.modules.inventory.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.company.asset.common.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * Inventory detail entity
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("inventory_detail")
public class InventoryDetail extends BaseEntity {

    /**
     * Task ID
     */
    private Long taskId;

    /**
     * Asset ID
     */
    private Long assetId;

    /**
     * Book status (asset status at inventory start)
     */
    private Integer bookStatus;

    /**
     * Actual status (status after inventory)
     */
    private Integer actualStatus;

    /**
     * Inventory result (1: consistent, 2: found, 3: missing, 4: damaged)
     */
    private Integer inventoryResult;

    /**
     * Inventory time
     */
    private LocalDateTime inventoryTime;

    /**
     * Inventory user ID
     */
    private Long inventoryUserId;

    /**
     * Actual location
     */
    private String location;

    /**
     * Remark
     */
    private String remark;

    /**
     * Photo URL
     */
    private String photoUrl;
}
