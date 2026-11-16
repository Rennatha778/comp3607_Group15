package plugin;

import java.util.TreeMap;
/**
 * This class contains a getMetricValue method that traverses through a TreeMap to return a key's value in the map.
 */
public class TreeMapTraverser {
  /**
   * The method searches through a TreeMap which has a key of type String and a value of type Data.
   * The method returns the value for the key entered by the user if found.
   * If the key is not found, null is returned.
   * The TreeMap is travered using the Iterator and Adapter design Patterns. 
   */
  public Object getMetricValue(TreeMap<String,Data> listenersMetricsTreeMap, String metricName){
    boolean metricValueFound = false;
    Object foundMetricValue = null;

    DataContainer metricNames = new DataCollection(new DataAdapter(listenersMetricsTreeMap));

    DataIterator nameIterator = metricNames.getDataIterator(0);
    DataIterator dataIterator = metricNames.getDataIterator(1);

   while(nameIterator.hasNext() && !metricValueFound){
     String dataName = (String)nameIterator.next();

     Data data = (Data)dataIterator.next();

     if(dataName.equals(metricName)){
          metricValueFound = true;
          foundMetricValue = (Object) data;
      }

     DataContainer subMetricInt = new DataCollection(new IntAdapter(data.getIntTreeMap()));
     DataCollection subMetricLong = new DataCollection(new LongAdapter(data.getLongTreeMap()));
     DataContainer objectionCreation = new DataCollection(new TreeAdapter(data.getObjectCreationMap()));
      

     DataIterator subNameIntIterator = subMetricInt.getDataIterator(0);
     DataIterator subDataIntIterator = subMetricInt.getDataIterator(1);
      
      
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
