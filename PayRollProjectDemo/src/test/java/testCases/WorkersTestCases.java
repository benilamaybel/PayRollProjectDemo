package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import constant.Constant;
import elementRepository.LoginPage;
import elementRepository.Workers;
import utilities.GeneralUtilities;

public class WorkersTestCases extends BaseClass {
	Workers workers;
	LoginPage loginpage;
	GeneralUtilities generalutility = new GeneralUtilities();
	String expected = "";
	String actual = "";

	@Test(groups = "Regression", priority = 2)
	public void deleteWorker() {
		loginpage = new LoginPage(driver);
		loginpage.performloginUsingExcelInput(Constant.excelFilePath, Constant.inputExcelFileName, Constant.loginSheetName);
		workers = new Workers(driver);
		workers.getDataFromExcel(Constant.excelFilePath, Constant.inputExcelFileName, Constant.workerSheetName);
		workers.navigateToWorkersMenu();
		String actual = workers.verifyDeleteFirstRecord();
		Assert.assertEquals(actual, Workers.expectedDeleteAlertMessage, Constant.workerpage_deleteWorker);
		generalutility.acceptAlert(driver);
	}

	@Test(groups = "Smoke")
	public void searchAndEditWorker() throws InterruptedException {
		loginpage = new LoginPage(driver);
		loginpage.performloginUsingExcelInput(Constant.excelFilePath, Constant.inputExcelFileName, Constant.loginSheetName);
		workers = new Workers(driver);
		workers.getDataFromExcel(Constant.excelFilePath, Constant.inputExcelFileName, Constant.workerSheetName);
		workers.navigateToWorkersMenu();
		String searchString = Workers.SearchString;
		workers.enterWorkerName(searchString);
		String searchElement = Workers.workerName;
		workers.clickSearch();
		boolean elementFound = workers.editWorker(driver, searchElement);
		Assert.assertEquals(elementFound, true, Constant.workerpage_editAndViewWorker);
		workers.navigateToBankDetailsSubMenu();
		workers.clearBankStartDate();
		String setDate = Workers.setDate;
		workers.enterBankStartDate(setDate);
		generalutility.scrollDown(driver);
		workers.saveWorker();
		Thread.sleep(5000); // Needed for the header to refresh
		workers.navigateToBankDetailsSubMenu();
		actual = workers.getBankDate();
		Assert.assertEquals(actual, setDate, Constant.workerpage_editWorkerBankDate);
	}
}
