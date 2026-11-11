package plugin;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Iterator;

public class ByKeyIterator implements DataIterator{
  private TreeMap keyTreeMap;
  private Iterator<String>keyIterator;

  public ByKeyIterator(AdapterInterface treeMapKey){
    if(treeMapKey instanceof DataAdapter){
      this.keyTreeMap = (TreeMap<String,Data>)treeMapKey.getMap();

    }else if (treeMapKey instanceof IntAdapter){
      this.keyTreeMap = (TreeMap<String,Integer>)treeMapKey.getMap();

    }else if(treeMapKey instanceof LongAdapter){
      this.keyTreeMap = (TreeMap<String,Long>)treeMapKey.getMap();

    }else if(treeMapKey instanceof TreeAdapter){
      this.keyTreeMap = (TreeMap<String,TreeMap<String,TreeMap>>) treeMapKey.getMap();
    }
    
    ArrayList<String> dataByName = new ArrayList<String>(this.keyTreeMap.keySet());
    //Collections.sort(dataByName);
    keyIterator = dataByName.iterator();
  }

  public boolean hasNext(){
    return keyIterator.hasNext();
  }

  public String next(){
    return keyIterator.next();
  }
}
