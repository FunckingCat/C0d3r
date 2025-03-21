<template>
    <div class="w-full mt-6">
      <!-- <table class="table w-full">
        <thead>
          <tr>
            <th>Username</th>
            <th>Full Name</th>
            <th>Permissions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="member in members" :key="member.username">
            <td>{{ member.username }}</td>
            <td>{{ member.fullName }}</td>
            <td>
              <span v-for="permission in member.permissions" :key="permission" class="block">
                {{ permission }}
              </span>
            </td>
          </tr>
        </tbody>
      </table> -->
    </div>
  </template>
  
<script setup lang="ts">
import { useAuthStore } from '@/stores/authStore';
import { storeToRefs } from 'pinia';

const userStore = useAuthStore()
const { authorized, loading, user } = storeToRefs(userStore)
</script>