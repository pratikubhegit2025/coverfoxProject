Fix a failing Selenium TestNG test in the CoverFox project.

Ask the user to provide (all at once):
- The name of the failing test class or method
- The full error message and stack trace from the console

Then:
1. Read the failing test file from src/test/java/coverFoxTest/
2. Read the relevant page object file(s) from src/main/java/coverFoxPOM/
3. Analyze the root cause

Respond with EXACTLY this structure:

ROOT CAUSE
[2-3 plain English sentences explaining WHY it failed. No jargon.]

LINES TO CHANGE
[Show ONLY the specific lines that need to change, with before/after comparison]
Before:
  [old code]
After:
  [new code]

WHY THIS FIXES IT
[1-2 sentences explaining the fix]

PREVENTION
[1 sentence on how to avoid this in future tests]

Rules:
- Do NOT rewrite the entire test class
- Do NOT remove any print statements
- Change only what is broken
- If the fix requires a page object change, show that too
- After showing the fix, ask if the user wants you to apply it directly to the file
