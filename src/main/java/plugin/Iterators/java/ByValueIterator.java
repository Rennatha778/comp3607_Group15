package plugin.Iterators.java;

import java.util.TreeMap;
import plugin.Adapters.java.AdapterInterface;
import plugin.Adapters.java.DataAdapter;
import plugin.Adapters.java.TreeAdapter;
import plugin.Adapters.java.LongAdapter;
import plugin.Adapters.java.IntAdapter;
import plugin.Data;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This DataITerator subclass provides the functionality to traverse through
 * a TreeMap by value (either a Integer, Long or {@code TreeMap<String,Long>)}.
 */
public class ByValueIterator implements DataIterator {
  private List dataByValue;
  private TreeMap valueTreeMap;
  private Iterator valueIterator;

  /**
   * Constructor determines the specific value type of the TreeMap.
   * It then intialises the TreeMap (valueTreeMap) and the specific iterator (valueIterator).
   * These will be used to iterate through the TreeMap's values.
   * @param treeMapValue Adapter class storing specific TreeMap Type
   */
  public ByValueIterator(AdapterInterface treeMapValue){
    if(treeMapValue instanceof DataAdapter){
      this.valueTreeMap = (TreeMap<String,Data>)treeMapValue.getMap();
      dataByValue = new ArrayList<Data>(this.valueTreeMap.values());

    }else if (treeMapValue instanceof IntAdapter){
      this.valueTreeMap = (TreeMap<String,Integer>)treeMapValue.getMap();
      dataByValue = new ArrayList<Integer>(this.valueTreeMap.values());

    }else if(treeMapValue instanceof LongAdapter){
      this.valueTreeMap = (TreeMap<String,Long>)treeMapValue.getMap();
      dataByValue = new ArrayList<Long>(this.valueTreeMap.values());

    }else if(treeMapValue instanceof TreeAdapter){
      this.valueTreeMap = (TreeMap<String,TreeMap<String,Long>>)treeMapValue.getMap();
      dataByValue = new ArrayList<TreeMap<String,Long>>(this.valueTreeMap.values()); 
    }
  
    valueIterator = dataByValue.iterator();
  }

  /**
   * The method determines if there is another value entry in the TreeMap
   * @return True/false depending on if there is another value entry in the TreeMap
   */
  public boolean hasNext(){
    return valueIterator.hasNext();
  }

  /**
   * The method gets the next value entry in the TreeMap
   * @return The next value entry in the TreeMap
   */
  public Object next(){
    return valueIterator.next();
  }
}
