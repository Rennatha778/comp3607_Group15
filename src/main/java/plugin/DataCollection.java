package plugin;

public class DataCollection implements DataContainer{
  AdapterInterface adapterData;
  public DataCollection(AdapterInterface metrics){
    this.adapterData = metrics;
  }
  
  public DataIterator getDataIterator(int iteratorCode){
    if(iteratorCode == 0)
      return new ByKeyIterator(adapterData);
    else
      return new ByValueIterator(adapterData);
  }
}
