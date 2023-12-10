package com.example.demo1;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;


@Listeners(CustomListeners.class)
public class LoginTest {
    static WebDriver driver;
    static ExtentReports extent;
//    public static void main(String[] args) {
//
//        LoginTest loginTest = new LoginTest();
//        loginTest.setUp();
//    }

    @BeforeTest
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "D:\\seleniumjars\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        String path = System.getProperty("user.dir")+"\\reports\\index.html";
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
    public void testRoleCreation(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@role='menu']//li[2]")));
        element.click();
        WebElement element1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='ant-tabs-nav-list'])[1]//div[1]//div//a")));
        element1.click();
        WebElement element2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='ant-row ant-row-space-between css-ru2fok'])//div[2]")));
        element2.click();

        WebElement roleNameInput1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='roleName']")));
        roleNameInput1.sendKeys("Test User(Super)");

        WebElement button1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
        button1.click();

        element2.click();

        // Input "Test User(Technician)" and click the button
        WebElement roleNameInput2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='roleName']")));
        roleNameInput2.sendKeys("Test User(Tech)");

        WebElement button2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
        button2.click();

        Boolean isRole1Added = validateRole("Test User(Super)");
        Boolean isRole2Added = validateRole("Test User(Tech)");
        Assert.assertTrue(isRole1Added&&isRole2Added, "Expected text 'Test User' not found in any element");
    }

    public Boolean validateRole(String role){
        boolean hasNextPage = true;
        boolean foundText = false;

        while (hasNextPage) {
            // Your code to iterate through elements on the current page
            List<WebElement> elementList = driver.findElements(By.xpath("//tr[@data-testId='row']//td[2]"));
            Iterator<WebElement> iterator = elementList.iterator();

            while (iterator.hasNext()) {
                WebElement webElement = iterator.next();

                if(webElement.getText().trim().equalsIgnoreCase(role)){
                    foundText = true;
                    break;  // Exit the loop once the text is found
                }
            }

            // Check if there is a next page by verifying the presence of the next page button
            try {
                WebElement nextPageButton = driver.findElement(By.xpath("(//button[@class='ant-pagination-item-link'])[2]"));
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
    public void testUserCreation() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));

        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='tab-link'])[1]//a[2]")));
        element.click();

        WebElement element1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@class='ant-btn css-ru2fok ant-btn-primary'])[1]")));
        element1.click();

        driver.findElement(By.xpath("(//input[@id='userName'])")).sendKeys("Test User 1");
        WebElement user1RoleId = driver.findElement(By.xpath("//input[@id='roleId']"));
        user1RoleId.click();
        try {
            WebElement superUserElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'Super User')]")));
            Actions actions = new Actions(driver);
            actions.moveToElement(superUserElement);
            actions.perform();
            superUserElement.click();
        } catch (Exception e) {
            e.printStackTrace();
        }

        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("testuser@gmail.com");
        driver.findElement(By.xpath(" //input[@id='contactNumber']")).sendKeys("9876543210");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Testuser@123");
        WebElement user1Site = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='ahid']")));
        user1Site.click();
        WebElement user1BFALele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='ant-select-tree-title']")));
        user1BFALele.click();
        driver.findElement(By.xpath("(//button[@type='submit'])[1]")).click();
        Boolean isValidUser1 = userValidation("Test User 1");

        //Second User
        element.click();
        element1.click();
        driver.findElement(By.xpath("(//input[@id='userName'])")).sendKeys("Test User 3");
        WebElement user2RoleId = driver.findElement(By.xpath("//input[@id='roleId']"));
        user2RoleId.click();
        try {
            WebElement superUserElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'Technician')]")));
            Actions actions = new Actions(driver);
            actions.moveToElement(superUserElement);
            actions.perform();
            superUserElement.click();
        } catch (Exception e) {
            e.printStackTrace();
        }

        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("testuser@gmail.com");
        driver.findElement(By.xpath(" //input[@id='contactNumber']")).sendKeys("9876543210");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Testuser@123");
        WebElement user2Site = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='ahid']")));
        user2Site.click();
        WebElement user2BFALele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='ant-select-tree-title']")));
        user2BFALele.click();
        driver.findElement(By.xpath("(//button[@type='submit'])[1]")).click();
        Boolean isValidUser2 = userValidation("Test User 2");
        Assert.assertTrue(isValidUser1 && isValidUser2);
    }

    public Boolean userValidation(String userName){
        boolean hasNextPage = true;
        boolean foundText = false;


        while (hasNextPage) {
            // Your code to iterate through elements on the current page
            List<WebElement> elementList = driver.findElements(By.xpath("//tr[@data-testId='row']//td[2]"));
            Iterator<WebElement> iterator = elementList.iterator();

            while (iterator.hasNext()) {
                WebElement webElement = iterator.next();

                if(webElement.getText().trim().equalsIgnoreCase("Test User 1")){
                    foundText = true;
                    break;  // Exit the loop once the text is found
                }
            }

            // Check if there is a next page by verifying the presence of the next page button
            try {
                WebElement nextPageButton = driver.findElement(By.xpath("//li[@title='Next Page']//button[@type='button']"));
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


    @Test(priority = 5)
    public void testUserAccess() {
        driver.findElement(By.xpath("//a[normalize-space()='UserAccess']")).click();
        WebElement element = driver.findElement(By.xpath("//input[@id='roleName']"));

        this.providingUserAccess("Test User(Super)");

        this.providingUserAccess("Test User(Tech)");

    }

    public void providingUserAccess(String role){
        String xpathExpression = "//div[contains(text(),'" + role + "')]";
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));

        WebElement roleNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='roleName']")));
        Actions actions = new Actions(driver);
        actions.moveToElement(roleNameInput).click().perform();
        roleNameInput.sendKeys(role);
        WebElement element1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathExpression)));
        element1.click();

        driver.findElement(By.xpath("(//div[@class='ant-col css-ru2fok'])[1]")).click();
        driver.findElement(By.xpath("(//div[@class='ant-col css-ru2fok'])[3]")).click();
        driver.findElement(By.xpath("(//div[@class='ant-col css-ru2fok'])[5]")).click();
        driver.findElement(By.xpath("(//div[@class='ant-col css-ru2fok'])[7]")).click();
        driver.findElement(By.xpath("(//div[@class='ant-col css-ru2fok'])[9]")).click();
        driver.findElement(By.xpath("(//div[@class='ant-col css-ru2fok'])[11]")).click();
        driver.findElement(By.xpath("(//div[@class='ant-col css-ru2fok'])[13]")).click();
        driver.findElement(By.xpath("(//div[@class='ant-col css-ru2fok'])[15]")).click();
        driver.findElement(By.xpath("(//div[@class='ant-col css-ru2fok'])[17]")).click();
        driver.findElement(By.xpath("(//div[@class='ant-col css-ru2fok'])[19]")).click();
        if(role.equalsIgnoreCase("Test User(Tech)")){
            driver.findElement(By.xpath("(//td[@class='ant-table-cell'])[33]//div//label[2]")).click();
        }else{
            driver.findElement(By.xpath("(//div[@class='ant-col css-ru2fok'])[21]")).click();
            driver.findElement(By.xpath("(//div[@class='ant-col css-ru2fok'])[23]")).click();
        }
        driver.findElement(By.xpath("(//div[@class='ant-col css-ru2fok'])[25]")).click();
        driver.findElement(By.xpath("(//div[@class='ant-col css-ru2fok'])[27]")).click();
        driver.findElement(By.xpath("(//div[@class='ant-col css-ru2fok'])[29]")).click();
        driver.findElement(By.xpath("(//div[@class='ant-col css-ru2fok'])[31]")).click();
        driver.findElement(By.xpath("(//div[@class='ant-col css-ru2fok'])[33]")).click();
        driver.findElement(By.xpath("(//div[@class='ant-col css-ru2fok'])[35]")).click();
        driver.findElement(By.xpath("//div[@id='rc-tabs-1-panel-general']//span[contains(text(),'Save')]")).click();

        wait.until(webDriver -> {
            try {
                Thread.sleep(5000); // Introduce a delay of 5 seconds (5000 milliseconds)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true; // Return true after the desired delay
        });

    }


    @Test(priority = 4)
    public void testUserGroupCreation(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        WebElement userGroupCreation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[normalize-space()='User Group']")));
        userGroupCreation.click();
        WebElement addButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[normalize-space()='Add']")));
        addButton.click();

        driver.findElement(By.xpath("//input[@id='userGroupName']")).sendKeys("Test Group");
        driver.findElement(By.xpath("//input[@id='description']")).sendKeys("This Group is created for Testing purpose");
        WebElement site = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='ahid']")));
        site.click();
        WebElement BFALele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='ant-select-tree-title']")));
        BFALele.click();

        WebElement dropdown =driver.findElement(By.xpath("//input[@id='userIds']"));
        dropdown.click();

        dropdown.sendKeys("Test User 1");
        WebElement member1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Test User 1')]")));
        member1.click();

        dropdown.sendKeys("Test User 2");
        WebElement member2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Test User 2')]")));
        member2.click();
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='none';", dropdown);

//        WebElement radioButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@type='radio'])[1]")));
//        radioButton.click();
        WebElement saveButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@type='submit']")));
        saveButton.click();


        boolean hasNextPage = true;
        boolean foundText = false;

        while (hasNextPage) {
            // Iterate through elements on the current page
            List<WebElement> elementList = driver.findElements(By.xpath("//tr[@data-testid='row']//td[2]"));
            Iterator<WebElement> iterator = elementList.iterator();

            while (iterator.hasNext()) {
                WebElement webElement = iterator.next();

                if(webElement.getText().trim().equalsIgnoreCase("Test Group")){
                    foundText = true;
                    break;  // Exit the loop once the text is found
                }
            }

            // Check if there is a next page by verifying the presence of the next page button
            try {
                WebElement nextPageButton = driver.findElement(By.xpath("//li[@title='Next Page']//button"));
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
        Assert.assertTrue(foundText, "Expected text 'Test Group' not found in any element");
    }


    @AfterTest
    public void tearDown() {

        if (driver != null) {
//            driver.quit();
        }
    }

}
