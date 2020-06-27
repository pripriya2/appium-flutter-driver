package example.appium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.URL;

public class BaseDriver {
  public AppiumDriver<MobileElement> driver;
  public WebDriverWait wait;
  private static AppiumDriverLocalService service;

  @Before
  public void setUp() throws Exception {
//    service = AppiumDriverLocalService.buildDefaultService();
//    service.start();
//
//    if (service == null || !service.isRunning()) {
//      throw new AppiumServerHasNotBeenStartedLocallyException("An appium server node is not started!");
//    }

    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability("platformName", "Android");
    capabilities.setCapability("deviceName", "Samsung Note 9");
//    capabilities.setCapability("platformVersion", "11.2");
//    capabilities.setCapability("deviceName", "iPhone 8");
//    capabilities.setCapability("udid", "a537d49ba508182ad961b390274e230248810acd");
    
    capabilities.setCapability("noReset", true);
    

    File classpathRoot = new File(System.getProperty("user.dir"));
    File appDir = new File(classpathRoot, "/../apps");
//    File app = new File(appDir.getCanonicalPath(), "ios-sim-debug.zip"); 
//    File app = new File(appDir.getCanonicalPath(), "android-real-debug.apk"); 
    File app = new File(appDir.getCanonicalPath(), "app-sapient-debug.apk");
    
    

    System.out.println(app.getAbsolutePath());
//    capabilities.setCapability("app", app.getAbsolutePath());
    capabilities.setCapability("appPackage", "com.sapient.skeletonapp");
    capabilities.setCapability("appActivity", "com.sapient.skeletonapp.MainActivity");
    capabilities.setCapability("automationName", "Flutter");

//    driver = new IOSDriver<MobileElement>(service.getUrl(), capabilities);
//    driver = new AndroidDriver<MobileElement>(service.getUrl(), capabilities);
    driver = new AndroidDriver<MobileElement>(new URL(
			"http://127.0.0.1:4723/wd/hub"), capabilities);
    wait = new WebDriverWait(driver, 10);
  }

  @After
  public void tearDown() throws Exception {
    if (driver != null) {
      driver.quit();
    }
    if (service != null) {
      service.stop();
    }
  }
}
