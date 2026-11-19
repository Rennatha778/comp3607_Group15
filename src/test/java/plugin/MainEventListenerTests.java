package plugin;
import org.junit.jupiter.api.Test;

import plugin.EventListeners.java.MainEventDataCollection;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.TreeMap;

import org.junit.jupiter.api.BeforeAll;

public class MainEventListenerTests {
  static MainEventDataCollection testerMainCatcher;
  static TreeMap<String,Data> testerMainEventTreeMap;
  
  @BeforeAll
  static void setup(){
    testerMainCatcher = new MainEventDataCollection();
  }

  @Test
  void TestForTotalSessionTime(){
    long startTime = (long) 4;
    long endTime = (long) 10;
    assertEquals(endTime-startTime, testerMainCatcher.calculateTotalSessionTime(startTime,endTime));
  }

  @Test
  void TestForTotalSessionTimeInvalidNumbers(){
    long startTime = (long) 0;
    long endTime = (long) 10;
    assertEquals(0, testerMainCatcher.calculateTotalSessionTime(startTime,endTime));
  }

  

  @Test
  void TestForDataMetrics(){
    /* testerMainCatcher.
    testerMainEventTreeMap = new TreeMap<String,Data>();
    testerMainEventTreeMap.put("Tester_Main_Event_Listener",testerMainCatcher.getMetricData()); */
  }



 
}
