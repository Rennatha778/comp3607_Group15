package plugin;

import java.util.TreeMap;

public interface ReportPublisher {
  public Object generateReport(TreeMap<String,Data> dataTreeMap, String filePath);
}
