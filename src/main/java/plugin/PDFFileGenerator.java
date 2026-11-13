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



public void generateReport(TreeMap<String,Data> listenersMetricsTreeMap){

  dataTreeMap = listenersMetricsTreeMap;
  TreeMapTraverser treeMapTraverser = new TreeMapTraverser();
    

  try (PDDocument pdfDocument = new PDDocument()) {
   PDPage page1 = new PDPage();
   pdfDocument.addPage(page1);

   PDPage page2 = new PDPage();
   pdfDocument.addPage(page2);

    

   PDPageContentStream pageContent1 = new PDPageContentStream(pdfDocument, page1);
   PDPageContentStream pageContent2 = new PDPageContentStream(pdfDocument, page2);
   
   

    
   pageContent1.beginText();
   pageContent1.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA),12);
   pageContent1.newLineAtOffset(100, 700);
   //pageContent1.showText("Expected errors: "+ String.valueOf(totalSessionTime));
   String[] headings = {"Metric Name", "Metric Value"};
   String[] metricNames = {"Total_Active_Typing_Time", "Total_Idle_Typing_Time"};
   long[] metricValues = new long[metricNames.length];
  for(int i = 0; i < metricNames.length; i++){
    Long val = (long) treeMapTraverser.getMetricValue(listenersMetricsTreeMap, metricNames[i]);
    System.out.println(val);
    if(val != null){
      metricValues[i] = val;
    }else{
      System.out.println("A value is null");
    }
  }
  Object[] combinedArray = {metricNames,metricValues};
  GenerateTable generateTable = new GenerateTable();
  generateTable.generateTableLong(headings,combinedArray,metricNames.length,pageContent1);
  
  
   pageContent1.endText();
   pageContent1.close();

   DefaultPieDataset<String> sessionPieChar = new DefaultPieDataset<>();
   sessionPieChar.setValue(metricNames[0],metricValues[0]);
   sessionPieChar.setValue(metricNames[1],metricValues[1]);

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
}

  
