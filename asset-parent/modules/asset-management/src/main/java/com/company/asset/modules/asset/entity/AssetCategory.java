package com.company.asset.modules.asset.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.company.asset.common.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Asset category entity (tree structure)
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("asset_category")
public class AssetCategory extends BaseEntity {

    /**
     * Category name
     */
    private String categoryName;

    /**
     * Parent category ID
     */
    private Long parentId;

    /**
     * Category code
     */
    private String categoryCode;

    /**
     * Category level (1, 2, 3...)
     */
    private Integer level;

    /**
     * Sort order
     */
    private Integer sort;

    /**
     * Category icon
     */
    private String icon;

    /**
     * Status (0: disabled, 1: normal)
     */
    private Integer status;
}
