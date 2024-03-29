package com.uniovi.myWallapop.pageobjects;

import com.uniovi.myWallapop.util.SeleniumUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
public class PO_NavView extends PO_View{
    /**
     * Clic en una de las opciones principales (a href) y comprueba que se vaya a la vista con el elemento de
     tipo type con el texto Destino
     * @param driver: apuntando al navegador abierto actualmente.
     * @param textOption: Texto de la opción principal.
     * @param criterio: "id" or "class" or "text" or "@attribute" or "free". Si el valor de criterio es free es una
    expresion xpath completa.
     * @param targetText: texto correspondiente a la búsqueda de la página destino.
     */
    public static void clickOption(WebDriver driver, String textOption, String criterio, String targetText) {
//CLickamos en la opción y esperamos a que se cargue el enlace.
        List<WebElement> elements = SeleniumUtils.waitLoadElementsBy(driver, "@href", textOption,
                getTimeout());
//Tiene que haber un sólo elemento.
        Assertions.assertEquals(1, elements.size());
//Ahora lo clickamos
        elements.get(0).click();
//Esperamos a que sea visible un elemento concreto
        elements = SeleniumUtils.waitLoadElementsBy(driver, criterio, targetText, getTimeout());
//Tiene que haber un sólo elemento.
        Assertions.assertEquals(1, elements.size());
    }

    /**
     * Selecciona el enlace de idioma correspondiente al texto textLanguage
     * @param driver: apuntando al navegador abierto actualmente.
     * @param textLanguage: el texto que aparece en el enlace de idioma ("English" o "Spanish")
     */
    public static void changeLanguage(WebDriver driver, String textLanguage) {
//clickamos la opción Idioma.
        List<WebElement> languageButton = SeleniumUtils.waitLoadElementsBy(driver, "id", "btnLanguage",
                getTimeout());
        languageButton.get(0).click();
//Esperamos a que aparezca el menú de opciones.
        SeleniumUtils.waitLoadElementsBy(driver, "id", "languageDropdownMenuButton", getTimeout());
//CLickamos la opción Inglés partiendo de la opción Español
        List<WebElement> Selectedlanguage = SeleniumUtils.waitLoadElementsBy(driver, "id", textLanguage,
                getTimeout());
        Selectedlanguage.get(0).click();
    }

    public static void clickPostedOffersOption(WebDriver driver) {
        clickOfferOption(driver, "list/posted");
    }

    public static void clickAddOfferOption(WebDriver driver) {
        clickOfferOption(driver, "add");
    }

    public static void clickOfferOption(WebDriver driver, String hrefOfferOption) {
        //Pinchamos en la opción de menú de Ofertas: //li[contains(@id, 'offers-menu')]/a
        List<WebElement> elements = checkElementBy(driver, "free", "//li[contains(@id, 'offers-menu')]/a");
        elements.get(0).click();
        //Esperamos a que aparezca la opción
        elements = checkElementBy(driver, "free", "//a[contains(@href, 'offer/"+hrefOfferOption+"')]");
        //Pinchamos en el enlace
        elements.get(0).click();
    }

    public static void clickAllOffersOption(WebDriver driver) {
        //Pinchamos en la opción de menú de Ofertas: //li[contains(@id, 'offers-menu')]/a
        List<WebElement> elements = checkElementBy(driver, "free", "//li[contains(@id, 'offers-menu')]/a");
        elements.get(0).click();
        //Esperamos a que aparezca la opción
        elements = checkElementBy(driver, "free", "//a[contains(@href, 'offer/list')]");
        //Pinchamos en el segundo enlace porque el primero es el offer/list/posted
        elements.get(1).click();
    }

    public static void clickOpenChatsOption(WebDriver driver) {
        List<WebElement> elements = checkElementBy(driver, "free", "//li[contains(@id, 'chats-menu')]/a");
        elements.get(0).click();
        elements = checkElementBy(driver, "free", "//a[contains(@href, 'chat/list')]");
        elements.get(0).click();
    }

    public static void clickLogsOption(WebDriver driver) {
        List<WebElement> elements = checkElementBy(driver, "free", "//li[contains(@id, 'logs-menu')]/a");
        elements.get(0).click();
        elements = checkElementBy(driver, "free", "//a[contains(@href, 'user/logslist')]");
        elements.get(0).click();
    }
}