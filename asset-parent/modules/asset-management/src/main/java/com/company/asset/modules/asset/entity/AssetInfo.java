package com.company.asset.modules.asset.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.company.asset.common.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Asset information entity
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("asset_info")
public class AssetInfo extends BaseEntity {

    /**
     * Asset code (unique)
     */
    private String assetCode;

    /**
     * Asset name
     */
    private String assetName;

    /**
     * Category ID
     */
    private Long categoryId;

    /**
     * Brand
     */
    private String brand;

    /**
     * Model
     */
    private String model;

    /**
     * Serial number
     */
    private String serialNumber;

    /**
     * Status (1: in use, 2: idle, 3: maintenance, 4: scrapped)
     */
    private Integer status;

    /**
     * Current user ID
     */
    private Long userId;

    /**
     * Department ID
     */
    private Long deptId;

    /**
     * Storage location
     */
    private String location;

    /**
     * Purchase date
     */
    private LocalDate purchaseDate;

    /**
     * Purchase price
     */
    private BigDecimal purchasePrice;

    /**
     * Warranty end date
     */
    private LocalDate warrantyEndDate;

    /**
     * Supplier
     */
    private String supplier;

    /**
     * Remark
     */
    private String remark;

    /**
     * QR code URL
     */
    private String qrCode;
}
