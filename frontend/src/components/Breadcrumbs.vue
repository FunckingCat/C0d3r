<template>
    <div class="breadcrumbs text-sm w-full max-w-5xl mb-4">
      <ul>
        <li v-for="(crumb, index) in breadcrumbs" :key="index">
          <RouterLink v-if="crumb.path" :to="crumb.path">{{ crumb.name }}</RouterLink>
          <span v-else>{{ crumb.name }}</span>
        </li>
      </ul>
    </div>
  </template>
  
  <script setup lang="ts">
  import { computed } from 'vue';
  import { useRoute, RouterLink } from 'vue-router';
  
  const route = useRoute();
  console.log("Route", route.meta, route.matched)
  
  const breadcrumbs = computed(() => {
    const matchedRoutes = route.matched;
    return matchedRoutes.map((route) => ({
      name: route.name || 'Unnamed',
      path: route.path,
    }));
  });
  </script>
