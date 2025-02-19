<template>
  <div class="p-6 w-full max-w-5xl">
    <h1 class="text-3xl font-bold mb-6">Task Details</h1>

    <div v-if="task">
      <TaskDetails :task="task" class="mb-6" />
      <TaskLogs :logs="task.executionResults?.[0]?.logs || []" />
    </div>

    <div v-else class="text-gray-400">Loading task information...</div>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, onUnmounted } from 'vue';
import { useRoute } from 'vue-router';
import { useTasksStore } from '@/stores/tasksStore';
import TaskDetails from '@/components/task/TaskDetails.vue';
import TaskLogs from '@/components/task/TaskLogs.vue';
import { UseCurrentTaskStore } from '@/stores/currentTaskStore';
import { storeToRefs } from 'pinia';
import { PollingService } from '@/api/polling/PollingService';
import type { Job } from '@/types/ApiTypes';
import jobApi from '@/api/JobsApi';

const route = useRoute()
const taskId = Number(route.params.id);

const currentTaskStore = UseCurrentTaskStore()
const { task } = storeToRefs(currentTaskStore)

const pollingService = new PollingService<Job>({
  name: 'CurrentTaskPullingService',
  interval: 1000,
  action: () => jobApi.getJobById(taskId),
  callback: (job) => currentTaskStore.setTask(job)
})

onMounted(async () => {
  await pollingService.start()
})

onUnmounted(() => {
  pollingService.stop()
})

</script>