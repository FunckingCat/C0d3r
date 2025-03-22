<template>
  <div class="modal" :class="{ 'modal-open': executionModelOpened }">
    <div class="modal-box">
      <div class="mockup-code w-full">
        <h3 class="ml-12 font-bold text-lg">Execution Logs</h3>
        <pre data-prefix="~" v-for="log in task?.executionResults?.find(res => res.id === activeExecutionId)?.logs" :key="log"><code>{{ log }}</code></pre>
      </div>

      <div class="modal-action">
        <button class="btn" @click="toggleModal">
          Close modal
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { UseCurrentTaskStore } from '@/stores/currentTaskStore';
import { storeToRefs } from 'pinia';

const currentTaskStore = UseCurrentTaskStore()
const { executionModelOpened, activeExecutionId, task } = storeToRefs(currentTaskStore)

function toggleModal(): void {
  currentTaskStore.setModelNotActive()
}
</script>