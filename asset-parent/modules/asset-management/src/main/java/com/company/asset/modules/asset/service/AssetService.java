package com.company.asset.modules.asset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.company.asset.common.core.result.PageResult;
import com.company.asset.modules.asset.dto.AssetCreateDTO;
import com.company.asset.modules.asset.dto.AssetQueryDTO;
import com.company.asset.modules.asset.dto.AssetUpdateDTO;
import com.company.asset.modules.asset.entity.AssetInfo;

import java.util.List;

/**
 * Asset service interface
 */
public interface AssetService extends IService<AssetInfo> {

    /**
     * Query asset page
     */
    PageResult<AssetInfo> queryPage(AssetQueryDTO queryDTO);

    /**
     * Query asset by code
     */
    AssetInfo getByCode(String assetCode);

    /**
     * Create asset
     */
    Long createAsset(AssetCreateDTO createDTO);

    /**
     * Update asset
     */
    void updateAsset(AssetUpdateDTO updateDTO);

    /**
     * Delete asset
     */
    void deleteAsset(Long id);

    /**
     * Batch delete assets
     */
    void batchDelete(List<Long> ids);

    /**
     * Transfer asset
     */
    void transferAsset(Long assetId, Long toUserId, Long toDeptId, String remark);

    /**
     * Receive asset
     */
    void receiveAsset(Long assetId, Long userId, Long deptId);

    /**
     * Return asset
     */
    void returnAsset(Long assetId);

    /**
     * Get assets by user ID
     */
    List<AssetInfo> listByUserId(Long userId);

    /**
     * Get assets by department ID
     */
    List<AssetInfo> listByDeptId(Long deptId);

    /**
     * Get assets by category ID
     */
    List<AssetInfo> listByCategoryId(Long categoryId);

    /**
     * Get asset statistics
     */
    AssetStatistics getStatistics(Long userId);
    
    /**
     * Asset statistics
     */
    class AssetStatistics {
        private Long totalCount;
        private Long inUseCount;
        private Long idleCount;
        private Long maintenanceCount;
        private Double totalValue;
        
        public Long getTotalCount() { return totalCount; }
        public void setTotalCount(Long totalCount) { this.totalCount = totalCount; }
        public Long getInUseCount() { return inUseCount; }
        public void setInUseCount(Long inUseCount) { this.inUseCount = inUseCount; }
        public Long getIdleCount() { return idleCount; }
        public void setIdleCount(Long idleCount) { this.idleCount = idleCount; }
        public Long getMaintenanceCount() { return maintenanceCount; }
        public void setMaintenanceCount(Long maintenanceCount) { this.maintenanceCount = maintenanceCount; }
        public Double getTotalValue() { return totalValue; }
        public void setTotalValue(Double totalValue) { this.totalValue = totalValue; }
    }
}
