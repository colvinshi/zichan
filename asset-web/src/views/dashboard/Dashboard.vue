<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-icon" style="background: #409eff;">
            <el-icon :size="30"><Box /></el-icon>
          </div>
          <div class="stat-content">
            <div class="stat-value">{{ statistics.totalAssets }}</div>
            <div class="stat-label">资产总数</div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-icon" style="background: #67c23a;">
            <el-icon :size="30"><CircleCheck /></el-icon>
          </div>
          <div class="stat-content">
            <div class="stat-value">{{ statistics.inUseCount }}</div>
            <div class="stat-label">使用中</div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-icon" style="background: #e6a23c;">
            <el-icon :size="30"><Clock /></el-icon>
          </div>
          <div class="stat-content">
            <div class="stat-value">{{ statistics.idleCount }}</div>
            <div class="stat-label">闲置</div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-icon" style="background: #f56c6c;">
            <el-icon :size="30"><Tools /></el-icon>
          </div>
          <div class="stat-content">
            <div class="stat-value">{{ statistics.maintenanceCount }}</div>
            <div class="stat-label">维修中</div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    
    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="16">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>最近资产</span>
              <el-button type="primary" link @click="router.push('/assets')">查看全部</el-button>
            </div>
          </template>
          <el-table :data="recentAssets" style="width: 100%">
            <el-table-column prop="assetCode" label="资产编码" width="150" />
            <el-table-column prop="assetName" label="资产名称" />
            <el-table-column prop="status" label="状态" width="100">
              <template #default="{ row }">
                <el-tag :type="getStatusType(row.status)">
                  {{ getStatusText(row.status) }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间" width="180" />
          </el-table>
        </el-card>
      </el-col>
      
      <el-col :span="8">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>我的盘点任务</span>
            </div>
          </template>
          <div class="task-list">
            <div v-for="task in myTasks" :key="task.id" class="task-item">
              <div class="task-name">{{ task.taskName }}</div>
              <div class="task-status">
                <el-tag :type="getTaskStatusType(task.status)" size="small">
                  {{ getTaskStatusText(task.status) }}
                </el-tag>
              </div>
            </div>
            <el-empty v-if="myTasks.length === 0" description="暂无任务" />
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { request } from '@/utils/request'

const router = useRouter()

const statistics = reactive({
  totalAssets: 0,
  inUseCount: 0,
  idleCount: 0,
  maintenanceCount: 0
})

const recentAssets = ref<any[]>([])
const myTasks = ref<any[]>([])

const getStatusType = (status: number) => {
  const types: Record<number, string> = {
    1: 'success',
    2: 'warning',
    3: 'danger',
    4: 'info'
  }
  return types[status] || 'info'
}

const getStatusText = (status: number) => {
  const texts: Record<number, string> = {
    1: '使用中',
    2: '闲置',
    3: '维修中',
    4: '已报废'
  }
  return texts[status] || '未知'
}

const getTaskStatusType = (status: number) => {
  const types: Record<number, string> = {
    1: 'info',
    2: 'warning',
    3: 'success',
    4: 'danger'
  }
  return types[status] || 'info'
}

const getTaskStatusText = (status: number) => {
  const texts: Record<number, string> = {
    1: '待开始',
    2: '进行中',
    3: '已完成',
    4: '已取消'
  }
  return texts[status] || '未知'
}

const loadStatistics = async () => {
  try {
    const data = await request.get('/v1/assets/statistics')
    Object.assign(statistics, data)
  } catch (error) {
    console.error('Failed to load statistics:', error)
  }
}

const loadRecentAssets = async () => {
  try {
    const data = await request.get('/v1/assets', { params: { current: 1, size: 5 } })
    recentAssets.value = data.records || []
  } catch (error) {
    console.error('Failed to load recent assets:', error)
  }
}

const loadMyTasks = async () => {
  try {
    const data = await request.get('/v1/inventory/my-tasks')
    myTasks.value = (data || []).slice(0, 5)
  } catch (error) {
    console.error('Failed to load tasks:', error)
  }
}

onMounted(() => {
  loadStatistics()
  loadRecentAssets()
  loadMyTasks()
})
</script>

<style scoped>
.dashboard {
  padding: 20px;
}

.stat-card {
  display: flex;
  align-items: center;
  padding: 20px;
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  margin-right: 20px;
}

.stat-content {
  flex: 1;
}

.stat-value {
  font-size: 28px;
  font-weight: 600;
  color: #333;
}

.stat-label {
  font-size: 14px;
  color: #999;
  margin-top: 5px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.task-list {
  max-height: 300px;
  overflow-y: auto;
}

.task-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 0;
  border-bottom: 1px solid #f0f0f0;
}

.task-item:last-child {
  border-bottom: none;
}

.task-name {
  font-size: 14px;
  color: #333;
}
</style>
