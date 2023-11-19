package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import constant.Constant;
import elementRepository.LoginPage;
import elementRepository.TimeSheet;

public class DashBoardTestCases extends BaseClass {
	TimeSheet timesheet;
	LoginPage loginpage;

	@Test(groups = "smoke")
	public void logout() throws InterruptedException {
		loginpage = new LoginPage(driver);
		loginpage.performloginUsingExcelInput(Constant.excelFilePath, inputExcelFileName, loginSheetName);
		timesheet = new TimeSheet(driver);
		timesheet.performLogout(driver);
		String expected = "LOGIN";
		String actual = loginpage.getLoginPageText();
		Assert.assertEquals(actual, expected, Constant.dashboardpage_verifyLogOutText);
	}
}
