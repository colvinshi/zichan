package com.company.asset.modules.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.asset.common.core.exception.BusinessException;
import com.company.asset.common.core.result.PageResult;
import com.company.asset.common.core.util.IdUtil;
import com.company.asset.modules.asset.entity.AssetInfo;
import com.company.asset.modules.asset.mapper.AssetInfoMapper;
import com.company.asset.modules.inventory.dto.InventoryExecuteDTO;
import com.company.asset.modules.inventory.dto.InventoryTaskCreateDTO;
import com.company.asset.modules.inventory.dto.InventoryTaskQueryDTO;
import com.company.asset.modules.inventory.dto.InventoryTaskUpdateDTO;
import com.company.asset.modules.inventory.entity.InventoryDetail;
import com.company.asset.modules.inventory.entity.InventoryTask;
import com.company.asset.modules.inventory.mapper.InventoryDetailMapper;
import com.company.asset.modules.inventory.mapper.InventoryTaskMapper;
import com.company.asset.modules.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Inventory service implementation
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl extends ServiceImpl<InventoryTaskMapper, InventoryTask> implements InventoryService {

    private final InventoryDetailMapper inventoryDetailMapper;
    private final AssetInfoMapper assetInfoMapper;

    @Override
    public PageResult<InventoryTask> queryTaskPage(InventoryTaskQueryDTO queryDTO) {
        LambdaQueryWrapper<InventoryTask> wrapper = new LambdaQueryWrapper<>();
        
        if (StringUtils.hasText(queryDTO.getTaskName())) {
            wrapper.like(InventoryTask::getTaskName, queryDTO.getTaskName());
        }
        if (queryDTO.getStatus() != null) {
            wrapper.eq(InventoryTask::getStatus, queryDTO.getStatus());
        }
        if (queryDTO.getTaskType() != null) {
            wrapper.eq(InventoryTask::getTaskType, queryDTO.getTaskType());
        }
        if (queryDTO.getCreatorId() != null) {
            wrapper.eq(InventoryTask::getCreatorId, queryDTO.getCreatorId());
        }
        
        wrapper.orderByDesc(InventoryTask::getCreateTime);
        
        IPage<InventoryTask> page = this.page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()), wrapper);
        return PageResult.of(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createTask(InventoryTaskCreateDTO createDTO) {
        InventoryTask task = new InventoryTask();
        task.setTaskName(createDTO.getTaskName());
        task.setTaskCode("INV-" + IdUtil.generateShortUuid().toUpperCase());
        task.setStartTime(createDTO.getStartTime());
        task.setEndTime(createDTO.getEndTime());
        task.setTaskScope(createDTO.getTaskScope());
        task.setTaskType(createDTO.getTaskType());
        task.setStatus(1); // Pending
        task.setRemark(createDTO.getRemark());
        
        this.save(task);
        log.info("Inventory task created: {}", task.getTaskCode());
        
        return task.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTask(InventoryTaskUpdateDTO updateDTO) {
        InventoryTask task = this.getById(updateDTO.getId());
        if (task == null) {
            throw new BusinessException("Task not found");
        }
        
        if (task.getStatus() != 1) {
            throw new BusinessException("Only pending tasks can be updated");
        }
        
        if (StringUtils.hasText(updateDTO.getTaskName())) {
            task.setTaskName(updateDTO.getTaskName());
        }
        if (updateDTO.getStartTime() != null) {
            task.setStartTime(updateDTO.getStartTime());
        }
        if (updateDTO.getEndTime() != null) {
            task.setEndTime(updateDTO.getEndTime());
        }
        if (StringUtils.hasText(updateDTO.getTaskScope())) {
            task.setTaskScope(updateDTO.getTaskScope());
        }
        if (StringUtils.hasText(updateDTO.getRemark())) {
            task.setRemark(updateDTO.getRemark());
        }
        
        this.updateById(task);
        log.info("Inventory task updated: {}", task.getTaskCode());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTask(Long id) {
        InventoryTask task = this.getById(id);
        if (task == null) {
            throw new BusinessException("Task not found");
        }
        
        if (task.getStatus() != 1 && task.getStatus() != 4) {
            throw new BusinessException("Only pending or cancelled tasks can be deleted");
        }
        
        this.removeById(id);
        log.info("Inventory task deleted: {}", task.getTaskCode());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void startTask(Long id) {
        InventoryTask task = this.getById(id);
        if (task == null) {
            throw new BusinessException("Task not found");
        }
        
        if (task.getStatus() != 1) {
            throw new BusinessException("Only pending tasks can be started");
        }
        
        task.setStatus(2); // In progress
        this.updateById(task);
        log.info("Inventory task started: {}", task.getTaskCode());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void completeTask(Long id) {
        InventoryTask task = this.getById(id);
        if (task == null) {
            throw new BusinessException("Task not found");
        }
        
        if (task.getStatus() != 2) {
            throw new BusinessException("Only in-progress tasks can be completed");
        }
        
        task.setStatus(3); // Completed
        this.updateById(task);
        log.info("Inventory task completed: {}", task.getTaskCode());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelTask(Long id) {
        InventoryTask task = this.getById(id);
        if (task == null) {
            throw new BusinessException("Task not found");
        }
        
        if (task.getStatus() == 3) {
            throw new BusinessException("Completed tasks cannot be cancelled");
        }
        
        task.setStatus(4); // Cancelled
        this.updateById(task);
        log.info("Inventory task cancelled: {}", task.getTaskCode());
    }

    @Override
    public List<InventoryDetail> getTaskDetails(Long taskId) {
        return inventoryDetailMapper.selectList(new LambdaQueryWrapper<InventoryDetail>()
                .eq(InventoryDetail::getTaskId, taskId)
                .orderByDesc(InventoryDetail::getInventoryTime));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void executeInventory(Long taskId, InventoryExecuteDTO executeDTO) {
        InventoryTask task = this.getById(taskId);
        if (task == null) {
            throw new BusinessException("Task not found");
        }
        
        if (task.getStatus() != 2) {
            throw new BusinessException("Task is not in progress");
        }
        
        // Find asset by code
        AssetInfo asset = assetInfoMapper.selectOne(new LambdaQueryWrapper<AssetInfo>()
                .eq(AssetInfo::getAssetCode, executeDTO.getAssetCode()));
        
        if (asset == null) {
            throw new BusinessException("Asset not found: " + executeDTO.getAssetCode());
        }
        
        // Check if already inventoried
        InventoryDetail existing = inventoryDetailMapper.selectOne(new LambdaQueryWrapper<InventoryDetail>()
                .eq(InventoryDetail::getTaskId, taskId)
                .eq(InventoryDetail::getAssetId, asset.getId()));
        
        if (existing != null) {
            // Update existing record
            existing.setActualStatus(executeDTO.getActualStatus());
            existing.setInventoryResult(executeDTO.getInventoryResult());
            existing.setLocation(executeDTO.getLocation());
            existing.setRemark(executeDTO.getRemark());
            existing.setPhotoUrl(executeDTO.getPhotoUrl());
            existing.setInventoryTime(LocalDateTime.now());
            inventoryDetailMapper.updateById(existing);
        } else {
            // Create new detail record
            InventoryDetail detail = new InventoryDetail();
            detail.setTaskId(taskId);
            detail.setAssetId(asset.getId());
            detail.setBookStatus(asset.getStatus());
            detail.setActualStatus(executeDTO.getActualStatus());
            detail.setInventoryResult(executeDTO.getInventoryResult());
            detail.setLocation(executeDTO.getLocation());
            detail.setRemark(executeDTO.getRemark());
            detail.setPhotoUrl(executeDTO.getPhotoUrl());
            detail.setInventoryTime(LocalDateTime.now());
            // Current user ID will be set from security context
            inventoryDetailMapper.insert(detail);
        }
        
        log.info("Inventory executed for asset: {}", executeDTO.getAssetCode());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitInventoryResult(Long taskId) {
        InventoryTask task = this.getById(taskId);
        if (task == null) {
            throw new BusinessException("Task not found");
        }
        
        if (task.getStatus() != 2) {
            throw new BusinessException("Task is not in progress");
        }
        
        // TODO: Generate inventory difference report and update asset status
        
        task.setStatus(3); // Completed
        this.updateById(task);
        log.info("Inventory result submitted: {}", task.getTaskCode());
    }

    @Override
    public List<InventoryTask> getMyTasks() {
        // Will be implemented with Spring Security context
        return this.list(new LambdaQueryWrapper<InventoryTask>()
                .orderByDesc(InventoryTask::getCreateTime));
    }

    @Override
    public TaskStatistics getTaskStatistics(Long taskId) {
        List<InventoryDetail> details = getTaskDetails(taskId);
        
        TaskStatistics stats = new TaskStatistics();
        stats.setTotalAssets((long) details.size());
        stats.setConsistentCount(details.stream().filter(d -> d.getInventoryResult() == 1).count());
        stats.setFoundCount(details.stream().filter(d -> d.getInventoryResult() == 2).count());
        stats.setMissingCount(details.stream().filter(d -> d.getInventoryResult() == 3).count());
        stats.setDamagedCount(details.stream().filter(d -> d.getInventoryResult() == 4).count());
        stats.setCompletedCount(details.stream().filter(d -> d.getInventoryTime() != null).count());
        stats.setPendingCount(stats.getTotalAssets() - stats.getCompletedCount());
        
        return stats;
    }
}
