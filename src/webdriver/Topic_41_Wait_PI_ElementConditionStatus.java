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

public class Topic_41_Wait_PI_ElementConditionStatus {
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
        // Apply for element findings (findElement / findElements) -- General
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        // Apply for the status of element -- specific case
        expilicitWait = new WebDriverWait(driver, 30);
    }

    @Test
    public void TC_01_Visible_Displayed() {
        driver.get("https://www.facebook.com/");
        WebElement emailTxt = driver.findElement(By.id("email"));
        expilicitWait.until(ExpectedConditions.visibilityOf(emailTxt));
        emailTxt.sendKeys("anho@gmail.com");
    }

    @Test
    public void TC_02_Invisible_Undisplayed_Case1() {
        // ***** Element is invisible on the screen (UI) but present in DOM *****
        driver.get("https://www.facebook.com/");
        // Click to navigate to Sign Up page
        driver.findElement(By.xpath("//a[text()='Sign Up']")).click();
        expilicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Create a new account']")));
        driver.findElement(By.xpath("//*[not(@class='clearfix')]/button[text()='Sign Up']")).click();

        // ===== Example 1: Check the Pronoun Preference dropdown is only displayed when user selects option "Custom" for Gender field
        // The dropdown is undisplayed (but present in DOM)
        expilicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.name("preferred_pronoun")));
        driver.findElement(By.xpath("//input[@id='sex' and @value='-1']")).click();
        // The dropdown is displayed
        expilicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.name("preferred_pronoun")));
        Select slPronoun = new Select(driver.findElement(By.name("preferred_pronoun")));
        slPronoun.selectByValue("1");

        // ===== Example 2: Check the tooltip for error message is only displayed when user focuses on the textbox
        // Click on the textbox to make the message appear in DOM
        driver.findElement(By.name("reg_email__")).click();
        // Click on another textbox to make the message undisplayed
        driver.findElement(By.name("reg_passwd__")).click();
        expilicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[text()=\"You'll use this when you log in and if you ever need to reset your password.\"]")));
        // Click on the textbox and wait for this message being displayed again
        driver.findElement(By.name("reg_email__")).click();
        expilicitWait.until((ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()=\"You'll use this when you log in and if you ever need to reset your password.\"]"))));
    }

    @Test
    public void TC_02_Invisible_Undisplayed_Case2() {
        // ***** The element is invisible on UI and also absent in DOM *****
        // The running time of this case is longer than TC1.
        // This takes time to wait for finding the element first (the time is declared when creating the instance of explicit wait)
        driver.get("https://www.facebook.com/");
        expilicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.name("preferred_pronoun")));
    }

    @Test
    public void TC_03_Presence_Case1() {
        // ***** The element is visible on UI *****
		driver.get("https://www.facebook.com/");
		expilicitWait.until(ExpectedConditions.presenceOfElementLocated(By.id("email")));
    }

    @Test
    public void TC_04_Presence_Case2() {
        // ***** The element is invisible on UI (i.e. the options of a long dropdown list which user needs to scroll down to see*****
		driver.get("https://www.facebook.com/");
		// Click to navigate to Sign Up page
		driver.findElement(By.xpath("//a[text()='Sign Up']")).click();
		By ddlYearLocator = By.cssSelector("select#year");
		Select ddlYear = new Select(driver.findElement(ddlYearLocator));
		driver.findElement(ddlYearLocator).click();
		// The option "1910" is not present on UI. User needs to scroll down to the bottom of the dropdown list to see this option.
		// Wait until the option "1910" is present on the DOM
		expilicitWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[@id='year']/option[@value='1910']")));
		ddlYear.selectByValue("1910");
    }

	@Test
	public void TC_05_Staleness() {
		// ***** The element is present on DOM first, and then be removed from DOM
		driver.get("https://www.facebook.com/");
		// Textbox "Email address or phone number" is displayed and present in DOM
		WebElement txtUsername = driver.findElement(By.id("email"));
		// Click "Forgotten account?" to navigate to another page (the above element is no longer present in DOM)
		driver.findElement(By.xpath("//a[text()='Forgotten password?']")).click();
		expilicitWait.until(ExpectedConditions.stalenessOf(txtUsername));
	}

    //@AfterClass
    public void afterClass() {
        driver.quit();
    }
}