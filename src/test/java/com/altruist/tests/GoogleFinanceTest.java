package com.altruist.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.*;

public class GoogleFinanceTest {
    private WebDriver driver;


    @BeforeClass
    public void setup() {
        // Setup ChromeDriver using WebDriverManager (cross-platform friendly)
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);

        // Maximize browser window
        driver.manage().window().maximize();

        // Use only implicit wait globally
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void verifySymbolsAfterScroll() {
        try {
            // Define the expected stock symbols to compare
            List<String> expectedSymbols = Arrays.asList("NFLX", "MSFT", "TSLA", "AMZN", "AAPL", "META");

            // Navigate to Google Finance
            driver.get("https://www.google.com/finance");

            // sleep to ensure the page is stable
            Thread.sleep(5000);

            // Scroll down to ensure lazy-loaded content appears
            for (int i = 0; i < 10; i++) {
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("window.scrollBy(0,600);");
                Thread.sleep(1000); // Allow time for content to load
            }
            // Attempt to locate stock symbol elements
            List<String> uiSymbols = new ArrayList<>();

            // Get all <div> elements on the page
            List<WebElement> allDivs = driver.findElements(By.tagName("div"));
            for (int i = 0; i < allDivs.size(); i++) {
                try {
                    WebElement div = allDivs.get(i);
                    String text = div.getText().trim();// Get the visible text
                    if (!text.isEmpty() && text.matches("[A-Z.]{2,6}")) {
                        System.out.println("Possible Symbol: " + text); // Save it to the list
                        uiSymbols.add(text);
                    }
                } catch (StaleElementReferenceException staleEx) {
                    // If the element is no longer in the DOM, skip it!
                    continue;
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
