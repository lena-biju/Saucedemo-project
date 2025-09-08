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

    public static String takeScreenshot(WebDriver driver, String pageName) {
        // today's date folder (yyyyMMdd)
        String today = new SimpleDateFormat("yyyyMMdd").format(new Date());

        // folder path
        String screenshotDir = System.getProperty("user.dir") + "/screenshots/" + today + "/";
        new File(screenshotDir).mkdirs();

        // final file path (no timestamp → always same name per page/test)
        String filePath = screenshotDir + pageName + ".png";
        File destFile = new File(filePath);

        // if file already exists → return existing
        if (destFile.exists()) {
            return filePath;
        }

        // else → capture and save new
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filePath;
    }
}
