package plugin;

import java.util.TreeMap;

public class TreeAdapter implements AdapterInterface {
  TreeMap<String,TreeMap<String,Long>> TreeMapDataTreeMap;
  public TreeAdapter(TreeMap<String,TreeMap<String,Long>> treeMapTreeMap){
      TreeMapDataTreeMap = treeMapTreeMap;
  }

  public TreeMap getMap(){
    return TreeMapDataTreeMap;
  }
}
