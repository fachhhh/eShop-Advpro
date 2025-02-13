package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {
    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void testCreateProduct_Success(ChromeDriver driver) throws Exception {
        // Navigate to create product page
        driver.get(baseUrl + "/product/create");

        // Fill in the product details
        WebElement nameInput = driver.findElement(By.id("productName"));
        WebElement quantityInput = driver.findElement(By.id("productQuantity"));
        nameInput.sendKeys("Test Product");
        quantityInput.sendKeys("100");

        // Submit the form
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Verify we're redirected to the product list page
        assertEquals(baseUrl + "/product/list", driver.getCurrentUrl());

        // Verify the product appears in the list
        WebElement productTable = driver.findElement(By.className("table"));
        String tableContent = productTable.getText();
        assertTrue(tableContent.contains("Test Product"));
        assertTrue(tableContent.contains("100"));
    }

    @Test
    void testCreateProduct_EmptyName(ChromeDriver driver) throws Exception {
        driver.get(baseUrl + "/product/create");

        // Fill in only quantity
        WebElement quantityInput = driver.findElement(By.id("productQuantity"));
        quantityInput.sendKeys("100");

        // Submit the form
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Verify form validation (HTML5 validation)
        assertTrue(driver.getCurrentUrl().contains("/product/create"));
    }

    @Test
    void testCreateProduct_EmptyQuantity(ChromeDriver driver) throws Exception {
        driver.get(baseUrl + "/product/create");

        // Fill in only name
        WebElement nameInput = driver.findElement(By.id("productName"));
        nameInput.sendKeys("Test Product");

        // Submit the form
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Verify form validation (HTML5 validation)
        assertTrue(driver.getCurrentUrl().contains("/product/create"));
    }

    @Test
    void testCreateProduct_NavigationFromList(ChromeDriver driver) throws Exception {
        // Start from product list page
        driver.get(baseUrl + "/product/list");

        // Click create product button
        driver.findElement(By.linkText("Create Product")).click();

        // Verify navigation to create product page
        assertTrue(driver.getCurrentUrl().contains("/product/create"));
        assertEquals("Create New Product", driver.findElement(By.tagName("h3")).getText());
    }
}