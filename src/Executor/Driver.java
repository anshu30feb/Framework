																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																				package Executor;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import generic.Excel;
import generic.Property;
import generic.Screenshot;

public class Driver implements IAutoconst{
     public static Logger log=Logger.getLogger("Driver");
     public static int pCount=0, fCount=0,tCount=0;
     public static WebDriver driver;
	static
	{
		log.info("set the path of drive executable");
		System.setProperty(CHROME_KEY,CHROME_VALUE);
	}
	
	public static void executeScript(String scriptName) throws Exception
	{
		log.info("open browser in remote system");
		URL remote = new URL(Property.getValue(SETTINGS_PATH, "REMOTE"));
		DesiredCapabilities d = new DesiredCapabilities();
		d.setBrowserName(Property.getValue(SETTINGS_PATH, "BROWSER"));
		
		driver=new RemoteWebDriver(remote, d);
		
//		log.info("open chrome browser");
//		driver=new ChromeDriver();
	
//		driver.manage().window().maximize();				//to maximize
		
		String sito=Property.getValue(SETTINGS_PATH, "ITO");
		Long l=Long.parseLong(sito);
		log.info("Set ITO:"+sito+"Sec");
		driver.manage().timeouts().implicitlyWait(l, TimeUnit.SECONDS);
		String url=Property.getValue(SETTINGS_PATH, "URL");
		log.info("enter the url:"+url);
		driver.get(url);
		log.info("Executing Script:"+scriptName);
		
		Steps.executeSteps(log , driver,SCRIPT_PATH, scriptName);
		
		log.info("end of script:"+scriptName);
	}
	
	public static void executeSuite()
	{
		ExtentReports extent=new ExtentReports();
		ExtentHtmlReporter htmlReport=new ExtentHtmlReporter(EXTENT_REPORT_PATH);
		extent.attachReporter(htmlReport);
		int scriptCount=Excel.getRowCount(SUITE_PATH, SUITE_SHEET);
		log.info("Script count:"+scriptCount);
		for(int j=1;j<=scriptCount;j++)
		{
			String scriptName=Excel.getCellValue(SUITE_PATH, SUITE_SHEET, j, 0);
			ExtentTest test=extent.createTest(scriptName);
			String scriptStatus=Excel.getCellValue(SUITE_PATH, SUITE_SHEET, j, 1);
			log.info("Script:"+scriptName+" Execute:"+scriptStatus);
			if(scriptStatus.equalsIgnoreCase("yes"))
			{
				try {
				executeScript(scriptName);
				log.info("Script is Passed:"+scriptName);
				pCount++;
				test.pass("Script is passed");
				}
				
				catch(Exception e)
				{
					log.error("Script is Failed:"+scriptName);
					fCount++;
					e.printStackTrace();
					String imgName=Screenshot.generateImageName();
                    String imgPath=FAIL_PHOTO_PATH+"/"+scriptName+"_"+imgName;
					log.info("Taking Screenshot:"+imgPath);
					Screenshot.get(driver, imgPath);
					
					test.fail("Script is Failed");
					String aImagePath=new File(imgPath).getAbsolutePath();
					try
					{
						test.addScreenCaptureFromPath(aImagePath);
					}				
					catch(IOException e1)
					{
						
					}
				}
				finally
				{
					driver.quit();
					log.info("quit the browser");
				}
		}
		else
		{
			log.info("Not executing script:"+scriptName);
			test.skip("Not executing script:");
		}
		}
		log.info("------------------------------------------");
		log.info("Total PASS:"+pCount);
		log.info("Total FAIL:"+fCount);
		tCount=pCount+fCount;
		log.info("Total Scripts executed:"+tCount);
		log.info("------------------------------------------");
	
	extent.flush();	
	}
	public static void main(String[] args)
	{	
		log.info("Suite execution started");
		executeSuite();
		log.info("Suite execution completed");
	}
}