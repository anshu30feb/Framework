package multiple;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import Executor.Driver;
import Executor.IAutoconst;

public class Demo extends Thread implements IAutoconst
{
	String browser;
	public Demo(String s)
	{
		browser=s;
	}
	
	public void run()
	{
		WebDriver driver;
		if(browser.equals("chrome"))
		{
			driver=new ChromeDriver();
		}
		else
		{
			driver=new FirefoxDriver();
		}
		
		driver.get("http://localhost/app/admin");
		for(int i=1;i<=100;i++)
		{
			driver.findElement(By.name("username")).sendKeys("abhijeet");
			driver.findElement(By.name("username")).clear();
		}
		//Driver.main(null);
	}
	static
	{
		System.setProperty(CHROME_KEY, CHROME_VALUE);
		System.setProperty(GECKO_KEY, GECKO_VALUE);
	}
	public static void main(String[] args) 
	{
//		Demo d=new Demo("chrome");
//		d.start();
		
		Demo d1=new Demo("firefox");
		d1.start();
		
	}
}
