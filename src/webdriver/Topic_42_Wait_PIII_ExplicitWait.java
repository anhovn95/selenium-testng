package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Topic_42_Wait_PIII_ExplicitWait {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");
    WebDriverWait explicitWait;

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
//        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

    }

    @Test
    public void TC_01_Wait_For_Attribute_Contains_Value() {
        driver.get("https://live.techpanda.org/");
        explicitWait = new WebDriverWait(driver,30);
        // Wait for Search textbox contains the placeholder text
        explicitWait.until(ExpectedConditions.attributeContains(By.cssSelector("input#search"),"placeholder","Search entire store here"));
        explicitWait.until(ExpectedConditions.attributeContains(By.cssSelector("input#search"),"placeholder","Search entire store here..."));

        explicitWait.until(ExpectedConditions.attributeContains(driver.findElement(By.cssSelector("input#search")),"placeholder","Search entire store here"));

        Assert.assertEquals(driver.findElement(By.id("search")).getAttribute("placeholder"),"Search entire store here...");
    }

    @Test
    public void TC_02_Wait_For_Element_Clickable() {
        // Example 1
        driver.get("https://automationfc.github.io/dynamic-loading/");

        // Wait for the Start button ready before clicking
        explicitWait = new WebDriverWait(driver,5);
        explicitWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div#start>button")));
        driver.findElement(By.cssSelector("div#start>button")).click();

        // Example 2
        driver.get("https://login.mailchimp.com/signup/?locale=en");
        
        explicitWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button#create-account-enabled")));
        driver.findElement(By.cssSelector("button#create-account-enabled")).click();

        // Example 3
        driver.get("https://www.fahasa.com/customer/account/login/");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

    //    explicitWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.fhs-btn-login")));
        explicitWait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector("button.fhs-btn-login"))));
    }

    @Test
    public void TC_03_Wait_For_Frame() {
        driver.get("https://netbanking.hdfcbank.com/netbanking/");
        explicitWait = new WebDriverWait(driver,10);
        // Wait and switch to frame
        explicitWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.name("login_page")));

        // Take actions with UserID
        driver.findElement(By.name("fldLoginUserId")).sendKeys("abcd");
        driver.findElement(By.cssSelector("a.login-btn")).click();

        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("keyboard")));
        Assert.assertTrue(driver.findElement(By.id("keyboard")).isDisplayed());
    }

    @Test
    public void TC_04_Wait_For_Alert() {
        driver.get("https://automationfc.github.io/basic-form/");
        explicitWait = new WebDriverWait(driver,10);

        explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Click for JS Alert']")));
        driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();

        // Wait until the alert is present
        explicitWait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();

        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("result")));
        Assert.assertEquals(driver.findElement(By.id("result")).getText(),"You clicked an alert successfully");
    }

    @Test
    public void TC_05_Wait_For_Element_Selected() {
        driver.get("https://automationfc.github.io/multiple-fields/");
        explicitWait = new WebDriverWait(driver,10);

        explicitWait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("input.form-checkbox"),29));
        List<WebElement> chkHealthList = driver.findElements(By.cssSelector("input.form-checkbox"));

        // click all the checkboxes
        for (WebElement chkHealth : chkHealthList)  {
            chkHealth.click();
        }

        // Wait until the checkboxes are selected
        for (WebElement chkHealth : chkHealthList){
            explicitWait.until(ExpectedConditions.elementToBeSelected(chkHealth));
            Assert.assertTrue(chkHealth.isDisplayed());
        }
    }

    @Test
    public void TC_06_Wait_For_GetText() {
        driver.get("http://live.techpanda.org/");
        explicitWait = new WebDriverWait(driver,10);

        // My Account on header (not visible) ==> failed
      //  explicitWait.until(ExpectedConditions.textToBe(By.xpath("//*[@id='header-account']//a[@title='My Account']"),"MY ACCOUNT"));

        // My Account on footer
        explicitWait.until(ExpectedConditions.textToBe(By.xpath("//*[@class='footer-container']//a[@title='My Account']"),"MY ACCOUNT"));
        driver.findElement(By.xpath("//*[@class='footer-container']//a[@title='My Account']")).click();

        explicitWait.until(ExpectedConditions.elementToBeClickable(By.id("send2")));
        driver.findElement(By.id("send2")).click();

        explicitWait.until(ExpectedConditions.textToBe(By.id("advice-required-entry-pass"),"This is a required field."));
        Assert.assertEquals(driver.findElement(By.id("advice-required-entry-pass")).getText(),"This is a required field.");
    }

    @Test
    public void TC_07_Url_Title() {
        driver.get("http://live.techpanda.org/");
        explicitWait = new WebDriverWait(driver,10);

        explicitWait.until(ExpectedConditions.textToBe(By.xpath("//*[@class='footer-container']//a[@title='My Account']"),"MY ACCOUNT"));
        driver.findElement(By.xpath("//*[@class='footer-container']//a[@title='My Account']")).click();

        explicitWait.until(ExpectedConditions.urlContains("/account/login/"));
        Assert.assertEquals(driver.getCurrentUrl(),"http://live.techpanda.org/index.php/customer/account/login/");

        explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='Create an Account']")));
        driver.findElement(By.xpath("//a[@title='Create an Account']")).click();

        explicitWait.until(ExpectedConditions.urlContains("/account/create/"));
        Assert.assertEquals(driver.getCurrentUrl(),"http://live.techpanda.org/index.php/customer/account/create/");
    }

    //@AfterClass
    public void afterClass() {
        driver.quit();
    }
}