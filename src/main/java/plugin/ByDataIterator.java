package plugin;

import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ByDataIterator implements DataIterator {
  private List dataByName;
  private TreeMap data;
  private Iterator nameIterator;

  public ByDataIterator(AdapterInterface data){
    if(data instanceof DataAdapter){
      this.data = (TreeMap<String,Data>)data.getMap();
      dataByName = new ArrayList<Data>(this.data.values());

    }else if (data instanceof IntAdapter){
      this.data = (TreeMap<String,Integer>)data.getMap();
      dataByName = new ArrayList<Integer>(this.data.values());

    }else if(data instanceof LongAdapter){
      this.data = (TreeMap<String,Long>)data.getMap();
      dataByName = new ArrayList<Long>(this.data.values());

    }
  
    nameIterator = dataByName.iterator();
  }
  public boolean hasNext(){
    return nameIterator.hasNext();
  }

  public Object next(){
    return nameIterator.next();
  }
}
