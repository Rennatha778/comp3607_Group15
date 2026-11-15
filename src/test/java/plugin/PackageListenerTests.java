package plugin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;

public class PackageListenerTests {
  static PackageDataCollection testPackageListener;

  @BeforeAll
  static void setup(){
    testPackageListener = new PackageDataCollection();  
  }
    
  
  @Test
  void numProjectsOpened(){
    testPackageListener.updateNumberOfPackagesOpened();
    testPackageListener.updateNumberOfPackagesOpened();
    testPackageListener.updateNumberOfPackagesOpened();
    assertEquals(3,testPackageListener.getNumberOfPackagesOpened());
  }
  
}
