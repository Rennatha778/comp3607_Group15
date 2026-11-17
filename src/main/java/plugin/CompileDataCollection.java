package plugin;

import bluej.extensions2.event.*;

/**
 * This class collects BlueJ complie event metrics
 */
public class CompileDataCollection implements CompileListener, BluejListeners {
  int numCompileFailed = 0;
  int numCompileSuccessful = 0;
  int expectedErrors = 0;
  int symbolError = 0;
  int unclosedError = 0;
  int returnError = 0;
  int otherError = 0;
  int totalNumError;
  int totalCorrected = 0;

  long compileStartTime = 0;
  long compileEndTime = 0;
  long totalCompileTime = 0;
  long errorFixStartTime = 0;
  long errorFixEndTime = 0;
  long totalCorrectionTime = 0;

  
  public Data getMetricData(){
    Data compileMetrics = new Data();
    compileMetrics.addLongData("Total_Compile_Time", totalCompileTime);
    compileMetrics.addIntData("Number_of_Compile_Failed", numCompileFailed);
    compileMetrics.addIntData("Number_of_Compile_Successful", numCompileSuccessful);
    compileMetrics.addIntData("Number_of_'Expected'_Errors",expectedErrors);
    compileMetrics.addIntData("Number_of_'Symbol'_Errors",getSymbolErrorCount());
    compileMetrics.addIntData("Number_of_'Unclosed'_Errors",unclosedError);
    compileMetrics.addIntData("Number_of_'Return'_Errors",returnError);
    compileMetrics.addIntData("Number_of_Other_Errors",otherError); 
    compileMetrics.addIntData("Total Errors",getTotalErrorCount());

    if(getTotalCompileFail() != 0 || getTotalCompileSuccessful() != 0)
      compileMetrics.addIntData("Approximate_Average_Compile_Time",calculateAverageCompileTime(getTotalCompileTime(),(getTotalCompileFail() + getTotalCompileSuccessful())));

    if(totalCorrectionTime != 0 && totalNumError != 0)
      compileMetrics.addIntData("Approximate_Average_Correction_Time",calculateAverageCorrectionTime(getTotalCorretionTime(),getTotalCorrected()));
    
    return compileMetrics;
  }


  /**
   * This method is called whenever a class in BlueJ starts to compile a class.
   * The time it starts is recorded.
   */
  @Override 
  public void compileStarted(CompileEvent event){
    setStartCompileTime(System.currentTimeMillis());
    System.out.println("Compile started successfully.");
    
  }

  /**
   * This method is called whenever a compilation fails.
   * The time it failed is recorded and the method to calculate
   * compilation time elapsed is called. A call to increment the number 
   * of failed compilations is also called.
   */
  @Override 
  public void compileFailed(CompileEvent event){
    setEndCompileTime(System.currentTimeMillis());
    System.out.println("Compile failed.");
    calculateTotalCompileTime(getCompileStartedTime(),getCompileEndedTime());
    incrementNumCompileFailed();
  }

  /**
   * This method stores the time at whicn an error was first recorded
   * using the setErrorFixedStartTime method. A count of  specific 
   * syntax errors is also kept using the updateErrorCount method.
   */
  @Override 
  public void compileError(CompileEvent event){
    setErrorFixedStartTime(System.currentTimeMillis());
    updateErrorCount(event.getErrorMessage());
     System.out.println("Compile error line number: " + event.getErrorLineNumber());
     System.out.println("Compile error message: " + event.getErrorMessage());
     System.out.println("Compile position: " + event.getErrorPosition());

  }

   /**
   * This method is called whenever a compilation succeeds .
   * The time it succeeded is recorded and the method to calculate
   * compilation time elapsed is called. If there was a pending error
   * that was resolved, the elapsed time to correct that error is also
   * calculated. A call to increment the number of successful compilations is also called.
   */
  @Override 
  public void compileSucceeded(CompileEvent event){
    setErrorFixedEndTime(System.currentTimeMillis());
    setEndCompileTime(getErrorFixedEndTime());

    calaculateTotalErrorFixedTime(getErrorFixedStartTime(),getErrorFixedEndTime());
    calculateTotalCompileTime(getCompileStartedTime(),getCompileEndedTime());
    incrementNumCompileSuccessful();
    System.out.println("Compile successful.");
  }

  /**
   * This message prints out the error message to the BlueJ debugger log
   */
  @Override 
  public void compileWarning(CompileEvent event){
    System.out.println("Compile warning: "+event.getErrorMessage());
  }

