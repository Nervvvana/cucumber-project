package dev.nervvvana.qa.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    private WebDriver driver;

    @FindBy(name = "firstname")
    private WebElement firstNameInput;

    @FindBy(name = "lastname")
    private WebElement lastNameInput;

    @FindBy(name = "email")
    private WebElement emailInput;

    @FindBy(name = "password")
    private WebElement passwordInput;

    @FindBy(xpath = "//input[@name='newsletter' and @type='checkbox']")
    private WebElement newsletterCheckbox;

    @FindBy(xpath = "//input[@name='agree' and @type='checkbox']")
    private WebElement privacyPolicyCheckbox;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement continueButton;

    public SignupPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void open(String url) {
        driver.get(url);
    }

    public void setFirstName(String name) {
        firstNameInput.clear();
        firstNameInput.sendKeys(name);
    }

    public void setLastName(String lastName) {
        lastNameInput.clear();
        lastNameInput.sendKeys(lastName);
    }

    public void setEmail(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void setPassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void setNewsletter(boolean value) {
        scrollToElement(newsletterCheckbox);
        if (newsletterCheckbox.isSelected() != value) {
            newsletterCheckbox.click();
        }
    }

    public void setPrivacyPolicy(boolean value) {
        scrollToElement(privacyPolicyCheckbox);
        if (privacyPolicyCheckbox.isSelected() != value) {
            privacyPolicyCheckbox.click();
        }
    }

    public void submit() {
        scrollToElement(continueButton);
        continueButton.click();
    }

    public String getHeaderText() {
        try {
            return driver.findElement(By.xpath("//h1")).getText();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    private WebElement scrollToElement(WebElement element) {
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", element);
        return element;
    }
}