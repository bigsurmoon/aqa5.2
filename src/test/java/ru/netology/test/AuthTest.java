package ru.netology.test;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;
import ru.netology.domain.RegistrationInfo;

import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class AuthTest {
    private DataGenerator user;
    private Faker faker = new Faker(new Locale("en"));

    @BeforeEach
    void setUpPage() {
        open("http://localhost:9999/");
        user = new DataGenerator();
    }

    @Test
    void shouldUseActiveUser() {
        RegistrationInfo activeUser = user.setActiveUser();
        $("[data-test-id='login'] input").sendKeys(activeUser.getLogin());
        $("[data-test-id='password'] input").sendKeys(activeUser.getPassword());
        $("[data-test-id='action-login']").click();
        $(byText("Личный кабинет")).shouldBe(visible);
    }

    @Test
    void shouldUseBlockedUser() {
        RegistrationInfo blockedUser = user.setBlockedUser();
        $("[data-test-id='login'] input").sendKeys(blockedUser.getLogin());
        $("[data-test-id='password'] input").sendKeys(blockedUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldBe(visible).shouldHave(text("Пользователь заблокирован"));
    }

    @Test
    void shouldUseInvalidLogin() {
        RegistrationInfo activeUser = user.setActiveUser();
        $("[data-test-id='login'] input").sendKeys(faker.name().firstName());
        $("[data-test-id='password'] input").sendKeys(activeUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldBe(visible).shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldUseInvalidPassword() {
        RegistrationInfo activeUser = user.setActiveUser();
        $("[data-test-id='login'] input").sendKeys(activeUser.getLogin());
        $("[data-test-id='password'] input").sendKeys(faker.internet().password());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldBe(visible).shouldHave(text("Неверно указан логин или пароль"));
    }
}