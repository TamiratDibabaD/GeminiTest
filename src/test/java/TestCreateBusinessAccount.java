import org.testng.Assert;
import org.testng.annotations.Test;

public class TestCreateBusinessAccount extends TestBase{

    @Test
    public void testHappyPath()
    {
        clickElementByXpath("//a[@data-testid='goToRegister']");
        clickElementByXpath("//a[@data-testid='cookiePolicyAgreement-close']");
        scrollWindow();
        clickElementByXpath("//*[text()='Create a business account']");
        verifyCurrentURL("https://exchange.sandbox.gemini.com/register/institution");
        sendTextToTextBox("//input[@name='company.legalName']","Test Company Name");
        selectCompanyType("Operating Company");
        SelectcountryOfBusiness("Georgia");
        //selectState("AL");
        sendTextToTextBox("//input[@name='personal.legalName.firstName']","Test First Name");
        sendTextToTextBox("//input[@name='personal.legalName.lastName']","Test Last Name");
        sendTextToTextBox("//input[@name='personal.email']","abc@gmail.com");
        scrollWindow();
        clickElementByXpath("//*[text()='Continue']");
        waitforElementToBePresent("//div[@class='NarrowTitle']/h3");
        verifyCurrentURL("https://exchange.sandbox.gemini.com/register/institution/thanks");
        Assert.assertEquals(getTextOfElementByXpath("//div[@class='NarrowTitle']/h3"),"Thanks for Registering!","Registration Confirmation Message is not proper");
    }

    @Test
    public void testRequiredFieldErrorMessage()
    {
        clickElementByXpath("//a[@data-testid='goToRegister']");
        clickElementByXpath("//a[@data-testid='cookiePolicyAgreement-close']");
        scrollWindow();
        clickElementByXpath("//*[text()='Create a business account']");
        verifyCurrentURL("https://exchange.sandbox.gemini.com/register/institution");
        scrollWindow();
        clickElementByXpath("//*[text()='Continue']");
        waitforElementToBePresent("//div[@class='AlertBody']");
        Assert.assertTrue(getWebElementByXpath("//div[@class='AlertBody']").isDisplayed(),"Expected Error Message not found");
    }

    @Test
    public void testRegistrationWithIncorrectEmail()
    {
        clickElementByXpath("//a[@data-testid='goToRegister']");
        clickElementByXpath("//a[@data-testid='cookiePolicyAgreement-close']");
        scrollWindow();
        clickElementByXpath("//*[text()='Create a business account']");
        verifyCurrentURL("https://exchange.sandbox.gemini.com/register/institution");
        sendTextToTextBox("//input[@name='company.legalName']","Test Company Name");
        selectCompanyType("Operating Company");
        SelectcountryOfBusiness("Georgia");
        //selectState("AL");
        sendTextToTextBox("//input[@name='personal.legalName.firstName']","Test First Name");
        sendTextToTextBox("//input[@name='personal.legalName.lastName']","Test Last Name");
        sendTextToTextBox("//input[@name='personal.email']","abcgmail.com");
        scrollWindow();
        clickElementByXpath("//*[text()='Continue']");
        waitforElementToBePresent("//div[@class='AlertBody']");
        Assert.assertTrue(getTextOfElementByXpath("//div[@class='AlertBody']").contains("Please specify a valid email domain"),"Expected Error Message not found");
    }

    private void selectCompanyType(String s) {
        clickElementByXpath("//*[text()='Company type']/following-sibling::div/div");
        selectValueFromDropDown(s);
    }

    private void SelectcountryOfBusiness(String s) {
        clickElementByXpath("//*[text()='Country of Business']/following-sibling::div/div");
        selectValueFromDropDown(s);
    }

    private void selectState(String s) {
        clickElementByXpath("//*[text()='State']/following-sibling::div/div");
        selectValueFromDropDown(s);
    }
}
