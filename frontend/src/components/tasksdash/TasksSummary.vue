<template>
	<div class="grid grid-cols-1 md:grid-cols-4 gap-4 mb-6">
	  <SummaryItem
		v-for="item in summaryItems"
		:key="item.title"
		:title="item.title"
		:value="summary[item.key]"
	  />
	</div>
  </template>
  
  <script setup>
  import { useTasksStore } from '@/stores/tasksStore';
  import { storeToRefs } from 'pinia';
  import SummaryItem from './SummaryItem.vue';
  
  const taskStore = useTasksStore()
  const { summary } = storeToRefs(taskStore)
  
  const summaryItems = [
	{ title: 'Total Tasks', key: 'total' },
	{ title: 'In Progress', key: 'inProgress' },
	{ title: 'Completed', key: 'completed' },
	{ title: 'Failed', key: 'failed' }
  ]
  </script>