package plugin;

import bluej.debugmgr.objectbench.InvokeListener;
import bluej.extensions2.event.InvocationFinishedEvent;
import bluej.extensions2.event.InvocationFinishedListener;
import bluej.views.ConstructorView;
import bluej.views.MethodView;

public class invocationDataCollection implements InvokeListener{
  @Override 
  public void callConstructor(ConstructorView constructorView){
    System.out.println(constructorView.getClassName() +" started execution");
  }

  @Override 
  public void executeMethod(MethodView methodView){
    System.out.println(methodView.getMethod().getName() +" started execution");
  }


  public class InvocationFinishedDataCollection implements InvocationFinishedListener{
    @Override
    public void invocationFinished(InvocationFinishedEvent invocationFinishedEvent){
      System.out.println(invocationFinishedEvent.getObjectName() +" finished execution");
    }
  }
}
