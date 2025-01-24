package StepDefinition;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import PageObject.LoginPage;
import Utilities.ReadConfig;

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.logging.log4j.*;

//Parent Class
public class BaseClass 
{
	public WebDriverWait wait;
	
	public static WebDriver driver;
	public LoginPage loginPg;

	public static Logger log;
	
	public ReadConfig readConfig;


//dynamic WebDriverWait ///
		public WebElement dynamicWebDriverWait(By locator, int timeout)
		{
			try {
			wait=new WebDriverWait(driver,Duration.ofSeconds(timeout));
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			}catch(Exception e)
			{
				System.out.println("Error inspecting in dynamic wait:"+e.getMessage());
				return null;
			}
		}
		
		 public WebElement inspectLastElement(WebDriver driver, By locator) {
		        try {
		            // Find all matching elements
		            List<WebElement> elements = driver.findElements(locator);

		            // Check if the list is not empty
		            if (!elements.isEmpty()) {
		                WebElement lastElement = elements.get(elements.size() - 1); // Get the last element
		                System.out.println("Last Element Text: " + lastElement.getText());
		                System.out.println("Last Element Attribute (example): " + lastElement.getAttribute("attribute_name"));
		                return lastElement;
		            } else {
		                System.out.println("No elements found.");
		                return null;
		            }
		        } catch (Exception e) {
		            System.out.println("Error inspecting the last element: " + e.getMessage());
		            return null;
		        }
		    }
		 //// Read List of Dropdown /////
		 public boolean selectOptionByText(By locator, String text) {
		        try {
		            // Find all elements matching the locator
		            List<WebElement> elementList = driver.findElements(locator);

		            // Iterate through the options and select the desired one
		            for (WebElement option : elementList) {
		                System.out.println("List of dropdown: " + option.getText());
		                if (option.getText().equals(text)) {
		                    option.click(); // Click the desired option
		                    return true; // Return true if the option is found and clicked
		                }
		            }
		            System.out.println("Text '" + text + "' not found in the list.");
		            return false; // Return false if the option is not found
		        } catch (Exception e) {
		            System.out.println("Error while selecting option: " + e.getMessage());
		            return false; // Handle exceptions gracefully
		        }
		        
		    }
		 /// Switch to new Window
		 public boolean switchToNewWindow(String currentWindow) {
		        try {
		            // Get all window handles
		            Set<String> allWindows = driver.getWindowHandles();

		            // Iterate through the window handles
		            for (String window : allWindows) {
		                if (!window.equals(currentWindow)) {
		                    driver.switchTo().window(window); // Switch to the new window
		                    System.out.println("Switched to new window with handle: " + window.toString());
		                    return true; // Successful switch
		                }
		            }

		            System.out.println("No new window found.");
		            return false; // No new window found
		        } catch (Exception e) {
		            System.out.println("Error while switching to new window: " + e.getMessage());
		            return false; // Handle exception
		        }
		 
		 }
		 
		 public void staticWait(int timeout)
		 {
			 try {
				Thread.sleep(timeout);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
}

