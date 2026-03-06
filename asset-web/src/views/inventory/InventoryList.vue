<template>
  <div class="inventory-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>盘点任务</span>
          <el-button type="primary" @click="handleCreate">
            <el-icon><Plus /></el-icon>
            创建任务
          </el-button>
        </div>
      </template>
      
      <el-table :data="tableData" v-loading="loading" style="width: 100%">
        <el-table-column prop="taskCode" label="任务编码" width="150" />
        <el-table-column prop="taskName" label="任务名称" min-width="150" />
        <el-table-column prop="taskType" label="任务类型" width="100">
          <template #default="{ row }">
            <el-tag>{{ row.taskType === 1 ? '全面盘点' : '部分盘点' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="startTime" label="开始时间" width="180" />
        <el-table-column prop="endTime" label="结束时间" width="180" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">
              {{ getStatusText(row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="250" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link @click="handleView(row)">查看</el-button>
            <el-button v-if="row.status === 1" type="success" link @click="handleStart(row)">开始</el-button>
            <el-button v-if="row.status === 2" type="warning" link @click="handleExecute(row)">执行</el-button>
            <el-button v-if="row.status === 2" type="success" link @click="handleComplete(row)">完成</el-button>
            <el-button v-if="row.status === 1" type="danger" link @click="handleCancel(row)">取消</el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <el-pagination
        v-model:current-page="queryForm.current"
        v-model:page-size="queryForm.size"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="loadData"
        @current-change="loadData"
        style="margin-top: 20px; justify-content: flex-end;"
      />
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { request } from '@/utils/request'

const router = useRouter()

const loading = ref(false)
const tableData = ref<any[]>([])
const total = ref(0)

const queryForm = reactive({ current: 1, size: 10 })

const getStatusType = (status: number) => {
  const types: Record<number, string> = { 1: 'info', 2: 'warning', 3: 'success', 4: 'danger' }
  return types[status] || 'info'
}

const getStatusText = (status: number) => {
  const texts: Record<number, string> = { 1: '待开始', 2: '进行中', 3: '已完成', 4: '已取消' }
  return texts[status] || '未知'
}

const loadData = async () => {
  loading.value = true
  try {
    const data = await request.get('/v1/inventory/tasks', { params: queryForm })
    tableData.value = data.records || []
    total.value = data.total || 0
  } catch (error) {
    console.error('Failed to load tasks:', error)
  } finally {
    loading.value = false
  }
}

const handleCreate = () => {
  router.push('/inventory/create')
}

const handleView = (row: any) => {
  router.push(`/inventory/${row.id}`)
}

const handleStart = async (row: any) => {
  try {
    await request.post(`/v1/inventory/tasks/${row.id}/start`)
    ElMessage.success('任务已开始')
    loadData()
  } catch (error) {
    ElMessage.error('操作失败')
  }
}

const handleExecute = (row: any) => {
  router.push(`/inventory/${row.id}?execute=true`)
}

const handleComplete = async (row: any) => {
  try {
    await request.post(`/v1/inventory/tasks/${row.id}/complete`)
    ElMessage.success('任务已完成')
    loadData()
  } catch (error) {
    ElMessage.error('操作失败')
  }
}

const handleCancel = async (row: any) => {
  try {
    await request.post(`/v1/inventory/tasks/${row.id}/cancel`)
    ElMessage.success('任务已取消')
    loadData()
  } catch (error) {
    ElMessage.error('操作失败')
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.inventory-list { padding: 20px; }
.card-header { display: flex; justify-content: space-between; align-items: center; }
</style>
