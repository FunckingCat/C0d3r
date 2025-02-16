<template>
    <div class="p-6 min-h-screen">
        <h1 class="text-3xl font-bold mb-6">Jobs Dashboard</h1>

        <!-- Summary -->
        <TasksSummary :summary="summary" class="mb-6" />

        <!-- Task List -->
        <TasksList />
    </div>
</template>

<script setup>
import { onMounted, computed } from 'vue';
import { useTasksStore } from '@/stores/tasksStore';
import TasksSummary from '@/components/tasksdash/TasksSummary.vue';
import TasksList from '@/components/tasksdash/TasksList.vue';

const tasksStore = useTasksStore();

const summary = computed(() => tasksStore.getSummary);
</script>