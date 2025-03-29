<template>
  <div class="flex flex-col items-start p-4">

    <div class="my-2 flex items-end w-full justify-between">
      <div class="flex-1">
        <label class="block text-sm font-medium text-gray-700">Create New Group:</label>
        <input 
          type="text" 
          placeholder="Enter new group name" 
          class="input input-bordered w-full mt-2" 
          v-model="newGroupName"
        />
      </div>
      <button 
        class="btn btn-accent ml-6 w-35" 
        @click="createGroup"
      >
        Create Group
      </button>
    </div>

    <div class="my-2 flex items-end w-full justify-between">
      <div class="flex-1">
        <label class="block text-sm font-medium text-gray-700">Join Group Token:</label>
        <input 
          type="text" 
          placeholder="Enter your join token" 
          class="input input-bordered w-full mt-2" 
          v-model="joinToken"
        />
      </div>
      <button class="btn btn-primary ml-6 w-35" @click="joinGroup">Join Group</button>
    </div>

    <div class="my-2 flex items-end w-full justify-between">
      <div class="flex-1">
        <label class="block text-sm font-medium text-gray-700">Select Group:</label>
        <select 
          class="select select-bordered w-full mt-2" 
          v-model="selectedGroup"
        >
          <option disabled value="">Select a group</option>
          <option v-for="group in user?.groups" :key="group.id" :value="group.id">
            {{ group.name }}
          </option>
        </select>
      </div>
      <button class="btn btn-soft btn-success ml-6 w-35" @click="setActive">Set Active</button>
      <button class="btn btn-warning ml-6 w-35" @click="leaveGroup">Leave Group</button>
    </div>
  </div>
</template>
  
<script setup lang="ts">
import roleModelApi from '@/api/RoleModelApi';
import { useAuthStore } from '@/stores/authStore';
import { storeToRefs } from 'pinia';
import { ref } from 'vue';

const userStore = useAuthStore()
const { authorized, loading, user, activeGroup, activeGroupDescription } = storeToRefs(userStore)

const joinToken = ref("")
const newGroupName = ref("")
const selectedGroup = ref("")
const groups: string[] = []

const createGroup = async () => {

  if (newGroupName.value.length < 2) {
    return
  }

  var result = await roleModelApi.createGroup({
    name: newGroupName.value
  })
  userStore.setActiveGroup(result)
  newGroupName.value = ""
  console.log("Create  group result", result)
}

const joinGroup = async () => {

  if (joinToken.value.length < 2) {
    return
  }

  var result = await roleModelApi.joinGroup({
    token: joinToken.value
  })

  joinToken.value = ""

}

const setActive = () => {
    console.log("SET ACTIVE GROUP", selectedGroup.value)
    userStore.setActiveGroup(selectedGroup.value)
}

const leaveGroup = async () => {
  var result = await roleModelApi.leaveGroup(selectedGroup.value)

  if (selectedGroup.value == activeGroup.value) {
    userStore.setActiveGroup(undefined)
    userStore.setActiveGroupDescription(undefined)
  }

  selectedGroup.value = ""
  console.log("Group left")
}
</script>