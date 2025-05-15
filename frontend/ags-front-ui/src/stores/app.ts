import { defineStore } from 'pinia'

export const useAppStore = defineStore('app', {
  state: () => ({
    language: 'ko' as 'ko' | 'en',
    isSidebarCollapsed: false
  }),
  
  actions: {
    toggleLanguage() {
      this.language = this.language === 'ko' ? 'en' : 'ko'
    },
    
    toggleSidebar() {
      this.isSidebarCollapsed = !this.isSidebarCollapsed
    }
  }
}) 