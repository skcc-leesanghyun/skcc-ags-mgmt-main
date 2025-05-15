<template>
  <div class="talent-sourcing">
    <h2>{{ $t('talent.title') }}</h2>
    
    <div class="search-section">
      <div class="filter-group">
        <label>{{ $t('talent.filter.tech') }}</label>
        <select>
          <option value="">Java</option>
          <option value="">Python</option>
          <option value="">JavaScript</option>
        </select>
      </div>
      <div class="filter-group">
        <label>{{ $t('talent.filter.experience') }}</label>
        <select>
          <option value="">3년 이상</option>
          <option value="">5년 이상</option>
          <option value="">7년 이상</option>
        </select>
      </div>
      <div class="filter-group">
        <label>{{ $t('talent.filter.salary') }}</label>
        <select>
          <option value="">5,000만원 이상</option>
          <option value="">7,000만원 이상</option>
          <option value="">1억원 이상</option>
        </select>
      </div>
      <button class="search-button">{{ $t('common.search') }}</button>
    </div>

    <div class="candidates-list">
      <h3>{{ $t('talent.candidate.title') }}</h3>
      <div class="candidates-grid">
        <div v-for="i in 5" :key="i" class="candidate-card">
          <div class="candidate-info">
            <h4>홍길동</h4>
            <div class="candidate-details">
              <div class="detail-item">
                <span class="label">{{ $t('talent.candidate.tech') }}:</span>
                <span class="value">Java, Spring, React</span>
              </div>
              <div class="detail-item">
                <span class="label">{{ $t('talent.candidate.experience') }}:</span>
                <span class="value">5년</span>
              </div>
              <div class="detail-item">
                <span class="label">{{ $t('talent.candidate.salary') }}:</span>
                <span class="value">7,000만원</span>
              </div>
            </div>
          </div>
          <div class="candidate-actions">
            <button class="action-button view">{{ $t('talent.candidate.view') }}</button>
            <button class="action-button evaluate">{{ $t('talent.candidate.evaluation') }}</button>
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
.talent-sourcing {
  padding: 20px;

  h2 {
    color: #172B4D;
    margin-bottom: 20px;
  }

  .search-section {
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

      select {
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

  .candidates-list {
    h3 {
      color: #172B4D;
      margin-bottom: 20px;
    }

    .candidates-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
      gap: 20px;

      .candidate-card {
        background: white;
        border-radius: 8px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        padding: 20px;
        display: flex;
        flex-direction: column;
        gap: 15px;

        .candidate-info {
          h4 {
            color: #172B4D;
            margin: 0 0 10px 0;
          }

          .candidate-details {
            display: flex;
            flex-direction: column;
            gap: 8px;

            .detail-item {
              display: flex;
              gap: 8px;
              color: #6B778C;

              .label {
                font-weight: 500;
                min-width: 80px;
              }

              .value {
                color: #172B4D;
              }
            }
          }
        }

        .candidate-actions {
          display: flex;
          gap: 10px;
          margin-top: auto;

          .action-button {
            flex: 1;
            padding: 8px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-weight: 500;
            transition: background-color 0.2s;

            &.view {
              background: #F4F5F7;
              color: #172B4D;

              &:hover {
                background: #DFE1E6;
              }
            }

            &.evaluate {
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
</style> 