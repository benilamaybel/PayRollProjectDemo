package testCases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import constant.Constant;
import elementRepository.Clients;
import elementRepository.LoginPage;

public class ClientTestCases extends BaseClass {
	Clients clients;
	LoginPage loginpage;

	@Test(groups="Smoke")
	public void createClient() throws InterruptedException, IOException {
		loginpage = new LoginPage(driver);
		loginpage.performloginUsingExcelInput(inputExcelFileName, loginSheetName);
		clients = new Clients(driver);
		clients.navigateToClient();
		clients.navigateToCreateClient();
		clients.getClientDetailsFromExcel(inputExcelFileName, clientSheetName);
		String generatedClientName = clients.enterClientDetails();
		clients.saveClient();
		String actualclient = clients.getSavedClientName(driver, generatedClientName);
		Assert.assertEquals(actualclient, generatedClientName, Constant.cli_createClient);
	}

	@Test (groups = "Regression", priority = 1)
	public void searchAndViewClient() throws InterruptedException {
		loginpage = new LoginPage(driver);
		loginpage.performloginUsingExcelInput(inputExcelFileName, loginSheetName);
		clients = new Clients(driver);
		clients.navigateToClient();
		String clientName = clients.getClientName(inputExcelFileName, clientSheetName);
		clients.enterClientName(clientName);
		String clientId = clients.getClientId(inputExcelFileName, clientSheetName);
		clients.enterClientId(clientId);
		clients.clickSearch();
		int elementIndex = clients.searchClientData(driver, clientId);
		if (elementIndex >= 0) { // view details only when element is present/found in search result list
			String locatorEye = "//table[@class='table table-striped table-bordered']//tbody//tr[" + (elementIndex + 1)
					+ "]//td[6]//span[@class='glyphicon glyphicon-eye-open']";
			WebElement eyeElement = driver.findElement(By.xpath(locatorEye));
			clients.navigateToViewClient(driver, eyeElement);
			String actualClient = clients.getViewPageTitle(driver, clientName);
			Assert.assertEquals(actualClient, clientName, Constant.cli_createClient);
		} else {
			System.out.println(clientId + " is NOT FOUND in Clients Page");
		}
	}
}
