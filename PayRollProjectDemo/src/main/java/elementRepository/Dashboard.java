package elementRepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.GeneralUtilities;

public class Dashboard {
	WebDriver driver;
	GeneralUtilities gu = new GeneralUtilities();;

	public Dashboard(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@class='col-lg-12 col-sm-12']//p[1]")
	WebElement dashboardText;

	public String verifyDashboardText() {
		return gu.getElementText(dashboardText);
	}
}
