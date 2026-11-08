package plugin;

import java.util.TreeMap;

public class DataCollection implements DataContainer{
  AdapterInterface data;
  public DataCollection(AdapterInterface data){
    this.data = data;
  }
  
  public DataIterator getDataIterator(int code){
    if(code == 0)
      return new ByNameIterator(data);
    else
      return new ByDataIterator(data);
  }
}
