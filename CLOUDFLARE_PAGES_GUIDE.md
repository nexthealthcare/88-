# 88WORKOUT (88웰니스) 클라우드플레어(Cloudflare Pages) 웹사이트 배포 가이드

본 프로젝트에는 안드로이드 앱뿐만 아니라, 클라우드플레어를 통해 즉시 전 세계에 무료 배포할 수 있는 **88WORKOUT 웹 랜딩페이지 & 온라인 바디체크(`/web` 폴더)**가 완벽히 구성되어 있습니다.

---

## 📌 전체 구성 요약
- **웹사이트 소스 위치**: 저장소 내 `/web/index.html`
- **호스팅 플랫폼**: **Cloudflare Pages** (GitHub 연동형 무료 무제한 트래픽 웹 호스팅)
- **도메인 연결**: `88WORKOUT` 도메인 (예: `88workout.com`, `88workout.kr` 등)
- **특징**:
  - 온라인 3분 MBTI 바디체크 인터랙티브 테스트 내장
  - 4×4 매트릭스 소개 및 12주 로드맵 안내
  - 오프라인 88센터 방문 예약 폼 연동

---

## 🚀 1단계: GitHub 저장소 최신 커밋 푸시
AI Studio에서 변경된 파일들이 GitHub 저장소에 push되어 있는지 확인합니다.
(현재 저장소의 `/web` 폴더에 웹페이지 파일이 포함되어 있습니다.)

---

## 🌐 2단계: Cloudflare Pages 프로젝트 생성

1. [Cloudflare 대시보드(dash.cloudflare.com)](https://dash.cloudflare.com/)에 로그인합니다.
2. 좌측 메뉴에서 **Workers & Pages (Workers 및 Pages)** 를 클릭합니다.
3. 상단의 **[Create application (애플리케이션 생성)]** 버튼을 클릭합니다.
4. **[Pages]** 탭을 선택하고 **[Connect to Git (Git에 연결)]** 을 클릭합니다.
5. GitHub 계정을 연동한 후, 본 프로젝트가 저장된 **GitHub 저장소(Repository)**를 선택하고 **[Begin setup (설정 시작)]**을 누릅니다.

---

## ⚙️ 3단계: 빌드 및 배포 설정 (중요!)

설정 화면에서 다음 항목을 입력합니다:

| 항목 | 설정값 | 설명 |
| :--- | :--- | :--- |
| **Project name (프로젝트 이름)** | `88workout` | 원하는 프로젝트 이름 |
| **Production branch (프로덕션 브랜치)** | `main` (또는 `master`) | 기본 브랜치 |
| **Framework preset (프레임워크 프리셋)** | `None` (없음) | 순수 정적 웹페이지 |
| **Build command (빌드 명령)** | *(비워둠)* | 정적 HTML이므로 필요 없음 |
| **Build output directory (빌드 출력 디렉터리)** | **`web`** | ⭐️ **반드시 `web`으로 입력** |

입력 후 하단의 **[Save and Deploy (저장 및 배포)]** 버튼을 클릭합니다.
약 10~20초 후 `https://88workout.pages.dev` 주소로 즉시 무료 사이트가 생성 및 배포됩니다!

---

## 🔗 4단계: 88WORKOUT 커스텀 도메인 연결

소유하고 계신 `88WORKOUT` 도메인(예: `88workout.com`)을 연결하는 방법입니다.

### Case A: 도메인의 네임서버가 이미 Cloudflare에 등록되어 있는 경우 (가장 간편)
1. 방금 생성된 Pages 프로젝트의 **[Custom domains (맞춤 도메인)]** 탭으로 이동합니다.
2. **[Set up a custom domain (맞춤 도메인 설정)]** 버튼을 클릭합니다.
3. 구매하신 도메인(예: `88workout.com` 또는 `www.88workout.com`)을 입력합니다.
4. **[Activate domain (도메인 활성화)]**을 누르면 Cloudflare가 DNS CNAME 레코드를 자동으로 추가하고 무료 SSL 인증서(HTTPS)를 즉시 발급합니다.

### Case B: 타 도메인 등록기관(가비아, 후이즈, 네임스핀 등)을 사용하는 경우
1. Pages 프로젝트의 **[Custom domains]** 에서 도메인 입력 후 제공되는 CNAME 대상을 확인합니다 (예: `88workout.pages.dev`).
2. 도메인 등록기관의 DNS 관리 페이지에서 아래와 같이 CNAME 레코드를 추가합니다:
   - **호스트 / 이름**: `@` 또는 `www`
   - **타입**: `CNAME`
   - **값 / 대상**: `<프로젝트명>.pages.dev`
3. 몇 분 후 Cloudflare에서 도메인 연결 및 SSL 보안 접속이 활성화됩니다.

---

## 🔓 5단계: HTTP(비보안 프로토콜)로도 접속 가능하게 설정하는 방법

기본적으로 Cloudflare는 모든 접속을 HTTPS로 강제 리다이렉트(301 Redirect)하도록 설정되어 있을 수 있습니다. **HTTP로도 접속을 허용**하려면 Cloudflare에서 아래 2가지를 설정하시면 됩니다:

1. **[Always Use HTTPS (항상 HTTPS 사용)] 끄기**:
   - Cloudflare 대시보드 ➡️ 해당 도메인 선택 ➡️ **SSL/TLS** 메뉴 클릭 ➡️ **Edge Certificates (엣지 인증서)** 탭으로 이동
   - **Always Use HTTPS (항상 HTTPS 사용)** 스위치를 **[꺼짐(Off)]**으로 변경합니다.
2. **[Automatic HTTPS Rewrites (자동 HTTPS 재작성)] 끄기**:
   - 같은 페이지 하단의 **Automatic HTTPS Rewrites**를 **[꺼짐(Off)]**으로 변경합니다.
3. **[안드로이드 앱 내 HTTP 허용 처리]**:
   - 본 안드로이드 앱의 `AndroidManifest.xml`에 **`android:usesCleartextTraffic="true"`** 및 **`INTERNET`** 권한이 이미 적용되어 있어, 앱 내부 WebView 및 브라우저에서 `http://` 주소도 보안 경고 없이 원활하게 로드됩니다.

---

## ⚡ 빠른 대안: GitHub Pages로 즉시 HTTP/HTTPS 열기
만약 Cloudflare 설정이 복잡하시다면, 저장소에 이미 준비된 **`/docs`** 폴더를 이용하여 GitHub Pages로 1분 만에 사이트를 열 수 있습니다:
1. GitHub 저장소 ➡️ **Settings** ➡️ 좌측 **Pages** 메뉴 클릭
2. **Build and deployment** 의 Source를 **[Deploy from a branch]** 로 선택
3. Branch를 **`main`** 선택, 폴더를 **`/docs`** 로 선택 후 **[Save]** 클릭
4. `https://<깃허브아이디>.github.io/<저장소이름>/` 주소로 즉시 웹페이지가 열립니다.
