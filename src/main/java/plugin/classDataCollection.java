package plugin;

import bluej.extensions2.event.ClassEvent;
import bluej.extensions2.event.ClassListener;

public class classDataCollection implements ClassListener{

    @Override
    public void classNameChanged(ClassEvent classEvent){
      System.out.println("Class name changed from: "+ classEvent.getOldName());
      System.out.println("Class name changed to: "+ classEvent.toString());
      
    }

    @Override 
    public void classRemoved(ClassEvent classEvent){
      System.out.println(classEvent.getOldName() + " was removed.");
    }

    @Override 
    public void classStateChanged(ClassEvent classEvent){
      System.out.println(classEvent.getOldName() + " was removed.");
    }
}
