# Resource Server (Spring Resource Server)
Spring Authorization Server 기반의 Resource Server
기본값인 JWT 토큰을 사용한다.

MS SQL(authorization-server 프로젝트의 Docker-Compose 사용)

- Public APIs: `/api/open/**`
- Secured APIs: `/api/secure/**` (JWT required)

## 요청 가능한 API 목록 
- Public (인증 불필요)
  - GET `/login`
    - 설명: Authorization Server 로그인 페이지로 리다이렉트합니다.
    - Query: `redirect_uri`(optional) — 인증 후 돌아올 리디렉션 URL. 기본값 `http://localhost:9091/authorized`
  - GET `/authorized`
    - 설명: Authorization Code를 받아 Access Token을 교환한 결과를 반환합니다.
    - Query: `code`(required)
  - GET `/public`
    - 설명: 토큰 없이 접근 가능한 공개 엔드포인트.

- Secured (JWT 필요)
  - 사용자 유틸리티
    - GET `/api/secure/user/userinfo`
      - 설명: 인증된 JWT의 클레임 정보를 반환합니다.
    - GET `/api/secure/user/userinfo2`
      - 설명: 인증 토큰, 권한, 상세 정보 등을 반환합니다.

  - 게시판 Boards
    - POST `/api/boards`
      - Body: `BoardCreateRequest`
      - 설명: 게시글 생성
    - GET `/api/boards`
      - Query: `type`(optional, `BoardType`) — 특정 타입만 조회
      - 설명: 게시글 목록 조회
    - GET `/api/boards/{id}`
      - 설명: 게시글 단건 조회
    - PUT `/api/boards/{id}`
      - Body: `BoardUpdateRequest`
      - 설명: 게시글 수정
    - DELETE `/api/boards/{id}`
      - 설명: 게시글 삭제
    - PUT `/api/boards/{id}/pin`
      - 설명: 게시글 상단 고정
    - PUT `/api/boards/{id}/unpin`
      - 설명: 게시글 상단 고정 해제

  - 댓글 Comments
    - POST `/api/boards/{boardId}/comments`
      - Body: `CommentCreateRequest`
      - 설명: 댓글 생성
    - GET `/api/boards/{boardId}/comments`
      - 설명: 특정 게시글의 댓글 목록 조회
    - DELETE `/api/boards/{boardId}/comments`
      - 설명: 특정 게시글의 모든 댓글 일괄 삭제
    - GET `/api/boards/{boardId}/comments/{commentId}`
      - 설명: 댓글 단건 조회
    - DELETE `/api/boards/{boardId}/comments/{commentId}`
      - 설명: 댓글 단건 삭제
    - DELETE `/api/boards/{boardId}/comments/{commentIds}`
      - 설명: 댓글 ID 목록(CSV 경로 세그먼트)으로 일괄 삭제. 예) `/api/boards/1/comments/10,11,12`

참고
- Swagger UI: `http://localhost:9091/swagger-ui` (prod 프로파일이 아닐 때 자동 허용)
- OpenAPI 문서: `http://localhost:9091/v3/api-docs`
- Secured 경로는 모두 `Authorization: Bearer <JWT>` 헤더가 필요합니다.
