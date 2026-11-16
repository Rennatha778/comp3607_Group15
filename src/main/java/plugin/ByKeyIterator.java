package plugin;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Iterator;

public class ByKeyIterator implements DataIterator{
  private TreeMap keyTreeMap;
  private Iterator<String>keyIterator;

  /**
   * Constructor initalises the class TreeMap (keyTreeMap) and the iterator (keyIterator) which will be used iterated through the TreeMap's keys
   */
  public ByKeyIterator(AdapterInterface treeMapKey){
    /* if(treeMapKey instanceof DataAdapter){
      this.keyTreeMap = (TreeMap<String,Data>)treeMapKey.getMap();

    }else if (treeMapKey instanceof IntAdapter){
      this.keyTreeMap = (TreeMap<String,Integer>)treeMapKey.getMap();

    }else if(treeMapKey instanceof LongAdapter){
      this.keyTreeMap = (TreeMap<String,Long>)treeMapKey.getMap();

    }else if(treeMapKey instanceof TreeAdapter){
      this.keyTreeMap = (TreeMap<String,TreeMap<String,TreeMap>>) treeMapKey.getMap();
    } */
    
    this.keyTreeMap = treeMapKey.getMap();

    ArrayList<String> dataByName = new ArrayList<String>(this.keyTreeMap.keySet());
    keyIterator = dataByName.iterator();
  }
  
  /**
   * The method determines if there is another key entry in the TreeMap
   * @return True  or false depending on if there is another key in the TreeMap
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
