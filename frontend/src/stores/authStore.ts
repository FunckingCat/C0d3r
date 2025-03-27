import { ref, computed } from "vue";
import { defineStore } from "pinia";
import type { GroupDescription, User } from "@/types/ApiTypes";

interface StateType {
	authFormState: {
		state: AuthFormState
	}
	authorized: boolean
	user?: User
	loading: boolean
	activeGroup?: string
	activeGroupDescription?: GroupDescription
}

export enum AuthFormState { LOGIN, REG, RESET }

export const useAuthStore = defineStore("auth", () => {

	const getDefaultState = () => ({
		authFormState: {
			state: AuthFormState.LOGIN
		},
		authorized: false,
		loading: false
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
		state.value.loading = false;
	}

	function setLoading() {
		state.value.loading = true;
	}

	function setActiveGroup(groupId: string | undefined) {
		state.value.activeGroup = groupId
	}

	function setActiveGroupDescription(groupDescription: GroupDescription | undefined) {
		state.value.activeGroupDescription = groupDescription
	}

	function setAuthFromState(stateValue: AuthFormState) {
		state.value.authFormState.state = stateValue
	}

	return {
		state,
		authorized: computed(() => state.value.authorized),
		loading: computed(() => state.value.loading),
		user: computed(() => state.value.user),
		activeGroup: computed(() => state.value.activeGroup),
		activeGroupDescription: computed(() => state.value.activeGroupDescription),
		authFormState: computed(() => state.value.authFormState.state),
		setAuthorized,
		setUser,
		setLoading,
		setActiveGroup,
		setActiveGroupDescription,
		setAuthFromState,
		reset
	};
});
