package com.example.demo1;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.BooleanSupplier;

@Listeners(CustomListeners.class)
public class SchedulerTest {
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
        String username = "Administrator";
        String password = "123456";
        WebElement usernameInput = driver.findElement(By.id("username"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.cssSelector(".btn.btn-lg.btn-primary.btn-block"));
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        loginButton.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.login(username, password);
    }

    public void login(String username, String password){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        WebElement usernameInput2 = driver.findElement(By.id("basic_userName"));
        WebElement passwordInput2 = driver.findElement(By.id("basic_password"));
        WebElement loginButton2 = driver.findElement(By.cssSelector(".ant-btn.css-ru2fok.ant-btn-primary.ant-btn-block"));

        usernameInput2.sendKeys(username);
        passwordInput2.sendKeys(password);
        loginButton2.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        wait.until(ExpectedConditions.urlToBe("http://192.168.0.189:4000/dashboard"));
        String expectedUrlAfterLogin = "http://192.168.0.189:4000/dashboard";
        String actualUrlAfterLogin = driver.getCurrentUrl();
        Assert.assertEquals(actualUrlAfterLogin, expectedUrlAfterLogin);
    }

//    @Test(priority = 2)
//    public void testSchedulerCreation(){
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
//        WebElement schedulerElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@role='menu']//li[3]")));
//        schedulerElement.click();
//
//        WebElement today = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='rbc-day-bg rbc-today']")));
//        Actions actions = new Actions(driver);
//        actions.moveToElement(today).click().perform();
//
//        WebElement assertInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='assetId']")));
//        assertInput.click();
//
//        WebElement assertName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Aging Furnace 11')]")));
//        assertName.click();
//
//        driver.findElement(By.xpath("//div[@class='ant-select-selection-overflow']")).click();
//        WebElement checkListInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Daily check')]")));
//        checkListInput.click();
//
//        driver.findElement(By.xpath("//input[@id='frequency']")).click();
//        WebElement frequencyInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='ant-select-item-option-content'][normalize-space()='Daily']")));
//        frequencyInput.click();
//
//        driver.findElement(By.xpath("//input[@id='userId']")).click();
//        WebElement assignedToUserInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'benny')]")));
//        assignedToUserInput.click();
//
//        driver.findElement(By.xpath("//input[@id='scheduleDate']")).click();
//        WebElement startDateInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@class='ant-picker-content']")));
//        startDateInput.click();
//
//        driver.findElement(By.xpath("//input[@id='startTime']")).click();
//        WebElement startTimeInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='ant-picker-dropdown css-ru2fok ant-picker-dropdown-placement-bottomLeft ']//a[@class='ant-picker-now-btn'][normalize-space()='Now']")));
//        startTimeInput.click();
//
//        WebElement endTimeInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='endTime']")));
//        endTimeInput.click();
//        endTimeInput.sendKeys("18:05:00");
//
//        WebElement okInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='ant-picker-dropdown css-ru2fok ant-picker-dropdown-placement-bottomLeft ']//li[@class='ant-picker-ok'][normalize-space()='OK']")));
//        okInput.click();
//
//        driver.findElement(By.xpath("//textarea[@id='description']")).sendKeys("Testing");
//        driver.findElement(By.xpath("//span[normalize-space()='Save']")).click();
//
//        String regexPattern = "more";
//
//        String xpathExpression = "//button[contains(normalize-space(), '" + regexPattern + "')]";
//
//        WebElement schedulers = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[@class='ant-radio-button-wrapper ant-radio-button-wrapper-checked css-ru2fok']")));
//
//        WebElement dayButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[normalize-space()='Day']")));
//        dayButton.click();
//
//        WebElement todayScheduler = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"rbc-events-container\"]")));
//
//        boolean foundText = false;
//        List<WebElement> todaySchedulers = driver.findElements(By.xpath("//div[@class='rbc-events-container']/div"));
//
//        for (WebElement webElement : todaySchedulers) {
//            try {
//                WebElement subElement = webElement.findElement(By.xpath(".//h3[@style='margin-bottom: 1px;']"));
//                if (subElement.getText().equalsIgnoreCase("benny")) {
//                    foundText = true;
//                    break;
//                }
//            } catch (NoSuchElementException e) {
//                // Handle exception or log a message if the h3 element is not found within this div
//                System.out.println("h3 element not found within this div");
//            }
//        }
//        Assert.assertTrue(foundText);
//    }

