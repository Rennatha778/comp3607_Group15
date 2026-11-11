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

    

  try (PDDocument pdfDocument = new PDDocument()) {
   PDPage page1 = new PDPage();
   pdfDocument.addPage(page1);

   PDPage page2 = new PDPage();
   pdfDocument.addPage(page2);

    

   PDPageContentStream pageContent1 = new PDPageContentStream(pdfDocument, page1);
   PDPageContentStream pageContent2 = new PDPageContentStream(pdfDocument, page2);

   DefaultPieDataset<String> sessionPieChar = new DefaultPieDataset<>();

   DataContainer metricNames = new DataCollection(new DataAdapter(dataTreeMap));

   DataIterator nameIterator = metricNames.getDataIterator(0);
   DataIterator dataIterator = metricNames.getDataIterator(1);
    
   pageContent1.beginText();
   pageContent1.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA),12);
   pageContent1.newLineAtOffset(100, 700);

   while(nameIterator.hasNext()){
     pageContent1.showText((String)nameIterator.next());
     pageContent1.newLineAtOffset(0, -27);

     Data data = (Data)dataIterator.next();

     DataContainer subMetricInt = new DataCollection(new IntAdapter(data.getIntTreeMap()));
     DataCollection subMetricLong = new DataCollection(new LongAdapter(data.getLongTreeMap()));
     DataContainer objectionCreation = new DataCollection(new TreeAdapter(data.getObjectCreationMap()));
      

     DataIterator subNameIntIterator = subMetricInt.getDataIterator(0);
     DataIterator subDataIntIterator = subMetricInt.getDataIterator(1);
      
      
     while(subNameIntIterator.hasNext()){
       String intlable = (String)subNameIntIterator.next();
       pageContent1.showText(intlable);
       names.add(intlable);

       pageContent1.showText(": ");

       int intdata = ((int)subDataIntIterator.next());
       intData.add(intdata);
       pageContent1.showText(String.valueOf(intdata));
       pageContent1.newLineAtOffset(0, -24);

       if(intlable == "'Expected' Errors"){
         expectedErrors = intdata;
       }else if(intlable == "'Symbol' Errors"){
         symbolErrors = intdata;
       }else if(intlable == "'Unclosed' Errors"){
         unclosedErros = intdata;
       }else if (intlable == "'Return' Errors"){
         returnErrors = intdata;
       }else if(intlable == "Other Errors"){
         otherErrors = intdata;
      }
     }

     DataIterator subNameLongIterator = subMetricLong.getDataIterator(0);
     DataIterator subDataLongIterator = subMetricLong.getDataIterator(1);
     while(subNameLongIterator.hasNext()){
       String longlable = (String)subNameLongIterator.next();
       names.add(longlable);
       pageContent1.showText(longlable);

       pageContent1.showText(": ");
       long longdata = (long)subDataLongIterator.next();
       pageContent1.showText(String.valueOf(longdata));
       pageContent1.newLineAtOffset(0, -24);

       if(longlable == "activeTypingTime"){
         activeTime = longdata;
       }else if(longlable == "idleTypingTime"){
         idolTime = longdata;
       }
     }

     DataIterator subOuterOCNameIterator = objectionCreation.getDataIterator(0);
     DataIterator subOuterTreeMapIterator = objectionCreation.getDataIterator(1);

     while(subOuterOCNameIterator.hasNext()){
       System.out.println("Outer OC Key:"+(String)subOuterOCNameIterator.next());

       TreeMap<String, TreeMap<String,Long>> innerTreeMapCast = (TreeMap<String, TreeMap<String,Long>>) subOuterTreeMapIterator.next();

       DataContainer innerTreeMap = new DataCollection(new TreeAdapter(innerTreeMapCast));

       DataIterator innerTreeMapNameIterator = innerTreeMap.getDataIterator(0);
       DataIterator innerTreeMapTreeIterator = innerTreeMap.getDataIterator(1);

        
       while(innerTreeMapNameIterator.hasNext()){
         String Key = (String) innerTreeMapNameIterator.next();
         System.out.println(Key);

         System.out.println(String.valueOf((long)innerTreeMapTreeIterator.next()));
       }
     }
    
   }
   pageContent1.endText();
   pageContent1.close();


  sessionPieChar.setValue("Total Idle Time", idolTime);
  sessionPieChar.setValue("Total Active Time", activeTime);
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

  
