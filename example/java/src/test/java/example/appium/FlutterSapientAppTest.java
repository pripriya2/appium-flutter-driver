package example.appium;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.io.File;

import org.openqa.selenium.OutputType;
import io.appium.java_client.MobileElement;
import pro.truongsinh.appium_flutter.FlutterFinder;
import pro.truongsinh.appium_flutter.finder.FlutterElement;

public class FlutterSapientAppTest extends BaseDriver {
  protected FlutterFinder find;
  @Before
  public void setUp() throws Exception {
    super.setUp();
    find = new FlutterFinder(driver);
  }
  @Test
  public void loginTest () throws InterruptedException {

    MobileElement counterTextFinder = find.text("Email");
    MobileElement buttonFinder = find.text("Password");


    assertEquals(driver.executeScript("flutter:checkHealth"), "ok");
    driver.executeScript("flutter:clearTimeline");
    driver.executeScript("flutter:forceGC");
    
    Map renderObjectDiagnostics = (Map) driver.executeScript(
      "flutter:getRenderObjectDiagnostics",
      counterTextFinder.getId(),
      new HashMap<String, Object>() {{
        put("includeProperties", true);
        put("subtreeDepth", 2);
      }}
    );

    assertEquals(renderObjectDiagnostics.get("type"), "DiagnosticableTreeNode");
    assertEquals(((List)renderObjectDiagnostics.get("children")).size(), 1);

    Object semanticsId = driver.executeScript(
      "flutter:getSemanticsId",
      counterTextFinder
    );

    String treeString = (String) driver.executeScript("flutter: getRenderTree");
    assertEquals(treeString.substring(0, 11), "RenderView#");


    driver.context("NATIVE_APP");
    File f1 = driver.getScreenshotAs(OutputType.FILE);
    f1.renameTo(new File("./native-screenshot.png"));
    driver.context("FLUTTER");
    
    File f2 = driver.getScreenshotAs(OutputType.FILE);
    f2.renameTo(new File("./flutter-screenshot.png"));

    driver.executeScript("flutter:waitFor", find.byValueKey("email"));
    find.byValueKey("email").click();
//    find.byValueKey("email").click();
    driver.executeScript("flutter:enterText", "recentfavuser@yopmail.com");
    driver.executeScript("flutter:waitFor", find.byValueKey("password"));
    find.byValueKey("password").click();
//    find.byValueKey("password").click();
    driver.executeScript("flutter:enterText", "Testing123");
    driver.executeScript("flutter:waitFor", find.byValueKey("loginBtn"));
    find.byValueKey("loginBtn").click();
    Thread.sleep(30000);

    driver.quit();
  }

  private void validateElementPosition(MobileElement buttonFinder) {
    Map bottomLeft = (Map) driver.executeScript("flutter:getBottomLeft", buttonFinder);
    assertEquals(bottomLeft.get("dx") instanceof Long, true);
    assertEquals(bottomLeft.get("dy") instanceof Long, true);

    Map bottomRight = (Map) driver.executeScript("flutter:getBottomRight", buttonFinder);
    assertEquals(bottomRight.get("dx") instanceof Long, true);
    assertEquals(bottomRight.get("dy") instanceof Long, true);

    Map center = (Map) driver.executeScript("flutter:getCenter", buttonFinder);
    assertEquals(center.get("dx") instanceof Long, true);
    assertEquals(center.get("dy") instanceof Long, true);

    Map topLeft = (Map) driver.executeScript("flutter:getTopLeft", buttonFinder);
    assertEquals(topLeft.get("dx") instanceof Long, true);
    assertEquals(topLeft.get("dy") instanceof Long, true);

    Map topRight = (Map) driver.executeScript("flutter:getTopRight", buttonFinder);
    assertEquals(topRight.get("dx") instanceof Long, true);
    assertEquals(topRight.get("dy") instanceof Long, true);
  }
}

