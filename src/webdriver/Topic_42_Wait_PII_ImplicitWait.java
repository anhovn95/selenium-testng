package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class Topic_42_Wait_PII_ImplicitWait {
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
        // driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    }

    @Test
    public void TC_01_ImplicitWait_TimeOut_Less_Than_5_Seconds() {
        driver.get("https://automationfc.github.io/dynamic-loading/");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@id='start']/button")).click();
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id='finish']/h4")).getText(),"Hello World!");
    }

    @Test
    public void TC_02_ImplicitWait_TimeOut_Equals_5_Seconds() {
        driver.get("https://automationfc.github.io/dynamic-loading/");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@id='start']/button")).click();
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id='finish']/h4")).getText(),"Hello World!");
    }

    @Test
    public void TC_03_ImplicitWait_TimeOut_Greater_Than_5_Seconds() {
        driver.get("https://automationfc.github.io/dynamic-loading/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@id='start']/button")).click();
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id='finish']/h4")).getText(),"Hello World!");
    }

    //@AfterClass
    public void afterClass() {
        driver.quit();
    }
}