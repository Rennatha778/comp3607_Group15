package plugin;

import java.util.TreeMap;

public class TreeMapTraverser {
  public Object getMetricValue(TreeMap<String,Data> listenersMetricsTreeMap, String metricName){
    boolean found = false;
    Object foundMetric = null;
   DataContainer metricNames = new DataCollection(new DataAdapter(listenersMetricsTreeMap));

   DataIterator nameIterator = metricNames.getDataIterator(0);
   DataIterator dataIterator = metricNames.getDataIterator(1);

   while(nameIterator.hasNext() && !found){
    String dataName = (String)nameIterator.next();
    //System.out.println(dataName);

     Data data = (Data)dataIterator.next();

     if(dataName.equals(metricName)){
          found = true;
          foundMetric = (Object) data;
      }

     DataContainer subMetricInt = new DataCollection(new IntAdapter(data.getIntTreeMap()));
     DataCollection subMetricLong = new DataCollection(new LongAdapter(data.getLongTreeMap()));
     DataContainer objectionCreation = new DataCollection(new TreeAdapter(data.getObjectCreationMap()));
      

     DataIterator subNameIntIterator = subMetricInt.getDataIterator(0);
     DataIterator subDataIntIterator = subMetricInt.getDataIterator(1);
      
      
     while(subNameIntIterator.hasNext()&& !found){
       String intlable = (String)subNameIntIterator.next();

       int intdata = ((int)subDataIntIterator.next());

       if(intlable.equals(metricName)){
          found = true;
          foundMetric = intdata;
        }
    
     }

     DataIterator subNameLongIterator = subMetricLong.getDataIterator(0);
     DataIterator subDataLongIterator = subMetricLong.getDataIterator(1);
     while(subNameLongIterator.hasNext() && !found){
       String longlable = (String)subNameLongIterator.next();
      
       long longdata = (long)subDataLongIterator.next();
       if(longlable.equals(metricName)){
          found = true;
          foundMetric = longdata;
        }
      
     }

     DataIterator subOuterOCNameIterator = objectionCreation.getDataIterator(0);
     DataIterator subOuterTreeMapIterator = objectionCreation.getDataIterator(1);

     while(subOuterOCNameIterator.hasNext() && !found){
      String treeName = (String)subOuterOCNameIterator.next();
      //System.out.println("Outer OC Key:"+treeName);

      TreeMap<String, TreeMap<String,Long>> innerTreeMapCast = (TreeMap<String, TreeMap<String,Long>>) subOuterTreeMapIterator.next();
      if(treeName.equals(metricName)){
          found = true;
          foundMetric = (Object)innerTreeMapCast;
      }
       
       DataContainer innerTreeMap = new DataCollection(new TreeAdapter(innerTreeMapCast));

       DataIterator innerTreeMapNameIterator = innerTreeMap.getDataIterator(0);
       DataIterator innerTreeMapTreeIterator = innerTreeMap.getDataIterator(1);

        
       while(innerTreeMapNameIterator.hasNext() && !found){
         String Key = (String) innerTreeMapNameIterator.next();
         //System.out.println(Key);
        
         long value = (long)innerTreeMapTreeIterator.next();
         //System.out.println(String.valueOf(value));
         if(Key.equals(metricName)){
          found = true;
          foundMetric = value;
        }
       }
     }
    
   }
   return foundMetric;
  }
}
