<template>
	<header class="bg-gray-900 text-white shadow-md">
		<div class="container mx-auto flex items-center justify-between py-4 px-6">
			<!-- Left: Logo & Navigation Links -->
			<nav class="flex items-center space-x-6">
				<RouterLink to="/" class="text-xl font-bold text-blue-400 hover:text-blue-300">
					Lyambda
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