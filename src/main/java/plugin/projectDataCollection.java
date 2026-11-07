package plugin;

import bluej.extensions2.PackageNotFoundException;
import bluej.extensions2.ProjectNotOpenException;
import bluej.extensions2.event.PackageEvent;
import bluej.extensions2.event.PackageListener;
import bluej.pkgmgr.Project;

public class projectDataCollection implements PackageListener, BluejListeners{
  String packageName;
  int numPackagesOpened = 0;

  public Data getData(){
    Data d = new Data();
    d.addIntData("numPackagesOpened",numPackagesOpened);

    return d;
  }

  @Override 
  public void packageOpened (PackageEvent packageEvent){
    numPackagesOpened++;
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
