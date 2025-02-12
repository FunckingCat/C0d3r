<template>
    <div class="flex h-screen bg-gray-900 text-white">
        <!-- Left Side: Forms -->
        <div class="w-1/2 flex flex-col justify-center items-center">
            <AuthForm />
        </div>

        <!-- Right Side: Logo Section -->
        <div class="w-1/2 flex justify-center items-center">
            <LogoSection />
        </div>
    </div>
</template>

<script setup>
import AuthForm from '@/components/login/AuthForm.vue';
import LogoSection from '@/components/login/LogoSection.vue';
</script>