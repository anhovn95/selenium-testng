package testNG;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Random;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class TestNG_DataProvider {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
    WebDriverWait explicitWait;
    String employeeID;

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		} else {
			System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver");
		}

		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
        explicitWait = new WebDriverWait(driver, 5);
	}

    @DataProvider(name = "InvalidCredential")
    public static Object[][] invalidUsernamePwd() {
        return new Object[][]{
                {"admin","admin"},
                {"admin123", "admin"},
                {"admin", "admin1234"}
        };
    }

	@Test(dataProvider = "InvalidCredential")
	public void TC_01_Login_Invalid(String sUsername, String sPassword) {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[text()='Login']")));
	    driver.findElement(By.xpath("//input[@name='username']")).sendKeys(sUsername);
	    driver.findElement(By.xpath("//input[@name='password']")).sendKeys(sPassword);
        driver.findElement(By.cssSelector("button.orangehrm-login-button")).click();
    }

    @DataProvider(name = "employee")
    public Object[][] employeeData() {
        return new Object[][]{
                {"Peter", "Crouch", "peter" + new Random().nextInt(9999) + "@hotmail.com", "Testing111###"},
                {"John", "Terry", "john" + new Random().nextInt(9999) + "@hotmail.com", "Testing111###"},
                {"Luka", "Modric", "luka" + new Random().nextInt(9999) + "@hotmail.com", "Testing111###"}
        };
    }

    @Test(dataProvider = "employee")
    public void TC_02_OrangeHRM(String firstName, String lastName, String emailAddress, String password) throws InterruptedException {
        driver.get("https://opensource-demo.orangehrmlive.com/");

        // Login
        driver.findElement(By.cssSelector("input[name='username']")).sendKeys("Admin");
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys("admin123");
        driver.findElement(By.cssSelector("button.orangehrm-login-button")).click();

        // Wait for all Loading Icon disappear
        Assert.assertTrue(isLoadingIconDisappear());

        // Verify Dashboard page displayed
        Assert.assertTrue(driver.findElement(By.xpath("//h6[text()='Dashboard']")).isDisplayed());

        // Click to PIM link
        driver.findElement(By.xpath("//span[text()='PIM']/parent::a")).click();
        Assert.assertTrue(isLoadingIconDisappear());

        // Verify PIM page displayed
        Assert.assertTrue(driver.findElement(By.xpath("//h6[text()='PIM']")).isDisplayed());

        // Add Employee
        driver.findElement(By.xpath("//a[text()='Add Employee']")).click();
        Assert.assertTrue(isLoadingIconDisappear());

        // Create new Employee
        driver.findElement(By.cssSelector("input[name='firstName']")).sendKeys(firstName);
        driver.findElement(By.cssSelector("input[name='lastName']")).sendKeys(lastName);

        employeeID = driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input")).getAttribute("value");

        driver.findElement(By.xpath("//p[text()='Create Login Details']/following-sibling::div//span")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//label[text()='Username']/parent::div/following-sibling::div/input")).sendKeys(emailAddress);
        driver.findElement(By.xpath("//label[text()='Password']/parent::div/following-sibling::div/input")).sendKeys(password);
        driver.findElement(By.xpath("//label[text()='Confirm Password']/parent::div/following-sibling::div/input")).sendKeys(password);

        driver.findElement(By.xpath("//button[contains(string(),'Save')]")).click();
        Thread.sleep(2000);

        // Verify Success Message displayed
        Assert.assertTrue(driver.findElement(By.xpath("//p[text()='Successfully Saved']")).isDisplayed());

        // Loading icon at Add Employee page
        Assert.assertTrue(isLoadingIconDisappear());

        // Loading Personal Details page
        Assert.assertTrue(isLoadingIconDisappear());

        // Personal Details page
        Assert.assertEquals(driver.findElement(By.cssSelector("input[name='firstName']")).getAttribute("value"), firstName);
        Assert.assertEquals(driver.findElement(By.cssSelector("input[name='lastName']")).getAttribute("value"), lastName);
        Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input")).getAttribute("value"), employeeID);

        // Logout
        driver.findElement(By.cssSelector("li.oxd-userdropdown")).click();
        driver.findElement(By.xpath("//a[text()='Logout']")).click();
    }

    private Boolean isLoadingIconDisappear() {
        // Explicit: áp dụng cho các trạng thái của element (hiển thị/ ko hiển thị (ẩn)/ clickable/ selectable/..)
        return new WebDriverWait(driver, 15).until(ExpectedConditions
                .invisibilityOfAllElements(driver.findElements(By.cssSelector("div.oxd-loading-spinner"))));
    }


    @AfterClass(alwaysRun = true)
	public void afterClass() {
		driver.quit();
	}
}