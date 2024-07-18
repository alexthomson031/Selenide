package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryOrder {
    private RegistrationInfo registrationInfo;
        private final int randomSum = (int) (Math.random() * 60);
        LocalDate date = LocalDate.now();
        LocalDate planeDate = date.plusDays(7 + randomSum);
        private final DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        registrationInfo = DataGenerator.RegistrationCard.generateInfo("ru");
    }

    @Test
    void registerByCreditCard() {
        $("[data-test-id='city'] input").setValue(registrationInfo.getCity());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        String date = DataGenerator.getDate(3);
        $("[data-test-id='date'] input").setValue(date).click();
        $("[data-test-id='name'] input").setValue(registrationInfo.getName());
        $("[data-test-id='phone'] input").setValue("+79111111111");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Успешно! Встреча успешно забронирована на " + date));
    }

    @Test
    void registerByCreditCardTwoLetters() {
        $("[data-test-id='city'] input").setValue("Ку");
        $$(".menu-item__control").find(exactText("Курск")).click();
        $(".input__icon").click();
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatDate.format(planeDate));
        $("[data-test-id='name'] input").setValue(registrationInfo.getName());
        $("[data-test-id='phone'] input").setValue("+79111111111");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Успешно! Встреча успешно забронирована на " + planeDate));
    }
}