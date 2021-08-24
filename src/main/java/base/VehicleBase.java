package base;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import pageObjects.VehicleBasidcInfoPO;
import pageObjects.VehicleDetailsPO;
import pageObjects.VehicleHomePO;
import pojo.DriverPojo;
import utils.ExcelUtils;
import utils.GeneralUtils;

public class VehicleBase {
	private WebDriver driver;

	public VehicleBase(WebDriver driver) {
		this.driver = driver;
	}

	public void openVehiclePage() throws Exception {
		ExcelUtils.loadTestingData("credentials.xlsx", "URL");
		final String url = ExcelUtils.getPropertyFromTestDataFile("url.vehicle.qa");
		driver.get(url);
	}

	public void clickAddNewVehicle() {
		VehicleHomePO obj = new VehicleHomePO(driver);
		obj.getAddNewVehicleBtn().click();
	}

	public void populateVehicleInfo() throws IOException {
		VehicleBasidcInfoPO obj = new VehicleBasidcInfoPO(driver);
		final String plate = GeneralUtils.generateRandomPlate();
		final String cab = ExcelUtils.getPropertyFromTestDataFile("vehicle.cab");
		final String make = ExcelUtils.getPropertyFromTestDataFile("vehicle.make");
		final String model = ExcelUtils.getPropertyFromTestDataFile("vehicle.model");
		
		obj.getLicenseTxtBox().clear();
		obj.getLicenseTxtBox().sendKeys(plate);
		obj.getMakeDropdown().sendKeys(make);
		obj.getModelDropdown().sendKeys(model);
		obj.getCabNumberTxtBox().sendKeys(cab);
		obj.getSaveCloseBtn().click();
		
		System.out.println("Using licence plate: "+plate);
		DriverPojo.setVehiclePlate(plate);
	}

	public void validateVehicule() throws IOException {
		VehicleHomePO obj = new VehicleHomePO(driver);
		VehicleDetailsPO obj2 = new VehicleDetailsPO(driver);
		Assert.assertNotNull(obj.getVehiclesTitle());
		obj.getSearchTxtBox().sendKeys(DriverPojo.getVehiclePlate());
		Assert.assertNotNull(obj2.getPlateLabel());
	}

}
