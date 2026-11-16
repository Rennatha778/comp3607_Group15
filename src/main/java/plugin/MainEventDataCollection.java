package plugin;

import bluej.extensions2.event.*;

public class MainEventDataCollection implements  ExtensionEventListener, BluejListeners {
  private long sessionStartTime;
  private long sessionEndTime;
  private long totalIdleTime = 0;
  private long prevEventTime = System.currentTimeMillis();
  private long currEventTime = 0;
  private long totalSessionTime = 0;

  private int numCompileEvent = 0;
  private int numPackageEvent = 0;
  private int numClassEvent = 0;
  private int numInvokeFinishedEvent = 0;
  private int numApplicationEvent = 0;
  
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

  protected void getTotalSessionTime(long totalSessionTime){this.totalSessionTime = totalSessionTime;}

  public Data getMetricData(){
    Data mainExtentionCaterMetrics = new Data();
    mainExtentionCaterMetrics.addLongData("Total_Session_Time",totalSessionTime);
    mainExtentionCaterMetrics.addLongData("Total_Active_Time",(totalSessionTime - totalIdleTime));
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

}
