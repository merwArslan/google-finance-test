#  QA Selenium Test

## Overview

This project is a take-home QA Automation exercise for Altruist.

It automates the validation of stock symbols displayed on [Google Finance](https://www.google.com/finance) using Java and Selenium WebDriver. The test scrolls through the dynamic content, extracts visible symbols, and compares them to a predefined expected list.

---

## What the Test Does

- Opens Chrome using Selenium WebDriver
- Navigates to [Google Finance](https://www.google.com/finance)
- Scrolls to load dynamically rendered content (lazy-loaded)
- Collects all possible stock symbols from visible page elements
- Compares them against this expected list:
- NFLX, MSFT, TSLA, AMZN, AAPL, META

- Logs:
- Matching symbols
- Extra symbols from UI (not in expected list)
- Missing symbols (expected but not found)

The test also handles stale elements and DOM updates using exception handling.

---

## Technologies Used

- **Java 17**
- **Selenium WebDriver 4.20.0**
- **TestNG 7.10.1**
- **WebDriverManager 5.6.3**
- **Maven** (for dependency management and running tests)

---

## How to Run the Test

### Prerequisites

- Java 17+ installed
- Maven installed and added to your system path
- Google Chrome installed

### Clone the Project

```bash
git clone https://github.com/your-username/altruist-qa-test.git
cd altruist-qa-test

# Run the test
mvn test
```

### Project Structure

altruist-qa-test
├── pom.xml
├── README.md
└── src/
    └── test/
        └── java/
            └── com/
                └── altruist/
                    └── tests/
                        └── GoogleFinanceTest.java
                        
## Author

**Merve Arslan**  
QA Automation Engineer – Java, Selenium, API, Trading & Finance Platforms  
New Jersey, USA  
Email: amerweska@gmail.com  
LinkedIn: linkedin.com/in/merve-arslan
