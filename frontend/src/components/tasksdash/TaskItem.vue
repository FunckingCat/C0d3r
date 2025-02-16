<template>
    <!-- <div class="list-row">
      <RouterLink :to="`/task/${task.id}`">
        <p class="font-medium text-lg">{{ task.name }}</p>
        <p class="text-sm text-gray-400">Image: {{ task.dockerImage }}</p>
        <p class="text-sm text-gray-500">Command: {{ task.command.join(' ') }}</p>
        <p class="text-xs text-gray-500">Created: {{ task.createdAt || 'N/A' }}</p>
      </RouterLink>
      <span :class="statusClass(task.status)" class="px-2 py-1 text-xs font-bold text-white rounded">
        {{ task.status || 'UNKNOWN' }}
      </span>
    </div> -->
    <li class="list-row">
        <RouterLink :to="`/task/${task.id}`" class="flex items-center space-x-10">
            <div class="bg-base-300 size-15 rounded-box flex items-center justify-center ml-10">
                <img class="size-7 rounded-box" src="@/assets/Check.svg" />
            </div>
            <div>
                <div class="font-medium text-lg">{{ task.name }}</div>
                <div :class="`badge badge-soft ${statusClass(task.status)}`">{{ task.status || 'UNKNOWN' }}</div>
            </div>
            <div>
                <div class="text-sm text-gray-400">Image: {{ task.dockerImage }}</div>
                <div class="text-sm text-gray-500">Command: {{ task.command.join(' ') }}</div>
                <div class="text-xs text-gray-500">Created: {{ task.createdAt || 'N/A' }}</div>
            </div>
        </RouterLink>
    </li>
</template>

<script setup>

import { RouterLink } from "vue-router";

const props = defineProps({
    task: Object
});

const statusClass = status => {
    const statusMap = {
        RUNNING: "badge-info",
        COMPLETED: "badge-success",
        FAILED: "badge-error",
        PENDING: "badge-warning",
        CANCELLED: "badge-neutral"
    };
    return statusMap[status] || "badge-info";
};
</script>
