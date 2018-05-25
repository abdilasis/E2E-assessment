package com.qa.vehicle_testing.vehicle_information;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;


import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class VehicleInformationTesting {

	WebDriver driver;
	ExtentReports report;
	ExtentTest test;
	String LoginData = ".\\project-folders\\development\\Vehicle details.xlsx";
	
	
	@Before
	public void setUp() {
		
		System.setProperty("webdriver.chrome.driver", ".\\project-folders\\development\\web-driver\\chromedriver.exe");
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		report = new ExtentReports(".\\project-folders\\development\\reports/report.html");
		
		test = report.startTest("Starting Test");
	}
	
	@Test
	public void test() throws InterruptedException, IOException {

		FileInputStream file = null;
		file = new FileInputStream(LoginData);
		XSSFWorkbook workbook = null;
		workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(0);

		for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {

			Cell vehicleNumber = sheet.getRow(i).getCell(0);
			Cell vehicleMake = sheet.getRow(i).getCell(1);

			String vehicle = vehicleNumber.getStringCellValue();
			String make = vehicleMake.getStringCellValue();

			driver.get("https://vehicleenquiry.service.gov.uk/ViewVehicle");
			test.log(LogStatus.INFO, "navigate to dvlasite");
			
			test.log(LogStatus.INFO, "inputting registration number");
			
			ScreenShot.screenShot(driver);
			
			

			StartingPage page = PageFactory.initElements(driver, StartingPage.class);
			page.searchForVehicle(vehicle);
			test.log(LogStatus.INFO, "Checking vehicle information");
			Thread.sleep(1000);
			
			
			ScreenShot.screenShot(driver);
			
			String result = driver.findElement(By.xpath("//*[@id=\"pr3\"]/div/"
					+ "/li[2]/span[2]/strong")).getText();
			
			String expected = make;
			
			if (!result.equals(expected)) {
				test.log(LogStatus.FAIL, "vehicle not found");
				report.endTest(test);
				report.flush();
				ExcelUtils.setCellData("Fail", i, 3);
			}
			
			
			assertEquals(expected, result);

			ExcelUtils.setCellData("Pass", i, 3);
			test.log(LogStatus.PASS, "Right vehicle found");
			
			
			

		}
		report.endTest(test);
		report.flush();
	}

	
	@After
	public void tearDown() {
		report.endTest(test);
		report.flush();
		driver.quit();

	}
	

}
