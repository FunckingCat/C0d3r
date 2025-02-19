import { defineStore } from "pinia";
import { ref, computed } from "vue";
import type { Job } from "@/types/ApiTypes";

interface ICurrentTaskStore {
    task: Job | null;
}

export const UseCurrentTaskStore = defineStore("currentTask", () => {

    const getDefaultState = () => ({
        task: null,
    });

    const state = ref<ICurrentTaskStore>(getDefaultState());

    async function setTask(tasks: Job) {
        state.value.task = tasks;
    }

    function reset() {
        state.value = getDefaultState()
    }

    return { 
        state, 
        task: computed(() => state.value.task),
        setTask, 
        reset,
    };
});