<template>
    <div class="flex flex-1 w-full justify-center">
        <!-- Left Side: Forms -->
        <div class="flex flex-1 max-w-md justify-between items-center">
            <AuthForm />
        </div>

        <!-- Right Side: Logo Section -->
        <div class="hidden flex-none justify-center items-center sm:flex ml-10">
            <LogoSection />
        </div>
    </div>
</template>

<script setup>
import AuthForm from '@/components/login/AuthForm.vue';
import LogoSection from '@/components/login/LogoSection.vue';
</script>