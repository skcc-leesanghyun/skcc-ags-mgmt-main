<template>
  <div class="app">
    <AppLayout>
      <router-view />
    </AppLayout>
  </div>
</template>

<script setup lang="ts">
import AppLayout from '@/components/layout/AppLayout.vue'
</script>

<style lang="scss">
.app {
  width: 100%;
  height: 100vh;
}
</style> 