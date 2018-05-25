package com.qa.vehicle_testing.vehicle_information;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class StartingPage {
  
  public StartingPage(WebDriver driver) {
		
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 15), this);
		
	}
  
  @FindBy(xpath = "//*[@id=\"content\"]/form/div/div/div[2]/fieldset/button")
	private WebElement continueBtn;
  
  @FindBy(xpath = "//*[@id=\"Vrm\"]")
	private WebElement searchBox;

  public void searchForVehicle(String text) {
	  
	  searchBox.sendKeys(text);
	  continueBtn.click();
  }
}
