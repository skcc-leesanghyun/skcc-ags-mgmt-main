<template>
  <div class="audit">
    <h2>On-Boarding/Off-Boarding Audit</h2>

    <div class="audit-filters">
      <div class="filter-group">
        <label>프로젝트</label>
        <select>
          <option value="">전체 프로젝트</option>
          <option value="1">프로젝트 A</option>
          <option value="2">프로젝트 B</option>
          <option value="3">프로젝트 C</option>
        </select>
      </div>
      <div class="filter-group">
        <label>상태</label>
        <select>
          <option value="">전체</option>
          <option value="onboarding">On-Boarding</option>
          <option value="offboarding">Off-Boarding</option>
        </select>
      </div>
      <div class="filter-group">
        <label>기간</label>
        <input type="date" />
      </div>
      <button class="search-button">검색</button>
    </div>

    <div class="audit-list">
      <div class="audit-card" v-for="i in 5" :key="i">
        <div class="audit-info">
          <div class="info-header">
            <h3>프로젝트 {{ i }}</h3>
            <span class="status" :class="i % 2 === 0 ? 'onboarding' : 'offboarding'">
              {{ i % 2 === 0 ? 'On-Boarding' : 'Off-Boarding' }}
            </span>
          </div>
          <div class="info-details">
            <p><strong>담당자:</strong> 홍길동</p>
            <p><strong>시작일:</strong> 2024-03-01</p>
            <p><strong>종료일:</strong> 2024-12-31</p>
            <p><strong>서약서 상태:</strong> 
              <span class="pledge-status" :class="i % 3 === 0 ? 'pending' : 'completed'">
                {{ i % 3 === 0 ? '대기중' : '완료' }}
              </span>
            </p>
          </div>
        </div>
        <div class="audit-actions">
          <button class="action-button view">상세보기</button>
          <button class="action-button download">서약서 다운로드</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.audit {
  padding: 20px;

  h2 {
    color: #172B4D;
    margin-bottom: 20px;
  }

  .audit-filters {
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

  .audit-list {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(400px, 1fr));
    gap: 20px;

    .audit-card {
      background: white;
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);

      .audit-info {
        margin-bottom: 15px;

        .info-header {
          display: flex;
          justify-content: space-between;
          align-items: center;
          margin-bottom: 15px;

          h3 {
            color: #172B4D;
            margin: 0;
          }

          .status {
            padding: 4px 8px;
            border-radius: 4px;
            font-size: 0.9em;
            font-weight: 500;

            &.onboarding {
              background: #E3FCEF;
              color: #006644;
            }

            &.offboarding {
              background: #FFEBE6;
              color: #BF2600;
            }
          }
        }

        .info-details {
          p {
            color: #6B778C;
            margin: 5px 0;

            strong {
              color: #172B4D;
            }

            .pledge-status {
              padding: 2px 6px;
              border-radius: 3px;
              font-size: 0.9em;

              &.pending {
                background: #FFEBE6;
                color: #BF2600;
              }

              &.completed {
                background: #E3FCEF;
                color: #006644;
              }
            }
          }
        }
      }

      .audit-actions {
        display: flex;
        gap: 10px;

        .action-button {
          flex: 1;
          padding: 8px;
          border: none;
          border-radius: 4px;
          cursor: pointer;
          transition: background-color 0.2s;

          &.view {
            background: #F4F5F7;
            color: #172B4D;

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
</style> 