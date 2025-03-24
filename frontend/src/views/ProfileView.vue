<template>
  <div class="px-6 w-full max-w-5xl">
    <Breadcrumbs/>
    <h1 class="text-3xl font-bold mb-6">Profile</h1>
    <div v-if="loading">
      <span class="loading loading-infinity loading-xl"></span>
    </div>
    <div v-else class="w-full">
      <div class="flex">
        <ProfileCard class="flex-none"/>
        <GroupSelection class="flex-1"/>
      </div>
      <h2 class="text-2xl font-bold">{{ activeGroupDescription?.name }}</h2>
      <AccessToken v-if="activeGroupDescription != undefined"/>
      <MembersTable v-if="activeGroupDescription != undefined"/>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, onUnmounted, watch } from 'vue';
import userApi from '@/api/UserApi';
import Breadcrumbs from '@/components/Breadcrumbs.vue';
import AccessToken from '@/components/profile/AccessToken.vue';
import ProfileCard from '@/components/profile/ProfileCard.vue';
import GroupSelection from '@/components/profile/GroupSelection.vue';
import { useAuthStore } from '@/stores/authStore';
import {type GroupDescription, type User } from '../types/ApiTypes'
import { PollingService } from '@/api/polling/PollingService';
import { storeToRefs } from 'pinia';
import MembersTable from '@/components/profile/MembersTable.vue';
import roleModelApi from '@/api/RoleModelApi';

const userStore = useAuthStore()
const { authorized, loading, user, activeGroup, activeGroupDescription } = storeToRefs(userStore)

watch(activeGroup, async (newGroup) => {
    if (newGroup == undefined) {
      return
    }
    console.log("Active group changed, polling new group")
    var userResponse = await userApi.getCurrentUser()
    userStore.setUser(userResponse)
    var group = await roleModelApi.getGroup(newGroup as string)
    userStore.setActiveGroupDescription(group)
}, { immediate: true });

const userPollingService = new PollingService<User>({
  name: 'UserPollingService',
  interval: 5100,
  action: () => userApi.getCurrentUser(),
  callback: (user) => userStore.setUser(user)
})

const gproupPollingService = new PollingService<GroupDescription>({
  name: 'GroupPollingService',
  interval: 5000,
  filter: () => activeGroup.value != undefined,
  action: () => roleModelApi.getGroup(activeGroup.value as string),
  callback: (group) => userStore.setActiveGroupDescription(group)
})

onMounted(async () => {
  await userPollingService.start()
  await gproupPollingService.start()
})

onUnmounted(() => {
  userPollingService.stop()
  gproupPollingService.stop()
})

</script>