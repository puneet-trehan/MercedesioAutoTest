package MercedesTest;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.annotations.AfterTest;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;


public class BaseMercedesTest {

        public static WebDriver driver;
        public static String screenshotFolderPath;

        public static void setUpBrowser(String browser)  {
            if(browser.equalsIgnoreCase("Chrome")) {
                //Set Path for chrome driver
                String driverPath=System.getProperty("user.dir")+"//drivers//chromedriver.exe";
                System.setProperty("webdriver.chrome.driver", driverPath);
                // Create a ChromeOptions object
                ChromeOptions options = new ChromeOptions();
                // To avoid latest Chrome errors
                options.addArguments("--remote-allow-origins=*");

                // Open Chrome in maximum size
                options.addArguments("start-maximized");

                // Accept all cookies
                options.addArguments("--disable-features=SameSiteByDefaultCookies,CookiesWithoutSameSiteMustBeSecure");

                // Disable web security
                options.addArguments("--disable-web-security");

                // Enable network service features
                options.addArguments("--enable-features=NetworkService,NetworkServiceInProcess");

                // Disable site isolation trials
                options.addArguments("--disable-site-isolation-trials");

                // Set user data directory for Chrome profile
                options.addArguments("--user-data-dir=/tmp/selenium-chrome-profile");

                // Disable Chrome notification used by automation
                options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));

                driver = new ChromeDriver(options);
            }else if(browser.equalsIgnoreCase("Firefox")) {
                //Set path for firefox driver
                String driverPath=System.getProperty("user.dir")+"//drivers//geckodriver.exe";
                System.setProperty("webdriver.gecko.driver", driverPath);

                // Create a FirefoxOptions object
/*
                FirefoxOptions options = new FirefoxOptions();
*/
                /*options.addArguments("--user-data-dir=/tmp/selenium-firefox-profile");*/

                // Add options to the FirefoxOptions object
                FirefoxOptions options = new FirefoxOptions();
                FirefoxProfile profile = new FirefoxProfile();



                profile.setPreference("network.cookie.cookieBehavior", 1);
                profile.setPreference("network.cookie.lifetimePolicy", 2);
                profile.setPreference("dom.webnotifications.enabled", false);
                profile.setPreference("privacy.firstparty.isolate", false);
                profile.setPreference("browser.contentblocking.introCount", 2);
                options.setProfile(profile);


                driver = new FirefoxDriver(options);
            }else {
                // handle unrecognized browser
                System.out.println("Unrecognized browser: " + browser);
            }

            driver.get("https://www.mercedes-benz.co.uk");
        }

        public void takeScreenCapture(){

            // fileName of the screenshot
            Date d=new Date();
            String screenshotFile=d.toString().replace(":", "_").replace(" ", "_")+".png";
            String reportsFolder=d.toString().replaceAll(":", "-") +"//screenshots";
            screenshotFolderPath = System.getProperty("user.dir") +"//reports//"+reportsFolder;
            // take screenshot
            File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            try {
                // get the dynamic folder name
                FileUtils.copyFile(srcFile, new File(screenshotFolderPath+"//"+screenshotFile));

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            File f = new File(screenshotFolderPath);
            f.mkdirs();


        }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
