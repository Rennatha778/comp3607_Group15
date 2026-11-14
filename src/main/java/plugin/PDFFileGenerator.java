package plugin;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.script.SimpleBindings;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.DefaultIntervalCategoryDataset;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.xy.XYDataset;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.KeyStore.Entry;

import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.jfree.chart.plot.PiePlot;
import java.util.Map;

public class PDFFileGenerator implements ReportPublisher {
  TreeMap<String,Data> dataTreeMap;
  /* ArrayList<String> names = new ArrayList<String>();
  ArrayList<Integer> intData = new ArrayList<Integer>();
  ArrayList<Long> longData = new ArrayList<Long>(); */
  /* long activeTime;
  long idolTime;
  int expectedErrors;
  int symbolErrors;
  int unclosedErros;
  int returnErrors;
  int otherErrors; */

  TreeMapTraverser treeMapTraverser;


public void generateReport(TreeMap<String,Data> listenersMetricsTreeMap){

  dataTreeMap = listenersMetricsTreeMap;
  treeMapTraverser = new TreeMapTraverser();
    

  try (PDDocument pdfDocument = new PDDocument()) {
   PDPage page1 = new PDPage();
   pdfDocument.addPage(page1);

   PDPage page2 = new PDPage();
   pdfDocument.addPage(page2);

   PDPageContentStream pageContent1 = new PDPageContentStream(pdfDocument, page1);
   PDPageContentStream pageContent2 = new PDPageContentStream(pdfDocument, page2);
   
   pageContent1.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER),12);

   String[] headings = {"Metric Name", "Metric Value"};
   String[] metricNames = {"Total_Active_Time","Total_Idle_Time","Total_Session_Time","Total_Active_Typing_Time","Total_Idle_Typing_Time","Average_Compile_Time","Number_of_Methods_Ran","Number_of_Packages_Opened"};
   Object[] metricValues = new Object[metricNames.length];
   metricValues = getMetricValues(metricNames);
   
  Object[] combinedArray = {metricNames,metricValues};
  TableGenerator generateTable = new TableGenerator();
  int newYValue = generateTable.generateTableLong(headings,combinedArray,metricNames.length,pageContent1,50,700);
  
  String[] ratioHeadings = {"Metrics","Ratio"};
  String[] ratioMetricNames = {"Total_Idle_Time","Total_Active_Time"};
  Object[] ratioValues = {4,5};
  Object[] ratioColoumValues = {ratioMetricNames,ratioValues};
  newYValue = generateTable.generateTableLong(ratioHeadings,ratioColoumValues,ratioMetricNames.length,pageContent1,50,newYValue-25);
   pageContent1.close();
   

   DefaultPieDataset<String> sessionPieChar = new DefaultPieDataset<>();
   sessionPieChar.setValue("Total_Active_Typing_Time", (long)treeMapTraverser.getMetricValue(listenersMetricsTreeMap, "Total_Active_Typing_Time"));
   sessionPieChar.setValue("Total_Idle_Typing_Time",(long)treeMapTraverser.getMetricValue(listenersMetricsTreeMap, "Total_Idle_Typing_Time"));

  JFreeChart sessionPieChart = ChartFactory.createPieChart("Session Breakdown",sessionPieChar,true,true,false);
  
  PiePlot plot = (PiePlot) sessionPieChart.getPlot();

  plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {2}", NumberFormat.getNumberInstance(),new DecimalFormat("0.00%")));

  BufferedImage pieCharImage = sessionPieChart.createBufferedImage(800, 600);
      
  PDImageXObject pdfImage = LosslessFactory.createFromImage(pdfDocument,pieCharImage);

  float pieChartYLocation = page2.getMediaBox().getHeight() - 280;
 
  pageContent2.drawImage(pdfImage,50, pieChartYLocation, 300, 225); 

    DefaultCategoryDataset programEventsDataset = new DefaultCategoryDataset();

    programEventsDataset.addValue((int)treeMapTraverser.getMetricValue(dataTreeMap, "Number_of_'Expected'_Errors"),"Error","Expected");
    programEventsDataset.addValue((int)treeMapTraverser.getMetricValue(dataTreeMap, "Number_of_'Return'_Errors"),"Error","Return");
    programEventsDataset.addValue((int)treeMapTraverser.getMetricValue(dataTreeMap, "Number_of_'Symbol'_Errors"),"Error","Symbol");
    programEventsDataset.addValue((int)treeMapTraverser.getMetricValue(dataTreeMap, "Number_of_'Unclosed'_Errors"),"Error","Unclosed");
    programEventsDataset.addValue((int)treeMapTraverser.getMetricValue(dataTreeMap, "Number_of_Other_Errors"),"Error","Others");

  
    JFreeChart barChart = ChartFactory.createBarChart("Error Frequency Breakdown","Frequency", "Error", programEventsDataset);


