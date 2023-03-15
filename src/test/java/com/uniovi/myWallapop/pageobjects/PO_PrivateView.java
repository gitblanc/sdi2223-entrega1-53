package com.uniovi.myWallapop.pageobjects;

import com.uniovi.myWallapop.util.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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

    static public void checkSubtitle(WebDriver driver, int language) {
        SeleniumUtils.waitLoadElementsBy(driver, "text", p.getString("offer.list.subtitle", language),
                getTimeout());
    }

    static public void checkTableHeaderDetails(WebDriver driver, int language) {
        SeleniumUtils.waitLoadElementsBy(driver, "text", p.getString("offer.list.table.details", language),
                getTimeout());
    }

    static public void checkChangeLanguage(WebDriver driver, String textLanguage1, String textLanguage,
                                           int locale1, int locale2) {
//Esperamos a que se cargue el texto en Español
        PO_PrivateView.checkSubtitle(driver, locale1);
        PO_PrivateView.checkTableHeaderDetails(driver, locale1);
//Cambiamos a segundo idioma
        PO_HomeView.changeLanguage(driver, textLanguage);
//Comprobamos que el texto haya cambiado a segundo idioma
        PO_PrivateView.checkSubtitle(driver, locale2);
        PO_PrivateView.checkTableHeaderDetails(driver, locale2);
//Volvemos a Español.
        PO_HomeView.changeLanguage(driver, textLanguage1);
//Esperamos a que se cargue el texto en Español
        PO_PrivateView.checkSubtitle(driver, locale1);
        PO_PrivateView.checkTableHeaderDetails(driver, locale1);
    }
}
