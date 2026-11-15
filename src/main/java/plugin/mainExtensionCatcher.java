package plugin;

import bluej.extensions2.*;
import bluej.extensions2.event.*;
import java.util.TreeMap;

public  class mainExtensionCatcher extends Extension implements ExtensionEventListener, BluejListeners {
  private long sessionStartTime;
  private long sessionEndTime;
  private long totalIdleTime = 0;
  private long prevEventTime = 0;
  private long currEventTime = 0;
  private BlueJ bluejInstance;

  private CompileDataCollection compileListner;
  private ClassDataCollection classListner;
  private InvocationFinishedDataCollection invokeListner;
  private PackageDataCollection packageListner;
  

  private int numCompileEvent = 0;
  private int numPackageEvent = 0;
  private int numClassEvent = 0;
  private int numInvokeFinishedEvent = 0;
  private int numApplicationEvent = 0;

  
  @Override 
  public void startup(BlueJ bluej){
    sessionStartTime = System.currentTimeMillis();
    prevEventTime = sessionStartTime;
    this.bluejInstance = bluej;
    connectListeners();
    System.out.println("Extension started successfully.");
  } 

  public void connectListeners(){
    compileListner = new CompileDataCollection();
    classListner = new ClassDataCollection();
    packageListner = new PackageDataCollection();
    invokeListner = new InvocationFinishedDataCollection();
  
    bluejInstance.addExtensionEventListener(this);
    bluejInstance.addCompileListener(compileListner);
    bluejInstance.addClassListener(classListner);
    bluejInstance.addPackageListener(packageListner);
    bluejInstance.addInvocationFinishedListener(invokeListner);
  }

  @Override
  public synchronized void eventOccurred(ExtensionEvent event){
    currEventTime = System.currentTimeMillis();
    System.out.println("Event occurred:"+event.getClass().getSimpleName());
    trackGap();
    updateEventCount(event);
  }

  private synchronized void trackGap(){
    if((currEventTime - prevEventTime) > 60000){ // if gap between events is more than 1 minute (60 seconds), it is counted as idle time
      totalIdleTime = totalIdleTime + (currEventTime - prevEventTime);
    }
    prevEventTime = currEventTime;
  }

  private void updateEventCount(ExtensionEvent event){
    if(event instanceof ApplicationEvent){
      numApplicationEvent++;
    }else if(event instanceof CompileEvent){
      numCompileEvent++;
    }else if (event instanceof ClassEvent){
      numClassEvent++;
    }else if (event instanceof PackageEvent){
      numPackageEvent++;
    }else if( event instanceof InvocationFinishedEvent){
      numInvokeFinishedEvent++;
    }
  }


  @Override 
  public void terminate(){
    sessionEndTime = System.currentTimeMillis();
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
    listenerMetricTreeMaps.put("main extension listener",this.getMetricData());
    return listenerMetricTreeMaps;
  }

  public void generateReport(TreeMap<String,Data> listenerMetricTreeMaps){
    ReportPublisher rp = new JSONFileGenerator();
    ReportPublisher jp = new PDFFileGenerator();

    rp.generateReport(listenerMetricTreeMaps);
    jp.generateReport(listenerMetricTreeMaps);

    System.out.println("Report generated");

  }

  private void disconnectListeners(){
    if (bluejInstance != null){
      bluejInstance.removeExtensionEventListener(this);
      bluejInstance.removePackageListener(packageListner);
      bluejInstance.removeClassListener(classListner);
      bluejInstance.removeCompileListener(compileListner);
      bluejInstance.removeInvocationFinishedListener(invokeListner);
    }
  }

  private void printEndingMetricMessage(){
    System.out.println("Extension terminated.");
    System.out.println("Total sesstion time: "+ getTotalSessionTime(this.sessionEndTime,this.sessionStartTime));
    System.out.println("Total Idle time: "+totalIdleTime);
    System.out.println("Total active time: "+(getTotalSessionTime(this.sessionEndTime,this.sessionStartTime) - totalIdleTime));
  }


  protected long getTotalSessionTime(long sessionEndTime, long sessionStartTime){
    if(sessionEndTime != 0 && sessionStartTime != 0)
      return sessionEndTime - sessionStartTime;
    return (long) 0;
  }

  public Data getMetricData(){
    Data mainExtentionCaterMetrics = new Data();
    mainExtentionCaterMetrics.addLongData("Total_Session_Time",getTotalSessionTime(this.sessionEndTime,this.sessionStartTime));
    mainExtentionCaterMetrics.addLongData("Total_Active_Time",(getTotalSessionTime(this.sessionEndTime,this.sessionStartTime) - totalIdleTime));
    mainExtentionCaterMetrics.addLongData("Total_Idle_Time",totalIdleTime);

    mainExtentionCaterMetrics.addIntData("Application_Events", numApplicationEvent);
    mainExtentionCaterMetrics.addIntData("Class_Events", numClassEvent);
    mainExtentionCaterMetrics.addIntData("Compile_Events", numCompileEvent);
    mainExtentionCaterMetrics.addIntData("Invoke_Finished_Events", numInvokeFinishedEvent);
    mainExtentionCaterMetrics.addIntData("Package_Events", numPackageEvent);
    mainExtentionCaterMetrics.addLongData("Session_Start_Time",sessionStartTime);
    mainExtentionCaterMetrics.addLongData("Session_End_Time",sessionEndTime);

    return mainExtentionCaterMetrics;
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
