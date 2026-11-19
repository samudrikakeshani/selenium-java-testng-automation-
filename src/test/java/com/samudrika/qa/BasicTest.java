package com.samudrika.qa;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class BasicTest {

    private WebDriver driver;
    private SoftAssert sa;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);
        sa = new SoftAssert();
    }

    @Test
    public void login() {
        driver.get("https://demo.guru99.com/V4/");

        sa.assertTrue(
                driver.getCurrentUrl().contains("demo.guru99.com/V4"),
                "Login page URL should be correct"
        );

        sa.assertTrue(
                driver.getPageSource().contains("uid"),
                "Login page should contain the username field"
        );

        driver.findElement(By.name("uid")).sendKeys("mngr26593");
        driver.findElement(By.name("password")).sendKeys("Test@123");
        driver.findElement(By.name("btnLogin")).click();

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(30));

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//tr[@class='heading3']/td")
                )
        );

        String actualText = driver.findElement(
                By.xpath("//tr[@class='heading3']/td")
        ).getText();

        sa.assertEquals(
                actualText,
                "Manger Id : mngr26593",
                "The expected manager home page should appear"
        );

        sa.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
