// src/stores/tasksStore.js
import { defineStore } from "pinia";
import { reactive, computed } from "vue";
import JobsService from "@/service/JobsService";
import type { Job } from "@/types/ApiTypes";

interface ITaskStore {
	tasks: Job[];
}

export const useTasksStore = defineStore("tasks", () => {
	const state = reactive<ITaskStore>({
		tasks: [],
	});

	async function fetchTasks() {
		try {
			state.tasks = await JobsService.getAllJobs();
		} catch (error) {
			console.error("Failed to fetch tasks:", error);
		}
	}

	const getSummary = computed(() => {
		const total = state.tasks.length;
		const inProgress = state.tasks.filter(
			(task) => task.status === "RUNNING"
		).length;
		const completed = state.tasks.filter(
			(task) => task.status === "COMPLETED"
		).length;
		const failed = state.tasks.filter(
			(task) => task.status === "FAILED"
		).length;
		return { total, inProgress, completed, failed };
	});

	return { tasks: state.tasks, fetchTasks, getSummary };
});
