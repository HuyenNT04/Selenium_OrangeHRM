package commons;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class BasePage {
    //common functions support for all PageObjects
    //1 ham static co the goi truc tiep tu class ma khong can qua doi tuong
//    public static BasePage getBasePage() {
//    return new BasePage(); --> 2nd way
//    } //new a page with type is BasePage - encapsulation


    //---------------Web Browser---------------------
    public void openPageURL(WebDriver driver, String url){
        driver.get(url);
    }
    public String getPageTitle(WebDriver driver){
        return driver.getTitle();
    }
    public String getPageUrl(WebDriver driver){
        return driver.getCurrentUrl();
    }
    public String getPageSourceCode(WebDriver driver) {
        return driver.getPageSource();
    }
    public void backToPreviousPage(WebDriver driver){
        driver.navigate().back();
    }
    public void forwardToNextPage(WebDriver driver){
        driver.navigate().forward();
    }
    public void refreshCurrentPage(WebDriver driver){
        driver.navigate().refresh();
    }
    private Alert waitToAlertPresence(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.alertIsPresent());
    }
    public void acceptAlert(WebDriver driver) {
        waitToAlertPresence(driver).accept();
    }
    public void cancelAlert(WebDriver driver) {
        waitToAlertPresence(driver).dismiss();
    }
    public void sendkeyToAlert(WebDriver driver, String valueToSend) {
        waitToAlertPresence(driver).sendKeys(valueToSend);
    }
    public String getAlertText(WebDriver driver) {
        return waitToAlertPresence(driver).getText();
    }
    public void switchToWindowByID(WebDriver driver, String parentID) {
        Set<String> allWindows = driver.getWindowHandles();
        for (String runWindow : allWindows) {
            if (!runWindow.equals(parentID)) {
                driver.switchTo().window(runWindow);
                break;
            }
        }
    }
    public void switchToWindowByTitle(WebDriver driver, String title) {
        Set<String> allWindows = driver.getWindowHandles();
        for (String runWindows : allWindows) {
            driver.switchTo().window(runWindows);
            String currentWin = driver.getTitle();

            if (currentWin.equals(title)) {
                break;
            }
        }
    }
    public void closeAllWindowsWithoutParent(WebDriver driver, String parentID) {
        Set<String> allWindows = driver.getWindowHandles();
        for (String windowID : allWindows) {
            if (!windowID.equals(parentID)) {
                driver.switchTo().window(windowID);
                driver.close();
            }
        }
        driver.switchTo().window(parentID);
    }

    //---------------Web Elements-------------------------------
    private By getByXpath(String locator) {
        return By.xpath(locator);
    }
    private WebElement getWebElement(WebDriver driver, String locator){ //only use for some funcs in this class -> private
        return  driver.findElement(getByXpath(locator));
    }
    private List<WebElement> getListWebElements(WebDriver driver, String locator){
        return driver.findElements(getByXpath(locator));
    }
    public void clickToElement(WebDriver driver, String locator){
        getWebElement(driver, locator).click();
    }
    public void sendkeyToElement(WebDriver driver, String locator, String valueToSend) {
        getWebElement(driver, locator).clear();
        getWebElement(driver, locator).sendKeys(valueToSend);
        waitForInputValuePresence(driver,locator,valueToSend);
    }
    public void selectItemDropdown(WebDriver driver, String locator, String textItem) {
        new Select(getWebElement(driver, locator)).selectByVisibleText(textItem);
    }
    public String getSelectedItemInDropdown(WebDriver driver, String locator) {
        return new Select(getWebElement(driver, locator))
                .getFirstSelectedOption().getText();
    }
    public boolean isDropdownMultiple(WebDriver driver, String locator) { //isXXX --> BOOLEAN
        return new Select(getWebElement(driver, locator)).isMultiple();
    }
    public String getElementText(WebDriver driver, String locator) {
        return getWebElement(driver, locator).getText();
    }
    public void selectItemInCustomDropdown(WebDriver driver, String parentXPath, String childXPath, String textItem) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(LONG_TIMEOUT));
        explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpath(parentXPath))).click();
        sleepInSeconds( 2);

        List<WebElement> allItems = waitForListElementsPresence(driver, childXPath);
        for (WebElement item : allItems) {
            if (item.getText().equals(textItem)) {
                item.click();
                break;
            }
        }
    }
    public String getElementAttribute(WebDriver driver, String locator, String attributeName) {
        return getWebElement(driver, locator).getAttribute(attributeName);
    }
    public String getCssValue(WebDriver driver, String locator, String propertyName) {
        return getWebElement(driver, locator).getCssValue(propertyName);
    }
    public String getHexaByRGB(String rgbaValue) {
        return Color.fromString(rgbaValue).asHex().toUpperCase();
    }
    public int getListElementSize(WebDriver driver, String locator) {
        return getListWebElements(driver, locator).size();
    }
    public void checkToCheckboxRadio(WebDriver driver, String locator) {
        if (!getWebElement(driver, locator).isSelected()) {
            getWebElement(driver, locator).click();
        }
    }
    public void uncheckToCheckboxRadio(WebDriver driver, String locator) {
        if (getWebElement(driver, locator).isSelected()) {
            getWebElement(driver, locator).click();
        }
    }
    public boolean isElementDisplayed(WebDriver driver, String locator) {
        return getWebElement(driver, locator).isDisplayed();
    }
    public boolean isElementSelected(WebDriver driver, String locator) {
        return getWebElement(driver, locator).isSelected();
    }
    public boolean isElementEnabled(WebDriver driver, String locator) {
        return getWebElement(driver, locator).isEnabled();
    }
    public WebDriver switchToIframe(WebDriver driver, String locator) {
        return driver.switchTo().frame(getWebElement(driver, locator));
    }
    public WebDriver switchToDefaultContent(WebDriver driver) {
        return driver.switchTo().defaultContent();
    }
    //----UserActions
    public void hoverToElement(WebDriver driver, String locator) {
        new Actions(driver).moveToElement(getWebElement(driver, locator)).perform();
    }
    public void doubleClickToElement(WebDriver driver, String locator) {
        new Actions(driver).doubleClick(getWebElement(driver, locator)).perform();
    }
    public void rightClickToElement(WebDriver driver, String locator) {
        new Actions(driver).contextClick(getWebElement(driver, locator)).perform();
    }
    public void scrollToElement(WebDriver driver, String locator) {
        new Actions(driver).scrollToElement(getWebElement(driver, locator)).perform();
    }
    public void sendKeyBoardToElement(WebDriver driver, String locator, Keys key) {
        new Actions(driver).sendKeys(getWebElement(driver, locator), key).perform();
    }
    public void clearValueInField(WebDriver driver, String locator) {
//        new Actions(driver)
//                .click(getWebElement(driver, locator))
//                .click()
//                .keyDown(Keys.CONTROL)
//                .sendKeys("a")
//                .keyUp(Keys.CONTROL)
//                .sendKeys(Keys.DELETE)
//                .perform();  // Chọn toàn bộ nội dung
        getWebElement(driver, locator).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
    }
    //----- jsExecutor
    public String getDomain(WebDriver driver) {
        return (String) ((JavascriptExecutor) driver).executeScript("return document.domain;");
    }
    public String getInnerText(WebDriver driver) {
        return (String) ((JavascriptExecutor) driver).executeScript("return document.documentElement.innerText;");
    }
    public void hightlightElement(WebDriver driver, String locator) {
        WebElement element = getWebElement(driver, locator);
        String originalStyle = element.getAttribute("style");
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('style', arguments[1])", element, "border: 2px solid red; border-style: dashed;");
        sleepInSeconds(2);
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('style', arguments[1])", element, originalStyle);
    }
    public void clickToElementByJS(WebDriver driver, String locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", getWebElement(driver, locator));
        sleepInSeconds(3);
    }
    public void scrollToElementOnTop(WebDriver driver, String locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", getWebElement(driver, locator));
    }
    public void scrollToElementToDown(WebDriver driver, String locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", getWebElement(driver, locator));
    }
    public void setAttributeInDOM(WebDriver driver, String locator, String attributeName, String attributeValue) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('" + attributeName + "', '" + attributeValue + "');", getWebElement(driver, locator));
    }
    public void removeAttributeInDOM(WebDriver driver, String locator, String attributeRemove) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getWebElement(driver, locator));
    }
    public void sendkeyToElementByJS(WebDriver driver, String locator, String value) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", getWebElement(driver, locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('value', '" + value + "');", getWebElement(driver, locator));
    }
    public String getAttributeInDOM(WebDriver driver, String locator, String attributeName) {
        return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].getAttribute('" + attributeName + "');", getWebElement(driver, locator));
    }
    public String getElementValidationMessage(WebDriver driver, String locator) {
        return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].validationMessage;", getWebElement(driver, locator));
    }
    public boolean isImageLoaded(WebDriver driver, String locator) {
        return (boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' " +
                "&& arguments[0].naturalWidth > 0;", getWebElement(driver, locator));
    }
    //-----Wait------
    public WebElement waitForElementVisible(WebDriver driver, String locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(LONG_TIMEOUT)).until(ExpectedConditions.visibilityOfElementLocated(getByXpath(locator)));
    }
    public List<WebElement> waitForListElementVisible(WebDriver driver, String locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(LONG_TIMEOUT)).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByXpath(locator)));
    }
    public boolean waitForElementInvisible(WebDriver driver, String locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(LONG_TIMEOUT)).until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(locator)));
    }
    public boolean waitForListElementInvisible(WebDriver driver, String locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(LONG_TIMEOUT)).until(ExpectedConditions.invisibilityOfAllElements(getListWebElements(driver, locator)));
    }
    public WebElement waitForElementClickable(WebDriver driver, String locator){
        return new WebDriverWait(driver,Duration.ofSeconds(LONG_TIMEOUT)).until(ExpectedConditions.elementToBeClickable(getByXpath(locator)));
    }
    public WebElement waitForElementPresence(WebDriver driver, String locator){
       return new WebDriverWait(driver,Duration.ofSeconds(LONG_TIMEOUT)).until(ExpectedConditions.presenceOfElementLocated(getByXpath(locator)));
    }
    private boolean waitForInputValuePresence(WebDriver driver, String locator, String inputedValue){
        return new WebDriverWait(driver,Duration.ofSeconds(LONG_TIMEOUT)).until(ExpectedConditions.textToBePresentInElementValue(getByXpath(locator), inputedValue));
    }
    public List<WebElement> waitForListElementsPresence(WebDriver driver, String locator){
        return new WebDriverWait(driver,Duration.ofSeconds(LONG_TIMEOUT)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByXpath(locator)));
    }
    public boolean waitForElementSelected(WebDriver driver, String locator){
        return new WebDriverWait(driver, Duration.ofSeconds(LONG_TIMEOUT)).until(ExpectedConditions.elementToBeSelected(getByXpath(locator)));
    }


    public void sleepInSeconds(long timeInSeconds){
        try {
            Thread.sleep(timeInSeconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private long LONG_TIMEOUT = 30;
}
