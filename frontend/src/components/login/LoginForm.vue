<template>
    <form @submit.prevent="login" class="space-y-4">
      <div>
        <label class="block text-gray-400">Email</label>
        <input v-model="email" type="email" class="w-full p-2 bg-gray-700 rounded" required />
      </div>
  
      <div>
        <label class="block text-gray-400">Password</label>
        <input v-model="password" type="password" class="w-full p-2 bg-gray-700 rounded" required />
      </div>
  
      <div class="flex justify-between items-center">
        <label class="flex items-center text-gray-400">
          <input v-model="rememberMe" type="checkbox" class="mr-2" />
          Remember this device
        </label>
  
        <a href="#" class="text-blue-400 text-sm hover:underline">Forgot password?</a>
      </div>
  
      <button type="submit" class="w-full bg-blue-500 hover:bg-blue-600 py-2 rounded">
        Log in
      </button>
    </form>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  import AuthService from '@/service/AuthService';
  
  const email = ref('');
  const password = ref('');
  const rememberMe = ref(false);
  
  const login = async () => {
    await AuthService.login({ username: email.value, password: password.value });
  };
  </script>
  