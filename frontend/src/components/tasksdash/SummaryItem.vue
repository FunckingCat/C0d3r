<template>
    <div class="border border-primary px-8 py-6 rounded-lg">
      <p class="text-md font-semibold">{{ title }}</p>
      <p class="text-3xl">{{ value }}</p>
    </div>
  </template>
  
  <script setup>
  defineProps({
    title: {
      type: String,
      required: true
    },
    value: {
      type: [Number, String],
      required: true
    }
  });
  </script>