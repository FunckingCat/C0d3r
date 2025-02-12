import { ref, computed } from "vue";
import { defineStore } from "pinia";

interface StateType {
	authentication: AuthenticationType;
}

interface AuthenticationType {
	authorized: boolean;
}

export const useGlobalStore = defineStore("global", () => {

	const state = ref<StateType>({
		authentication: { authorized: false },
	});

	function setAuthorized(authorized: boolean) {
		state.value.authentication.authorized = authorized;
	}

	return {
		state,
		setAuthorized,
	};
});
