package MercedesTest;

import MercedesResources.Locators;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;


public class GenericMercedesTest extends BaseMercedesTest {
    JavascriptExecutor js;

    // Method to select 'Our Models' button
    public void selectModel() {

        js = (JavascriptExecutor) driver;
        WebElement ourModels = getElement(Locators.OUR_MODELS);
        ourModels.click();

    }

    // Method to select the type of car
    public void selectCarType() {

        WebElement hatchBackModel = getElement(Locators.HATCHBACK_MODEL);
        js.executeScript("arguments[0].scrollIntoView()", hatchBackModel);
        hatchBackModel.click();
    }

    // Method to select the model of the car
    public void selectCarModel() {

        WebElement aClassModel = getElement(Locators.A_CLASS_MODEL);
        aClassModel.click();
    }

    // Method to click on the 'Build Car' button
    public void buildCar() {

        WebElement buildCarButton = getElement(Locators.BUILD_CAR);
        buildCarButton.click();
    }

    // Method to add a filter to the car model
    public void addFilter() {
        //To move down the screen
        js.executeScript("window.scrollBy(0, 500)");
        WebElement fuelType = getElement(Locators.FUEL_TYPE);
        fuelType.click();
    }

    // Method to filter cars based on fuel type
    public void filterFuelType() throws InterruptedException {
        //To move down the screen
        js.executeScript("window.scrollBy(0, 500)");

        WebElement filterDieselCar = getElement(Locators.FILTER_DIESEL_CAR);
        filterDieselCar.click();

        Actions actions = new Actions(driver);
        // Move mouse cursor to a location outside the focus of action to close the filter
        actions.moveByOffset(1, 1).click().build().perform();
        js.executeScript("window.scrollBy(0, 500)");
        Thread.sleep(500);
        takeScreenCapture();
    }

    //Method to get the Price of the card printed in txt file
    public void priceResult() {

        WebElement lowestPriceElemen = getElement(Locators.LOWEST_PRICE_DIESEL_CAR);
        String lowestPrice = lowestPriceElemen.getText();

        WebElement highestPriceElem = getElement(Locators.HIGHEST_PRICE_DIESEL_CAR);
        String highestPrice = highestPriceElem.getText();

        //Create new with price, write in the price text file and file gets save in project directory
        try {
            FileWriter writer = new FileWriter("prices.txt");
            writer.write("Highest Price of Diesel Car: " + highestPrice + "\n");
            writer.write("Lowest Price of Diesel Car: " + lowestPrice);
            writer.close();
            System.out.println("Prices written to file successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing prices to file: " + e.getMessage());
        }
    }

    // Method to get a web element and wait based on the given locator
    public WebElement getElement(String locator) {
        try {
            Wait wait = new FluentWait(driver)
                    .withTimeout(Duration.ofSeconds(10))
                    .pollingEvery(Duration.ofMillis(1000))
                    .ignoring(NoSuchElementException.class);

            WebElement element = (WebElement) wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.querySelector" + locator));

            return element;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
