package plugin.FileGenerators.java;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

/**
 * This class is used to generate a table on a pdf page
 */
public class TableGenerator {
  /**
   * This method generates a table on a pdf page using the parameters passed into the method
   * @param tableHeadings The string headings for each column in the table
   * @param columVals An object array of other specifc type arrays that storing column data
   * @param content1 PDF content stream used for writing to the PDF
   * @param xValue The x coordinate on the page where the top of the table will be drawn
   * @param yValue The y coordinate on the page where the top of the table will be drawn
   * @return The new y coordinate that the next item on the pdf should start from
   */
  public int generateTable(String[] tableHeadings, Object[]columVals, int numberOfRows, PDPageContentStream content1, int xValue, int yValue ){
    String[] coloumNames  = tableHeadings;
    Object[] columnValues = columVals;
    
    int numberOfColumns = coloumNames.length; 
    int pageXValue = xValue, pageYValue = yValue;
    /**
     * Adjusting values of x and y used to place table content in the correct 
     * position on the page
     */
    int tableMovingYValue = 700, tableMovingXValue = 50;
    
    /**
     * Variable holds the number of characters of the longest string, 
     * if any strings are present. This is used to calculate the spacing bar 
     * for the top and bottom of the table. As well as for the row under 
     * the headings
     */
    int largetsStringLenght = getLargestStringLenght((String[])columnValues[0]);
    try{
      int row, col, currentCol;
      content1.beginText();
      content1.newLineAtOffset(pageXValue, pageYValue);
      printSpacerBar(content1,largetsStringLenght,columnValues, coloumNames);
      content1.endText();

      content1.beginText();
      pageYValue = pageYValue-10;
      tableMovingYValue = pageYValue;
      content1.newLineAtOffset(pageXValue, pageYValue);

      int[] columnLengts = getColoumLenght(columnValues,tableHeadings);

    for(row = 0; row < numberOfRows + 2; row++){
      currentCol = 0;
      for(col = 0; col < numberOfColumns; col++ ){
        if(row == 0){
          content1.showText("|  "+coloumNames[col]);
        }

        if(col == currentCol && row > 1){
          if(columnValues[currentCol] instanceof String[] ){
            
            String[] curCol = (String[]) columnValues[currentCol];
    
            content1.showText("|  "+curCol[row - 2]);
          }else{
            Object[] curCol = (Object[])columnValues[currentCol];
            if(curCol[row-2] == null){
              content1.showText("|  N/A");
            }else{
              content1.showText("|  "+String.valueOf(curCol[row - 2]));
            }
          }
        }

        if(row == 1 && col == 0){
         printSpacerBar(content1, largetsStringLenght,columnValues,coloumNames);
        }
        System.out.println("Coloum val: "+getColoumLenght(columnValues, coloumNames)[col]+ " * 6: "+ getColoumLenght(columnValues, coloumNames)[col] * 6);
        tableMovingXValue += (getColoumLenght(columnValues, coloumNames)[col] * 6 )+54;
        //if(col + 1 != numberOfColumns){
          currentCol++;
          content1.endText();
          content1.beginText();
          content1.newLineAtOffset(tableMovingXValue, tableMovingYValue);
        //}
      
      }

      if(row != 1){
        content1.endText();
        content1.beginText();
        content1.newLineAtOffset(xValue , tableMovingYValue);
        for(int i = 0; i < getSpacingBarLenght(columnValues,tableHeadings); i++){
          if(i < getSpacingBarLenght(columnValues,tableHeadings)-1){
            content1.showText(" ");
          }else{
            content1.showText("|");
          }
        }
        
      }
      content1.endText();
      content1.beginText();
      
        content1.newLineAtOffset(pageXValue, pageYValue - ((row + 1)* 15));
        tableMovingYValue = pageYValue - (row + 1)* 15;
        tableMovingXValue = pageXValue;
      
      
    }

    content1.endText();
    content1.beginText();
    
    content1.newLineAtOffset(pageXValue, tableMovingYValue);
    printSpacerBar(content1,largetsStringLenght,columnValues, coloumNames);
    content1.endText();

    }catch(IOException e){
      System.out.println("Error in prinitng table");
    }

    return tableMovingYValue;
  }

  /**
   * The method returns the most number of characters of all the strings in a string array
   * @param arrayOfStrings A column with string values
   * @return lenght of the element with the largest number of characters
   */
  public int getLargestStringLenght(String[] arrayOfStrings){
    int largestStringLenght = 0;
    for(String string :arrayOfStrings){
      if(string.length() > largestStringLenght){
        largestStringLenght = string.length();
      }
    }

    return largestStringLenght;
  }

  /**
   * The method prints a serise of -es on a PDF page.
   */
  public void printSpacerBar(PDPageContentStream content1, int lagestStringLenght, Object[] colValues, String[] headings){
    //- (4 * headings.length) - (5 * colValues.length) + 2
    int barLenght = getSpacingBarLenght(colValues,headings);
    try{
      for(int i = 0; i < barLenght ; i ++){
        content1.showText("-");
      }
    }catch(IOException e){
      System.out.println("Error Occured in printing spacer bar");
    }

  }

  /**
   * The method gets the spacing bar lenght by adding up the individual column lenghts 
   */
  public int getSpacingBarLenght(Object[] columnValues, String[] tableHeadings){
    int spacingBarLenght = 0;
    for(int lenght : getColoumLenght(columnValues, tableHeadings)){
      spacingBarLenght += lenght+5;
    }
    return spacingBarLenght;
  }

  /**
   * The method calculates each column's lenght by setting it as the largest number 
   * of characters if the column holds strings, or the column header lenght if 
   * it is greater than the largest string or the column header lenght if the 
   * column contains numbers.
   * @return An array of column lenghts
   */
  public int[] getColoumLenght(Object[] coloumValues, String[] headings){
    int numColoums = coloumValues.length;
    int[] coloumLenghts = new int[numColoums];
    int i = 0;
    for(Object coloum : coloumValues){
      if(coloum instanceof String[]){
        if(getLargestStringLenght((String[])coloum) > headings[i].length()){
          coloumLenghts[i] = getLargestStringLenght((String[])coloum);
        }else{
          coloumLenghts[i] = headings[i].length();
        }
        
      }else{
        //coloumLenghts[i] = headings[i].length();
        if(headings[i].length() > 13){
          coloumLenghts[i] = headings[i].length();
        }else{
          coloumLenghts[i] = 13;
        }
        
      }

      i++;
    }
    return coloumLenghts;
  }
   
}
