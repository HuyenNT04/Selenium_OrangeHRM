package admin.organization;

import org.testng.annotations.*;

public class TestNGExample {
    @BeforeSuite
    public void beforeSuite() { System.out.println("Run Before Suite"); }

    @AfterSuite
    public void afterSuite() { System.out.println("Run After Suite"); }

    @BeforeTest
    public void beforeTest() { System.out.println("Run Before Test"); }

    @AfterTest
    public void afterTest() { System.out.println("Run After Test"); }

    @BeforeClass
    public void beforeClass() { System.out.println("Run Before Class"); }

    @AfterClass
    public void afterClass() { System.out.println("Run After Class"); }

    @BeforeMethod
    public void beforeMethod() { System.out.println("Run Before Method"); }

    @AfterMethod
    public void afterMethod() { System.out.println("Run After Method"); }

    @Test
    public void TC01() { System.out.println("Run TC01"); }

    @Test
    public void TC02() { System.out.println("Run TC02"); }

    @Test
    public void TC03() { System.out.println("Run TC03"); }
}
