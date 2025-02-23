package commons;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.*;
import org.testng.Assert;
import pageObject.admin.organization.GeneralInforPageObject;

import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.concurrent.TimeUnit;

public class BaseTest extends BasePage{
    protected WebDriver driver;

    //----------------------Annotations for Testcases----------------------------

    @BeforeTest
    @Parameters({"browser", "headless"})
    public void setUp(String browserName, @Optional("false") boolean headless){
        driver = openBrowser(browserName, headless);
    }
    @AfterTest(alwaysRun = true)
    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
    public WebDriver openBrowser (String browserName, boolean headless) {
        if (browserName.equalsIgnoreCase("Chrome")) {
            ChromeOptions options = new ChromeOptions();
            if (headless) {
                options.addArguments("--headless=new"); // run in Headless mode
            }
            options.addArguments("--disable-gpu"); // stability
            options.addArguments("--window-size=1920,1080"); // size of screen
            driver = new ChromeDriver(options);
        } else if (browserName.equalsIgnoreCase("Edge")) {
            EdgeOptions options = new EdgeOptions();
            if (headless) {
                options.addArguments("--headless=new");
            }
            driver = new EdgeDriver(options);
        } else if (browserName.equalsIgnoreCase("Firefox")) {
                FirefoxOptions options = new FirefoxOptions();
                if (headless) {
                    options.addArguments("--headless");
                }
                driver = new FirefoxDriver(options);
        } else {
            throw new RuntimeException("Browser name is not valid!!!");
        }
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        //Login
        openPageURL(driver, GlobalConstants.URL);
        waitForElementVisible(driver,"//input[@name='username']");
        sendkeyToElement(driver,"//input[@name='username']","Admin");
        waitForElementVisible(driver,"//input[@name='password']");
        sendkeyToElement(driver,"//input[@name='password']","admin123");
        clickToElement(driver,"//button[@type='submit']");
        return driver;
    }

//----------------------Common Functions for all Testcases----------------------------
    public void verifyTrue(boolean condition) {
        Assert.assertTrue(condition);
    }

    public void verifyFalse(boolean condition) {
        Assert.assertFalse(condition);
    }

    public void verifyEquals(Object actual, Object expected) {
        Assert.assertEquals(actual, expected);
    }

    public int getRandomNumber() {
        Random rand = new Random();
        return rand.nextInt(1000);  // random numbers 0 - 999
    }

    public String getCurrentDate(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(new Date());
    }
}
