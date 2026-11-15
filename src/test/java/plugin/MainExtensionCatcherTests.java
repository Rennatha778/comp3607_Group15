package plugin;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;

public class MainExtensionCatcherTests {
  static MainExtensionCatcherTests testerMainCatcher;
  
  @BeforeAll
  static void setup(){
    testerMainCatcher = new MainExtensionCatcherTests();
  }

  @Test
  void TestForTotalSessionTime(){
    long startTime = (long) 4;
    long endTime = (long) 10;
    assertEquals(endTime-startTime, testerMainCatcher.getTotalSessionTime(endTime,startTime));
  }
}
