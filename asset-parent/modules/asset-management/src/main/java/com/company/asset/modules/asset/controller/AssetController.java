package com.company.asset.modules.asset.controller;

import com.company.asset.common.core.result.PageResult;
import com.company.asset.common.core.result.Result;
import com.company.asset.modules.asset.dto.AssetCreateDTO;
import com.company.asset.modules.asset.dto.AssetQueryDTO;
import com.company.asset.modules.asset.dto.AssetUpdateDTO;
import com.company.asset.modules.asset.entity.AssetInfo;
import com.company.asset.modules.asset.service.AssetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Asset controller
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    /**
     * Query asset page
     */
    @GetMapping
    public Result<PageResult<AssetInfo>> queryPage(AssetQueryDTO queryDTO) {
        return Result.success(assetService.queryPage(queryDTO));
    }

    /**
     * Get asset detail
     */
    @GetMapping("/{id}")
    public Result<AssetInfo> getDetail(@PathVariable Long id) {
        AssetInfo asset = assetService.getById(id);
        if (asset == null) {
            return Result.notFound("Asset not found");
        }
        return Result.success(asset);
    }

    /**
     * Get asset by code
     */
    @GetMapping("/code/{code}")
    public Result<AssetInfo> getByCode(@PathVariable String code) {
        AssetInfo asset = assetService.getByCode(code);
        if (asset == null) {
            return Result.notFound("Asset not found");
        }
        return Result.success(asset);
    }

    /**
     * Create asset
     */
    @PostMapping
    @PreAuthorize("hasRole('admin') or hasRole('asset_manager')")
    public Result<Long> create(@Validated @RequestBody AssetCreateDTO createDTO) {
        Long assetId = assetService.createAsset(createDTO);
        return Result.success(assetId);
    }

    /**
     * Update asset
     */
    @PutMapping
    @PreAuthorize("hasRole('admin') or hasRole('asset_manager')")
    public Result<Void> update(@Validated @RequestBody AssetUpdateDTO updateDTO) {
        assetService.updateAsset(updateDTO);
        return Result.success();
    }

    /**
     * Delete asset
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public Result<Void> delete(@PathVariable Long id) {
        assetService.deleteAsset(id);
        return Result.success();
    }

    /**
     * Batch delete assets
     */
    @DeleteMapping("/batch")
    @PreAuthorize("hasRole('admin')")
    public Result<Void> batchDelete(@RequestBody List<Long> ids) {
        assetService.batchDelete(ids);
        return Result.success();
    }

    /**
     * Transfer asset
     */
    @PostMapping("/{id}/transfer")
    @PreAuthorize("hasRole('admin') or hasRole('asset_manager')")
    public Result<Void> transfer(@PathVariable Long id, 
                                 @RequestParam Long toUserId,
                                 @RequestParam Long toDeptId,
                                 @RequestParam(required = false) String remark) {
        assetService.transferAsset(id, toUserId, toDeptId, remark);
        return Result.success();
    }

    /**
     * Receive asset (assign to user)
     */
    @PostMapping("/{id}/receive")
    @PreAuthorize("hasRole('admin') or hasRole('asset_manager')")
    public Result<Void> receive(@PathVariable Long id,
                                @RequestParam Long userId,
                                @RequestParam Long deptId) {
        assetService.receiveAsset(id, userId, deptId);
        return Result.success();
    }

    /**
     * Return asset
     */
    @PostMapping("/{id}/return")
    public Result<Void> returnAsset(@PathVariable Long id) {
        assetService.returnAsset(id);
        return Result.success();
    }

    /**
     * Get assets by user
     */
    @GetMapping("/user/{userId}")
    public Result<List<AssetInfo>> listByUser(@PathVariable Long userId) {
        return Result.success(assetService.listByUserId(userId));
    }

    /**
     * Get assets by department
     */
    @GetMapping("/dept/{deptId}")
    public Result<List<AssetInfo>> listByDept(@PathVariable Long deptId) {
        return Result.success(assetService.listByDeptId(deptId));
    }

    /**
     * Get assets by category
     */
    @GetMapping("/category/{categoryId}")
    public Result<List<AssetInfo>> listByCategory(@PathVariable Long categoryId) {
        return Result.success(assetService.listByCategoryId(categoryId));
    }

    /**
     * Get asset statistics
     */
    @GetMapping("/statistics")
    public Result<AssetService.AssetStatistics> getStatistics(@RequestParam(required = false) Long userId) {
        return Result.success(assetService.getStatistics(userId));
    }

    /**
     * Get my assets
     */
    @GetMapping("/my")
    public Result<List<AssetInfo>> getMyAssets() {
        // Will be implemented with Spring Security context
        return Result.success(List.of());
    }

    /**
     * Get my asset statistics
     */
    @GetMapping("/my/statistics")
    public Result<AssetService.AssetStatistics> getMyStatistics() {
        // Will be implemented with Spring Security context
        return Result.success(new AssetService.AssetStatistics());
    }
}
