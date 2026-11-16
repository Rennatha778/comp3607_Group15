package plugin;

import java.util.TreeMap;

/**
 * Specific adapter class used to store and return a TreeMap<String,Integer>.
 */
public class IntAdapter implements AdapterInterface{
  /**
   * TreeMap that will store the specific TreeMap type (TreeMap<String,Integer>) passed in by calling class
   */
  TreeMap<String,Integer> intDataTreeMap;
   /**
   * Constructor to initialise TreeMap variable with the specific TreeMap type passes in by the user.
   */
  public IntAdapter(TreeMap<String,Integer> intTreeMap){
      intDataTreeMap = intTreeMap;
  }
 
  public TreeMap getMap(){
    return intDataTreeMap;
  }
}
