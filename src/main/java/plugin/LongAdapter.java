package plugin;

import java.util.TreeMap;

/**
 * Specific adapter class used to store and return a TreeMap<String,Long>.
 */
public class LongAdapter implements AdapterInterface{
  /**
   * TreeMap that will store the specific TreeMap type (TreeMap<String,Long>) passed in by calling class
   */
  TreeMap<String,Long> longDataTreeMap;

  /**
   * Constructor to initialise TreeMap variable with the specific TreeMap type passes in by the user.
   */
  public LongAdapter(TreeMap<String,Long> longTreeMap){
      longDataTreeMap = longTreeMap;
  }
  
  public TreeMap getMap(){
    return longDataTreeMap;
  }
}
