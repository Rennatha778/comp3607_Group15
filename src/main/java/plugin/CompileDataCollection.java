package plugin;

import bluej.extensions2.event.*;



public class CompileDataCollection implements CompileListener, BluejListeners {
  int numCompileFailed = 0;
  int numCompileSuccessful = 0;
  int expectedErrors = 0;
  int sysmbolError = 0;
  int unclosedError = 0;
  int returnError = 0;
  int otherError = 0;
  int numErrors;

  long compileStartTime = 0;
  long compileEndTime = 0;
  long totalCompileTime = 0;
  long errorFixStartTime = 0;
  long errorFixEndTime = 0;
  long averageCorrectionTime = 0;

  
  public Data getMetricData(){
    Data compileMetrics = new Data();
    compileMetrics.addLongData("Total_Compile_Time", totalCompileTime);
    compileMetrics.addIntData("Number_of_Compile_Failed", numCompileFailed);
    compileMetrics.addIntData("Number_of_Compile_Successful", numCompileSuccessful);
    compileMetrics.addIntData("Number_of_'Expected'_Errors",expectedErrors);
    compileMetrics.addIntData("Number_of_'Symbol'_Errors",sysmbolError);
    compileMetrics.addIntData("Number_of_'Unclosed'_Errors",unclosedError);
    compileMetrics.addIntData("Number_of_'Return'_Errors",returnError);
    compileMetrics.addIntData("Number_of_Other_Errors",otherError);

    numErrors = expectedErrors+sysmbolError+unclosedError+returnError+otherError;

    compileMetrics.addIntData("Total Errors",numErrors);

    if(numCompileFailed != 0 || numCompileSuccessful != 0)
      compileMetrics.addLongData("averageCompileTime",totalCompileTime/(numCompileFailed + numCompileSuccessful));

    if(averageCorrectionTime != 0 && numErrors != 0)
      compileMetrics.addLongData("Average Correction Time", averageCorrectionTime/numErrors);
    
    return compileMetrics;
  }



  @Override 
  public void compileStarted(CompileEvent event){
    compileStartTime = System.currentTimeMillis();
    System.out.println("Compile started successfully.");
    
  }

  @Override 
  public void compileFailed(CompileEvent event){
    System.out.println("Compile failed.");
    compileEndTime = System.currentTimeMillis();
    calculateTotalCompileTime();
    numCompileFailed++;
  }

  @Override 
  public void compileError(CompileEvent event){
    errorFixStartTime = System.currentTimeMillis();
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
    errorFixEndTime = System.currentTimeMillis();

    if(errorFixStartTime != 0){
      averageCorrectionTime = averageCorrectionTime + (errorFixEndTime - errorFixStartTime);
      errorFixStartTime = 0;
      errorFixEndTime = 0;
    }

     System.out.println("Compile successful.");
     compileEndTime = System.currentTimeMillis();
     calculateTotalCompileTime();
     numCompileSuccessful++;
  }

  private synchronized void calculateTotalCompileTime(){
    totalCompileTime = totalCompileTime + (compileEndTime - compileStartTime);
  }

  @Override 
  public void compileWarning(CompileEvent event){
    System.out.println("Compile warning!");
  }

  public int getcompileSuccessRate(){
    return this.numCompileSuccessful;
  }

}
