package plugin;

import java.util.TreeMap;

/**
 * An interface used to implement Stategy design pattern.
 * Classes implementing this interface accept a TreeMap of type String keys and Data class values, create a report from the TreeMap and return the file created.
 */
public interface ReportPublisher {
  /**
   * The method accepts a TreeMap of type String keys and Data class values, create a report from the TreeMap and return the file created.
   * @param dataTreeMap A specific type TreeMap
   * @param filePath The file path where the report is to be stored as type String
   * @return The specific file cast as a generic object
   */
  public Object generateReport(TreeMap<String,Data> dataTreeMap, String filePath);
}
