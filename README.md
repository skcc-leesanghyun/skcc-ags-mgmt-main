# AGS 운영관리 시스템

AGS 운영관리 시스템은 다국적 인재 소싱을 위한 엔터프라이즈급 시스템입니다.

## 시스템 개요

본 시스템은 다음과 같은 주요 기능을 제공합니다:

- 인재 소싱 및 평가
- 온보딩/오프보딩 감사
- 계획 및 성과 관리
- 지식 관리

## 기술 스택

### Frontend
- Vue 3
- Modern Corporate UI Design
- 다국어 지원 (영어/한국어)

### Backend
- Spring Boot & Java
- MyBatis
- PostgreSQL (단일 인스턴스)
- 마이크로서비스 아키텍처

### Infrastructure
- Kubernetes
- Docker
- GitHub Actions (CI/CD)

### Third-Party Integrations
- Okta OAuth2
- REST API (구매시스템 연동)

## 프로젝트 구조

```
.
├── frontend/          # Vue 3 프론트엔드 코드
├── backend/           # Spring Boot 마이크로서비스
└── database/          # PostgreSQL 스키마 스크립트

```

## 시작하기

### 필수 요구사항
- Node.js
- Java JDK
- PostgreSQL
- Docker & Kubernetes

### 개발 환경 설정
1. 저장소 클론
2. 필요한 의존성 설치
3. 환경 변수 설정
4. 개발 서버 실행

## 라이선스

이 프로젝트는 비공개 라이선스로 보호됩니다.