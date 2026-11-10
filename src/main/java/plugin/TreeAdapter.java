package plugin;

import java.util.TreeMap;

public class TreeAdapter {
  TreeMap<String,TreeMap> dataMap;
  public TreeAdapter(TreeMap<String,TreeMap> o){
      dataMap = o;
  }

  public TreeMap getMap(){
    return dataMap;
  }
}
