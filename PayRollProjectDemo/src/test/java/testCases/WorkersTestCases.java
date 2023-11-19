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
		loginpage.performloginUsingExcelInput(Constant.excelFilePath, inputExcelFileName, loginSheetName);
		workers = new Workers(driver);
		workers.navigateToWorkersMenu();
		String actual = workers.verifyDeleteFirstRecord();
		String expected = "Are you sure you want to delete this item?";
		Assert.assertEquals(actual, expected, Constant.workerpage_deleteWorker);
		generalutility.acceptAlert(driver);
	}

	@Test(groups = "Smoke")
	public void searchAndEditWorker() throws InterruptedException {
		loginpage = new LoginPage(driver);
		loginpage.performloginUsingExcelInput(Constant.excelFilePath, inputExcelFileName, loginSheetName);
		workers = new Workers(driver);
		workers.navigateToWorkersMenu();
		String searchString = workers.getSearchStringFromExcel(Constant.excelFilePath, inputExcelFileName,
				workerSheetName);
		workers.enterWorkerName(searchString);
		String searchElement = workers.getWorkerNameFromExcel(Constant.excelFilePath, inputExcelFileName,
				workerSheetName);
		workers.clickSearch();
		boolean elementFound = workers.editWorker(driver, searchElement);
		Assert.assertEquals(elementFound, true, "Element Not Found!");
		expected = "UPDATE WORKER: " + searchElement.toUpperCase();
		actual = workers.verifyPageTitle(driver, searchElement.toUpperCase());
		Assert.assertEquals(actual, expected, Constant.workerpage_editAndViewWorker);
		workers.navigateToBankDetailsSubMenu();
		expected = "WORKER BANK DETAILS: " + searchElement.toUpperCase();
		actual = workers.verifyPageTitle(driver, searchElement.toUpperCase());
		System.out.println("Actual Text = " + actual);
		Assert.assertEquals(actual, expected, Constant.workerpage_editWorkerBankPage);
		workers.clearBankStartDate();
		String setDate = workers.getSetDateFromExcel(Constant.excelFilePath, inputExcelFileName, workerSheetName);
		workers.enterBankStartDate(setDate);
		generalutility.scrollDown(driver);
		workers.saveWorker();
		Thread.sleep(5000); // Needed for the header to refresh
		workers.navigateToBankDetailsSubMenu();
		actual = workers.getBankDate();
		Assert.assertEquals(actual, setDate, Constant.workerpage_editWorkerBankDate);
	}
}
