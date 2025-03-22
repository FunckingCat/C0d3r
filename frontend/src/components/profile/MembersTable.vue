<template>
    <div class="w-full mt-6">
      <h2 class="text-xl font-bold ml-4">Manage group members permissions</h2>
      <table class="table w-full">
        <thead>
          <tr>
            <th>Username</th>
            <th>Permissions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="member in activeGroupDescription?.members" :key="member.id">
            <td class="text-md">{{ member.username }}</td>
            <td class="flex justify-between">
              <div 
                v-for="permission in permissionsToStringList()" :key="permission" class="flex">
                <Toggle
                  :modelValue="userPermissionContains(member, permission)"
                  :checkboxDisabled="checboxDisabled(member, permission)"
                  @change="(state: boolean) => toggleFeature(member.id, permission, state)"
                  class="mr-3"/>
                <div class="text-md">
                  {{ permission }}
                </div>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </template>
  
<script setup lang="ts">
import { useAuthStore } from '@/stores/authStore';
import { storeToRefs } from 'pinia';
import { Permission, type Member, type PermissionRequest, ActionType } from '@/types/ApiTypes';
import Toggle from '../Toggle.vue'
import roleModelApi from '@/api/RoleModelApi';

const userStore = useAuthStore()
const { authorized, loading, user, activeGroup, activeGroupDescription } = storeToRefs(userStore)

const checboxDisabled = (member: Member, permission: string) => {

  //Disable self admin manipulating
  if (user.value?.id == member.id && permission == 'ADMIN') {
      return true;
  }

  if (userPermissionContains(member, "ADMIN")) {
    return false
  }

  return true
}

const toggleFeature = async (id: string, permission: string, state: boolean) => {

    if (user.value?.id == id && permission == 'ADMIN') {
      console.log("Cant self REVOKE or SELF grant ADMIN")
      return;
    }

    var request: PermissionRequest = {
      action: state ? ActionType.GRANT : ActionType.REVOKE,
      memberId: id,
      groupId: activeGroup.value as string, // Using non-null assertion assuming it's always present
      permission: permission
    }
    console.log("Permission change", id, permission, state, request)
    var result = await roleModelApi.changePermission(request)
    console.log("Permission change reslut", result)
};

const permissionsToStringList = () => {
  return Object.keys(Permission).filter(key => isNaN(Number(key)))
}

const userPermissionContains = (member: Member, permission: string) => {
    var permissions = member.permissions.map(status => status.toString())
    // console.log(permissions, permission, permissions.includes(permission))
    return permissions.includes(permission)
}

</script>