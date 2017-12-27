package multiple;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import Executor.IAutoconst;

public class D implements IAutoconst
{
	static
	{
		System.setProperty(CHROME_KEY, CHROME_VALUE);
	}
	public static void main(String[] args) 
	{
		WebDriver driver = new ChromeDriver();
		driver.get("http://localhost/app/admin");
		driver.findElement(By.xpath("//input[@name='username']")).sendKeys("hi");
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("h");
		driver.findElement(By.xpath("//button[.='Login']")).click();
		//boolean d = driver.findElement(By.xpath("//div[@class='alert alert-danger'and contains(.,'information you had')]")).isDisplayed();
		boolean d = driver.findElement(By.xpath("//div[@class='alert alert-danger']")).isDisplayed();

		System.out.println(d);

	}
}
