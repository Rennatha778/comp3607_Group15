package plugin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.TreeMap;
public class ByValueIterator {
  static TreeMap<String, Integer> testerTreeMap;
  DataIterator intValueIterator;
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
    intValueIterator = new ByValueIterator(testerIntAdapter);
  }

}
