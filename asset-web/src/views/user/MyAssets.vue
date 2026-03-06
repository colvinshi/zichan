<template>
  <div class="my-assets">
    <el-card>
      <template #header>
        <span>我的资产</span>
      </template>
      
      <el-row :gutter="20" style="margin-bottom: 20px;">
        <el-col :span="6">
          <div class="stat-item">
            <div class="stat-value">{{ statistics.totalCount }}</div>
            <div class="stat-label">资产总数</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="stat-item">
            <div class="stat-value" style="color: #67c23a;">{{ statistics.inUseCount }}</div>
            <div class="stat-label">使用中</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="stat-item">
            <div class="stat-value" style="color: #e6a23c;">{{ statistics.idleCount }}</div>
            <div class="stat-label">闲置</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="stat-item">
            <div class="stat-value" style="color: #f56c6c;">{{ statistics.maintenanceCount }}</div>
            <div class="stat-label">维修中</div>
          </div>
        </el-col>
      </el-row>
      
      <el-table :data="tableData" v-loading="loading" style="width: 100%">
        <el-table-column prop="assetCode" label="资产编码" width="150" />
        <el-table-column prop="assetName" label="资产名称" />
        <el-table-column prop="brand" label="品牌" width="100" />
        <el-table-column prop="model" label="型号" width="120" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">{{ getStatusText(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="100">
          <template #default="{ row }">
            <el-button type="primary" link @click="handleView(row)">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { request } from '@/utils/request'

const router = useRouter()
const loading = ref(false)
const tableData = ref<any[]>([])
const statistics = reactive({ totalCount: 0, inUseCount: 0, idleCount: 0, maintenanceCount: 0 })

const getStatusType = (status: number) => {
  const types: Record<number, string> = { 1: 'success', 2: 'warning', 3: 'danger', 4: 'info' }
  return types[status] || 'info'
}

const getStatusText = (status: number) => {
  const texts: Record<number, string> = { 1: '使用中', 2: '闲置', 3: '维修中', 4: '已报废' }
  return texts[status] || '未知'
}

const loadData = async () => {
  loading.value = true
  try {
    const [stats, data] = await Promise.all([
      request.get('/v1/assets/statistics', { params: { userId: 1 } }),
      request.get('/v1/assets/user/1')
    ])
    Object.assign(statistics, stats)
    tableData.value = data || []
  } catch (error) {
    console.error('Failed to load:', error)
  } finally {
    loading.value = false
  }
}

const handleView = (row: any) => {
  router.push(`/assets/${row.id}`)
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.my-assets { padding: 20px; }
.stat-item { text-align: center; padding: 20px; background: #f5f7fa; border-radius: 8px; }
.stat-value { font-size: 28px; font-weight: 600; color: #333; }
.stat-label { font-size: 14px; color: #999; margin-top: 5px; }
</style>
