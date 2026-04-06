# CoverFox Automation Project — Claude Agent Instructions

> This file is read automatically by Claude Code every time it starts in this folder.
> It gives Claude permanent context so you never have to re-explain the project.

---

## Project Identity

**What this is:** Selenium + Java + TestNG automation framework for www.coverfox.com
(Indian health insurance comparison website)

**My role:** Senior SDET for this project. Help the user add, modify, fix, and run tests.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 11+ |
| Browser automation | Selenium WebDriver 4.25.0 |
| Test framework | TestNG 7.10.1 |
| Build tool | Maven (pom.xml) |
| IDE | Eclipse |
| Logging | Log4j 1.2.17 |
| Test data | Apache POI (Excel) |
| AI integration | OpenAI GPT-4o-mini via java.net.http.HttpClient |
| JSON | org.json 20240303 |

---

## Application Under Test — Flow

```
Home Page
  └─ Select Gender (Male / Female)
        └─ Health Plan Page → click Next
              └─ Member Details Page → select Age from dropdown → click Next
                    └─ Address Details Page → enter Pincode + Mobile → click Continue
                          └─ Results Page → shows matching insurance plans + count
```

---

## Project Structure

```
src/main/java/
├── ai/
│   ├── AIClient.java           ← calls OpenAI API (reads OPENAI_API_KEY env var)
│   ├── PromptBuilder.java      ← builds prompts: failure analysis, test gen, API validation
│   ├── AIResponseParser.java   ← parses OpenAI JSON → plain text
│   ├── AgentReportParser.java  ← parses testng-results.xml into Java objects
│   └── CoverFoxTestAgent.java  ← autonomous agent (3 modes: analyze/suggest/coverage)
├── coverFoxBase/
│   └── Base.java               ← launchBrowser(), closeBrowser(), protected WebDriver driver
├── coverFoxPOM/
│   ├── CoverFoxHomePage.java           ← selectGender(), clickOnGenderButton(), clickOnFemaleButton()
│   ├── CoverFoxHealthPlanPage.java     ← clickOnNextButton()
│   ├── CoverFoxMemberDetailsPage.java  ← handleAgeDropDown(String), clickOnNextButton()
│   ├── CoverFoxAddressDetailsPage.java ← enterPinCode(String), enterMobNum(String), clickOnContinueButton()
│   └── CoverFoxResultPage.java         ← getInsuranceCount(), getCountFromText(), getCountFromBanner()
└── coverFoxUtility/
    ├── Listener.java  ← ITestListener: screenshot + AI analysis on failure
    └── Utility.java   ← takeScreenShot(), readDataFromExcel(), readDataFromPropertyFile()

src/test/java/
└── coverFoxTest/
    ├── TC01_CoverFox_ValidateBannerCount.java         ← validates text count == banner count
    ├── TC02_CoverFox_ValidateTotalPremium.java        ← (in progress)
    ├── TC03_CoverFox_ValidateInsuranceCountByAge.java ← Male, 6 ages, DataProvider
    ├── TC04_CoverFox_ValidateInsuranceCountByGender.java ← Male vs Female comparison
    └── TC05_CoverFox_ValidateInsuranceCombinations.java  ← 12 combinations: gender × age × city
```

---

## Coding Rules — ALWAYS Follow These

1. Every test class MUST `extend Base`
2. `@BeforeClass` → call `launchBrowser()`
3. `@AfterClass`  → call `closeBrowser()`
4. `@BeforeMethod` → `driver.get(Utility.readDataFromPropertyFile("url"))` + init page objects
5. Add `System.out.println("[Step] ...")` BEFORE every action
6. Add `System.out.println("[Done] ...")` AFTER every action, printing actual values where possible
7. Never remove existing logic — only add or modify
8. New test classes: `TC0X_CoverFox_Description.java` in package `coverFoxTest` (auto-increment TC number)
9. New page objects: `CoverFox[Name]Page.java` in package `coverFoxPOM`
10. POM constructors always call `PageFactory.initElements(driver, this)`
11. Use `@DataProvider` for any test needing multiple input combinations
12. After creating a new test, always add it to `testngCoverFoxTestNG.xml`

---

## Key Files — Never Modify Without Being Asked

- `Base.java` — base class, shared by all tests
- `config.properties` — contains the application URL
- `pom.xml` — only modify if a new dependency is truly needed
- `testngCoverFoxTestNG.xml` — must be updated when a new test class is created

---

## How to Run

```powershell
# Set API key (required for AI features)
$env:OPENAI_API_KEY = "your-key-here"

# Run all tests
mvn test

# Run AI agent after tests
mvn exec:java -Dexec.mainClass="ai.CoverFoxTestAgent"

# Agent modes
mvn exec:java -Dexec.mainClass="ai.CoverFoxTestAgent" -Dexec.args="suggest"
mvn exec:java -Dexec.mainClass="ai.CoverFoxTestAgent" -Dexec.args="coverage"

# One-click: tests + agent report
run-agent.bat
```

---

## Available Claude Skills

| Command | What it does |
|---|---|
| `/new-test` | Guided new test case creation |
| `/fix-test` | Root cause analysis for a failing test |
| `/add-prints` | Adds [Step]/[Done] print statements to any file |
| `/combo-test` | Generates a DataProvider combination test |

---

## When I Ask You to Add a Test — Checklist

- [ ] Find the highest TC number in `src/test/java/coverFoxTest/`
- [ ] Name the file `TC(N+1)_CoverFox_Description.java`
- [ ] Package: `coverFoxTest`
- [ ] Class extends `Base`
- [ ] Imports include all needed page objects
- [ ] `@BeforeClass` launches browser, configures logger
- [ ] `@BeforeMethod` navigates to URL, inits page objects
- [ ] `@Test` has the validation with Assert
- [ ] `@AfterClass` closes browser
- [ ] Add XML entry to `testngCoverFoxTestNG.xml`
- [ ] Output complete file (not just a snippet)

---

## When I Ask You to Fix a Test

- Read the failing file first
- Read the error message carefully
- Make the MINIMUM change needed — do not rewrite
- Explain the fix in plain English after applying it

---

## Test Data

- Excel file: `TestSheets/TestExcel.xls` (Sheet4, row 1: age col0, pincode col1, mobile col2)
- Default pincode used in new tests: `400001` (Mumbai)
- Default mobile used in new tests: `9876543210`
- Ages for dropdown: pass as string `"25"` → becomes `"25y"` internally
