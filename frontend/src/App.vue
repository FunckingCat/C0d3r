<script setup lang="ts">
import { RouterLink, RouterView, useRoute } from 'vue-router'
import Header from '@/components/Header.vue';
import { computed, onBeforeMount, onMounted } from 'vue';
import { logInIfTokenPresent } from './scripts/login';
import Alert from './components/Alert.vue';

onBeforeMount(async () => {
  await logInIfTokenPresent()
});

</script>

<template>
  <div class="bg-base-100 text-white h-screen flex flex-col items-center">
    <Header />
    <RouterView />
  </div>
  <Alert />
</template>