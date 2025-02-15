<template>
    <div class="flex items-center justify-between py-2">
      <div>
        <p class="font-medium text-lg">{{ task.name }}</p>
        <p class="text-sm text-gray-400">Image: {{ task.dockerImage }}</p>
        <p class="text-sm text-gray-500">Command: {{ task.command.join(' ') }}</p>
        <p class="text-xs text-gray-500">Created: {{ task.createdAt || 'N/A' }}</p>
      </div>
      <span :class="statusClass(task.status)" class="px-2 py-1 text-xs font-bold text-white rounded">
        {{ task.status || 'UNKNOWN' }}
      </span>
    </div>
  </template>
  
  <script setup>
  
  const props = defineProps({
    task: Object
  });
  
  const statusClass = status => {
    const statusMap = {
      RUNNING: "bg-blue-600",
      COMPLETED: "bg-green-600",
      FAILED: "bg-red-600",
      PENDING: "bg-yellow-600",
      CANCELLED: "bg-gray-600"
    };
    return statusMap[status] || "bg-gray-600";
  };
  </script>
