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
		loginpage.performloginUsingExcelInput(Constant.excelFilePath, Constant.inputExcelFileName,
				Constant.loginSheetName);
		timesheet = new TimeSheet(driver);
		timesheet.getExpectedDataFromExcel(Constant.excelFilePath, Constant.inputExcelFileName, Constant.timeSheetName);
		timesheet.gotoTimeSheet();
		String expected = TimeSheet.expectedTimeSheetPageTitle;
		String actual = timesheet.getPageTitle();
		Assert.assertEquals(actual, expected, Constant.timesheetpage_verifyTimeSheetTitle);
		timesheet.gotoCreateTimeSheet();
		timesheet.uploadTimeSheet(Constant.imageFilePath, Constant.timeSheetImage);
		String uploadedFileName = timesheet.getFileUploaded();
		Assert.assertEquals(uploadedFileName, Constant.timeSheetImage,
				Constant.timesheetpage_createTimeSheetFileUpload);
		timesheet.performUploadButtonClick();
		timesheet.getTimeSheetDataFromExcel(Constant.excelFilePath, Constant.inputExcelFileName,
				Constant.timeSheetName);
		String timeSheetNumber = timesheet.enterTimeSheetDetails();
		String atualNumber = timesheet.getTimeSheetNumberAfterSave();
		Assert.assertEquals(atualNumber, timeSheetNumber, Constant.timesheetpage_createTimeSheetTimeSheetNo);
	}

	@Test(groups = "Regression", priority = 2)
	public void generatePaySlip() throws InterruptedException {
		loginpage = new LoginPage(driver);
		loginpage.performloginUsingExcelInput(Constant.excelFilePath, Constant.inputExcelFileName,
				Constant.loginSheetName);
		timesheet = new TimeSheet(driver);
		timesheet.getExpectedDataFromExcel(Constant.excelFilePath, Constant.inputExcelFileName, Constant.timeSheetName);
		timesheet.gotoTimeSheet();
		String expected = TimeSheet.expectedTimeSheetPageTitle;
		String actual = timesheet.getPageTitle();
		Assert.assertEquals(actual, expected, Constant.timesheetpage_verifyTimeSheetTitle);
		timesheet.clickGeneratePayslip();
		String actualPayslipAlert = timesheet.getTimeSheetAlertMessage();
		Assert.assertEquals(actualPayslipAlert, TimeSheet.expectedPaySlipAlert, Constant.timesheetpage_verifyAlertText);
		timesheet.acceptTimeSheetAlert();
		actualPayslipAlert = timesheet.getTimeSheetAlertMessage();
		Assert.assertEquals(actualPayslipAlert, TimeSheet.expectedPaySlipGenerateSuccessMessage,
				Constant.timesheetpage_verifyAlertText);
		timesheet.acceptTimeSheetAlert();
		Assert.assertEquals(actual, expected, Constant.timesheetpage_verifyTimeSheetTitle);
	}

	@Test(groups = "Regression", priority = 2)
	public void cancelInvoiceGeneraion() throws InterruptedException {
		loginpage = new LoginPage(driver);
		loginpage.performloginUsingExcelInput(Constant.excelFilePath, Constant.inputExcelFileName,
				Constant.loginSheetName);
		timesheet = new TimeSheet(driver);
		timesheet.getExpectedDataFromExcel(Constant.excelFilePath, Constant.inputExcelFileName, Constant.timeSheetName);
		timesheet.gotoTimeSheet();
		String expected = TimeSheet.expectedTimeSheetPageTitle;
		String actual = timesheet.getPageTitle();
		Assert.assertEquals(actual, expected, Constant.timesheetpage_verifyTimeSheetTitle);
		timesheet.clickGenerateInvoice();
		String actualInvoicAlert = timesheet.getTimeSheetAlertMessage();
		Assert.assertEquals(actualInvoicAlert, TimeSheet.expectedInvoiceAlert,
				Constant.timesheetpage_verifyInvoiceAlertText);
		timesheet.dismissTimeSheetAlert();
		Assert.assertEquals(actual, expected, Constant.timesheetpage_verifyTimeSheetTitle);
	}
}
