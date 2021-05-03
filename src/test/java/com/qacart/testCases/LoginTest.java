package com.qacart.testCases;

import org.testng.annotations.Test;

import com.qacart.base.Base;
import com.qacart.screens.LoginBase;

public class LoginTest extends Base{
	
	LoginBase loginObject;
	
	@Test 
	public void loiginTestCase() {
		
		loginObject = new LoginBase(driver);
		loginObject.userLogin("test@test1.com", "123123");

	}

}
