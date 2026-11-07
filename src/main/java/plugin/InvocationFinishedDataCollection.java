package plugin;

//import bluej.BlueJEventListener;
//import bluej.debugmgr.objectbench.InvokeListener;
import bluej.extensions2.event.InvocationFinishedEvent;
import bluej.extensions2.event.InvocationFinishedListener;
//import bluej.views.ConstructorView;
//import bluej.views.MethodView;

/* public class invocationDataCollection implements InvokeListener{
  @Override 
  public void callConstructor(ConstructorView constructorView){
    System.out.println(constructorView.getClassName() +" started execution");
  }

  @Override 
  public void executeMethod(MethodView methodView){
    System.out.println(methodView.getMethod().getName() +" started execution");
  }
} */


  public class InvocationFinishedDataCollection implements InvocationFinishedListener, BluejListeners{
    int numMethodRuns = 0;

    public Data getData(){
      Data d = new Data();
      d.addIntData("numMethodRuns", numMethodRuns);
    
      return d;
    }
    @Override
    public void invocationFinished(InvocationFinishedEvent invocationFinishedEvent){
      numMethodRuns++;
      System.out.println(invocationFinishedEvent.getObjectName() +" finished execution");
    }
  }
