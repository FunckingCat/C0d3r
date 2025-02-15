import { defineStore } from "pinia";
import { ref, computed } from "vue";
import JobsService from "@/api/JobsApi";
import type { Job } from "@/types/ApiTypes";

interface ITaskStore {
	tasks: Job[];
}

export const useTasksStore = defineStore("tasks", () => {

	const getDefaultState = () => ({
		tasks: [],
	});

	const state = ref<ITaskStore>(getDefaultState());

	async function fetchTasks() {
		try {
			state.value.tasks = await JobsService.getAllJobs();
		} catch (error) {
			console.error("Failed to fetch tasks:", error);
		}
	}

	const tasks = computed(() => state.value.tasks)

	const summary = computed(() => {
		const total = state.value.tasks.length;
		const inProgress = state.value.tasks.filter(
			(task) => task.status === "RUNNING"
		).length;
		const completed = state.value.tasks.filter(
			(task) => task.status === "COMPLETED"
		).length;
		const failed = state.value.tasks.filter(
			(task) => task.status === "FAILED"
		).length;
		return { total, inProgress, completed, failed };
	});

	function reset() {
		state.value = getDefaultState()
	}

	return { 
		state, 
		tasks,
		summary,
		fetchTasks, 
		reset,
	};
});
