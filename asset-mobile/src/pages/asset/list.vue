<template>
  <view class="asset-list">
    <view class="search-bar">
      <input class="search-input" v-model="keyword" placeholder="搜索资产编号/名称" />
    </view>
    
    <scroll-view 
      scroll-y 
      class="asset-scroll"
      :refresher-enabled="true"
      :refresher-triggered="refreshing"
      @refresherrefresh="onRefresh"
    >
      <view class="asset-card" v-for="item in list" :key="item.id" @click="goDetail(item.id)">
        <view class="asset-header">
          <text class="asset-code">{{ item.assetCode }}</text>
          <text class="asset-status" :class="getStatusClass(item.status)">
            {{ getStatusText(item.status) }}
          </text>
        </view>
        <view class="asset-name">{{ item.assetName }}</view>
        <view class="asset-info">
          <text class="info-item">{{ item.brand }} {{ item.model }}</text>
          <text class="info-item">{{ item.location }}</text>
        </view>
      </view>
      
      <view v-if="list.length === 0" class="empty-tip">
        <text>暂无资产数据</text>
      </view>
    </scroll-view>
  </view>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const keyword = ref('')
const refreshing = ref(false)
const list = ref([])

const getStatusClass = (status) => {
  const classes = { 1: 'inuse', 2: 'idle', 3: 'repair', 4: 'scrapped' }
  return classes[status] || ''
}

const getStatusText = (status) => {
  const texts = { 1: '使用中', 2: '闲置', 3: '维修中', 4: '已报废' }
  return texts[status] || '未知'
}

const goDetail = (id) => {
  uni.navigateTo({ url: `/pages/asset/detail?id=${id}` })
}

const loadData = () => {
  // Mock data
  list.value = [
    { id: 1, assetCode: 'AS-001', assetName: 'Dell显示器', brand: 'Dell', model: 'U2720Q', status: 1, location: 'IT部' },
    { id: 2, assetCode: 'AS-002', assetName: 'ThinkPad笔记本', brand: 'Lenovo', model: 'X1 Carbon', status: 1, location: 'IT部' },
    { id: 3, assetCode: 'AS-003', assetName: 'HP打印机', brand: 'HP', model: 'LaserJet', status: 2, location: '会议室' },
    { id: 4, assetCode: 'AS-004', assetName: 'iPhone 14', brand: 'Apple', model: 'iPhone 14', status: 3, location: 'IT部' }
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
.asset-list {
  height: 100vh;
  display: flex;
  flex-direction: column;
  background: #f5f5f5;
}

.search-bar {
  background: #fff;
  padding: 20rpx;
}

.search-input {
  background: #f5f5f5;
  border-radius: 32rpx;
  height: 64rpx;
  padding: 0 30rpx;
  font-size: 28rpx;
}

.asset-scroll {
  flex: 1;
  padding: 20rpx;
}

.asset-card {
  background: #fff;
  border-radius: 16rpx;
  padding: 30rpx;
  margin-bottom: 20rpx;
}

.asset-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16rpx;
}

.asset-code {
  font-size: 24rpx;
  color: #999;
}

.asset-status {
  font-size: 24rpx;
  padding: 6rpx 16rpx;
  border-radius: 20rpx;
}

.asset-status.inuse { background: #f0f9ff; color: #67c23a; }
.asset-status.idle { background: #fdf6ec; color: #e6a23c; }
.asset-status.repair { background: #fef0f0; color: #f56c6c; }
.asset-status.scrapped { background: #f5f5f5; color: #999; }

.asset-name {
  font-size: 32rpx;
  font-weight: 600;
  color: #333;
  margin-bottom: 12rpx;
}

.asset-info {
  display: flex;
  justify-content: space-between;
}

.info-item {
  font-size: 26rpx;
  color: #999;
}

.empty-tip {
  text-align: center;
  padding: 100rpx;
  color: #999;
}
</style>
