<template>
    <div class="breadcrumbs text-sm w-full max-w-5xl mb-4">
      <ul>
        <li v-for="(crumb, index) in crumbs" :key="index">
          <RouterLink :to="crumb.link">{{ crumb.name }}</RouterLink>
        </li>
      </ul>
    </div>
  </template>
  
  <script setup lang="ts">
  import { computed } from 'vue';
  import { useRoute, RouterLink } from 'vue-router';
  import { useBreadCrumbStore } from '@/stores/breadCrumbsStore';
  import { storeToRefs } from 'pinia';
  
  const breadCrumbsStore = useBreadCrumbStore()
  const { crumbs } = storeToRefs(breadCrumbsStore)
  
  </script>
