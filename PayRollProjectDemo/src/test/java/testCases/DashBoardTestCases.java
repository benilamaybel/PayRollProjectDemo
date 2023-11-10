package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import constant.Constant;
import elementRepository.LoginPage;
import elementRepository.TimeSheet;
import utilities.GeneralUtilities;

public class DashBoardTestCases extends BaseClass {
	TimeSheet ts;
	GeneralUtilities gu;
	LoginPage lp;

	@Test (groups = "smoke")
	public void logout() throws InterruptedException {
		lp = new LoginPage(driver);
		lp.performloginUsingExcelInput(inputExcelFileName, loginSheetName);
		ts = new TimeSheet(driver);
		ts.performLogout(driver);
		String expected = "LOGIN";
		String actual = lp.getLoginPageText();
		Assert.assertEquals(actual, expected, Constant.db_verifyLogOutText);
	}
}
