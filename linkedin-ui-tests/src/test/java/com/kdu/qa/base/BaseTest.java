package com.kdu.qa.base;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
    }

    @AfterMethod(alwaysRun = true)
public void tearDown(ITestResult result) {
    if (driver != null) {
        try {
            saveScreenshot(result.getMethod().getMethodName());
        } finally {
            driver.quit();
        }
    }
}

    private void saveScreenshot(String testName) {
        try {
            Path folder = Path.of("screenshots");
            Files.createDirectories(folder);

            File screenshot =
                    ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            Files.copy(
                    screenshot.toPath(),
                    folder.resolve(testName + ".png"),
                    StandardCopyOption.REPLACE_EXISTING
            );
        } catch (Exception e) {
            System.out.println("Screenshot could not be saved: " + e.getMessage());
        }
    }
}