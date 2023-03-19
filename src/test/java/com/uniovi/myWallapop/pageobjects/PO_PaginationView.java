package com.uniovi.myWallapop.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PO_PaginationView extends PO_NavView {

    public static void clickNextPage(WebDriver driver) {
        List<WebElement> pageLinks = PO_View.checkElementBy(driver, "free", "//a[contains(@class, 'page-link')]");
        pageLinks.get(3).click();
    }

    public static void clickLastPage(WebDriver driver) {
        List<WebElement> pageLinks = PO_View.checkElementBy(driver, "free", "//a[contains(@class, 'page-link')]");
        pageLinks.get(pageLinks.size()-1).click();
    }

    public static boolean isLastPage(WebDriver driver) {
        List<WebElement> pageLinks = PO_View.checkElementBy(driver, "free", "//a[contains(@class, 'page-link')]");
        String lastPageText = PO_View.getP().getString("pagination.last", PO_Properties.getSPANISH());
        return pageLinks.get(3).getText().equals(lastPageText);
    }
}
