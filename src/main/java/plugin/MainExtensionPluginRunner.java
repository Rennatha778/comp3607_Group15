package plugin;

import bluej.extensions2.*;
import bluej.extensions2.event.*;
import java.util.TreeMap;

/**
 * This class is the main event listener that catches all event that occur in BlueJ.
 * The class also instantiates the other class listeners, connecting then to the current BlueJ instance.
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

  
  @Override 
  public void startup(BlueJ bluej){
    sessionStartTime = System.currentTimeMillis();
    this.bluejInstance = bluej;
    connectListeners();
    System.out.println("Extension started successfully.");
  } 

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


  @Override 
  public void terminate(){
    sessionEndTime = System.currentTimeMillis();
    mainListener.getTotalSessionTime(getTotalSessionTime(sessionStartTime,sessionEndTime));
    generateReport(collectListenerMetrics());
    disconnectListeners();

    printEndingMetricMessage();
  }

  private TreeMap<String, Data> collectListenerMetrics(){
    TreeMap<String,Data> listenerMetricTreeMaps = new TreeMap<String,Data>();
    listenerMetricTreeMaps.put("packageListner",packageListner.getMetricData());
    listenerMetricTreeMaps.put("classListner",classListner.getMetricData());
    listenerMetricTreeMaps.put("compileListner",compileListner.getMetricData());
    listenerMetricTreeMaps.put("invokeListner",invokeListner.getMetricData());
    listenerMetricTreeMaps.put("main event listener",mainListener.getMetricData());
    return listenerMetricTreeMaps;
  }

  public void generateReport(TreeMap<String,Data> listenerMetricTreeMaps){
    String filePath = "C:\\Users\\Owner\\Documents\\PluginFiles";
    ReportPublisher rp = new JSONFileGenerator();
    ReportPublisher jp = new PDFFileGenerator();

    rp.generateReport(listenerMetricTreeMaps, filePath);
    jp.generateReport(listenerMetricTreeMaps, filePath);

    System.out.println("Report generated");

  }

  private void disconnectListeners(){
    if (bluejInstance != null){
      bluejInstance.removeExtensionEventListener(mainListener);
      bluejInstance.removePackageListener(packageListner);
      bluejInstance.removeClassListener(classListner);
      bluejInstance.removeCompileListener(compileListner);
      bluejInstance.removeInvocationFinishedListener(invokeListner);
    }
  }

  private void printEndingMetricMessage(){
    System.out.println("Extension terminated.");
    System.out.println("Total sesstion time: "+ getTotalSessionTime(this.sessionEndTime,this.sessionStartTime));
  }


  protected long getTotalSessionTime(long sessionStartTime, long sessionEndTime){
    if(sessionEndTime != 0 && sessionStartTime != 0)
      return sessionEndTime - sessionStartTime;
    return (long) 0;
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
