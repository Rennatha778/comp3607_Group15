package plugin.FileGenerators.java;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.TreeMap;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.SimpleTimePeriod;

import plugin.Data;

import java.awt.image.BufferedImage;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.jfree.chart.plot.PiePlot;
import java.util.Map;

/**
 * This class contains a method that generates a PDF file
 */
public class PDFFileGenerator implements ReportGenerationStrategy {
  TreeMap<String,Data> dataTreeMap;
  TreeMapTraverser treeMapTraverser;
  TableGenerator tableGenerator = new TableGenerator();

  float movingYLocation;

  /**
   * This method creates a PDF file using specific data from a TreeMap sent 
   * from the main runner. The report contains two pages. Page one has two 
   * tables. One showing key metrics and the other showing active to idle 
   * time ratio. The second page has three charts. The first is a pie chart 
   * showing active and idle typing times, the second a bar graph show the 
   * frequency of error types, and the third is a gantt chart showing lifespan 
   * of classes that were created during the session. 
   * @param listenersMetricsTreeMap TreeMap containing all event listeners' data TreeMaps
   * @param filePath String variable containing the location where the file is to be stored
   * @return A pdf file 
   */
  public PDDocument generateReport(TreeMap<String,Data> listenersMetricsTreeMap, String filePath){
    dataTreeMap = listenersMetricsTreeMap;
    treeMapTraverser = new TreeMapTraverser();
    
    try{
      PDDocument pdfDocument = new PDDocument();
      PDPage page1 = new PDPage();
      pdfDocument.addPage(page1);

      PDPage page2 = new PDPage();
      pdfDocument.addPage(page2);

      PDPageContentStream pageContent1 = new PDPageContentStream(pdfDocument, page1);
      PDPageContentStream pageContent2 = new PDPageContentStream(pdfDocument, page2);
   
      int newYValue = createKeyMetricsTable(pageContent1);

      createActiveToIdleTimeRatioTable(newYValue,pageContent1);
      
      pageContent1.close();
   
      movingYLocation = createActiveToIdleTypingPieChart(pdfDocument,page2,pageContent2);
   
      movingYLocation = createErrorFrequencyBarChart(pdfDocument,page2,pageContent2,movingYLocation);
      
      movingYLocation = createClassLifeSpanGanttChart(pdfDocument,page2,pageContent2,movingYLocation);

      pageContent2.close();

      System.out.println("Charts created.");

      pdfDocument.save(filePath+"\\PluginMetricsPDF ~"+System.currentTimeMillis()+".pdf");

      System.out.println("PDF created.");
      return pdfDocument;
    }
    catch (Exception e) {
      e.printStackTrace();
      System.out.println("PDF file was not created");
    }
    return null;
  }

  /**
   * This method traverses the TreeMap and stores all the values for
   * the keys passed in a string array in an object array. If the metric 
   * key is not found, a null value is put in its place.
   * @param metricNames String array of all metric keys to be found
   * @return An object array of metric values for each metric name provide or null if not found
   */
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

  /**
   * This method creates and prints the key metrics table on the pdf document.
   * @return the y value at which the next item on the pdf can be placed
   */
  protected int createKeyMetricsTable(PDPageContentStream pageContent1){
    try{
      pageContent1.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER),12);
      pageContent1.beginText();
      pageContent1.newLineAtOffset(50,700);
      pageContent1.showText("Table showing Key Session Metrics ");
      pageContent1.endText();
      
