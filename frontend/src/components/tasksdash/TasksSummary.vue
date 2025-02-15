<!-- src/components/tasksdash/TasksSummary.vue -->
<template>
	<div class="grid grid-cols-1 md:grid-cols-4 gap-4 mb-6">
		<div class="bg-gray-800 p-4 rounded-lg text-center">
			<p class="text-lg font-semibold">Total Tasks</p>
			<p class="text-2xl">{{ summary.total }}</p>
		</div>
		<div class="bg-gray-800 p-4 rounded-lg text-center">
			<p class="text-lg font-semibold">In Progress</p>
			<p class="text-2xl">{{ summary.inProgress }}</p>
		</div>
		<div class="bg-gray-800 p-4 rounded-lg text-center">
			<p class="text-lg font-semibold">Completed</p>
			<p class="text-2xl">{{ summary.completed }}</p>
		</div>
		<div class="bg-gray-800 p-4 rounded-lg text-center">
			<p class="text-lg font-semibold">Failed</p>
			<p class="text-2xl">{{ summary.failed }}</p>
		</div>
	</div>
</template>

<script setup>
import { useTasksStore } from '@/stores/tasksStore';
import { storeToRefs } from 'pinia';

const taskStore = useTasksStore()
const { summary } = storeToRefs(taskStore)
 
</script>