package plugin;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

public class GenerateTable {
  String[][] table;

   

  public void generateTableLong(String[] cn, Object[]ov, int rowNum, PDPageContentStream content1 ) throws IOException{
    String[] coloumNames  = cn;
    //String[] metricNames  = {"Total Session Time", "Total Idle Time", "Total Active Time"};
    //int[] metricValues = {500,200,300};
    Object[] colValues = ov;
    String[] colNames = (String[])ov[0];
    int numCols = coloumNames.length; 
    int x = 50, y = 700, movingy = 700, movingx =50;
    try{
      int row, col, currentCol;
      content1.beginText();
      content1.newLineAtOffset(x, y);
    for(row = 0; row < rowNum + 2; row++){
      currentCol = 0;
      for(col = 0; col < numCols; col++ ){
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
            Object[] curCol = (Object[])colValues[currentCol];
            //long[] curCol = (long[]) colValues[currentCol];
            /* if(curCol[row-2] instanceof Integer){
              
            }else if(curCol[row-2] instanceof Long){

            }else if(curCol[row-2] == null){

            } */
            //System.out.print(curCol[row - 2]);
            if(curCol[row-2] == null){
              content1.showText("N/A");
            }else{
              content1.showText(String.valueOf(curCol[row - 2]));
            }
          }
          //System.out.print(colValues[row - 2]);
        }

        movingx += 180;
        content1.newLineAtOffset(movingx, movingy);
        
        if(row == 1){
          //System.out.print("-------");
          for(int i=0; i < colNames[col].length(); i++){
            content1.showText("-");
          }
        } else{
          
          /* movingx += 150;
          content1.newLineAtOffset(movingx, movingy); */
          content1.showText("|");
        }
        //content1.newLineAtOffset((col + 1) * x + 10, y);
        currentCol++;
        content1.endText();
        content1.beginText();
        //content1.newLineAtOffset((col + 1) * x, movingy);
        /* if(row == 1){
          content1.newLineAtOffset(movingx, movingy);
        }  */
        content1.newLineAtOffset(movingx + 15, movingy);
        /* if(row == 0)
          movingx += 30; */
      }
      
      //System.out.println("");
      content1.endText();
      content1.beginText();
      
      content1.newLineAtOffset(x, y - ((row + 1)* 15));
      movingy = y - (row + 1)* 15;
      movingx = x;
      /* content1.showText("");
      content1.showText(""); */
    }
    }catch(IOException e){
      System.out.println("Error in prinitng table");
    }
  }

   
}
