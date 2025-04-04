package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class Topic_42_Wait_PII_FindElement {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");
    WebDriverWait expilicitWait;

    @BeforeClass
    public void beforeClass() {
        if (osName.contains("Windows")) {
            System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
        } else {
            System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
        }

        driver = new FirefoxDriver();
        driver.manage().window().maximize();

        // Implicit Wait = 10s
        // Apply for element findings (findElement / findElements) -- General
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


    }

    @Test
    public void TC_01_FindElement() {
        // There are 3 normal cases
        // Case 1: Only 1 element that meets the defined locator
        // Case 2: More than 1 element were found
        // Case 3: No elements were found
    }

    @Test
    public void TC_02_FindElements() {
        // Case 1: Only 1 element that meets the defined locator
        // Case 2: More than 1 element were found
        // Case 3: No elements were found
    }

    //@AfterClass
    public void afterClass() {
        driver.quit();
    }
}