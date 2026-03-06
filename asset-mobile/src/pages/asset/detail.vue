<template>
  <view class="asset-detail">
    <view class="detail-card" v-if="asset">
      <view class="header">
        <text class="asset-code">{{ asset.assetCode }}</text>
        <text class="status" :class="getStatusClass(asset.status)">
          {{ getStatusText(asset.status) }}
        </text>
      </view>
      
      <view class="name">{{ asset.assetName }}</view>
      
      <view class="info-list">
        <view class="info-item">
          <text class="label">品牌型号</text>
          <text class="value">{{ asset.brand }} {{ asset.model }}</text>
        </view>
        <view class="info-item">
          <text class="label">序列号</text>
          <text class="value">{{ asset.serialNumber }}</text>
        </view>
        <view class="info-item">
          <text class="label">存放地点</text>
          <text class="value">{{ asset.location }}</text>
        </view>
        <view class="info-item">
          <text class="label">采购日期</text>
          <text class="value">{{ asset.purchaseDate }}</text>
        </view>
        <view class="info-item">
          <text class="label">采购价格</text>
          <text class="value">¥{{ asset.purchasePrice }}</text>
        </view>
        <view class="info-item">
          <text class="label">供应商</text>
          <text class="value">{{ asset.supplier }}</text>
        </view>
      </view>
    </view>
    
    <view v-else class="loading">
      <text>加载中...</text>
    </view>
  </view>
</template>

<script setup>
import { ref, onLoad } from '@dcloudio/uni-app'

const asset = ref(null)

const getStatusClass = (status) => {
  const classes = { 1: 'inuse', 2: 'idle', 3: 'repair', 4: 'scrapped' }
  return classes[status] || ''
}

const getStatusText = (status) => {
  const texts = { 1: '使用中', 2: '闲置', 3: '维修中', 4: '已报废' }
  return texts[status] || '未知'
}

onLoad((options) => {
  // Mock data
  asset.value = {
    id: options.id,
    assetCode: 'AS-001',
    assetName: 'Dell显示器',
    brand: 'Dell',
    model: 'U2720Q',
    serialNumber: 'SN123456789',
    status: 1,
    location: 'IT部',
    purchaseDate: '2023-01-15',
    purchasePrice: '2999.00',
    supplier: 'Dell官方渠道'
  }
})
</script>

<style scoped>
.asset-detail {
  min-height: 100vh;
  background: #f5f5f5;
  padding: 20rpx;
}

.detail-card {
  background: #fff;
  border-radius: 16rpx;
  padding: 30rpx;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20rpx;
}

.asset-code {
  font-size: 26rpx;
  color: #999;
}

.status {
  font-size: 24rpx;
  padding: 8rpx 20rpx;
  border-radius: 20rpx;
}

.status.inuse { background: #f0f9ff; color: #67c23a; }
.status.idle { background: #fdf6ec; color: #e6a23c; }
.status.repair { background: #fef0f0; color: #f56c6c; }

.name {
  font-size: 36rpx;
  font-weight: 600;
  color: #333;
  margin-bottom: 30rpx;
}

.info-list {
  border-top: 1rpx solid #f0f0f0;
  padding-top: 20rpx;
}

.info-item {
  display: flex;
  justify-content: space-between;
  padding: 16rpx 0;
}

.label {
  font-size: 28rpx;
  color: #999;
}

.value {
  font-size: 28rpx;
  color: #333;
}

.loading {
  text-align: center;
  padding: 100rpx;
  color: #999;
}
</style>
