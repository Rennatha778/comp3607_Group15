package plugin.Iterators.java;

import plugin.Adapters.java.AdapterInterface;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Iterator;

/**
 * This DataITerator subclass provides the functionality to traverse through
 * a TreeMap by Key (a String).
 */
public class ByKeyIterator implements DataIterator{
  private TreeMap keyTreeMap;
  private Iterator<String>keyIterator;

  /**
   * Constructor initalises the class TreeMap (keyTreeMap) 
   * and the iterator (keyIterator) which will be used to iterate
   *  through the TreeMap's keys
   * @param treeMapKey Adapter class storing specific TreeMap Type
   */
  public ByKeyIterator(AdapterInterface treeMapKey){  
    this.keyTreeMap = treeMapKey.getMap();

    ArrayList<String> dataByName = new ArrayList<String>(this.keyTreeMap.keySet());
    keyIterator = dataByName.iterator();
  }
  
  /**
   * The method determines if there is another key entry in the TreeMap
   * @return True/false depending on if there is another key in the TreeMap
   */
  public boolean hasNext(){
    return keyIterator.hasNext();
  }

  /**
   * The method gets the next key in the TreeMap
   * @return The next key in the TreeMap
   */
  public String next(){
    return keyIterator.next();
  }
}
