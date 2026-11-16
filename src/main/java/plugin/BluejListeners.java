package plugin;

/**
 * An interface implemented by BlueJ Listeners.
 * Its role is to causes each of its subclasses to return a Data class which stores metric data pertaining to the BlueJ event that it listens for.
 */
public interface BluejListeners {
  /**
   * The method is used to populate a Data class with listener specific metrics from BlueJ events.
   * @return a populated Data class
   */
  public Data getMetricData();
}
