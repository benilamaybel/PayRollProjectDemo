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
	Workers w;
	LoginPage lp;
	WaitUtilities wu;
	GeneralUtilities gu = new GeneralUtilities();
	String expected = "";
	String actual = "";

	@Test
	public void deleteWorker() {
		lp = new LoginPage(driver);
		lp.performloginUsingExcelInput(inputExcelFileName, loginSheetName);
		w = new Workers(driver);
		w.navigateToWorkersMenu();
		String actual = w.verifyDeleteFirstRecord();
		String expected = "Are you sure you want to delete this item?";
		Assert.assertEquals(actual, expected, Constant.w_deleteWorker);
		gu.acceptAlert(driver);
	}

	@Test (groups="Smoke")
	public void searchAndEditWorker() throws InterruptedException {
		lp = new LoginPage(driver);
		lp.performloginUsingExcelInput(inputExcelFileName, loginSheetName);
		w = new Workers(driver);
		w.navigateToWorkersMenu();
		String searchString = w.getSearchStringFromExcel(inputExcelFileName, workerSheetName);
		w.enterWorkerName(searchString);
		String searchElement = w.getWorkerNameFromExcel(inputExcelFileName, workerSheetName);
		w.clickSearch();
		Thread.sleep(5000);
		int elementIndex = w.searchWorker(driver, searchElement);
		System.out.println("Element Index = "+elementIndex);
		if (elementIndex >= 0) { // edit details only when element is present/found in search result list
			System.out.println("Inside IF");
			String locatorEdit = "//table[@class='table table-striped table-bordered']//tbody//tr[" + (elementIndex + 1)
					+ "]//td[8]//span[@class='glyphicon glyphicon-pencil']";

			WebElement editElement = driver.findElement(By.xpath(locatorEdit));
			wu = new WaitUtilities();
			//wu.waitElementClickable(driver, editElement);
			wu.fluentWaitElementClickable(driver, editElement);
			
			/*Actions actions = new Actions(driver);
			actions.moveToElement(editElement).click().build().perform();*/
			editElement.click();
			System.out.println("Element is clicked!");
			expected = "UPDATE WORKER: " + searchElement.toUpperCase();
			actual = w.verifyPageTitle(driver, searchElement.toUpperCase());
			Assert.assertEquals(actual, expected, Constant.w_editWorker);
			w.navigateToBankDetailsSubMenu();
			expected = "WORKER BANK DETAILS: " + searchElement.toUpperCase();
			actual = w.verifyPageTitle(driver, searchElement.toUpperCase());
			Assert.assertEquals(actual, expected, Constant.w_editWorkerBankPage);
			w.clearBankStartDate();
			String setDate = w.getSetDateFromExcel(inputExcelFileName, workerSheetName);
			w.enterBankStartDate(setDate);
			gu.scrollDown(driver);
			w.saveWorker();
			Thread.sleep(5000); // Needed for the header to refresh
			expected = searchElement.toUpperCase();
			actual = w.verifyPageTitle(driver, searchElement.toUpperCase());
			Assert.assertEquals(actual, expected, Constant.w_editWorkerSave);
			w.navigateToBankDetailsSubMenu();
			actual = w.getBankDate();
			Assert.assertEquals(actual, setDate, Constant.w_editWorkerBankDate);
		} else {
			System.out.println(searchElement + " is NOT FOUND in Workers Page");
		}
	}
}
