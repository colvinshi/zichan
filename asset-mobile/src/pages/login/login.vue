<template>
  <view class="login-container">
    <view class="login-header">
      <image class="logo" src="/static/logo.png" mode="aspectFit" />
      <text class="title">资产管理系统</text>
    </view>
    
    <view class="login-form">
      <view class="form-item">
        <text class="label">用户名</text>
        <input 
          class="input" 
          v-model="form.username" 
          placeholder="请输入用户名" 
          placeholder-class="placeholder"
        />
      </view>
      
      <view class="form-item">
        <text class="label">密码</text>
        <input 
          class="input" 
          v-model="form.password" 
          type="password" 
          placeholder="请输入密码" 
          placeholder-class="placeholder"
        />
      </view>
      
      <button class="login-btn" :loading="loading" @click="handleLogin">
        {{ loading ? '登录中...' : '登 录' }}
      </button>
    </view>
    
    <view class="login-tip">
      <text>默认账号: admin / Admin@123</text>
    </view>
  </view>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useUserStore } from '@/store/user'

const userStore = useUserStore()

const loading = ref(false)
const form = reactive({
  username: '',
  password: ''
})

const handleLogin = async () => {
  if (!form.username) {
    uni.showToast({ title: '请输入用户名', icon: 'none' })
    return
  }
  if (!form.password) {
    uni.showToast({ title: '请输入密码', icon: 'none' })
    return
  }
  
  loading.value = true
  
  try {
    // Mock login
    const mockToken = 'mock-token-' + Date.now()
    const mockUserInfo = {
      userId: 1,
      username: form.username,
      realName: '系统管理员',
      roleCode: 'admin',
      deptId: 1
    }
    
    uni.setStorageSync('token', mockToken)
    uni.setStorageSync('userInfo', mockUserInfo)
    userStore.setToken(mockToken)
    userStore.setUserInfo(mockUserInfo)
    
    uni.showToast({ title: '登录成功', icon: 'success' })
    
    setTimeout(() => {
      uni.switchTab({ url: '/pages/index/index' })
    }, 500)
  } catch (error) {
    uni.showToast({ title: '登录失败', icon: 'none' })
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 100rpx 60rpx;
}

.login-header {
  text-align: center;
  margin-bottom: 80rpx;
}

.logo {
  width: 160rpx;
  height: 160rpx;
  margin-bottom: 30rpx;
}

.title {
  display: block;
  color: #fff;
  font-size: 48rpx;
  font-weight: 600;
}

.login-form {
  background: #fff;
  border-radius: 24rpx;
  padding: 60rpx 40rpx;
}

.form-item {
  margin-bottom: 40rpx;
}

.label {
  display: block;
  color: #333;
  font-size: 28rpx;
  margin-bottom: 16rpx;
  font-weight: 500;
}

.input {
  height: 88rpx;
  background: #f5f5f5;
  border-radius: 44rpx;
  padding: 0 30rpx;
  font-size: 28rpx;
}

.placeholder {
  color: #999;
}

.login-btn {
  background: #409eff;
  color: #fff;
  border-radius: 44rpx;
  height: 88rpx;
  line-height: 88rpx;
  font-size: 32rpx;
  margin-top: 40rpx;
}

.login-btn:active {
  opacity: 0.8;
}

.login-tip {
  text-align: center;
  margin-top: 40rpx;
  color: rgba(255, 255, 255, 0.8);
  font-size: 24rpx;
}
</style>
