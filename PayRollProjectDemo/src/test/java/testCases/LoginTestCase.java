package testCases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import constant.Constant;
import elementRepository.Dashboard;
import elementRepository.LoginPage;

public class LoginTestCase extends BaseClass {
	LoginPage lp;
	Dashboard d;

	@Test(groups = "Smoke")
	public void verifyValidLogin() throws IOException {
		lp = new LoginPage(driver);
		d = new Dashboard(driver);
		lp.performloginUsingExcelInput(inputExcelFileName, loginSheetName);
		String ExpectedText = "Welcome to Payroll Application";
		String ActualText = d.verifyDashboardText();
		Assert.assertEquals(ActualText, ExpectedText, Constant.lp_verifyValidLogin);
	}

	@DataProvider(name = "validInvalidTestData")
	public Object[][] dpMethod() {
		return new Object[][] { { "carolle", "1q2w3e4r" }, { "carol", "password" }, { "username", "password" } };
	}

	@Test(dataProvider = "validInvalidTestData")
	public void verifyInValidLogin(String userName, String passWord) {
		lp = new LoginPage(driver);
		lp.performlogin(userName, passWord);
		String ExpectedText = "Incorrect username or password.";
		String ActualText = lp.getValidationError();
		Assert.assertEquals(ActualText, ExpectedText, Constant.lp_verifyInValidLogin);
	}
}
