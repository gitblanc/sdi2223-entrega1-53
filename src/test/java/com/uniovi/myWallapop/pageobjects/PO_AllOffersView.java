package com.uniovi.myWallapop.pageobjects;

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
        List<WebElement> offersList = PO_View.checkElementBy(driver, "free", "//tbody/tr[1]");
        List<WebElement> offerToBuyRow = offersList.get(0).findElements(By.tagName("td"));
        WebElement buyLink = offerToBuyRow.get(offerToBuyRow.size()-1).findElement(By.tagName("a"));
        buyLink.click();
    }
}
