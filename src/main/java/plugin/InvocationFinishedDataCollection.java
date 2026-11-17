package plugin;

import bluej.extensions2.event.InvocationFinishedEvent;
import bluej.extensions2.event.InvocationFinishedListener;


/**
 * This class listens for and collects BlueJ invocation finised event metrics
 */
public class InvocationFinishedDataCollection implements InvocationFinishedListener, BluejListeners{
  int numMethodRuns = 0;

  public Data getMetricData(){
      Data invokeFinisedMetrics = new Data();
      invokeFinisedMetrics.addIntData("Number_of_Methods_Ran", getNumberOfMethodRuns());
    
      return invokeFinisedMetrics;
    }

    /**
     * Thid method is called when a method has finised running.
     * It also calls a function to increment the numMethodRuns counter.
     */
    @Override
    public void invocationFinished(InvocationFinishedEvent invocationFinishedEvent){
      incrmentNumberOfMethodRuns();
      System.out.println(invocationFinishedEvent.getMethodName() +" finished execution");

    }

    protected void incrmentNumberOfMethodRuns(){this.numMethodRuns++;}
    protected int getNumberOfMethodRuns(){return this.numMethodRuns;}
}
