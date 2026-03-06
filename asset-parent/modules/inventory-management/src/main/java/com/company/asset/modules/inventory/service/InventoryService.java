package com.company.asset.modules.inventory.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.company.asset.common.core.result.PageResult;
import com.company.asset.modules.inventory.dto.InventoryTaskCreateDTO;
import com.company.asset.modules.inventory.dto.InventoryTaskQueryDTO;
import com.company.asset.modules.inventory.dto.InventoryTaskUpdateDTO;
import com.company.asset.modules.inventory.dto.InventoryExecuteDTO;
import com.company.asset.modules.inventory.entity.InventoryTask;
import com.company.asset.modules.inventory.entity.InventoryDetail;

import java.util.List;

/**
 * Inventory service interface
 */
public interface InventoryService extends IService<InventoryTask> {

    /**
     * Query inventory task page
     */
    PageResult<InventoryTask> queryTaskPage(InventoryTaskQueryDTO queryDTO);

    /**
     * Create inventory task
     */
    Long createTask(InventoryTaskCreateDTO createDTO);

    /**
     * Update inventory task
     */
    void updateTask(InventoryTaskUpdateDTO updateDTO);

    /**
     * Delete inventory task
     */
    void deleteTask(Long id);

    /**
     * Start inventory task
     */
    void startTask(Long id);

    /**
     * Complete inventory task
     */
    void completeTask(Long id);

    /**
     * Cancel inventory task
     */
    void cancelTask(Long id);

    /**
     * Get task details
     */
    List<InventoryDetail> getTaskDetails(Long taskId);

    /**
     * Execute inventory (scan asset)
     */
    void executeInventory(Long taskId, InventoryExecuteDTO executeDTO);

    /**
     * Submit inventory result
     */
    void submitInventoryResult(Long taskId);

    /**
     * Get my inventory tasks
     */
    List<InventoryTask> getMyTasks();

    /**
     * Get task statistics
     */
    TaskStatistics getTaskStatistics(Long taskId);
    
    /**
     * Task statistics
     */
    class TaskStatistics {
        private Long totalAssets;
        private Long consistentCount;
        private Long foundCount;
        private Long missingCount;
        private Long damagedCount;
        private Long completedCount;
        private Long pendingCount;
        
        public Long getTotalAssets() { return totalAssets; }
        public void setTotalAssets(Long totalAssets) { this.totalAssets = totalAssets; }
        public Long getConsistentCount() { return consistentCount; }
        public void setConsistentCount(Long consistentCount) { this.consistentCount = consistentCount; }
        public Long getFoundCount() { return foundCount; }
        public void setFoundCount(Long foundCount) { this.foundCount = foundCount; }
        public Long getMissingCount() { return missingCount; }
        public void setMissingCount(Long missingCount) { this.missingCount = missingCount; }
        public Long getDamagedCount() { return damagedCount; }
        public void setDamagedCount(Long damagedCount) { this.damagedCount = damagedCount; }
        public Long getCompletedCount() { return completedCount; }
        public void setCompletedCount(Long completedCount) { this.completedCount = completedCount; }
        public Long getPendingCount() { return pendingCount; }
        public void setPendingCount(Long pendingCount) { this.pendingCount = pendingCount; }
    }
}
