package plugin;

import java.util.TreeMap;

public class IntAdapter implements AdapterInterface{
  TreeMap<String,Integer> intDataTreeMap;
  public IntAdapter(TreeMap<String,Integer> intTreeMap){
      intDataTreeMap = intTreeMap;
  }

  public TreeMap getMap(){
    return intDataTreeMap;
  }
}
