package plugin.EventListeners.java;

import plugin.Data;

/**
 * An interface implemented by all plugin BlueJ Listeners.
 * Its role is to causes each subclasses to return a Data 
 * class which stores the metric data pertaining to the specific event 
 * that it listens for.
 */
public interface BluejListeners {
  /**
   * The method is used to populate a Data class with listener 
   * specific metrics from a BlueJ session.
   * @return a populated Data class
   */
  public Data getMetricData();
}
