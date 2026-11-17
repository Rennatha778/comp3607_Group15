package plugin;
import org.junit.jupiter.api.Test;

import plugin.Iterators.java.ByKeyIterator;
import plugin.Adapters.java.LongAdapter;

import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.TreeMap;
public class ByKeyIteratorTest {
  static TreeMap<String, Long> testerTreeMap;
  static LongAdapter testerLongAdapter;
  
  @BeforeAll
  static void setup(){
    testerTreeMap = new TreeMap<String,Long>();
    testerTreeMap.put("Test_Metric_1",(long)1);
    testerTreeMap.put("Test_Metric_2",(long)2);
    testerTreeMap.put("Test_Metric_3",(long)3);

    testerLongAdapter = new LongAdapter(testerTreeMap);
  }

  @Test
  void TestForTrueHasNext(){
    ByKeyIterator testerKeyIterator = new ByKeyIterator(testerLongAdapter);
    assertTrue(testerKeyIterator.hasNext());
  }

  @Test
  void TestForNextIs(){
    ByKeyIterator testerKeyIterator = new ByKeyIterator(testerLongAdapter);
    testerKeyIterator.next();

    assertTrue(testerKeyIterator.next().equals("Test_Metric_2"));
  }

}
