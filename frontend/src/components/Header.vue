<template>
    <header class="bg-gray-900 text-white shadow-md">
        <div class="container mx-auto flex items-center justify-between py-4 px-6">
            <!-- Left: Logo & Navigation Links -->
            <nav class="flex items-center space-x-6">
                <RouterLink to="/" class="text-xl font-bold text-blue-400 hover:text-blue-300">
                    Lyambda
                </RouterLink>
                <RouterLink to="/dashboard" class="nav-link">Dashboard</RouterLink>
                <RouterLink to="/tasks" class="nav-link">Tasks</RouterLink>
                <RouterLink to="/teams" class="nav-link">Teams</RouterLink>
                <RouterLink to="/docs" class="nav-link">Documentation</RouterLink>
            </nav>

            <!-- Right: New Task Button & Invite -->
            <div class="flex items-center space-x-4">
                <RouterLink to="/tasks/new" class="btn-primary">New Task</RouterLink>
                <RouterLink to="/invite" class="btn-secondary">Invite</RouterLink>
            </div>
        </div>
    </header>
</template>

<script setup>
import { RouterLink } from 'vue-router';
</script>