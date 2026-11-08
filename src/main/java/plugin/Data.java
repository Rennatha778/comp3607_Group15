package plugin;
import java.util.TreeMap;

public class Data{
  TreeMap<String,Long> longData;
  TreeMap<String,Integer> intData;

  public Data(){
    longData = new TreeMap<String,Long>();
    intData = new TreeMap<String,Integer>();
  }

  public void addLongData(String key, Long value){
    longData.put(key,value);
  }

  public void addIntData(String key, Integer value){
    intData.put(key,value);
  }

  public TreeMap<String,Long> getLongTreeMap(){
    return longData;
  }

  public TreeMap<String,Integer> getIntTreeMap(){
    return intData;
  }
}
