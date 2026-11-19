package plugin;

import org.junit.jupiter.api.Test;

import bluej.extensions2.event.PackageEvent;
import plugin.EventListeners.java.PackageDataCollection;
import plugin.Iterators.java.TreeMapTraverser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import java.util.TreeMap;

public class PackageListenerTests {
  static PackageDataCollection testPackageListener;
  static TreeMapTraverser testerTreeMapTraverser;
  static TreeMap<String,Data> testerPackageTreeMap;

  @BeforeAll
  static void setup(){ 
    testerTreeMapTraverser = new TreeMapTraverser();
    testerPackageTreeMap = new TreeMap<String,Data>();
  }
    
  
  @Test
  void TestForNumberOfProjectsOpened(){
    testPackageListener = new PackageDataCollection(); 
    testPackageListener.incrementNumberOfPackagesOpened();
    testPackageListener.incrementNumberOfPackagesOpened();
    testPackageListener.incrementNumberOfPackagesOpened();
    assertEquals(3,testPackageListener.getNumberOfPackagesOpened());
  }

  @Test
  void TestForNumberOfProjectsClosed(){
    testPackageListener = new PackageDataCollection(); 
    testPackageListener.incrementNumberOfPackagesClosed();
    testPackageListener.incrementNumberOfPackagesClosed();
    assertEquals(2,testPackageListener.getNumberOfPackagesClosed());
  }

  @Test
  void TestForGetMetrics1(){
    testPackageListener = new PackageDataCollection(); 
    testPackageListener.incrementNumberOfPackagesClosed();
    testPackageListener.incrementNumberOfPackagesClosed();
    testPackageListener.incrementNumberOfPackagesOpened();
    testPackageListener.incrementNumberOfPackagesOpened();
    testPackageListener.incrementNumberOfPackagesClosed();
    testerPackageTreeMap.put("Tester_Package_Data",testPackageListener.getMetricData());
    assertEquals(3,testerTreeMapTraverser.getMetricValue(testerPackageTreeMap,"Number_of_Packages_Closed"));
  }

  @Test
  void TestForGetMetrics2(){
    testPackageListener = new PackageDataCollection(); 
    testPackageListener.incrementNumberOfPackagesClosed();
    testPackageListener.incrementNumberOfPackagesClosed();
    testPackageListener.incrementNumberOfPackagesOpened();
    testPackageListener.incrementNumberOfPackagesOpened();
    testPackageListener.incrementNumberOfPackagesClosed();
    testerPackageTreeMap.put("Tester_Package_Data",testPackageListener.getMetricData());
    assertEquals(2,testerTreeMapTraverser.getMetricValue(testerPackageTreeMap,"Number_of_Packages_Opened"));
  }

  @Test
  void testForpackageOpened(){
    testPackageListener = new PackageDataCollection();
    testPackageListener.packageOpened(null);
    testPackageListener.packageOpened(null);
    testPackageListener.packageOpened(null);
    testPackageListener.packageOpened(null);
    assertEquals(4,testPackageListener.getNumberOfPackagesOpened());
  }

  @Test
  void TestForpackageClosed(){
    testPackageListener = new PackageDataCollection();
    testPackageListener.packageClosing(null);
    testPackageListener.packageClosing(null);
    assertEquals(2,testPackageListener.getNumberOfPackagesClosed());
  }
  
}
