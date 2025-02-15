<template>
	<div class="flex justify-center mt-4">
		<button @click="flip" class="text-blue-500 hover:underline text-sm">
			{{ modelValue ? "Need an account? Sign up" : "Already have an account? Log in" }}
		</button>
	</div>
</template>

<script setup>

const props = defineProps(["modelValue"]);
const emit = defineEmits(["update:modelValue"]);

const flip = () => {
	emit("update:modelValue", !props.modelValue);
};
</script>