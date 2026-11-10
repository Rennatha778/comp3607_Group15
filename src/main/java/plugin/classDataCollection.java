package plugin;

import bluej.extensions2.BClass;
import bluej.extensions2.BMethod;
import bluej.extensions2.PackageNotFoundException;
import bluej.extensions2.ProjectNotOpenException;
import bluej.extensions2.editor.JavaEditor;
import bluej.extensions2.event.ClassEvent;
import bluej.extensions2.event.ClassListener;
import bluej.extensions2.event.PackageListener;
import java.util.ArrayList;
import java.util.TreeMap;

import bluej.extensions2.event.PackageEvent;


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
    BClass currBClass;

    PackageEvent packageEvent;
    BClass[] classes = null;
    BMethod[] classMethods;
    int arraySize;
    TreeMap<String,Long>removedClasses = new TreeMap<String,Long>();
    TreeMap<String,Long>createdClasses = new TreeMap<String,Long>();
    TreeMap<String,Long>modifiedClasses = new TreeMap<String,Long>();
    TreeMap<String,Long>createdMethods = new TreeMap<String,Long>();;
    
    
    public Data getData(){
      Data d = new Data();
      d.addIntData("numStatechanges",numStatechanges);
      d.addIntData("numClassesRemoved",numClassesRemoved);
      d.addLongData("activeTypingTime", activeTypeTime);
      d.addLongData("idleTypingTime", idelTypeTime);
      d.addObjectCreationData("CreatedClasses",createdClasses);
      d.addObjectCreationData("RemovedClasses",removedClasses);
      d.addObjectCreationData("ModifiedClasses",modifiedClasses);

      return d;
    }

    @Override
    public void classNameChanged(ClassEvent classEvent){
      long time = System.currentTimeMillis();
      System.out.println("Class name changed from: "+ classEvent.getOldName());
      System.out.println("Class name changed to: "+ classEvent.getClass().getName());  

      if(checkName(classEvent.getOldName(),classEvent) && this.classes != null){
        modifiedClasses.put(classEvent.getBClass().getName(),time);
        System.out.println("Created name changed:"+classEvent.getBClass().getName());
        
        updateClasses(classEvent);
        
      }

    }

    @Override 
    public void classRemoved(ClassEvent classEvent){
      Long removedTime = System.currentTimeMillis();

      if(checkName(classEvent.getBClass().getName(),classEvent) && this.classes != null){
        removedClasses.put(classEvent.getBClass().getName(),removedTime);
        numClassesRemoved++;
        updateClasses(classEvent);
      }

      
      
      System.out.println(classEvent.getClass().getName() + " was removed.");
    }

    private void classCreated(Long time, ClassEvent classEvent){

      if(!checkName(classEvent.getBClass().getName(),classEvent) && this.classes != null){
        createdClasses.put(classEvent.getBClass().getName(),time);
        System.out.println("Created class:"+classEvent.getBClass().getName());
        updateClasses(classEvent);
      }

      
    }

    private void trackTypeTime(ClassEvent classEvent){
      currBClass = classEvent.getBClass();
      try{
        classEditor = classEvent.getBClass().getJavaEditorIfOpen();
        currentTyped = classEditor.getTextLength();
    
        if(prevType != 0){
          if(prevTyped != currentTyped && (currType - prevType) < 45000){
            activeTypeTime = activeTypeTime + (currType - prevType);
          }else{
            idelTypeTime = idelTypeTime + (currType - prevType);
          }
        }
        prevTyped = currentTyped;
        prevType = currType;
        updateClasses(classEvent);
        //System.out.println("Lenght of "+classEditor.getTextLength());
      }catch(ProjectNotOpenException e){
        System.err.println("PACKAGE NOT OPENED!");
      }catch(PackageNotFoundException e){
        System.err.println("PACKAGE NOT OPENED!");
      }
      
    }

    public void updateClasses(ClassEvent classEvent){
      try{
          if(classes != null){
            classes = classEvent.getPackage().getClasses();
          }
          
          
        }catch(ProjectNotOpenException e){
        System.err.println("PACKAGE NOT OPENED!");
        }catch(PackageNotFoundException e){
        System.err.println("PACKAGE NOT OPENED!");
        }
    }

    private boolean checkName(String name, ClassEvent classEvent){
      updateClasses(classEvent);
      if(classes != null && classes.length != 0){
        for(BClass bc : classes){
          if(name.equals(bc.getName())){
            return true;
          }
        }
      }
      return false;
    }

    

    @Override 
    public void classStateChanged(ClassEvent classEvent){
      currType = System.currentTimeMillis();

      classCreated(currType, classEvent);
      
      trackTypeTime(classEvent);
      
      //methodCreated(currType,classEvent);

      numStatechanges++;
      System.out.println(classEvent.getClass().getName() + " was changed.");

    }

    public void getPackageInfo(PackageEvent packageEvent){
      this.packageEvent = packageEvent;
      
      try{
        this.classes = this.packageEvent.getPackage().getClasses();
        arraySize = classes.length;
        System.out.println("Classes size :"+arraySize);
        //getClasses(classes);

      }catch(ProjectNotOpenException e){
        System.err.println("PACKAGE NOT OPENED!");
      }catch(PackageNotFoundException e){
        System.err.println("PACKAGE NOT OPENED!");
      }
    }
    /* private void getClasses(BClass[] classes){
      return classes;
    } */
    

}
