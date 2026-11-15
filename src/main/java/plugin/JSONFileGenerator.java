package plugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.TreeMap;


public class JSONFileGenerator implements ReportPublisher {
  TreeMap<String,Data> listenerMetricsTreeMap;
  TreeMap<String,Integer> studentInfo;
  //TreeMap<String,Object> jsonData;
  TreeMap<String,Data> jsonData;

  public File generateReport(TreeMap<String,Data> listenerMetricsTreeMaps, String filePath){ 
    try {
      /* File directory =  new File(filePath);
      directory.mkdirs(); */

      File file = new File(filePath+"\\PluginMetrics.json");

      ObjectMapper mapper = new ObjectMapper();
      mapper.writerWithDefaultPrettyPrinter().writeValue(file, compileTreeMaps(listenerMetricsTreeMaps));
      //TreeMap<String,Data> metrics = mapper.readTree(new File ("PluginMetrics.json"),listenerMetricsTreeMap.class)
      System.out.println("JSON file created.");
      
      return file;
    }catch(Exception e) {
      e.printStackTrace();
      System.out.println("Failed to create JSON file.");
    }

    return null;
  }

  protected TreeMap<String,Data> compileTreeMaps(TreeMap<String,Data> listenerMetricsTreeMaps){
    this.listenerMetricsTreeMap = listenerMetricsTreeMaps;
    Data studentData = new Data();
    studentData.addIntData("Ethan Mohan",8160);
    studentData.addIntData("Mikayla Roberts",8160);
    studentData.addIntData("Rennatha Thomas",816044740);
    
    //studentInfo = new TreeMap<String,Integer>();
    /* studentInfo.put("Ethan Mohan",8160);
    studentInfo.put("Mikayla Roberts",8160);
    studentInfo.put("Rennatha Thomas",816044740); */
    
    //jsonData = new TreeMap<String,Object>();
    /* jsonData = new TreeMap<String,Data>();
    jsonData.put("Plugin Metrics",listenerMetricsTreeMap);
    jsonData.put("Student Info",studentInfo); */
    this.listenerMetricsTreeMap.put("Student Information",studentData);
    //return jsonData;
    return this.listenerMetricsTreeMap;
  }
    
}
