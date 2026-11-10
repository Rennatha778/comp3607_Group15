package plugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.TreeMap;

public class CreatingJSONFile implements ReportPublisher {
  TreeMap<String,Data> dataTreeMap;
  TreeMap<String,Integer> studentInfo;


  public void generateReport(TreeMap<String,Data> tr){
    dataTreeMap = tr;
    studentInfo = new TreeMap<String,Integer>();
    studentInfo.put("Ethan Mohan",8160);
    studentInfo.put("Mikayla Roberts",8160);
    studentInfo.put("Rennatha Thomas",816044740);
    
    try {
        File directory =  new File("C:\\Users\\Owner\\Documents\\PluginFiles");
        directory.mkdirs();

        File file = new File(directory, "PluginMetrics.json");

        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(file,studentInfo);
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, dataTreeMap);
        
        System.out.println("JSON file created.");
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to create JSON file.");
        }
    }
    
}


  /* public CreatingJASONFile(BlueJ blueJ){
    
  } */

  /* public static void main(String[] args) {
        try {
            Map<String, Object> data = new HashMap<>();
            data.put("Name:","John");

            ObjectMapper mapper = new ObjectMapper();

            mapper.writerWithDefaultPrettyPrinter()
                  .writeValue(new File("student.json"), data);

            System.out.println("JSON file created.");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
} */
