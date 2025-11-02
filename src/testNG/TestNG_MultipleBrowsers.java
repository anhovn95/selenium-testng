package testNG;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TestNG_MultipleBrowsers {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
    WebDriverWait explicitWait;
    String sUsername = "admin";
    String sPassword = "admin123";

    @Parameters("browser")
	@BeforeClass
	public void beforeClass(@Optional("Chrome") String browser) {

        if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver");
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckoriver");
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            System.setProperty("webdriver.edge.driver", projectPath + "/browserDrivers/msedgedriver");
            driver = new EdgeDriver();
        } else {
            // Handle unknown browser
            throw new IllegalArgumentException("Invalid browser name specified: " + browser);
        }

		if (osName.contains("Windows")) {
			;
		} else {
			System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver");
		}

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
        explicitWait = new WebDriverWait(driver, 5);
	}

	@Test
	public void TC_01_Login_Invalid() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[text()='Login']")));
	    driver.findElement(By.xpath("//input[@name='username']")).sendKeys(sUsername);
	    driver.findElement(By.xpath("//input[@name='password']")).sendKeys(sPassword);
        driver.findElement(By.cssSelector("button.orangehrm-login-button")).click();
    }

    @AfterClass(alwaysRun = true)
	public void afterClass() {
		driver.quit();
	}
}