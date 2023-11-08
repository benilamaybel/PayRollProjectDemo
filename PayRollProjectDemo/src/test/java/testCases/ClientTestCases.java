package testCases;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import constant.Constant;
import elementRepository.Clients;
import elementRepository.LoginPage;
import utilities.GeneralUtilities;
import utilities.WaitUtilities;

public class ClientTestCases extends BaseClass {
	Clients cli;
	LoginPage lp;

	@Test(groups="Smoke")
	public void createClient() throws InterruptedException {
		lp = new LoginPage(driver);
		lp.performloginUsingExcelInput(inputExcelFileName, loginSheetName);
		cli = new Clients(driver);
		cli.navigateToClient();
		cli.navigateToCreateClient();
		cli.getClientDetailsFromExcel(inputExcelFileName, clientSheetName);
		String generatedClientName = cli.enterClientDetails();
		cli.saveClient();
		String actualclient = cli.getSavedClientName(driver, generatedClientName);
		Assert.assertEquals(actualclient, generatedClientName, Constant.cli_createClient);
	}

	@Test
	public void searchAndViewClient() throws InterruptedException {
		lp = new LoginPage(driver);
		lp.performloginUsingExcelInput(inputExcelFileName, loginSheetName);
		cli = new Clients(driver);
		cli.navigateToClient();
		String clientName = cli.getClientName(inputExcelFileName, clientSheetName);
		cli.enterClientName(clientName);
		String clientId = cli.getClientId(inputExcelFileName, clientSheetName);
		cli.enterClientId(clientId);
		cli.clickSearch();
		int elementIndex = cli.searchClientData(driver, clientId);
		if (elementIndex >= 0) { // view details only when element is present/found in search result list
			String locatorEye = "//table[@class='table table-striped table-bordered']//tbody//tr[" + (elementIndex + 1)
					+ "]//td[6]//span[@class='glyphicon glyphicon-eye-open']";
			WebElement eyeElement = driver.findElement(By.xpath(locatorEye));
			cli.navigateToViewClient(driver, eyeElement);
			String actualClient = cli.getViewPageTitle(driver, clientName);
			Assert.assertEquals(actualClient, clientName, Constant.cli_createClient);
		} else {
			System.out.println(clientId + " is NOT FOUND in Clients Page");
		}
	}
}
