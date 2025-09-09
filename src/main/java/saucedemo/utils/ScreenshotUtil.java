package saucedemo.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtil {

	public static String takeScreenshot(WebDriver driver, String className, String testName) {
	    String today = new SimpleDateFormat("yyyyMMdd").format(new Date());
	    String screenshotDir = System.getProperty("user.dir") + "/screenshots/" + today + "/" + className + "/";
	    new File(screenshotDir).mkdirs();

	    String filePath = screenshotDir + testName + ".png";
	    File destFile = new File(filePath);

	    try {
	        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	        FileUtils.copyFile(src, destFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return filePath;
	}

}
