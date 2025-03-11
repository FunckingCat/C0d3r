<template>
  <div class="modal" :class="{ 'modal-open': executionModelOpened }">
    <div class="modal-box">
      <h3 class="font-bold text-lg">Execution Logs</h3>
        <div class="h-64 overflow-y-auto bg-gray-800 text-white p-2 rounded">
            {{ task?.executionResults?.filter(res => res.id == activeExecutionId)[0]?.logs?.join("\n") }}
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