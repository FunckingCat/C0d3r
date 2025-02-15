// @ts-ignore
import TasksView from '@/views/TasksView.vue'
// @ts-ignore
import LoginView from '@/views/LoginView.vue'
// @ts-ignore
import TaskView from "@/views/TaskView.vue";
import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from "@/stores/authStore.ts";
import { storeToRefs } from 'pinia';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: LoginView,
    },
    {
      path: '/tasks',
      name: 'tasksDashboard',
      component: TasksView,
      meta: {
        requiresAuth: true
      },
    },
    {
      path: '/',
      redirect: '/tasks',
      meta: {
        requiresAuth: true
      },
    },
    {
      path: '/task/:id',
      name: 'task',
      component: TaskView,
      meta: {
        requiresAuth: true
      },
    },
  ],
})

router.beforeEach((to, from, next) => {
  const authStore = useAuthStore()
  const { authorized } = storeToRefs(authStore)
  console.log(`Guard: ${authorized.value}`)

  if (to.meta.requiresAuth && !authorized.value) {
    next('/login')
  } else {
    next()
  }
})

export default router
