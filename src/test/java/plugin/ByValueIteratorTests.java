package plugin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.TreeMap;
  public class ByValueIteratorTests {
  static TreeMap<String, Integer> testerTreeMap;
  static IntAdapter testerIntAdapter;
  
  @BeforeAll
  static void setup(){
    testerTreeMap = new TreeMap<String,Integer>();
    testerTreeMap.put("Test_Metric_1",1);
    testerTreeMap.put("Test_Metric_2",2);
    testerTreeMap.put("Test_Metric_3",3);

    testerIntAdapter = new IntAdapter(testerTreeMap);
  }

  @Test
  void TestForTrueHasNext(){
    ByValueIterator testerValueIterator = new ByValueIterator(testerIntAdapter);
    assertTrue(testerValueIterator.hasNext());
  }

  @Test
  void TestForFalseHasNext(){
    ByValueIterator testerValueIterator = new ByValueIterator(testerIntAdapter);
    testerValueIterator.next();
    testerValueIterator.next();
    testerValueIterator.next();
    assertFalse(testerValueIterator.hasNext());
  }

  @Test
  void TestForNextIs2(){
    ByValueIterator testerValueIterator = new ByValueIterator(testerIntAdapter);
    testerValueIterator.next();

    assertTrue((int)testerValueIterator.next() == 2);
  }
}