  /**
   * This method keeps running counts of specific types of syntax errors
   * encountered while BlueJ is in session. 
   * @param errorMessage The String variable containing the specific compile error message
   */
  protected void updateErrorCount(String errorMessage){
    if (errorMessage.contains("expected")){
      incrementTotalExpectedErrorsCount();
     }else if(errorMessage.contains("symbol")){
      incrementTotalSymbolErrorsCount();
     }else if(errorMessage.contains("unclosed")){
      incrementTotalUnclosedErrorsCount();
     }else if(errorMessage.contains("return")){
      incrementTotalReturnErrorsCount();
     }else{
      incrementTotalOtherErrorsCount();
     }
  }

  /**
   * This method keeps a running count of total compile time for the
   * entire BlueJ session.
   */
  protected synchronized void calculateTotalCompileTime(long compileStartedTime, long compileEndedTime){
    setTotalCompileTime(compileEndedTime - compileStartedTime);
  }

  /**
   * This method keeps a running count of the total time taken to
   * correct all errors preventing a successful compilation.
   * A call to reset errorFixedStartTime and errorFixedEndTime is made 
   * as well as a call to increment the total number of errors corrected.
   */
  protected void calaculateTotalErrorFixedTime(long errorFixedStartTime, long errorFixedEndTime){
    if(errorFixedStartTime != (long)0){
      setTotalCorrectionTime(errorFixedStartTime,errorFixedEndTime);
      
      setErrorFixedEndTime((long)0);
      setErrorFixedStartTime((long)0);
      incrementTotalCorrected();
    }
  }

  protected void incrementNumCompileSuccessful(){this.numCompileSuccessful++;}
  protected void incrementNumCompileFailed(){this.numCompileFailed++;}
  protected void incrementTotalExpectedErrorsCount(){this.expectedErrors++;}
  protected void incrementTotalSymbolErrorsCount(){this.symbolError++;}
  protected void incrementTotalUnclosedErrorsCount(){this.unclosedError++;}
  protected void incrementTotalReturnErrorsCount(){this.returnError++;}
  protected void incrementTotalOtherErrorsCount(){this.returnError++;}
  protected void incrementTotalCorrected(){this.totalCorrected++;}

  protected void setTotalCompileTime(long totalCompileTime){this.totalCompileTime = this.totalCompileTime + totalCompileTime;}
  protected void setErrorFixedEndTime(long errorFixedEndTime){this.errorFixEndTime = errorFixedEndTime;}
  protected void setTotalCorrectionTime(long errorFixedStartTime, long errorFixedEndTime){
    this.totalCorrectionTime += (errorFixedEndTime - errorFixedStartTime);
  }
  
  /**
   * This method sets errorFixedStartTime to the value passed only if
   * both its current value and the value of errorFixedEndTime is zero, 
   * or the value being passed in is zero.
   * @param errorFixedStartTime A new value for errorFixedStartTime
   */
  protected void setErrorFixedStartTime(long errorFixedStartTime){
    if(getErrorFixedStartTime() == (long)0 && getErrorFixedEndTime() == (long)0 || errorFixedStartTime == 0){
      this.errorFixStartTime = errorFixedStartTime;
    }
  }

  protected int calculateAverageCompileTime(long totalCompileTime, int totalNumberOfCompiles){
    return (int)totalCompileTime/totalNumberOfCompiles;
  }

  protected int calculateAverageCorrectionTime(long totalCorrectionTime, int totalCorrected){
    return (int)totalCorrectionTime/totalCorrected;
  }

  protected void setStartCompileTime(long startCompileTime){this.compileStartTime = startCompileTime;}
  protected void setEndCompileTime(long endCompileTime){this.compileEndTime = endCompileTime;}


  protected int getTotalCompileSuccessful(){return this.numCompileSuccessful;}
  protected int getTotalCompileFail(){return this.numCompileFailed;}
  protected int getExpectedErrorCount(){return this.expectedErrors;}
  protected int getSymbolErrorCount(){return this.symbolError;}
  protected int getUnclodedErrorCount(){return this.unclosedError;}
  protected int getReturnErrorCount(){return this.returnError;}
  protected int getOtherErrorCount(){return this.otherError;}
  protected int getTotalCorrected(){return this.totalCorrected;}
  
  /**
   * This method sums up all the compilation error encountered during
   * the session time.
   */
  protected int getTotalErrorCount(){
    this.totalNumError = getExpectedErrorCount()+ getSymbolErrorCount()+getUnclodedErrorCount()
    +getReturnErrorCount()+getOtherErrorCount();
    return this.totalNumError;
  }

  protected long getCompileStartedTime(){return this.compileStartTime;}
  protected long getCompileEndedTime(){return this.compileEndTime;}
  protected long getErrorFixedStartTime(){return this.errorFixStartTime;}
  protected long getErrorFixedEndTime(){return this.errorFixEndTime;}
  protected long getTotalCompileTime(){return this.totalCompileTime;}
  protected long getTotalCorretionTime(){return this.totalCorrectionTime;}
}
