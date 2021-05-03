package com.qacart.base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class Base {

	protected Properties prop;
	public static AndroidDriver<MobileElement> driver;
	public static ExtentReports extent;
	public static ExtentTest logger;
	
	@BeforeSuite
	public void extrntReprot() {
		
		extent = new ExtentReports("Reprots/index.html");
		extent.addSystemInfo("frameworkType","Appium Page object");
		extent.addSystemInfo("Auther","Mahmoud Gawish");
		extent.addSystemInfo("Environment","Windows");
		extent.addSystemInfo("App","To do Cart");
	}

	@AfterSuite
	public void afterReprot() {
		
		extent.flush();
	}
	
	@BeforeMethod
	public void BeforeReprotMethod(Method method) {
		
		logger = extent.startTest(method.getName());
		
	}
	
	@AfterMethod
	public void afterReprotMethod(Method method, ITestResult result) {
		
		File image = driver.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(image, new File("snapshots/"+ method.getName()+ ".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String fullpath = System.getProperty("user.dir") + File.separator + "snapshots/"+ method.getName()+ ".jpg" ;
		
		if (result.getStatus() == ITestResult.SUCCESS) {
			logger.log(LogStatus.PASS, "this is passed because there is no error");
			
		}else if (result.getStatus() == ITestResult.FAILURE) {
			
			logger.log(LogStatus.FAIL, "this is failed");
			logger.log(LogStatus.FAIL, result.getThrowable());
			logger.log(LogStatus.FAIL, logger.addScreenCapture(fullpath));
			
		}else {
			
			logger.log(LogStatus.SKIP, "this is skipped because there is no error");
			
		}
		
		
	}
    @Parameters({"deviceName","platformName","platformVersion"})
	@BeforeClass
	public void beforeClass(String deviceName,String platformName, String platformVersion) throws FileNotFoundException, IOException {

		prop = new Properties();
		prop.load(new FileReader("src\\main\\resources\\config\\config.properties"));

		File androidapp = new File(prop.getProperty("androidAppPath"));
		
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
		caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
		caps.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
		caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, prop.getProperty("androidAutomationName"));
		caps.setCapability(MobileCapabilityType.APP, androidapp.getAbsolutePath());
		driver = new AndroidDriver<MobileElement>(new URL(prop.getProperty("appiumServerLink")),caps);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

}
