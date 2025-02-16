<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
// @ts-ignore
import Header from '@/components/Header.vue';
import { onBeforeMount } from 'vue';
import { logInIfTokenPresent } from './scripts/login';

onBeforeMount(async () => {
  await logInIfTokenPresent()
});

</script>

<template>
  <div class="bg-base-100 text-white shadow-md">
    <Header />
    <RouterView />
  </div>
</template>