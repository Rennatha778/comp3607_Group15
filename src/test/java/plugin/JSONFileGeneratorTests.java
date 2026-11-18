package plugin;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import plugin.FileGenerators.java.JSONFileGenerator;
import plugin.Iterators.java.TreeMapTraverser;

import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Map;
import java.util.TreeMap;
import java.io.File;
import java.io.IOException;

public class JSONFileGeneratorTests {
  static TreeMap<String,Data> testerDataTreeMap;
  static TreeMap<String,Long> testerInnerTreeMap1;
  static TreeMap<String,Long> testerInnerTreeMap2;
  static TreeMap<String,Data> testercombinedDataTreeMap;
  static Data testerData1;
  static Data testerData2;
  static JSONFileGenerator testerJsonFileGenerator;
  static TreeMapTraverser testerTreeMapTraverser;
  static File jsonFile;
  static TreeMap<String,Data> newMapWithJSONData;

  @BeforeAll
  static void setup(){
    testerDataTreeMap = new TreeMap<String,Data>();
    testercombinedDataTreeMap = new TreeMap<String,Data>();

    testerData1 = new Data();
    testerData1.addIntData("Test_Int_Metric_1",1);
    testerData1.addIntData("Test_Int_Metric_2",2);
    testerData1.addIntData("Test_Int_Metric_3",3);

    testerData1.addLongData("Test_Long_Metric_1",(long)1);
    testerData1.addLongData("Test_Long_Metric_2",(long)2);
    testerData1.addLongData("Test_Long_Metric_3",(long)3);

    testerInnerTreeMap1 = new TreeMap<String,Long>();
    testerInnerTreeMap1.put("Test_Inner_Long_Metric_1",(long)1);
    testerInnerTreeMap1.put("Test_Inner_Long_Metric_2",(long)2);
    testerInnerTreeMap1.put("Test_Inner_Long_Metric_3",(long)3);
    
    testerData1.addObjectCreationData("Tester_Inner_TreeMap1",testerInnerTreeMap1);

    testerData2 = new Data();
    testerData2.addIntData("Test_Int_Metric_4",4);
    testerData2.addIntData("Test_Int_Metric_5",5);
    testerData2.addIntData("Test_Int_Metric_6",6);

    testerData2.addLongData("Test_Long_Metric_4",(long)4);
    testerData2.addLongData("Test_Long_Metric_5",(long)5);
    testerData2.addLongData("Test_Long_Metric_6",(long)6);

    testerInnerTreeMap2 = new TreeMap<String,Long>();
    testerInnerTreeMap2.put("Test_Inner_Long_Metric_1",(long)1);
    testerInnerTreeMap2.put("Test_Inner_Long_Metric_2",(long)2);
    testerInnerTreeMap2.put("Test_Inner_Long_Metric_3",(long)3);
    
    testerData2.addObjectCreationData("Tester_Inner_TreeMap2",testerInnerTreeMap2);

    testerDataTreeMap.put("Tester_Data_1",testerData1);
    testerDataTreeMap.put("Tester_Data_2",testerData2);

    testerJsonFileGenerator = new JSONFileGenerator();
    testerTreeMapTraverser = new TreeMapTraverser();

    testercombinedDataTreeMap = testerJsonFileGenerator.compileTreeMaps(testerDataTreeMap);
  }

  @Test
  void TestThatCombinedTreeMapContainsStudentInfo(){
    assertEquals(816044740,testerTreeMapTraverser.getMetricValue(testercombinedDataTreeMap,"Rennatha Thomas"));
  }

  @Test
  void TestThatCombinedTreeMapContainsTester_Inner_TreeMap2(){
    assertEquals(testerInnerTreeMap2,testerTreeMapTraverser.getMetricValue(testercombinedDataTreeMap,"Tester_Inner_TreeMap2"));
  }

   @Test
  void TestThatCombinedTreeMapContainsTester_Data_1(){
    assertEquals(testerData1,testerTreeMapTraverser.getMetricValue(testercombinedDataTreeMap,"Tester_Data_1"));
  }

  @Test
  void TestThatTreeMapGeneratesJSONFile(){
    String filePath = "C:\\Users\\Owner\\OneDrive\\Documents\\COMP 1601 Programs\\OOP II\\bluejplugin";
    jsonFile = (File)testerJsonFileGenerator.generateReport(testerDataTreeMap,filePath);
    assertNotNull(jsonFile);
  }


  @Test
  void TestThatJSONFileNotCreated(){
    String filePath ="";
    jsonFile = (File)testerJsonFileGenerator.generateReport(testerDataTreeMap,filePath);
    assertNull(jsonFile);
  }

  @Test
  void TestThatJSONFileNotEmpty(){
    String filePath = "C:\\Users\\Owner\\OneDrive\\Documents\\COMP 1601 Programs\\OOP II\\bluejplugin";
    jsonFile = (File)testerJsonFileGenerator.generateReport(testerDataTreeMap,filePath);
     ObjectMapper testerobjectMapper = new ObjectMapper();

    try{
       newMapWithJSONData = testerobjectMapper.readValue(jsonFile,new TypeReference<TreeMap<String,Data>>(){});
    }catch(IOException e){
      e.printStackTrace();
      System.out.println("Unable to read from JSON file");
    }
    assertFalse(newMapWithJSONData.isEmpty());
  }

  @Test
  void TestForCorrectDataInNewJSONFile(){
    String filePath = "C:\\Users\\Owner\\OneDrive\\Documents\\COMP 1601 Programs\\OOP II\\bluejplugin";
    jsonFile = (File)testerJsonFileGenerator.generateReport(testerDataTreeMap,filePath);
    ObjectMapper testerobjectMapper = new ObjectMapper();

    try{
       newMapWithJSONData = testerobjectMapper.readValue(jsonFile,new TypeReference<TreeMap<String,Data>>(){});
       assertEquals(1,testerTreeMapTraverser.getMetricValue(newMapWithJSONData,"Test_Int_Metric_1"));
    }catch(IOException e){
      e.printStackTrace();
      System.out.println("Unable to read from JSON file");
    }
    
  }

}
