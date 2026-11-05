package plugin;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.image.BufferedImage;

import java.io.File;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class CreatingPDFFile {
  public static void main(String[] args) {
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
}
