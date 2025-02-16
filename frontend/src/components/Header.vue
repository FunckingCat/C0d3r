<template>
	<header class="border-b-1">
		<div class="container mx-auto flex items-center justify-between py-4 px-6">
			<!-- Left: Logo & Navigation Links -->
			<nav class="flex items-center space-x-6">
				<RouterLink to="/" class="flex items-center text-xl font-bold hover:text-blue-300">
					<img src="@/assets/LyambdaLogo.svg" alt="Lyambda Logo" class="mr-2 w-6 h-6" />
					Lambda
				</RouterLink>
			</nav>

			<div v-if="authorized" class="flex items-center space-x-4" :key="authorized">
				<div>
					{{ user?.username || "Problem"}}
				</div>
				<LogoutButton />
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