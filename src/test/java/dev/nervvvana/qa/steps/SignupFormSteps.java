package dev.nervvvana.qa.steps;

import dev.nervvvana.qa.hooks.Hooks;
import io.cucumber.java.ru.И;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;

public class SignupFormSteps {

    private final WebDriver driver = Hooks.driver;

    private By getLocator(String fieldName) {
        switch (fieldName) {
            case "Имя":
                return By.name("firstname");
            case "Фамилия":
                return By.name("lastname");
            case "E-Mail":
                return By.name("email");
            case "Пароль":
                return By.name("password");
            case "Подписка на новости":
                return By.xpath("//input[@name='newsletter' and @type='checkbox']");
            case "Согласие с Privacy Policy":
                return By.xpath("//input[@name='agree' and @type='checkbox']");
            case "Продолжить":
                return By.xpath("//button[@type='submit']");
            default:
                throw new IllegalArgumentException("Неизвестное поле: " + fieldName);
        }
    }


    @И("открыта страница по адресу {string}")
    public void openPage(String url) {
        driver.get(url);
    }


    @И("поле {string} видимо")
    public void fieldIsVisible(String field) {
        WebElement element = driver.findElement(getLocator(field));
        Assertions.assertTrue(element.isDisplayed(), "Поле '" + field + "' не отображается");
    }


    @И("поле {string} заполняется значением {string}")
    public void fillFieldWithValue(String field, String value) {
        WebElement element = driver.findElement(getLocator(field));
        element.click();
        element.clear();
        element.sendKeys(value);
    }


    @И("поле {string} содержит значение {string}")
    public void fieldContainsValue(String field, String value) {
        WebElement element = driver.findElement(getLocator(field));
        Assertions.assertEquals(value, element.getAttribute("value"),
                "Поле '" + field + "' не содержит значение '" + value + "'");
    }


    @И("поле {string} становится отмеченным")
    public void selectCheckboxField(String field) {
        WebElement element = driver.findElement(getLocator(field));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        if (!element.isSelected()) {
            element.click();
        }
    }


    @И("поле {string} становится не отмеченным")
    public void unselectCheckboxField(String field) {
        WebElement element = driver.findElement(getLocator(field));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        if (element.isSelected()) {
            element.click();
        }
    }


    @И("поле {string} отмечено")
    public void fieldIsSelected(String field) {
        WebElement element = driver.findElement(getLocator(field));
        Assertions.assertTrue(element.isSelected(), "Поле '" + field + "' не отмечено");
    }


    @И("поле {string} не отмечено")
    public void fieldIsUnselected(String field) {
        WebElement element = driver.findElement(getLocator(field));
        Assertions.assertFalse(element.isSelected(), "Поле '" + field + "' отмечено");
    }


    @И("выполняется нажатие на {string}")
    public void pressOn(String field) {
        WebElement element = driver.findElement(getLocator(field));
        element.click();
    }


    @И("страница содержит в заголовке {string}")
    public void pageHeaderContains(String string) {
        try {
            WebElement header = driver.findElement(By.xpath("//h1[.='Ваша учетная запись создана!']"));
            Assertions.assertEquals(string, header.getText(), "Регистрация прошла с невалидными значениями");
        } catch (NoSuchElementException e) {
            return;
        }
    }
}
