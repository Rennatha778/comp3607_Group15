package plugin.FileGenerators.java;

import java.util.TreeMap;

import plugin.Data;

public class ReportGenerationContext {
  protected ReportGenerationStrategy strategy;
  public void setFileGenerationStrategy(ReportGenerationStrategy strategy){
    this.strategy = strategy;
  }
  
  public Object createReport(TreeMap<String,Data> listenerDataTreeMap, String filePath){
    return this.strategy.generateReport(listenerDataTreeMap, filePath);
  }
}
