package plugin;

import java.util.TreeMap;

public class LongAdapter implements AdapterInterface{
  TreeMap<String,Long> longDataTreeMap;

  public LongAdapter(TreeMap<String,Long> longTreeMap){
      longDataTreeMap = longTreeMap;
  }
  
  public TreeMap getMap(){
    return longDataTreeMap;
  }
}
