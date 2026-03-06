<template>
  <div class="asset-detail">
    <el-card v-loading="loading">
      <template #header>
        <div class="card-header">
          <span>资产详情</span>
          <div>
            <el-button @click="router.back()">返回</el-button>
            <el-button type="primary" @click="handleEdit">编辑</el-button>
          </div>
        </div>
      </template>
      
      <el-descriptions :column="2" border v-if="asset">
        <el-descriptions-item label="资产编码">{{ asset.assetCode }}</el-descriptions-item>
        <el-descriptions-item label="资产名称">{{ asset.assetName }}</el-descriptions-item>
        <el-descriptions-item label="资产分类">{{ asset.categoryId }}</el-descriptions-item>
        <el-descriptions-item label="品牌">{{ asset.brand }}</el-descriptions-item>
        <el-descriptions-item label="型号">{{ asset.model }}</el-descriptions-item>
        <el-descriptions-item label="序列号">{{ asset.serialNumber }}</el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag :type="getStatusType(asset.status)">{{ getStatusText(asset.status) }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="存放地点">{{ asset.location }}</el-descriptions-item>
        <el-descriptions-item label="采购日期">{{ asset.purchaseDate }}</el-descriptions-item>
        <el-descriptions-item label="采购价格">{{ asset.purchasePrice }}</el-descriptions-item>
        <el-descriptions-item label="供应商">{{ asset.supplier }}</el-descriptions-item>
        <el-descriptions-item label="保修截止">{{ asset.warrantyEndDate }}</el-descriptions-item>
        <el-descriptions-item label="备注" :span="2">{{ asset.remark }}</el-descriptions-item>
      </el-descriptions>
      
      <el-empty v-else description="资产不存在" />
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
const asset = ref<any>(null)

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
    const id = route.params.id
    asset.value = await request.get(`/v1/assets/${id}`)
  } catch (error) {
    console.error('Failed to load asset:', error)
  } finally {
    loading.value = false
  }
}

const handleEdit = () => {
  router.push(`/assets/${route.params.id}?edit=true`)
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.asset-detail {
  padding: 20px;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
