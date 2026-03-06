package com.company.asset.modules.asset.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Asset query DTO
 */
@Data
public class AssetQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Keyword (asset code, name, serial number)
     */
    private String keyword;

    /**
     * Category ID
     */
    private Long categoryId;

    /**
     * Status
     */
    private Integer status;

    /**
     * Department ID
     */
    private Long deptId;

    /**
     * User ID
     */
    private Long userId;

    /**
     * Current page
     */
    private Long current = 1L;

    /**
     * Page size
     */
    private Long size = 10L;
}
