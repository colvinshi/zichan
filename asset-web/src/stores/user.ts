import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { RouteRecordRaw } from 'vue-router'

export interface UserInfo {
  userId: number
  username: string
  realName: string
  roleCode: string
  deptId: number
}

export const useUserStore = defineStore(
  'user',
  () => {
    const token = ref<string>('')
    const userInfo = ref<UserInfo | null>(null)
    const permissions = ref<string[]>([])

    // Set token
    const setToken = (newToken: string) => {
      token.value = newToken
    }

    // Set user info
    const setUserInfo = (info: UserInfo) => {
      userInfo.value = info
    }

    // Set permissions
    const setPermissions = (perms: string[]) => {
      permissions.value = perms
    }

    // Logout
    const logout = () => {
      token.value = ''
      userInfo.value = null
      permissions.value = []
    }

    // Check if has permission
    const hasPermission = (permission: string) => {
      return permissions.value.includes(permission)
    }

    // Check if has role
    const hasRole = (role: string) => {
      return userInfo.value?.roleCode === role
    }

    return {
      token,
      userInfo,
      permissions,
      setToken,
      setUserInfo,
      setPermissions,
      logout,
      hasPermission,
      hasRole
    }
  },
  {
    persist: {
      key: 'asset-user',
      storage: localStorage
    }
  }
)
