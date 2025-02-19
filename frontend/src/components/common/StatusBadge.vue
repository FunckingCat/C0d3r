<template>
    <div :class="`badge badge-soft ${statusClass()}`">
        {{ status || 'UNKNOWN' }}
    </div>
</template>

<script setup lang="ts">
import type { JobStatus } from '@/types/ApiTypes';

const props = defineProps<{
  status: JobStatus
}>()

const statusClass = () => {
    const statusMap = {
        RUNNING: "badge-info",
        COMPLETED: "badge-success",
        FAILED: "badge-error",
        PENDING: "badge-warning",
        CANCELLED: "badge-neutral"
    };
    return statusMap[props.status] || "badge-info";
};
</script>