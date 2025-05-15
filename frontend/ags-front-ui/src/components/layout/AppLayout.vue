<template>
  <div class="app-layout">
    <AppHeader />
    <div class="main-container">
      <AppSidebar />
      <main class="content">
        <slot />
      </main>
    </div>
  </div>
</template>

<script setup lang="ts">
import AppHeader from '@/components/common/AppHeader.vue'
import AppSidebar from '@/components/common/AppSidebar.vue'
</script>

<style lang="scss" scoped>
.app-layout {
  display: flex;
  flex-direction: column;
  height: 100%;

  .main-container {
    display: flex;
    flex: 1;
    overflow: hidden;

    .content {
      flex: 1;
      padding: 20px;
      overflow-y: auto;
    }
  }
}
</style> 