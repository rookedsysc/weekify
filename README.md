## 테스트 커버리지/포맷 CI

- 추가한 것: CI에서 Spotless, 테스트, JaCoCo 커버리지 리포트 생성을 실행합니다.
- 이유: PR마다 코드 스타일, 테스트 실패, 커버리지 부족을 자동으로 확인하기 위함입니다.
- 해야 할 것: push 전 `./gradlew spotlessApply`와 `./gradlew test jacocoTestReport -x jacocoTestCoverageVerification`를 실행합니다.

JaCoCo HTML 리포트는 `build/reports/jacoco/test/html/index.html`에 생성됩니다.

### GitHub Actions 설정

- `.github/workflows/test-visibility.yml`에서 `pull_request`, `push`에 브랜치 필터를 두지 않았습니다.
- 모든 브랜치 PR과 모든 브랜치 push commit마다 `test` job이 실행됩니다.

### 머지 차단 설정

- GitHub repository ruleset에서 모든 브랜치(`~ALL`)를 대상으로 `test` status check를 required로 설정합니다.
- `strict_required_status_checks_policy`를 켜서 최신 base 기준으로 `test`가 통과해야 merge할 수 있게 합니다.
- 이 설정은 저장소 관리자 권한으로 적용해야 하며, 실패한 PR은 GitHub에서 merge가 차단됩니다.
