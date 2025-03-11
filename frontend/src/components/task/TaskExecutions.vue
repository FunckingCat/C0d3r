<template>
	<div class="overflow-x-auto">
		<h1 class="text-3xl font-bold mb-6">Executions</h1>
	  <table class="table w-full table-zebra">
		<thead>
		  <tr>
			<th>#</th>
			<th>ID</th>
			<th>Started At</th>
			<th>Finished At</th>
			<th>Status</th>
			<th></th>
		  </tr>
		</thead>
		<tbody>
		  <tr v-for="(result, index) in props.task.executionResults" :key="result.id || index">
			<td>{{ index + 1 }}</td>
			<td>{{ result.id ?? 'N/A' }}</td>
			<td>{{ formatDate(result.startedAt) }}</td>
			<td>{{ formatDate(result.finishedAt) }}</td>
			<td>{{ result.status }}</td>
			<td><button class="btn" @click="openModal(result.id ?? -1)">View Logs</button></td>
		  </tr>
		</tbody>
	  </table>
	</div>
  </template>

<script setup lang="ts">
import type { Job } from "../../types/ApiTypes";
import { UseCurrentTaskStore } from '@/stores/currentTaskStore';
import { storeToRefs } from 'pinia';

const currentTaskStore = UseCurrentTaskStore()
const { activeExecutionId, task } = storeToRefs(currentTaskStore)

const props = defineProps<{
  task: Job
}>()

const openModal = (executionId: Number) => {
	currentTaskStore.setModelActive(executionId)
}

const formatDate = (dateString?: string | null) => 
  dateString ? new Date(dateString).toLocaleString() : 'N/A';

</script>
  