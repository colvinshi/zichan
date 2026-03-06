package com.company.asset.modules.inventory.controller;

import com.company.asset.common.core.result.PageResult;
import com.company.asset.common.core.result.Result;
import com.company.asset.modules.inventory.dto.InventoryExecuteDTO;
import com.company.asset.modules.inventory.dto.InventoryTaskCreateDTO;
import com.company.asset.modules.inventory.dto.InventoryTaskQueryDTO;
import com.company.asset.modules.inventory.dto.InventoryTaskUpdateDTO;
import com.company.asset.modules.inventory.entity.InventoryDetail;
import com.company.asset.modules.inventory.entity.InventoryTask;
import com.company.asset.modules.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Inventory controller
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    /**
     * Query inventory task page
     */
    @GetMapping("/tasks")
    @PreAuthorize("hasRole('admin') or hasRole('asset_manager')")
    public Result<PageResult<InventoryTask>> queryTaskPage(InventoryTaskQueryDTO queryDTO) {
        return Result.success(inventoryService.queryTaskPage(queryDTO));
    }

    /**
     * Get task detail
     */
    @GetMapping("/tasks/{id}")
    public Result<InventoryTask> getTaskDetail(@PathVariable Long id) {
        InventoryTask task = inventoryService.getById(id);
        if (task == null) {
            return Result.notFound("Task not found");
        }
        return Result.success(task);
    }

    /**
     * Create inventory task
     */
    @PostMapping("/tasks")
    @PreAuthorize("hasRole('admin') or hasRole('asset_manager')")
    public Result<Long> createTask(@Validated @RequestBody InventoryTaskCreateDTO createDTO) {
        Long taskId = inventoryService.createTask(createDTO);
        return Result.success(taskId);
    }

    /**
     * Update inventory task
     */
    @PutMapping("/tasks")
    @PreAuthorize("hasRole('admin') or hasRole('asset_manager')")
    public Result<Void> updateTask(@Validated @RequestBody InventoryTaskUpdateDTO updateDTO) {
        inventoryService.updateTask(updateDTO);
        return Result.success();
    }

    /**
     * Delete inventory task
     */
    @DeleteMapping("/tasks/{id}")
    @PreAuthorize("hasRole('admin')")
    public Result<Void> deleteTask(@PathVariable Long id) {
        inventoryService.deleteTask(id);
        return Result.success();
    }

    /**
     * Start inventory task
     */
    @PostMapping("/tasks/{id}/start")
    @PreAuthorize("hasRole('admin') or hasRole('asset_manager')")
    public Result<Void> startTask(@PathVariable Long id) {
        inventoryService.startTask(id);
        return Result.success();
    }

    /**
     * Complete inventory task
     */
    @PostMapping("/tasks/{id}/complete")
    @PreAuthorize("hasRole('admin') or hasRole('asset_manager')")
    public Result<Void> completeTask(@PathVariable Long id) {
        inventoryService.completeTask(id);
        return Result.success();
    }

    /**
     * Cancel inventory task
     */
    @PostMapping("/tasks/{id}/cancel")
    @PreAuthorize("hasRole('admin')")
    public Result<Void> cancelTask(@PathVariable Long id) {
        inventoryService.cancelTask(id);
        return Result.success();
    }

    /**
     * Get task details (inventory results)
     */
    @GetMapping("/tasks/{id}/details")
    public Result<List<InventoryDetail>> getTaskDetails(@PathVariable Long id) {
        return Result.success(inventoryService.getTaskDetails(id));
    }

    /**
     * Execute inventory (scan asset code)
     */
    @PostMapping("/tasks/{id}/execute")
    public Result<Void> executeInventory(@PathVariable Long id, 
                                          @Validated @RequestBody InventoryExecuteDTO executeDTO) {
        inventoryService.executeInventory(id, executeDTO);
        return Result.success();
    }

    /**
     * Submit inventory result
     */
    @PostMapping("/tasks/{id}/submit")
    @PreAuthorize("hasRole('admin') or hasRole('asset_manager')")
    public Result<Void> submitResult(@PathVariable Long id) {
        inventoryService.submitInventoryResult(id);
        return Result.success();
    }

    /**
     * Get task statistics
     */
    @GetMapping("/tasks/{id}/statistics")
    public Result<InventoryService.TaskStatistics> getTaskStatistics(@PathVariable Long id) {
        return Result.success(inventoryService.getTaskStatistics(id));
    }

    /**
     * Get my inventory tasks
     */
    @GetMapping("/my-tasks")
    public Result<List<InventoryTask>> getMyTasks() {
        return Result.success(inventoryService.getMyTasks());
    }
}
