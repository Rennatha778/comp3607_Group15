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
import java.util.ArrayList;

/**
 * This class collects BlueJ class event metrics
 */

public class ClassDataCollection implements ClassListener, BluejListeners{
  int numStatechanges = 0;
  int prevTypedCount = 0;
  int currTypedCount = 0;

  long activeTypeTime = 0;
  long idleTypeTime = 0;
  long prevTypedTime = 0;
  long currTypedTime = 0;
  /**
   * Variable used to store the open editior of a class
   */
  JavaEditor currClassEditor;

  

  TreeMap<String,Long>removedClassesTreeMap = new TreeMap<String,Long>();
  TreeMap<String,Long>createdClassesTreeMap = new TreeMap<String,Long>();
  TreeMap<String,Long>modifiedClassesTreeMap = new TreeMap<String,Long>();
  TreeMap<String,Long>createdMethodsTreeMap = new TreeMap<String,Long>();
    
  /**
   * @return a populated Data class
   */
  public Data getMetricData(){
   Data classMetrics = new Data();
    classMetrics.addIntData("Number_of_Class_State_Changes",numStatechanges);
    classMetrics.addIntData("Number_of_Classes_Removed",removedClassesTreeMap.size());
    classMetrics.addLongData("Total_Active_Typing_Time", activeTypeTime);
    classMetrics.addLongData("Total_Idle_Typing_Time", idleTypeTime);
    classMetrics.addObjectCreationData("Created_Classes",createdClassesTreeMap);
    classMetrics.addObjectCreationData("Removed_Classes",removedClassesTreeMap);
    classMetrics.addObjectCreationData("Modified_Classes",modifiedClassesTreeMap);

     return classMetrics;
  }

  /**
   * This method is used to output class name changes made to the BlueJ debugger log
   */
  @Override
  public void classNameChanged(ClassEvent classEvent){
    //long timeChange = System.currentTimeMillis();
    System.out.println("Class name changed from: "+ classEvent.getOldName());
    System.out.println("Class name changed to: "+ classEvent.getClass().getName());  

    System.out.println("Name changed successfully:");
  }

  /**
   * This method is used to track deleted classes.
   * Their names are stored in a TreeMap, along with the 
   * currMills time they were removed
   */
  @Override 
  public void classRemoved(ClassEvent classEvent){
     Long timeRemoved = System.currentTimeMillis();
    removedClassesTreeMap.put(classEvent.getBClass().getName(),timeRemoved);
  }

  /**
   * This method tracks the creation of new classes within a BlueJ session.
   * Their names are stored in a TreeMap, along with the 
   * currMills time they were created
   * @return True/false depending on if the current class is determined to be a new class
   */
  private synchronized boolean classCreated(Long time, ClassEvent classEvent){
     ArrayList<BClass> bClassesList = new ArrayList<BClass>();
     try{
      for(BClass bClass : classEvent.getPackage().getClasses()){
        bClassesList.add(bClass);
      }
      if(!bClassesList.contains(classEvent.getBClass())){
          createdClassesTreeMap.put(classEvent.getBClass().getName(),time);
          return true;
        }
     }catch(PackageNotFoundException e){
      e.printStackTrace();
     }catch(ProjectNotOpenException e){
      e.printStackTrace();
     }
     return false;
     }
   

  /**
   * This method tracks all the classes that were modified during 
   * the BlueJ session. Their names are stored in a TreeMap along with
   * the time modified.
   */
  private synchronized void classModified(Long time, ClassEvent classEvent){
    modifiedClassesTreeMap.put(classEvent.getBClass().getName(),time);
    System.out.println("Modified class:"+classEvent.getBClass().getName());
  }

 
  /**
   * This method is called whenever a class related event happens.
   * It calls the classCreated, classModified and trackTypeTime methods.
   * It also updated the number of state changes count. 
   */
  @Override 
  public synchronized void classStateChanged(ClassEvent classEvent){
    currTypedTime = System.currentTimeMillis();
      if(!classCreated(currTypedTime,classEvent)){
        classModified(currTypedTime,classEvent);
      }
      trackTypeTime(classEvent);
      
      System.out.println("Class changes event ");
      
    numStatechanges++;
  }


  /**
   * This method is used to determine active and idle typing time when
   * a class editor is opened. A gap between two events greater than 45 seconds 
   * is counted as idle typing time. Gaps that are less than 45 seconds are
   * counted as active typing time.
   */
  private void trackTypeTime(ClassEvent classEvent){
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
        classModified(System.currentTimeMillis(),classEvent);
      }
      prevTypedCount = currTypedCount;
      prevTypedTime = currTypedTime;
     }
        
    }catch(ProjectNotOpenException e){
      System.err.println("PACKAGE NOT OPENED!");
    }catch(PackageNotFoundException e){
      System.err.println("PACKAGE NOT OPENED!");
    }
      
  }
}
