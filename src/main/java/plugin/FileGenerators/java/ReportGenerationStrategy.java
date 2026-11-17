package plugin.FileGenerators.java;

import java.util.TreeMap;
import plugin.Data;

/**
 * An interface used to represent the Stategy design pattern.
 * It contains a method which generates a report file and returns it to
 * the calling class.
 */
public interface ReportGenerationStrategy {
  /**
   * The method accepts a TreeMap of String type keys and Data class values, 
   * create a report from the TreeMap and return the file created.
   * @param dataTreeMap A specific type TreeMap
   * @param filePath The file path where the report is to be stored as type String
   * @return The specific file casted as a generic object
   */
  public Object generateReport(TreeMap<String,Data> dataTreeMap, String filePath);
}
