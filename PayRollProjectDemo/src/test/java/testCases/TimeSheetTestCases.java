package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import constant.Constant;
import elementRepository.LoginPage;
import elementRepository.TimeSheet;

public class TimeSheetTestCases extends BaseClass {
	TimeSheet timesheet;
	LoginPage loginpage;

	@Test(groups = "Smoke")
	public void createTimeSheetFromFile() throws InterruptedException {
		loginpage = new LoginPage(driver);
		loginpage.performloginUsingExcelInput(inputExcelFileName, loginSheetName);
		timesheet = new TimeSheet(driver);
		timesheet.gotoTimeSheet();
		String expected = "TIMESHEETS";
		String actual = timesheet.getPageTitle();
		Assert.assertEquals(actual, expected, Constant.ts_verifyTimeSheetTitle);
		timesheet.gotoCreateTimeSheet();
		String imageFileName = pro.getProperty("TimeSheetImage");
		timesheet.uploadTimeSheet(imageFileName);
		String uploadedFileName = timesheet.getFileUploaded();
		Assert.assertEquals(uploadedFileName, imageFileName, Constant.ts_createTimeSheetFileUpload);
		timesheet.performUploadButtonClick();
		timesheet.getTimeSheetDataFromExcel(inputExcelFileName, timeSheetName);
		String timeSheetNumber = timesheet.enterTimeSheetDetails();
		String atualNumber = timesheet.getTimeSheetNumberAfterSave();
		Assert.assertEquals(atualNumber, timeSheetNumber, Constant.ts_createTimeSheetTimeSheetNo);
	}

	@Test(groups = "Regression", priority = 2)
	public void generatePaySlip() throws InterruptedException {
		loginpage = new LoginPage(driver);
		loginpage.performloginUsingExcelInput(inputExcelFileName, loginSheetName);
		timesheet = new TimeSheet(driver);
		timesheet.gotoTimeSheet();
		String expected = "TIMESHEETS";
		String actual = timesheet.getPageTitle();
		Assert.assertEquals(actual, expected, Constant.ts_verifyTimeSheetTitle);
		timesheet.clickGeneratePayslip();
		String expectedPayslipAlert = "Are you sure you want to generate payslip?";
		String actualPayslipAlert = timesheet.getTimeSheetAlertMessage();
		Assert.assertEquals(actualPayslipAlert, expectedPayslipAlert, Constant.ts_verifyAlertText);
		timesheet.acceptTimeSheetAlert();
		expectedPayslipAlert = "Payslip generated!!!";
		actualPayslipAlert = timesheet.getTimeSheetAlertMessage();
		Assert.assertEquals(actualPayslipAlert, expectedPayslipAlert, Constant.ts_verifyAlertText);
		timesheet.acceptTimeSheetAlert();
		Assert.assertEquals(actual, expected, Constant.ts_verifyTimeSheetTitle);
	}

	@Test(groups = "Regression", priority = 2)
	public void cancelInvoiceGeneraion() throws InterruptedException {
		loginpage = new LoginPage(driver);
		loginpage.performloginUsingExcelInput(inputExcelFileName, loginSheetName);
		timesheet = new TimeSheet(driver);
		timesheet.gotoTimeSheet();
		String expected = "TIMESHEETS";
		String actual = timesheet.getPageTitle();
		Assert.assertEquals(actual, expected, Constant.ts_verifyTimeSheetTitle);
		timesheet.clickGenerateInvoice();
		String expectedPayslipAlert = "Are you sure you want to generate invoice?";
		String actualPayslipAlert = timesheet.getTimeSheetAlertMessage();
		Assert.assertEquals(actualPayslipAlert, expectedPayslipAlert, Constant.ts_verifyAlertText);
		timesheet.dismissTimeSheetAlert();
		Assert.assertEquals(actual, expected, Constant.ts_verifyTimeSheetTitle);
	}
}
