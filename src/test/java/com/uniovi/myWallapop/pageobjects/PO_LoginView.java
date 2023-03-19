package com.uniovi.myWallapop.pageobjects;

import com.uniovi.myWallapop.util.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_LoginView extends PO_NavView {

    static public void fillLoginForm(WebDriver driver, String emailp, String passwordp) {
        WebElement email = driver.findElement(By.name("username"));
        email.click();
        email.clear();
        email.sendKeys(emailp);
        WebElement password = driver.findElement(By.name("password"));
        password.click();
        password.clear();
        password.sendKeys(passwordp);

        By boton = By.className("btn");
        driver.findElement(boton).click();

    }

    static public void checkEmailMessage(WebDriver driver, int language) {
        SeleniumUtils.waitLoadElementsBy(driver, "text", p.getString("login.correo", language),
                getTimeout());
    }

    static public void checkPasswordMessage(WebDriver driver, int language) {
        SeleniumUtils.waitLoadElementsBy(driver, "text", p.getString("login.password", language),
                getTimeout());
    }

    static public void checkChangeLanguage(WebDriver driver, String textLanguage1, String textLanguage,
                                           int locale1, int locale2) {
//Esperamos a que se cargue el texto en Español
        PO_LoginView.checkEmailMessage(driver, locale1);
        PO_LoginView.checkPasswordMessage(driver, locale1);
//Cambiamos a segundo idioma
        PO_HomeView.changeLanguage(driver, textLanguage);
//Comprobamos que el texto haya cambiado a segundo idioma
        PO_LoginView.checkEmailMessage(driver, locale2);
        PO_LoginView.checkPasswordMessage(driver, locale2);
//Volvemos a Español.
        PO_HomeView.changeLanguage(driver, textLanguage1);
//Esperamos a que se cargue el texto en Español
        PO_LoginView.checkEmailMessage(driver, locale1);
        PO_LoginView.checkPasswordMessage(driver, locale1);
    }
}
