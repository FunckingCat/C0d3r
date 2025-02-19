<template>
	<header class="h-15 border-b-1 w-full flex justify-center">
		<div class="flex items-center justify-between py-4 px-6 max-w-5xl w-full">
		
			<RouterLink to="/" class="flex items-center text-xl font-bold hover:text-blue-300">
				<img src="@/assets/LyambdaLogo.svg" alt="Lyambda Logo" class="mr-2 w-6 h-6" />
				Lambda
			</RouterLink>

			<div v-if="authorized" class="flex items-center space-x-4" :key="authorized">
				<div>
					{{ user?.username || "Problem" }}
				</div>
				<LogoutButton />
				<RouterLink to="/new-job" class="btn btn-primary">
					New Job
				</RouterLink>
			</div>
		</div>
	</header>
</template>

<script setup>
import { RouterLink } from "vue-router";
import { useAuthStore } from "@/stores/authStore";
import { storeToRefs } from "pinia";
import LogoutButton from "./common/LogoutButton.vue";

var userStore = useAuthStore()
var { authorized, user } = storeToRefs(userStore)
</script>