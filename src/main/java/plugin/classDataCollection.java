package plugin;

import bluej.extensions2.BClass;
import bluej.extensions2.BMethod;
import bluej.extensions2.BPackage;
import bluej.extensions2.PackageNotFoundException;
import bluej.extensions2.ProjectNotOpenException;
import bluej.extensions2.editor.JavaEditor;
import bluej.extensions2.event.ClassEvent;
import bluej.extensions2.event.ClassListener;
import java.util.TreeMap;



public class ClassDataCollection implements ClassListener, BluejListeners{
  int numStatechanges = 0;
  int numClassesRemoved = 0;
  int prevTypedCount = 0;
  int currTypedCount = 0;

  long activeTypeTime = 0;
  long idleTypeTime = 0;
  long prevTypedTime = 0;
  long currTypedTime = 0;

  JavaEditor currClassEditor;
  BClass currBClass;

  BPackage currPackage = null;
  BClass[] classes = null;
  BMethod[] classMethods;
  int classesSize;

  TreeMap<String,Long>removedClassesTreeMap = new TreeMap<String,Long>();
  TreeMap<String,Long>createdClassesTreeMap = new TreeMap<String,Long>();
  TreeMap<String,Long>modifiedClassesTreeMap = new TreeMap<String,Long>();
  TreeMap<String,Long>createdMethodsTreeMap = new TreeMap<String,Long>();;
    
    
  public Data getMetricData(){
   Data classMetrics = new Data();
    classMetrics.addIntData("Number_of_State_Changes",numStatechanges);
    classMetrics.addIntData("Number_of_Classes_Removed",numClassesRemoved);
    classMetrics.addLongData("Total_Active_Typing_Time", activeTypeTime);
    classMetrics.addLongData("Total_Idle_Typing_Time", idleTypeTime);
    classMetrics.addObjectCreationData("Created_Classes",createdClassesTreeMap);
    classMetrics.addObjectCreationData("Removed_Classes",removedClassesTreeMap);
    classMetrics.addObjectCreationData("Modified_Classes",modifiedClassesTreeMap);

     return classMetrics;
  }

  @Override
  public void classNameChanged(ClassEvent classEvent){
    long timeChange = System.currentTimeMillis();
    System.out.println("Class name changed from: "+ classEvent.getOldName());
    System.out.println("Class name changed to: "+ classEvent.getClass().getName());  

    if(this.classes != null && checkName(classEvent.getOldName(),classEvent)){
      modifiedClassesTreeMap.put(classEvent.getBClass().getName(),timeChange);
      System.out.println("Name changed successfully:");
      updateClasses(classEvent);
    }

  }

  @Override 
  public void classRemoved(ClassEvent classEvent){
    Long timeRemoved = System.currentTimeMillis();

    if(this.classes != null && !checkName(classEvent.getBClass().getName(),classEvent) && !removedClassesTreeMap.containsKey(classEvent.getBClass().getName())){
      removedClassesTreeMap.put(classEvent.getBClass().getName(),timeRemoved);
      numClassesRemoved++;
      updateClasses(classEvent);
    }
      
    System.out.println(classEvent.getBClass().getName() + " was removed.");
  }

  private synchronized void classCreated(Long time, ClassEvent classEvent){
    if(this.classes != null && !checkName(classEvent.getBClass().getName(),classEvent) && !createdClassesTreeMap.containsKey(classEvent.getBClass().getName())){
      createdClassesTreeMap.put(classEvent.getBClass().getName(),time);
      System.out.println("Created class:"+classEvent.getBClass().getName());
      updateClasses(classEvent);
    }
  }

  private synchronized void classModified(Long time, ClassEvent classEvent){
    if(this.classes != null && !checkName(classEvent.getBClass().getName(),classEvent) && !modifiedClassesTreeMap.containsKey(classEvent.getBClass().getName())){
      modifiedClassesTreeMap.put(classEvent.getBClass().getName(),time);
      System.out.println("Created class:"+classEvent.getBClass().getName());
      updateClasses(classEvent);
    }
  }

  private void trackTypeTime(ClassEvent classEvent){
    currBClass = classEvent.getBClass();
    try{
      if(classEvent.getBClass().getJavaEditorIfOpen() != null){
        currClassEditor = classEvent.getBClass().getJavaEditorIfOpen();
        currTypedCount = currClassEditor.getTextLength();
    
      if(prevTypedTime != 0){
        long typeTime = currTypedTime - prevTypedTime;
        if(prevTypedCount != currTypedCount && typeTime < 45000){ //a gap time 45 seconds or greater is counted as idle time
          activeTypeTime = activeTypeTime + typeTime;
        }else{
          idleTypeTime = idleTypeTime + typeTime;
        }
      }
      prevTypedCount = currTypedCount;
      prevTypedTime = currTypedTime;
      updateClasses(classEvent);
     }
        
    }catch(ProjectNotOpenException e){
      System.err.println("PACKAGE NOT OPENED!");
    }catch(PackageNotFoundException e){
      System.err.println("PACKAGE NOT OPENED!");
    }
      
  }

  public synchronized void updateClasses(ClassEvent classEvent){
    try{
      if(classes != null && currPackage != null){
        this.classes = currPackage.getClasses();
      }
        
    }catch(ProjectNotOpenException e){
      System.err.println("PACKAGE NOT OPENED!");
    }catch(PackageNotFoundException e){
      System.err.println("PACKAGE NOT OPENED!");
    }
  }

  private synchronized boolean checkName(String name, ClassEvent classEvent){
    updateClasses(classEvent);
    if(this.classes != null && classes.length != 0){
      for(BClass bc : classes){
        if(name.equals(bc.getName())){
          return true;
        }
      }
    }
    return false;
  }

  @Override 
  public synchronized void classStateChanged(ClassEvent classEvent){
    currTypedTime = System.currentTimeMillis();

    classCreated(currTypedTime, classEvent);
    classModified(currTypedTime,classEvent);
      
    trackTypeTime(classEvent);
      
    numStatechanges++;
    System.out.println(classEvent.getClass().getName() + " was changed.");

  }

  public void getPackageInfo(BPackage currentPackage){
    this.currPackage = currentPackage;
      
    try{
      if(currentPackage!= null){
        this.classes = this.currPackage.getClasses();
        classesSize = classes.length;
        System.out.println("Classes size :"+classesSize);
      }
    
    }catch(ProjectNotOpenException e){
      System.err.println("PACKAGE NOT OPENED!");
    }catch(PackageNotFoundException e){
      System.err.println("PACKAGE NOT OPENED!");
    }
  }   

}
