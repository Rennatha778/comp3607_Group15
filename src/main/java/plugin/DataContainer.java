package plugin;

/**
 * Interface used to represent the Iterator design pattern 
 * Subclasses that implement this interface contain a TreeMap to be iterated through
 */
public interface DataContainer {

  /**
   * This method returns an instantiated subclass of DataIterator (either ByKeyIterator or ByValueIterator)
   * @param iteratorCode digit that specifies the iterator(0 - ByKeyIterator, 1 - ByValueIterator)
   * @return an instantiated subclass of DataIterator
   */
  public DataIterator getDataIterator(int iteratorCode);
}
