<template>
	<h2 class="p-4 pb-2 text-xl opacity-60 tracking-wide">Tasks</h2>

	<ul class="list bg-base-200 rounded-box shadow-md">
		<TaskItem v-for="task in tasks" :key="task.id" :task="task" />
	</ul>
</template>

<script setup>
import { useTasksStore } from "@/stores/tasksStore";
import TaskItem from "@/components/tasksdash/TaskItem.vue";
import { storeToRefs } from "pinia";

const tasksStore = useTasksStore()
const { tasks } = storeToRefs(tasksStore);
console.log(tasks)

</script>