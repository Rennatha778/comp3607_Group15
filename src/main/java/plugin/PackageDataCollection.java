package plugin;

import bluej.extensions2.PackageNotFoundException;
import bluej.extensions2.ProjectNotOpenException;
import bluej.extensions2.event.PackageEvent;
import bluej.extensions2.event.PackageListener;


/**
 * This class collects BlueJ package event metrics
 */
public class PackageDataCollection implements PackageListener, BluejListeners{
  int numPackagesOpened = 0;
  int numPackagesClosed = 0;
 

  public Data getMetricData(){
    Data packageMetrics = new Data();
    packageMetrics.addIntData("Number_of_Packages_Opened",getNumberOfPackagesOpened());
    packageMetrics.addIntData("Number_of_Packages_Closed",getNumberOfPackagesClosed());

    return packageMetrics;
  }

  @Override 
  /**
   *This method is automatically called when an package is opened and it updates the opened package counter
   */
  public void packageOpened(PackageEvent packageEvent){ 
    /* try{ */
      incrementNumberOfPackagesOpened();   
      System.out.println(packageEvent.toString());

    /* } catch(ProjectNotOpenException e){
      System.err.println("PACKAGE NOT OPENED!");
    }catch(PackageNotFoundException e){
      System.err.println("PACKAGE NOT OPENED!");
    } */

  }

  /**
   *This method is automatically called when an package is closed and it updates the closed package counter
   */
  @Override 
  public void packageClosing(PackageEvent packageEvent){
    /* try{ */
      //System.out.println(packageEvent.getPackage().getProject().getDir().getName+ " closed!");
      incrementNumberOfPackagesClosed();
    /* } catch(ProjectNotOpenException e){
      System.err.println("PACKAGE NOT OPENED!");
    }catch(PackageNotFoundException e){
      System.err.println("PACKAGE NOT OPENED!");
    } */
    
  }
  protected void incrementNumberOfPackagesOpened(){this.numPackagesOpened++;}
  protected void incrementNumberOfPackagesClosed(){this.numPackagesClosed++;}
  protected int getNumberOfPackagesOpened(){return this.numPackagesOpened;}
  protected int getNumberOfPackagesClosed(){return this.numPackagesClosed;}
}
