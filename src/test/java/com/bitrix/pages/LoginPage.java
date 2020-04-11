package com.bitrix.pages;

import com.bitrix.testPackage.AbstractTestBase;
import com.bitrix.utilities.BrowserUtilities;
import com.bitrix.utilities.Driver;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends AbstractTestBase {

    protected WebDriver driver = Driver.getDriver();
    protected WebDriverWait wait = new WebDriverWait(driver, 20);

    @FindBy(className = "log-popup-header")
    protected WebElement siteLogoElement;

    @FindBy(name = "USER_LOGIN")
    protected WebElement userName;

    @FindBy(css = "[placeholder='Password']")
    protected WebElement password;

    @FindBy(css = "[class='logo-text']")
    protected WebElement getPageLogoElement2;

    public String getSiteLogoText() {
        return siteLogoElement.getText();
    }

    public String getPageLogoText() {
        return getPageLogoElement2.getText();
    }

    public LoginPage() {
        PageFactory.initElements(driver, this);
    }

    public void login(String usernameValue, String passwordValue) {
        userName.sendKeys(usernameValue);
        password.sendKeys(passwordValue, Keys.ENTER);
        BrowserUtilities.waitForPageToLoad(10);
        BrowserUtilities.wait(3);
    }


}
