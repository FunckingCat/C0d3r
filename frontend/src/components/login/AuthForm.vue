<template>
	<div class="w-full bg-base-300 p-8 rounded-lg shadow-lg">
		<h2 class="text-2xl font-bold mb-4 text-center">
			{{ isLogin ? "Welcome back" : "Create an Account" }}
		</h2>

		<p class="text-gray-400 text-center mb-6">
			{{ isLogin ? "Log in to your Lambda account." : "Sign up for an account to get started." }}
		</p>

		<!-- Show relevant form -->
		<LoginForm v-if="isLogin" />
		<RegisterForm v-else />

		<!-- Toggle between Login and Register -->
		<AuthSwitch v-model="isLogin" />

	</div>
</template>

<script setup>
import { ref } from "vue";
import AuthSwitch from "@/components/login/AuthSwitch.vue";
import LoginForm from "@/components/login/LoginForm.vue";
import RegisterForm from "@/components/login/RegisterForm.vue";

const isLogin = ref(true);
</script>
  