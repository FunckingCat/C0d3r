<template>
    <li class="list-row">
        <RouterLink :to="`/tasks/${task.id}`" class="flex items-center space-x-10">
            <div class="bg-base-300 size-15 rounded-box flex items-center justify-center ml-10">
                <img class="size-7 rounded-box" src="@/assets/Check.svg" />
            </div>
            <div class="w-50 flex flex-col items-center gap-2">
                <div class="font-medium text-lg">{{ task.name }}</div>
                <StatusBadge :status="task.status!!" />
            </div>
            <div class="flex flex-col">
                <div class="text-sm text-gray-400">Image: {{ task.dockerImage }}</div>
                <div class="text-sm text-gray-500">Command: {{ task.command.join(' ') }}</div>
                <div class="text-xs text-gray-500">Created: {{ task.createdAt || 'N/A' }}</div>
            </div>
        </RouterLink>
    </li>
</template>

<script setup lang="ts">

import { RouterLink } from "vue-router";
import StatusBadge from "../common/StatusBadge.vue";
import type { Job } from "@/types/ApiTypes"

const props = defineProps<{
    task: Job
}>();

</script>
