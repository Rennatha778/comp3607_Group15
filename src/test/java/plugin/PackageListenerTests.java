package plugin;

import org.junit.jupiter.api.Test;

import plugin.EventListeners.java.PackageDataCollection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;

public class PackageListenerTests {
  static PackageDataCollection testPackageListener;

  @BeforeAll
  static void setup(){
    testPackageListener = new PackageDataCollection();  
  }
    
  
  @Test
  void TestForNumberOfProjectsOpened(){
    testPackageListener.incrementNumberOfPackagesOpened();
    testPackageListener.incrementNumberOfPackagesOpened();
    testPackageListener.incrementNumberOfPackagesOpened();
    assertEquals(3,testPackageListener.getNumberOfPackagesOpened());
  }
  
}
