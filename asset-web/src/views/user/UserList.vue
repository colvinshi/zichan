<template>
  <div class="user-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>用户管理</span>
          <el-button type="primary" @click="handleCreate">
            <el-icon><Plus /></el-icon>
            新增用户
          </el-button>
        </div>
      </template>
      
      <el-table :data="tableData" v-loading="loading" style="width: 100%">
        <el-table-column prop="username" label="用户名" width="150" />
        <el-table-column prop="realName" label="真实姓名" width="150" />
        <el-table-column prop="deptId" label="部门" width="150" />
        <el-table-column prop="roleCode" label="角色" width="120">
          <template #default="{ row }">
            <el-tag>{{ getRoleText(row.roleCode) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="phone" label="手机号" width="150" />
        <el-table-column prop="email" label="邮箱" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">
              {{ row.status === 1 ? '正常' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button type="primary" link @click="handleEdit(row)">编辑</el-button>
            <el-button :type="row.status === 1 ? 'danger' : 'success'" link @click="handleToggleStatus(row)">
              {{ row.status === 1 ? '禁用' : '启用' }}
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { request } from '@/utils/request'

const loading = ref(false)
const tableData = ref<any[]>([])

const queryForm = reactive({ current: 1, size: 10 })

const getRoleText = (roleCode: string) => {
  const texts: Record<string, string> = { admin: '管理员', asset_manager: '资产管理员', employee: '普通员工' }
  return texts[roleCode] || '未知'
}

const loadData = async () => {
  loading.value = true
  try {
    const data = await request.get('/v1/users', { params: queryForm })
    tableData.value = data.records || []
  } catch (error) {
    console.error('Failed to load users:', error)
  } finally {
    loading.value = false
  }
}

const handleCreate = () => {
  ElMessage.info('功能开发中')
}

const handleEdit = (row: any) => {
  ElMessage.info('功能开发中')
}

const handleToggleStatus = async (row: any) => {
  try {
    const api = row.status === 1 ? `/v1/users/${row.id}/disable` : `/v1/users/${row.id}/enable`
    await request.post(api)
    ElMessage.success('操作成功')
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
.user-list { padding: 20px; }
.card-header { display: flex; justify-content: space-between; align-items: center; }
</style>
