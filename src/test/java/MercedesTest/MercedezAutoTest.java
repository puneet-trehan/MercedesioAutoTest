package MercedesTest;

import org.testng.annotations.Test;

public class MercedezAutoTest extends GenericMercedesTest {

    @Test
    public void dieselCarPrice() throws InterruptedException {
        setUpBrowser("chrome");
        selectModel();
        selectCarType();
        selectCarModel();
        buildCar();
        addFilter();
        filterFuelType();
        priceResult();

    }

}
