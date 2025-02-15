import StorageService from "@/util/StorageService";
import UserApi from "@/api/UserApi"
import { useAuthStore } from "@/stores/authStore";
import router from "@/router";
import { useTasksStore } from "@/stores/tasksStore";

export async function logInIfTokenPresent() {
    var token = StorageService.getAccessToken()
    if (token == null) {
        console.log("No token in storage")
        return
    }

    var user = await UserApi.getCurrentUser()
    var store = useAuthStore()
    store.setAuthorized(true)
    store.setUser(user)
    useTasksStore().fetchTasks()
    router.push("/tasks")
    console.log("post logIn pre fetch success")
}