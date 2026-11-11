package plugin;

import java.util.TreeMap;

public class DataAdapter implements AdapterInterface{
  TreeMap<String,Data> DataTreeMap;
  public DataAdapter(TreeMap<String,Data> dataTreeMap){
      DataTreeMap = dataTreeMap;
  }

  public TreeMap getMap(){
    return DataTreeMap;
  }
}
