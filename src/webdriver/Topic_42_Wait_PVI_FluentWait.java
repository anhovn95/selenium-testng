package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Topic_42_Wait_PVI_FluentWait {
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
        }

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        // Implicit Wait = 10s
        // Apply for element findings (findElement / findElements) -- General
//        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

    }


    public void TC_01_Element_Found() {
        driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
        explicitWait = new WebDriverWait(driver,15);

        driver.get("https://www.facebook.com");

        // Implicit --> Not wait 15s
        System.out.println("1 - Start: " + getDateTimeNow());
        driver.findElement(By.xpath("//button[@name='login']"));
        System.out.println("1 - End: " + getDateTimeNow());

        // Explicit --> Not wait 15s
        System.out.println("2 - Start: " + getDateTimeNow());
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@name='login']")));
        System.out.println("2 - End: " + getDateTimeNow());
    }


    public void TC_02_Element_Not_Found_Only_Implicit() {
        driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
        driver.get("https://www.facebook.com");

        // Implicit
        // Repeat finding every 0.5s
        // If found -> Not wait 15s
        // If not found --> Wait 15s then throw NoSuchElementException and test case failed
        System.out.println("1 - Start: " + getDateTimeNow());
        driver.findElement(By.xpath("//button[@name='selenium']"));
        System.out.println("1 - End: " + getDateTimeNow());
    }

    @Test
    public void TC_02B_Element_Not_Found_Only_Implicit_But_Used_Explicit() {
        driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
        explicitWait = new WebDriverWait(driver, 0); // 0s ~ not using explicit

        driver.get("https://www.facebook.com");

        System.out.println("1 - Start: " + getDateTimeNow());
        try {
            explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@name='selenium']")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("1 - End: " + getDateTimeNow());
    }


    public void TC_03_Element_Not_Found_Implicit_And_Explicit() {
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        explicitWait = new WebDriverWait(driver,10);

        driver.get("https://www.facebook.com");

        // Explicit + Implicit
        // Implicit only applies to findElement ---> execute first
        // Explicit applies to Element conditions --> execute after findElement method 0.5-1s
        // In this case, both timeouts are applied (as async)

        // Total execution time is 11-12s
        System.out.println("1 - Start: " + getDateTimeNow());
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@name='selenium']")));
        System.out.println("1 - End: " + getDateTimeNow());
    }

    @Test
    public void TC_04_Element_Not_Found_Only_Explicit_By() {
        // If implicit wait is not set => default timeout = 0s
        explicitWait = new WebDriverWait(driver,5);

        driver.get("https://www.facebook.com");

        System.out.println("1 - Start: " + getDateTimeNow());

        try {
            explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@name='selenium']")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("1 - End: " + getDateTimeNow());
    }

    @Test
    public void TC_05_Element_Not_Found_Only_Explicit_WebElement() {
// If implicit wait is not set => default timeout = 0s
        explicitWait = new WebDriverWait(driver,5);

        driver.get("https://www.facebook.com");

        System.out.println("1 - Start: " + getDateTimeNow());

        try {
            explicitWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//button[@name='selenium']"))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("1 - End: " + getDateTimeNow());
    }

    public String getDateTimeNow(){
        Date date = new Date();
        return date.toString();
    }

    //@AfterClass
    public void afterClass() {
        driver.quit();
    }
}