<template>
    <div class="border border-gray-700 rounded-xl p-4">
      <h2 class="text-xl font-semibold mb-3">Status</h2>
      <div class="flex items-center space-x-3">
        <span
          class="w-3 h-3 rounded-full"
          :class="statusColor"
        ></span>
        <span>{{ status }}</span>
      </div>
    </div>
  </template>
  
  <script setup>
  import { computed } from 'vue';
  
  const props = defineProps({ status: String });
  
  const statusColor = computed(() => {
    switch (props.status) {
      case 'RUNNING':
        return 'bg-green-500';
      case 'FAILED':
        return 'bg-red-500';
      case 'COMPLETED':
        return 'bg-blue-500';
      case 'PENDING':
        return 'bg-yellow-500';
      default:
        return 'bg-gray-500';
    }
  });
  </script>
  