      String[] headings = {"Metric Name", "Metric Value"};
      String[] metricNames = {"Total_Active_Time","Total_Idle_Time","Total_Session_Time","Total_Active_Typing_Time","Total_Idle_Typing_Time","Average_Compile_Time","Number_of_Methods_Ran","Number_of_Packages_Opened"};
      Object[] metricValues = new Object[metricNames.length];
      metricValues = getMetricValues(metricNames);
      Object[] combinedArray = {metricNames,metricValues};
      int newYValue = tableGenerator.generateTable(headings,combinedArray,metricNames.length,pageContent1,50,680);
      return newYValue;
    }catch(IOException e){
      e.printStackTrace();
      return 680;
    }
  }

  /**
   * This method calculates and prints the Active to Idle ratios on the pdf document 
   */
  protected void createActiveToIdleTimeRatioTable(int newYValue, PDPageContentStream pageContent1){
    try{
      pageContent1.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER),12);
      pageContent1.beginText();
      pageContent1.newLineAtOffset(50,newYValue - 45);
      pageContent1.showText("Table showing Active to Idle Ratio ");
      pageContent1.endText();

      String[] ratioHeadings = {"Metrics","Ratio"};
      String[] ratioMetricNames = {"Total_Idle_Time","Total_Active_Time"};
      Object[] ratioValues = {4,5};
      Object[] ratioColoumValues = {ratioMetricNames,ratioValues};
      newYValue = tableGenerator.generateTable(ratioHeadings,ratioColoumValues,ratioMetricNames.length,pageContent1,50,newYValue-55);
    }catch(IOException e){
      e.printStackTrace();
    }
  }

  /**
   * This method creates and draws a pie chart showing Active to Idle typing 
   * time on the pdf.
   * @return the y value at which the next item on the pdf should be placed
   */
  protected float createActiveToIdleTypingPieChart(PDDocument pdfDocument, PDPage page2, PDPageContentStream pageContent2){
    try{
      DefaultPieDataset<String> sessionPieChar = new DefaultPieDataset<>();
      sessionPieChar.setValue("Total_Active_Typing_Time", (long)treeMapTraverser.getMetricValue(dataTreeMap, "Total_Active_Typing_Time"));
      sessionPieChar.setValue("Total_Idle_Typing_Time",(long)treeMapTraverser.getMetricValue(dataTreeMap, "Total_Idle_Typing_Time"));

      JFreeChart sessionPieChart = ChartFactory.createPieChart("Session Breakdown",sessionPieChar,true,true,false);
  
      PiePlot plot = (PiePlot) sessionPieChart.getPlot();

      plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {2}", NumberFormat.getNumberInstance(),new DecimalFormat("0.00%")));

      BufferedImage pieCharImage = sessionPieChart.createBufferedImage(800, 600);
      PDImageXObject pdfImage = LosslessFactory.createFromImage(pdfDocument,pieCharImage);
      float pieChartYLocation = page2.getMediaBox().getHeight() - 280;
   
      pageContent2.drawImage(pdfImage,50, pieChartYLocation, 300, 225); 
      return pieChartYLocation;
    }catch(IOException e){
      e.printStackTrace();
      return page2.getMediaBox().getHeight() - 280;
    }
  }

  /**
   * This method creates and draws a bar chart showing the frequency of certain 
   * types of compile error on the pdf document.
   * @return the y value at which the next item on the pdf should be placed
   */
  protected float createErrorFrequencyBarChart(PDDocument pdfDocument, PDPage page2, PDPageContentStream pageContent2, float pieChartYLocation){
    try{
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

      return barChartYLocation;
    }catch(IOException e){
      e.printStackTrace();
      return pieChartYLocation - 250;
    }
  }

  /**
   * This method creates and draws a gantt chart showing the lifespan of created 
   * classes during the session time on the pdf.
   * @return the y value at which the next item on the pdf should be placed
   */
  protected float createClassLifeSpanGanttChart(PDDocument pdfDocument, PDPage page2, PDPageContentStream pageContent2, float barChartYLocation){
    try{
      long sessionEndTime = (long)treeMapTraverser.getMetricValue(dataTreeMap, "Session_End_Time");
      TaskSeries series = new TaskSeries("Class Creation");
      TreeMap<String,Long>createdObjects = (TreeMap<String,Long>)treeMapTraverser.getMetricValue(dataTreeMap, "Created_Classes");
      TreeMap<String,Long>removedObjects = (TreeMap<String,Long>)treeMapTraverser.getMetricValue(dataTreeMap, "Removed_Classes");
      
      for(Map.Entry<String,Long> classDataEntry : createdObjects.entrySet()){
        Long endTime = removedObjects.get(classDataEntry.getKey());
        System.out.println(classDataEntry.getKey()+" :"+classDataEntry.getValue()+" "+endTime);
        if(endTime != null){
          series.add(new Task(classDataEntry.getKey(),new SimpleTimePeriod(classDataEntry.getValue(),endTime)));
        }else{
          series.add(new Task(classDataEntry.getKey(),new SimpleTimePeriod((classDataEntry.getValue()),sessionEndTime)));
        }
      }

      TaskSeriesCollection ganttChartDataset = new TaskSeriesCollection();
      ganttChartDataset.add(series);
      JFreeChart ganttChart = ChartFactory.createGanttChart("Class Creation Chart","Class","Time",ganttChartDataset,true,true,false);
    
      BufferedImage objectCreationImage = ganttChart.createBufferedImage(800, 600);
      
      PDImageXObject objectCreationGantChart = LosslessFactory.createFromImage(pdfDocument,objectCreationImage);
      float ganttChartYLocation = barChartYLocation - 250;
      pageContent2.drawImage(objectCreationGantChart,30, ganttChartYLocation,300, 225);
    
      return ganttChartYLocation;

    }catch(IOException e){
      e.printStackTrace();
      return barChartYLocation - 250;
    }
  }
}

  
