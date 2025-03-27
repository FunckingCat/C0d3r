<template>
	<div v-if="authFormState != AuthFormState.RESET" class="flex justify-center mt-4">
		<button 
			@click="() => { setState(authFormState == AuthFormState.LOGIN ? AuthFormState.REG : AuthFormState.LOGIN) }" class="text-primary underline hover:underline text-sm">
			{{ authFormState == AuthFormState.LOGIN ? "Need an account? Sign up" : "Already have an account? Log in" }}
		</button>
	</div>
	<div v-if="authFormState == AuthFormState.LOGIN" class="flex justify-center mt-4">
		<button @click="() => { setState(AuthFormState.RESET) }" class="text-primary underline hover:underline text-sm">
			Forgot password?
		</button>
	</div>
	<div v-if="authFormState == AuthFormState.RESET" class="flex justify-center mt-4">
		<button @click="() => { setState(AuthFormState.LOGIN) }" class="text-primary underline hover:underline text-sm">
			Go back
		</button>
	</div>
</template>

<script setup lang="ts">
import { AuthFormState, useAuthStore } from '@/stores/authStore';
import { storeToRefs } from 'pinia';

var authStore = useAuthStore()
var { authFormState } = storeToRefs(authStore)

var setState = (state: AuthFormState) => {
	console.log(`Change form state from ${authFormState.value} to ${state}`)
	authStore.setAuthFromState(state)
}
</script>