package com.company.asset.modules.inventory.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Inventory task create DTO
 */
@Data
public class InventoryTaskCreateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Task name
     */
    @NotBlank(message = "Task name cannot be empty")
    private String taskName;

    /**
     * Start time
     */
    @NotNull(message = "Start time cannot be empty")
    private LocalDateTime startTime;

    /**
     * End time
     */
    @NotNull(message = "End time cannot be empty")
    private LocalDateTime endTime;

    /**
     * Task scope (JSON array of department IDs and user IDs)
     */
    private String taskScope;

    /**
     * Task type (1: full, 2: partial)
     */
    private Integer taskType = 1;

    /**
     * Remark
     */
    private String remark;
}
