package com.company.asset.modules.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.asset.common.core.exception.BusinessException;
import com.company.asset.common.core.result.PageResult;
import com.company.asset.modules.asset.dto.AssetCreateDTO;
import com.company.asset.modules.asset.dto.AssetQueryDTO;
import com.company.asset.modules.asset.dto.AssetUpdateDTO;
import com.company.asset.modules.asset.entity.AssetInfo;
import com.company.asset.modules.asset.mapper.AssetInfoMapper;
import com.company.asset.modules.asset.service.AssetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Asset service implementation
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AssetServiceImpl extends ServiceImpl<AssetInfoMapper, AssetInfo> implements AssetService {

    @Override
    public PageResult<AssetInfo> queryPage(AssetQueryDTO queryDTO) {
        LambdaQueryWrapper<AssetInfo> wrapper = new LambdaQueryWrapper<>();
        
        if (StringUtils.hasText(queryDTO.getKeyword())) {
            wrapper.and(w -> w.like(AssetInfo::getAssetCode, queryDTO.getKeyword())
                    .or().like(AssetInfo::getAssetName, queryDTO.getKeyword())
                    .or().like(AssetInfo::getSerialNumber, queryDTO.getKeyword()));
        }
        if (queryDTO.getCategoryId() != null) {
            wrapper.eq(AssetInfo::getCategoryId, queryDTO.getCategoryId());
        }
        if (queryDTO.getStatus() != null) {
            wrapper.eq(AssetInfo::getStatus, queryDTO.getStatus());
        }
        if (queryDTO.getDeptId() != null) {
            wrapper.eq(AssetInfo::getDeptId, queryDTO.getDeptId());
        }
        if (queryDTO.getUserId() != null) {
            wrapper.eq(AssetInfo::getUserId, queryDTO.getUserId());
        }
        
        wrapper.orderByDesc(AssetInfo::getCreateTime);
        
        IPage<AssetInfo> page = this.page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()), wrapper);
        return PageResult.of(page);
    }

    @Override
    public AssetInfo getByCode(String assetCode) {
        return this.getOne(new LambdaQueryWrapper<AssetInfo>()
                .eq(AssetInfo::getAssetCode, assetCode));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createAsset(AssetCreateDTO createDTO) {
        // Check asset code uniqueness
        AssetInfo existing = this.getByCode(createDTO.getAssetCode());
        if (existing != null) {
            throw new BusinessException("Asset code already exists");
        }
        
        AssetInfo asset = new AssetInfo();
        asset.setAssetCode(createDTO.getAssetCode());
        asset.setAssetName(createDTO.getAssetName());
        asset.setCategoryId(createDTO.getCategoryId());
        asset.setBrand(createDTO.getBrand());
        asset.setModel(createDTO.getModel());
        asset.setSerialNumber(createDTO.getSerialNumber());
        asset.setStatus(2); // Default to idle
        asset.setDeptId(createDTO.getDeptId());
        asset.setLocation(createDTO.getLocation());
        asset.setPurchaseDate(createDTO.getPurchaseDate());
        asset.setPurchasePrice(createDTO.getPurchasePrice());
        asset.setWarrantyEndDate(createDTO.getWarrantyEndDate());
        asset.setSupplier(createDTO.getSupplier());
        asset.setRemark(createDTO.getRemark());
        
        // Generate QR code URL
        asset.setQrCode("/qr/" + asset.getAssetCode() + ".png");
        
        this.save(asset);
        log.info("Asset created: {}", asset.getAssetCode());
        
        return asset.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAsset(AssetUpdateDTO updateDTO) {
        AssetInfo asset = this.getById(updateDTO.getId());
        if (asset == null) {
            throw new BusinessException("Asset not found");
        }
        
        if (StringUtils.hasText(updateDTO.getAssetName())) {
            asset.setAssetName(updateDTO.getAssetName());
        }
        if (updateDTO.getCategoryId() != null) {
            asset.setCategoryId(updateDTO.getCategoryId());
        }
        if (StringUtils.hasText(updateDTO.getBrand())) {
            asset.setBrand(updateDTO.getBrand());
        }
        if (StringUtils.hasText(updateDTO.getModel())) {
            asset.setModel(updateDTO.getModel());
        }
        if (StringUtils.hasText(updateDTO.getSerialNumber())) {
            asset.setSerialNumber(updateDTO.getSerialNumber());
        }
        if (updateDTO.getStatus() != null) {
            asset.setStatus(updateDTO.getStatus());
        }
        if (updateDTO.getDeptId() != null) {
            asset.setDeptId(updateDTO.getDeptId());
        }
        if (StringUtils.hasText(updateDTO.getLocation())) {
            asset.setLocation(updateDTO.getLocation());
        }
        if (updateDTO.getPurchaseDate() != null) {
            asset.setPurchaseDate(updateDTO.getPurchaseDate());
        }
        if (updateDTO.getPurchasePrice() != null) {
            asset.setPurchasePrice(updateDTO.getPurchasePrice());
        }
        if (updateDTO.getWarrantyEndDate() != null) {
            asset.setWarrantyEndDate(updateDTO.getWarrantyEndDate());
        }
        if (StringUtils.hasText(updateDTO.getSupplier())) {
            asset.setSupplier(updateDTO.getSupplier());
        }
        if (StringUtils.hasText(updateDTO.getRemark())) {
            asset.setRemark(updateDTO.getRemark());
        }
        
        this.updateById(asset);
        log.info("Asset updated: {}", asset.getAssetCode());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAsset(Long id) {
        AssetInfo asset = this.getById(id);
        if (asset == null) {
            throw new BusinessException("Asset not found");
        }
        
        this.removeById(id);
        log.info("Asset deleted: {}", asset.getAssetCode());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        this.removeByIds(ids);
        log.info("Assets batch deleted: {}", ids.size());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transferAsset(Long assetId, Long toUserId, Long toDeptId, String remark) {
        AssetInfo asset = this.getById(assetId);
        if (asset == null) {
            throw new BusinessException("Asset not found");
        }
        
        Long fromUserId = asset.getUserId();
        Long fromDeptId = asset.getDeptId();
        
        asset.setUserId(toUserId);
        asset.setDeptId(toDeptId);
        asset.setStatus(1); // In use
        asset.setUpdateTime(LocalDateTime.now());
        
        this.updateById(asset);
        log.info("Asset transferred: {} from user {} to user {}", asset.getAssetCode(), fromUserId, toUserId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void receiveAsset(Long assetId, Long userId, Long deptId) {
        AssetInfo asset = this.getById(assetId);
        if (asset == null) {
            throw new BusinessException("Asset not found");
        }
        
        asset.setUserId(userId);
        asset.setDeptId(deptId);
        asset.setStatus(1); // In use
        asset.setUpdateTime(LocalDateTime.now());
        
        this.updateById(asset);
        log.info("Asset received: {} by user {}", asset.getAssetCode(), userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void returnAsset(Long assetId) {
        AssetInfo asset = this.getById(assetId);
        if (asset == null) {
            throw new BusinessException("Asset not found");
        }
        
        asset.setUserId(null);
        asset.setStatus(2); // Idle
        asset.setUpdateTime(LocalDateTime.now());
        
        this.updateById(asset);
        log.info("Asset returned: {}", asset.getAssetCode());
    }

    @Override
    public List<AssetInfo> listByUserId(Long userId) {
        return this.list(new LambdaQueryWrapper<AssetInfo>()
                .eq(AssetInfo::getUserId, userId)
                .orderByDesc(AssetInfo::getCreateTime));
    }

    @Override
    public List<AssetInfo> listByDeptId(Long deptId) {
        return this.list(new LambdaQueryWrapper<AssetInfo>()
                .eq(AssetInfo::getDeptId, deptId)
                .orderByDesc(AssetInfo::getCreateTime));
    }

    @Override
    public List<AssetInfo> listByCategoryId(Long categoryId) {
        return this.list(new LambdaQueryWrapper<AssetInfo>()
                .eq(AssetInfo::getCategoryId, categoryId)
                .orderByDesc(AssetInfo::getCreateTime));
    }

    @Override
    public AssetStatistics getStatistics(Long userId) {
        LambdaQueryWrapper<AssetInfo> wrapper = new LambdaQueryWrapper<>();
        if (userId != null) {
            wrapper.eq(AssetInfo::getUserId, userId);
        }
        
        List<AssetInfo> assets = this.list(wrapper);
        
        AssetStatistics stats = new AssetStatistics();
        stats.setTotalCount((long) assets.size());
        stats.setInUseCount(assets.stream().filter(a -> a.getStatus() == 1).count());
        stats.setIdleCount(assets.stream().filter(a -> a.getStatus() == 2).count());
        stats.setMaintenanceCount(assets.stream().filter(a -> a.getStatus() == 3).count());
        stats.setTotalValue(assets.stream()
                .filter(a -> a.getPurchasePrice() != null)
                .mapToDouble(a -> a.getPurchasePrice().doubleValue())
                .sum());
        
        return stats;
    }
}
