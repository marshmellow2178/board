# MyBoard

Spring Boot, Thymeleaf, Spring Data JPA로 만든 개인용 심플 게시판 웹 애플리케이션입니다.  
회원가입·로그인, 게시글 작성·조회·수정·삭제, 댓글·좋아요, 마이페이지 기능을 제공합니다.

---

## 🚀 주요 기능

- **회원 관리**  
  - 회원가입, 로그인/로그아웃  
  - 내 정보 조회(아이디·이메일·가입일·마지막 로그인)  

- **게시판(Posts)**  
  - 게시글 작성·조회·수정·삭제 (CRUD)  
  - 조회수 집계  
  - 좋아요(토글 방식, 사용자당 하나)  
  - 제목 검색 & 페이징  

- **댓글(Comments)**  
  - 댓글 작성·수정·삭제  
  - 게시글 상세 페이지에 표시  

- **마이페이지(My Page)**  
  - 사이드바 메뉴: 내 글 / 내 댓글 / 내 좋아요 / 내 정보  
  - 2단 레이아웃으로 사이드바 + 콘텐츠 영역 분리  

- **보안(Security)**  
  - Spring Security 폼 로그인(간단한 CSRF 비활성화)  
  - `PasswordEncoder` 로 비밀번호 해싱  
  - 작성자만 수정·삭제 가능하도록 권한 검사  

---

## 🛠️ 기술 스택

- **백엔드**: Java 17, Spring Boot 3.x  
- **DB**: Spring Data JPA, Hibernate, MySQL/MariaDB  
- **템플릿**: Thymeleaf + Bootstrap 5  
- **보안**: Spring Security  
- **빌드 도구**: Maven (또는 Gradle)  
- **추가기능**: Lombok, JPA Auditing, Bean Validation

---

## 📂 프로젝트 구조

<img width="350" height="327" alt="image" src="https://github.com/user-attachments/assets/471885a8-d635-41b5-b09f-ae72286e71bd" />

<img width="301" height="476" alt="image" src="https://github.com/user-attachments/assets/0c917495-23e6-4476-9322-d74eaaa72998" />

---

## ⚙️ 실행 방법

### 사전 준비

- Java 17 이상  
- Maven 3.8 이상 (또는 Gradle 7 이상)  
- MySQL 또는 MariaDB

### 데이터베이스 설정

1. 데이터베이스 생성  
   ```sql
   CREATE DATABASE myboard CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

2. src/main/resources/application.properties에 접속 정보 입력
   
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/myboard?serverTimezone=UTC
spring.datasource.username=YOUR_DB_USER
spring.datasource.password=YOUR_DB_PASS

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.thymeleaf.cache=false
```

### 빌드 & 실행

```bash
# Maven
./mvnw clean package
./mvnw spring-boot:run

# or Gradle
./gradlew clean build
./gradlew bootRun
```
브라우저에서 http://localhost:8080 으로 접속하세요.

## 스크린샷

<img width="736" height="425" alt="image" src="https://github.com/user-attachments/assets/ec1d6949-a7a4-4818-a4b6-be918d4890f5" />

<img width="832" height="611" alt="image" src="https://github.com/user-attachments/assets/ce68ef25-2747-4547-b58e-405f6883b535" />

<img width="733" height="328" alt="image" src="https://github.com/user-attachments/assets/fe197c5e-456b-4d8c-b18b-cdfbd9f4ee2c" />

