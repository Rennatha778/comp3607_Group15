package plugin;

import java.util.TreeMap;

/**
 * Specific adapter class used to store and return a TreeMap<String,TreeMap<String,Long>>.
 */
public class TreeAdapter implements AdapterInterface {
  /**
   * TreeMap that will store the specific TreeMap type (TreeMap<String,TreeMap<String,Long>>) passed in by calling class
   */
  TreeMap<String,TreeMap<String,Long>> TreeMapDataTreeMap;
  
  /**
   * Constructor to initialise the TreeMap variable with the specific TreeMap type passes in by the user.
   */
  public TreeAdapter(TreeMap<String,TreeMap<String,Long>> treeMapTreeMap){
      TreeMapDataTreeMap = treeMapTreeMap;
  }

  public TreeMap getMap(){
    return TreeMapDataTreeMap;
  }
}
