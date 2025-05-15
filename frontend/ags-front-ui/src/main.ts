import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
import i18n from './i18n'

// 스타일시트
import './assets/main.css'

const app = createApp(App)

// Pinia 설정
app.use(createPinia())

// Vue Router 설정
app.use(router)

// i18n 설정
app.use(i18n)

// 앱 마운트
app.mount('#app') 