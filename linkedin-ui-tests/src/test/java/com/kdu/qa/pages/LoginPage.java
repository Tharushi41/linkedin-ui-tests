package com.kdu.qa.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By emailField = By.cssSelector("input[autocomplete^='username']");
    private final By passwordField = By.cssSelector("input[type='password']");
    private final By signInButton =
        By.xpath("//button[normalize-space(.)='Sign in']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void open() {
        driver.get("https://www.linkedin.com/login");
    }

    public void enterEmail(String email) {
    WebElement field = wait.until(driver -> {
        for (WebElement element : driver.findElements(emailField)) {
            if (element.isDisplayed() && element.isEnabled()) {
                return element;
            }
        }
        return null;
    });

    field.clear();
    field.sendKeys(email);
}

    public void enterPassword(String password) {
    WebElement field = wait.until(driver -> {
        for (WebElement element : driver.findElements(passwordField)) {
            if (element.isDisplayed() && element.isEnabled()) {
                return element;
            }
        }
        return null;
    });

    field.clear();
    field.sendKeys(password);
}

    public void clickSignIn() {
    WebElement button = wait.until(driver -> {
        for (WebElement element : driver.findElements(signInButton)) {
            if (element.isDisplayed() && element.isEnabled()) {
                return element;
            }
        }
        return null;
    });

    button.click();
}
}