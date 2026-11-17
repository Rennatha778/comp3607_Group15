package plugin;

import java.util.TreeMap;

/**
 * Specific adapter class used to store and return a TreeMap of type TreeMap<String,Integer>.
 */
public class IntAdapter implements AdapterInterface{
  /**
   * TreeMap that will store the specific TreeMap type (TreeMap<String,Integer>) passed in by calling class
   */
  TreeMap<String,Integer> intDataTreeMap;

   /**
   * Constructor to initialise class TreeMap. 
   * @param intTreeMap A TreeMap that has a key of type String and a value of type Integer
   */
  public IntAdapter(TreeMap<String,Integer> intTreeMap){
      intDataTreeMap = intTreeMap;
  }
 
  /**
   * This method returns the stored TreeMap as a generic type
   */
  public TreeMap getMap(){
    return intDataTreeMap;
  }
}
