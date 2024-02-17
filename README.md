# 게시판
> 기본적인 게시판 프로젝트입니다
> [점프 투 스프링부트](https://wikidocs.net/book/7601) 강의를 실습한 후, 개인적으로 여러 기능들을 추가해 보았습니다

**개발 기간**
2024.1.14 ~ 2.17 약 1개월

**추가된 기능**
- 디자인 변경(Bootstrap -> pico css): 기존 디자인이 상대적으로 크기가 작아보이고 코드는 복잡한 것을 해결하기 위함
- 댓글 페이징
- 대댓글 기능
- 글 카테고리
- 회원 활동 보기(마이페이지)
- 비밀번호 변경/찾기
- 글 작성 시 마크다운 에디터 적용
- 조회수 표시

## 사용기술

- Java 17
- SpringBoot 3.2.1
- JPA/Spring Security
- h2 database
- thymeleaf/pico css
- html/css/javascript

## 구조

**1. 공통 헤더**
모든 페이지에 나타나는 헤더
 ![Pasted image 20240206194723](https://github.com/marshmellow2178/board/assets/115971843/6fcf27d8-7679-4193-88d7-3b1c6b295462)

로그아웃 상태
![로그인 헤더](https://github.com/marshmellow2178/board/assets/115971843/b1b16774-6c59-4608-95d1-5dc70eaca473)
로그인 상태

**2. 글 목록**
![Pasted image 20240205000220](https://github.com/marshmellow2178/board/assets/115971843/3faf79c6-c9de-4226-886c-e5a38c7a31b3)

- 전체 게시글 조회/페이징
- 홈 페이지로 이용
- 카테고리 탭 메뉴 추가: 해당 카테고리의 글만 모아보기
**3. 글 등록**
![Pasted image 20240209231222](https://github.com/marshmellow2178/board/assets/115971843/e3f9bdb7-a68f-437c-a555-0a94539f674f)

- 로그인한 사용자만 글 작성 가능
- 카테고리 선택 가능(기본값 자유)
- 작성 후 목록 화면으로
- 마크다운 에디터 사용가능
**4. 글 상세**
![Pasted image 20240206194904](https://github.com/marshmellow2178/board/assets/115971843/f84dfe33-a24e-45e8-ae81-a4d8294f73d5)

- 본인이 작성한 글은 수정/삭제 가능
- 본인이 좋아요한 글에는 빨간색 하트를, 아니면 하얀색을 표시
- 조회수/추천수 표시

![Pasted image 20240210000713](https://github.com/marshmellow2178/board/assets/115971843/0492cf08-4bf5-41f8-8399-d021fc7dd901)

- 댓글/대댓글이 많을 경우 페이징 적용 
- 댓글을 추천순 > 최신순으로 정렬
**5. 글 수정**
![Pasted image 20240206195015](https://github.com/marshmellow2178/board/assets/115971843/ef9a56dd-099c-4bc0-bda6-6b1c13298e81)

- 카테고리 변경 가능
- 제목/내용 수정 가능
- 수정 후 수정한 글로 이동
![Pasted image 20240209231449](https://github.com/marshmellow2178/board/assets/115971843/b3fe9e1b-60ec-4399-86f4-421cca898573)

- 수정하면 작성일시가 수정된 날짜로 바뀌고, (수정됨) 표시
**6. 글 삭제**
![Pasted image 20240209231604](https://github.com/marshmellow2178/board/assets/115971843/164edb1a-df8a-4cc2-a3b6-92dbb8bbe380)

- 삭제할지 확인하고, 삭제 후 전체 목록 화면으로 이동
**7. 검색/페이징**
![Pasted image 20240209231835](https://github.com/marshmellow2178/board/assets/115971843/227d7bb7-1d40-465e-aeb5-2e396842a9ef)

- 검색창, 카테고리 메뉴, 페이지네이션의 정보를 숨겨진 폼을 사용해 쿼리 스트링으로 전달해 검색
- 새로운 페이지에서 쿼리스트링을 통해 기존 정보 유지

**8. 댓글 작성**
- 로그인한 사용자만 댓글과 대댓글 사용 가능
![Pasted image 20240209231942](https://github.com/marshmellow2178/board/assets/115971843/dba8d7a8-d9d9-4485-847e-5bf2391f30fc)

- 비로그인 화면
![Pasted image 20240209232038](https://github.com/marshmellow2178/board/assets/115971843/1b9ba001-5922-48d3-ade5-9729dd77e2c4)

- 로그인 후 폼 화면
![Pasted image 20240209232552](https://github.com/marshmellow2178/board/assets/115971843/e101fd3a-30ac-4483-934e-fd8433427ea6)

- 본인의 댓글/대댓글만 수정/삭제 가능
- 댓글을 클릭하면 대댓글 작성 폼이 나타남
- 대댓글은 작성자에 re: 표시를 하고, 배경색이 다르게 원 댓글 아래에 표시
- 삭제 여부 확인 후, 삭제시 현재 페이지 새로고침

**9. 회원가입**
![Pasted image 20240206195106](https://github.com/marshmellow2178/board/assets/115971843/52ee5b05-b79c-4d62-a3e3-74ca226bb28c)

- ID(변경 불가), 비밀번호, 비밀번호 확인, 이메일 입력
![Pasted image 20240209233204](https://github.com/marshmellow2178/board/assets/115971843/bed146fe-1b70-4010-b176-57ff16b0bf9f)

![Pasted image 20240209233232](https://github.com/marshmellow2178/board/assets/115971843/94af90e5-d8f4-4394-a4c8-e6c7d3774bf0)

- 비밀번호 일치 확인
**10. 로그인**
![Pasted image 20240206195120](https://github.com/marshmellow2178/board/assets/115971843/93117831-c51b-401a-a31a-cefbdb906090)

![Pasted image 20240209233416](https://github.com/marshmellow2178/board/assets/115971843/23bc159f-df28-4d36-b04b-3dc1732e8d65)

- 아이디와 비밀번호를 틀릴 경우
- 비밀번호를 분실한 경우, 비밀번호 찾기를 통해 임시 비밀번호 발급 가능

- 아이디/이메일이 일치하면 랜덤 영문자 조합의 임시 비밀번호 출력
**11. 마이페이지**
![메뉴바](https://github.com/marshmellow2178/board/assets/115971843/958d3d05-8aed-47b1-8bd6-2ea30a700c65)

- 마이페이지 탭 메뉴바
- 홈: 간단한 회원 활동내역
- 글/댓글/대댓글: 회원이 작성했던 모든 내역 

![마이페이지 홈](https://github.com/marshmellow2178/board/assets/115971843/26601b35-282d-4b41-84cb-fb8a2aadc998)

- 회원이 작성한 글/댓글의 수
- 회원의 각 최근 활동 5개 모아보기

## 데이터

![Pasted image 20240210113855](https://github.com/marshmellow2178/board/assets/115971843/e27aed4d-ea83-409c-b14d-7b06e12c9909)

- ON DELETE CASCADE(글이 삭제되면 댓글과 대댓글 같이 삭제)

![Pasted image 20240206200850](https://github.com/marshmellow2178/board/assets/115971843/97d5c404-65c2-4d16-956a-284f74aa78f0)


**POST**

| 컬럼명 | 타입 | 조건 | 설명 |
| ---- | ---- | ---- | ---- |
| ID | INT | PK | 글번호 |
| TITLE | VARCHAR(200) | NOT NULL | 제목 |
| CONTENT | TEXT | NOT NULL | 내용 |
| MEMBER_ID | INT | FK | 작성자 |
| CATEGORY_ID | INT | FK | 분류 |
| READ | INT | DEFAULT 0 | 조회수 |
| CREATE_DATE | DATETIME |  | 작성일 |
| MODIFY_DATE | DATETIME |  | 수정일 |

**COMMENT**

| 컬럼명 | 타입 | 조건 | 설명 |
| ---- | ---- | ---- | ---- |
| ID | INT | PK | 글번호 |
| CONTENT | TEXT | NOT NULL | 내용 |
| MEMBER_ID | INT | FK | 작성자 |
| CREATE_DATE | DATETIME |  | 작성일 |
| MODIFY_DATE | DATETIME |  | 수정일 |
| POST_ID | INT | NOT NULL, FK | 글번호 |

**REPLY**

| 컬럼명 | 타입 | 조건 | 설명 |
| ---- | ---- | ---- | ---- |
| ID | INT | PK | 글번호 |
| CONTENT | TEXT | NOT NULL | 내용 |
| MEMBER_ID | INT | FK | 작성자 |
| CREATE_DATE | DATETIME |  | 작성일 |
| MODIFY_DATE | DATETIME |  | 수정일 |
| POST_ID | INT | NOT NULL, FK | 글번호 |
| COMMENT_ID | INT | NOT NULL, FK | 댓글번호 |

**MEMBER**

| 컬럼명 | 타입 | 조건 | 설명 |
| ---- | ---- | ---- | ---- |
| ID | INT | PK | 회원번호 |
| UID | VARCHAR(255) | NOT NULL, UNIQUE | 아이디 |
| PW | VARCHAR(255) | NOT NULL | 비밀번호 |
| EMAIL | VARCHAR(255) | NOT NULL | 이메일 |

**CATEGORY**

| 컬럼명 | 타입 | 조건 | 설명 |
| ---- | ---- | ---- | ---- |
| ID | INT | PK |  |
| NAME | VARCHAR(200) | NOT NULL UNIQUE | 카테고리명 |

**VOTER**

| 컬럼명 | 타입 | 조건 | 설명 |
| ---- | ---- | ---- | ---- |
| POST_ID | INT | FK | 글번호 |
| VOTER_ID | INT | FK | 회원번호 |

## API
- 메소드가 표시되지 않은 경우, GET방식

**게시글 관련 기능**

| 기능 | 로그인 | 메소드 | URL | 결과 |
| ---- | ---- | ---- | ---- | ---- |
| 홈  |  |  | / | redirect: /post/list |
| 전체 목록 |  |  | /post/list | 전체 글 목록 |
| 검색 |  |  | /post/list?ctgr={category_id}&keyword={keyword}&page={page} | 검색된 글 목록 |
| 등록 | O |  | /post/create | 글 작성 폼 |
| 등록 | O | POST | /post/create | 작성한 글 |
| 수정 | O |  | /post/modify/{id} | 글 수정 폼 |
| 수정 | O | POST | /post/modify/{id} | 수정된 글 |
| 삭제 | O |  | /post/delete/{id} | 전체 목록 |
| 조회 |  |  | /post/detail/{id} | 해당 글 |
| 좋아요 | O |  | /post/like/{id} | 추천수 증가 |

**댓글 관련 기능**

| 기능 | 로그인 | 메소드 | URL | 결과 |
| ---- | ---- | ---- | ---- | ---- |
| 등록 | O | POST | /comment/create/{post_id} | 댓글이 작성된 글 |
| 수정 | O |  | /comment/modify/{id} | 댓글 수정 폼 |
| 수정 | O | POST | /comment/modify/{id} | 댓글이 작성된 글 |
| 삭제 | O |  | /comment/delete/{id} | 댓글이 있던 글 |
| 좋아요 | O |  | /comment/like/{id} | 추천수 증가 |

**대댓글 관련 기능**

| 기능 | 로그인 | 메소드 | URL | 결과 |
| ---- | ---- | ---- | ---- | ---- |
| 등록 | O | POST | /reply/create/{post_id} | 댓글이 작성된 글 |
| 수정 | O |  | /reply/modify/{id} | 수정 폼 |
| 수정 | O | POST | /reply/modify/{id} | 댓글이 작성된 글 |
| 삭제 | O |  | /reply/delete/{id} | 댓글이 있던 글 |
| 좋아요 | O |  | /reply/like/{id} | 추천수 증가 |

**회원**

| 기능 | 로그인 | 메소드 | URL | 결과 |
| ---- | ---- | ---- | ---- | ---- |
| 회원가입 |  |  | /member/signup | 회원가입 폼 |
| 회원가입 |  | POST | /member/signup | 홈 페이지 |
| 로그인 |  |  | /member/login | 로그인 폼 |
| 로그인 |  | POST | /member/login | 홈 페이지 |
| 로그아웃 | O | POST | /member/logout | 홈 페이지 |
| 비밀번호 찾기 |  |  | /member/pwfind | 비밀번호 찾기 폼 |
| 비밀번호 찾기 |  | POST | /member/pwfind | 폼에 결과 표시 |
| 비밀번호 변경 | O |  | /member/pwchange | 비밀번호 변경 폼 |
| 비밀번호 변경 | O | POST | /member/pwchange | 로그아웃 후 홈 |
| 회원 활동 | O |  | /mypage/ | 마이페이지 홈 |
| 회원 글 | O |  | /mypage/post | 회원 글 모음 |
| 회원 댓글 | O |  | /mypage/comment | 회원 댓글 모음 |
| 회원 대댓글 | O |  | /mypage/reply | 회원 대댓글 모음 |


## UPDATES

**2.17**

- 이클립스 연동
- 작업 내용 업로드
