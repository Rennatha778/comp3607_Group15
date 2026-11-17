package plugin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

/**
 * This class contains a method that generates a json file
 */
public class JSONFileGenerator implements ReportPublisher {
  TreeMap<String,Data> listenerMetricsTreeMap;
  TreeMap<String,Integer> studentInfo;

  /**
   * TreeMap that will be used to generated json file.
   * It will contain student information and metrics from all event listeners
   */
  TreeMap<String,Data> jsonData;

  /**
   * This method generates a json file based on the TreeMap passed to the method.
   * @param listenerMetricsTreeMaps Metric data gathered in the main runner from all listeners
   */
  public File generateReport(TreeMap<String,Data> listenerMetricsTreeMaps, String filePath){ 
    try {
      File file = new File(filePath+"\\PluginMetrics.json");

      ObjectMapper mapper = new ObjectMapper();
      mapper.writerWithDefaultPrettyPrinter().writeValue(file, compileTreeMaps(listenerMetricsTreeMaps));
      System.out.println("JSON file created.");
      
      return file;
    }catch(JsonProcessingException e) {
      e.printStackTrace();
      System.out.println("Failed to create JSON file - Processing Errors");
    }catch(IOException e){
      e.printStackTrace();
      System.out.println("Failed to create JSON file - File Writing Errors");
    }
    return null;
  }

  /**
   * This method places student information at the beginning of the TreeMap
   * @param listenerMetricsTreeMaps  Metric data gathered in the main runner from all listeners
   */
  protected TreeMap<String,Data> compileTreeMaps(TreeMap<String,Data> listenerMetricsTreeMaps){
    this.listenerMetricsTreeMap = listenerMetricsTreeMaps;
    Data studentData = new Data();

    studentData.addIntData("Ethan Mohan",8160);
    studentData.addIntData("Mikayla Roberts",8160);
    studentData.addIntData("Rennatha Thomas",816044740);
    
    this.listenerMetricsTreeMap.put("Student Information",studentData);
    return this.listenerMetricsTreeMap;
  }
    
}
