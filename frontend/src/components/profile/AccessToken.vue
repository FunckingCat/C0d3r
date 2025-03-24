<template>
    <div v-if="isGroupAdmin" class="card w-full p-4">
      <div class="mt-4">
        <label>Access Token:</label>
        <div class="flex items-center">
          <input type="text" v-model="accessToken" readonly class="input w-full" />
          <button @click="copyToken" class="btn btn-secondary ml-2">Copy token</button>
          <button @click="revokeToken" class="btn btn-danger ml-2">Revoke token</button>
        </div>
      </div>
    </div>
  </template>
  
<script setup lang="ts">
import roleModelApi from '@/api/RoleModelApi';
import { useAuthStore } from '@/stores/authStore';
import { Permission } from '@/types/ApiTypes';
import { computed } from '@vue/reactivity';
import { storeToRefs } from 'pinia';
import { ref, watch } from 'vue';

const userStore = useAuthStore()
const { authorized, loading, user, activeGroup, activeGroupDescription } = storeToRefs(userStore)

const accessToken = ref('******************');

watch(activeGroup, async (newGroup) => {
    if (newGroup == undefined) {
      return "Active group not selected"
    }
    var token = await roleModelApi.getJoinGroupToken(newGroup as string)
    console.log("TOKEN", token)
    accessToken.value = token.token
}, { immediate: true });

const copyToken = () => {
  console.log("Copy token")
  const token = accessToken.value; // Get the current token value
  if (!token || token === "Active group not selected" || token === "Error fetching token") {
    console.error("No valid token to copy.");
    return;
  }
  navigator.clipboard.writeText(token);
}

const revokeToken = async () => {
    await roleModelApi.refreshJoinGroupToken(activeGroup.value as string)
    var token = await roleModelApi.getJoinGroupToken(activeGroup.value as string)
    console.log("TOKEN", token)
    accessToken.value = token.token
}

const userPermissionContains = (permissions: Permission[], permission: string) => {
    var permissionsList = permissions?.map(status => status.toString())
    return permissionsList.includes(permission)
}

const isGroupAdmin = computed(
  () => {

    console.log("isGroupAdmin", activeGroup.value, activeGroupDescription.value)

    if (activeGroup.value == undefined) {
      console.log("No active group selected")
      return false
    }

    var userPermissionsInGroup = user.value?.groups.filter(g => g.id == activeGroup.value)?.[0]?.permissions
    var isAdmin = userPermissionContains(userPermissionsInGroup as Permission[], "ADMIN")
    console.log(`User ${user.value?.username} isAdmin = ${isAdmin} in group ${activeGroupDescription.value?.name}`)
    return isAdmin
  }
)

</script>