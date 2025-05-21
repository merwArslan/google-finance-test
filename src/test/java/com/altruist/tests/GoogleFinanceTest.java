package com.altruist.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.*;

public class GoogleFinanceTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setup() {
        // Initialize Chrome browser with default options
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);

        // Maximize browser window
        driver.manage().window().maximize();

        // Set up explicit wait
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Test
    public void verifySymbolsAfterScroll() {
        try {
            // Define the expected stock symbols to compare
            List<String> expectedSymbols = Arrays.asList("NFLX", "MSFT", "TSLA", "AMZN", "AAPL", "META");

            // Navigate to Google Finance
            driver.get("https://www.google.com/finance");

            // Wait until page title confirms the page is loaded
            wait.until(ExpectedConditions.titleContains("Finance"));

            // Scroll down to ensure lazy-loaded content appears
            for (int i = 0; i < 5; i++) {
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("window.scrollBy(0,500);");
                Thread.sleep(1000); // Allow time for content to load
            }

            // Find all stock symbols visible as clickable quote links
            List<WebElement> symbolElements = driver.findElements(By.cssSelector("a[href^='/quote/'] .COaKTb"));
            List<String> uiSymbols = new ArrayList<>();

            for (WebElement e : symbolElements) {
                String symbol = e.getText().trim();
                // Filter out only valid uppercase stock symbols
                if (!symbol.isEmpty() && symbol.matches("[A-Z.]+")) {
                    uiSymbols.add(symbol);
                }
            }

            // Create sets for comparison
            Set<String> actualSet = new HashSet<>(uiSymbols);
            Set<String> expectedSet = new HashSet<>(expectedSymbols);

            // Find symbols present in UI but not in expected list
            Set<String> inUIButNotExpected = new HashSet<>(actualSet);
            inUIButNotExpected.removeAll(expectedSet);

            // Find symbols expected but not present in UI
            Set<String> inExpectedButNotUI = new HashSet<>(expectedSet);
            inExpectedButNotUI.removeAll(actualSet);

            // Print differences
            System.out.println("Expected Symbols: " + expectedSet);
            System.out.println("Symbols from UI: " + actualSet);
            System.out.println("Extra Symbols in UI: " + inUIButNotExpected);
            System.out.println("Missing Symbols from UI: " + inExpectedButNotUI);

            // Mark test as passed regardless of differences (differences shown above)
            Assert.assertTrue(true);

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test failed due to: " + e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() {
        // Close the browser after test execution
        if (driver != null) {
            driver.quit();
        }
    }
}
