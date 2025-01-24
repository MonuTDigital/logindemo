package StepDefinition;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import PageObject.LoginPage;
import Utilities.ReadConfig;
import io.cucumber.java.*;
import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;

//Child class of Base Class
public class StepDef extends BaseClass
{
	@Before
	public void setup()
	{
		//properties file initialization
		readConfig=new ReadConfig();
		
		
		
		//initialization of log class
		log=LogManager.getLogger("StepDef");
		
		System.out.println("setup method executed...");
		
		//read browser name from readconfig method
		String browser=readConfig.getBrowser();
		
		ChromeOptions options=new ChromeOptions();
		options.addArguments("--headless"); // Enable headless mode
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
		
		//launch browser
		switch(browser.toLowerCase())
		{
		case "chrome":
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(options);
			driver.manage().window().maximize();
			break;

		case "msedge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;

		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
		default:
			driver = null;
			break;

		}
		
		
		log.info("Setup1 executed...");
	}
	
	@Given("User Launch Chrome browser")
	public void user_launch_chrome_browser() {
		loginPg=new LoginPage(driver);
	}
	
	
	@When("User opens URL {string}")
	public void user_opens_url(String url) {
		
		driver.get(url);
		log.info("url opened...");
	}
	
	//////////////////Login ////////////////
	////////////////////////////////////////////
	@When("User enters ATNT Email as {string}")
	public void user_enters_atnt_email_as(String atntUser) {
//		staticWait(7000);
		//move on login Window
				String origionalWindow=driver.getWindowHandle();
				Set<String> allPages=driver.getWindowHandles();
				for(String page:allPages)
				{
					driver.switchTo().window(page);
				}
				System.out.println("move to login window");
				dynamicWebDriverWait(By.xpath("//input[@id='username']"),60);
				loginPg.enterUserName(atntUser);
				log.info("Entered Email...");
	}
	@When("User enters ATNT Password as {string}")
	public void user_enters_atnt_password_as(String atntPass) {
		loginPg.enterATNTPassword(atntPass);
		log.info("Entered Password...");
	}
	
	@When("Click on Login to sandbox")
	public void click_on_login_to_sandbox() {
		loginPg.clickOnLoginButton();
		log.info("Clicked On Login to sandbox...");
		staticWait(5000);
	}
	
	@When("Click on Allow Access button")
	public void click_on_allow_access_button() {
		loginPg.clickOnAllowAccessButton();
		log.info("Clicked On Allow Access button...");
//		staticWait(10000);
		dynamicWebDriverWait(By.xpath("//aside[@class='main-sidebar elevation-4 sidebar-dark-primary left-sidebar']"),60);
	}
	
	//////////////Login Page Title Verified///////////////////
	@Then("Verify Page Title as {string}")
	public void verify_page_title_as(String title) {
	System.out.println("read Data from File:"+title);
	String actualTitle=driver.getTitle();
    System.out.println("read actual Title :"+actualTitle);
    String expectedTitle=title;
    
    Assert.assertEquals(expectedTitle, actualTitle);
    System.out.println("Title Verified");
    log.info("Verify Title....");
    
	
	}
	
	/////////////////Verify Login Page Logo////////////
	@Then("Verify Page Logo")
	public void verify_page_logo() {
		 WebElement logo = driver.findElement(By.xpath("//pf-image[@class='sc-gTRrQi kDtyhN hydrated']")); // Replace with actual ID or other locator

            // Verify logo presence
            if (logo.isDisplayed()) {
                System.out.println("Logo is displayed on the page.");

                // Verify logo's source attribute
                String logoSrc = logo.getAttribute("src");
                if (logoSrc.contains("https://i.ibb.co/XDjSrGn/at-t-logo-brandlogos-net-57cuk.png")) { // Replace with the expected image name or URL
                    System.out.println("Logo source is correct: " + logoSrc);
                } else {
                    System.out.println("Logo source is incorrect: " + logoSrc);
                }

                // Verify logo's alt text
                String logoAlt = logo.getAttribute("alt");
                if (logoAlt.equals("AT&T Logo")) { // Replace with the expected alt text
                    System.out.println("Logo alt text is correct: " + logoAlt);
                } else {
                    System.out.println("Logo alt text is incorrect: " + logoAlt);
                }
            } else {
                System.out.println("Logo is not displayed on the page.");
            }
	}
	
	@When("click on User Icon")
	public void click_on_user_icon() {
		staticWait(6000);
		loginPg.clickOnUserIcon();				
		log.info("Clicked on User Icon....");
		staticWait(2000);
	}
	
	@When("click on Logout button")
	public void click_on_logout_button() {
		switchToNewWindow("currentWindow");
		loginPg.clickOnLogoutButton();				
		log.info("Clicked on LogOut Button....");
		staticWait(5000);
	}
	
	@After
	public void teardown(Scenario sc)
	{
		System.out.println("tear down method executed...");
		
		if(sc.isFailed()==true)
		{
			//Convert web driver object to TakeScreenshot

			String fileWithPath = "D:\\Shivam Download\\Eclipse Workspace\\LoginPipeline\\DemoPipe\\Screenshot\\failedScreenshot.png";
			TakesScreenshot scrShot =((TakesScreenshot)driver);

			//Call getScreenshotAs method to create image file
			File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);

			//Move image file to new destination
			File DestFile=new File(fileWithPath);

			//Copy file at destination

			try {
				FileUtils.copyFile(SrcFile, DestFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		driver.quit();
	}

}
