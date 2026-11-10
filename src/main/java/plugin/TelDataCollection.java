package plugin;

import bluej.BlueJEventListener;
import bluej.editor.Editor;
import bluej.extensions2.*;
import bluej.extensions2.event.*;
import java.net.URL;


public class TelDataCollection implements CompileListener, BluejListeners {
  int numCompileFailed = 0;
  int numCompileSuccessful = 0;
  long startTime = 0;
  long endTime = 0;
  long totalCompileTime = 0;

  long fixStart = 0;
  long fixEnd = 0;
  long averageCorrectionTime = 0;

  int expectedErrors = 0;
  int sysmbolError = 0;
  int unclosedError = 0;
  int returnError = 0;
  int otherError = 0;
  int numErrors;
  
  public Data getData(){
    Data d = new Data();
    d.addLongData("totalCompileTime", totalCompileTime);
    d.addIntData("numCompileFailed", numCompileFailed);
    d.addIntData("numCompileSuccessful", numCompileSuccessful);
    d.addIntData("'Expected' Errors",expectedErrors);
    d.addIntData("'Symbol' Errors",sysmbolError);
    d.addIntData("'Unclosed' Errors",unclosedError);
    d.addIntData("'Return' Errors",returnError);
    d.addIntData("Other Errors",otherError);

    numErrors = expectedErrors+sysmbolError+unclosedError+returnError+otherError;

    d.addIntData("Total Errors",numErrors);

    if(numCompileFailed != 0 || numCompileSuccessful != 0)
      d.addLongData("averageCompileTime",totalCompileTime/(numCompileFailed + numCompileSuccessful));

    if(averageCorrectionTime != 0 && numErrors != 0)
      d.addLongData("Average Correction Time", averageCorrectionTime/numErrors);
    return d;
  }



  @Override 
  public void compileStarted(CompileEvent event){
    startTime = System.currentTimeMillis();
    System.out.println("Compile started successfully.");
    
  }

  @Override 
  public void compileFailed(CompileEvent event){
    System.out.println("Compile failed.");
    endTime = System.currentTimeMillis();
    totalCompileTime = totalCompileTime + (endTime - startTime);
    numCompileFailed++;
  }

  @Override 
  public void compileError(CompileEvent event){
    fixStart = System.currentTimeMillis();
     System.out.println("Compile error line number: " + event.getErrorLineNumber());
     System.out.println("Compile error message: " + event.getErrorMessage());
     System.out.println("Compile position: " + event.getErrorPosition());
     
     if (event.getErrorMessage().contains("expected")){
      expectedErrors++;
     }else if(event.getErrorMessage().contains("symbol")){
      sysmbolError++;
     }else if(event.getErrorMessage().contains("unclosed")){
      unclosedError++;
     }else if(event.getErrorMessage().contains("return")){
      returnError++;
     }else{
      otherError++;
     }

  }

  @Override 
  public void compileSucceeded(CompileEvent event){
    fixEnd = System.currentTimeMillis();
    if(fixStart != 0){
      averageCorrectionTime = averageCorrectionTime + (fixEnd - fixStart);
      fixStart = 0;
      fixEnd = 0;
    }
     System.out.println("Compile successful.");
     endTime = System.currentTimeMillis();
     totalCompileTime = totalCompileTime + (endTime - startTime);
     numCompileSuccessful++;
  }

  @Override 
  public void compileWarning(CompileEvent event){
    System.out.println("Compile warning!");
  }

}
