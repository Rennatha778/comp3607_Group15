package plugin;

import java.util.TreeMap;

public class TreeAdapter implements AdapterInterface {
  TreeMap<String,TreeMap<String,Long>> dataMap;
  public TreeAdapter(TreeMap<String,TreeMap<String,Long>> o){
      dataMap = o;
  }

  public TreeMap getMap(){
    return dataMap;
  }
}
