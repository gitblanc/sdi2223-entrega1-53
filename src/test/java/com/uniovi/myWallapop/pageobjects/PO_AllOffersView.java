package com.uniovi.myWallapop.pageobjects;

import com.uniovi.myWallapop.util.SeleniumUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PO_AllOffersView extends PO_NavView{
    public static void writeIntoSearchBar(WebDriver driver, String searchText) {
        String searchBarType = "class";
        String searchBarText = "form-control";
        List<WebElement> searchBarList =  PO_PrivateView.checkElementBy(driver, searchBarType, searchBarText);
        // Tiene que haber una barra de búsqueda
        Assertions.assertEquals(1, searchBarList.size());
        WebElement searchBar = searchBarList.get(0);
        // Escribimos en ella
        searchBar.click();
        searchBar.clear();
        searchBar.sendKeys(searchText);
        // Pinchamos el botón de buscar
        By boton = By.className("btn");
        driver.findElement(boton).click();
        // Comprobamos que se recarga la página
        PO_PrivateView.checkElementBy(driver, searchBarType, searchBarText);
    }

    public static void buyFirstOffer(WebDriver driver) {
        List<WebElement> offersToBuyRow = PO_View.checkElementBy(driver, "free", "//tbody/tr[1]");
        List<WebElement> offerToBuyLink = offersToBuyRow.get(0).findElements(By.tagName("a"));

        WebElement buyLink = offerToBuyLink.get(0);

        buyLink.click();
    }

    static public void checkSubtitle(WebDriver driver, int language) {
        SeleniumUtils.waitLoadElementsBy(driver, "text", p.getString("offer.list.all.subtitle", language),
                getTimeout());
    }

    static public void checkTableHeaderStatus(WebDriver driver, int language) {
        SeleniumUtils.waitLoadElementsBy(driver, "text", p.getString("offer.list.table.status", language),
                getTimeout());
    }

    static public void checkChangeLanguage(WebDriver driver, String textLanguage1, String textLanguage,
                                           int locale1, int locale2) {
//Esperamos a que se cargue el texto en Español
        PO_AllOffersView.checkSubtitle(driver, locale1);
        PO_AllOffersView.checkTableHeaderStatus(driver, locale1);
//Cambiamos a segundo idioma
        PO_HomeView.changeLanguage(driver, textLanguage);
//Comprobamos que el texto haya cambiado a segundo idioma
        PO_AllOffersView.checkSubtitle(driver, locale2);
        PO_AllOffersView.checkTableHeaderStatus(driver, locale2);
//Volvemos a Español.
        PO_HomeView.changeLanguage(driver, textLanguage1);
//Esperamos a que se cargue el texto en Español
        PO_AllOffersView.checkSubtitle(driver, locale1);
        PO_AllOffersView.checkTableHeaderStatus(driver, locale1);
    }
}
