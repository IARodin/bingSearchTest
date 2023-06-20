package com.example.bingsearchtest;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.net.UrlChecker;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainPageTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        // Fix the issue https://github.com/SeleniumHQ/selenium/issues/11750
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }

    @AfterEach
    public void tearDown() {
//        driver.quit();
    }

    @Test
    public void search() {
        driver.get("https://www.bing.com/");
        String input = "Selenium";
        WebElement searchField = driver.findElement(By.cssSelector("#sb_form_q"));
        searchField.sendKeys(input);
        searchField.submit();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        wait.until(ExpectedConditions.and(ExpectedConditions.attributeContains(By.cssSelector("h2 > a[href]"), "href", "selenium"), ExpectedConditions.elementToBeClickable(By.cssSelector("h2 > a[href]"))));
        List<WebElement> results = driver.findElements(By.cssSelector("h2 > a[href]"));
        clickElement(results, 0);

        assertEquals("https://www.selenium.dev/", driver.getCurrentUrl());


    }

    public void clickElement(List<WebElement> results, int num){
            results.get(num).click();
            System.out.println("Произведен клик по номеру: " + num +  " - из результатов поиска");

            ArrayList<String> tabs = new ArrayList<> (driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));
        }

}


