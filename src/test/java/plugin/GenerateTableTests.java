package plugin;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GenerateTableTests {
  TableGenerator testerTableGenerator = new TableGenerator();
  String[] headings = {"Metric Header 1","Metric Header 2", "Metric Header 3"}; 
  String[] column1 = {"Colum Value 1","Colum Value 2","Longest Column Value 3","Colum Value 4","Colum Value 5"};
  Object[] column2 = {1,2,3,4,5};
  Object[] column3 = {6,7,8,9,10};
  Object[] combinedColumns = {column1,column2,column3};
    
  
  @Test
  void TestForLongestString(){
    assertEquals(22,testerTableGenerator.getLargestStringLenght(column1));
  }

  @Test
  void TestForColumnLenght(){
    assertEquals(15+5,testerTableGenerator.getColoumLenght(combinedColumns,headings)[1]);
  }

  @Test
  void TestForBarSpacing(){
    assertEquals(15+5+22+5+15+5,testerTableGenerator.getSpacingBarLenght(combinedColumns,headings));
  }
}
