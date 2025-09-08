package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentTestNGListener implements ITestListener {

    private static ExtentReports extent = ExtentManager.getInstance();
    private static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        testThread.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        // Log negative/invalid tests clearly
        if(testName.toLowerCase().contains("negative") || testName.toLowerCase().contains("invalid")) {
            testThread.get().log(Status.PASS, "Negative test passed as expected ✅");
        } else {
            testThread.get().log(Status.PASS, "Test Passed");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        // If negative test fails (unexpected), mark FAIL
        if(testName.toLowerCase().contains("negative") || testName.toLowerCase().contains("invalid")) {
            testThread.get().log(Status.FAIL, "Negative test failed ❌: " + result.getThrowable());
        } else {
            testThread.get().log(Status.FAIL, "Test Failed: " + result.getThrowable());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        testThread.get().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
