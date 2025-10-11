package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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

    @Test
    public void TC_01_Wait_For_Attribute_Contains_Value() {
        driver.get("https://live.techpanda.org/");
        explicitWait = new WebDriverWait(driver, 30);
        // Wait for Search textbox contains the placeholder text
        explicitWait.until(ExpectedConditions.attributeContains(By.cssSelector("input#search"), "placeholder", "Search entire store here"));
        explicitWait.until(ExpectedConditions.attributeContains(By.cssSelector("input#search"), "placeholder", "Search entire store here..."));

        explicitWait.until(ExpectedConditions.attributeContains(driver.findElement(By.cssSelector("input#search")), "placeholder", "Search entire store here"));

        Assert.assertEquals(driver.findElement(By.id("search")).getAttribute("placeholder"), "Search entire store here...");
    }

    @Test
    public void TC_02_Wait_For_Element_Clickable() {
        // Example 1
        driver.get("https://automationfc.github.io/dynamic-loading/");

        // Wait for the Start button ready before clicking
        explicitWait = new WebDriverWait(driver, 5);
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
        explicitWait = new WebDriverWait(driver, 10);
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
        explicitWait = new WebDriverWait(driver, 10);

        explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Click for JS Alert']")));
        driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();

        // Wait until the alert is present
        explicitWait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();

        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("result")));
        Assert.assertEquals(driver.findElement(By.id("result")).getText(), "You clicked an alert successfully");
    }

    @Test
    public void TC_05_Wait_For_Element_Selected() {
        driver.get("https://automationfc.github.io/multiple-fields/");
        explicitWait = new WebDriverWait(driver, 10);

        explicitWait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("input.form-checkbox"), 29));
        List<WebElement> chkHealthList = driver.findElements(By.cssSelector("input.form-checkbox"));

        // click all the checkboxes
        for (WebElement chkHealth : chkHealthList) {
            chkHealth.click();
        }

        // Wait until the checkboxes are selected
        for (WebElement chkHealth : chkHealthList) {
            explicitWait.until(ExpectedConditions.elementToBeSelected(chkHealth));
            Assert.assertTrue(chkHealth.isDisplayed());
        }
    }

    @Test
    public void TC_06_Wait_For_GetText() {
        driver.get("http://live.techpanda.org/");
        explicitWait = new WebDriverWait(driver, 10);

        // My Account on header (not visible) ==> failed
        //  explicitWait.until(ExpectedConditions.textToBe(By.xpath("//*[@id='header-account']//a[@title='My Account']"),"MY ACCOUNT"));

        // My Account on footer
        explicitWait.until(ExpectedConditions.textToBe(By.xpath("//*[@class='footer-container']//a[@title='My Account']"), "MY ACCOUNT"));
        driver.findElement(By.xpath("//*[@class='footer-container']//a[@title='My Account']")).click();

        explicitWait.until(ExpectedConditions.elementToBeClickable(By.id("send2")));
        driver.findElement(By.id("send2")).click();

        explicitWait.until(ExpectedConditions.textToBe(By.id("advice-required-entry-pass"), "This is a required field."));
        Assert.assertEquals(driver.findElement(By.id("advice-required-entry-pass")).getText(), "This is a required field.");
    }

    @Test
    public void TC_07_Url_Title() {
        driver.get("http://live.techpanda.org/");
        explicitWait = new WebDriverWait(driver, 10);

        explicitWait.until(ExpectedConditions.textToBe(By.xpath("//*[@class='footer-container']//a[@title='My Account']"), "MY ACCOUNT"));
        driver.findElement(By.xpath("//*[@class='footer-container']//a[@title='My Account']")).click();

        explicitWait.until(ExpectedConditions.urlContains("/account/login/"));
        Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/login/");

        explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='Create an Account']")));
        driver.findElement(By.xpath("//a[@title='Create an Account']")).click();

        explicitWait.until(ExpectedConditions.urlContains("/account/create/"));
        Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/create/");
    }

    @Test
    public void TC_08_Exercise_04_Invisible() {
        driver.get("https://automationfc.github.io/dynamic-loading/");

        // Check with timeout = 3s ==> Failed because the loading icon visible for 5 seconds
        // Check with timeout = 5s ==> Passed
        // Check with timeout = 15s ==> Passed. The run time of this test case is around 5 seconds

        // Wait for the Start button ready before clicking
        explicitWait = new WebDriverWait(driver, 15);
        explicitWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div#start>button")));
        driver.findElement(By.cssSelector("div#start>button")).click();

        // Wait for loading icon invisible
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loading")));
        Assert.assertEquals(driver.findElement(By.cssSelector("div#finish>h4")).getText(), "Hello World!");
    }

    @Test
    public void TC_09_Exercise_05_Visible() {
        driver.get("https://automationfc.github.io/dynamic-loading/");

        // Check with timeout = 3s ==> Failed because it reaches the timeout before the loading icon is invisible
        // Check with timeout = 5s ==> Passed
        // Check with timeout = 15s ==> Passed. The run time of this test case is around 5 seconds

        // Wait for the Start button ready before clicking
        explicitWait = new WebDriverWait(driver, 5);
        explicitWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div#start>button")));
        driver.findElement(By.cssSelector("div#start>button")).click();

        // Wait for the following step visible (Hello World)
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#finish>h4")));
        Assert.assertEquals(driver.findElement(By.cssSelector("div#finish>h4")).getText(), "Hello World!");
    }

    @Test
    public void TC_10_Exercise_06() {
        driver.get("https://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx");
        explicitWait = new WebDriverWait(driver, 30);

        // Wait for the calendar visible
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table.rcMainTable")));
        Assert.assertEquals(driver.findElement(By.cssSelector(".RadAjaxPanel>span")).getText(), "No Selected Dates to display.");

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy"));
        System.out.println(today);

        int dayOfToday = LocalDate.now().getDayOfMonth();

        List<WebElement> dates = driver.findElements(By.xpath("//table[@class='rcMainTable']/tbody//td/a"));

        for (WebElement date : dates) {
            if (date.getText().equals(String.valueOf(dayOfToday))) {
                date.click();
                break;
            }
        }

        // Wait until the Ajax loading icon invisible
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[not(@style='display:none;')]/*[@class='raDiv']")));

        // Wait until the day is selected (visible)
        // Cannot use elementToBeSelected because it is a hyperlink, not a checkbox or radio button
        explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'rcSelected')]//a[text()='" + dayOfToday + "']")));

        explicitWait.until(ExpectedConditions.textToBe(By.cssSelector(".RadAjaxPanel>span"), today));
        Assert.assertEquals(driver.findElement(By.cssSelector(".RadAjaxPanel>span")).getText(), today);
    }

    @Test
    public void TC_11_Exercise_07() {
        String picture1 = "picture1.png";
        String picture2 = "picture2.jpg";
        String picture3 = "picture3.jpg";

        String pathToPicture1 = projectPath + File.separator + "pictures" + File.separator + picture1;
        String pathToPicture2 = projectPath + File.separator + "pictures" + File.separator + picture2;
        String pathToPicture3 = projectPath + File.separator + "pictures" + File.separator + picture3;

        explicitWait = new WebDriverWait(driver, 30);
        driver.get("https://gofile.io/?t=uploadFiles");
        // Wait until the loading spin invisible
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='index_main']//*[contains(@class,'animate-spin')]")));

        // Wait until the upload button visible and upload file
        explicitWait.until(ExpectedConditions.elementToBeClickable(By.id("home_uploadFile")));
        driver.findElement(By.cssSelector("input.uploadInput")).sendKeys(pathToPicture1 + "\n" + pathToPicture2 + "\n" + pathToPicture3);

        // Wait until the progress bar invisible
        explicitWait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(By.cssSelector("div.file-progressbar"))));

        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='Upload Complete']")));

        // Verify the QR code is displayed
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[starts-with(@id,'qrcode')]/img")));
        Assert.assertTrue(driver.findElement(By.xpath("//div[starts-with(@id,'qrcode')]/img")).isDisplayed());

        // Wait and click on the Folder link
        explicitWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.linkSuccessCard"))).click();

        // Wait until the file table is displayed
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("filemanager_itemslist")));
        explicitWait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//*[@id='filemanager_itemslist']/*[not(contains(@class,'hidden'))]"))));
        Assert.assertEquals(driver.findElements(By.xpath("//*[@id='filemanager_itemslist']/*[not(contains(@class,'hidden'))]")).size(), 3);
        Assert.assertEquals(driver.findElements(By.cssSelector("button.item_play")).size(), 3);
        Assert.assertEquals(driver.findElements(By.cssSelector("button.item_download")).size(), 3);
    }

    //@AfterClass
    public void afterClass() {
        driver.quit();
    }
}