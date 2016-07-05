# 우여명의 웹서버 만들기 도전 - 패스트캠프 JWP 수업

### 실습을 위한 개발 환경 세팅
* https://github.com/slipp/web-application-server 참고

### 요구사항 1 - http://localhost:8080/index.html로 접속시 응답
* HTTP Header 텍스트를 추상화한 http.HttpHeader 객체를 만들었다.
* HttpHeader 객체는 header 텍스트를 파싱해서 가져온다.
* HttpHeader 에서 요청 URL을 가져와서 정적 파일을 응답하도록 한다.

### 요구사항 2 - get 방식으로 회원가입
* URI path 가 /user/create 로 시작할 경우 회원 가입 하도록 작업.
* 헤더에서 요청 URI 를 가져오고 요청 URI를 파싱해서 http.HttpRequest 객체를 만든다.
* httpRequest 객체에서 map 형식으로 저장되어 있는 파라미터들을 가져와서 유효성 체크를 하고 회원 객체를 만든다.
* 회원 객체를 DataBase라는 임의의 데이터 저장 객체에 저장한다.

#### 막간 리펙토링
* 요청이 들어오면 어떤 작업을 하고 응답을 내뱉은 공통 규칙을 발견했다. 그래서 Action이라는 인터페이스를 만들었다.
* Action이라는 인터페이스는 다음의 메서드를 구현해야 한다. 
`String act(Request httpRequest)`
* 기본 정적 파일을 읽는 부분을 StaticFileReadAction 클래스로 추출했다.
* 회원 가입하는 부분을 SignInAction 클래스로 추출했다.

### 요구사항 3 - post 방식으로 회원가입
* POST는 HTTP 헤더 이후 빈 공백을 가지는 한 줄(line) 다음부터 데이터가 시작된다.
* 기존에 빈 공백이 나오면 bufferReader의 readLine을 중단했는데, 
POST 메서드의 경우에는 헤더의 Contents-length 만큼 읽어오도록 수정했다.

#### 막간 리펙토링
* HttpHeader 클래스와 HttpRequest 클래스가 분리될 이유가 없는것 같아서 둘을 합치고,
이름을 HttpRequest라고 했다.
* HttpResponse 클래스를 추출해냈다.
* Action 인터페이스의 act 메서드의 시그니처를 수정했다.
`void act(HttpRequest httpRequest, HttpResponse response)`
* act 메서드에서 응답을 처리하는 일 까지 하기 위함이다.

### 요구사항 4 - redirect 방식으로 이동
* HttpResponse 클래스에 redirect 메서드(status code : 302)를 추가했다.
* SignInAction을 회원 가입 성공시 redirect 하도록 수정했다.

### 요구사항 5 - cookie
* HttpResponse 에 setCookie 하는 메서드를 추가했다.
* HttpRequest 에 Map 으로 쿠키를 읽는다.
* LoginAction 에서 로그인에 성공할 경우 logined=true 쿠키를 추가하고 index.html로 리다이렉트한다.
* 아이디가 없거나 비번이 안맞을 경우 logined=false 쿠키를 추가하고 로그인 실패 페이지를 연다.

### 요구사항 6 - 로그인 했을 때 사용자 리스트 보여주기
* UserListAction 을 구현한다.

### 요구사항 7 - stylesheet 적용
* 

### heroku 서버에 배포 후
* 