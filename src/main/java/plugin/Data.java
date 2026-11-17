package plugin;
import java.util.TreeMap;

/**
 * This class is used to store and retrieve event listener metric data stored in TreeMaps
 */
public class Data{
  TreeMap<String,Long> longDataTreeMap;
  TreeMap<String,Integer> intDataTreeMap;
  TreeMap<String,TreeMap<String,Long>>objectCreationTreeMap;

  /**
   * This constructor intantiates TreeMaps
   */
  public Data(){
    longDataTreeMap = new TreeMap<String,Long>();
    intDataTreeMap = new TreeMap<String,Integer>();
    objectCreationTreeMap = new TreeMap<String,TreeMap<String,Long>>();
  }

  /**
   * This Method add metric of type Long and its name of type String 
   */
  public void addLongData(String metricName, Long metricLongValue){
    longDataTreeMap.put(metricName,metricLongValue);
  }

  /**
   * This Method add metric of type Integer and its name of type String 
   */
  public void addIntData(String metricName, Integer metricIntValue){
    intDataTreeMap.put(metricName,metricIntValue);
  }

  /**
   * This Method add metric of type TreeMap<String,Long> and its name of type String 
   */
  public void addObjectCreationData(String metricName, TreeMap<String,Long> metricTreeMapValue){
    objectCreationTreeMap.put(metricName,metricTreeMapValue);
  }

  /**
   * This method returns the TreeMap of type TreeMap<String,Long>
   */
  public TreeMap<String,Long> getLongTreeMap(){
    return longDataTreeMap;
  }

  /**
   * This method returns the TreeMap of type TreeMap<String,Integer>
   */
  public TreeMap<String,Integer> getIntTreeMap(){
    return intDataTreeMap;
  }

  /**
   * This method returns the TreeMap of type TreeMap<String,TreeMap<String,Long>>
   */
  public TreeMap<String,TreeMap<String,Long>> getObjectCreationMap(){
    return objectCreationTreeMap;
  }
}
