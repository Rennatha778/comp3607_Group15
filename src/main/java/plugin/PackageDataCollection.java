package plugin;

import bluej.extensions2.PackageNotFoundException;
import bluej.extensions2.ProjectNotOpenException;
import bluej.extensions2.event.PackageEvent;
import bluej.extensions2.event.PackageListener;


public class PackageDataCollection implements PackageListener, BluejListeners{
  int numPackagesOpened = 0;

  public Data getMetricData(){
    Data packageMetrics = new Data();
    packageMetrics.addIntData("Number_of_Packages_Opened",numPackagesOpened);

    return packageMetrics;
  }

  @Override 
  public void packageOpened (PackageEvent packageEvent){ 
    try{
      numPackagesOpened++;      
      System.out.println(packageEvent.getPackage().getName() + " opened!");

    } catch(ProjectNotOpenException e){
      System.err.println("PACKAGE NOT OPENED!");
    }catch(PackageNotFoundException e){
      System.err.println("PACKAGE NOT OPENED!");
    }

  }

  @Override 
  public void packageClosing(PackageEvent packageEvent){
    try{
      System.out.println(packageEvent.getPackage().getName()+ " closed!");
    } catch(ProjectNotOpenException e){
      System.err.println("PACKAGE NOT OPENED!");
    }catch(PackageNotFoundException e){
      System.err.println("PACKAGE NOT OPENED!");
    }
    
  }
}
