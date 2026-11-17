package plugin;

import bluej.extensions2.*;
import java.util.TreeMap;

/**
 * This class is the main file that first runs when BlueJ opens.
 * The class also instantiates the other class listeners and  
 * connects then to the current BlueJ instance.
 * It also represents the facade design pattern 
 */
public class MainExtensionPluginRunner extends Extension {
  private long sessionStartTime;
  private long sessionEndTime;
  private BlueJ bluejInstance;

  private CompileDataCollection compileListner;
  private ClassDataCollection classListner;
  private InvocationFinishedDataCollection invokeListner;
  private PackageDataCollection packageListner;
  private MainEventDataCollection mainListener;

  
  /**
   * This method is called when BlueJ starts up.
   * It records the app start time and calls the method that connects all event listeners to the BlueJ instance
   * @param bluej Current BlueJ instance
   */
  @Override 
  public void startup(BlueJ bluej){
    sessionStartTime = System.currentTimeMillis();
    this.bluejInstance = bluej;
    connectListeners();
    System.out.println("Extension started successfully.");
  } 


  /**
   * Instantiates event listener classes and connects then to the BlueJ instance
   */
  public void connectListeners(){
    compileListner = new CompileDataCollection();
    classListner = new ClassDataCollection();
    packageListner = new PackageDataCollection();
    invokeListner = new InvocationFinishedDataCollection();
    mainListener = new MainEventDataCollection();

  
    bluejInstance.addExtensionEventListener(mainListener);
    bluejInstance.addCompileListener(compileListner);
    bluejInstance.addClassListener(classListner);
    bluejInstance.addPackageListener(packageListner);
    bluejInstance.addInvocationFinishedListener(invokeListner);
  }


  /**
   * This method is called when the BlueJ app is closed. It captures 
   * the time BlueJ was closed, sends it to the main event listener class,
   * calls the method that generates reports with the data collected 
   * from the listener classes, and lastly disconnects the event listener classes.
   */
  @Override 
  public void terminate(){
    sessionEndTime = System.currentTimeMillis();
    mainListener.getSessionTimes(sessionStartTime,sessionEndTime);
    generateReports(collectListenerMetrics());
    disconnectListeners();
    System.out.println("Extension terminated.");
  }

  /**
   * This method gathers all data that was collected by the event listeners,
   * which were stored in TreeMaps and combines them into a single TreeMap.
   * @return A populated {@code TreeMap<String,Data>} with metrics from allclass listeners
   */
  protected TreeMap<String, Data> collectListenerMetrics(){
    TreeMap<String,Data> listenerMetricTreeMaps = new TreeMap<String,Data>();
    listenerMetricTreeMaps.put("packageListner",packageListner.getMetricData());
    listenerMetricTreeMaps.put("classListner",classListner.getMetricData());
    listenerMetricTreeMaps.put("compileListner",compileListner.getMetricData());
    listenerMetricTreeMaps.put("invokeListner",invokeListner.getMetricData());
    listenerMetricTreeMaps.put("main event listener",mainListener.getMetricData());
    return listenerMetricTreeMaps;
  }

  /**
   * Method class classes responsible for generating reports in different formats
   * using the data in the combined TreeMap. The report files are stored
   * in the location directed by the filePath String variable.
   * @param listenerMetricTreeMaps TreeMap with all BlueJ class listener metrics 
   */
  public void generateReports(TreeMap<String,Data> listenerMetricTreeMaps){
    String filePath = "C:\\Users\\Owner\\Documents\\PluginFiles";
    ReportGenerationContext jsonContext = new ReportGenerationContext();
    ReportGenerationContext pdfContext = new ReportGenerationContext();

    jsonContext.setFileGenerationStrategy(new JSONFileGenerator());
    pdfContext.setFileGenerationStrategy(new PDFFileGenerator());

    jsonContext.createReport(listenerMetricTreeMaps,filePath);
    pdfContext.createReport(listenerMetricTreeMaps,filePath);

    System.out.println("Report generated");

  }

  /**
   * This method disconnects class listeners from the terminated BlueJ instance 
   */
  private void disconnectListeners(){
    if (bluejInstance != null){
      bluejInstance.removeExtensionEventListener(mainListener);
      bluejInstance.removePackageListener(packageListner);
      bluejInstance.removeClassListener(classListner);
      bluejInstance.removeCompileListener(compileListner);
      bluejInstance.removeInvocationFinishedListener(invokeListner);
    }
  }


  @Override 
  public boolean isCompatible(){
    return true;
  }

  @Override 
  public String getVersion(){
    return "1.0";
  }

  @Override 
  public String getName(){
    return "BlueJ Metrics Plugin";
  } 

  @Override
  public String getDescription() {
      return "A metric tracker plugin";
   }
}
