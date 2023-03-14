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
}
