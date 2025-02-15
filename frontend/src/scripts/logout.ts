import router from "@/router";
import { useAuthStore } from "@/stores/authStore";
import { useTasksStore } from "@/stores/tasksStore";
import StorageService from "@/util/StorageService";
import { nextTick } from 'vue'

export async function logOut() {
    const authStore = useAuthStore()
    const tasksStore = useTasksStore()
    authStore.reset()
    tasksStore.reset()
    StorageService.clearTokens()
    await router.push("/login");
}