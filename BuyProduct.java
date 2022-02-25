package testngtest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.openqa.selenium.interactions.Actions;

import javax.swing.*;

public class BuyProduct {

    WebDriver driver;


    @Test
    void openBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demo.nopcommerce.com/");
    }

    @Test(dependsOnMethods = {"openBrowser"})
    void selectProduct() throws InterruptedException {

        WebElement item = driver.findElement(By.xpath("/html/body/div[6]/div[2]/ul[1]/li[1]/a"));
        Actions actions = new Actions(driver);
        actions.moveToElement(item).perform();
        driver.findElement(By.xpath("/html/body/div[6]/div[2]/ul[1]/li[1]/ul/li[1]/a")).click();
        driver.findElement(By.xpath("/html/body/div[6]/div[3]/div/div[3]/div/div[2]/div[2]/div[2]/div/div/div[3]/div/div[1]/a")).click();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)", "");
        Thread.sleep(1000);

    }

    @Test(dependsOnMethods = {"selectProduct"})
    void addToCart() {
        driver.findElement(By.id("add-to-cart-button-3")).click();
    }

    //@Test
//       void verify () {
//
//       WebElement el = driver.findElement(By.className("bar-notification-container"));
//
//       WebElement w = el.findElement(By.className("bar-notification success"));
//       String s= el.findElement(By.className("content")).getText();
//        }

    @Test(dependsOnMethods = {"addToCart"})
    void checkout() throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"topcartlink\"]/a/span[1]")).click();
        WebElement w = driver.findElement(By.className("qty-input"));
        w.clear();
        w.sendKeys("3");
        driver.findElement(By.id("termsofservice")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("checkout")).click();
        driver.findElement(By.className("checkout-as-guest-button")).click();
    }
    @Test(dependsOnMethods = {"checkout"})
    void checkoutForm() {
        driver.findElement(By.id("BillingNewAddress_FirstName")).sendKeys("Anjana");
        driver.findElement(By.id("BillingNewAddress_LastName")).sendKeys("Vaghasiya");
        driver.findElement(By.id("BillingNewAddress_Email")).sendKeys("borad.anju@gmail.com");
        driver.findElement(By.id("BillingNewAddress_Company")).sendKeys("Unify");
        WebElement country = driver.findElement(By.id("BillingNewAddress_CountryId"));
        Select select = new Select(country);
        select.selectByVisibleText("United Kingdom");
        driver.findElement(By.id("BillingNewAddress_City")).sendKeys("Crawley");
        driver.findElement(By.id("BillingNewAddress_Address1")).sendKeys("Iris House");
        driver.findElement(By.id("BillingNewAddress_ZipPostalCode")).sendKeys("RH103NH");
        driver.findElement(By.id("BillingNewAddress_PhoneNumber")).sendKeys("7876543452");
        driver.findElement(By.className("new-address-next-step-button")).click();
    }

    @Test(dependsOnMethods = {"checkoutForm"})
    void shippingMethod(){
        driver.findElement(By.id("shippingoption_0")).click();
        driver.findElement(By.className("shipping-method-next-step-button")).click();
        driver.findElement(By.name("paymentmethod"));
        driver.findElement(By.className("payment-method-next-step-button")).click();
        driver.findElement(By.className("payment-info-next-step-button")).click();
        driver.findElement(By.className("confirm-order-next-step-button")).click();
    }
}




