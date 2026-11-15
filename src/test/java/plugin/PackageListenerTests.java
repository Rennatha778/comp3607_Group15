package plugin;
import bluej.extensions2.PackageNotFoundException;
import bluej.extensions2.ProjectNotOpenException;
import bluej.extensions2.event.PackageEvent;
import bluej.extensions2.event.PackageListener;

public class PackageListenerTests {
  PackageDataCollection testPackageListener = new PackageDataCollection();
  testPackageListener.packageOpened(new Object()); 
  
  //packageListner.packageOpened((PackageEvent)new Object());
}
