import StorageService from "@/util/StorageService";
import UserApi from "@/api/UserApi"
import { useAuthStore } from "@/stores/authStore";
import router from "@/router";

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
    router.push("/tasks")
}