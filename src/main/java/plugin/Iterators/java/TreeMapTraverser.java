package plugin.Iterators.java;

import java.util.TreeMap;

import plugin.Data;
import plugin.Adapters.java.DataAdapter;
import plugin.Adapters.java.IntAdapter;
import plugin.Adapters.java.LongAdapter;
import plugin.Adapters.java.TreeAdapter;

/**
 * This class contains a method that traverses through a TreeMap 
 * to return a key's value in the map.
 */
public class TreeMapTraverser {
  /**
   * The method searches through a TreeMap which has a key of type String and a value of type Data.
   * The method returns the value for the key entered by the user if found.
   * If the key is not found, null is returned.
   * The TreeMap is travered using the Iterator and Adapter design Patterns. 
   * @param listenersMetricsTreeMap Populated TreeMap with the class specific TreeMaps from listener classes.
   * @param metricName The metric value being searched for
   */
  public Object getMetricValue(TreeMap<String,Data> listenersMetricsTreeMap, String metricName){
    boolean metricValueFound = false;
    Object foundMetricValue = null;

    DataContainer metricNames = new DataCollection(new DataAdapter(listenersMetricsTreeMap));

    DataIterator nameIterator = metricNames.getDataIterator(0);
    DataIterator dataIterator = metricNames.getDataIterator(1);
   //traverses outer TreeMap (TreeMap<String,Data>)
   while(nameIterator.hasNext() && !metricValueFound){
     String dataName = (String)nameIterator.next();

     Data data = (Data)dataIterator.next();

     if(dataName.equals(metricName)){
          metricValueFound = true;
          foundMetricValue = (Object) data;
      }

     DataContainer subMetricInt = new DataCollection(new IntAdapter(data.getIntTreeMap()));
     DataContainer subMetricLong = new DataCollection(new LongAdapter(data.getLongTreeMap()));
     DataContainer objectionCreation = new DataCollection(new TreeAdapter(data.getObjectCreationMap()));
      

     DataIterator subNameIntIterator = subMetricInt.getDataIterator(0);
     DataIterator subDataIntIterator = subMetricInt.getDataIterator(1);
      
     //traverses inner class specific Integer TreeMaps (TreeMap<String,Integer>)
     while(subNameIntIterator.hasNext()&& !metricValueFound){
       String intlable = (String)subNameIntIterator.next();

       int intdata = ((int)subDataIntIterator.next());

       if(intlable.equals(metricName)){
          metricValueFound = true;
          foundMetricValue = intdata;
        }
     }

     DataIterator subNameLongIterator = subMetricLong.getDataIterator(0);
     DataIterator subDataLongIterator = subMetricLong.getDataIterator(1);

     //traverses inner class specific Long TreeMaps (TreeMap<String,Long>)
     while(subNameLongIterator.hasNext() && !metricValueFound){
       String longlable = (String)subNameLongIterator.next();
      
       long longdata = (long)subDataLongIterator.next();
       if(longlable.equals(metricName)){
          metricValueFound = true;
          foundMetricValue = longdata;
        }
      
     }

     DataIterator subOuterOCNameIterator = objectionCreation.getDataIterator(0);
     DataIterator subOuterTreeMapIterator = objectionCreation.getDataIterator(1);

     //traverses inner class specific ObjectCreation TreeMaps (TreeMap<String,TreeMap<String,Long>>)
     while(subOuterOCNameIterator.hasNext() && !metricValueFound){
      String treeName = (String)subOuterOCNameIterator.next();

      TreeMap<String, TreeMap<String,Long>> innerTreeMapCast = (TreeMap<String, TreeMap<String,Long>>) subOuterTreeMapIterator.next();
      if(treeName.equals(metricName)){
          metricValueFound = true;
          foundMetricValue = (Object)innerTreeMapCast;
      }
       
       DataContainer innerTreeMap = new DataCollection(new TreeAdapter(innerTreeMapCast));

       DataIterator innerTreeMapNameIterator = innerTreeMap.getDataIterator(0);
       DataIterator innerTreeMapTreeIterator = innerTreeMap.getDataIterator(1);

       //traverses ObjectCreation TreeMap specific inner Long TreeMaps (TreeMap<String,Long>)
       while(innerTreeMapNameIterator.hasNext() && !metricValueFound){
         String Key = (String) innerTreeMapNameIterator.next();
        
         long value = (long)innerTreeMapTreeIterator.next();
         if(Key.equals(metricName)){
          metricValueFound = true;
          foundMetricValue = value;
        }
       }
     }
    
   }
   return foundMetricValue;
  }
}
