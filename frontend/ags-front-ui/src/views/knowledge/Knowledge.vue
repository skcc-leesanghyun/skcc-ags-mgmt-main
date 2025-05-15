<template>
  <div class="knowledge">
    <h2>{{ $t('knowledge.title') }}</h2>

    <div class="knowledge-filters">
      <div class="filter-group">
        <label>{{ $t('common.category') }}</label>
        <select>
          <option value="">{{ $t('common.all') }}</option>
          <option value="guide">{{ $t('knowledge.guide.title') }}</option>
          <option value="qa">{{ $t('knowledge.qa.title') }}</option>
          <option value="lesson">{{ $t('knowledge.lesson.title') }}</option>
        </select>
      </div>
      <div class="filter-group">
        <label>{{ $t('common.search') }}</label>
        <input type="text" :placeholder="$t('common.search')" />
      </div>
      <button class="search-button">{{ $t('common.search') }}</button>
    </div>

    <div class="knowledge-content">
      <div class="knowledge-section">
        <div class="section-header">
          <h3>{{ $t('knowledge.guide.title') }}</h3>
          <button class="upload-button">{{ $t('knowledge.guide.upload') }}</button>
        </div>
        <div class="document-list">
          <div class="document-card" v-for="i in 3" :key="`guide-${i}`">
            <div class="document-info">
              <h4>{{ $t('knowledge.guide.title') }} {{ i }}</h4>
              <p class="description">{{ $t('knowledge.guide.description') }}</p>
              <p class="meta">{{ $t('common.author') }}: 홍길동 | {{ $t('common.date') }}: 2024-03-01</p>
            </div>
            <div class="document-actions">
              <button class="action-button view">{{ $t('common.view') }}</button>
              <button class="action-button download">{{ $t('common.download') }}</button>
            </div>
          </div>
        </div>
      </div>

      <div class="knowledge-section">
        <div class="section-header">
          <h3>{{ $t('knowledge.qa.title') }}</h3>
          <button class="upload-button">{{ $t('knowledge.qa.upload') }}</button>
        </div>
        <div class="qa-list">
          <div class="qa-card" v-for="i in 3" :key="`qa-${i}`">
            <div class="qa-info">
              <h4>Q: {{ $t('knowledge.qa.question') }} {{ i }}</h4>
              <p class="answer">A: {{ $t('knowledge.qa.answer') }}</p>
              <p class="meta">{{ $t('common.author') }}: 홍길동 | {{ $t('common.date') }}: 2024-03-01</p>
            </div>
            <div class="qa-actions">
              <button class="action-button view">{{ $t('common.detail') }}</button>
            </div>
          </div>
        </div>
      </div>

      <div class="knowledge-section">
        <div class="section-header">
          <h3>{{ $t('knowledge.lesson.title') }}</h3>
          <button class="upload-button">{{ $t('knowledge.lesson.upload') }}</button>
        </div>
        <div class="lesson-list">
          <div class="lesson-card" v-for="i in 3" :key="`lesson-${i}`">
            <div class="lesson-info">
              <h4>{{ $t('knowledge.lesson.title') }} {{ i }}</h4>
              <p class="description">{{ $t('knowledge.lesson.description') }}</p>
              <p class="meta">{{ $t('common.author') }}: 홍길동 | {{ $t('common.date') }}: 2024-03-01</p>
            </div>
            <div class="lesson-actions">
              <button class="action-button view">{{ $t('common.detail') }}</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useI18n } from 'vue-i18n'

const { t } = useI18n()
</script>

<style lang="scss" scoped>
.knowledge {
  padding: 20px;

  h2 {
    color: #172B4D;
    margin-bottom: 20px;
  }

  .knowledge-filters {
    background: white;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    margin-bottom: 20px;
    display: flex;
    gap: 20px;
    align-items: flex-end;

    .filter-group {
      display: flex;
      flex-direction: column;
      gap: 8px;

      label {
        color: #172B4D;
        font-weight: 500;
      }

      select, input {
        padding: 8px;
        border: 1px solid #DFE1E6;
        border-radius: 4px;
        min-width: 200px;
      }
    }

    .search-button {
      background: #0052CC;
      color: white;
      border: none;
      padding: 8px 20px;
      border-radius: 4px;
      cursor: pointer;
      transition: background-color 0.2s;

      &:hover {
        background: #0747A6;
      }
    }
  }

  .knowledge-content {
    display: grid;
    gap: 20px;

    .knowledge-section {
      background: white;
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);

      .section-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 20px;

        h3 {
          color: #172B4D;
          margin: 0;
        }

        .upload-button {
          background: #0052CC;
          color: white;
          border: none;
          padding: 8px 16px;
          border-radius: 4px;
          cursor: pointer;
          transition: background-color 0.2s;

          &:hover {
            background: #0747A6;
          }
        }
      }

      .document-list, .qa-list, .lesson-list {
        display: grid;
        gap: 15px;

        .document-card, .qa-card, .lesson-card {
          background: #F4F5F7;
          padding: 15px;
          border-radius: 6px;
          display: flex;
          justify-content: space-between;
          align-items: center;

          .document-info, .qa-info, .lesson-info {
            flex: 1;

            h4 {
              color: #172B4D;
              margin: 0 0 8px 0;
            }

            .description, .answer {
              color: #6B778C;
              margin: 0 0 8px 0;
            }

            .meta {
              color: #97A0AF;
              font-size: 0.9em;
              margin: 0;
            }
          }

          .document-actions, .qa-actions, .lesson-actions {
            display: flex;
            gap: 10px;

            .action-button {
              padding: 6px 12px;
              border: none;
              border-radius: 4px;
              cursor: pointer;
              transition: background-color 0.2s;

              &.view {
                background: #F4F5F7;
                color: #172B4D;
                border: 1px solid #DFE1E6;

                &:hover {
                  background: #EBECF0;
                }
              }

              &.download {
                background: #0052CC;
                color: white;

                &:hover {
                  background: #0747A6;
                }
              }
            }
          }
        }
      }
    }
  }
}
</style> 