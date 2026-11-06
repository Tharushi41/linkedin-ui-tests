package com.kdu.qa.tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.kdu.qa.base.BaseTest;
import com.kdu.qa.pages.LoginPage;
import com.kdu.qa.pages.SearchPage;

public class LinkedInTests extends BaseTest {

    private String requiredVariable(String name) {
        String value = System.getenv(name);

        if (value == null || value.isBlank()) {
            throw new SkipException(
                    "Set the " + name + " environment variable before running."
            );
        }

        return value;
    }

    private void stopIfCheckpointAppears() {
        String url = driver.getCurrentUrl().toLowerCase();

        if (url.contains("/checkpoint") || url.contains("/captcha")) {
            throw new SkipException(
                    "LinkedIn showed a security checkpoint or CAPTCHA. " +
                    "The test stopped without attempting to bypass it."
            );
        }
    }

    private void loginWithValidAccount() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.enterEmail(requiredVariable("LINKEDIN_EMAIL"));
        loginPage.enterPassword(requiredVariable("LINKEDIN_PASSWORD"));
        loginPage.clickSignIn();

        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(d -> !d.getCurrentUrl().contains("/login"));

        stopIfCheckpointAppears();
    }

    @Test
    public void positiveLoginTest() {
        loginWithValidAccount();

        boolean signedIn =
                driver.getCurrentUrl().contains("/feed")
                || !driver.findElements(
                        By.cssSelector("input[placeholder='Search']")
                ).isEmpty();

        Assert.assertTrue(signedIn, "Successful login should open the LinkedIn feed.");
    }

    @Test
    public void negativeLoginTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.enterEmail(requiredVariable("LINKEDIN_EMAIL"));
        loginPage.enterPassword("InvalidPasswordForTesting123!");
        loginPage.clickSignIn();

        new WebDriverWait(driver, Duration.ofSeconds(15))
        .until(d -> d.getCurrentUrl().contains("/checkpoint")
                || d.findElement(By.tagName("body"))
                    .getText()
                    .contains("Wrong email or password."));

stopIfCheckpointAppears();

        Assert.assertTrue(
        driver.findElement(By.tagName("body"))
                .getText()
                .contains("Wrong email or password."),
        "Invalid login message was not shown.");
    }

    @Test
    public void searchTest() {
        loginWithValidAccount();

        SearchPage searchPage = new SearchPage(driver);
        searchPage.search("software testing");

        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(d -> d.getCurrentUrl().contains("/search/results"));

        Assert.assertTrue(
                searchPage.hasVisibleResults(),
                "At least one search result should be visible."
        );
    }

    @Test
    public void clickFirstSearchResultTest() {
        loginWithValidAccount();

        SearchPage searchPage = new SearchPage(driver);
        searchPage.search("software testing");

        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(d -> d.getCurrentUrl().contains("/search/results"));

        Assert.assertTrue(searchPage.hasVisibleResults());
        searchPage.openFirstResult();

        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(d -> d.getCurrentUrl().contains("/in/")
                        || d.getCurrentUrl().contains("/company/"));

        Assert.assertTrue(
                driver.getCurrentUrl().contains("/in/")
                        || driver.getCurrentUrl().contains("/company/"),
                "A profile or company page should open."
        );
    }
}