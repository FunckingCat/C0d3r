<template>
    <div class="card w-full p-4">
      <div class="mt-4">
        <label>Access Token:</label>
        <div class="flex items-center">
          <input type="text" v-model="accessToken" readonly class="input w-full" />
          <button :click="copyToken" class="btn btn-secondary ml-2">Copy token</button>
          <button :click="revokeToken" class="btn btn-danger ml-2">Revoke token</button>
        </div>
      </div>
    </div>
  </template>
  
<script setup lang="ts">
import { useAuthStore } from '@/stores/authStore';
import { storeToRefs } from 'pinia';

var accessToken = "***********************************************************************"
const userStore = useAuthStore()
const { authorized, loading, user } = storeToRefs(userStore)

const copyToken = () => {

}

const revokeToken = () => {
    
}
</script>