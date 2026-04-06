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
