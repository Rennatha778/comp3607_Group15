package plugin;

import java.util.TreeMap;

/**
 * Specific adapter class used to store and return a TreeMap of type {@code TreeMap<String,Long>}.
 */
public class LongAdapter implements AdapterInterface{
  /**
   * TreeMap that will store the specific TreeMap type {@code (TreeMap<String,Long>)} passed in by calling class
   */
  TreeMap<String,Long> longDataTreeMap;

  /**
   * Constructor to initialise class TreeMap. 
   * @param longTreeMap A TreeMap that has a key of type String and a value of type Long
   */
  public LongAdapter(TreeMap<String,Long> longTreeMap){
      longDataTreeMap = longTreeMap;
  }
  
  /**
   * This method returns the class TreeMap as a generic type
   */
  public TreeMap getMap(){
    return longDataTreeMap;
  }
}
