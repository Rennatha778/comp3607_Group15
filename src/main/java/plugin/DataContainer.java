package plugin;

/**
 * Interface used to implement Iterator design pattern 
 * Subclasses that implemnt this interface contain a TreeMap to be iterated through
 */
public interface DataContainer {

  /**
   * This method returns a subclass of a DataIterator (either ByKeyIterator or ByValueIterator)
   */
  public DataIterator getDataIterator(int iteratorCode);
}
