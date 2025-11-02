package webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Date;

import static java.time.Duration.*;

public class Topic_42_Wait_PVII_PageReady {
    WebDriver driver;
    WebDriverWait explicitWait;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    @BeforeClass
    public void beforeClass() {
        if (osName.contains("Windows")) {
            System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
        } else {
            System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver");
//            System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
//
//            FirefoxOptions options = new FirefoxOptions();
//            options.setBinary("/Applications/Firefox.app/Contents/MacOS/firefox");
        }

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        // Implicit Wait = 10s
        // Apply for element findings (findElement / findElements) -- General
//        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        explicitWait = new WebDriverWait(driver, 30);
    }

    @Test
    public void Exercise_TC_10_PageReady() {
        driver.get("https://admin-demo.nopcommerce.com");
        explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//h1[text()='Admin area demo']")));

        driver.findElement(By.cssSelector("input#Email")).clear();
        driver.findElement(By.cssSelector("input#Email")).sendKeys("admin@yourstore.com");

        driver.findElement(By.cssSelector("input#Password")).clear();
        driver.findElement(By.cssSelector("input#Password")).sendKeys("admin");

        driver.findElement(By.cssSelector("button.login-button")).click();

        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("ajaxBusy")));

        //   driver.findElement(By.xpath("//a[text()='Logout']")).click();
    }

    public String getDateTimeNow() {
        Date date = new Date();
        return date.toString();
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}