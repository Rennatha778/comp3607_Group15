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

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.image.BufferedImage;

import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.jfree.chart.plot.PiePlot;

public class PDFFileGenerator implements ReportPublisher {
  TreeMap<String,Data> dataTreeMap;
  ArrayList<String> names = new ArrayList<String>();
  ArrayList<Integer> intData = new ArrayList<Integer>();
  ArrayList<Long> longData = new ArrayList<Long>();

  long activeTime;
  long idolTime;
  int expectedErrors;
  int symbolErrors;
  int unclosedErros;
  int returnErrors;
  int otherErrors;
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
   
   

    
   //pageContent1.beginText();
   pageContent1.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA),12);
   //pageContent1.newLineAtOffset(100, 700);
   //pageContent1.showText("Expected errors: "+ String.valueOf(totalSessionTime));
   String[] headings = {"Metric Name", "Metric Value"};
   String[] metricNames = {"Total_Active_Time","Total_Idle_Time","Total_Session_Time","Total_Active_Typing_Time","Total_Idle_Typing_Time","Average_Compile_Time","Number_of_Methods_Ran","Number_of_Packages_Opened"};
   Object[] metricValues = new Object[metricNames.length];
   metricValues = getMetricValues(metricNames);
   
  Object[] combinedArray = {metricNames,metricValues};
  GenerateTable generateTable = new GenerateTable();
  generateTable.generateTableLong(headings,combinedArray,metricNames.length,pageContent1);
  

  String[] creationHeadings = {"Class Name", "Creation Time", "Modification Time", "Deletion Time"};
  String[] classNames;
  TreeMap<String,Long> allCreatedClass = new TreeMap<String,Long>();
  allCreatedClass = (TreeMap<String,Long>) treeMapTraverser.getMetricValue(listenersMetricsTreeMap, "Created_Classes");

  TreeMap<String,Long> allModifiedClass = new TreeMap<String,Long>();
  allModifiedClass = (TreeMap<String,Long>) treeMapTraverser.getMetricValue(listenersMetricsTreeMap, "Modified_Classes");
  
  TreeMap<String,Long> allRemovedClass = new TreeMap<String,Long>();
  allRemovedClass = (TreeMap<String,Long>) treeMapTraverser.getMetricValue(listenersMetricsTreeMap, "Removed_Classes");

   String[] createdClasses = new String[ allModifiedClass.size() + allRemovedClass.size()];
   
   String[] metricNames = {"Total_Active_Time","Total_Idle_Time","Total_Session_Time","Total_Active_Typing_Time","Total_Idle_Typing_Time","Average_Compile_Time","Number_of_Methods_Ran","Number_of_Packages_Opened"};
   Object[] metricValues = new Object[metricNames.length];
   metricValues = getMetricValues(metricNames);
   
  Object[] combinedArray = {metricNames,metricValues};
  GenerateTable generateTable = new GenerateTable();
  generateTable.generateTableLong(headings,combinedArray,metricNames.length,pageContent1);
  
   //pageContent1.endText();
   pageContent1.close();

   DefaultPieDataset<String> sessionPieChar = new DefaultPieDataset<>();
   sessionPieChar.setValue("Total_Active_Typing_Time", (long)treeMapTraverser.getMetricValue(listenersMetricsTreeMap, "Total_Active_Typing_Time"));
   sessionPieChar.setValue("Total_Idle_Typing_Time",(long)treeMapTraverser.getMetricValue(listenersMetricsTreeMap, "Total_Idle_Typing_Time"));

  JFreeChart sessionPieChart = ChartFactory.createPieChart("Session Breakdown",sessionPieChar,true,true,false);
  
  PiePlot plot = (PiePlot) sessionPieChart.getPlot();

  plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {2}", NumberFormat.getNumberInstance(),new DecimalFormat("0.00%")));

  BufferedImage pieCharImage = sessionPieChart.createBufferedImage(800, 600);
      
  PDImageXObject pdfImage = LosslessFactory.createFromImage(pdfDocument,pieCharImage);

  float pieChartYLocation = page2.getMediaBox().getHeight() - 380;

  pageContent2.drawImage(pdfImage,50, pieChartYLocation, 400, 300); 

    DefaultCategoryDataset programEventsDataset = new DefaultCategoryDataset();

    programEventsDataset.addValue(expectedErrors,"Error","Expected");
    programEventsDataset.addValue(symbolErrors,"Error","Symbol");
    programEventsDataset.addValue(unclosedErros,"Error","Unclosed");
    programEventsDataset.addValue(returnErrors,"Error","Return");
    programEventsDataset.addValue(otherErrors,"Error","Other");
  
    JFreeChart barChart = ChartFactory.createBarChart("Error Frequency Breakdown","Frequency", "Error", programEventsDataset);


    BufferedImage errorImage = barChart.createBufferedImage(800, 600);
      
    PDImageXObject barChartImage = LosslessFactory.createFromImage(pdfDocument,errorImage);
    float barChartYLocation = pieChartYLocation - 300 - 20;
    pageContent2.drawImage(barChartImage,30, barChartYLocation,400, 300);
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
}

  
