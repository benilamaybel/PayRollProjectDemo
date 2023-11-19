package testCases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import constant.Constant;
import elementRepository.Dashboard;
import elementRepository.LoginPage;

public class LoginTestCase extends BaseClass {
	LoginPage loginpage;
	Dashboard dashboard;

	@Test(groups = "Smoke", priority = 1)
	public void verifyValidLogin() throws IOException {
		loginpage = new LoginPage(driver);
		dashboard = new Dashboard(driver);
		loginpage.performloginUsingExcelInput(Constant.excelFilePath, inputExcelFileName, loginSheetName);
		loginpage.getExpectedDataFromExcel(Constant.excelFilePath, inputExcelFileName, loginSheetName);
		String ActualText = dashboard.verifyDashboardText();
		Assert.assertEquals(ActualText, LoginPage.expectedValidLoginText, Constant.loginpage_verifyValidLogin);
	}

	@DataProvider(name = "validInvalidTestData")
	public Object[][] dpMethod() {
		return new Object[][] { { "carolle", "1q2w3e4r" }, { "carol", "password" }, { "username", "password" } };
	}

	@Test(dataProvider = "validInvalidTestData", groups = "Regression", priority = 1)
	public void verifyInValidLogin(String userName, String passWord) {
		loginpage = new LoginPage(driver);
		loginpage.performlogin(userName, passWord);
		loginpage.getExpectedDataFromExcel(Constant.excelFilePath, inputExcelFileName, loginSheetName);
		String ActualText = loginpage.getValidationError();
		Assert.assertEquals(ActualText, LoginPage.expectedInvalidLoginText, Constant.loginpage_verifyInValidLogin);
	}
}
