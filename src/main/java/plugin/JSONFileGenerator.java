package plugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.TreeMap;

public class JSONFileGenerator implements ReportPublisher {
  TreeMap<String,Data> listenerMetricsTreeMap;
  TreeMap<String,Integer> studentInfo;
  TreeMap<String,Object> jsonData;

  public void generateReport(TreeMap<String,Data> listenerMetricsTreeMaps){ 
    try {
      File directory =  new File("C:\\Users\\Owner\\Documents\\PluginFiles");
      directory.mkdirs();

      File file = new File(directory, "PluginMetrics.json");

      ObjectMapper mapper = new ObjectMapper();
      mapper.writerWithDefaultPrettyPrinter().writeValue(file, compileTreeMaps(listenerMetricsTreeMaps));
        
      System.out.println("JSON file created.");
    }catch(Exception e) {
      e.printStackTrace();
      System.out.println("Failed to create JSON file.");
    }
  }

  private TreeMap<String,Object> compileTreeMaps(TreeMap<String,Data> listenerMetricsTreeMaps){
    this.listenerMetricsTreeMap = listenerMetricsTreeMaps;
    studentInfo = new TreeMap<String,Integer>();
    studentInfo.put("Ethan Mohan",8160);
    studentInfo.put("Mikayla Roberts",8160);
    studentInfo.put("Rennatha Thomas",816044740);
    
    jsonData = new TreeMap<String,Object>();
    jsonData.put("Plugin Metrics",listenerMetricsTreeMap);
    jsonData.put("Student Info",studentInfo);
    return jsonData;
  }
    
}
