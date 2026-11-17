package plugin;

import bluej.extensions2.event.PackageEvent;
import bluej.extensions2.event.PackageListener;


/**
 * This class listens for and collects BlueJ package event metrics
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
   * This method is called when a package is opened and it also updates the opened package counter
   * @param packageEvent A BlueJ package event that occurred
   */
  public void packageOpened(PackageEvent packageEvent){ 
      incrementNumberOfPackagesOpened();   
  }

  /**
   *This method is called when an package is closed and it updates the closed package counter
   *@param packageEvent A BlueJ package event that occurred
   */
  @Override 
  public void packageClosing(PackageEvent packageEvent){
      incrementNumberOfPackagesClosed();
  }
  
  protected void incrementNumberOfPackagesOpened(){this.numPackagesOpened++;}
  protected void incrementNumberOfPackagesClosed(){this.numPackagesClosed++;}
  protected int getNumberOfPackagesOpened(){return this.numPackagesOpened;}
  protected int getNumberOfPackagesClosed(){return this.numPackagesClosed;}
}
