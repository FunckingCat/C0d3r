<!-- src/components/tasksdash/TasksList.vue -->
<template>
	<div class="bg-gray-800 p-4 rounded-lg">
	  <h2 class="text-xl font-semibold mb-4">Tasks</h2>
	  <div class="divide-y divide-gray-700">
		<TaskItem v-for="task in tasks" :key="task.id" :task="task" />
	  </div>
	</div>
  </template>
  
  <script setup>
  import { useTasksStore } from "@/stores/tasksStore";
  import TaskItem from "@/components/tasksdash/TaskItem.vue";
  import { storeToRefs } from "pinia";
  
  const tasksStore = useTasksStore()
  const { tasks } = storeToRefs(tasksStore);
  console.log(tasks)

  </script>
  
  