# CoverFox Automation Testing — Complete Guide

> **Who is this for?**
> This guide is written for **everyone** — whether you have never written code before or are an experienced developer.
> Follow every step in order and you will have a fully working, AI-powered test automation project.

---

## Table of Contents

1. [What This Project Does](#1-what-this-project-does)
2. [Tools You Need to Install](#2-tools-you-need-to-install)
3. [Setting Up Eclipse IDE — Step by Step](#3-setting-up-eclipse-ide--step-by-step)
4. [Creating a Maven Project in Eclipse](#4-creating-a-maven-project-in-eclipse)
5. [Understanding the Folder Structure](#5-understanding-the-folder-structure)
6. [Creating Packages and Classes](#6-creating-packages-and-classes)
7. [Adding Dependencies (pom.xml)](#7-adding-dependencies-pomxml)
8. [Running the Existing Tests](#8-running-the-existing-tests)
9. [How AI is Integrated in This Project](#9-how-ai-is-integrated-in-this-project)
10. [Switching AI Provider — OpenAI / Gemini / Claude](#10-switching-ai-provider--openai--gemini--claude)
11. [Improving and Writing New Test Cases](#11-improving-and-writing-new-test-cases)
12. [API Testing with AI](#12-api-testing-with-ai)
13. [Project Test Case Map](#13-project-test-case-map)
14. [Troubleshooting Common Problems](#14-troubleshooting-common-problems)
15. [Glossary — Words You Will See](#15-glossary--words-you-will-see)
16. [Using AI Tools to Add and Modify Test Cases](#16-using-ai-tools-to-add-and-modify-test-cases)
17. [Create a Custom GPT for This Project](#17-create-a-custom-gpt-for-this-project)
18. [Create Claude Skills and Codex Skills](#18-create-claude-skills-and-codex-skills)
19. [Build an AI Agent for This Project](#19-build-an-ai-agent-for-this-project)

---

## 1. What This Project Does

This project **automatically tests the CoverFox insurance website** (www.coverfox.com).

Instead of a human manually:
- Opening a browser
- Clicking buttons
- Filling forms
- Checking results

...this project does all of that **automatically**, in seconds, and reports whether everything passed or failed.

### On top of basic automation, this project also uses **Artificial Intelligence** to:
| AI Feature | What it does |
|---|---|
| **Failure Analyzer** | When a test fails, AI explains *why* it failed and *how to fix it* |
| **Test Case Generator** | You describe a feature in plain English, AI writes the test cases |
| **API Validator** | AI checks if an API response is correct |

---

## 2. Tools You Need to Install

Install all tools below **in this exact order**.

### 2.1 Java Development Kit (JDK 11 or higher)

Java is the programming language this project uses.

**Download:** https://www.oracle.com/java/technologies/downloads/

**Steps:**
1. Open the link above
2. Click **Java 17** (recommended)
3. Choose your OS: Windows, Mac, or Linux
4. Download the installer (.exe for Windows)
5. Run the installer, click Next → Next → Finish
6. Verify installation:
   - Press `Windows + R`, type `cmd`, press Enter
   - Type: `java -version`
   - You should see something like: `java version "17.0.x"`

---

### 2.2 Apache Maven (Build Tool)

Maven downloads all libraries your project needs automatically.

**Download:** https://maven.apache.org/download.cgi

**Steps:**
1. Download `apache-maven-3.9.x-bin.zip`
2. Extract to `C:\maven`
3. Add to PATH:
   - Search "Environment Variables" in Windows
   - Click "Environment Variables"
   - Under "System Variables" → click "Path" → "Edit"
   - Click "New" → type `C:\maven\bin`
   - Click OK → OK → OK
4. Verify:
   - Open new CMD window
   - Type: `mvn -version`
   - You should see: `Apache Maven 3.9.x`

---

### 2.3 Eclipse IDE

Eclipse is where you write and run the code.

**Download:** https://www.eclipse.org/downloads/

**Steps:**
1. Click "Download x86_64"
2. Run the installer
3. Choose **"Eclipse IDE for Java Developers"**
4. Click Install → Launch

---

### 2.4 Google Chrome + ChromeDriver

The tests run in Chrome browser.

1. Install Chrome from https://www.google.com/chrome/
2. Check your Chrome version:
   - Open Chrome → `...` menu → Help → About Google Chrome
   - Note your version (e.g., 124.x.x.x)
3. Selenium 4 (used in this project) **downloads ChromeDriver automatically** — you don't need to install it manually.

---

### 2.5 Git (Version Control)

Git lets you save, share, and track changes to your code.

**Download:** https://git-scm.com/downloads

- Install with default settings
- Verify: open CMD, type `git --version`

---

## 3. Setting Up Eclipse IDE — Step by Step

### 3.1 First Launch

1. Open Eclipse
2. It will ask for a **Workspace** — this is where your projects are saved
3. Choose or create a folder, e.g.: `C:\workspace`
4. Click **Launch**

### 3.2 Install TestNG Plugin in Eclipse

TestNG is the testing framework used by this project.

1. In Eclipse, click menu: **Help → Eclipse Marketplace**
2. In the search box type: `TestNG`
3. Click **Go**
4. Find **"TestNG for Eclipse"** → click **Install**
5. Accept license → click **Finish**
6. Restart Eclipse when asked

### 3.3 Install M2Eclipse (Maven Plugin)

Usually pre-installed. To verify:
1. **Help → Eclipse Marketplace**
2. Search `m2e`
3. If not installed, install **"Maven Integration for Eclipse (M2E)"**

---

## 4. Creating a Maven Project in Eclipse

> If you are **cloning this existing project from Git**, skip to Section 4.4.

### 4.1 Create New Maven Project

1. Click **File → New → Project**
2. Expand **Maven** → select **Maven Project** → click Next
3. Check ✅ **"Create a simple project (skip archetype selection)"**
4. Click **Next**
5. Fill in:
   - **Group Id:** `SeleniumAutomation`  *(like a company name)*
   - **Artifact Id:** `TestingCoverFo`    *(like a project name)*
   - **Version:** `0.0.1-SNAPSHOT`
   - **Packaging:** `jar`
6. Click **Finish**

Your project is created!

### 4.2 Fix the Source Folders

Maven requires a specific folder structure. Set it up:

1. Right-click the project → **Properties**
2. Click **Java Build Path → Source tab**
3. Make sure these folders exist:
   - `src/main/java`
   - `src/test/java`
4. If missing, click **Add Folder** and create them

### 4.3 Set Java Version

1. Right-click project → **Properties**
2. Click **Java Compiler**
3. Set **Compiler compliance level** to `17` (or 11 minimum)
4. Click Apply and Close

### 4.4 Clone Existing Project from Git

If you have the Git URL:

**Option A — Using Eclipse:**
1. **File → Import → Git → Projects from Git**
2. Click **Next → Clone URI**
3. Paste the Git repository URL
4. Click Next → Next → Finish

**Option B — Using Command Line:**
```
cd C:\workspace
git clone https://github.com/YOUR_USERNAME/coverfoxProject.git
```
Then import into Eclipse:
1. **File → Import → Maven → Existing Maven Projects**
2. Browse to `C:\workspace\coverfoxProject`
3. Click Finish

---

## 5. Understanding the Folder Structure

This is what the project looks like and what each folder does:

```
coverfoxProject/
│
├── src/
│   ├── main/java/                        ← Reusable code (not tests)
│   │   ├── ai/                           ← 🤖 AI integration package
│   │   │   ├── AIClient.java             ← Talks to AI API (OpenAI/Gemini/Claude)
│   │   │   ├── PromptBuilder.java        ← Builds questions to ask AI
│   │   │   └── AIResponseParser.java     ← Reads AI's answers
│   │   │
│   │   ├── coverFoxBase/
│   │   │   └── Base.java                 ← Opens/closes browser, shared setup
│   │   │
│   │   ├── coverFoxPOM/                  ← Page Object Model — one file per webpage
│   │   │   ├── CoverFoxHomePage.java     ← Home page buttons & actions
│   │   │   ├── CoverFoxHealthPlanPage.java
│   │   │   ├── CoverFoxMemberDetailsPage.java
│   │   │   ├── CoverFoxAddressDetailsPage.java
│   │   │   └── CoverFoxResultPage.java   ← Results page — reads insurance count
│   │   │
│   │   └── coverFoxUtility/
│   │       ├── Listener.java             ← Runs on pass/fail — takes screenshots + AI analysis
│   │       └── Utility.java              ← Helper methods: screenshots, Excel, config
│   │
│   └── test/java/
│       └── coverFoxTest/                 ← All test cases live here
│           ├── TC01_CoverFox_ValidateBannerCount.java
│           ├── TC02_CoverFox_ValidateTotalPremium.java
│           ├── TC03_CoverFox_ValidateInsuranceCountByAge.java
│           ├── TC04_CoverFox_ValidateInsuranceCountByGender.java
│           └── TC05_CoverFox_ValidateInsuranceCombinations.java
│
├── TestSheets/
│   └── TestExcel.xls                     ← Test data (age, pincode, mobile number)
│
├── Logs/
│   ├── ExecutionLog.log                  ← Plain text log
│   ├── ExecutionLog1.log
│   └── ExecutionLog.html                 ← HTML formatted log
│
├── Screenshot/                           ← Auto-saved on test failure
│
├── test-output/                          ← TestNG HTML report (auto-generated)
│
├── config.properties                     ← Application URL and settings
├── log4j (1).properties                  ← Logging configuration
├── pom.xml                               ← All project dependencies
└── testngCoverFoxTestNG.xml              ← Which tests to run and in what order
```

### What is a Package?
A **package** is just a folder that groups related Java files together. In Java, the package name matches the folder path.

Example: `coverFoxBase` package → files live in `src/main/java/coverFoxBase/`

### What is a Class?
A **class** is a Java file (`.java`). It contains the code that performs actions — clicking buttons, reading text, making API calls, etc.

---

## 6. Creating Packages and Classes

### 6.1 Create a Package

1. In Eclipse, expand your project in **Package Explorer** (left panel)
2. Right-click `src/main/java`
3. Click **New → Package**
4. Type the package name, e.g.: `coverFoxPOM`
5. Click **Finish**

> **Tip:** Package names use only lowercase letters and no spaces.

### 6.2 Create a Class

1. Right-click the package you just created
2. Click **New → Class**
3. Fill in:
   - **Name:** e.g., `CoverFoxHomePage`
   - Check ✅ `public static void main` only if it's a standalone program (not needed for test classes)
4. Click **Finish**

### 6.3 Create a Test Class

Test classes go in `src/test/java/coverFoxTest/`:

1. Right-click `src/test/java`
2. **New → Package** → name it `coverFoxTest`
3. Right-click `coverFoxTest` → **New → Class**
4. Name it: `TC06_CoverFox_YourTestName`
5. Click **Finish**

### 6.4 Page Object Model Pattern (POM)

Every webpage in the application has its own Java class. This keeps things organized.

**Rule:** One webpage = One Java class

Example structure for a new page:
```java
package coverFoxPOM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CoverFoxNewPage {

    // Step 1: Declare web elements using @FindBy
    // Right-click the element in Chrome → Inspect → copy XPath or ID
    @FindBy(id = "some-button-id")
    private WebElement someButton;

    @FindBy(xpath = "//div[@class='result-count']")
    private WebElement resultCount;

    // Step 2: Constructor — always the same pattern
    public CoverFoxNewPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    // Step 3: Methods — one method per action
    public void clickSomeButton() {
        someButton.click();
    }

    public String getResultCount() {
        return resultCount.getText();
    }
}
```

### How to Find XPath in Chrome (for non-technical users)

1. Open Chrome and go to the webpage
2. Right-click on the button/field you want to automate
3. Click **Inspect**
4. In the panel that opens, right-click the highlighted HTML line
5. Click **Copy → Copy XPath**
6. Paste into your `@FindBy(xpath = "...")` in Java

---

## 7. Adding Dependencies (pom.xml)

**Dependencies** are pre-built libraries that your project uses. Maven downloads them automatically.

### 7.1 What is pom.xml?

`pom.xml` is like a shopping list. You tell Maven what libraries you need, and Maven downloads them from the internet.

### 7.2 Current Dependencies in This Project

Open `pom.xml` — you will see:

| Library | Purpose |
|---|---|
| `selenium-java` | Controls the Chrome browser |
| `testng` | Runs test cases, generates reports |
| `poi-ooxml` | Reads Excel files for test data |
| `log4j` | Writes log files |
| `slf4j-api` | Logging helper |
| `org.json` | Reads/writes JSON (used by AI integration) |

### 7.3 How to Add a New Dependency

1. Go to https://mvnrepository.com/
2. Search for the library you need (e.g., "rest-assured")
3. Click the latest stable version
4. Copy the `<dependency>` block shown
5. Open `pom.xml` in Eclipse
6. Paste inside the `<dependencies>` tag
7. Press `Ctrl+S` to save
8. Right-click project → **Maven → Update Project** (or press `Alt+F5`)

**Example — adding RestAssured for API testing:**
```xml
<!-- Add this inside <dependencies> in pom.xml -->
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>rest-assured</artifactId>
    <version>5.4.0</version>
    <scope>test</scope>
</dependency>
```

### 7.4 Complete pom.xml with All Recommended Dependencies

```xml
<dependencies>

    <!-- Browser automation -->
    <dependency>
        <groupId>org.seleniumhq.selenium</groupId>
        <artifactId>selenium-java</artifactId>
        <version>4.25.0</version>
    </dependency>

    <!-- Test framework -->
    <dependency>
        <groupId>org.testng</groupId>
        <artifactId>testng</artifactId>
        <version>7.10.1</version>
        <scope>compile</scope>
    </dependency>

    <!-- Excel reading -->
    <dependency>
        <groupId>org.apache.poi</groupId>
        <artifactId>poi-ooxml</artifactId>
        <version>5.2.5</version>
    </dependency>

    <!-- Logging -->
    <dependency>
        <groupId>log4j</groupId>
        <artifactId>log4j</artifactId>
        <version>1.2.17</version>
    </dependency>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>2.0.16</version>
    </dependency>

    <!-- JSON — used by AI integration -->
    <dependency>
        <groupId>org.json</groupId>
        <artifactId>json</artifactId>
        <version>20240303</version>
    </dependency>

    <!-- API testing (add when needed) -->
    <dependency>
        <groupId>io.rest-assured</groupId>
        <artifactId>rest-assured</artifactId>
        <version>5.4.0</version>
        <scope>test</scope>
    </dependency>

</dependencies>
```

---

## 8. Running the Existing Tests

### 8.1 Set the API Key (for AI features)

Before running tests, set your OpenAI API key in PowerShell:

```powershell
$env:OPENAI_API_KEY = "your-api-key-here"
```

> If you skip this, tests still run normally — AI analysis is simply skipped with a message.

### 8.2 Navigate to Project Folder

```powershell
cd C:\Users\Codengine\git\coverfoxProject
```

### 8.3 Run All Tests via Maven

```powershell
mvn test
```

### 8.4 Run Tests from Eclipse (Recommended for Beginners)

1. Open Eclipse
2. In Package Explorer, find `testngCoverFoxTestNG.xml`
3. Right-click it → **Run As → TestNG Suite**
4. Watch the browser open and tests execute
5. See results in the **Console** tab at the bottom

### 8.5 Run a Single Test Class

Right-click any test file (e.g., `TC03_CoverFox_ValidateInsuranceCountByAge.java`)
→ **Run As → TestNG Test**

### 8.6 View the Report

After tests finish:
1. In Eclipse Package Explorer, expand `test-output/`
2. Right-click `index.html` → **Open With → Web Browser**
3. You see a full HTML report with pass/fail/screenshots

---

## 9. How AI is Integrated in This Project

The AI system lives entirely in the `ai/` package. Here is how each piece works:

```
Your Test Fails
      │
      ▼
Listener.java (onTestFailure)
      │  captures: exception, stack trace, page URL
      ▼
PromptBuilder.java
      │  builds a structured question for AI
      ▼
AIClient.java
      │  sends question to OpenAI API
      ▼
AIResponseParser.java
      │  extracts the answer from JSON response
      ▼
Reporter.log() + System.out.println()
      │  shows in Console and TestNG HTML report
      ▼
You see: Root Cause + Fix Suggestion
```

### 9.1 AIClient.java — The API Caller

This file handles all communication with the AI provider.

```java
// How it works (simplified):
String apiKey = System.getenv("OPENAI_API_KEY");  // reads key from environment
// sends HTTP POST to https://api.openai.com/v1/chat/completions
// returns the raw JSON response
```

### 9.2 PromptBuilder.java — What to Ask AI

Three built-in prompt types:

```java
// 1. Analyze why a test failed
PromptBuilder.buildFailureAnalysisPrompt(testName, exceptionType, message, stackTrace, pageUrl);

// 2. Generate test cases from plain English
PromptBuilder.buildTestCaseGenerationPrompt("User should be able to filter plans by premium amount");

// 3. Validate API response
PromptBuilder.buildApiValidationPrompt(apiResponseBody, "Should return 200 with list of plans");
```

### 9.3 Using AI in Your Own Test

```java
import ai.AIClient;
import ai.AIResponseParser;
import ai.PromptBuilder;

// Generate test cases
String prompt = PromptBuilder.buildTestCaseGenerationPrompt(
    "Health insurance plan page should show premium amount for each plan");

String rawResponse = AIClient.callOpenAI(prompt);
String answer = AIResponseParser.extractMessage(rawResponse);
System.out.println(answer);
```

---

## 10. Switching AI Provider — OpenAI / Gemini / Claude

The project currently uses **OpenAI**. Here is how to switch to Gemini or Claude.

### 10.1 Using OpenAI (Current Default)

**Get API Key:**
1. Go to https://platform.openai.com/
2. Sign up / Log in
3. Click your profile → **API Keys → Create new secret key**
4. Copy the key (starts with `sk-...`)

**Set in PowerShell:**
```powershell
$env:OPENAI_API_KEY = "sk-your-key-here"
```

**Model used:** `gpt-4o-mini` (fast and cheap — good for testing)

**Cost:** Very low — analyzing 100 test failures costs less than $0.10

---

### 10.2 Switching to Google Gemini

**Get API Key:**
1. Go to https://aistudio.google.com/
2. Sign in with Google account
3. Click **Get API Key → Create API key**

**Modify `AIClient.java`:**

Change these lines:
```java
// Replace this:
private static final String API_URL = "https://api.openai.com/v1/chat/completions";
private static final String MODEL   = "gpt-4o-mini";

// With this:
private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent";
private static final String MODEL   = "gemini-1.5-flash";
```

Change `callOpenAI` method to build Gemini's request format:
```java
public static String callOpenAI(String prompt) {
    String apiKey = System.getenv("GEMINI_API_KEY");
    if (apiKey == null || apiKey.trim().isEmpty()) {
        return "[AI] GEMINI_API_KEY not set.";
    }

    // Gemini uses a different JSON structure
    JSONObject part    = new JSONObject().put("text", prompt);
    JSONObject content = new JSONObject().put("parts", new JSONArray().put(part));
    JSONObject body    = new JSONObject().put("contents", new JSONArray().put(content));

    try {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(API_URL + "?key=" + apiKey))  // key goes in URL for Gemini
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    } catch (Exception e) {
        return "[AI] Gemini call failed: " + e.getMessage();
    }
}
```

Update `AIResponseParser.extractMessage` to parse Gemini's response:
```java
// Gemini response path: candidates[0].content.parts[0].text
JSONObject json = new JSONObject(rawResponse);
return json.getJSONArray("candidates")
           .getJSONObject(0)
           .getJSONObject("content")
           .getJSONArray("parts")
           .getJSONObject(0)
           .getString("text");
```

**Set in PowerShell:**
```powershell
$env:GEMINI_API_KEY = "your-gemini-key-here"
```

---

### 10.3 Switching to Anthropic Claude

**Get API Key:**
1. Go to https://console.anthropic.com/
2. Sign up / Log in
3. Click **API Keys → Create Key**

**Modify `AIClient.java`:**
```java
private static final String API_URL = "https://api.anthropic.com/v1/messages";
private static final String MODEL   = "claude-haiku-4-5-20251001";  // fastest, cheapest

public static String callOpenAI(String prompt) {
    String apiKey = System.getenv("CLAUDE_API_KEY");
    if (apiKey == null || apiKey.trim().isEmpty()) {
        return "[AI] CLAUDE_API_KEY not set.";
    }

    JSONObject message = new JSONObject()
        .put("role", "user")
        .put("content", prompt);

    JSONObject body = new JSONObject()
        .put("model", MODEL)
        .put("max_tokens", 600)
        .put("messages", new JSONArray().put(message));

    try {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(API_URL))
            .header("Content-Type", "application/json")
            .header("x-api-key", apiKey)            // Claude uses x-api-key header
            .header("anthropic-version", "2023-06-01")
            .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    } catch (Exception e) {
        return "[AI] Claude call failed: " + e.getMessage();
    }
}
```

Update `AIResponseParser.extractMessage` for Claude's response:
```java
// Claude response path: content[0].text
JSONObject json = new JSONObject(rawResponse);
return json.getJSONArray("content")
           .getJSONObject(0)
           .getString("text");
```

**Set in PowerShell:**
```powershell
$env:CLAUDE_API_KEY = "your-claude-key-here"
```

---

### AI Provider Comparison

| Feature | OpenAI GPT-4o-mini | Google Gemini Flash | Anthropic Claude Haiku |
|---|---|---|---|
| Speed | Fast | Very Fast | Very Fast |
| Cost | Very Low | Free tier available | Very Low |
| Quality | Excellent | Very Good | Excellent |
| Free Tier | No (pay-as-you-go) | Yes (generous) | No |
| Best for | General use | Cost saving | Detailed analysis |

> **Recommendation for beginners:** Start with **Google Gemini** — it has a free tier and is easy to set up.

---

## 11. Improving and Writing New Test Cases

### 11.1 Use AI to Generate Test Cases

You do not need to think of test cases manually. Ask AI:

```java
// In any test class, add this method and run it once:
@Test
public void generateTestIdeas() {
    String feature = "Filter insurance plans by premium amount between 500 and 2000 rupees";

    String prompt = PromptBuilder.buildTestCaseGenerationPrompt(feature);
    String raw    = AIClient.callOpenAI(prompt);
    String cases  = AIResponseParser.extractMessage(raw);

    System.out.println("\n===== AI GENERATED TEST CASES =====");
    System.out.println(cases);
    System.out.println("====================================");
}
```

Run this test → AI prints 3-5 ready-to-implement test cases.

### 11.2 Template for a New Test Case

Copy this template for any new test you want to write:

```java
package coverFoxTest;

import java.io.IOException;
import org.apache.log4j.PropertyConfigurator;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import coverFoxBase.Base;
import coverFoxPOM.CoverFoxHomePage;
import coverFoxPOM.CoverFoxResultPage;
import coverFoxUtility.Utility;

public class TC06_CoverFox_YourTestName extends Base {

    CoverFoxHomePage   homePage;
    CoverFoxResultPage resultPage;

    public static org.apache.log4j.Logger logger;

    @BeforeClass
    public void openApplication() throws IOException {
        System.out.println("========================================");
        System.out.println("  Executing Test Case 6: YOUR TEST NAME");
        System.out.println("========================================");

        System.out.println("[Step] Opening browser...");
        launchBrowser();
        System.out.println("[Done] Browser opened. URL: " + driver.getCurrentUrl());

        logger = org.apache.log4j.Logger.getLogger("TC06");
        PropertyConfigurator.configure("log4j (1).properties");
    }

    @BeforeMethod
    public void navigateToHome() throws IOException {
        System.out.println("[Step] Navigating to home page...");
        driver.get(Utility.readDataFromPropertyFile("url"));
        homePage   = new CoverFoxHomePage(driver);
        resultPage = new CoverFoxResultPage(driver);
        System.out.println("[Done] Home page ready.");
    }

    @Test
    public void yourTestMethod() throws InterruptedException {
        System.out.println("[Step] Write what you are doing...");
        // Your test steps here
        // homePage.clickOnGenderButton();

        System.out.println("[Step] Checking the result...");
        // Assert.assertEquals(actual, expected, "failure message");

        System.out.println("[PASS] Test passed!");
    }

    @AfterClass
    public void closeApplication() {
        System.out.println("[Step] Closing browser...");
        closeBrowser();
        System.out.println("[Done] Browser closed.");
    }
}
```

### 11.3 Checklist Before Writing a Test

Before writing any test, answer these questions:

- [ ] What page does this test start on?
- [ ] What actions does the user take? (click, type, select)
- [ ] What is the expected result?
- [ ] What data does the test need? (from Excel, hardcoded, or DataProvider)
- [ ] Is this a positive test (happy path) or negative test (error case)?

### 11.4 TestNG Annotations — Quick Reference

| Annotation | When it runs | Use it for |
|---|---|---|
| `@BeforeClass` | Once before all tests in the class | Open browser, configure logger |
| `@BeforeMethod` | Before each `@Test` | Navigate to page, init page objects |
| `@Test` | The actual test | Your assertions |
| `@AfterMethod` | After each `@Test` | Clean up, log result |
| `@AfterClass` | Once after all tests | Close browser |
| `@DataProvider` | Supplies data rows | Run same test with multiple inputs |

### 11.5 Data-Driven Testing with Excel

Store test data in Excel (`TestSheets/TestExcel.xls`) and read it in tests:

```java
// Read row 1, column 0 from Sheet4
String age = Utility.readDataFromExcel(excelpath, "Sheet4", 1, 0);

// Read row 1, column 1
String pincode = Utility.readDataFromExcel(excelpath, "Sheet4", 1, 1);
```

**Excel structure:**
```
Sheet4:
Row 0:  (header) Age    Pincode   Mobile
Row 1:  (data)   25     400001    9876543210
Row 2:  (data)   35     110001    9876543211
```

---

## 12. API Testing with AI

### 12.1 What is API Testing?

When you use an app, behind the scenes:
1. App sends a **request** to a server (like asking a question)
2. Server sends back a **response** (the answer, usually in JSON format)

API testing verifies this request-response is correct.

### 12.2 Add RestAssured to pom.xml

```xml
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>rest-assured</artifactId>
    <version>5.4.0</version>
    <scope>test</scope>
</dependency>
```

### 12.3 Basic API Test Structure

Create a new package `coverFoxApiTest` and add this class:

```java
package coverFoxApiTest;

import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import ai.AIClient;
import ai.AIResponseParser;
import ai.PromptBuilder;

public class TC_API_01_ValidateInsurancePlansAPI {

    private static final String BASE_URL = "https://www.coverfox.com";

    @Test
    public void validateHealthInsurancePlansAPI() {
        System.out.println("========================================");
        System.out.println("  API Test: Validate Health Plans API");
        System.out.println("========================================");

        // Step 1: Make the API call
        System.out.println("[Step] Calling the insurance plans API...");
        Response response = RestAssured
            .given()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
            .when()
                .get("/api/v2/health/plans")    // replace with actual endpoint
            .then()
                .extract().response();

        System.out.println("[Done] API call made.");
        System.out.println("[Info] Status Code : " + response.getStatusCode());
        System.out.println("[Info] Response Time: " + response.getTime() + "ms");

        // Step 2: Print the raw response
        String responseBody = response.getBody().asString();
        System.out.println("[Info] Response Body (first 500 chars):");
        System.out.println(responseBody.substring(0, Math.min(500, responseBody.length())));

        // Step 3: Basic assertion — status must be 200
        System.out.println("[Step] Asserting status code is 200...");
        assert response.getStatusCode() == 200
            : "Expected 200 but got: " + response.getStatusCode();
        System.out.println("[PASS] Status code is 200.");

        // Step 4: Send response to AI for validation
        System.out.println("[Step] Sending response to AI for analysis...");
        String prompt = PromptBuilder.buildApiValidationPrompt(
            responseBody,
            "Should return HTTP 200 with a JSON array of health insurance plans. "
            + "Each plan should have: id, name, premium, coverage_amount, insurer_name.");

        String rawAiResponse = AIClient.callOpenAI(prompt);
        String aiAnalysis    = AIResponseParser.extractMessage(rawAiResponse);

        System.out.println("\n===== AI API VALIDATION REPORT =====");
        System.out.println(aiAnalysis);
        System.out.println("=====================================");
    }
}
```

### 12.4 Full API Test Workflow

```
1. Identify the API endpoint
      │  (use Chrome DevTools: F12 → Network tab → watch requests while using the app)
      ▼
2. Write RestAssured test to call the API
      │
      ▼
3. Assert basic things (status code, response time)
      │
      ▼
4. Send full response to AI with what you expect
      │
      ▼
5. AI validates: correct fields? correct values? missing data?
      │
      ▼
6. Print AI's report — fix any issues found
```

### 12.5 How to Find API Endpoints Using Chrome DevTools

1. Open Chrome and go to www.coverfox.com
2. Press `F12` to open DevTools
3. Click the **Network** tab
4. Use the website (fill a form, click a button)
5. Watch requests appear in the Network panel
6. Click on any `fetch` or `XHR` request
7. Click **Headers** tab — see the URL (endpoint) and method (GET/POST)
8. Click **Response** tab — see the JSON response
9. Use that endpoint URL in your RestAssured test

### 12.6 API Test Checklist

For every API endpoint test:
- [ ] Status code is correct (usually 200 for success, 201 for created)
- [ ] Response time is acceptable (under 3000ms)
- [ ] Required fields are present in response
- [ ] Field values are of correct type (string, number, boolean)
- [ ] Error responses return proper error messages
- [ ] AI validation report shows PASS

---

## 13. Project Test Case Map

| Test Class | What it validates | Type | Data Source |
|---|---|---|---|
| TC01 | Banner count = text count on results page | UI | Excel |
| TC02 | Total premium display (in progress) | UI | Hardcoded |
| TC03 | Insurance count for 6 age groups (Male) | UI + Data-driven | @DataProvider |
| TC04 | Insurance count: Male vs Female comparison | UI + Data-driven | @DataProvider |
| TC05 | All combinations: Gender × Age × City | UI + Data-driven | @DataProvider |

### Adding a New Test Class

1. Create class in `src/test/java/coverFoxTest/`
2. Name it `TC06_CoverFox_YourDescription.java`
3. Add it to `testngCoverFoxTestNG.xml`:

```xml
<test thread-count="1" name="TC06 - Your Description">
    <classes>
        <class name="coverFoxTest.TC06_CoverFox_YourDescription"/>
    </classes>
</test>
```

---

## 14. Troubleshooting Common Problems

### Problem: `mvn test` fails with "No POM in this directory"
**Fix:** You are in the wrong folder. Run:
```powershell
cd C:\Users\Codengine\git\coverfoxProject
mvn test
```

### Problem: `ChromeDriver` version mismatch error
**Fix:** Selenium 4 auto-manages ChromeDriver. Update Chrome to the latest version:
Chrome menu → Help → About Google Chrome → auto-updates.

### Problem: `NoSuchElementException` — element not found
**Meaning:** The XPath or locator is wrong, or the page hasn't loaded yet.
**Fix options:**
1. Increase the wait: in `Base.java`, change `1000` to `3000` in `implicitlyWait`
2. Re-inspect the element in Chrome and get a fresh XPath
3. Check if the page structure changed (websites update their HTML)

### Problem: AI analysis shows `OPENAI_API_KEY not set`
**Fix:** Set the environment variable in the SAME PowerShell window where you run `mvn test`:
```powershell
$env:OPENAI_API_KEY = "your-key-here"
mvn test
```

### Problem: `Assert.assertEquals` fails — counts don't match
**Meaning:** The insurance count shown in text doesn't match the number of cards displayed.
**Fix:** This may be a real website bug! Check:
1. If the page fully loaded (try increasing `Thread.sleep(5000)` to `Thread.sleep(8000)`)
2. If some cards are hidden or loading lazily
3. If AI analysis gives a clue in the console output

### Problem: Tests pass locally but fail in CI/CD
**Fix:** Add headless mode to Chrome in `Base.java`:
```java
ChromeOptions options = new ChromeOptions();
options.addArguments("--disable-notification");
options.addArguments("--headless");    // add this line for CI
options.addArguments("--no-sandbox");  // add this for Linux CI
```

### Problem: Eclipse shows red errors after adding dependency
**Fix:**
1. Right-click project → **Maven → Update Project**
2. Or press `Alt+F5` → click OK

### Problem: `NullPointerException` on driver
**Meaning:** `driver` is null — browser wasn't opened before the test.
**Fix:** Make sure `@BeforeClass` runs `launchBrowser()` before any `@Test` runs.

---

## 15. Glossary — Words You Will See

| Word | Plain English Meaning |
|---|---|
| **Selenium** | A tool that controls a web browser like a robot |
| **TestNG** | A framework that runs tests and reports results |
| **Maven** | A tool that downloads libraries and builds the project |
| **POM (Project Object Model)** | The `pom.xml` file that lists dependencies |
| **Page Object Model (POM pattern)** | Design pattern — one Java class per webpage |
| **WebDriver** | The object that represents an open browser window |
| **WebElement** | A single button, field, or text on a webpage |
| **XPath** | An address that points to a specific element on a page |
| **@FindBy** | Tells Selenium how to find a WebElement on the page |
| **@Test** | Marks a method as a test case to be run |
| **@DataProvider** | Supplies multiple rows of input data to a single test |
| **Assert** | Checks if something is true — fails the test if not |
| **Package** | A folder that groups related Java files |
| **Class** | A Java file containing code |
| **Listener** | Code that automatically runs when a test passes or fails |
| **API** | A way for software to communicate over the internet |
| **JSON** | A format for data exchange (like a structured text file) |
| **AI Prompt** | A question or instruction sent to an AI model |
| **Environment Variable** | A secret value stored outside your code (e.g., API key) |
| **Thread.sleep(1000)** | Pauses execution for 1000 milliseconds (1 second) |
| **Stack Trace** | The list of code lines shown when an error occurs |
| **Assertion** | A check: "I expect X to equal Y" — test fails if it doesn't |
| **DataProvider** | A method that feeds data into a test (for data-driven testing) |

---

## Quick Command Reference

```powershell
# Navigate to project
cd C:\Users\Codengine\git\coverfoxProject

# Set AI key (do this before running tests)
$env:OPENAI_API_KEY = "your-key-here"

# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=TC03_CoverFox_ValidateInsuranceCountByAge

# Run specific test method
mvn test -Dtest=TC03_CoverFox_ValidateInsuranceCountByAge#validateInsuranceCountForAge

# Clean and rebuild project
mvn clean install

# Skip tests during build
mvn install -DskipTests

# Git: save your changes
git add .
git commit -m "Added TC06 for premium validation"
git push
```

---

*This guide is maintained alongside the project. If something is missing or unclear, raise an issue in the repository.*

---

## 16. Using AI Tools to Add and Modify Test Cases

> **Anyone can do this.** You do not need to know Java or Selenium.
> You describe what you want in plain English — the AI writes the code.
> This section shows you exactly how to do that with any AI tool.

---

### 16.1 Which AI Tools Work With This Project

| AI Tool | Where to open it | Best for |
|---|---|---|
| **Claude Code** (recommended) | Terminal inside the project folder | Full code changes, multi-file edits |
| **Claude.ai** | claude.ai in browser | Explaining + generating code snippets |
| **ChatGPT** | chatgpt.com | Generating new test cases |
| **GitHub Copilot** | Inside Eclipse or VS Code | Autocomplete while writing tests |
| **Google Gemini** | gemini.google.com | Free alternative for code generation |

---

### 16.2 How to Point an AI Tool to This Project

The AI needs to **see your project files** to give accurate answers. Here is how to do that for each tool.

---

#### Claude Code (Best Option — Reads Your Actual Files)

Claude Code runs directly inside your project folder and reads all your files automatically.

**Setup (one time only):**
```powershell
# Install Claude Code
npm install -g @anthropic-ai/claude-code

# Navigate to your project
cd C:\Users\Codengine\git\coverfoxProject

# Start Claude Code — it reads all project files automatically
claude
```

Once started, Claude Code knows about every file in the project. You can immediately start giving it instructions — no copy-pasting needed.

**Example session:**
```
You: add a test case that validates the page title on the home page
Claude: [reads your existing test files, writes TC06 following the exact same pattern]
```

---

#### ChatGPT / Claude.ai / Gemini (Browser-based)

These tools do not automatically see your files. You need to **paste the relevant code** into the chat.

**What to paste — choose based on your task:**

| Task | What to paste |
|---|---|
| Add a new test case | Paste `TC01_CoverFox_ValidateBannerCount.java` as a reference |
| Modify a POM class | Paste the specific POM file (e.g., `CoverFoxResultPage.java`) |
| Fix a failing test | Paste the test file + the error message from console |
| Add a new page | Paste `CoverFoxHomePage.java` as a template example |

**How to paste a file:**
1. Open the file in Eclipse
2. Press `Ctrl+A` to select all → `Ctrl+C` to copy
3. In the AI chat, type your prompt first, then paste the code below it

**Template message to start any AI conversation:**
```
I have a Selenium + Java + TestNG test automation project for the CoverFox insurance website.

Project structure:
- src/main/java/coverFoxBase/Base.java         → opens/closes browser
- src/main/java/coverFoxPOM/                   → one class per webpage
- src/main/java/coverFoxUtility/Listener.java  → handles pass/fail events
- src/test/java/coverFoxTest/                  → all test cases

Here is an existing test case as a reference:
[PASTE TC01 CODE HERE]

My request: [DESCRIBE WHAT YOU WANT]
```

---

#### GitHub Copilot (Inside Eclipse)

Copilot suggests code as you type — it reads your open files automatically.

**Install:**
1. In Eclipse: **Help → Eclipse Marketplace** → search `Copilot` → install
2. Sign in with your GitHub account

**How to use:**
1. Open a test file (e.g., `TC01`)
2. Start typing a comment describing what you want:
   ```java
   // Test that validates the premium amount is displayed for each plan
   ```
3. Copilot auto-suggests the full method — press `Tab` to accept

---

### 16.3 Prompt Templates — Copy, Paste, and Use

These are ready-to-use prompts. Replace the `[BRACKETS]` with your specific details.

---

#### Add a Brand New Test Case

```
I have a Selenium Java TestNG automation project.
Here is an existing test class for reference — follow this exact structure and coding style:

[PASTE TC01 OR TC03 CODE HERE]

Please write a NEW test class called TC06_CoverFox_[YourDescription] that:
- [Describe what the test should do in plain English]
- Uses the same @BeforeClass / @BeforeMethod / @Test / @AfterClass structure
- Has System.out.println() print statements after every step
- Validates: [what should pass or fail]

The page objects available are:
- CoverFoxHomePage     → clickOnGenderButton(), clickOnFemaleButton(), selectGender(String)
- CoverFoxHealthPlanPage  → clickOnNextButton()
- CoverFoxMemberDetailsPage → handleAgeDropDown(String), clickOnNextButton()
- CoverFoxAddressDetailsPage → enterPinCode(String), enterMobNum(String), clickOnContinueButton()
- CoverFoxResultPage   → getInsuranceCount(), getCountFromBanner()
```

**Real example:**
```
...same header as above...

Please write a NEW test class called TC06_CoverFox_ValidatePageTitle that:
- Opens the CoverFox homepage
- Reads the browser page title
- Asserts that the title contains the word "Insurance"
- Has print statements after every step
```

---

#### Modify an Existing Test Case

```
Here is an existing test case from my Selenium Java project:

[PASTE THE TEST CLASS CODE HERE]

Please modify it to:
- [Describe the change]

Rules:
- Do NOT remove any existing print statements
- Keep the same @BeforeClass / @AfterClass structure
- Only change what I asked — leave everything else exactly as is
```

**Real example:**
```
...same header...

Please modify it to:
- Also validate that each plan card has a non-empty premium price displayed
- Add a print statement showing the premium of the first plan
```

---

#### Add a New Page Object (for a new webpage)

```
I need to automate a new page in my Selenium project.
Here is an existing page object class as a template — follow this exact style:

[PASTE CoverFoxHomePage.java HERE]

Create a new page object class called CoverFox[PageName]Page for this page:
URL / description: [describe the page or paste its URL]

Elements to interact with:
- [Element 1]: [what it does] — XPath/ID if you know it: [paste it]
- [Element 2]: [what it does]
- [Element 3]: [what it does — could be a text, button, dropdown]

For each element, create a @FindBy field and a method.
```

---

#### Fix a Failing Test

```
My Selenium test is failing. Here is the test code and the exact error:

TEST CODE:
[PASTE THE FAILING TEST CLASS]

ERROR FROM CONSOLE:
[PASTE THE FULL ERROR MESSAGE AND STACK TRACE]

Please:
1. Explain why this error is happening in simple terms
2. Show me exactly which lines to change
3. Give me the fixed version of those lines only
```

---

#### Generate Test Cases From a Feature Description

```
I am a QA engineer building a test suite for CoverFox (an insurance website).

Feature to test:
[Describe the feature in plain English — e.g., "User can filter health insurance plans by annual premium range"]

Please generate 5 test cases covering:
1. Happy path (everything works correctly)
2. Boundary values (min/max inputs)
3. Negative cases (wrong input, empty input)
4. UI validation (error messages shown correctly)

For each test case provide:
- Test case name
- Precondition
- Steps
- Expected result
- Priority (High / Medium / Low)
```

---

#### Add Data-Driven Testing to an Existing Test

```
Here is my existing test class:

[PASTE THE TEST CLASS]

Please convert the @Test method to be data-driven using TestNG @DataProvider.
Test it with these combinations:
- [Combination 1: e.g., Male, 25, 400001]
- [Combination 2: e.g., Female, 30, 110001]
- [Combination 3: e.g., Male, 45, 560001]

Keep all existing print statements.
Add a summary table printed at the end showing all results.
```

---

### 16.4 Step-by-Step: Add a Test Case Using Claude Code

This is the **fastest and most reliable** way — Claude reads your project directly.

**Step 1 — Open terminal in project folder**
```powershell
cd C:\Users\Codengine\git\coverfoxProject
```

**Step 2 — Start Claude Code**
```powershell
claude
```

**Step 3 — Give your instruction in plain English**

Just type what you want. Examples:

```
add a test case that checks whether clicking the Female button
shows the same results page as the Male button
```

```
modify TC03 to also test ages 50 and 55
```

```
add print statements to every method in CoverFoxResultPage
```

```
create a new page object for the plan detail page — it should
have a method to read the insurer name and the annual premium
```

**Step 4 — Review the changes Claude made**

Claude will show you exactly what it changed. Review it, then run:
```powershell
mvn test -Dtest=TC06_YourNewTest
```

**Step 5 — Save to Git**
```powershell
git add src/test/java/coverFoxTest/TC06_YourNewTest.java
git commit -m "Add TC06 - your description"
git push origin master
```

---

### 16.5 What Context to Always Include in Your Prompt

The more context you give the AI, the better the output.

| Always include | Why it helps |
|---|---|
| An existing test class as a reference | AI matches your exact coding style |
| The error message (if fixing a bug) | AI diagnoses the exact problem |
| The page URL (if adding a new page) | AI can reason about what elements exist |
| What the test should PASS on | AI writes the right assertion |
| What the test should FAIL on | AI adds the right negative check |

**Never needed:**
- You do NOT need to explain Java syntax
- You do NOT need to explain Selenium or TestNG
- You do NOT need to know XPath — just describe the button/field in plain English and let AI figure it out

---

### 16.6 Real Prompt Examples — Beginner Friendly

#### "I want to test that the results page loads within 5 seconds"
```
My Selenium project tests coverfox.com. Here is an existing test for reference:
[paste TC01]

Add a test to TC01 that measures how long the results page takes to load
after clicking Continue. Print the load time. Assert it is less than 5 seconds.
```

#### "I want to check all plan cards have a Buy button"
```
My Selenium project tests coverfox.com. Here is the result page object:
[paste CoverFoxResultPage.java]

Add a new method called validateAllPlansHaveBuyButton() that:
- Finds all plan cards on the results page
- Checks each one has a visible "Buy" or "Get Quote" button
- Prints how many plans have the button vs how many don't
- Returns true if all plans have the button
```

#### "I want to test with 3 different mobile numbers"
```
Here is my existing test:
[paste TC01]

Make the test run 3 times with these mobile numbers:
9876543210, 9876543211, 9876543212

Use @DataProvider. Keep everything else the same.
Print which mobile number is being used at each step.
```

---

### 16.7 After the AI Generates Code — Checklist

Before running AI-generated code, always check:

- [ ] The class name matches the file name (e.g., `TC06_...` in both)
- [ ] The package declaration at the top says `package coverFoxTest;`
- [ ] `extends Base` is present in the class definition
- [ ] `@BeforeClass` calls `launchBrowser()`
- [ ] `@AfterClass` calls `closeBrowser()`
- [ ] The new class is added to `testngCoverFoxTestNG.xml`
- [ ] No red underlines in Eclipse (means compile errors — ask AI to fix them)

**How to add the new class to testngCoverFoxTestNG.xml:**
```xml
<test thread-count="1" name="TC06 - Your Description">
    <classes>
        <class name="coverFoxTest.TC06_CoverFox_YourDescription"/>
    </classes>
</test>
```

---

### 16.8 Prompt to Fix Eclipse Compile Errors

If Eclipse shows red underlines after AI generates code:

```
This Java code has compile errors in Eclipse. Here are the errors:
[copy the error text from Eclipse Problems tab — Window → Show View → Problems]

Here is the full code:
[paste the Java file]

Fix only the compile errors. Do not change any test logic.
Show me the corrected lines only.
```

---

## 17. Create a Custom GPT for This Project

> A **Custom GPT** is your own personal AI assistant trained specifically on this project.
> Once created, anyone on your team can open it and ask questions or request changes —
> without needing to paste code every time.

---

### 17.1 What is a Custom GPT?

A Custom GPT is a version of ChatGPT that you configure with:
- A **name and purpose** ("CoverFox Test Assistant")
- **Instructions** — rules about how it should behave
- **Knowledge files** — your actual project files it should know about
- **Capabilities** — whether it can browse the web, run code, etc.

Anyone with the link can use it. It always knows your project context.

---

### 17.2 Step-by-Step: Create a Custom GPT

**Requirements:** ChatGPT Plus or Team subscription (paid)

**Step 1 — Open GPT Builder**
1. Go to https://chatgpt.com
2. Log in to your account
3. Click **"Explore GPTs"** in the left sidebar
4. Click **"+ Create"** (top right)

---

**Step 2 — Configure the GPT (Create tab)**

You will see a chat interface. Type this to set it up:

```
Name this GPT: CoverFox QA Assistant

It helps QA engineers and non-technical users add, modify, and debug
Selenium Java TestNG automation tests for the CoverFox insurance website.
It should always follow the existing code style and patterns.
```

The builder will ask follow-up questions. Answer them, or click **"Configure"** tab for full control.

---

**Step 3 — Configure tab (full control)**

Fill in the fields:

**Name:**
```
CoverFox QA Assistant
```

**Description:**
```
Your personal assistant for the CoverFox Selenium Java automation project.
Add test cases, fix failures, modify page objects, and understand the project —
all in plain English, no coding knowledge required.
```

**Instructions (paste this entire block):**
```
You are a senior SDET and Java automation expert working on the CoverFox insurance
website test automation project.

PROJECT STACK:
- Language: Java
- Browser automation: Selenium WebDriver 4.25.0
- Test framework: TestNG 7.10.1
- Build tool: Maven
- IDE: Eclipse
- Logging: Log4j
- Data: Excel via Apache POI
- AI integration: OpenAI GPT-4o-mini via java.net.http.HttpClient

PROJECT STRUCTURE:
- src/main/java/coverFoxBase/Base.java → opens/closes browser
- src/main/java/coverFoxPOM/ → one class per webpage (Page Object Model)
- src/main/java/coverFoxUtility/Listener.java → screenshot + AI on failure
- src/main/java/ai/ → AIClient, PromptBuilder, AIResponseParser
- src/test/java/coverFoxTest/ → test cases TC01–TC05

CODING RULES YOU MUST ALWAYS FOLLOW:
1. Every test class must extend Base
2. Use @BeforeClass to call launchBrowser(), @AfterClass to call closeBrowser()
3. Use @BeforeMethod to navigate to URL and initialize page objects
4. Add System.out.println("[Step] ...") before every action
5. Add System.out.println("[Done] ...") after every action
6. Never remove existing logic — only add or modify
7. New test classes go in package coverFoxTest, file name: TC0X_CoverFox_Description.java
8. New page objects go in package coverFoxPOM
9. Always use PageFactory.initElements(driver, this) in POM constructors
10. Use @DataProvider for any test that needs multiple input combinations

AVAILABLE PAGE OBJECTS:
- CoverFoxHomePage: clickOnGenderButton(), clickOnFemaleButton(), selectGender(String)
- CoverFoxHealthPlanPage: clickOnNextButton()
- CoverFoxMemberDetailsPage: handleAgeDropDown(String), clickOnNextButton()
- CoverFoxAddressDetailsPage: enterPinCode(String), enterMobNum(String), clickOnContinueButton()
- CoverFoxResultPage: getInsuranceCount(), getCountFromText(), getCountFromBanner()

When a user asks you to add or modify a test, always output the COMPLETE file,
not just a snippet. Include the package declaration, all imports, and all methods.
```

**Conversation starters (add these):**
```
Add a test case that validates the page title
Fix this failing test: [paste error]
Modify TC03 to also test age 50 and 55
Create a page object for the plan detail page
What test cases should I write for the filter feature?
Generate a @DataProvider with 10 age combinations
```

---

**Step 4 — Upload Knowledge Files**

Click **"+ Upload files"** under the Knowledge section and upload these files from your project:

| File to upload | Why |
|---|---|
| `src/main/java/coverFoxBase/Base.java` | AI knows the base class |
| `src/main/java/coverFoxPOM/CoverFoxHomePage.java` | AI knows page object pattern |
| `src/main/java/coverFoxPOM/CoverFoxResultPage.java` | AI knows result page |
| `src/test/java/coverFoxTest/TC01_CoverFox_ValidateBannerCount.java` | AI knows test style |
| `src/test/java/coverFoxTest/TC03_CoverFox_ValidateInsuranceCountByAge.java` | AI knows DataProvider pattern |
| `pom.xml` | AI knows available dependencies |

> **How to get the files:** In Eclipse, right-click any file → **Show In → System Explorer** → navigate to the file.

---

**Step 5 — Set Capabilities**

Turn ON:
- ✅ Code Interpreter (lets GPT run and test code logic)

Turn OFF:
- ❌ Web Browsing (keeps responses focused on your project)
- ❌ Image Generation (not needed)

---

**Step 6 — Save and Share**

1. Click **"Save"** (top right)
2. Choose visibility:
   - **Only me** — private
   - **Anyone with a link** — share with your team
   - **Public** — anyone on ChatGPT can find it
3. Click **"Confirm"**
4. Copy the link and share it with your team

---

### 17.3 How to Use Your Custom GPT

Once created, your team can:

1. Open the GPT link
2. Type in plain English — no code knowledge needed:

```
add a test that checks if the coverfox homepage loads in under 3 seconds
```

```
TC03 is failing with NoSuchElementException on the age dropdown — here is the error:
[paste error]
```

```
I want to test 5 different pincodes — generate a DataProvider with
Mumbai (400001), Delhi (110001), Bangalore (560001), Chennai (600001), Pune (411001)
```

The GPT already knows your project structure and coding rules — it gives ready-to-paste Java code every time.

---

## 18. Create Claude Skills and Codex Skills

> **Skills** are reusable AI instructions saved as files inside your project.
> Instead of typing the same prompt every time, you save it once as a skill
> and invoke it with a single short command.

---

### 18.1 What is a Claude Code Skill?

A Claude Code skill is a Markdown file saved in `.claude/commands/` inside your project.

When you type `/skill-name` in Claude Code, it reads that file and executes the instructions automatically.

**Benefits:**
- One-word commands instead of long prompts
- Team members run the same instructions consistently
- Skills are version-controlled alongside your code

---

### 18.2 Step-by-Step: Create Claude Code Skills

**Step 1 — Create the commands folder**
```powershell
cd C:\Users\Codengine\git\coverfoxProject
mkdir .claude\commands
```

**Step 2 — Create your first skill file**

Create `.claude/commands/new-test.md`:

```markdown
Create a new Selenium TestNG test case for the CoverFox project.

Ask the user:
1. What should this test validate? (plain English description)
2. What page does it start on?
3. What is the expected result?
4. Should it be data-driven? If yes, ask for the data combinations.

Then generate a complete Java test class that:
- Is named TC0X_CoverFox_[Description].java (auto-increment the number)
- Is in package coverFoxTest
- Extends Base
- Has @BeforeClass calling launchBrowser()
- Has @BeforeMethod navigating to URL and initializing page objects
- Has @Test with the validation logic
- Has @AfterClass calling closeBrowser()
- Has System.out.println("[Step]") before and "[Done]" after every action
- Follows the exact same pattern as TC03_CoverFox_ValidateInsuranceCountByAge.java

After generating the class, also show the XML snippet to add to testngCoverFoxTestNG.xml.
```

**Step 3 — Create a fix-test skill**

Create `.claude/commands/fix-test.md`:

```markdown
The user has a failing Selenium test. Help them fix it.

Ask the user to paste:
1. The failing test class code
2. The full error message from the console

Then:
1. Explain the root cause in 2-3 plain English sentences (no jargon)
2. Show exactly which lines need to change — highlight with comments
3. Provide the fixed version of those lines only
4. Suggest a prevention strategy

Do NOT rewrite the entire test — change only what is broken.
```

**Step 4 — Create an add-prints skill**

Create `.claude/commands/add-prints.md`:

```markdown
Add System.out.println() print statements to a Java test class or page object.

Rules:
- Add [Step] print before every action (click, sendKeys, navigation, assertion)
- Add [Done] print after every action confirming what happened
- For results, print the actual value: [Done] Count = 12
- Do not add prints inside constructors or @FindBy fields
- Do not change any existing logic

Ask the user to paste the Java file, then return the complete updated file.
```

**Step 5 — Create a combo-test skill**

Create `.claude/commands/combo-test.md`:

```markdown
Generate a data-driven combination test for the CoverFox insurance project.

Ask the user:
1. Which variables to combine? (e.g., gender, age, pincode)
2. What values for each variable?
3. What to assert on the results page?

Then generate a complete test class using @DataProvider with all combinations,
following the exact pattern of TC05_CoverFox_ValidateInsuranceCombinations.java.

Include a summary table printed at @AfterClass showing:
- Each combination tested
- The insurance count returned
- PASS or FAIL for each
```

---

### 18.3 How to Run Claude Skills

After creating skill files, open Claude Code in your project and type:

```
/new-test
```
→ Claude asks what you want to test, then writes the full Java class.

```
/fix-test
```
→ Claude asks for the error, then shows exactly what to fix.

```
/add-prints
```
→ Paste any Java file, Claude adds all print statements.

```
/combo-test
```
→ Claude generates a full combination matrix test.

---

### 18.4 What is OpenAI Codex / GitHub Copilot?

**Codex** is the AI model behind GitHub Copilot. You do not create "skills" for Copilot the same way — instead you use **comments as instructions** directly in your Java files.

**How it works in Eclipse with Copilot installed:**

```java
// Create a method that reads all plan names from the results page and returns them as a List<String>
```
→ Copilot generates the full method automatically as you pause after the comment.

```java
// @DataProvider that supplies Male/Female × ages 18,25,30,35,40,45 × pincodes 400001,110001
```
→ Copilot generates the entire data provider array.

**Best comment patterns for this project:**
```java
// Test that validates [WHAT] on the [PAGE NAME] page
// Assert that [CONDITION] — fail with message "[FAILURE MESSAGE]"
// DataProvider with all combinations of [VAR1] and [VAR2]
// Page object method that clicks [ELEMENT] and waits [X] seconds
// Read the text content of all elements with class [CLASS NAME] and return as List
```

---

### 18.5 Share Skills With Your Team

Skills are just files inside `.claude/commands/`. They are version-controlled:

```powershell
git add .claude/commands/
git commit -m "Add Claude skills: new-test, fix-test, add-prints, combo-test"
git push origin master
```

Now anyone who clones the repo and runs `claude` in the project folder has access to all skills.

---

## 19. Build an AI Agent for This Project

> An **AI Agent** goes beyond a chatbot — it autonomously reads your files,
> writes code, runs tests, and reports results without you doing each step manually.
> This section shows how to build one specifically for this CoverFox project.

---

### 19.1 What an Agent Can Do vs a Chatbot

| Chatbot (ChatGPT / Claude.ai) | AI Agent (Claude Code / Custom Agent) |
|---|---|
| You paste code → it replies | It reads files itself |
| One question at a time | Completes multi-step tasks |
| You copy-paste the output | It writes files directly |
| You run the tests | It can run `mvn test` and read results |
| Stateless — forgets context | Remembers the whole project |

---

### 19.2 Option A — Claude Code as Your Agent (Easiest, No Coding)

Claude Code already behaves like an agent — it reads your project, writes code, and can run commands.

**Setup:**
```powershell
npm install -g @anthropic-ai/claude-code
cd C:\Users\Codengine\git\coverfoxProject
claude
```

**Give it multi-step agent tasks:**

```
Analyse the existing test cases, identify what flows are NOT yet tested,
write 2 new test cases to cover those gaps, add them to testngCoverFoxTestNG.xml,
and run mvn test to verify they compile and run
```

```
Read TC05 results from the last run, identify which combination had the lowest
insurance count, and write a new focused test case just for that combination
```

```
Scan all test classes for Thread.sleep() calls and replace them with
explicit WebDriver waits — do not break any existing logic
```

Claude Code will: read the files → plan the changes → write the code → run the tests → report back.

---

### 19.3 Option B — Build a Custom Java Agent

This option lets you build a Java program that acts as an autonomous agent —
it reads test results and automatically decides what to do next.

**Step 1 — Create the agent class**

Create `src/main/java/ai/CoverFoxTestAgent.java`:

```java
package ai;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * CoverFoxTestAgent — Autonomous AI agent that:
 * 1. Reads the latest TestNG results
 * 2. Sends failures to AI for analysis
 * 3. Prints a full diagnostic report
 *
 * Run this class directly after mvn test to get an AI report on all failures.
 */
public class CoverFoxTestAgent {

    private static final String RESULTS_FILE =
        System.getProperty("user.dir") + "/test-output/testng-results.xml";

    public static void main(String[] args) throws IOException {
        System.out.println("========================================");
        System.out.println("  CoverFox AI Test Agent Starting...");
        System.out.println("========================================");

        // Step 1: Read the TestNG results XML
        System.out.println("[Agent] Reading test results from: " + RESULTS_FILE);
        String resultsXml = new String(Files.readAllBytes(Paths.get(RESULTS_FILE)));

        // Step 2: Ask AI to analyze the results
        System.out.println("[Agent] Sending results to AI for analysis...");
        String prompt = buildResultsAnalysisPrompt(resultsXml);
        String rawResponse = AIClient.callOpenAI(prompt);
        String analysis = AIResponseParser.extractMessage(rawResponse);

        // Step 3: Print the full report
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║         AI TEST RUN ANALYSIS REPORT      ║");
        System.out.println("╚══════════════════════════════════════════╝");
        System.out.println(analysis);
        System.out.println("==========================================");
    }

    private static String buildResultsAnalysisPrompt(String resultsXml) {
        return "You are a senior QA engineer. Analyze this TestNG XML results file.\n\n"
             + "Provide:\n"
             + "1. SUMMARY: How many passed, failed, skipped\n"
             + "2. FAILURES: For each failed test — likely cause and fix\n"
             + "3. TRENDS: Any patterns in the failures\n"
             + "4. NEXT STEPS: Top 3 actions to take\n\n"
             + "TestNG Results:\n" + resultsXml.substring(0, Math.min(3000, resultsXml.length()));
    }
}
```

**Step 2 — Run the agent after tests**
```powershell
# Run tests first
mvn test

# Then run the agent
mvn exec:java -Dexec.mainClass="ai.CoverFoxTestAgent"
```

**Sample agent output:**
```
╔══════════════════════════════════════════╗
║         AI TEST RUN ANALYSIS REPORT      ║
╚══════════════════════════════════════════╝
SUMMARY
  Passed : 4
  Failed : 1
  Skipped: 0

FAILURES
  TC01_validateBannerCount
  Cause: NoSuchElementException on resultText element.
  Likely reason: Page load was slower than usual — implicit wait of 1s not enough.
  Fix: Increase Thread.sleep(4000) to Thread.sleep(7000) or use explicit WebDriverWait.

TRENDS
  All failures involve timing — page load waits are too short for current network speed.

NEXT STEPS
  1. Replace all Thread.sleep() with WebDriverWait(driver, Duration.ofSeconds(10))
  2. Increase implicitlyWait in Base.java from 1000ms to 5000ms
  3. Re-run TC01 in isolation to confirm fix works
==========================================
```

---

### 19.4 Option C — Build a Scheduled Agent (Runs Tests Automatically)

This agent runs your tests on a schedule and emails/logs the AI report.

**Step 1 — Create a batch/shell script**

Create `run-agent.bat` in the project root:

```bat
@echo off
echo ========================================
echo  Running CoverFox Tests + AI Agent
echo ========================================

cd /d C:\Users\Codengine\git\coverfoxProject

REM Set API key
set OPENAI_API_KEY=your-api-key-here

REM Run tests
echo [Step] Running Maven tests...
mvn test

REM Run AI agent to analyze results
echo [Step] Running AI analysis agent...
mvn exec:java -Dexec.mainClass="ai.CoverFoxTestAgent"

echo [Done] Agent run complete.
pause
```

**Step 2 — Schedule it using Windows Task Scheduler**

1. Search **"Task Scheduler"** in Windows Start
2. Click **"Create Basic Task"**
3. Name: `CoverFox Daily Test Run`
4. Trigger: **Daily** at a time you choose (e.g., 8:00 AM)
5. Action: **Start a Program** → browse to `run-agent.bat`
6. Click **Finish**

Now your tests run automatically every day and the AI agent reports any failures.

---

### 19.5 Option D — Claude Code Agent with Custom Instructions File

Create a file called `CLAUDE.md` in the project root. Claude Code reads this automatically every time it starts — it acts as permanent instructions for the agent.

Create `CLAUDE.md`:

```markdown
# CoverFox Project — Claude Agent Instructions

## Project Identity
This is a Selenium + Java + TestNG automation project for www.coverfox.com.

## My Role
You are the senior SDET for this project. Your job is to:
- Add new test cases when asked
- Fix failing tests
- Keep code clean and consistent
- Never break existing tests
- Always add print statements to new code

## Coding Standards
- Every test class must extend Base
- @BeforeClass → launchBrowser(), @AfterClass → closeBrowser()
- @BeforeMethod → driver.get(url), initialize page objects
- System.out.println("[Step] ...") before every action
- System.out.println("[Done] ...") after every action
- New tests: TC0X_CoverFox_Description.java in package coverFoxTest
- New page objects: CoverFox[Name]Page.java in package coverFoxPOM

## Available Page Objects
- CoverFoxHomePage     → selectGender(String), clickOnGenderButton(), clickOnFemaleButton()
- CoverFoxHealthPlanPage → clickOnNextButton()
- CoverFoxMemberDetailsPage → handleAgeDropDown(String), clickOnNextButton()
- CoverFoxAddressDetailsPage → enterPinCode(String), enterMobNum(String), clickOnContinueButton()
- CoverFoxResultPage   → getInsuranceCount(), getCountFromBanner()

## When Adding a Test
1. Check the highest existing TC number in src/test/java/coverFoxTest/
2. Name the new file TC(N+1)_CoverFox_Description.java
3. Add the class to testngCoverFoxTestNG.xml
4. Ask me before running mvn test if the change is large

## When Fixing a Test
1. Read the failing test first
2. Read the error message
3. Make the minimal change needed — do not rewrite
4. Explain the fix in plain English after applying it

## Files to Never Modify
- Base.java (unless specifically asked)
- Utility.java (unless specifically asked)
- config.properties (unless specifically asked)
```

Now every time you run `claude` in the project folder, it reads `CLAUDE.md` and behaves as your dedicated QA agent automatically — no extra prompting needed.

---

### 19.6 Full Agent Capability Summary

| Capability | Claude Code | Custom GPT | Java Agent | Scheduled Agent |
|---|---|---|---|---|
| Reads project files | ✅ Auto | ✅ Uploaded files | ✅ At runtime | ✅ At runtime |
| Writes new test files | ✅ | ✅ (you paste) | ❌ | ❌ |
| Runs `mvn test` | ✅ | ❌ | ✅ | ✅ |
| Analyzes failures with AI | ✅ | ✅ | ✅ | ✅ |
| Runs on a schedule | ❌ | ❌ | ❌ | ✅ |
| No coding needed to use | ✅ | ✅ | ❌ | ❌ |
| Free to use | ✅* | ❌ Paid | ✅ (own API key) | ✅ (own API key) |

*Claude Code free tier available

---

### 19.7 Recommended Setup for a Team

| Role | Tool to use |
|---|---|
| **Non-technical tester** | Custom GPT — just type what you want |
| **Developer / SDET** | Claude Code with `CLAUDE.md` — full agent |
| **Team lead** | Scheduled agent — daily reports |
| **Anyone writing tests** | `/new-test` skill in Claude Code |
| **Anyone fixing failures** | `/fix-test` skill in Claude Code |
