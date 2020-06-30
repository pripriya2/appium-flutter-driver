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
      String ctrTxt = "";
      var i = 0;

      while (i < 10) {
        await driver.waitFor(find.byValueKey("increment"));
        print("tap on the Floating btn");
        await driver.tap(find.byValueKey("increment"));
        print("tapped on the Floating btn");
        await driver.waitFor(find.byValueKey("counter"));
        ctrTxt = await driver.getText(find.byValueKey("counter"));
        print("count increases to $ctrTxt");
        i++;
        expect(ctrTxt, i.toString());
      }
      await driver.tap(find.byValueKey("next_route_key"));
      await driver.waitForAbsent(find.byTooltip('counter_tooltip'));
      expect(await driver.getText(find.text('This is 2nd route')),
          'This is 2nd route');
    });
  });
}
