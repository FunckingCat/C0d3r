<template>
	<form class="space-y-4">
		<div>
			<label class="block text-gray-400">Email</label>
			<input v-model="email" type="email" class="w-full p-2 bg-gray-700 rounded" required />
		</div>

		<div>
			<label class="block text-gray-400">Password</label>
			<input v-model="password" type="password" class="w-full p-2 bg-gray-700 rounded" required />
		</div>

		<button @click="register" class="w-full bg-green-500 hover:bg-green-600 py-2 rounded">
			Sign up
		</button>
	</form>
</template>

<script setup>
import { ref } from "vue";
import AuthService from "@/api/AuthApi";
import router from "@/router/index.js";

const email = ref("");
const password = ref("");

const register = async () => {
	await AuthService.register({ username: email.value, password: password.value });
	await router.push("/")
};
</script>
  