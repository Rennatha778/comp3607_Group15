package plugin;

import java.util.TreeMap;

public class LongAdapter implements AdapterInterface{
  TreeMap<String,Long> dataMap;
  public LongAdapter(TreeMap<String,Long> o){
      dataMap = o;
  }
  
  public TreeMap getMap(){
    return dataMap;
  }
}
