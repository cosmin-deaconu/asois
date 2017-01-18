package com.asois.airplane;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AirplaneNumber{
	public static int count = 0;
	
	public static void main(String[] args) {
		try {
			WebDriver driver;
			String pageUrl = "https://planefinder.net/#";
	    	String databaseUrl = "jdbc:mysql://localhost/airplanes";
	        String user = "root";
	        String parola = "";
	        Connection con = DriverManager.getConnection(databaseUrl, user, parola);
	        java.sql.Statement st = con.createStatement();
	        
	        DateFormat df2 = new SimpleDateFormat("dd-MMMM-yyyy");
	    	java.util.Date date = new java.util.Date();
	    	String dateDB = df2.format(date);
	    	
	    	driver = new FirefoxDriver();
	    	WebDriverWait wait = new WebDriverWait(driver, 30);
	    	
	    	driver.navigate().to(pageUrl);
	    	wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='map_search_box']")));
	    	driver.findElement(By.xpath(".//*[@id='map_search_box']")).sendKeys("Bucharest");
			Thread.sleep(3000);
	    	driver.findElement(By.xpath(".//*[@id='result-5-0']/span[1]")).click();
	    	
	    	WebElement html = driver.findElement(By.tagName("html"));
	//    	html.sendKeys(Keys.chord(Keys.CONTROL, Keys.ADD));
	    	Thread.sleep(5000);
	    	while(true){
		    	String airplanes = driver.findElement(By.xpath(".//*[@id='visible_planes_counter']/div/span[2]")).getText().split(" ")[0];
		    	driver.navigate().refresh();
		    	Thread.sleep(3000);
		    	DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		    	java.util.Date date1 = new java.util.Date();
		    	System.out.println("Numar de avioane care traverseaza orasul in acest moment: "+airplanes+" "+df.format(date1));
		    	if((LocalDateTime.now().getHour() == 23) && LocalDateTime.now().getMinute() == 59){
		    		st.execute("INSERT INTO bucharestnumber VALUES (NULL, '"+dateDB+"','"+count+"')");
		    		count=0;
		    		airplanes = "0";
		    	}
		    	Thread.sleep(90000);	
		    	count = count+Integer.parseInt(airplanes);
		    	System.out.println("total: "+count);
	    	}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}
