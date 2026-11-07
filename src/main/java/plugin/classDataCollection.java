package plugin;

import bluej.extensions2.event.ClassEvent;
import bluej.extensions2.event.ClassListener;

public class classDataCollection implements ClassListener, BluejListeners{
    int numStatechanges = 0;
    int numClassesRemoved = 0;
    
    public Data getData(){
      Data d = new Data();
      d.addIntData("numStatechanges",numStatechanges);
      d.addIntData("numClassesRemoved",numClassesRemoved);

      return d;
    }
    @Override
    public void classNameChanged(ClassEvent classEvent){
      System.out.println("Class name changed from: "+ classEvent.getOldName());
      System.out.println("Class name changed to: "+ classEvent.getClass().getName());
      
    }

    @Override 
    public void classRemoved(ClassEvent classEvent){
      numClassesRemoved++;
      System.out.println(classEvent.getClass().getName() + " was removed.");
    }

    @Override 
    public void classStateChanged(ClassEvent classEvent){
      numStatechanges++;
      System.out.println(classEvent.getClass().getName() + " was changed.");
      
    }
}
