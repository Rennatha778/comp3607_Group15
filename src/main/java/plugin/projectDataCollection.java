package plugin;

import bluej.extensions2.PackageNotFoundException;
import bluej.extensions2.ProjectNotOpenException;
import bluej.extensions2.event.PackageEvent;
import bluej.extensions2.event.PackageListener;
import bluej.pkgmgr.Project;

public class projectDataCollection implements PackageListener{
  String packageName;

  @Override 
  public void packageOpened (PackageEvent packageEvent){

    try{
      packageName = packageEvent.getPackage().getName();
      System.out.println(packageName + " opened!");
    } catch(ProjectNotOpenException e){
      System.err.println("PACKAGE NOT OPENED!");
      packageName = "Unknown Package";
    }catch(PackageNotFoundException e){
      System.err.println("PACKAGE NOT OPENED!");
      packageName = "Unknown Package";
    }

  }

  @Override 
  public void packageClosing(PackageEvent packageEvent){
    try{
      packageName = packageEvent.getPackage().getName();
      System.out.println(packageName + " closed!");
    } catch(ProjectNotOpenException e){
      System.err.println("PACKAGE NOT OPENED!");
      packageName = "Unknown Package";
    }catch(PackageNotFoundException e){
      System.err.println("PACKAGE NOT OPENED!");
      packageName = "Unknown Package";
    }
    
  }

  
}
