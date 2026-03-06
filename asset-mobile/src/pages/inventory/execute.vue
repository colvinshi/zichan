<template>
  <view class="execute-container">
    <!-- Scan Area -->
    <view class="scan-section">
      <view class="scan-icon" @click="handleScan">
        <text class="iconfont">&#xe6a0;</text>
      </view>
      <text class="scan-text">点击扫描资产二维码</text>
      <text class="scan-hint">或输入资产编号</text>
    </view>
    
    <!-- Manual Input -->
    <view class="input-section">
      <input 
        class="code-input" 
        v-model="assetCode" 
        placeholder="请输入资产编号" 
        @confirm="handleSubmit"
      />
      <button class="submit-btn" @click="handleSubmit">确 定</button>
    </view>
    
    <!-- Current Asset Info -->
    <view v-if="currentAsset" class="asset-card">
      <view class="card-header">
        <text class="title">资产信息</text>
        <text class="status" :class="getStatusClass(currentAsset.status)">
          {{ getStatusText(currentAsset.status) }}
        </text>
      </view>
      
      <view class="info-grid">
        <view class="info-item">
          <text class="label">资产编号</text>
          <text class="value">{{ currentAsset.assetCode }}</text>
        </view>
        <view class="info-item">
          <text class="label">资产名称</text>
          <text class="value">{{ currentAsset.assetName }}</text>
        </view>
        <view class="info-item">
          <text class="label">品牌型号</text>
          <text class="value">{{ currentAsset.brand }} {{ currentAsset.model }}</text>
        </view>
        <view class="info-item">
          <text class="label">存放地点</text>
          <text class="value">{{ currentAsset.location }}</text>
        </view>
      </view>
      
      <!-- Inventory Result -->
      <view class="result-section">
        <text class="section-title">盘点结果</text>
        <radio-group @change="onResultChange">
          <label class="result-item" v-for="item in resultOptions" :key="item.value">
            <radio :value="item.value" :checked="inventoryResult === item.value" />
            <text class="result-text">{{ item.label }}</text>
          </label>
        </radio-group>
      </view>
      
      <!-- Submit Button -->
      <button class="submit-btn-large" @click="handleInventorySubmit">
        提交盘点结果
      </button>
    </view>
    
    <!-- History -->
    <view class="history-section">
      <text class="section-title">盘点记录</text>
      <view class="history-list">
        <view class="history-item" v-for="item in inventoryHistory" :key="item.id">
          <view class="history-info">
            <text class="history-code">{{ item.assetCode }}</text>
            <text class="history-name">{{ item.assetName }}</text>
          </view>
          <text class="history-result" :class="getResultClass(item.result)">
            {{ getResultText(item.result) }}
          </text>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup>
import { ref } from 'vue'

const assetCode = ref('')
const currentAsset = ref(null)
const inventoryResult = ref(1)
const inventoryHistory = ref([])

const resultOptions = [
  { value: 1, label: '一致' },
  { value: 2, label: '盘盈' },
  { value: 3, label: '盘亏' },
  { value: 4, label: '损坏' }
]

const getStatusClass = (status) => {
  const classes = { 1: 'inuse', 2: 'idle', 3: 'repair' }
  return classes[status] || ''
}

const getStatusText = (status) => {
  const texts = { 1: '使用中', 2: '闲置', 3: '维修中' }
  return texts[status] || '未知'
}

const getResultClass = (result) => {
  const classes = { 1: 'consistent', 2: 'found', 3: 'missing', 4: 'damaged' }
  return classes[result] || ''
}

const getResultText = (result) => {
  const texts = { 1: '一致', 2: '盘盈', 3: '盘亏', 4: '损坏' }
  return texts[result] || '未知'
}

const handleScan = () => {
  // #ifdef MP-WEIXIN
  uni.scanCode({
    success: (res) => {
      assetCode.value = res.result
      queryAsset(res.result)
    }
  })
  // #endif
  // #ifndef MP-WEIXIN
  uni.showToast({ title: '请在微信小程序中使用', icon: 'none' })
  // #endif
}

const queryAsset = (code) => {
  // Mock asset data
  currentAsset.value = {
    id: 1,
    assetCode: code || 'AS-001',
    assetName: 'Dell显示器',
    brand: 'Dell',
    model: 'U2720Q',
    status: 1,
    location: 'IT部'
  }
}

