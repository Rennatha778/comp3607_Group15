package plugin;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Iterator;

public class ByNameIterator implements DataIterator{
  private TreeMap data;
  private Iterator<String>nameIterator;

  public ByNameIterator(AdapterInterface data){
    if(data instanceof DataAdapter){
      this.data = (TreeMap<String,Data>)data.getMap();

    }else if (data instanceof IntAdapter){
      this.data = (TreeMap<String,Integer>)data.getMap();

    }else if(data instanceof LongAdapter){
      this.data = (TreeMap<String,Long>)data.getMap();

    }
    
    ArrayList<String> dataByName = new ArrayList<String>(this.data.keySet());
    //Collections.sort(dataByName);
    nameIterator = dataByName.iterator();
  }

  public boolean hasNext(){
    return nameIterator.hasNext();
  }

  public String next(){
    return nameIterator.next();
  }
}
