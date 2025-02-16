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

		<div class="flex justify-between items-center">
			<a href="#" class="text-primary text-sm hover:underline">Forgot password?</a>
		</div>

		<button @click="login" class="w-full btn btn-soft btn-primary">
			Log in
		</button>
	</div>
</template>

<script setup>
import { ref } from "vue";
import AuthService from "@/api/AuthApi";
import router from "@/router/index.js";
import { logInIfTokenPresent } from "@/scripts/login";

const email = ref("a@mail.ru");
const password = ref("password");
const rememberMe = ref(false);

const login = async () => {
	await AuthService.login({ username: email.value, password: password.value });
	await logInIfTokenPresent()
	await router.push("/")
};

</script>
  