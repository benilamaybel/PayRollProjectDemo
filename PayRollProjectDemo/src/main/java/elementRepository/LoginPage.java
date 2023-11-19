package elementRepository;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.ExcelUtilities;
import utilities.GeneralUtilities;

public class LoginPage {
	WebDriver driver;
	GeneralUtilities generalutility = new GeneralUtilities();
	ExcelUtilities excelutility = new ExcelUtilities();
	public static String expectedValidLoginText = "";
	public static String expectedInvalidLoginText = "";

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "loginform-username")
	WebElement userNameField;
	@FindBy(id = "loginform-password")
	WebElement passWordField;
	@FindBy(name = "login-button")
	WebElement loginBtn;
	@FindBy(xpath = "//div[@class='form-group field-loginform-password required has-error']//p[@class='help-block help-block-error']")
	WebElement validationError;
	@FindBy(xpath = "//div[@class='col-sm-4 form-area inner']//h1")
	WebElement loginPageText;

	public String[] getUserDetailsFromExcel(String filePath, String fileName, String sheetName) {
		String[] userCredentials = new String[2];
		try {
			List<String> excelInputList;
			excelInputList = excelutility.getDataFromExcel(filePath, fileName, sheetName);
			userCredentials[0] = excelInputList.get(2);
			userCredentials[1] = excelInputList.get(3);
		} catch (Exception e) {
			System.out.println("An Exception Occurred!!!" + e);
		}
		return userCredentials;
	}

	public void performlogin(String userName, String passWord) {
		userNameField.sendKeys(userName);
		passWordField.sendKeys(passWord);
		loginBtn.click();
	}

	public void performloginUsingExcelInput(String filePath, String fileName, String sheetName) {
		String[] userData = getUserDetailsFromExcel(filePath, fileName, sheetName); // calling another function
		// getUserDetailsFromExcel()
		String userName = userData[0];
		String passWord = userData[1];
		performlogin(userName, passWord);
	}

	public String getValidationError() {
		return generalutility.getElementText(validationError);
	}

	public String getLoginPageText() {
		return generalutility.getElementText(loginPageText);
	}

	public void getExpectedDataFromExcel(String filePath, String fileName, String sheetName) {
		List<String> excelInputList;
		try {
			excelInputList = excelutility.getDataFromExcel(filePath, fileName, sheetName);
			expectedValidLoginText = excelInputList.get(5);
			expectedInvalidLoginText = excelInputList.get(7);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
