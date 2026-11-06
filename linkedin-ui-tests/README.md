# LinkedIn UI Automation Tests

Java, Selenium WebDriver and TestNG use karala LinkedIn login, search saha search result open karana UI tests.

## Requirements

- Java 17 or later
- Maven
- Google Chrome

## Configure credentials

VS Code PowerShell terminal eke project root folder ekata yanna. Oyage own LinkedIn account credentials set karanna. Password eka type karaddi terminal eke penne naha.

```powershell
$env:LINKEDIN_EMAIL = Read-Host "LinkedIn email"
$securePassword = Read-Host "LinkedIn password" -AsSecureString
$env:LINKEDIN_PASSWORD = [System.Net.NetworkCredential]::new("", $securePassword).Password
```

Me terminal session ekema tests run karanna. Credentials source code ekata danna epa.

## Run all tests

```powershell
mvn clean test
```

## Run one test

```powershell
mvn "-Dtest=LinkedInTests#positiveLoginTest" test
mvn "-Dtest=LinkedInTests#negativeLoginTest" test
mvn "-Dtest=LinkedInTests#searchTest" test
mvn "-Dtest=LinkedInTests#clickFirstSearchResultTest" test
```

## Test cases

- `positiveLoginTest` — valid account credentials walin login wenawada balai.
- `negativeLoginTest` — invalid password ekak dunna wita error message eka pennanawada balai.
- `searchTest` — search ekak karala results pennanawada balai.
- `clickFirstSearchResultTest` — search result ekak open wenawada balai.

## Reports and screenshots

Maven test report eka `target/surefire-reports` athule hadenawa. Project root eke `screenshots` folder eka screenshots walata use karanawa.

LinkedIn CAPTCHA/security verification ekak pennuwoth eka bypass karanna epa. Test eka nawaththala, report eke challenge eka note karanna.