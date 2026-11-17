package plugin.Iterators.java;

import plugin.Adapters.java.AdapterInterface;

/**
 * This class contains the TreeMap (using the AdapterInterface) that will be iterated through.
 *  It also contains a link to acces an initialised Data iterator (either by key or by value)
 */
public class DataCollection implements DataContainer{
  /**
   * This constructor initialises the AdapterInterface variable with a 
   * subclass instance.
   * @param metrics An instantiated subclass of AdapterInterface
   */
  AdapterInterface adapterData;
  public DataCollection(AdapterInterface metrics){
    this.adapterData = metrics;
  }
  
  /**
   * The method returns an initialised Data iterator 
   * @param iteratorCode digit that specifies the iterator(0 - ByKeyIterator, 1 - ByValueIterator)
   * @return instantiated DataIterator subclass
   */
  public DataIterator getDataIterator(int iteratorCode){
    if(iteratorCode == 0)
      return new ByKeyIterator(adapterData);
    else
      return new ByValueIterator(adapterData);
  }
}
