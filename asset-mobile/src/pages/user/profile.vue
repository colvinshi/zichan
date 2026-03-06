<template>
  <view class="profile-container">
    <!-- User Info Card -->
    <view class="user-card">
      <image class="avatar" src="/static/avatar.png" mode="aspectFill" />
      <text class="name">{{ userInfo.realName || userInfo.username }}</text>
      <text class="role">{{ getRoleText(userInfo.roleCode) }}</text>
    </view>
    
    <!-- Menu List -->
    <view class="menu-list">
      <view class="menu-item" @click="goPage('/pages/user/edit')">
        <text class="menu-icon">&#xe6b0;</text>
        <text class="menu-text">个人信息</text>
        <text class="menu-arrow">></text>
      </view>
      
      <view class="menu-item" @click="handleChangePassword">
        <text class="menu-icon">&#xe6b1;</text>
        <text class="menu-text">修改密码</text>
        <text class="menu-arrow">></text>
      </view>
      
      <view class="menu-item">
        <text class="menu-icon">&#xe6b2;</text>
        <text class="menu-text">消息通知</text>
        <text class="menu-arrow">></text>
      </view>
      
      <view class="menu-item">
        <text class="menu-icon">&#xe6b3;</text>
        <text class="menu-text">关于我们</text>
        <text class="menu-arrow">></text>
      </view>
    </view>
    
    <!-- Logout Button -->
    <button class="logout-btn" @click="handleLogout">退出登录</button>
  </view>
</template>

<script setup>
import { reactive } from 'vue'
import { useUserStore } from '@/store/user'

const userStore = useUserStore()
const userInfo = userStore.userInfo || { username: 'Admin', realName: '管理员', roleCode: 'admin' }

const getRoleText = (roleCode) => {
  const texts = { admin: '管理员', asset_manager: '资产管理员', employee: '普通员工' }
  return texts[roleCode] || '员工'
}

const goPage = (url) => {
  uni.showToast({ title: '功能开发中', icon: 'none' })
}

const handleChangePassword = () => {
  uni.showToast({ title: '功能开发中', icon: 'none' })
}

const handleLogout = () => {
  uni.showModal({
    title: '提示',
    content: '确定要退出登录吗？',
    success: (res) => {
      if (res.confirm) {
        userStore.logout()
      }
    }
  })
}
</script>

<style scoped>
.profile-container {
  min-height: 100vh;
  background: #f5f5f5;
  padding: 20rpx;
}

.user-card {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 24rpx;
  padding: 60rpx;
  text-align: center;
  margin-bottom: 30rpx;
}

.avatar {
  width: 140rpx;
  height: 140rpx;
  border-radius: 50%;
  margin-bottom: 20rpx;
}

.name {
  display: block;
  color: #fff;
  font-size: 40rpx;
  font-weight: 600;
  margin-bottom: 10rpx;
}

.role {
  color: rgba(255, 255, 255, 0.8);
  font-size: 26rpx;
}

.menu-list {
  background: #fff;
  border-radius: 16rpx;
  overflow: hidden;
  margin-bottom: 30rpx;
}

.menu-item {
  display: flex;
  align-items: center;
  padding: 30rpx;
  border-bottom: 1rpx solid #f0f0f0;
}

.menu-item:last-child {
  border-bottom: none;
}

.menu-icon {
  font-size: 40rpx;
  margin-right: 20rpx;
  color: #409eff;
}

.menu-text {
  flex: 1;
  font-size: 28rpx;
  color: #333;
}

.menu-arrow {
  color: #ccc;
}

.logout-btn {
  width: 100%;
  height: 88rpx;
  background: #fff;
  color: #f56c6c;
  border-radius: 44rpx;
  font-size: 32rpx;
  line-height: 88rpx;
  text-align: center;
}
</style>