    @Test(priority = 2)
    public void testChecklistExecution() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement schedulerElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@role='menu']//li[4]")));
        schedulerElement.click();
        Assert.assertTrue(this.validateChecklistExecution());
    }

    public Boolean validateChecklistExecution() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean hasNextPage = true;
        boolean foundText = false;
        String assignedTo = "benny";
        String description = "Testing";
        String assertName = "Aging Furnace 11";

        while (hasNextPage) {

            List<WebElement> tableData =  driver.findElements(By.xpath("//tbody//tr"));
            System.out.println(tableData.size());

            for (int i = 0; i < tableData.size(); i++) {
                WebElement webElement = tableData.get(i);
                WebElement assignedToElement;
                WebElement descriptionElement;
                WebElement assertNameElement;

                try {
                    descriptionElement = webElement.findElement(By.xpath(".//td[5]"));
                    assertNameElement = webElement.findElement(By.xpath(".//td[6]"));
                    assignedToElement = webElement.findElement(By.xpath(".//td[7]"));

                } catch (StaleElementReferenceException e) {
                    System.out.println("Stale element reference encountered");
                    try {
                        Thread.sleep(3000);
                    }catch (Exception e1){
                        e1.printStackTrace();
                    }
                    tableData = driver.findElements(By.xpath("//tbody//tr"));
                    webElement = tableData.get(i);
                    descriptionElement = webElement.findElement(By.xpath(".//td[5]"));
                    assertNameElement = webElement.findElement(By.xpath(".//td[6]"));
                    assignedToElement = webElement.findElement(By.xpath(".//td[7]"));
                }
                System.out.println(assignedToElement.getText());
                Boolean isValidRole = assignedToElement.getText().equalsIgnoreCase(assignedTo);
                Boolean isValidDes = descriptionElement.getText().equalsIgnoreCase(description);
                Boolean isValidAssert = assertNameElement.getText().equalsIgnoreCase(assertName);

                if (isValidRole && isValidDes && isValidAssert) {
                    foundText = true;
                    System.out.println(foundText);
                    break;  // Exit the loop once the text is found
                }
            }

            try {
                WebElement nextPageButton = driver.findElement(By.xpath("(//button[@class=\"ant-pagination-item-link\"])[2]"));
                if (nextPageButton.isEnabled()) {
                    nextPageButton.click(); // Click the next page button

                } else {
                    hasNextPage = false; // Set hasNextPage to false if there's no next page
                }
            } catch (NoSuchElementException e) {
                System.out.println("No next page button found. Exiting loop.");
                hasNextPage = false; // Exit loop if there's no next page button
            }
        }
        return foundText;
    }

    @Test(priority = 3)
    public void testLogout(){
        driver.findElement(By.xpath("//strong[normalize-space()='Administrator']")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement logoutElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[normalize-space()='Logout']")));
        logoutElement.click();
    }

    @Test(priority = 4)
    public void testSchedulerExecution() throws InterruptedException {
        this.login("benny", "123456");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement schedulerElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@role='menu']//li[4]")));
        schedulerElement.click();
        boolean hasNextPage = true;
        boolean foundText = false;
        String assignedTo = "benny";
        String description = "Testing";
        String assertName = "Aging Furnace 11";

        while (hasNextPage) {
            List<WebElement> tableData = driver.findElements(By.xpath("//tbody//tr"));
            for (int i = 0; i < tableData.size(); i++) {
                WebElement webElement = tableData.get(i);
                WebElement assignedToElement;
                WebElement descriptionElement;
                WebElement assertNameElement;
                WebElement openElement;
                try {
                    Thread.sleep(3000);
                }catch (Exception e1){
                    e1.printStackTrace();
                }

                try {
                    descriptionElement = webElement.findElement(By.xpath(".//td[5]"));
                    assignedToElement = webElement.findElement(By.xpath(".//td[7]"));
                    assertNameElement = webElement.findElement(By.xpath(".//td[6]"));
                    openElement = webElement.findElement(By.xpath("//span[contains(text(),'Open')]"));
                } catch (StaleElementReferenceException | NoSuchElementException e) {
                    System.out.println("Stale element reference encountered");
                    tableData = driver.findElements(By.xpath("//tbody//tr"));
                    webElement = tableData.get(i);
                    descriptionElement = webElement.findElement(By.xpath(".//td[5]"));
                    assignedToElement = webElement.findElement(By.xpath(".//td[7]"));
                    assertNameElement = webElement.findElement(By.xpath(".//td[6]"));
                    try {
                        Thread.sleep(3000);
                    }catch (Exception e1){
                        e1.printStackTrace();
                    }
                    openElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Open')]")));
                }

                Boolean isValidRole = assignedToElement.getText().equalsIgnoreCase(assignedTo);
                Boolean isValidDes = descriptionElement.getText().equalsIgnoreCase(description);
                Boolean isValidAssert = assertNameElement.getText().equalsIgnoreCase(assertName);

                if (isValidRole && isValidDes && isValidAssert) {
                    foundText = true;
                    openElement.click();
                    this.updateCheckList();
                    System.out.println(foundText);
                    break;  // Exit the loop once the text is found
                }
            }

//            try {
//                WebElement nextPageButton = driver.findElement(By.xpath("(//button[@class=\"ant-pagination-item-link\"])[2]"));
//                if (nextPageButton.isEnabled()) {
//                    nextPageButton.click(); // Click the next page button
//
//                } else {
//                    hasNextPage = false; // Set hasNextPage to false if there's no next page
//                }
//            } catch (NoSuchElementException e) {
//                System.out.println("No next page button found. Exiting loop.");
//                hasNextPage = false; // Exit loop if there's no next page button
//            }
        }

    }


    public void updateCheckList(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        System.out.println("In Update Checklist");
//        WebElement startButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[normalize-space()='Start']")));
//        startButton.click();

        List<WebElement> elementList = driver.findElements(By.xpath("//table[@class='table table-stripped']//tbody/tr"));
        System.out.println("Update Checklist"+elementList.size());
//        for(int i=0; i<elementList.size(); i++){
//            System.out.println("in Loop");
//            String xpathExpression = "//div[contains(@id, 'checks_" + i + "_status')]//span[contains(text(),'Yes')]";
//            driver.findElement(By.xpath(xpathExpression)).click();
//
//            if(i==elementList.size()-1){
//                xpathExpression = "//div[contains(@id, 'checks_" + i + "_status')]//span[contains(text(),'No')]";
//                driver.findElement(By.xpath(xpathExpression)).click();
//            }
//        }
//
//        WebElement savePreviewEle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[normalize-space()='Save Preview']")));
//        savePreviewEle.click();



    }


    @AfterTest
    public void tearDown() {

        if (driver != null) {
//            driver.quit();
        }
    }
}
