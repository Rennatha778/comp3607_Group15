package plugin;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import java.util.TreeMap;

public class DataTests {
  static Data testerData;
  static TreeMap<String,Integer> testerTreeMap1;
  static TreeMap<String,Long> testerTreeMap2;
  static TreeMap<String,Long> testerTreeMap3;
  static TreeMap<String,TreeMap<String,Long>> testerNestedTreeMap;
  
  @BeforeAll
  static void setup(){
    testerData = new Data();
    testerData.addIntData("Test_Metric_1",1);
    testerData.addIntData("Test_Metric_2",2);
    testerData.addIntData("Test_Metric_3",3);

    testerTreeMap1 = new TreeMap<String,Integer>();
    testerTreeMap1.put("Test_Metric_1",1);
    testerTreeMap1.put("Test_Metric_2",2);
    testerTreeMap1.put("Test_Metric_3",3);

    testerTreeMap2 = new TreeMap<String,Long>();
    testerTreeMap2.put("Test_Metric_4",(long)4);
    testerTreeMap2.put("Test_Metric_5",(long)5);
    testerTreeMap2.put("Test_Metric_6",(long)6);

    testerTreeMap3 = new TreeMap<String,Long>();
    testerTreeMap3.put("Test_Metric_7",(long)7);
    testerTreeMap3.put("Test_Metric_8",(long)8);
    testerTreeMap3.put("Test_Metric_9",(long)9);

    testerNestedTreeMap = new TreeMap<String,TreeMap<String,Long>>();
    testerNestedTreeMap.put("InnerTreeMap2",testerTreeMap2);
    testerNestedTreeMap.put("InnerTreeMap3",testerTreeMap3);


    testerData.addObjectCreationData("InnerTreeMap2",testerTreeMap2);
    testerData.addObjectCreationData("InnerTreeMap3",testerTreeMap3);
    
  }

  @Test
  void TestAddAndGetFuntions(){
    assertEquals(testerTreeMap1,testerData.getIntTreeMap());
  }

  @Test
  void TestForAddAndGetFuntionsForNestedTreeMap(){
    assertEquals(testerNestedTreeMap,testerData.getObjectCreationMap());
  }
}
