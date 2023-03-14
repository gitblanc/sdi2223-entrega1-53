package com.uniovi.myWallapop.pageobjects;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PO_PrivateView extends PO_NavView {
    public static void fillFormAddOffer(WebDriver driver, String titlep, String descriptionp, String amountp) {
        WebElement title = driver.findElement(By.name("title"));
        title.click();
        title.clear();
        title.sendKeys(titlep);
        WebElement description = driver.findElement(By.name("description"));
        description.click();
        description.clear();
        description.sendKeys(descriptionp);
        WebElement amount = driver.findElement(By.name("amount"));
        amount.click();
        amount.clear();
        amount.sendKeys(amountp);

        By boton = By.className("btn");
        driver.findElement(boton).click();
    }
}