    BufferedImage errorImage = barChart.createBufferedImage(800, 600);
      
    PDImageXObject barChartImage = LosslessFactory.createFromImage(pdfDocument,errorImage);
    float barChartYLocation = pieChartYLocation - 250;
    pageContent2.drawImage(barChartImage,30, barChartYLocation,300, 225);


    try{
      //long sessionStartTime = (long)treeMapTraverser.getMetricValue(dataTreeMap, "Session_Start_Time");
      long sessionEndTime = (long)treeMapTraverser.getMetricValue(dataTreeMap, "Session_End_Time");
      TaskSeries series = new TaskSeries("Class Creation");
      TreeMap<String,Long>createdObjects = (TreeMap<String,Long>)treeMapTraverser.getMetricValue(dataTreeMap, "Created_Classes");
      TreeMap<String,Long>removedObjects = (TreeMap<String,Long>)treeMapTraverser.getMetricValue(dataTreeMap, "Removed_Classes");
      //IntervalCategoryDataset lineGraphData = new DefaultIntervalCategoryDataset;
      
      for(Map.Entry<String,Long> classDataEntry : createdObjects.entrySet()){
        Long endTime = removedObjects.get(classDataEntry.getKey());
        System.out.println(classDataEntry.getKey()+" :"+classDataEntry.getValue()+" "+endTime);
        if(endTime != null){
          series.add(new Task(classDataEntry.getKey(),new SimpleTimePeriod(classDataEntry.getValue(),endTime)));
        }else{
          series.add(new Task(classDataEntry.getKey(),new SimpleTimePeriod((classDataEntry.getValue()),sessionEndTime)));
        }
        //lineGraphData.addValue((Long)treeMapTraverser.getMetricValue(createdObjects, className),"Time Class Created","ClassName");
        
      }
      
      
      TaskSeriesCollection ganttChartDataset = new TaskSeriesCollection();
      ganttChartDataset.add(series);
      JFreeChart ganttChart = ChartFactory.createGanttChart("Class Creation Chart","Class","Time",ganttChartDataset,true,true,false);
      
      BufferedImage objectCreationImage = ganttChart.createBufferedImage(800, 600);
      
      PDImageXObject objectCreationGantChart = LosslessFactory.createFromImage(pdfDocument,objectCreationImage);
      float ganttChartYLocation = barChartYLocation - 250;
      pageContent2.drawImage(objectCreationGantChart,30, ganttChartYLocation,300, 225);


    }catch(IOException e){
      e.printStackTrace();
    }


    pageContent2.close();

    System.out.println("Charts created.");

    pageContent1.close();

    pdfDocument.save("C:\\Users\\Owner\\Documents\\PluginFiles\\PluginMetricsPDF.pdf");

    System.out.println("PDF created.");
    }
    catch (Exception e) {
      e.printStackTrace();
      System.out.println("PDF file was not created");
    }
  }
  public Object[] getMetricValues(String[] metricNames){
      Object[] metricValues = new Object[metricNames.length];
      for(int i = 0; i < metricNames.length; i++){
        Object val = treeMapTraverser.getMetricValue(dataTreeMap, metricNames[i]);
        System.out.println(val);
      if(val != null){
        metricValues[i] = val;
      }else{
        metricValues[i] = null;
      }
      
    }
    return metricValues;
  }
  public String[] getAllObjectNames(){
    TreeMap<String,Long> allCreatedClass = new TreeMap<String,Long>();
    allCreatedClass = (TreeMap<String,Long>) treeMapTraverser.getMetricValue(dataTreeMap, "Created_Classes");
  
    TreeMap<String,Long> allModifiedClass = new TreeMap<String,Long>();
    allModifiedClass = (TreeMap<String,Long>) treeMapTraverser.getMetricValue(dataTreeMap, "Modified_Classes");
  
    TreeMap<String,Long> allRemovedClass = new TreeMap<String,Long>();
    allRemovedClass = (TreeMap<String,Long>) treeMapTraverser.getMetricValue(dataTreeMap, "Removed_Classes");

    String[] classNames = new String[ allModifiedClass.size() + allRemovedClass.size()];
    int i = 0;
    for(String className : allCreatedClass.keySet()){
      classNames[i] = className;
      i++;
    }

    for(String className : allModifiedClass.keySet()){
      classNames[i] = className;
      i++;
    }

    for(String className : allRemovedClass.keySet()){
      classNames[i] = className;
      i++;
    }

    return classNames;
  }

  private void createObjectCreationTable(){

  }
}

  
