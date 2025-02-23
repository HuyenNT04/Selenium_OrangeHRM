package reportConfig;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utilities.Log;

public class AllureReportListener implements ITestListener {

    WebDriver driver;

    public String getTestName(ITestResult result) {
        return result.getTestName() != null ? result.getTestName()
                : result.getMethod().getConstructorOrMethod().getName();
    }

    public String getTestDescription(ITestResult result) {
        return result.getMethod().getDescription() != null ? result.getMethod().getDescription() : getTestName(result);
    }

    //Text attachments for Allure
    @Attachment(value = "{0}", type = "text/plain")
    public static String saveTextLog(String message) {
        return message;
    }

    //HTML attachments for Allure
    @Attachment(value = "{0}", type = "text/html")
    public static String attachHtml(String html) {
        return html;
    }

    //Text attachments for Allure
    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshotPNG(WebDriver driver) {
    try {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    } catch (Exception e) {
        Log.LogPrint.error("Screenshot capture failed: " + e.getMessage());
        return new byte[0]; // Trả về mảng rỗng để tránh lỗi Allure
    }
    }

    @Override
    public void onStart(ITestContext iTestContext) {
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        //Allure Screenshot custom
        Log.LogPrint.error("Screenshot captured for test case: " + getTestName(iTestResult));
        if (driver != null && ((RemoteWebDriver) driver).getSessionId() != null) {
            saveScreenshotPNG(driver);
        } else {
            Log.LogPrint.error("Driver is null or session is closed, cannot take screenshot!");
        }
        //Save a log on Allure report.
        saveTextLog(getTestName(iTestResult) + " failed and screenshot taken!");
        throw new RuntimeException("Test failed: " + getTestName(iTestResult));
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
    }
}
