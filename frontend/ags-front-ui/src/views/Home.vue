<template>
  <div class="home">
    <h1>AGS 운영관리 시스템</h1>
    <div class="dashboard">
      <div class="dashboard-item">
        <h3>인력 소싱 및 평가</h3>
        <p>인력 소싱, 평가, 매칭 관리</p>
      </div>
      <div class="dashboard-item">
        <h3>On-Boarding/Off-Boarding Audit</h3>
        <p>인력 On-Boarding/Off-Boarding 관리</p>
      </div>
      <div class="dashboard-item">
        <h3>계획 및 성과 관리</h3>
        <p>프로젝트 계획 및 성과 관리</p>
      </div>
      <div class="dashboard-item">
        <h3>Knowledge Management</h3>
        <p>지식 관리 및 공유</p>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.home {
  padding: 20px;

  h1 {
    margin-bottom: 30px;
    color: #172B4D;
  }

  .dashboard {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
    gap: 20px;

    .dashboard-item {
      background: white;
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);

      h3 {
        color: #0052CC;
        margin-bottom: 10px;
      }

      p {
        color: #6B778C;
      }
    }
  }
}
</style> 