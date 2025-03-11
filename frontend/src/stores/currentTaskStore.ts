import { defineStore } from "pinia";
import { ref, computed } from "vue";
import type { Job } from "@/types/ApiTypes";

interface ICurrentTaskStore {
    executionModelOpened: Boolean,
    activeExecutionId: Number | null,
    task: Job | null
}

export const UseCurrentTaskStore = defineStore("currentTask", () => {

    const getDefaultState = () => ({
        executionModelOpened: false,
        activeExecutionId: null,
        task: null,
    });

    const state = ref<ICurrentTaskStore>(getDefaultState());

    async function setTask(tasks: Job) {
        state.value.task = tasks;
    }

    async function setModelActive(executionId: Number) {
        state.value.executionModelOpened = true
        state.value.activeExecutionId = executionId;
    }

    async function setModelNotActive() {
        state.value.executionModelOpened = false
        state.value.activeExecutionId = null;
    }

    function reset() {
        state.value = getDefaultState()
    }

    return { 
        state, 
        task: computed(() => state.value.task),
        activeExecutionId: computed(() => state.value.activeExecutionId),
        executionModelOpened: computed(() => state.value.executionModelOpened),
        setTask, 
        reset,
        setModelActive,
        setModelNotActive
    };
});