<template>
  <view class="index-container">
    <!-- User Info -->
    <view class="user-card">
      <view class="user-info">
        <image class="avatar" src="/static/avatar.png" mode="aspectFill" />
        <view class="info">
          <text class="name">{{ userInfo.realName || userInfo.username }}</text>
          <text class="role">{{ getRoleText(userInfo.roleCode) }}</text>
        </view>
      </view>
    </view>
    
    <!-- Quick Actions -->
    <view class="action-grid">
      <view class="action-item" @click="goToPage('/pages/inventory/execute')">
        <view class="action-icon scan">
          <text class="iconfont">&#xe6a0;</text>
        </view>
        <text class="action-text">扫码盘点</text>
      </view>
      
      <view class="action-item" @click="goToPage('/pages/asset/list')">
        <view class="action-icon asset">
          <text class="iconfont">&#xe6a1;</text>
        </view>
        <text class="action-text">我的资产</text>
      </view>
      
      <view class="action-item" @click="goToPage('/pages/inventory/list')">
        <view class="action-icon task">
          <text class="iconfont">&#xe6a2;</text>
        </view>
        <text class="action-text">盘点任务</text>
      </view>
      
      <view class="action-item" @click="goToPage('/pages/user/profile')">
        <view class="action-icon profile">
          <text class="iconfont">&#xe6a3;</text>
        </view>
        <text class="action-text">个人中心</text>
      </view>
    </view>
    
    <!-- Statistics -->
    <view class="section-title">资产统计</view>
    <view class="stat-card">
      <view class="stat-item">
        <text class="stat-value">{{ statistics.totalCount }}</text>
        <text class="stat-label">资产总数</text>
      </view>
      <view class="stat-item">
        <text class="stat-value stat-inuse">{{ statistics.inUseCount }}</text>
        <text class="stat-label">使用中</text>
      </view>
      <view class="stat-item">
        <text class="stat-value stat-idle">{{ statistics.idleCount }}</text>
        <text class="stat-label">闲置</text>
      </view>
      <view class="stat-item">
        <text class="stat-value stat-repair">{{ statistics.maintenanceCount }}</text>
        <text class="stat-label">维修中</text>
      </view>
    </view>
    
    <!-- Recent Tasks -->
    <view class="section-title">待办任务</view>
    <view class="task-list">
      <view 
        class="task-item" 
        v-for="item in myTasks" 
        :key="item.id"
        @click="goToPage(`/pages/inventory/execute?id=${item.id}`)"
      >
        <view class="task-info">
          <text class="task-name">{{ item.taskName }}</text>
          <text class="task-time">{{ item.endTime }} 截止</text>
        </view>
        <view class="task-status">
          <text class="status-tag" :class="getStatusClass(item.status)">
            {{ getStatusText(item.status) }}
          </text>
        </view>
      </view>
      
      <view v-if="myTasks.length === 0" class="empty-tip">
        <text>暂无待办任务</text>
      </view>
    </view>
  </view>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useUserStore } from '@/store/user'

const userStore = useUserStore()
const userInfo = userStore.userInfo || { username: 'Admin', realName: '管理员', roleCode: 'admin' }

const statistics = reactive({
  totalCount: 0,
  inUseCount: 0,
  idleCount: 0,
  maintenanceCount: 0
})

const myTasks = ref([])

const getRoleText = (roleCode) => {
  const texts = { admin: '管理员', asset_manager: '资产管理员', employee: '普通员工' }
  return texts[roleCode] || '员工'
}

const getStatusClass = (status) => {
  const classes = { 1: 'pending', 2: 'progress', 3: 'completed', 4: 'cancelled' }
  return classes[status] || ''
}

const getStatusText = (status) => {
  const texts = { 1: '待开始', 2: '进行中', 3: '已完成', 4: '已取消' }
  return texts[status] || '未知'
}

const goToPage = (url) => {
  uni.navigateTo({ url })
}

const loadStatistics = () => {
  // Mock data
  statistics.totalCount = 12
  statistics.inUseCount = 8
  statistics.idleCount = 3
  statistics.maintenanceCount = 1
}

const loadTasks = () => {
  // Mock data
  myTasks.value = [
    { id: 1, taskName: '2024年Q1资产盘点', endTime: '2024-03-31', status: 2 },
    { id: 2, taskName: 'IT部门资产清查', endTime: '2024-04-15', status: 1 }
  ]
}

onMounted(() => {
  loadStatistics()
  loadTasks()
})
</script>

<style scoped>
.index-container {
  min-height: 100vh;
  background: #f5f5f5;
  padding: 20rpx;
}

.user-card {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 24rpx;
  padding: 40rpx;
  margin-bottom: 30rpx;
}

.user-info {
  display: flex;
  align-items: center;
}

.avatar {
  width: 100rpx;
  height: 100rpx;
  border-radius: 50rpx;
  margin-right: 24rpx;
}

.info {
  display: flex;
  flex-direction: column;
}

.name {
  color: #fff;
  font-size: 36rpx;
  font-weight: 600;
  margin-bottom: 8rpx;
}

.role {
  color: rgba(255, 255, 255, 0.8);
  font-size: 24rpx;
}

.action-grid {
  display: flex;
  justify-content: space-between;
  margin-bottom: 30rpx;
}

.action-item {
  width: 23%;
  text-align: center;
}

.action-icon {
  width: 100rpx;
  height: 100rpx;
  border-radius: 20rpx;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 16rpx;
}

.action-icon.scan { background: #409eff; }
.action-icon.asset { background: #67c23a; }
.action-icon.task { background: #e6a23c; }
.action-icon.profile { background: #f56c6c; }

.action-text {
  font-size: 24rpx;
  color: #666;
}

.section-title {
  font-size: 32rpx;
  font-weight: 600;
  color: #333;
  margin-bottom: 20rpx;
  padding-left: 10rpx;
  border-left: 6rpx solid #409eff;
}

.stat-card {
  background: #fff;
  border-radius: 16rpx;
  padding: 30rpx;
  display: flex;
  justify-content: space-between;
  margin-bottom: 30rpx;
}

.stat-item {
  text-align: center;
}

.stat-value {
  display: block;
  font-size: 40rpx;
  font-weight: 600;
  color: #333;
}

.stat-inuse { color: #67c23a; }
.stat-idle { color: #e6a23c; }
.stat-repair { color: #f56c6c; }

.stat-label {
  font-size: 24rpx;
  color: #999;
  margin-top: 8rpx;
}

.task-list {
  background: #fff;
  border-radius: 16rpx;
  overflow: hidden;
}

.task-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 30rpx;
  border-bottom: 1rpx solid #f0f0f0;
}

.task-item:last-child {
  border-bottom: none;
}

.task-info {
  display: flex;
  flex-direction: column;
}

.task-name {
  font-size: 28rpx;
  color: #333;
  margin-bottom: 8rpx;
}

.task-time {
  font-size: 24rpx;
  color: #999;
}

.status-tag {
  font-size: 24rpx;
  padding: 8rpx 16rpx;
  border-radius: 20rpx;
}

.status-tag.pending { background: #f0f0f0; color: #999; }
.status-tag.progress { background: #ecf5ff; color: #409eff; }
.status-tag.completed { background: #f0f9ff; color: #67c23a; }

.empty-tip {
  text-align: center;
  padding: 60rpx;
  color: #999;
}
</style>
