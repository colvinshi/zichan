<template>
  <div class="profile">
    <el-card>
      <el-row :gutter="20">
        <el-col :span="8">
          <div class="profile-avatar">
            <el-avatar :size="120" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
            <h3>{{ userInfo?.realName || userInfo?.username }}</h3>
            <el-tag>{{ getRoleText(userInfo?.roleCode || '') }}</el-tag>
          </div>
        </el-col>
        <el-col :span="16">
          <el-form label-width="100px">
            <el-form-item label="用户名">
              <el-input v-model="userInfo.username" disabled />
            </el-form-item>
            <el-form-item label="真实姓名">
              <el-input v-model="userInfo.realName" />
            </el-form-item>
            <el-form-item label="手机号">
              <el-input v-model="userInfo.phone" />
            </el-form-item>
            <el-form-item label="邮箱">
              <el-input v-model="userInfo.email" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary">保存</el-button>
              <el-button @click="handleChangePassword">修改密码</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { reactive } from 'vue'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()
const userInfo = reactive({ ...userStore.userInfo })

const getRoleText = (roleCode: string) => {
  const texts: Record<string, string> = { admin: '管理员', asset_manager: '资产管理员', employee: '普通员工' }
  return texts[roleCode] || '未知'
}

const handleChangePassword = () => {
  console.log('Change password')
}
</script>

<style scoped>
.profile { padding: 20px; }
.profile-avatar { text-align: center; padding: 40px; }
.profile-avatar h3 { margin: 20px 0 10px; }
</style>
