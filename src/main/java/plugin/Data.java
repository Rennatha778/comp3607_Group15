package plugin;
import java.util.TreeMap;

/**
 * This class is used to store and retrieve listener specific
 * event metric data which are stored in TreeMaps.
 */
public class Data{
  TreeMap<String,Long> longDataTreeMap;
  TreeMap<String,Integer> intDataTreeMap;
  TreeMap<String,TreeMap<String,Long>>objectCreationTreeMap;

  /**
   * This constructor initialises the TreeMaps
   */
  public Data(){
    longDataTreeMap = new TreeMap<String,Long>();
    intDataTreeMap = new TreeMap<String,Integer>();
    objectCreationTreeMap = new TreeMap<String,TreeMap<String,Long>>();
  }

  /**
   * This method add a metric value of type Long and its name of type String 
   * @param metricName The name of the metric 
   * @param metricLongValue The value of the metric as type long
   */
  public void addLongData(String metricName, Long metricLongValue){
    longDataTreeMap.put(metricName,metricLongValue);
  }

  /**
   * This method add a metric value of type Integer and its name of type String
   * @param metricName The name of the metric 
   * @param metricIntValue The value of the metric as type Integer
   */
  public void addIntData(String metricName, Integer metricIntValue){
    intDataTreeMap.put(metricName,metricIntValue);
  }

  /**
   * This method add a metric value of type {@code TreeMap<String,Long>} and its name of type String
   * @param metricName The name of the metric 
   * @param metricTreeMapValue The value of the metric as type {@code TreeMap<String,Long>}
   */
  public void addObjectCreationData(String metricName, TreeMap<String,Long> metricTreeMapValue){
    objectCreationTreeMap.put(metricName,metricTreeMapValue);
  }

  /**
   * This method returns the TreeMap of type {@code TreeMap<String,Long>}
   * @return {@code TreeMap<String,Long>}
   */
  public TreeMap<String,Long> getLongTreeMap(){
    return longDataTreeMap;
  }

  /**
   * This method returns the TreeMap of type {@code TreeMap<String,Integer>}
   * @return {@code TreeMap<String,Integer>}
   */
  public TreeMap<String,Integer> getIntTreeMap(){
    return intDataTreeMap;
  }

  /**
   * This method returns the TreeMap of type {@code TreeMap<String,TreeMap<String,Long>}
   * @return {@code TreeMap<String,TreeMap<String,Long>>}
   */
  public TreeMap<String,TreeMap<String,Long>> getObjectCreationMap(){
    return objectCreationTreeMap;
  }
}
