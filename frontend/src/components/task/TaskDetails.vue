<template>
	<div class="border border-gray-700 rounded-xl p-4">
		<h2 class="text-xl font-semibold mb-3">Task Info</h2>
		<div class="space-y-2">
			<div><strong>Name:</strong> {{ task.name }}</div>
			<div><strong>Docker Image:</strong> {{ task.dockerImage }}</div>
			<div><strong>Execution Type:</strong> {{ task.executionType }}</div>
			<div><strong>Command:</strong> {{ task.command.join(" ") }}</div>
			<div><strong>Created At:</strong> {{ task.createdAt }}</div>
		</div>
	</div>
</template>

<script setup>
defineProps({ task: Object });
</script>
  