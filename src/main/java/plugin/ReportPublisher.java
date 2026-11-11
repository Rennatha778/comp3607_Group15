package plugin;

import java.util.TreeMap;

public interface ReportPublisher {
  public void generateReport(TreeMap<String,Data> dataTreeMap);
}
