<template>
	<div class="container space-y-4">
		<div>
			<label class="block text-gray-400">Email</label>
			<input v-model="email" type="email" class="w-full p-2 bg-gray-700 rounded" required />
		</div>

		<div>
			<label class="block text-gray-400">Password</label>
			<input v-model="password" type="password" class="w-full p-2 bg-gray-700 rounded" required />
		</div>

		<button @click="login" class="w-full btn btn-soft btn-primary mt-4">
			Log in
		</button>
	</div>
</template>

<script setup>
import { ref } from "vue";
import AuthService from "@/api/AuthApi";
import { logInIfTokenPresent } from "@/scripts/login";

const email = ref("");
const password = ref("");

const login = async () => {
	await AuthService.login({ username: email.value, password: password.value });
	await logInIfTokenPresent()
};

</script>
  