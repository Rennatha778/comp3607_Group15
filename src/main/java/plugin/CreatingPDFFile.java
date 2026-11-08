package plugin;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TreeMap;

import javax.swing.text.ParagraphView;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.image.BufferedImage;

import java.io.File;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class CreatingPDFFile implements ReportPublisher {
  TreeMap<String,Data> dataTreeMap;
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

    PDPageContentStream content = new PDPageContentStream(document, page);

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
      content.newLineAtOffset(0, -15);

      //String metricName = (String)nameIterator.next();
      Data data = (Data)dataIterator.next();

      DataContainer subMetricInt = new DataCollection(new IntAdapter(data.getIntTreeMap()));
      DataCollection subMetricLong = new DataCollection(new LongAdapter(data.getLongTreeMap()));

      DataIterator subNameIntIterator = subMetricInt.getDataIterator(0);
      DataIterator subDataIntIterator = subMetricInt.getDataIterator(1);
      while(subNameIntIterator.hasNext()){
        content.showText((String)subNameIntIterator.next());
        content.showText(String.valueOf((int)subDataIntIterator.next()));
        content.newLineAtOffset(0, -15);
      }

      DataIterator subNameLongIterator = subMetricLong.getDataIterator(0);
      DataIterator subDataLongIterator = subMetricLong.getDataIterator(1);
      while(subNameLongIterator.hasNext()){
        content.showText((String)subNameLongIterator.next());
        content.showText(String.valueOf((long)subDataLongIterator.next()));
        content.newLineAtOffset(0, -15);
      }
    
    }
    content.endText();

    /* DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    dataset.addValue(15, "Appointments", "Cardiology");
    dataset.addValue(22, "Appointments", "Pediatrics");
    dataset.addValue(18, "Appointments", "Neurology"); */
  
      /* JFreeChart chart = ChartFactory.createBarChart(
        "Appointments by Department",
        "Department",
        "Appointments",
        //dataset
      ); */

      /* ChartUtils.saveChartAsPNG(
        new File("appointments.png"),
        chart,
        800,
        600
      ); */

      /* BufferedImage image =
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
    ); */

      //System.out.println("Chart created.");

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
  
  
