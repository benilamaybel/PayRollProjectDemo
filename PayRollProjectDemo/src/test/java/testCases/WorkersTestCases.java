package testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import constant.Constant;
import elementRepository.LoginPage;
import elementRepository.Workers;
import utilities.GeneralUtilities;
import utilities.WaitUtilities;

public class WorkersTestCases extends BaseClass {
	Workers workers;
	LoginPage loginpage;
	GeneralUtilities generalutility = new GeneralUtilities();
	String expected = "";
	String actual = "";

	@Test(groups = "Regression", priority = 2)
	public void deleteWorker() {
		loginpage = new LoginPage(driver);
		loginpage.performloginUsingExcelInput(inputExcelFileName, loginSheetName);
		workers = new Workers(driver);
		workers.navigateToWorkersMenu();
		String actual = workers.verifyDeleteFirstRecord();
		String expected = "Are you sure you want to delete this item?";
		Assert.assertEquals(actual, expected, Constant.w_deleteWorker);
		generalutility.acceptAlert(driver);
	}

	@Test(groups = "Smoke")
	public void searchAndEditWorker() throws InterruptedException {
		loginpage = new LoginPage(driver);
		loginpage.performloginUsingExcelInput(inputExcelFileName, loginSheetName);
		workers = new Workers(driver);
		workers.navigateToWorkersMenu();
		String searchString = workers.getSearchStringFromExcel(inputExcelFileName, workerSheetName);
		workers.enterWorkerName(searchString);
		String searchElement = workers.getWorkerNameFromExcel(inputExcelFileName, workerSheetName);
		workers.clickSearch();
		int elementIndex = workers.searchWorker(driver, searchElement);
		if (elementIndex >= 0) { // edit details only when element is present/found in search result list
			String locatorEdit = "//table[@class='table table-striped table-bordered']//tbody//tr[" + (elementIndex + 1)
					+ "]//td[8]//span[@class='glyphicon glyphicon-pencil']";
			WebElement editElement = driver.findElement(By.xpath(locatorEdit));
			workers.clickOnEditWorker(editElement);
			expected = "UPDATE WORKER: " + searchElement.toUpperCase();
			actual = workers.verifyPageTitle(driver, searchElement.toUpperCase());
			Assert.assertEquals(actual, expected, Constant.w_editWorker);
			workers.navigateToBankDetailsSubMenu();
			expected = "WORKER BANK DETAILS: " + searchElement.toUpperCase();
			actual = workers.verifyPageTitle(driver, searchElement.toUpperCase());
			System.out.println("Actual Text = " + actual);
			Assert.assertEquals(actual, expected, Constant.w_editWorkerBankPage);
			workers.clearBankStartDate();
			String setDate = workers.getSetDateFromExcel(inputExcelFileName, workerSheetName);
			workers.enterBankStartDate(setDate);
			generalutility.scrollDown(driver);
			workers.saveWorker();
			Thread.sleep(5000); // Needed for the header to refresh
			expected = searchElement.toUpperCase();
			actual = workers.verifyPageTitle(driver, searchElement.toUpperCase());
			Assert.assertEquals(actual, expected, Constant.w_editWorkerSave);
			workers.navigateToBankDetailsSubMenu();
			actual = workers.getBankDate();
			Assert.assertEquals(actual, setDate, Constant.w_editWorkerBankDate);
		} else {
			System.out.println(searchElement + " is NOT FOUND in Workers Page");
		}
	}
}
