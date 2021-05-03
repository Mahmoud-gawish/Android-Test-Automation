package com.qacart.screens;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class LoginBase extends ScreenBase {
	
	public LoginBase(AndroidDriver<MobileElement> driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@AndroidFindBy(xpath = "//android.widget.EditText[@index='1']")
	AndroidElement loginBox;
	
	@AndroidFindBy(xpath = "//android.widget.EditText[@index='2']")
	AndroidElement basswordBox;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Login']")
	AndroidElement loginBTN;
	
	public void userLogin(String email ,String password ) {
		loginBox.sendKeys(email);
		basswordBox.sendKeys(password);
		loginBTN.click();
		
	}
	

}
