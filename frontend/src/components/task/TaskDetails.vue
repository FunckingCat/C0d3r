<template>
	<div class="">
		<h2 class="text-xl font-semibold mb-3">Run parameters</h2>
		<div class="space-y-2 flex w-full">
			<div class="flex-1 border border-gray-700 rounded-xl p-6">
				<div><strong>Name:</strong> {{ task?.name }}</div>
				<div><strong>Docker Image:</strong> {{ task?.dockerImage }}</div>
				<div><strong>Execution Type:</strong> {{ task?.executionType }}</div>
				<div><strong>Command:</strong> {{ task?.command.join(" ") }}</div>
				<div><strong>Created At:</strong> {{ task?.createdAt }}</div>
			</div>
			<div class="flex flex-col ml-6 gap-4">
				<button v-if="task?.executionType == ExecutionType.ON_DEMAND" @click="rerunAction" class="btn btn-soft btn-accent w-70">Re-run</button>
				<button v-if="task?.executionType == ExecutionType.WEBHOOK" @click="triggerWebhook" class="btn btn-soft btn-accent w-70">Trigger webhook</button>
				<button @click="terminateAction" class="btn btn-soft btn-warning w-70">Terminate</button>
				<button @click="deleteAction" class="btn btn-soft btn-error w-70">Delete</button>
			</div>
		</div>
	</div>
</template>

<script setup lang="ts">
import jobApi from '@/api/JobsApi';
import router from '@/router';
import { UseCurrentTaskStore } from '@/stores/currentTaskStore';
import { ExecutionType } from '@/types/ApiTypes';
import { storeToRefs } from 'pinia';

const currentTaskStore = UseCurrentTaskStore()
const { task } = storeToRefs(currentTaskStore)

const rerunAction = () => {
	jobApi.rerunJob(task.value?.id as number)
	console.log("Call rerunAction")
}

const terminateAction = () => {
	jobApi.cancelJob(task.value?.id as number)
	console.log("Call terminateAction")
}

const deleteAction = () => {
	jobApi.delete(task.value?.id as number)
	console.log("Call deleteAction")
	router.push("/tasks")
}

const triggerWebhook = () => {
	jobApi.triggerWebhook(task.value?.id as number)
	console.log("Call trigger webhook")
}

</script>
  