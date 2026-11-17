package plugin;

import java.util.TreeMap;

public class ReportGenerationContext {
  protected ReportGenerationStrategy strategy;
  protected void setFileGenerationStrategy(ReportGenerationStrategy strategy){
    this.strategy = strategy;
  }
  
  public Object createReport(TreeMap<String,Data> listenerDataTreeMap, String filePath){
    return this.strategy.generateReport(listenerDataTreeMap, filePath);
  }
}
