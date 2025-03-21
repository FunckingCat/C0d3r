import { ref, computed } from "vue";
import { defineStore } from "pinia";
import type { GroupDescription, User } from "@/types/ApiTypes";

interface StateType {
	authorized: boolean
	user?: User
	loading: boolean
	activeGroup?: string
	activeGroupDescription?: GroupDescription
}

export const useAuthStore = defineStore("auth", () => {

	const getDefaultState = () => ({
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
		if (state.value.activeGroup == undefined) {
			const group = user.groups?.[0]?.id ?? null;
			if (group != null) {
				state.value.activeGroup = group
			}
		}
	}

	function setLoading() {
		state.value.loading = true;
	}

	function setActiveGroup(groupId: string) {
		state.value.activeGroup = groupId
	}

	function setActiveGroupDescription(groupDescription: GroupDescription) {
		state.value.activeGroupDescription = groupDescription
	}

	return {
		state,
		authorized: computed(() => state.value.authorized),
		loading: computed(() => state.value.loading),
		user: computed(() => state.value.user),
		activeGroup: computed(() => state.value.activeGroup),
		activeGroupDescription: computed(() => state.value.activeGroupDescription),
		setAuthorized,
		setUser,
		setLoading,
		setActiveGroup,
		setActiveGroupDescription,
		reset
	};
});
