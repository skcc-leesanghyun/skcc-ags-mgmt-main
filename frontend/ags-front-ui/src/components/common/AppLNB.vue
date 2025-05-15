<template>
  <nav class="app-lnb">
    <div class="menu-section">
      <h3>{{ $t('menu.talent.title') }}</h3>
      <ul>
        <li>
          <router-link to="/talent/sourcing">{{ $t('menu.talent.sourcing') }}</router-link>
        </li>
        <li>
          <router-link to="/talent/evaluation">{{ $t('menu.talent.evaluation') }}</router-link>
        </li>
      </ul>
    </div>

    <div class="menu-section">
      <h3>{{ $t('menu.audit.title') }}</h3>
      <ul>
        <li>
          <router-link to="/audit/onboarding">{{ $t('menu.audit.onboarding') }}</router-link>
        </li>
        <li>
          <router-link to="/audit/offboarding">{{ $t('menu.audit.offboarding') }}</router-link>
        </li>
      </ul>
    </div>

    <div class="menu-section">
      <h3>{{ $t('menu.planning.title') }}</h3>
      <ul>
        <li>
          <router-link to="/planning/workforce">{{ $t('menu.planning.workforce') }}</router-link>
        </li>
        <li>
          <router-link to="/planning/performance">{{ $t('menu.planning.performance') }}</router-link>
        </li>
      </ul>
    </div>

    <div class="menu-section">
      <h3>{{ $t('menu.knowledge.title') }}</h3>
      <ul>
        <li>
          <router-link to="/knowledge/guide">{{ $t('menu.knowledge.guide') }}</router-link>
        </li>
        <li>
          <router-link to="/knowledge/qa">{{ $t('menu.knowledge.qa') }}</router-link>
        </li>
        <li>
          <router-link to="/knowledge/lesson">{{ $t('menu.knowledge.lesson') }}</router-link>
        </li>
      </ul>
    </div>
  </nav>
</template>

<script setup lang="ts">
import { useI18n } from 'vue-i18n'

const { t } = useI18n()
</script>

<style lang="scss" scoped>
.app-lnb {
  width: 240px;
  height: 100%;
  background-color: $secondary-color;
  color: $highlight-color;
  padding: $spacing-lg;

  .menu-section {
    margin-bottom: $spacing-xl;

    h3 {
      font-size: $font-size-md;
      font-weight: $font-weight-bold;
      margin-bottom: $spacing-md;
      color: $accent-color;
    }

    ul {
      list-style: none;
      padding: 0;
      margin: 0;

      li {
        margin-bottom: $spacing-sm;

        a {
          display: block;
          padding: $spacing-sm;
          color: $highlight-color;
          text-decoration: none;
          border-radius: $border-radius-sm;
          transition: background-color $transition-fast;

          &:hover {
            background-color: rgba($highlight-color, 0.1);
          }

          &.router-link-active {
            background-color: $primary-color;
            color: $highlight-color;
          }
        }
      }
    }
  }
}
</style> 