<template>
    <div @click="handleLogout"
        class="flex items-center justify-between py-2 hover:bg-gray-50 transition-all duration-200 rounded-lg hover:shadow-md cursor-pointer group">
        <div class="flex items-center space-x-3">
            <span class="font-medium text-lg text-gray-700 group-hover:text-gray-900">
                Log Out
            </span>
            <svg class="w-5 h-5 text-gray-400 group-hover:text-gray-600 transition-colors transform group-hover:scale-110 transition-transform"
                fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                    d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1" />
            </svg>
        </div>
    </div>
</template>

<script setup>
import { logOut } from '@/scripts/logout'

async function handleLogout() {
    await logOut()
}

</script>