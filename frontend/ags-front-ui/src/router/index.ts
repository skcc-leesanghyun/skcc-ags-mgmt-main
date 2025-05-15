import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import('@/views/Home.vue'),
  },
  {
    path: '/talent',
    name: 'TalentSourcing',
    component: () => import('@/views/talent/TalentSourcing.vue'),
  },
  {
    path: '/audit',
    name: 'Audit',
    component: () => import('@/views/audit/Audit.vue'),
  },
  {
    path: '/planning',
    name: 'Planning',
    component: () => import('@/views/planning/Planning.vue'),
  },
  {
    path: '/knowledge',
    name: 'Knowledge',
    component: () => import('@/views/knowledge/Knowledge.vue'),
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router 