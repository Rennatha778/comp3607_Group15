package plugin;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;
import org.w3c.dom.events.Event;

import plugin.FileGenerators.java.TreeMapTraverser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.TreeMap;
public class TreeMapTraverserTests {
static TreeMap<String,Data> testerTreeMap;
static TreeMap<String,Long> testerInnerTreeMap;
static Data testerData1;
static Data testerData2;
  @BeforeAll
  static void setupTreeMap(){
    testerData1 = new Data();
    testerData2 = new Data();
    testerTreeMap = new TreeMap<String,Data>();
    testerInnerTreeMap = new TreeMap<String,Long>();

    testerData1.addIntData("Test_Int_Metric_1",1);
    testerData1.addIntData("Test_Int_Metric_2",2);
    testerData1.addIntData("Test_Int_Metric_3",3);
    
    testerData1.addLongData("Test_Long_Metric_1",(long)1);
    testerData1.addLongData("Test_Long_Metric_2",(long)2);
    testerData1.addLongData("Test_Long_Metric_3",(long)3);

    testerData2.addIntData("Test_Int_Metric_1",1);
    testerData2.addIntData("Test_Int_Metric_2",2);
    testerData2.addIntData("Test_Int_Metric_3",3);
    
    testerData2.addLongData("Test_Long_Metric_1",(long)1);
    testerData2.addLongData("Test_Long_Metric_2",(long)2);
    testerData2.addLongData("Test_Long_Metric_3",(long)3);

    testerInnerTreeMap.put("Test_Inner_TreeMap_Metric_1",(long)1);
    testerInnerTreeMap.put("Test_Inner_TreeMap_Metric_2",(long)2);
    testerInnerTreeMap.put("Test_Inner_TreeMap_Metric_3",(long)3);
    testerInnerTreeMap.put("Test_Inner_TreeMap_Metric_4",(long)4);

    testerData2.addObjectCreationData("Tester_Inner_TreeMap",testerInnerTreeMap);
    
    testerTreeMap.put("TesterData1",testerData1);
    testerTreeMap.put("TesterData2",testerData2);
  }

  @Test
  void TraversalTestForNull(){
    
    TreeMapTraverser testerTreeMapTraverser = new TreeMapTraverser();
    assertNull(testerTreeMapTraverser.getMetricValue(testerTreeMap,"Fake_Metric"));
  }


  @Test
  void TraversalTestForTest_Long_Metric_2(){
    
    TreeMapTraverser testerTreeMapTraverser = new TreeMapTraverser();
    assertEquals((long)2,(long)testerTreeMapTraverser.getMetricValue(testerTreeMap,"Test_Long_Metric_2"));
  }

  @Test
  void TraversalTestForTest_Int_Metric_3(){
    
    TreeMapTraverser testerTreeMapTraverser = new TreeMapTraverser();
    assertEquals((int)3,(int)testerTreeMapTraverser.getMetricValue(testerTreeMap,"Test_Int_Metric_3"));
  }

  @Test
  void TraversalTestForTest_Inner_TreeMap_Metric_4(){
    
    TreeMapTraverser testerTreeMapTraverser = new TreeMapTraverser();
    assertEquals((long)4,(long)testerTreeMapTraverser.getMetricValue(testerTreeMap,"Test_Inner_TreeMap_Metric_4"));
  }
  
  @Test
  void TraversalTestForTreeMap(){
    TreeMap<String,Long> testCompareTreeMap = testerInnerTreeMap;
    TreeMapTraverser testerTreeMapTraverser = new TreeMapTraverser();
    assertEquals((TreeMap<String,Long>)testCompareTreeMap,(TreeMap<String,Long>)testerTreeMapTraverser.getMetricValue(testerTreeMap,"Tester_Inner_TreeMap"));
  }

  @Test
  void TraversalTestForDataClass(){
    Data testCompareData = testerData1;
    TreeMapTraverser testerTreeMapTraverser = new TreeMapTraverser();
    assertEquals((Data)testCompareData,(Data)testerTreeMapTraverser.getMetricValue(testerTreeMap,"TesterData1"));
  }

  @Test
  void FalseAssertTestForDataClasses(){
    Data testCompareData = testerData1;
    TreeMapTraverser testerTreeMapTraverser = new TreeMapTraverser();
    assertFalse((Data)testCompareData == (Data)testerTreeMapTraverser.getMetricValue(testerTreeMap,"TesterData2"));
  }
  
}
