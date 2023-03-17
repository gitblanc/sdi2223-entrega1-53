package com.uniovi.myWallapop.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PO_ChatView extends PO_NavView {

    public static void sendMessage(WebDriver driver, String messagep) {
        WebElement text = driver.findElement(By.name("text"));
        text.click();
        text.clear();
        text.sendKeys(messagep);

        By boton = By.className("btn");
        driver.findElement(boton).click();
    }

    public static void checkTitle(WebDriver driver) {
        checkElementByKey(driver, "chat.title", 0);
    }

    public static List<WebElement> getMessages(WebDriver driver) {
        return checkElementBy(driver, "free", "//tbody/tr");
    }
}
