package testNG;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class TestNG_MultipleEnv {
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

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        explicitWait = new WebDriverWait(driver, 5);
    }

    @Parameters("env")
    @Test
    public void TC_01_MultipleEnvironment(String envName) {
        String url = getUrlByEnvironmentName(envName);
        System.out.println("URL = " + url);
        driver.get(url);
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        driver.quit();
    }

    private String getUrlByEnvironmentName(String envName) {
        String env = envName.toUpperCase();
        String url = "";
        switch (env) {
            case "DEV":
                url = "https://facebook.com";
                break;
            case "TEST":
                url = "https://youtube.com";
                break;
            case "STAGE":
                url = "https://google.com";
                break;
            default:
                new IllegalArgumentException("Unexpected value: " + envName);
        }
        return url;
    }
}
