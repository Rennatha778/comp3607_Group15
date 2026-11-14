package plugin;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

public class TableGenerator {
 

  public int generateTableLong(String[] cn, Object[]ov, int rowNum, PDPageContentStream content1, int xValue, int yValue ) throws IOException{
    String[] coloumNames  = cn;
    Object[] colValues = ov;
    //String[] colNames = (String[])ov[0];
    int numCols = coloumNames.length; 
    int x = xValue, y = yValue, movingy = 700, movingx = 50;

    //int largetsStringLenght = getLargestStringLenght((String[])colValues[0]) + 10;
    int largetsStringLenght = getLargestStringLenght((String[])colValues[0]);
  
    try{
      int row, col, currentCol;
      content1.beginText();
      content1.newLineAtOffset(x, y);
      printSpacerBar(content1,largetsStringLenght,colValues, coloumNames);
      content1.endText();
      content1.beginText();
      y = y-10;
      movingy = y;
      content1.newLineAtOffset(x, y);

    for(row = 0; row < rowNum + 2; row++){
      currentCol = 0;
      for(col = 0; col < numCols; col++ ){
        if(row == 0){
          //System.out.print(coloumNames[col]);
          content1.showText("|  "+coloumNames[col]);
        }
        //System.out.println(col+" - "+currentCol);
        if(col == currentCol && row > 1){
          //System.out.print("Yes");
          if(colValues[currentCol] instanceof String[] ){
            //System.out.print("Yes");
            String[] curCol = (String[]) colValues[currentCol];
            //System.out.print(curCol[row - 2]);
            content1.showText("|  "+curCol[row - 2]);
          }else{
            Object[] curCol = (Object[])colValues[currentCol];
            if(curCol[row-2] == null){
              content1.showText("|  N/A");
            }else{
              content1.showText("|  "+String.valueOf(curCol[row - 2]));
            }
          }
          //System.out.print(colValues[row - 2]);
        }

        if(row == 1 && col == 0){
          //System.out.print("-------");
          /* for(int i= 0; i < largetsStringLenght; i++){
            content1.showText("-");
          } */
         printSpacerBar(content1, largetsStringLenght,colValues,coloumNames);
        }

        /* if(col + 1 == numCols && row != 1){
          content1.showText(" |");
        } */

        //movingx += 180;
        //movingx += (getColoumLenght(colValues)[col]* 6);
        movingx += getColoumLenght(colValues, coloumNames)[col] * 6;
        //content1.newLineAtOffset(movingx, movingy);
        
        
        currentCol++;
        content1.endText();
        content1.beginText();
      
        //content1.newLineAtOffset(movingx + 15, movingy);
        content1.newLineAtOffset(movingx, movingy);
      
      }
      if(row != 1)
        content1.showText(" |");
      content1.endText();
      content1.beginText();
      
      content1.newLineAtOffset(x, y - ((row + 1)* 15));
      movingy = y - (row + 1)* 15;
      movingx = x;
    }

    content1.endText();
    content1.beginText();
    /* content1.newLineAtOffset(x, y - ((row + 1)* 15));
    movingy = y - (row + 1)* 15; */
    content1.newLineAtOffset(x, movingy - 10);
    printSpacerBar(content1,largetsStringLenght,colValues, coloumNames);
    content1.endText();
    }catch(IOException e){
      System.out.println("Error in prinitng table");
    }

    return movingy;
  }

  private int getLargestStringLenght(String[] strings){
    int largestStringLenght = 0;
    for(String string :strings){
      if(string.length() > largestStringLenght){
        largestStringLenght = string.length();
      }
    }

    return largestStringLenght;
  }

  private void printSpacerBar(PDPageContentStream content1, int lagestStringLenght, Object[] colValues, String[] headings){
    int barLenght = getSpacingBarLenght(colValues,headings);
    try{
      for(int i = 0; i < barLenght - (4 * headings.length); i ++){
        content1.showText("-");
      }
    }catch(IOException e){
      System.out.println("Error Occured in printing spacer bar");
    }

  }

  private int getSpacingBarLenght(Object[] colValues, String[] headings){
    int spacingBarLenght = 0;
    for(int lenght : getColoumLenght(colValues, headings)){
      spacingBarLenght += lenght;
    }
    return spacingBarLenght;
  }

  private int[] getColoumLenght(Object[] coloumValues, String[] headings){
    int numColoums = coloumValues.length;
    int[] coloumLenghts = new int[numColoums];
    int i = 0;
    for(Object coloum : coloumValues){
      if(coloum instanceof String[]){
        coloumLenghts[i] = getLargestStringLenght((String[])coloum) + 10;
      }else{
        coloumLenghts[i] = headings[i].length()+ 10;
      }

      i++;
    }
    return coloumLenghts;
  }
   
}
