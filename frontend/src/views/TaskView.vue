<template>
  <div class="px-6 w-full max-w-5xl">
    <Breadcrumbs/>
    <h1 class="text-3xl font-bold mb-6">Task Details</h1>

    <div v-if="task">
      <TaskDetails class="mb-6" />
      <TaskExecutions :task="task" />
    </div>

    <div v-else class="text-gray-400">Loading task information...</div>
  </div>
  <TaskExecutionModal />
</template>

<script setup lang="ts">
import { watch, computed, onMounted, onUnmounted } from 'vue';
import { useRoute } from 'vue-router';
import { useTasksStore } from '@/stores/tasksStore';
import TaskDetails from '@/components/task/TaskDetails.vue';
import TaskExecutions from '@/components/task/TaskExecutions.vue';
import { UseCurrentTaskStore } from '@/stores/currentTaskStore';
import { storeToRefs } from 'pinia';
import { PollingService } from '@/api/polling/PollingService';
import type { Job } from '@/types/ApiTypes';
import jobApi from '@/api/JobsApi';
import Breadcrumbs from '@/components/Breadcrumbs.vue';
import TaskExecutionModal from '@/components/task/TaskExecutionModal.vue';
import { useBreadCrumbStore } from '@/stores/breadCrumbsStore';

const breadCrumbsStore = useBreadCrumbStore()
const route = useRoute()
const taskId = Number(route.params.id);

const currentTaskStore = UseCurrentTaskStore()
const { task } = storeToRefs(currentTaskStore)

watch(task, async (newTaskId) => {
  breadCrumbsStore.setCrumbs([
      { name: 'Jobs Dashboard', link: "/tasks" },
      { name: `Job ${task.value?.name}`, link: `/tasks/${task.value?.id}` }
  ])
}, { immediate: true });

const pollingService = new PollingService<Job>({
  name: 'CurrentTaskPullingService',
  interval: 500,
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