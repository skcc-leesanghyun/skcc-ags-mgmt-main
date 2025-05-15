# audit-service 
> **CodeGuide**에서 생성된 rule을 활용, **Cursor**가 생성한 프로젝트에 대한 기본적인 검증

---
## Build
### Maven VS Gradle
현재 Maven으로 프로젝트가 구성된 것으로 보여지는데, **ATS** 및 **재활용 모듈**은 Gradle로 구성. 통일 필요.

### package 오류.
DTO 클래스들이 'skcc'가 누락되었거나, 'audit'이 누락되는 형태로 소스 생성이 정상적이 않음.

### JPA 관련 Page 클래스 오류.
Cursor Rule(JPA 금지)에 의한 라이브러리 이슈. repository는 JPA 형식으로 생성

### 빈 클래스 생성
파일은 존재하나 0Kb로 내용 없는 클래스 존재