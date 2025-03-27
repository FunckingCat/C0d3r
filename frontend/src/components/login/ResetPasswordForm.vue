<template>
	<div class="space-y-4">
		<div>
			<label class="block text-gray-400">Email</label>
			<input v-model="email" type="email" class="w-full p-2 bg-gray-700 rounded" required />
		</div>

		<div>
			<label class="block text-gray-400">New password</label>
			<input v-model="password" type="password" class="w-full p-2 bg-gray-700 rounded" required />
		</div>

		<button @click="resetPassword" class="w-full btn btn-soft btn-primary py-2 rounded mt-10">
			Reset password
		</button>
	</div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import AuthService from "@/api/AuthApi";
import router from "@/router/index.js";
import { logInIfTokenPresent } from "@/scripts/login";
import { AuthFormState, useAuthStore } from '@/stores/authStore';
import { storeToRefs } from 'pinia';
import ResetPasswordForm from "./ResetPasswordForm.vue";

var authStore = useAuthStore()
var { authFormState } = storeToRefs(authStore)
const email = ref("");
const password = ref("");

const resetPassword = async () => {
	await AuthService.ressetPassword({ username: email.value, password: password.value });
	authStore.setAuthFromState(AuthFormState.LOGIN);
};
</script>
  