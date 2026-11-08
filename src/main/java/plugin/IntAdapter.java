package plugin;

import java.util.TreeMap;

public class IntAdapter implements AdapterInterface{
  TreeMap<String,Integer> dataMap;
  public IntAdapter(TreeMap<String,Integer> o){
      dataMap = o;
  }

  public TreeMap getMap(){
    return dataMap;
  }
}
