<!-- src/components/tasksdash/TasksList.vue -->
<template>
    <div class="bg-gray-800 p-4 rounded-lg">
      <h2 class="text-xl font-semibold mb-4">Tasks</h2>
      <div class="divide-y divide-gray-700">
        <div v-for="task in tasks" :key="task.id" class="py-2 flex justify-between items-center">
          <div>
            <p class="font-medium">{{ task.name }}</p>
            <p class="text-sm text-gray-400">{{ task.dockerImage }}</p>
          </div>
          <span class="px-2 py-1 rounded text-sm" :class="statusClass(task.status)">
            {{ task.status }}
          </span>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { computed } from 'vue';
  
  const props = defineProps({ tasks: Array });
  
  const statusClass = status => {
    const statusMap = {
      RUNNING: 'bg-blue-600',
      COMPLETED: 'bg-green-600',
      FAILED: 'bg-red-600',
      PENDING: 'bg-yellow-600',
      CANCELLED: 'bg-gray-600',
    };
    return statusMap[status] || 'bg-gray-600';
  };
  </script>
  