package plugin;
import java.util.TreeMap;

/**
 * Interface used in representing the Adapter design pattern.
 * Implementing subclasses store a specific TreeMap type and returns the data in a generic TreeMap type.
 */
public interface AdapterInterface {
  /**
   *Returns the specific TreeMap type stored as a generic TreeMap
   * @return specific TreeMap type as a generic TreeMap
   */
  public TreeMap getMap();
}
