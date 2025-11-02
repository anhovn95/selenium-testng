package testNG;

import org.testng.Assert;
import org.testng.annotations.*;

public class Annotation {
    @Test(groups = "mustRun")
    public void TestCase_01() {
        System.out.println("Run the test case");
        Assert.assertTrue(false);
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Before Method");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("After Method");
    }

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        System.out.println("Before Class");
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        System.out.println("After Class");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("Before Test");
    }

    @AfterTest
    public void afterTest() {
        System.out.println("After Test");
    }
}