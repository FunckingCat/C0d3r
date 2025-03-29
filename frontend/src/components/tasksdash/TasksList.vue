<template>
	<h2 class="p-4 pb-2 text-4xl opacity-60 tracking-wide">Tasks</h2>
	
	<div v-for="(groupJobs, groupName) in taskGroups">
		<h3 v-if="groupJobs.length > 0" class="p-4 pb-2 text-xl opacity-60 tracking-wide">{{ groupName }}</h3>
		<ul class="list bg-base-200 rounded-box shadow-md">
			<TaskItem v-for="job in groupJobs" :key="job.name" :task="job" />
		</ul>
	</div>
	
</template>

<script setup lang="ts">
import { useTasksStore } from "@/stores/tasksStore";
import TaskItem from "@/components/tasksdash/TaskItem.vue";
import { storeToRefs } from "pinia";
import { computed } from "vue";
import { useAuthStore } from '@/stores/authStore';
import { type User, type Job } from "@/types/ApiTypes";

const userStore = useAuthStore()
const { user } = storeToRefs(userStore)
const tasksStore = useTasksStore()
const { tasks } = storeToRefs(tasksStore);
console.log(tasks)

type GroupedTasks = Record<string, Job[]>;

const taskGroups = computed(() => {
	const groupMap = new Map(user.value?.groups.map(group => [group.id, group.name]));
  	const defaultGroupName = "No Group";
  	const groupedTasks: GroupedTasks = { [defaultGroupName]: [] };

	console.log("groupMap", groupMap)
	console.log("tasks", tasks.value)
  
	for (const task of tasks.value) {
		console.log("Process task", task)
		const groupName = task.groupId ? groupMap.get(task.groupId) ?? defaultGroupName : defaultGroupName;
		console.log("Group name for task", groupName)
		if (!groupedTasks[groupName]) {
			groupedTasks[groupName] = [];
		}
		console.log("Before push", groupedTasks[groupName])
		groupedTasks[groupName].push(task);
		console.log("After push", groupedTasks[groupName])
	}
  
	return Object.fromEntries(
		Object.entries(groupedTasks).sort(([a], [b]) => (a === defaultGroupName ? -1 : b === defaultGroupName ? 1 : 0))
	);
})

console.log("TASKSKSKSKSK", taskGroups.value)

</script>