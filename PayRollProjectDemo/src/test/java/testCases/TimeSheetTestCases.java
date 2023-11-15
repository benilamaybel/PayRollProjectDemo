package testCases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import constant.Constant;
import elementRepository.LoginPage;
import elementRepository.TimeSheet;
import utilities.GeneralUtilities;

public class TimeSheetTestCases extends BaseClass {
	TimeSheet ts;
	GeneralUtilities gu;
	LoginPage lp;

	@Test(groups = "Smoke")
	public void createTimeSheetFromFile() throws InterruptedException {
		lp = new LoginPage(driver);
		lp.performloginUsingExcelInput(inputExcelFileName, loginSheetName);
		ts = new TimeSheet(driver);
		ts.gotoTimeSheet();
		String expected = "TIMESHEETS";
		String actual = ts.getPageTitle();
		Assert.assertEquals(actual, expected, Constant.ts_verifyTimeSheetTitle);
		ts.gotoCreateTimeSheet();
		String imageFileName = pro.getProperty("TimeSheetImage");
		ts.uploadTimeSheet(imageFileName);
		String uploadedFileName = ts.getFileUploaded();
		Assert.assertEquals(uploadedFileName, imageFileName, Constant.ts_createTimeSheetFileUpload);
		ts.performUploadButtonClick();
		ts.getTimeSheetDataFromExcel(inputExcelFileName, timeSheetName);
		String timeSheetNumber = ts.enterTimeSheetDetails();
		String atualNumber = ts.getTimeSheetNumberAfterSave();
		Assert.assertEquals(atualNumber, timeSheetNumber, Constant.ts_createTimeSheetTimeSheetNo);
	}

	@Test(groups = "Regression", priority = 2)
	public void generatePaySlip() throws InterruptedException {
		lp = new LoginPage(driver);
		lp.performloginUsingExcelInput(inputExcelFileName, loginSheetName);
		ts = new TimeSheet(driver);
		ts.gotoTimeSheet();
		String expected = "TIMESHEETS";
		String actual = ts.getPageTitle();
		Assert.assertEquals(actual, expected, Constant.ts_verifyTimeSheetTitle);
		ts.clickGeneratePayslip();
		String expectedPayslipAlert = "Are you sure you want to generate payslip?";
		String actualPayslipAlert = ts.getTimeSheetAlertMessage();
		Assert.assertEquals(actualPayslipAlert, expectedPayslipAlert, Constant.ts_verifyAlertText);
		ts.acceptTimeSheetAlert();
		expectedPayslipAlert = "Payslip generated!!!";
		actualPayslipAlert = ts.getTimeSheetAlertMessage();
		Assert.assertEquals(actualPayslipAlert, expectedPayslipAlert, Constant.ts_verifyAlertText);
		ts.acceptTimeSheetAlert();
		Assert.assertEquals(actual, expected, Constant.ts_verifyTimeSheetTitle);
	}

	@Test(groups = "Regression", priority = 2)
	public void cancelInvoiceGeneraion() throws InterruptedException {
		lp = new LoginPage(driver);
		lp.performloginUsingExcelInput(inputExcelFileName, loginSheetName);
		ts = new TimeSheet(driver);
		ts.gotoTimeSheet();
		String expected = "TIMESHEETS";
		String actual = ts.getPageTitle();
		Assert.assertEquals(actual, expected, Constant.ts_verifyTimeSheetTitle);
		ts.clickGenerateInvoice();
		String expectedPayslipAlert = "Are you sure you want to generate invoice?";
		String actualPayslipAlert = ts.getTimeSheetAlertMessage();
		Assert.assertEquals(actualPayslipAlert, expectedPayslipAlert, Constant.ts_verifyAlertText);
		ts.dismissTimeSheetAlert();
		Assert.assertEquals(actual, expected, Constant.ts_verifyTimeSheetTitle);
	}
}
