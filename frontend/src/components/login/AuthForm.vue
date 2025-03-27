<template>
	<div class="w-full bg-base-300 p-8 rounded-lg shadow-lg">
		<h2 class="text-2xl font-bold mb-4 text-center">
			{{ getWelcomeMessage(authFormState) }}
		</h2>

		<p class="text-gray-400 text-center mb-6">
			{{ getWelcomeMessageDescription(authFormState) }}
		</p>

		<!-- Show relevant form -->
		<LoginForm v-if="authFormState == AuthFormState.LOGIN" />
		<RegisterForm v-if="authFormState == AuthFormState.REG" />
		<ResetPasswordForm v-if="authFormState == AuthFormState.RESET" />

		<!-- Toggle between Login and Register -->
		<AuthSwitch/>

	</div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import AuthSwitch from "@/components/login/AuthSwitch.vue";
import LoginForm from "@/components/login/LoginForm.vue";
import RegisterForm from "@/components/login/RegisterForm.vue";
import { AuthFormState, useAuthStore } from '@/stores/authStore';
import { storeToRefs } from 'pinia';
import ResetPasswordForm from "./ResetPasswordForm.vue";

var authStore = useAuthStore()
var { authFormState } = storeToRefs(authStore)

function getWelcomeMessage(state: AuthFormState): string {
    switch (state) {
        case AuthFormState.LOGIN:
            return "Welcome back!";
        case AuthFormState.REG:
            return "Join us!";
        case AuthFormState.RESET:
            return "Reset your password and regain access.";
        default:
            return "Welcome!";
    }
}

function getWelcomeMessageDescription(state: AuthFormState): string {
    switch (state) {
        case AuthFormState.LOGIN:
            return "Log in to your Lambda account.";
        case AuthFormState.REG:
            return "Sign up for an account to get started.";
        case AuthFormState.RESET:
            return "";
        default:
            return "Welcome!";
    }
}

</script>
  