<template>
    <div class="p-6 w-full max-w-5xl">
        <h1 class="text-3xl font-bold mb-6">Jobs Dashboard</h1>

        <!-- Summary -->
        <TasksSummary :summary="summary" class="mb-6" />

        <!-- Task List -->
        <TasksList />
    </div>
</template>

<script setup lang="ts">
import { onMounted, onUnmounted, computed } from 'vue';
import { useTasksStore } from '@/stores/tasksStore';
import TasksSummary from '@/components/tasksdash/TasksSummary.vue';
import TasksList from '@/components/tasksdash/TasksList.vue';
import { PollingService } from '@/api/polling/PollingService';
import jobApi from '@/api/JobsApi';
import type { Job } from '@/types/ApiTypes';

const tasksStore = useTasksStore();
const summary = computed(() => tasksStore.summary);

const pollingService: PollingService<Job[]> = new PollingService<Job[]>({
    name: 'TasksPullingService',
    interval: 1000,
    action: () => jobApi.getAllJobs(),
    callback: (jobs) => tasksStore.setTasks(jobs)
})

onMounted(async () => {
    await pollingService.start()
})

onUnmounted(() => {
    pollingService.stop()
  })

</script>