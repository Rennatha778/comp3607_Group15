package plugin;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

public class GenerateTable {
  String[][] table;

  public void generateTableInt(String[] cn, Object[]ov, int rowNum, PDPageContentStream content1 ) throws IOException{
    String[] coloumNames  = cn;
    String[] metricNames  = {"Total Session Time", "Total Idle Time", "Total Active Time"};
    int[] metricValues = {500,200,300};
    Object[] colValues = ov;

    int row, col, currentCol;
    for(row = 0; row < rowNum + 2; row++){
      currentCol = 0;
      for(col = 0; col < colValues.length; col++ ){
        if(row == 0){
          //System.out.print(coloumNames[col]);
          content1.showText(coloumNames[col]);
        }
        //System.out.println(col+" - "+currentCol);
        if(col == currentCol && row > 1){
          //System.out.print("Yes");
          if(colValues[currentCol] instanceof String[] ){
            //System.out.print("Yes");
            String[] curCol = (String[]) colValues[currentCol];
            //System.out.print(curCol[row - 2]);
            content1.showText(curCol[row - 2]);
          }else{
            int[] curCol = (int[]) colValues[currentCol];
            //System.out.print(curCol[row - 2]);
            content1.showText(String.valueOf(curCol[row - 2]));
          }
          //System.out.print(colValues[row - 2]);
        }

        if(row == 1){
          //System.out.print("-------");
          content1.showText("-------");
        } else{
          System.out.print("|  ");
          content1.showText("|  ");
        }
        currentCol++;
      }
      
      //System.out.println("");
      content1.showText("");
    }
  }

  public void generateTableLong(String[] cn, Object[]ov, int rowNum, PDPageContentStream content1 ) throws IOException{
    String[] coloumNames  = cn;
    //String[] metricNames  = {"Total Session Time", "Total Idle Time", "Total Active Time"};
    //int[] metricValues = {500,200,300};
    Object[] colValues = ov;
    try{
      int row, col, currentCol;
    for(row = 0; row < rowNum + 2; row++){
      currentCol = 0;
      for(col = 0; col < colValues.length; col++ ){
        if(row == 0){
          //System.out.print(coloumNames[col]);
          content1.showText(coloumNames[col]);
        }
        //System.out.println(col+" - "+currentCol);
        if(col == currentCol && row > 1){
          //System.out.print("Yes");
          if(colValues[currentCol] instanceof String[] ){
            //System.out.print("Yes");
            String[] curCol = (String[]) colValues[currentCol];
            //System.out.print(curCol[row - 2]);
            content1.showText(curCol[row - 2]);
          }else{
            long[] curCol = (long[]) colValues[currentCol];
            //System.out.print(curCol[row - 2]);
            content1.showText(String.valueOf(curCol[row - 2]));
          }
          //System.out.print(colValues[row - 2]);
        }

        if(row == 1){
          //System.out.print("-------");
          content1.showText("-------");
        } else{
          //System.out.print("|  ");
          content1.showText("|  ");
        }
        currentCol++;
      }
      
      //System.out.println("");
      content1.newLineAtOffset(100, -18);
      /* content1.showText("");
      content1.showText(""); */
    }
    }catch(IOException e){
      System.out.println("Error in prinitng table");
    }
  }

   
}
