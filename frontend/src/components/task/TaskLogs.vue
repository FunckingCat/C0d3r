<template>
	<div class="border border-gray-700 rounded-xl p-4">
		<h2 class="text-xl font-semibold mb-3">Logs</h2>
		<div class="max-h-64 overflow-y-auto text-sm font-mono bg-gray-800 p-2 rounded">
			<div v-if="logs.length > 0">
				<div
					v-for="(log, index) in logs"
					:key="index"
					class="mb-2"
				>
					{{ log }}
				</div>
			</div>
			<div v-else class="text-gray-400">No logs available.</div>
		</div>
	</div>
</template>

<script setup>
defineProps({ logs: Array });
</script>
  