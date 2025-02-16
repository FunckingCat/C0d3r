<template>
    <div class="p-6min-h-screen ">
      <h1 class="text-3xl font-bold mb-6">Task Details</h1>
  
      <div v-if="task">
        <TaskDetails :task="task" class="mb-6" />
        <TaskStatus :status="task.status" class="mb-6" />
        <TaskLogs :logs="task.executionResults?.[0]?.logs || []" />
      </div>
  
      <div v-else class="text-gray-400">Loading task information...</div>
    </div>
  </template>
  
  <script setup>
  import { computed, onMounted } from 'vue';
  import { useRoute } from 'vue-router';
  import { useTasksStore } from '@/stores/tasksStore';
  import TaskDetails from '@/components/task/TaskDetails.vue';
  import TaskStatus from '@/components/task/TaskStatus.vue';
  import TaskLogs from '@/components/task/TaskLogs.vue';
  
  const tasksStore = useTasksStore();
  const route = useRoute();
  
  const taskId = computed(() => Number(route.params.id));
  
  const task = computed(() => tasksStore.tasks.find((t) => t.id === taskId.value));
  </script>
  