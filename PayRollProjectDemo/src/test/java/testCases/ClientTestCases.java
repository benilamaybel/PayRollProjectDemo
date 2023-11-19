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

	@Test(groups = "Smoke")
	public void createClient() throws InterruptedException, IOException {
		loginpage = new LoginPage(driver);
		loginpage.performloginUsingExcelInput(Constant.excelFilePath, inputExcelFileName, loginSheetName);
		clients = new Clients(driver);
		clients.navigateToClient();
		clients.navigateToCreateClient();
		clients.getClientDetailsFromExcel(Constant.excelFilePath, inputExcelFileName, clientSheetName);
		String generatedClientName = clients.enterClientDetails();
		clients.saveClient();
		String actualclient = clients.getSavedClientName(driver, generatedClientName);
		Assert.assertEquals(actualclient, generatedClientName, Constant.clientpage_createClient);
	}

	@Test(groups = "Regression", priority = 1)
	public void searchAndViewClient() throws InterruptedException {
		loginpage = new LoginPage(driver);
		loginpage.performloginUsingExcelInput(Constant.excelFilePath, inputExcelFileName, loginSheetName);
		clients = new Clients(driver);
		clients.navigateToClient();
		String clientName = clients.getClientName(Constant.excelFilePath, inputExcelFileName, clientSheetName);
		clients.enterClientName(clientName);
		String clientId = clients.getClientId(Constant.excelFilePath, inputExcelFileName, clientSheetName);
		clients.enterClientId(clientId);
		clients.clickSearch();
		boolean elementFound = clients.searchAndEditClientData(driver, clientId);
		Assert.assertEquals(elementFound, true,Constant.clientpage_editAndViewClient);
	}
}
