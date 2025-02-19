<script setup lang="ts">
import { RouterLink, RouterView, useRoute } from 'vue-router'
import Header from '@/components/Header.vue';
import { computed, onBeforeMount } from 'vue';
import { logInIfTokenPresent } from './scripts/login';
import Breadcrumbs from './components/Breadcrumbs.vue';

onBeforeMount(async () => {
  await logInIfTokenPresent()
});

</script>

<template>
  <div class="bg-base-100 text-white shadow-md h-screen flex flex-col items-center">
    <Header />
    <RouterView />
  </div>
</template>