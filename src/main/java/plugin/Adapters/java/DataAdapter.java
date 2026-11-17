package plugin.Adapters.java;

import java.util.TreeMap;

import plugin.Data;


/**
 * This is a specific adapter class used to store and return a TreeMap of type {@code TreeMap<String,Data>}.
 */
public class DataAdapter implements AdapterInterface{
  /**
   * TreeMap that will store the specific TreeMap type {@code (TreeMap<String,Data>)} passed in by calling class
   */
  TreeMap<String,Data> DataTreeMap;

  /**
   * Constructor to initialise class TreeMap. 
   * @param dataTreeMap A TreeMap that has a key of type String and a value of type Data
   */
  public DataAdapter(TreeMap<String,Data> dataTreeMap){
      DataTreeMap = dataTreeMap;
  }

  /**
   * This method returns the class TreeMap as a generic type
   */
  public TreeMap getMap(){
    return DataTreeMap;
  }
}
