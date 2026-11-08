package plugin;

import java.util.TreeMap;

public class DataAdapter implements AdapterInterface{
  TreeMap<String,Data> dataMap;
  public DataAdapter(TreeMap<String,Data> o){
      dataMap = o;
  }

  public TreeMap getMap(){
    return dataMap;
  }
}
