<template>
  <div class="planning">
    <h2>{{ $t('planning.title') }}</h2>

    <div class="planning-filters">
      <div class="filter-group">
        <label>{{ $t('planning.filter.project') }}</label>
        <select>
          <option value="">프로젝트 A</option>
          <option value="">프로젝트 B</option>
        </select>
      </div>
      <div class="filter-group">
        <label>{{ $t('planning.filter.month') }}</label>
        <input type="month" />
      </div>
      <button class="search-button">{{ $t('common.search') }}</button>
    </div>

    <div class="planning-content">
      <div class="workforce-planning">
        <h3>{{ $t('planning.workforce.title') }}</h3>
        <table>
          <thead>
            <tr>
              <th>{{ $t('planning.workforce.grade') }}</th>
              <th>{{ $t('planning.workforce.planned') }}</th>
              <th>{{ $t('planning.workforce.actual') }}</th>
              <th>{{ $t('planning.workforce.achievement') }}</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="grade in ['A', 'B', 'C']" :key="grade">
              <td>{{ grade }}</td>
              <td><input type="number" :value="10" /></td>
              <td>{{ 8 }}</td>
              <td :class="{ 'high': 8/10 >= 0.8, 'medium': 8/10 >= 0.6 && 8/10 < 0.8, 'low': 8/10 < 0.6 }">
                {{ Math.round(8/10 * 100) }}%
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <div class="kpi-status">
        <h3>{{ $t('planning.kpi.title') }}</h3>
        <div class="kpi-grid">
          <div class="kpi-card">
            <h4>{{ $t('planning.kpi.fulfillment') }}</h4>
            <div class="kpi-value">85%</div>
            <div class="kpi-trend up">+5%</div>
          </div>
          <div class="kpi-card">
            <h4>{{ $t('planning.kpi.inputPeriod') }}</h4>
            <div class="kpi-value">3.2일</div>
            <div class="kpi-trend down">-0.5일</div>
          </div>
          <div class="kpi-card">
            <h4>{{ $t('planning.kpi.satisfaction') }}</h4>
            <div class="kpi-value">4.5/5.0</div>
            <div class="kpi-trend up">+0.2</div>
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
.planning {
  padding: 20px;

  h2 {
    color: #172B4D;
    margin-bottom: 20px;
  }

  .planning-filters {
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

  .planning-content {
    display: grid;
    gap: 20px;

    .workforce-planning, .kpi-status {
      background: white;
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);

      h3 {
        color: #172B4D;
        margin: 0 0 20px 0;
      }
    }

    .workforce-planning {
      table {
        width: 100%;
        border-collapse: collapse;

        th, td {
          padding: 12px;
          text-align: left;
          border-bottom: 1px solid #DFE1E6;
        }

        th {
          color: #172B4D;
          font-weight: 500;
        }

        td {
          color: #6B778C;

          input {
            width: 80px;
            padding: 4px 8px;
            border: 1px solid #DFE1E6;
            border-radius: 4px;
          }

          &.high {
            color: #36B37E;
          }

          &.medium {
            color: #FFAB00;
          }

          &.low {
            color: #FF5630;
          }
        }
      }
    }

    .kpi-status {
      .kpi-grid {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
        gap: 20px;

        .kpi-card {
          background: #F4F5F7;
          padding: 20px;
          border-radius: 6px;

          h4 {
            color: #172B4D;
            margin: 0 0 10px 0;
            font-size: 0.9em;
          }

          .kpi-value {
            font-size: 1.8em;
            font-weight: 500;
            color: #172B4D;
            margin-bottom: 8px;
          }

          .kpi-trend {
            font-size: 0.9em;
            font-weight: 500;

            &.up {
              color: #36B37E;
            }

            &.down {
              color: #FF5630;
            }
          }
        }
      }
    }
  }
}
</style> 