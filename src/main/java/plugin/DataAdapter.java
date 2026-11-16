package plugin;

import java.util.TreeMap;

/**
 * Specific adapter class used to store and return a TreeMap<String,Data>.
 */
public class DataAdapter implements AdapterInterface{
  /**
   * TreeMap that will store the specific TreeMap type (TreeMap<String,Data>) passed in by calling class
   */
  TreeMap<String,Data> DataTreeMap;

  /**
   * Constructor to initialise variable with the specific TreeMap type passes in by the user.
   */
  public DataAdapter(TreeMap<String,Data> dataTreeMap){
      DataTreeMap = dataTreeMap;
  }

  public TreeMap getMap(){
    return DataTreeMap;
  }
}
