<template>
    <div class="card p-4">
      <div class="flex flex-col items-center">
        <img src="../../assets/Avatar.png" alt="Profile Picture" class="w-64 h-64 rounded-3xl" />
        <div class="mt-4">
          <h2 class="text-xl font-bold">{{ user?.username }}</h2>
        </div>
      </div>
    </div>
  </template>
  
<script setup lang="ts">
import { useAuthStore } from '@/stores/authStore';
import { storeToRefs } from 'pinia';

const userStore = useAuthStore()
const { authorized, loading, user } = storeToRefs(userStore)
</script>