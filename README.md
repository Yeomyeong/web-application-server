# 우여명의 웹서버 만들기 도전 - 패스트캠프 JWP 수업

### 실습을 위한 개발 환경 세팅
* https://github.com/slipp/web-application-server 참고

### 요구사항 1 - http://localhost:8080/index.html로 접속시 응답
* HTTP Header 텍스트를 추상화한 http.HttpHeader 객체를 만들었다.
* HttpHeader 객체는 header 텍스트를 파싱해서 가져온다.
* HttpHeader 에서 요청 URL을 가져와서 정적 파일을 응답하도록 한다.

### 요구사항 2 - get 방식으로 회원가입
* URI path 가 /user/create 로 시작할 경우 회원 가입 하도록 작업.
* 헤더에서 요청 URI 를 가져오고 요청 URI를 파싱해서 http.Request 객체를 만든다.
* request 객체에서 map 형식으로 저장되어 있는 파라미터들을 가져와서 유효성 체크를 하고 회원 객체를 만든다.
* 회원 객체를 DataBase라는 임의의 데이터 저장 객체에 저장한다.

#### 막간 리펙토링
* 요청이 들어오면 어떤 작업을 하고 응답을 내뱉은 공통 규칙을 발견했다. 그래서 Action이라는 인터페이스를 만들었다.
* Action이라는 인터페이스는 다음의 메서드를 구현해야 한다. String act(Request request)
* 기본 정적 파일을 읽는 부분을 StaticFileReadAction 클래스로 추출했다.
* 회원 가입하는 부분을 SignInAction 클래스로 추출했다.

### 요구사항 3 - post 방식으로 회원가입
* 

### 요구사항 4 - redirect 방식으로 이동
* 

### 요구사항 5 - cookie
* 

### 요구사항 6 - stylesheet 적용
* 

### heroku 서버에 배포 후
* 