import TasksView from '@/views/TasksView.vue'
import LoginView from '@/views/LoginView.vue'
import TaskView from "@/views/TaskView.vue";
import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from "@/stores/authStore.ts";
import { storeToRefs } from 'pinia';
import CreateJobView from '@/views/CreateJobView.vue';

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: LoginView,
      meta: {
        breadcrumbsEnabled: false
      }
    },
    {
      path: '/tasks',
      name: 'Jobs Dashboard',
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
      path: '/tasks/:id',
      name: 'Task',
      component: TaskView,
      meta: {
        requiresAuth: true
      },
    },
    {
      path: '/new-job',
      name: 'New Job',
      component: CreateJobView,
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
