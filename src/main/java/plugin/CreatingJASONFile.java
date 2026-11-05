package plugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CreatingJASONFile {
  public static void main(String[] args) {
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
}
