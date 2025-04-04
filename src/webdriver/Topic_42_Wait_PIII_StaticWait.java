package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_42_Wait_PIII_StaticWait {
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
    public void TC_01_StaticWait_TimeOut_Less_Than_5_Seconds() throws InterruptedException {
        driver.get("https://automationfc.github.io/dynamic-loading/");
        driver.findElement(By.xpath("//*[@id='start']/button")).click();
        sleepInSecond(3);
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id='finish']/h4")).getText(),"Hello World!");
    }

    @Test
    public void TC_02_StaticWait_TimeOut_Equals_5_Seconds() throws InterruptedException {
        driver.get("https://automationfc.github.io/dynamic-loading/");
        driver.findElement(By.xpath("//*[@id='start']/button")).click();
        sleepInSecond(5);
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id='finish']/h4")).getText(),"Hello World!");
    }

    @Test
    public void TC_03_StaticWait_TimeOut_Greater_Than_5_Seconds() throws InterruptedException {
        driver.get("https://automationfc.github.io/dynamic-loading/");
        driver.findElement(By.xpath("//*[@id='start']/button")).click();
        sleepInSecond(10);
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id='finish']/h4")).getText(),"Hello World!");
    }

    //@AfterClass
    public void afterClass() {
        driver.quit();
    }

    public void sleepInSecond(long timeInSecond){
        try {
            Thread.sleep(timeInSecond * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}