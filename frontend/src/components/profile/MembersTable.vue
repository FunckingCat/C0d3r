<template>
    <div class="w-full mt-6">
      <h2 class="text-xl font-bold ml-4">{{ isGroupAdmin ? "Manage" : "View" }} group members permissions</h2>
      <table class="table w-full">
        <thead>
          <tr>
            <th>Username</th>
            <th>Permissions</th>
            <th v-if="isGroupAdmin"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="tableRow in tableRows" :key="tableRow.userId">
            <td class="text-md">{{ tableRow.userName }}</td>
            <td class="flex justify-between mt-2">
              <div 
                v-for="checkbox in tableRow.checkboxes" :key="checkbox.label" class="flex items-center">
                <Toggle
                  :checked="checkbox.checked"
                  :checkboxDisabled="checkbox.disabled"
                  @change="(state: boolean) => toggleFeature(tableRow.userId, checkbox.label, state)"
                  class="mr-3"/>
                <div class="text-md">
                  {{ checkbox.label }}
                </div>
              </div>
            </td>
            <td v-if="isGroupAdmin">
              <div 
                v-if="tableRow.userId != user?.id" 
                class="btn btn-secondary" 
                @click = "() => { excludeUser(tableRow.userId) }">
                  Exclude
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
import { computed, type ComputedRef } from 'vue';

const userStore = useAuthStore()
const { user, activeGroup, activeGroupDescription } = storeToRefs(userStore)

interface ITableRow {
  key: string;
  userId: string;
  userName: string;
  checkboxes: ICheckboxState[]
}

interface ICheckboxState {
  key: string;
  label: string;
  checked: boolean;
  disabled: boolean;
}

const tableRows = computed(() => {
  if (activeGroupDescription.value === undefined) return []

  const rows: ITableRow[] = activeGroupDescription.value?.members.map(member => {
        const userPermissionSet = member.permissions;
        const checkboxStates: ICheckboxState[] = permissionsToStringList()
          .map(permissionKey => {
              const isChecked = userPermissionContains(userPermissionSet, permissionKey);
              const isDisabled = checboxDisabled(member, permissionKey);
              return {
                  key: `${activeGroupDescription.value?.name}-${member.id}-${permissionKey}`,
                  label: permissionKey,
                  checked: isChecked,
                  disabled: isDisabled,
              };
          });

        return {
            key: `${activeGroupDescription.value?.name}-${member.id}`,
            userId: member.id,
            userName: member.username,
            checkboxes: checkboxStates,
        };
    });

    console.log("Table rows", rows)

    return rows;
})

const checboxDisabled = (member: Member, permission: string) => {

  //Disable self admin manipulating
  if (user.value?.id == member.id && permission == 'ADMIN') {
      return true;
  }

  var userPermissionsList = user.value?.groups.filter(group => group.id == activeGroup.value)[0].permissions

  if (userPermissionContains(userPermissionsList as Permission[], "ADMIN")) {
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
      groupId: activeGroupDescription.value?.id as string,
      permission: permission
    }
    console.log("Permission change", id, permission, state, request)
    var result = await roleModelApi.changePermission(request)
    console.log("Permission change reslut", result)
};

const permissionsToStringList = () => {
  return Object.keys(Permission).filter(key => isNaN(Number(key)))
}

const userPermissionContains = (permissions: Permission[], permission: string) => {
    var permissionsList = permissions.map(status => status.toString())
    // console.log(permissions, permission, permissions.includes(permission))
    return permissionsList.includes(permission)
}

const excludeUser = async (userId: string) => {
    console.log(`Exclude user ${userId} from group ${activeGroup}`)
    await roleModelApi.excludeMember({
      memberId: userId,
      groupId: activeGroup.value as string
    })
} 

const isGroupAdmin = computed(
  () => {

    console.log("isGroupAdmin", activeGroup)

    if (activeGroup.value == undefined) {
      console.log("No active group selected")
      return false
    }

    var userPermissionsInGroup = user.value?.groups.filter(g => g.id == activeGroup.value)[0].permissions
    var isAdmin = userPermissionContains(userPermissionsInGroup as Permission[], "ADMIN")
    console.log(`User ${user.value?.username} isAdmin = ${isAdmin} in group ${activeGroupDescription.value?.name}`)
    return isAdmin
  }
)

</script>