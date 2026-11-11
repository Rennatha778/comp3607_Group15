package plugin;

import bluej.extensions2.event.InvocationFinishedEvent;
import bluej.extensions2.event.InvocationFinishedListener;



public class InvocationFinishedDataCollection implements InvocationFinishedListener, BluejListeners{
  int numMethodRuns = 0;

  public Data getMetricData(){
      Data invokeFinisedMetrics = new Data();
      invokeFinisedMetrics.addIntData("Number_of_Methods_Ran", this.numMethodRuns);
    
      return invokeFinisedMetrics;
    }

    @Override
    public void invocationFinished(InvocationFinishedEvent invocationFinishedEvent){
      numMethodRuns++;
      System.out.println(invocationFinishedEvent.getMethodName() +" finished execution");
    }
  }
