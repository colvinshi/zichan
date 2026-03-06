<template>
  <div class="asset-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>资产管理</span>
          <el-button type="primary" @click="handleCreate">
            <el-icon><Plus /></el-icon>
            新增资产
          </el-button>
        </div>
      </template>
      
      <!-- Search form -->
      <el-form :inline="true" :model="queryForm" class="search-form">
        <el-form-item label="关键词">
          <el-input v-model="queryForm.keyword" placeholder="资产编码/名称/序列号" clearable />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="queryForm.status" placeholder="请选择" clearable>
            <el-option label="使用中" :value="1" />
            <el-option label="闲置" :value="2" />
            <el-option label="维修中" :value="3" />
            <el-option label="已报废" :value="4" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
      
      <!-- Table -->
      <el-table :data="tableData" v-loading="loading" style="width: 100%">
        <el-table-column prop="assetCode" label="资产编码" width="150" />
        <el-table-column prop="assetName" label="资产名称" min-width="150" />
        <el-table-column prop="categoryName" label="资产分类" width="120" />
        <el-table-column prop="brand" label="品牌" width="100" />
        <el-table-column prop="model" label="型号" width="120" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">
              {{ getStatusText(row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="location" label="存放地点" width="150" />
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link @click="handleView(row)">查看</el-button>
            <el-button type="primary" link @click="handleEdit(row)">编辑</el-button>
            <el-button type="danger" link @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <!-- Pagination -->
      <el-pagination
        v-model:current-page="queryForm.current"
        v-model:page-size="queryForm.size"
        :total="total"
        :page-sizes="[10, 20, 50, 100]"
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
import { ElMessage, ElMessageBox } from 'element-plus'
import { request } from '@/utils/request'

const router = useRouter()

const loading = ref(false)
const tableData = ref<any[]>([])
const total = ref(0)

const queryForm = reactive({
  keyword: '',
  status: null as number | null,
  current: 1,
  size: 10
})

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

const loadData = async () => {
  loading.value = true
  try {
    const data = await request.get('/v1/assets', { params: queryForm })
    tableData.value = data.records || []
    total.value = data.total || 0
  } catch (error) {
    console.error('Failed to load assets:', error)
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  queryForm.current = 1
  loadData()
}

const handleReset = () => {
  queryForm.keyword = ''
  queryForm.status = null
  queryForm.current = 1
  loadData()
}

const handleCreate = () => {
  router.push('/assets/create')
}

const handleView = (row: any) => {
  router.push(`/assets/${row.id}`)
}

const handleEdit = (row: any) => {
  router.push(`/assets/${row.id}?edit=true`)
}

const handleDelete = async (row: any) => {
  try {
    await ElMessageBox.confirm('确认删除该资产吗？', '提示', {
      type: 'warning'
    })
    await request.delete(`/v1/assets/${row.id}`)
    ElMessage.success('删除成功')
    loadData()
  } catch (error: any) {
    if (error !== 'cancel') {
      ElMessage.error(error.message || '删除失败')
    }
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.asset-list {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search-form {
  margin-bottom: 20px;
}
</style>
