package com.kdu.qa.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By searchLauncher =
            By.cssSelector("div[role='button'][aria-label='Search']");

    private final By searchBox =
            By.cssSelector("input[placeholder='Search']");

    private final By resultLinks =
        By.cssSelector("a[href*='/jobs/view/'], a[href*='/in/'], a[href*='/company/']");

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void search(String query) {
    wait.until(d -> {
        try {
            for (WebElement input : d.findElements(searchBox)) {
                if (input.isDisplayed() && input.isEnabled()) {
                    return true;
                }
            }

            WebElement launcher = d.findElement(searchLauncher);
            if (launcher.isDisplayed() && launcher.isEnabled()) {
                launcher.click();
            }
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            return false;
        }

        return false;
    });

    WebElement box =
            wait.until(ExpectedConditions.elementToBeClickable(searchBox));
    box.sendKeys(query);
    box.sendKeys(Keys.ENTER);
}

    public boolean hasVisibleResults() {
    try {
        return wait.until(driver -> {
            for (WebElement result : driver.findElements(resultLinks)) {
                if (result.isDisplayed()) {
                    return true;
                }
            }
            return false;
        });
    } catch (Exception e) {
        return false;
    }
}

public void openFirstResult() {
    WebElement firstResult = wait.until(driver -> {
        for (WebElement result : driver.findElements(resultLinks)) {
            if (result.isDisplayed() && result.isEnabled()) {
                return result;
            }
        }
        return null;
    });

    firstResult.click();
}
}