package plugin;
import java.util.TreeMap;

public class Data{
  TreeMap<String,Long> longDataTreeMap;
  TreeMap<String,Integer> intDataTreeMap;
  TreeMap<String,TreeMap<String,Long>>objectCreationTreeMap;

  public Data(){
    longDataTreeMap = new TreeMap<String,Long>();
    intDataTreeMap = new TreeMap<String,Integer>();
    objectCreationTreeMap = new TreeMap<String,TreeMap<String,Long>>();
  }

  public void addLongData(String metricName, Long metricLongValue){
    longDataTreeMap.put(metricName,metricLongValue);
  }

  public void addIntData(String metricName, Integer metricIntValue){
    intDataTreeMap.put(metricName,metricIntValue);
  }

  public void addObjectCreationData(String metricName, TreeMap<String,Long> metricTreeMapValue){
    objectCreationTreeMap.put(metricName,metricTreeMapValue);
  }

  public TreeMap<String,Long> getLongTreeMap(){
    return longDataTreeMap;
  }

  public TreeMap<String,Integer> getIntTreeMap(){
    return intDataTreeMap;
  }

  public TreeMap<String,TreeMap<String,Long>> getObjectCreationMap(){
    return objectCreationTreeMap;
  }
}
