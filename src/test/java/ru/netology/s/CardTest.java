package ru.netology.s;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

class CardTest {


    private String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    String planningDate = generateDate(3, "dd.MM.yyyy");

    @Test
    void valid() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Волгоград");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Никита Андреев");
        $("[data-test-id='phone'] input").setValue("+79281234567");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='notification'] .notification__title").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Успешно!"));
        $("[data-test-id='notification'] .notification__content").shouldHave(exactText("Встреча успешно забронирована на " + planningDate));
    }

    @Test
    void emptyCityLine() {
        open("http://localhost:9999");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Никита Андреев");
        $("[data-test-id='phone'] input").setValue("+79281234567");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='city'].input_invalid").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void incorrectDate() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Волгоград");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='name'] input").setValue("Никита Андреев");
        $("[data-test-id='phone'] input").setValue("+79281234567");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='date'] .input_invalid").shouldHave(exactText("Неверно введена дата"));
    }

    @Test
    void emptyPhoneLine() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Волгоград");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Никита Андреев");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='phone'].input_invalid").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void emptyNameLine() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Волгоград");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='phone'] input").setValue("+79281234567");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='name'].input_invalid").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shoulBeTwoLetters() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Во");
        $$(".popup .menu-item").findBy(Condition.text("Волгоград")).click();
        $(".icon-button").click();
        if(!generateDate(3,"MM").equals((generateDate(7, "MM")))) $(" .calendar__arrow_direction_right[data-step='1']").click();
        $$(".calendar__day").findBy(Condition.text(generateDate(7, "d"))).click();
        $("[data-test-id='name'] input").setValue("Никита Андреев");
        $("[data-test-id='phone'] input").setValue("+79281234567");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='notification'] .notification__title").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Успешно!"));
        $("[data-test-id='notification'] .notification__content").shouldHave(exactText("Встреча успешно забронирована на " + generateDate(7, "dd.MM.yyyy")));
    }


    @Test
    void shouldFailIfCheckbox() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Волгоград");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Никита Андреев");
        $("[data-test-id='phone'] input").setValue("+79281234567");
        $(".button").click();
        $(".checkbox.input_invalid").shouldBe(visible);
    }

}