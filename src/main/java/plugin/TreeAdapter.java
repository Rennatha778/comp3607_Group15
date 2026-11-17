package plugin;

import java.util.TreeMap;

/**
 * Specific adapter class used to store and return a TreeMap of type TreeMap<String,TreeMap<String,Long>>.
 */
public class TreeAdapter implements AdapterInterface {
  /**
   * TreeMap that will store the specific TreeMap type (TreeMap<String,TreeMap<String,Long>>) passed in by calling class
   */
  TreeMap<String,TreeMap<String,Long>> TreeMapDataTreeMap;
  
  /**
   * Constructor to initialise class TreeMap. 
   * @param treeTreeMap A TreeMap that has a key of type String and a value of type TreeMap<String,Long>
   */
  public TreeAdapter(TreeMap<String,TreeMap<String,Long>> treeMapTreeMap){
      TreeMapDataTreeMap = treeMapTreeMap;
  }

  /**
   * This method returns the class TreeMap as a generic type
   */
  public TreeMap getMap(){
    return TreeMapDataTreeMap;
  }
}
