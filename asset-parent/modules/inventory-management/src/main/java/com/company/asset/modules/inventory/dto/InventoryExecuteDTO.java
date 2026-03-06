package com.company.asset.modules.inventory.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Inventory execute DTO
 */
@Data
public class InventoryExecuteDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Asset code
     */
    @NotNull(message = "Asset code cannot be empty")
    private String assetCode;

    /**
     * Actual status
     */
    private Integer actualStatus;

    /**
     * Inventory result (1: consistent, 2: found, 3: missing, 4: damaged)
     */
    @NotNull(message = "Inventory result cannot be empty")
    private Integer inventoryResult;

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
