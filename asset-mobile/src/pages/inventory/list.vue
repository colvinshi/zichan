<template>
  <view class="inventory-list">
    <scroll-view 
      scroll-y 
      class="list-scroll"
      :refresher-enabled="true"
      :refresher-triggered="refreshing"
      @refresherrefresh="onRefresh"
    >
      <view class="task-card" v-for="item in list" :key="item.id" @click="goExecute(item.id)">
        <view class="task-header">
          <text class="task-name">{{ item.taskName }}</text>
          <text class="task-status" :class="getStatusClass(item.status)">
            {{ getStatusText(item.status) }}
          </text>
        </view>
        <view class="task-info">
          <view class="info-row">
            <text class="label">任务编码:</text>
            <text class="value">{{ item.taskCode }}</text>
          </view>
          <view class="info-row">
            <text class="label">截止时间:</text>
            <text class="value">{{ item.endTime }}</text>
          </view>
          <view class="info-row">
            <text class="label">任务类型:</text>
            <text class="value">{{ item.taskType === 1 ? '全面盘点' : '部分盘点' }}</text>
          </view>
        </view>
        <view class="task-action" v-if="item.status === 2">
          <text class="action-btn">立即盘点</text>
        </view>
      </view>
      
      <view v-if="list.length === 0" class="empty-tip">
        <text>暂无盘点任务</text>
      </view>
    </scroll-view>
  </view>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const refreshing = ref(false)
const list = ref([])

const getStatusClass = (status) => {
  const classes = { 1: 'pending', 2: 'progress', 3: 'completed', 4: 'cancelled' }
  return classes[status] || ''
}

const getStatusText = (status) => {
  const texts = { 1: '待开始', 2: '进行中', 3: '已完成', 4: '已取消' }
  return texts[status] || '未知'
}

const goExecute = (id) => {
  uni.navigateTo({ url: `/pages/inventory/execute?id=${id}` })
}

const loadData = () => {
  // Mock data
  list.value = [
    { id: 1, taskCode: 'INV-2024001', taskName: '2024年Q1资产盘点', taskType: 1, endTime: '2024-03-31', status: 2 },
    { id: 2, taskCode: 'INV-2024002', taskName: 'IT部门资产清查', taskType: 2, endTime: '2024-04-15', status: 1 },
    { id: 3, taskCode: 'INV-2024003', taskName: '办公设备年度盘点', taskType: 1, endTime: '2024-02-28', status: 3 }
  ]
}

const onRefresh = () => {
  refreshing.value = true
  setTimeout(() => {
    loadData()
    refreshing.value = false
    uni.showToast({ title: '刷新成功', icon: 'success' })
  }, 1000)
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.inventory-list {
  height: 100vh;
  background: #f5f5f5;
}

.list-scroll {
  height: 100%;
  padding: 20rpx;
}

.task-card {
  background: #fff;
  border-radius: 16rpx;
  padding: 30rpx;
  margin-bottom: 20rpx;
}

.task-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20rpx;
}

.task-name {
  font-size: 32rpx;
  font-weight: 600;
  color: #333;
}

.task-status {
  font-size: 24rpx;
  padding: 8rpx 20rpx;
  border-radius: 20rpx;
}

.task-status.pending { background: #f5f5f5; color: #999; }
.task-status.progress { background: #ecf5ff; color: #409eff; }
.task-status.completed { background: #f0f9ff; color: #67c23a; }
.task-status.cancelled { background: #fef0f0; color: #f56c6c; }

.task-info {
  margin-bottom: 20rpx;
}

.info-row {
  display: flex;
  margin-bottom: 12rpx;
}

.label {
  font-size: 26rpx;
  color: #999;
  width: 160rpx;
}

.value {
  font-size: 26rpx;
  color: #333;
}

.task-action {
  text-align: right;
}

.action-btn {
  display: inline-block;
  padding: 12rpx 30rpx;
  background: #67c23a;
  color: #fff;
  border-radius: 30rpx;
  font-size: 26rpx;
}

.empty-tip {
  text-align: center;
  padding: 100rpx;
  color: #999;
}
</style>
