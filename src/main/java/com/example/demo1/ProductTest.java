package com.example.demo1;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

@Listeners(CustomListeners.class)
public class ProductTest {
    static WebDriver driver;
    static ExtentReports extent;

    @BeforeTest
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "D:\\seleniumjars\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        String path = System.getProperty("user.dir") + "\\reports\\index.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("Web Automation Results");
        reporter.config().setDocumentTitle("Test Results");
        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester", "Vigneshkumar D");

    }

    @Test(priority = 1)
    public void testSuccessfulLogin() {
        driver.get("http://192.168.0.189:4000/login");
        WebElement usernameInput = driver.findElement(By.id("username"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.cssSelector(".btn.btn-lg.btn-primary.btn-block"));
        usernameInput.sendKeys("Administrator");
        passwordInput.sendKeys("123456");
        loginButton.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement usernameInput2 = driver.findElement(By.id("basic_userName"));
        WebElement passwordInput2 = driver.findElement(By.id("basic_password"));
        WebElement loginButton2 = driver.findElement(By.cssSelector(".ant-btn.css-ru2fok.ant-btn-primary.ant-btn-block"));

        usernameInput2.sendKeys("Administrator");
        passwordInput2.sendKeys("123456");
        loginButton2.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        wait.until(ExpectedConditions.urlToBe("http://192.168.0.189:4000/dashboard"));
        String expectedUrlAfterLogin = "http://192.168.0.189:4000/dashboard";
        String actualUrlAfterLogin = driver.getCurrentUrl();
        Assert.assertEquals(actualUrlAfterLogin, expectedUrlAfterLogin);
    }

    @Test(priority = 2)
    public void testProduct(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@role='menu']//li[2]")));
        element.click();
        WebElement element1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='ant-tabs-nav-list'])[1]//div[1]//div//a")));
        element1.click();

    }

    @AfterTest
    public void tearDown() {

        if (driver != null) {
//            driver.quit();
        }

    }
}
