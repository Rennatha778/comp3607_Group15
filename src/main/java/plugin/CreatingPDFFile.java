package plugin;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.text.ParagraphView;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import java.awt.image.BufferedImage;

import java.io.File;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;

public class CreatingPDFFile implements ReportPublisher {
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



  public void generateReport(TreeMap<String,Data> tr){
    dataTreeMap = tr;

    

    try (PDDocument document = new PDDocument()) {
    
      Path path = Paths.get(
      System.getProperty("user.home"),
      "Documents",
      "PluginFiles"
    );

    PDPage page = new PDPage();
    document.addPage(page);

    PDPage page1 = new PDPage();
    document.addPage(page1);

    

    PDPageContentStream content = new PDPageContentStream(document, page);
    PDPageContentStream content2 = new PDPageContentStream(document, page1);

    DefaultPieDataset<String> sessionPieChar = new DefaultPieDataset<>();

    DataContainer metricNames = new DataCollection(new DataAdapter(dataTreeMap));
    //DataCollection metricData = new DataCollection(nAdapter,1);


    DataIterator nameIterator = metricNames.getDataIterator(0);
    DataIterator dataIterator = metricNames.getDataIterator(1);

    //DefaultCategoryDataset metricsTable = new DefaultCategoryDataset();
    content.beginText();
    content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA),12);
    content.newLineAtOffset(100, 700);

    while(nameIterator.hasNext()){
      content.showText((String)nameIterator.next());
      content.newLineAtOffset(0, -27);

      //String metricName = (String)nameIterator.next();
      Data data = (Data)dataIterator.next();

      DataContainer subMetricInt = new DataCollection(new IntAdapter(data.getIntTreeMap()));
      DataCollection subMetricLong = new DataCollection(new LongAdapter(data.getLongTreeMap()));
      DataContainer objectionCreation = new DataCollection(new TreeAdapter(data.getObjectCreationMap()));
      

      DataIterator subNameIntIterator = subMetricInt.getDataIterator(0);
      DataIterator subDataIntIterator = subMetricInt.getDataIterator(1);
      
      
      while(subNameIntIterator.hasNext()){
        String intlable = (String)subNameIntIterator.next();
        content.showText(intlable);
        names.add(intlable);

        content.showText(": ");

        int intdata = ((int)subDataIntIterator.next());
        intData.add(intdata);
        content.showText(String.valueOf(intdata));
        content.newLineAtOffset(0, -24);

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
        content.showText(longlable);

        content.showText(": ");
        long longdata = (long)subDataLongIterator.next();
        content.showText(String.valueOf(longdata));
        content.newLineAtOffset(0, -24);

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
    content.endText();
    content.close();


        if(intData.size() != longData.size()){

        }

        sessionPieChar.setValue("Total Idle Time", idolTime);
        sessionPieChar.setValue("Total Active Time", activeTime);

        JFreeChart sessionPieChart =
                ChartFactory.createPieChart(
                        "Session Breakdown",
                        sessionPieChar,
                        true,
                        true,
                        false
                );
        PiePlot plot = (PiePlot) sessionPieChart.getPlot();

        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
        "{0}: {2}",
        NumberFormat.getNumberInstance(),
        new DecimalFormat("0.00%")
    )
);

        BufferedImage pieCharImage =
        sessionPieChart.createBufferedImage(800, 600);
      
      PDImageXObject pdfImage =
      LosslessFactory.createFromImage(
        document,
        pieCharImage
    );

    float pieChartYLocation = page1.getMediaBox().getHeight() - 380;

    content2.drawImage(
      pdfImage,
      50,   // x position
      pieChartYLocation,  // y position
      400,  // width
      300   // height
    ); 

    DefaultCategoryDataset programEventsDataset = new DefaultCategoryDataset();

    programEventsDataset.addValue(expectedErrors,"Error","Expected");
    programEventsDataset.addValue(symbolErrors,"Error","Symbol");
    programEventsDataset.addValue(unclosedErros,"Error","Unclosed");
    programEventsDataset.addValue(returnErrors,"Error","Return");
    programEventsDataset.addValue(otherErrors,"Error","Other");
  
      JFreeChart barChart = ChartFactory.createBarChart(
        "Error Frequency Breakdown",
        "Frequency",
        "Error",
        programEventsDataset
      );


      BufferedImage errorImage =
        barChart.createBufferedImage(800, 600);
      
      PDImageXObject barChartImage =
      LosslessFactory.createFromImage(
        document,
        errorImage
    );
    float barChartYLocation = pieChartYLocation - 300 - 20;
    content2.drawImage(barChartImage,
        30,
        barChartYLocation,
        400,
        300);

        


    content2.close();

      System.out.println("Charts created.");

    content.close();

    document.save("C:\\Users\\Owner\\Documents\\PluginFiles\\PluginMetricsPDF.pdf");

    System.out.println("PDF created.");
    }
    catch (Exception e) {
      e.printStackTrace();
      System.out.println("PDF file was not created");
    }
  }
}

/* public static void main(String[] args) {
    public void generateReport(TreeMap<String,Data> tr){
    dataTreeMap = tr;
    
    try (PDDocument document = new PDDocument()) {

    PDPage page = new PDPage();
    document.addPage(page);

    PDPageContentStream content = new PDPageContentStream(document, page);

    content.beginText();
    content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA),12);
    content.newLineAtOffset(100, 700);
    content.showText("Hello PDF!");
    content.endText();

    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

      dataset.addValue(15, "Appointments", "Cardiology");
      dataset.addValue(22, "Appointments", "Pediatrics");
      dataset.addValue(18, "Appointments", "Neurology");

      JFreeChart chart = ChartFactory.createBarChart(
        "Appointments by Department",
        "Department",
        "Appointments",
        dataset
      );

      ChartUtils.saveChartAsPNG(
        new File("appointments.png"),
        chart,
        800,
        600
      );

      BufferedImage image =
        chart.createBufferedImage(800, 600);
      
      PDImageXObject pdfImage =
      LosslessFactory.createFromImage(
        document,
        image
    );

    content.drawImage(
      pdfImage,
      50,   // x position
      250,  // y position
      400,  // width
      300   // height
    );

      System.out.println("Chart created.");

    content.close();

    document.save("output.pdf");

    System.out.println("PDF created.");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
} */
  
  
