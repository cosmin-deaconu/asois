package com.asois.airplane;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Bucharest {
	
	static int k =1;
	public static void main(String[] args) throws InterruptedException, SQLException {
		String nameOfAeroport="otp";
		String cityName="Bucharest";
		String path = "D:\\Facultate\\MasterAn1\\sem1\\ASOIS\\Proiect";
		String pageUrl = "https://www.flightradar24.com/data";
		
    	WebDriver driver;
    	driver = new FirefoxDriver();
    	WebDriverWait wait = new WebDriverWait(driver, 20);
    	
    	
    	String databaseUrl = "jdbc:mysql://localhost/airplanes";
        String user = "root";
        String parola = "";
        Connection con = DriverManager.getConnection(databaseUrl, user, parola);
        java.sql.Statement st = con.createStatement();
    	
    	
    	driver.navigate().to(pageUrl);
    	Thread.sleep(3000);
    	System.out.println("arivals");
    	driver.navigate().to("https://www.flightradar24.com/data/airports/"+nameOfAeroport);
    	wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='cnt-data-content']/div/div[2]/div/aside[2]/div/p[1]/a[1]")));
    	
    	driver.navigate().to("https://www.flightradar24.com/data/airports/"+nameOfAeroport+"/arrivals");
    	wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='cnt-data-content']/div/div[2]/div/aside/div[1]/table/tfoot/tr[1]/td/button")));
    	if(isElementPresent(driver, By.xpath(".//*[@id='cnt-data-content']/div/div[2]/div/aside/div[1]/table/tfoot/tr[1]/td/button"))){
    		driver.findElement(By.xpath(".//*[@id='cnt-data-content']/div/div[2]/div/aside/div[1]/table/tfoot/tr[1]/td/button")).click();
    		Thread.sleep(3000);
    		driver.findElement(By.xpath(".//*[@id='cnt-data-content']/div/div[2]/div/aside/div[1]/table/thead/tr[2]/td/button")).click();
    		Thread.sleep(3000);
    		try{
    			driver.findElement(By.xpath(".//*[@id='cnt-data-content']/div/div[2]/div/aside/div[1]/table/tfoot/tr[1]/td/button")).click();
    			Thread.sleep(4000);
    			driver.findElement(By.xpath(".//*[@id='cnt-data-content']/div/div[2]/div/aside/div[1]/table/thead/tr[2]/td/button")).click();
    			Thread.sleep(4000);
    			driver.findElement(By.xpath(".//*[@id='cnt-data-content']/div/div[2]/div/aside/div[1]/table/thead/tr[2]/td/button")).click();
    			Thread.sleep(4000);
    			driver.findElement(By.xpath(".//*[@id='cnt-data-content']/div/div[2]/div/aside/div[1]/table/tfoot/tr[1]/td/button")).click();
    			Thread.sleep(4000);
    			driver.findElement(By.xpath(".//*[@id='cnt-data-content']/div/div[2]/div/aside/div[1]/table/thead/tr[2]/td/button")).click();
    			Thread.sleep(4000);
    			driver.findElement(By.xpath(".//*[@id='cnt-data-content']/div/div[2]/div/aside/div[1]/table/tfoot/tr[1]/td/button")).click();
    			Thread.sleep(4000);
    			driver.findElement(By.xpath(".//*[@id='cnt-data-content']/div/div[2]/div/aside/div[1]/table/thead/tr[2]/td/button")).click();
    			Thread.sleep(4000);
    			driver.findElement(By.xpath(".//*[@id='cnt-data-content']/div/div[2]/div/aside/div[1]/table/tfoot/tr[1]/td/button")).click();
    			Thread.sleep(4000);
    		}catch(Exception e){}
    		
    	}
    		
    		
    		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
    		DateFormat df1 = new SimpleDateFormat("yyyy/MMMMdd");
    		DateFormat df2 = new SimpleDateFormat("dd-MMMM-yyyy");
        	Date date = new Date();
