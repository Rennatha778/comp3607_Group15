package plugin;

import bluej.extensions2.event.*;

/**
 * This class listens for BlueJ events.
 * It collects general event metric and session time metrics
 */
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
  
  /**
   * This method is called automatically everytime a event occurs in BlueJ
   */
  @Override
  public synchronized void eventOccurred(ExtensionEvent event){
    currEventTime = System.currentTimeMillis();
    System.out.println("Event occurred:"+event.getClass().getSimpleName());
    trackGap();
    updateEventCount(event);
  }

  /**
   * This method calculates the time elapsed between events and 
   * determines if it was idle time or active time.
   * Idle time is elapsed time greater than 1 minute (60 seconds), 
   * Active time is elapsed time less than 1 minute (60 seconds).
   * A running total for idle time is also done in this method.
   */
  private synchronized void trackGap(){
    if((currEventTime - prevEventTime) > 60000){ 
      totalIdleTime = totalIdleTime + (currEventTime - prevEventTime);
    }
    prevEventTime = currEventTime;
  }

  /**
   * This method determines what time of event occured and updates
   * its respective event count. 
   */
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

  /**
   * This method is called when BlueJ is terminated.
   * It receives session start and end times from the main runner.
   * It also calls the method to calculate total session time.
   */
  protected void getSessionTimes(long sessionStartTime, long sessionEndTime){
    this.sessionStartTime = sessionStartTime;
    this.sessionEndTime = sessionEndTime;
    this.totalSessionTime = calculateTotalSessionTime(this.sessionStartTime,this.sessionEndTime);
  }

  /**
   * This method calculates total session time elapsed based off of 
   * recorded app start and end times.
   * @param sessionStartTime System currMil when BlueJ started
   * @param sessionEndTime System currMills when BlueJ terminated 
   */
  protected long calculateTotalSessionTime(long sessionStartTime, long sessionEndTime){
    if(sessionEndTime != 0 && sessionStartTime != 0)
      return sessionEndTime - sessionStartTime;
    return (long) 0;
  }

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
