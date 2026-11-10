package plugin;

import bluej.extensions2.PackageNotFoundException;
import bluej.extensions2.ProjectNotOpenException;
import bluej.extensions2.editor.JavaEditor;
import bluej.extensions2.event.ClassEvent;
import bluej.extensions2.event.ClassListener;

public class classDataCollection implements ClassListener, BluejListeners{
    int numStatechanges = 0;
    int numClassesRemoved = 0;
    int prevTyped = 0;
    int currentTyped = 0;
    long activeTypeTime = 0;
    long idelTypeTime = 0;
    long prevType = 0;
    long currType;
    JavaEditor classEditor;
    
    
    public Data getData(){
      Data d = new Data();
      d.addIntData("numStatechanges",numStatechanges);
      d.addIntData("numClassesRemoved",numClassesRemoved);
      d.addLongData("activeTypingTime", activeTypeTime);
      d.addLongData("idleTypingTime", idelTypeTime);

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
      currType = System.currentTimeMillis();
      try{
        classEditor = classEvent.getBClass().getJavaEditorIfOpen();
        currentTyped = classEditor.getTextLength();
        if(prevType != 0){
          if(prevTyped != currentTyped && (currType - prevType) < 9000){
            activeTypeTime = activeTypeTime + (currType - prevType);
          }else{
            idelTypeTime = idelTypeTime + (currType - prevType);
          }
        }
        prevTyped = currentTyped;
        prevType = currType;
        //System.out.println("Lenght of "+classEditor.getTextLength());
      }catch(ProjectNotOpenException e){
        System.err.println("PACKAGE NOT OPENED!");
      }catch(PackageNotFoundException e){
        System.err.println("PACKAGE NOT OPENED!");
      }

      numStatechanges++;
      System.out.println(classEvent.getClass().getName() + " was changed.");

      
    }
}
