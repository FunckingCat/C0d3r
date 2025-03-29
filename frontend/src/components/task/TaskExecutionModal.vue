<template>
  <div class="modal" :class="{ 'modal-open': executionModelOpened }">
    <div class="modal-box w-11/12 max-w-4xl"> 
      <div class="mb-4"> 
        <h3 class="font-bold text-lg">Execution Logs</h3>
      </div>

      <div class="mockup-code max-h-96 overflow-auto">
        <pre
          v-if="logsToShow && logsToShow.length > 0"
          v-for="(log, index) in logsToShow"
          :key="index"
          :data-prefix="index + 1"  
          class="text-xs sm:text-sm" 
        ><code>{{ log }}</code></pre>
         <pre v-else data-prefix="!" class="text-warning"><code>No logs available for this execution.</code></pre>
      </div>

      <div class="modal-action mt-4"> 
        <button class="btn btn-soft btn-primary" @click="toggleModal">
          Close
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { computed } from 'vue';
import { UseCurrentTaskStore } from '@/stores/currentTaskStore';
import { storeToRefs } from 'pinia';

const currentTaskStore = UseCurrentTaskStore()
const { executionModelOpened, activeExecutionId, task } = storeToRefs(currentTaskStore)

const logsToShow = computed(() => {
  const executionResult = task.value?.executionResults?.find(res => res.id === activeExecutionId.value);
  return executionResult?.logs ?? [];
})

function toggleModal(): void {
  currentTaskStore.setModelNotActive()
}
</script>

<style scoped>
.mockup-code::-webkit-scrollbar {
  width: 8px;
  height: 8px;
}

.mockup-code::-webkit-scrollbar-track {
  background: hsl(var(--b2, var(--b1)) / var(--tw-bg-opacity, 1));
  border-radius: 4px;
}

.mockup-code::-webkit-scrollbar-thumb {
  background: hsl(var(--b3, var(--b1)) / var(--tw-bg-opacity, 1)); 
  border-radius: 4px;
}

.mockup-code::-webkit-scrollbar-thumb:hover {
  background: hsl(var(--bc) / 0.5); 
}

.mockup-code pre {
  white-space: pre;
  word-break: keep-all;
  overflow-wrap: normal;
}
</style>