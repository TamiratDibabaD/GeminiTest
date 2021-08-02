import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import java.io.FileReader;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {

    WebDriver driver = null;
    WebDriverWait wait = null;
    Properties env = null;
    Properties element = null;

    @BeforeMethod
    void OpenUrl(){
        initialiseRespectiveDriver();
        System.out.println("WebDriver Initialised!");
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver,120);
        driver.get(env.getProperty("app.url"));
    }



    @BeforeSuite
    void setProperties()
    {
        env = loadFile("src/main/resources/env.properties");
        //element = loadFile("src/main/resources/element.properties");
    }

    private void initialiseRespectiveDriver() {
        String browser =  env.getProperty("browser");
        switch(browser)
        {
            case "firefox":
                System.out.println("Starting Firefox Driver");
                WebDriverManager.firefoxdriver().setup();
                driver = new ChromeDriver();
                break;
            case "ie":
                System.out.println("Starting IE Driver");
                WebDriverManager.iedriver().setup();
                driver = new ChromeDriver();
                break;
            default:
                System.out.println("Starting chrome Driver");
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-notifications");
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(options);
                break;
        }
    }

    Properties loadFile(String FileName){
        FileReader reader= null;
        Properties p= null;
        try {
            reader = new FileReader(FileName);
            p=new Properties();
            p.load(reader);
        } catch (Exception e){e.printStackTrace();}
        return p;
    }

    void clickElementByXpath(String xpath)
    {
        System.out.println("Clicking on element "+ xpath);
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(xpath))));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        driver.findElement(By.xpath(xpath)).click();
    }

    void waitforElementToBePresent(String s) {
        System.out.println("Waiting for element "+ s);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(s)));
    }

    void selectValueFromDropDown(String s) {
        System.out.println("Selecting "+ s + " from dropdown");
        basicWait(2000);
        String xpath = "//*[text()='" + s + "']";
        clickElementByXpath(xpath);
    }

    String getTextOfElementByXpath(String xpath) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        return driver.findElement(By.xpath(xpath)).getText();
    }

    WebElement getWebElementByXpath(String xpath) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        return driver.findElement(By.xpath(xpath));
    }

    void sendTextToTextBox(String xpath, String text) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        driver.findElement(By.xpath(xpath)).sendKeys(text);
    }

    void verifyCurrentURL(String ExpectedURL) {
        Assert.assertEquals(driver.getCurrentUrl(),ExpectedURL,"URL is not matching");
    }

    void scrollWindow() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1000)");
        basicWait(5000);
    }

    void basicWait(int a){try{Thread.sleep(a);}catch (Exception e){}}

    @AfterMethod
    void CloseUrl(){
        driver.close();
    }

    @AfterSuite
    void CloseBrowsers(){
        //driver.quit();
    }
}
