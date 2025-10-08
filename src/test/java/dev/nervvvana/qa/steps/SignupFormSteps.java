package dev.nervvvana.qa.steps;

import dev.nervvvana.qa.hooks.Hooks;
import dev.nervvvana.qa.pages.SignupPage;
import io.cucumber.java.ru.И;
import org.junit.jupiter.api.Assertions;

public class SignupFormSteps {

    private final SignupPage signupPage = new SignupPage(Hooks.driver);

    @И("открыта страница по адресу {string}")
    public void openPage(String url) {
        signupPage.open(url);
    }

    @И("поле {string} заполняется значением {string}")
    public void fillTextField(String field, String value) {
        switch (field) {
            case "Имя":
                signupPage.setFirstName(value);
                break;
            case "Фамилия":
                signupPage.setLastName(value);
                break;
            case "E-Mail":
                signupPage.setEmail(value);
                break;
            case "Пароль":
                signupPage.setPassword(value);
                break;
            default:
                throw new RuntimeException("Неизвестное поле");
        }
    }

    @И("чекбокс {string} становится {string}")
    public void fillCheckboxField(String field, String state) {
        switch (field) {
            case "Подписка на новости":
                signupPage.setNewsletter(state.equalsIgnoreCase("отмеченным"));
                break;
            case "Согласие с Privacy Policy":
                signupPage.setPrivacyPolicy(state.equalsIgnoreCase("отмеченным"));
                break;
            default:
                throw new RuntimeException("Неизвестное поле");
        }
    }

    @И("выполняется нажатие на {string}")
    public void pressButton(String button) {
        switch (button) {
            case "Продолжить":
                signupPage.submit();
                break;
            default:
                throw new RuntimeException("Неизвестное поле");
        }
    }

    /**
     * Добавлена задержка на проверку заголовка страницы, т.к. в случае успешной регистрации
     * открывается страница с заголовком "Ваша учетная запись создана", но через какое-то время,
     * а Selenium обрабатывает моментально
     */
    @И("страница содержит в заголовке {string}")
    public void checkHeader(String expected) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(expected, signupPage.getHeaderText());
    }
}