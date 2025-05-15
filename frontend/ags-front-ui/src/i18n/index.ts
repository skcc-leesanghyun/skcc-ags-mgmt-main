import { createI18n } from 'vue-i18n'
import ko from './locales/ko'
import en from './locales/en'

const i18n = createI18n({
  legacy: false,
  locale: 'ko',
  fallbackLocale: 'en',
  messages: {
    ko,
    en
  }
})

export default i18n 