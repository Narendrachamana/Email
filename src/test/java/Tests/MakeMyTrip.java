package Tests;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MakeMyTrip {

	public static void main(String[] args) throws InterruptedException {

		WebDriver driver;
		WebDriverManager.chromedriver().setup();
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("disable-extensions");
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		driver = new ChromeDriver(capabilities);
		driver.manage().window().maximize();

		driver.get("https://www.makemytrip.com");
		Thread.sleep(5000);

		JavascriptExecutor js = (JavascriptExecutor) driver;
		Thread.sleep(5000);

		WebElement roundTrip = driver.findElement(By.xpath("(//span[@class='tabsCircle appendRight5'])[2]"));

		js.executeScript("arguments[0].click();", roundTrip);
		Thread.sleep(4000);

		WebElement dateSelector = driver.findElement(By.xpath("//span[@class='lbl_input latoBold appendBottom10']"));

		js.executeScript("arguments[0].click();", dateSelector);

				
		String str=futureDateByDays(90).toString();
		System.out.println("TRest111"+str);
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMMM-yyyy");
		String date = formatter.format(futureDateByDays(90));
		System.out.println("TRest"+date);
		
		String splitter[] = date.split("-");
		String month_year = splitter[1];
		String day = splitter[0];
		String year = splitter[2];
		System.out.println(month_year);
		System.out.println(day);
		System.out.println(year);
		
		String deptdate =str.substring(0, 10)+ " "+year;
		System.out.println(" deptdate : "+deptdate);
		
		String str1=futureDateByDays(93).toString();
		String returndate =str1.substring(0, 10)+ " "+year;
		System.out.println(" returndate : "+returndate);
		

		List<WebElement> months = driver.findElements(By.xpath("(//div[@class='DayPicker-Caption']/div)"));

		Thread.sleep(4000);
		List<WebElement> dates = driver
				.findElements(By.xpath("//div[@class='DayPicker-Body']/div[@class='DayPicker-Week']/div"));

		String flag = "False";

		for (int i = 0; i <= months.size(); i++) {
			String month = months.get(1).getText();

			System.out.println("month of the year : " + month);
			
			if (month_year.concat(year).contains(month)) {

				WebElement departureDate = driver.findElement(By.xpath(
						"//div[@class='DayPicker-Body']//div[@class='DayPicker-Week']//div[contains(@class,'DayPicker-Day') and @aria-label ='"+deptdate+"']"));

				js.executeScript("arguments[0].click();", departureDate);

				WebElement returnDate = driver.findElement(By.xpath(
						"//div[@class='DayPicker-Body']//div[@class='DayPicker-Week']//div[contains(@class,'DayPicker-Day') and @aria-label ='"+returndate+"']"));
				js.executeScript("arguments[0].click();", returnDate);
				flag = "True";
				Thread.sleep(5000);
			} else {
				Thread.sleep(5000);
				WebElement nextBtn = driver.findElement(By.xpath("//span[@aria-label='Next Month']"));

				js.executeScript("arguments[0].click();", nextBtn);
			}

		}
		
		
		WebElement travellers = driver.findElement(By.xpath("//span[@class='lbl_input latoBold appendBottom5']"));
		js.executeScript("arguments[0].click();", travellers);
		
		WebElement adults = driver.findElement(By.xpath("//ul[@class='guestCounter font12 darkText gbCounter']//li[@data-cy='adults-2']"));
		
		js.executeScript("arguments[0].click();", adults);
		
		WebElement childs = driver.findElement(By.xpath("//ul[@class='guestCounter font12 darkText gbCounter']//li[@data-cy='children-1']"));
		js.executeScript("arguments[0].click();", childs);
		
		WebElement infants = driver.findElement(By.xpath("//ul[@class='guestCounter font12 darkText gbCounter']//li[@data-cy='infants-1']"));
		
		js.executeScript("arguments[0].click();", infants);
		
		WebElement applybtn = driver.findElement(By.xpath("//button[@data-cy='travellerApplyBtn']"));
		js.executeScript("arguments[0].click();", applybtn);
		
		WebElement saerchbtn = driver.findElement(By.xpath("//a[text()='Search']"));
		js.executeScript("arguments[0].click();", saerchbtn);
		Thread.sleep(6000);
		
		List<WebElement> disfightNames = driver.findElements(By.xpath("(//div[@class='paneView'])[1]//div[contains(@class,'listingCard')]//span[contains(@class,'boldFont')]"));
		
		for(int k=0;k<disfightNames.size();k++) {
			
			System.out.println(disfightNames.get(k).getText());
			
			if ("IndiGo".contains(disfightNames.get(k).getText()))
			{
				Thread.sleep(5000);
				//div[@class='paneView']//div[@class='makeFlex spaceBetween appendBottom15']//span[@class='customRadioBtn']
				WebElement radiobutton = driver.findElement(By.xpath("(//div[@class='paneView'])[1]//div[contains(@class,'listingCard')]//span[contains(text(),'IndiGo')]"));
				js.executeScript("arguments[0].click();", radiobutton);
			
			}
			
			break;
		}
	}

	public static Date futureDateByDays(int days) {
		Date currentDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMMM-yyyy");

		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);

		c.add(Calendar.DATE, days);
		System.out.println("time and date : "+ c.getTime());
		
		return c.getTime();

	}

}
