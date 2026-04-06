@echo off
setlocal enabledelayedexpansion

:: ============================================================
::  CoverFox AI Test Agent — One-Click Runner
::
::  What this script does:
::    1. Checks your API key is set
::    2. Shows a menu to pick what you want to do
::    3. Runs mvn test (optional)
::    4. Runs the AI agent in the mode you chose
:: ============================================================

title CoverFox AI Test Agent

cls
echo.
echo  ============================================================
echo   COVERFOX AI TEST AGENT
echo   Powered by OpenAI GPT-4o-mini
echo  ============================================================
echo.

:: ── Step 1: Check project directory ─────────────────────────
if not exist "pom.xml" (
    echo  [ERROR] pom.xml not found.
    echo  [ERROR] Please run this script from the project root folder:
    echo          C:\Users\Codengine\git\coverfoxProject
    echo.
    pause
    exit /b 1
)

:: ── Step 2: Check API key ────────────────────────────────────
if "%OPENAI_API_KEY%"=="" (
    echo  [WARNING] OPENAI_API_KEY is not set.
    echo.
    set /p USER_KEY="  Enter your OpenAI API key (or press Enter to skip AI): "
    if not "!USER_KEY!"=="" (
        set OPENAI_API_KEY=!USER_KEY!
        echo  [OK] API key set for this session.
    ) else (
        echo  [INFO] No API key — agent will run but AI analysis will be skipped.
    )
    echo.
)

:: ── Step 3: Show menu ────────────────────────────────────────
echo  What would you like to do?
echo.
echo   [1] Run tests + analyze results with AI     (most common)
echo   [2] Analyze last run only (no new test run)
echo   [3] Suggest new test cases using AI
echo   [4] Analyze test coverage gaps using AI
echo   [5] Run all 4 modes in sequence
echo   [6] Exit
echo.
set /p CHOICE="  Enter your choice (1-6): "
echo.

:: ── Step 4: Execute chosen mode ─────────────────────────────

if "%CHOICE%"=="1" goto RUN_AND_ANALYZE
if "%CHOICE%"=="2" goto ANALYZE_ONLY
if "%CHOICE%"=="3" goto SUGGEST
if "%CHOICE%"=="4" goto COVERAGE
if "%CHOICE%"=="5" goto RUN_ALL
if "%CHOICE%"=="6" goto EXIT
echo  [ERROR] Invalid choice. Please enter 1-6.
pause
exit /b 1


:: ──────────────────────────────────────────────────────────────
:RUN_AND_ANALYZE
echo  ============================================================
echo   STEP 1/2 — Running Maven Tests
echo  ============================================================
echo.
call mvn test
if %ERRORLEVEL% neq 0 (
    echo.
    echo  [INFO] Some tests failed. Running AI failure analysis...
) else (
    echo.
    echo  [INFO] All tests passed. Running AI insights...
)
echo.
echo  ============================================================
echo   STEP 2/2 — AI Analysis
echo  ============================================================
echo.
call mvn exec:java -Dexec.mainClass="ai.CoverFoxTestAgent" -Dexec.args="analyze" -q
goto END


:: ──────────────────────────────────────────────────────────────
:ANALYZE_ONLY
echo  ============================================================
echo   Analyzing Last Test Run with AI
echo  ============================================================
echo.
if not exist "test-output\testng-results.xml" (
    echo  [ERROR] No test results found at test-output\testng-results.xml
    echo  [INFO]  Run option [1] first to generate results.
    pause
    goto EXIT
)
call mvn exec:java -Dexec.mainClass="ai.CoverFoxTestAgent" -Dexec.args="analyze" -q
goto END


:: ──────────────────────────────────────────────────────────────
:SUGGEST
echo  ============================================================
echo   Suggesting New Test Cases with AI
echo  ============================================================
echo.
call mvn exec:java -Dexec.mainClass="ai.CoverFoxTestAgent" -Dexec.args="suggest" -q
goto END


:: ──────────────────────────────────────────────────────────────
:COVERAGE
echo  ============================================================
echo   Analyzing Test Coverage Gaps with AI
echo  ============================================================
echo.
call mvn exec:java -Dexec.mainClass="ai.CoverFoxTestAgent" -Dexec.args="coverage" -q
goto END


:: ──────────────────────────────────────────────────────────────
:RUN_ALL
echo  ============================================================
echo   FULL RUN: Tests + All 3 Agent Modes
echo  ============================================================
echo.

echo  --- Running Maven Tests ---
call mvn test
echo.

echo  --- Mode 1: Analyze Results ---
call mvn exec:java -Dexec.mainClass="ai.CoverFoxTestAgent" -Dexec.args="analyze" -q
echo.

echo  --- Mode 2: Suggest New Tests ---
call mvn exec:java -Dexec.mainClass="ai.CoverFoxTestAgent" -Dexec.args="suggest" -q
echo.

echo  --- Mode 3: Coverage Analysis ---
call mvn exec:java -Dexec.mainClass="ai.CoverFoxTestAgent" -Dexec.args="coverage" -q
goto END


:: ──────────────────────────────────────────────────────────────
:END
echo.
echo  ============================================================
echo   Agent run complete.
echo  ============================================================
echo.
pause
goto EXIT

:EXIT
endlocal
exit /b 0
