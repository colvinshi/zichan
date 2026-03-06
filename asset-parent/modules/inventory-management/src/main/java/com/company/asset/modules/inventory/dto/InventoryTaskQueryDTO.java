package com.company.asset.modules.inventory.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Inventory task query DTO
 */
@Data
public class InventoryTaskQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Task name (fuzzy)
     */
    private String taskName;

    /**
     * Status
     */
    private Integer status;

    /**
     * Task type
     */
    private Integer taskType;

    /**
     * Creator ID
     */
    private Long creatorId;

    /**
     * Current page
     */
    private Long current = 1L;

    /**
     * Page size
     */
    private Long size = 10L;
}
