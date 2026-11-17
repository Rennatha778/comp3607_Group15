package plugin;

/**
 * This class contains the TreeMap (using the AdapterInterface) to be iterated through.
 *  It also contains a link to acces an intantiated Data iterator (either by key or by value)
 */
public class DataCollection implements DataContainer{
  AdapterInterface adapterData;
  public DataCollection(AdapterInterface metrics){
    this.adapterData = metrics;
  }
  
  /**
   * The method returns an intantiated Data iterator 
   * @param iteratorCode digit that intantiates a specific iterator(0 - ByKeyIterator, 1 - ByValueIterator)
   */
  public DataIterator getDataIterator(int iteratorCode){
    if(iteratorCode == 0)
      return new ByKeyIterator(adapterData);
    else
      return new ByValueIterator(adapterData);
  }
}
