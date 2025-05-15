# 재활용 모듈 테스트 Guilde
> 변경사항은 git 이력으로 확인

---
## 서비스 기동
> 기본적인 세팅은 상위 README.md를 참고
1. common-service 기동 
```
cd ./backend/ra-common
./gradlew :common-service:bootRun
```

2. account-service 기동
```
cd ./backend/ra-common
./gradlew :account-service:bootRun
```

3. api-gateway 기동
```
cd ./backend/ra-api-gateway
./gradlew :api-gateway:bootRun
```


---
## 서비스 점검
아래와 같이 로그인을 호출하면 Access Token과 Refresh Token 응답을 받을 수 있다. 해당 호출을 통해 점검할 수 있는 서비스는 MySQL/Redis/Kafka 모두 포함.
```
curl --location 'http://localhost:9102/api/v1/com/account/login' \
--header 'Content-Type: application/json' \
--header 'Accept: */*' \
--data-raw '{
  "userid": "user01",
  "connPsswd": "pass123!@#",
  "systmCtgryCd": "W",
  "userContPhno": "01012345678"
}'
```
이 후, API-Gateway를 통한 Backend API 호출이 가능해지면 호출 방식은 아래와 같이 Header에 **Authorization**을 추가한다.
```
curl --location 'http://localhost:9102/api/v1/com/account/authorization/tokenAliveChk' \
--header 'Content-Type: application/json' \
--header 'Accept: */*' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJST0xFIjpbXSwiQUNDT1VOVF9JRCI6InVzZXIwMSIsImV4cCI6MTc0NTk4NjA1OCwiaWF0IjoxNzQ1OTg1MTU4fQ.SKlIDjwJ4_70AfdjxIYoPvf4HcJIFtRaxcs2JVJAPL5b_5ph8OZKAloujdMPyxxPtV6y3bA9H2IZCW3tWgpv-A' \
--data '{
  "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJST0xFIjpbXSwiQUNDT1VOVF9JRCI6InVzZXIwMSIsImV4cCI6MTc0NTk4NjA1OCwiaWF0IjoxNzQ1OTg1MTU4fQ.SKlIDjwJ4_70AfdjxIYoPvf4HcJIFtRaxcs2JVJAPL5b_5ph8OZKAloujdMPyxxPtV6y3bA9H2IZCW3tWgpv-A",
  "refreshToken": "7c8b4214-bed0-48f8-a8ae-b2b7d993b1d0"
}'
```


---
### 주의사항
1. 로그인 API의 **connPsswd** 는 DB에 암호화되어있기 때문에 data-sample.sql 실행시 주의.
2. **jwtSecret** 값이 불일치할 경우, account-service 에서 획득한 Token 유효성이 api-gateway 에서 검증되지 않으므로 주의