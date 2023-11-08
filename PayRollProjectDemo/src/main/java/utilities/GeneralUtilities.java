package utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class GeneralUtilities {

	WaitUtilities wu = new WaitUtilities();

	public String getElementText(WebElement element) {
		return element.getText();

	}

	public void selectDropdownByIndex(WebElement element, int index) {
		Select dropdown = new Select(element);
		dropdown.selectByIndex(index);
	}

	public void selectDropdownByValue(WebElement element, String value) {
		Select dropdown = new Select(element);
		dropdown.selectByValue(value);
	}

	public void selectDropdownByVisibleText(WebElement element, String value) {
		Select dropdown = new Select(element);
		dropdown.selectByVisibleText(value);
	}

	public void enterText(WebElement element, String text) {
		element.sendKeys(text);
	}

	public void scrollDown(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	public void acceptAlert(WebDriver driver) {
		driver.switchTo().alert().accept();
	}

	public void dismissAlert(WebDriver driver) {
		driver.switchTo().alert().dismiss();
	}

	public String getAlertText(WebDriver driver) {
		return driver.switchTo().alert().getText();
	}

	public void uploadFile(WebElement element, String filePath) {
		element.sendKeys(filePath);

	}

	// to generate random numbers
	public int randon() {
		Random random = new Random();
		int limit = 1000; // specify array list size if needed
		int randomNumber = random.nextInt(limit);
		return randomNumber;
	}

	public String generateCurrentDateAndTime() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyhhmmss");
		return formatter.format(date);
	}

	public void clickElement(WebDriver driver, WebElement element) {
		// WebElement elementToClick = driver.findElement(By.id("elementId"));

		// Create a JavaScriptExecutor instance
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		// Use JavaScriptExecutor to click the element
		jsExecutor.executeScript("arguments[0].click();", element);
	}

	public void clearField(WebElement element) {
		element.clear();
	}

	public List<WebElement> getTableColumnElements(WebDriver driver) {
		List<WebElement> column = driver
				.findElements(By.xpath("//table[@class='table table-striped table-bordered']//tbody//tr[1]//td"));
		return column;
	}

	public List<WebElement> getTableRowElements(WebDriver driver) {
		List<WebElement> row = driver
				.findElements(By.xpath("//table[@class='table table-striped table-bordered']//tbody//tr"));
		return row;
	}

	public void printHtmlTableContent(WebDriver driver) {
		System.out.println("THE CONTENTS OF TABLE : \n");
		int row = getTableRowElements(driver).size();
		int column = getTableColumnElements(driver).size();
		for (int j = 0; j < row; j++) {
			for (int k = 0; k < column; k++) {
				String locator = "//table[@class='table table-striped table-bordered']//tbody//tr[" + (j + 1) + "]//td["
						+ (k + 1) + "]";
				WebElement element = driver.findElement(By.xpath(locator));
				System.out.println(element.getText() + "\t");
			}

		}
	}

	public int findTableElement(WebDriver driver, String searchElement) {
		int j;
		int m;
		int found = 0;
		int row = getTableRowElements(driver).size();
		int column = getTableColumnElements(driver).size();

		loop: for (j = 0; j < row; j++) {
			for (m = 0; m < column; m++) {
				String locator = "//table[@class='table table-striped table-bordered']//tbody//tr[" + (j + 1) + "]//td["
						+ (m + 1) + "]";
				try {
					wu.fluentWaitForVisibility(driver, driver.findElement(By.xpath(locator)));
				} catch (StaleElementReferenceException e) {
					wu.waitForStaleElement(driver, driver.findElement(By.xpath(locator)));
				}
				WebElement element = driver.findElement(By.xpath(locator));
				String elementText;
				if (element.getText().equals(searchElement)) {
					System.out.println("Element found!!");
					found = 1;
					break loop;
				}
			}
		}
		if (found == 0) {
			j = -1; // Set j = -1 if No results found. This can be verified in Testcases using a
					// condition check
		}
		// }
		return j;
	}

	public void selectFromDynamicDropdown(WebDriver driver, List<WebElement> dropdownList, String searchElement) {
		int listSize = dropdownList.size();
		for (int i = 0; i < listSize; i++) {
			String elementText = dropdownList.get(i).getText();
			if (elementText.equals(searchElement)) {
				dropdownList.get(i).click();
				break;
			}
		}
	}
}
