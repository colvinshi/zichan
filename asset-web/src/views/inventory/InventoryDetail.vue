<template>
  <div class="inventory-detail">
    <el-card v-loading="loading">
      <template #header>
        <div class="card-header">
          <span>盘点详情</span>
          <div>
            <el-button @click="router.back()">返回</el-button>
          </div>
        </div>
      </template>
      
      <div v-if="task">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="任务编码">{{ task.taskCode }}</el-descriptions-item>
          <el-descriptions-item label="任务名称">{{ task.taskName }}</el-descriptions-item>
          <el-descriptions-item label="任务类型">{{ task.taskType === 1 ? '全面盘点' : '部分盘点' }}</el-descriptions-item>
          <el-descriptions-item label="状态">
            <el-tag>{{ getStatusText(task.status) }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="开始时间">{{ task.startTime }}</el-descriptions-item>
          <el-descriptions-item label="结束时间">{{ task.endTime }}</el-descriptions-item>
        </el-descriptions>
        
        <el-divider />
        
        <h3>盘点明细</h3>
        <el-table :data="details" style="width: 100%; margin-top: 20px;">
          <el-table-column prop="assetCode" label="资产编码" width="150" />
          <el-table-column prop="assetName" label="资产名称" />
          <el-table-column prop="bookStatus" label="账面状态" width="100" />
          <el-table-column prop="actualStatus" label="实际状态" width="100" />
          <el-table-column prop="inventoryResult" label="盘点结果" width="100">
            <template #default="{ row }">
              <el-tag :type="getResultType(row.inventoryResult)">
                {{ getResultText(row.inventoryResult) }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="inventoryTime" label="盘点时间" width="180" />
        </el-table>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { request } from '@/utils/request'

const route = useRoute()
const router = useRouter()

const loading = ref(false)
const task = ref<any>(null)
const details = ref<any[]>([])

const getStatusText = (status: number) => {
  const texts: Record<number, string> = { 1: '待开始', 2: '进行中', 3: '已完成', 4: '已取消' }
  return texts[status] || '未知'
}

const getResultType = (result: number) => {
  const types: Record<number, string> = { 1: 'success', 2: 'info', 3: 'danger', 4: 'warning' }
  return types[result] || 'info'
}

const getResultText = (result: number) => {
  const texts: Record<number, string> = { 1: '一致', 2: '盘盈', 3: '盘亏', 4: '损坏' }
  return texts[result] || '未知'
}

const loadData = async () => {
  loading.value = true
  try {
    const id = route.params.id
    task.value = await request.get(`/v1/inventory/tasks/${id}`)
    details.value = await request.get(`/v1/inventory/tasks/${id}/details`)
  } catch (error) {
    console.error('Failed to load task:', error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.inventory-detail { padding: 20px; }
.card-header { display: flex; justify-content: space-between; align-items: center; }
</style>
