# order-system-backend

이 프로젝트는 결제 서비스의 백엔드 서비스입니다.

## 목차

- [사용된 기술](#사용된-기술)
- [모듈](#모듈)
- [문서](#문서)
- [실행하는 법](#실행하는-법)

## 사용된 기술

- Java SDK 17
- Spring Boot 2.7.9
- Redis
- Postgres
- Docker
- Eureka

## 모듈

| 서비스           | 설명                                       | 권한 부여          | 포트 번호 |
|---------------|------------------------------------------|----------------|-------|
| 사용자 서비스       | 사용자 관련 작업을 관리                            | jwt 토큰으로 권한 검사 | 8080  |
| 결제 서비스        | 결제 관련 기능을 처리                             | -              | 8081  |
| 상품 서비스        | 상품 관련 기능을 처리                             | -              | 8082  |
| 재고 서비스        | 재고 관련 기능을 처리                             | -              | 8083  |
| API 게이트웨이 서비스 | 외부 통신을 위한 게이트웨이 역할,<br/>요청을 적절한 서비스로 라우팅 | jwt 토큰으로 권한 검사 | 8000  |
| Eureka 서비스    | 서비스 검색 및 등록 담당                           | -              | 8761  |

## ERD

![img.png](.github/resources/img.png)

## 문서

> - [API 명세](https://linktodocumentation)

## 실행하는 법

> - 빌드하고 실행
> ```bash
> docker compose up --build
> ```
> - 단일 모듈 빌드 후 실행
> ```bash
> docker compose up --build {service-name}
> ```
> - 빌드하지 않고 실행
> ```bash
> docker compose up
> ```