const handleSubmit = () => {
  if (!assetCode.value) {
    uni.showToast({ title: '请输入资产编号', icon: 'none' })
    return
  }
  queryAsset(assetCode.value)
}

const onResultChange = (e) => {
  inventoryResult.value = parseInt(e.detail.value)
}

const handleInventorySubmit = () => {
  if (!currentAsset.value) {
    uni.showToast({ title: '请先扫描资产', icon: 'none' })
    return
  }
  
  // Add to history
  inventoryHistory.value.unshift({
    id: Date.now(),
    assetCode: currentAsset.value.assetCode,
    assetName: currentAsset.value.assetName,
    result: inventoryResult.value
  })
  
  uni.showToast({ title: '提交成功', icon: 'success' })
  
  // Reset
  assetCode.value = ''
  currentAsset.value = null
  inventoryResult.value = 1
}
</script>

<style scoped>
.execute-container {
  min-height: 100vh;
  background: #f5f5f5;
  padding: 30rpx;
}

.scan-section {
  background: #fff;
  border-radius: 16rpx;
  padding: 60rpx;
  text-align: center;
  margin-bottom: 30rpx;
}

.scan-icon {
  width: 160rpx;
  height: 160rpx;
  background: #67c23a;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 30rpx;
}

.scan-icon .iconfont {
  font-size: 80rpx;
  color: #fff;
}

.scan-text {
  display: block;
  font-size: 32rpx;
  color: #333;
  margin-bottom: 10rpx;
}

.scan-hint {
  font-size: 24rpx;
  color: #999;
}

.input-section {
  display: flex;
  gap: 20rpx;
  margin-bottom: 30rpx;
}

.code-input {
  flex: 1;
  background: #fff;
  border-radius: 44rpx;
  height: 88rpx;
  padding: 0 30rpx;
  font-size: 28rpx;
}

.submit-btn {
  width: 160rpx;
  height: 88rpx;
  background: #67c23a;
  color: #fff;
  border-radius: 44rpx;
  font-size: 28rpx;
  line-height: 88rpx;
  text-align: center;
}

.asset-card {
  background: #fff;
  border-radius: 16rpx;
  padding: 30rpx;
  margin-bottom: 30rpx;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30rpx;
}

.title {
  font-size: 32rpx;
  font-weight: 600;
  color: #333;
}

.status {
  font-size: 24rpx;
  padding: 8rpx 20rpx;
  border-radius: 20rpx;
}

.status.inuse { background: #f0f9ff; color: #67c23a; }
.status.idle { background: #fdf6ec; color: #e6a23c; }

.info-grid {
  display: flex;
  flex-wrap: wrap;
  margin-bottom: 30rpx;
}

.info-item {
  width: 50%;
  margin-bottom: 20rpx;
}

.label {
  display: block;
  font-size: 24rpx;
  color: #999;
  margin-bottom: 8rpx;
}

.value {
  font-size: 28rpx;
  color: #333;
}

.result-section {
  margin-bottom: 30rpx;
}

.section-title {
  display: block;
  font-size: 28rpx;
  font-weight: 600;
  color: #333;
  margin-bottom: 20rpx;
}

.result-item {
  display: flex;
  align-items: center;
  margin-bottom: 16rpx;
}

.result-text {
  margin-left: 16rpx;
  font-size: 28rpx;
  color: #333;
}

.submit-btn-large {
  width: 100%;
  height: 88rpx;
  background: #67c23a;
  color: #fff;
  border-radius: 44rpx;
  font-size: 32rpx;
  line-height: 88rpx;
}

.history-section {
  background: #fff;
  border-radius: 16rpx;
  padding: 30rpx;
}

.history-list {
  margin-top: 20rpx;
}

.history-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20rpx 0;
  border-bottom: 1rpx solid #f0f0f0;
}

.history-item:last-child {
  border-bottom: none;
}

.history-info {
  display: flex;
  flex-direction: column;
}

.history-code {
  font-size: 26rpx;
  color: #999;
}

.history-name {
  font-size: 28rpx;
  color: #333;
}

.history-result {
  font-size: 24rpx;
  padding: 8rpx 16rpx;
  border-radius: 20rpx;
}

.history-result.consistent { background: #f0f9ff; color: #67c23a; }
.history-result.found { background: #ecf5ff; color: #409eff; }
.history-result.missing { background: #fef0f0; color: #f56c6c; }
.history-result.damaged { background: #fdf6ec; color: #e6a23c; }
</style>
