<template>
  <aside class="app-sidebar">
    <nav>
      <router-link to="/" class="nav-item">홈</router-link>
      <router-link to="/talent" class="nav-item">인력 소싱 및 평가</router-link>
      <router-link to="/audit" class="nav-item">On-Boarding/Off-Boarding Audit</router-link>
      <router-link to="/planning" class="nav-item">계획 및 성과 관리</router-link>
      <router-link to="/knowledge" class="nav-item">Knowledge Management</router-link>
    </nav>
  </aside>
</template>

<style lang="scss" scoped>
.app-sidebar {
  width: 250px;
  background-color: #F4F5F7;
  border-right: 1px solid #DFE1E6;
  padding: 20px 0;

  nav {
    display: flex;
    flex-direction: column;

    .nav-item {
      padding: 12px 20px;
      color: #172B4D;
      text-decoration: none;
      transition: background-color 0.2s;

      &:hover {
        background-color: #EBECF0;
      }

      &.router-link-active {
        background-color: #EBECF0;
        font-weight: bold;
      }
    }
  }
}
</style> 