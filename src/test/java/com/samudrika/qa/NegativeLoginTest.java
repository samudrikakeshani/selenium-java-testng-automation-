
package com.samudrika.qa;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class NegativeLoginTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.get("https://demo.guru99.com/V4/");
    }

    @Test
    public void loginWithInvalidCredentials() {
        driver.findElement(By.name("uid"))
                .sendKeys("invalid_user_123");

        driver.findElement(By.name("password"))
                .sendKeys("wrong_password_123");

        driver.findElement(By.name("btnLogin")).click();

        // Wait for the application's JavaScript alert.
        wait.until(ExpectedConditions.alertIsPresent());

        String alertMessage = driver.switchTo().alert().getText();

        Assert.assertTrue(
                alertMessage.toLowerCase().contains("not valid"),
                "Expected an invalid-login message, but received: "
                        + alertMessage
        );

        driver.switchTo().alert().accept();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