//        	LocalDateTime dateBeforeXDays = LocalDateTime.now().minusDays(1);
//        	Date date = Date.from(dateBeforeXDays.toInstant(ZoneOffset.UTC));
        	String dateDB = df2.format(date);
        	String day = df.format(date).split("/")[2];
        	String MonthDay = df1.format(date).split("/")[1];
    		String portalDate = driver.findElement(By.xpath("//*[contains(@class,'row-date-separator hidden-xs hidden-sm')]/td[contains(text(),'"+day+"')]")).getText();
    		System.out.println(portalDate);
    		
    		try{
    	
    	    //Get all the rows in the table
    	    List<WebElement> allRows = driver.findElements(By.xpath(".//*/tr[contains(@class,'hidden-xs hidden-sm ng-scope')][contains(@data-date,'"+day+"')]"));
    	    //Get the size(row no) of allRows
    	    int rowSize = allRows.size();
    	    System.out.println("row size::"+rowSize);
    	    
    	    FileOutputStream fos = new FileOutputStream(path+"\\"+MonthDay+"_"+cityName+".xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("My Worksheet");
    	    

    	    String time;
    	    String flight;
    	    String from;
    	    String airplane;
    	    String aircraft;
    	    String departTime;
    	    String status;
            
            for (k=1; k<=rowSize; k++){
    	    	time = driver.findElement(By.xpath(".//*/tr[contains(@class,'hidden-xs hidden-sm ng-scope')][contains(@data-date,'"+day+"')]["+k+"]/td[1]")).getText();
    	        flight = driver.findElement(By.xpath(".//*/tr[contains(@class,'hidden-xs hidden-sm ng-scope')][contains(@data-date,'"+day+"')]["+k+"]/td[2]")).getText();
    	        from = driver.findElement(By.xpath(".//*/tr[contains(@class,'hidden-xs hidden-sm ng-scope')][contains(@data-date,'"+day+"')]["+k+"]/td[3]")).getText();
    	        airplane = driver.findElement(By.xpath(".//*/tr[contains(@class,'hidden-xs hidden-sm ng-scope')][contains(@data-date,'"+day+"')]["+k+"]/td[4]")).getText();
    	        aircraft = driver.findElement(By.xpath(".//*/tr[contains(@class,'hidden-xs hidden-sm ng-scope')][contains(@data-date,'"+day+"')]["+k+"]/td[5]")).getText();
    	        departTime = driver.findElement(By.xpath(".//*/tr[contains(@class,'hidden-xs hidden-sm ng-scope')][contains(@data-date,'"+day+"')]["+k+"]/td[7]")).getText();
    	        status="Arrivals";
    	        st.execute("INSERT INTO bucharest VALUES (NULL, '"+dateDB+"','"+time+"','"+flight+"','"+airplane+"','"+aircraft+"','"+status+"')");
    	        String[] list = {time, flight, from, airplane, aircraft,  departTime, status};
    	        HSSFRow excelRow = worksheet.createRow((short)k);
    	        for (int j=0; j<7; j++){
    	        	excelRow.createCell(j).setCellValue(list[j]);
    	        }
    	    }
            
            Thread.sleep(10000);
            System.out.println("departures");
            driver.navigate().to("https://www.flightradar24.com/data/airports/"+nameOfAeroport+"/departures");
        	wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='cnt-data-content']/div/div[2]/div/aside/div[1]/table/tfoot/tr[1]/td/button")));
        	if(isElementPresent(driver, By.xpath(".//*[@id='cnt-data-content']/div/div[2]/div/aside/div[1]/table/tfoot/tr[1]/td/button"))){
        		driver.findElement(By.xpath(".//*[@id='cnt-data-content']/div/div[2]/div/aside/div[1]/table/tfoot/tr[1]/td/button")).click();
        		Thread.sleep(3000);
        		driver.findElement(By.xpath(".//*[@id='cnt-data-content']/div/div[2]/div/aside/div[1]/table/thead/tr[2]/td/button")).click();
        		Thread.sleep(3000);
        		try{
        			driver.findElement(By.xpath(".//*[@id='cnt-data-content']/div/div[2]/div/aside/div[1]/table/tfoot/tr[1]/td/button")).click();
        			Thread.sleep(4000);
        			driver.findElement(By.xpath(".//*[@id='cnt-data-content']/div/div[2]/div/aside/div[1]/table/thead/tr[2]/td/button")).click();
        			Thread.sleep(4000);
        			driver.findElement(By.xpath(".//*[@id='cnt-data-content']/div/div[2]/div/aside/div[1]/table/thead/tr[2]/td/button")).click();
        			Thread.sleep(4000);
        			driver.findElement(By.xpath(".//*[@id='cnt-data-content']/div/div[2]/div/aside/div[1]/table/tfoot/tr[1]/td/button")).click();
        			Thread.sleep(4000);
        			driver.findElement(By.xpath(".//*[@id='cnt-data-content']/div/div[2]/div/aside/div[1]/table/thead/tr[2]/td/button")).click();
        			Thread.sleep(4000);
        			driver.findElement(By.xpath(".//*[@id='cnt-data-content']/div/div[2]/div/aside/div[1]/table/tfoot/tr[1]/td/button")).click();
        			Thread.sleep(4000);
        			driver.findElement(By.xpath(".//*[@id='cnt-data-content']/div/div[2]/div/aside/div[1]/table/thead/tr[2]/td/button")).click();
        			Thread.sleep(4000);
        		}catch(Exception e){}
        		
        	}
        	
        	 //Get all the rows in the table
    	    List<WebElement> allRows1 = driver.findElements(By.xpath(".//*/tr[contains(@class,'hidden-xs hidden-sm ng-scope')][contains(@data-date,'"+day+"')]"));
    	    //Get the size(row no) of allRows
    	    int rowSize1 = allRows1.size();
    	    System.out.println("row size::"+rowSize1);
        	
    	    for (int i=1; i<=rowSize1; i++){
    	    	time = driver.findElement(By.xpath(".//*/tr[contains(@class,'hidden-xs hidden-sm ng-scope')][contains(@data-date,'"+day+"')]["+i+"]/td[1]")).getText();
    	        flight = driver.findElement(By.xpath(".//*/tr[contains(@class,'hidden-xs hidden-sm ng-scope')][contains(@data-date,'"+day+"')]["+i+"]/td[2]")).getText();
    	        from = driver.findElement(By.xpath(".//*/tr[contains(@class,'hidden-xs hidden-sm ng-scope')][contains(@data-date,'"+day+"')]["+i+"]/td[3]")).getText();
    	        airplane = driver.findElement(By.xpath(".//*/tr[contains(@class,'hidden-xs hidden-sm ng-scope')][contains(@data-date,'"+day+"')]["+i+"]/td[4]")).getText();
    	        aircraft = driver.findElement(By.xpath(".//*/tr[contains(@class,'hidden-xs hidden-sm ng-scope')][contains(@data-date,'"+day+"')]["+i+"]/td[5]")).getText();
    	        departTime = driver.findElement(By.xpath(".//*/tr[contains(@class,'hidden-xs hidden-sm ng-scope')][contains(@data-date,'"+day+"')]["+i+"]/td[7]")).getText();
    	        status="Departures";
    	        st.execute("INSERT INTO bucharest VALUES (NULL, '"+dateDB+"','"+time+"','"+flight+"','"+airplane+"','"+aircraft+"','"+status+"')");
    	        String[] list = {time, flight, from, airplane, aircraft,  departTime, status};
    	        HSSFRow excelRow = worksheet.createRow((short)i+k);
    	        for (int j=0; j<7; j++){
    	        	excelRow.createCell(j).setCellValue(list[j]);
    	        }
    	    }
    	    
    	    Thread.sleep(5000);
    	    String value = null;
            String getDate = null;
    	    String select = "SELECT @date:=date,@count := COUNT(*) from bucharest WHERE date like '"+dateDB+"';";
            st = con.createStatement();
            ResultSet rs = st.executeQuery(select);

            while (rs.next()){
                value = rs.getString(2);
            	getDate = rs.getString(1);
            }
            System.out.println("DB date:"+getDate);
            System.out.println("total count: "+value);
            
            String insert="INSERT INTO bucharestnumber VALUES(NULL,'"+getDate+"','"+value+"');";
            st.execute(insert);
        	Thread.sleep(2000);
        	
    	    st.close();
    	    workbook.write(fos);
            fos.close();
            workbook.close();
            driver.close();
    	    }catch(Exception e){
    	    }
	        System.out.println("FInish!!!!");
    	}

	public static boolean isElementPresent(WebDriver driver, By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
}
