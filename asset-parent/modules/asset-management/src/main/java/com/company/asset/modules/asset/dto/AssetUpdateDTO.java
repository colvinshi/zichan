package com.company.asset.modules.asset.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Asset update DTO
 */
@Data
public class AssetUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Asset ID
     */
    @NotNull(message = "Asset ID cannot be empty")
    private Long id;

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
     * Status
     */
    private Integer status;

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
}
