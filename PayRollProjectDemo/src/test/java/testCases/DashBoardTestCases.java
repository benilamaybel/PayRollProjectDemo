package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import constant.Constant;
import elementRepository.Dashboard;
import elementRepository.LoginPage;

public class DashBoardTestCases extends BaseClass {
	Dashboard dashboard;
	LoginPage loginpage;

	@Test(groups = "smoke")
	public void logout() throws InterruptedException {
		loginpage = new LoginPage(driver);
		loginpage.performloginUsingExcelInput(Constant.excelFilePath, Constant.inputExcelFileName,
				Constant.loginSheetName);
		dashboard = new Dashboard(driver);
		dashboard.performLogout(driver);
		String expectedLogoutText = dashboard.getExpectedDataFromExcel(Constant.excelFilePath,
				Constant.inputExcelFileName, Constant.dashboardSheetName);
		String actual = loginpage.getLoginPageText();
		Assert.assertEquals(actual, expectedLogoutText, Constant.dashboardpage_verifyLogOutText);
	}
}
