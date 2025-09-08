package saucedemo.utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReport_Manager implements ITestListener {

	public ExtentSparkReporter sparkReporter;
	public ExtentReports reporter;
	public ExtentTest test;

	public void onStart(ITestContext context) {
		sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/reports/Saucedemo_Report.html");
		sparkReporter.config().setDocumentTitle("Saucedemo");
		sparkReporter.config().setReportName("Webautomation");
		sparkReporter.config().setTheme(Theme.STANDARD);

		reporter = new ExtentReports();
		reporter.attachReporter(sparkReporter);

	}

	public void onTestSuccess(ITestResult result) {
		test = reporter.createTest(result.getName());
		test.log(Status.PASS, "Test passed successfully: " + result.getName());
	}

	public void onTestFailure(ITestResult result) {
		test = reporter.createTest(result.getName());
		test.log(Status.FAIL, "Test Failed: " + result.getName());
		test.log(Status.FAIL,"Test failed because :"+result.getThrowable());
		
	}

	public void onTestSkipped(ITestResult result) {
		test = reporter.createTest(result.getName());
		test.log(Status.SKIP, "Test skipped: " + result.getName());
		test.log(Status.SKIP,"Test skipped because :"+result.getThrowable());
	}

	public void onFinish(ITestContext context) {
		reporter.flush();
	}

}
