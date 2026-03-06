package com.company.asset.modules.asset.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Asset create DTO
 */
@Data
public class AssetCreateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Asset code
     */
    @NotBlank(message = "Asset code cannot be empty")
    private String assetCode;

    /**
     * Asset name
     */
    @NotBlank(message = "Asset name cannot be empty")
    private String assetName;

    /**
     * Category ID
     */
    @NotNull(message = "Category cannot be empty")
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
