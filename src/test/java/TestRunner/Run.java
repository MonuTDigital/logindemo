package TestRunner;
import org.junit.runner.RunWith;

import io.cucumber.testng.CucumberOptions;
//import io.cucumber.junit.Cucumber;
//import io.cucumber.junit.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;

//@RunWith(Cucumber.class)



@CucumberOptions(
		
		features = ".//Features/LoginFeature.feature",
		glue="StepDefinition",
		dryRun = false,
		monochrome = true,
//		tags = "@Sanity",//scenarios under @sanity tag will be executed
		plugin = {"pretty","html:target/cucumber-reports/reports_html.html"}
		)
public class Run extends AbstractTestNGCucumberTests
{
	/* This class will be Empty */

}
