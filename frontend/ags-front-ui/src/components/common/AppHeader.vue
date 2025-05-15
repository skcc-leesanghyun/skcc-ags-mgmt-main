<template>
  <header class="app-header">
    <div class="logo">AGS 운영관리 시스템</div>
    <button class="lang-toggle" @click="toggleLanguage">
      {{ currentLanguage === 'ko' ? 'EN' : '한글' }}
    </button>
  </header>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useI18n } from 'vue-i18n'

const { locale } = useI18n()

const currentLanguage = computed(() => locale.value)

const toggleLanguage = () => {
  locale.value = locale.value === 'ko' ? 'en' : 'ko'
}
</script>

<style lang="scss" scoped>
.app-header {
  height: 60px;
  background: #0052CC;
  color: white;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);

  .logo {
    font-size: 1.2em;
    font-weight: 500;
  }

  .lang-toggle {
    background: transparent;
    border: 1px solid white;
    color: white;
    padding: 4px 12px;
    border-radius: 4px;
    cursor: pointer;
    transition: all 0.2s;

    &:hover {
      background: rgba(255, 255, 255, 0.1);
    }
  }
}
</style> 