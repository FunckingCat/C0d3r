<template>
    <div
      class="fixed bottom-4 right-4 z-50 flex flex-col items-end space-y-3 w-sm"
    >
      <TransitionGroup
        name="alert-fade"
        tag="div"
        class="w-full flex flex-col items-end space-y-3"
      >
        <div
          v-for="alert in alerts"
          :key="alert.id"
          :class="[
            'alert', 
            'alert-soft',
            alertLevelClasses[alert.level], 
            'shadow-lg', 
            'w-full',    
            'flex',      
            'items-start', 
          ]"
          role="alert"
        >
          <div class="flex-shrink-0 pt-0.5">
            <svg v-if="alert.level === 'success'" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" class="stroke-current h-6 w-6"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>
              <svg v-if="alert.level === 'info'" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" class="stroke-current h-6 w-6"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>
              <svg v-if="alert.level === 'warn'" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" class="stroke-current h-6 w-6"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z"></path></svg>
              <svg v-if="alert.level === 'error'" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" class="stroke-current h-6 w-6"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>
          </div>
  
          <div class="flex-grow">
            <h3 class="font-bold">{{ alert.title }}</h3>
            <div v-if="alert.text != undefined" class="text-sm">{{ alert.text }}</div>
          </div>
  
          <div class="flex-shrink-0">
              <button
                  class="btn btn-sm btn-circle btn-ghost"
                  @click="alertStore.removeAlert(alert.id)"
                  aria-label="Close alert"
              >
                  ✕
              </button>
          </div>
        </div>
      </TransitionGroup>
    </div>
  </template>
  
  <script setup lang="ts">
  import { computed } from 'vue';
  import { useAlertStore, type AlertLevel } from '@/stores/alertStore'; 
  import { storeToRefs } from 'pinia';
  
  const alertStore = useAlertStore();
  const { alerts } = storeToRefs(alertStore);
  
  const alertLevelClasses: Record<AlertLevel, string> = {
    success: 'alert-success',
    info: 'alert-info',
    warn: 'alert-warning',
    error: 'alert-error',
  };
  
  </script>
  
  <style scoped>  
  .alert-fade-enter-active {
    transition: all 0.3s ease-out;
  }
  .alert-fade-enter-from {
    opacity: 0;
    transform: translateX(30px);
  }
  .alert-fade-enter-to {
    opacity: 1;
    transform: translateX(0);
  }
  
  .alert-fade-leave-active {
    transition: all 0.3s ease-in;
    position: absolute;
    width: calc(100% - theme('spacing.4')); 
    right: theme('spacing.4');
  }
  .alert-fade-leave-from {
    opacity: 1;
    transform: translateX(0);
  }
  .alert-fade-leave-to {
    opacity: 0;
    transform: translateX(30px); /* Fade out and slide right */
  }
  
  /* Ensure moving items transition smoothly */
  .alert-fade-move {
    transition: transform 0.3s ease;
  }
  </style>