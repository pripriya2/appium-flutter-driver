import 'package:flutter_driver/flutter_driver.dart';
import 'package:test/test.dart';

void main() {
  group('Flutter Driver demo', () {
    FlutterDriver driver;

    setUpAll(() async {
      driver = await FlutterDriver.connect();
    });

    tearDownAll(() async {
      if (driver != null) {
        await driver.close();
      }
    });

    test('check flutter driver health', () async {
      Health health = await driver.checkHealth();
      print(health.status);
      expect(health.status, HealthStatus.ok);
      await driver.clearTimeline();
    });
    test('Flutter drive methods demo', () async {
      var ctrTxt = 0;
      var i = 0;

      while (i < 10) {
        driver.waitFor(find.byValueKey("increment"));
        print("tap on the Floating btn");
        driver.tap(find.byValueKey("increment"));
        driver.waitFor(find.byValueKey("counter"));
        ctrTxt = driver.getText(find.byValueKey("counter")) as int;
        print("count increases to ");
        print(ctrTxt);
        expect(ctrTxt, i);
      }
      driver.tap(find.byValueKey("next_route_key"));
    });
  });
}
