import { ref, computed } from "vue";
import { defineStore } from "pinia";
import type { User } from "@/types/ApiTypes";

interface StateType {
	authorized: boolean
	user?: User
}

export const useAuthStore = defineStore("auth", () => {

	const getDefaultState = () => ({
		authorized: false
	  });

	const state = ref<StateType>(getDefaultState());

	function reset() {
		state.value = getDefaultState()
	}

	function setAuthorized(authorized: boolean) {
		state.value.authorized = authorized;
	}

	function setUser(user: User) {
		state.value.user = user;
	}

	return {
		state,
		authorized: computed(() => state.value.authorized),
		user: computed(() => state.value.user),
		setAuthorized,
		setUser,
		reset
	};
});
