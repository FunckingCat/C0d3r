<template>
    <div @click="handleLogout"
        class="btn btn-soft btn-primary">
            Log Out
    </div>
</template>

<script setup>
import { logOut } from '@/scripts/logout'

async function handleLogout() {
    await logOut()
}

</script>