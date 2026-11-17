package plugin.EventListeners.java;

import bluej.extensions2.event.*;
import plugin.Data;

/**
 * This class listens for and collects BlueJ complie event metrics
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

  /**
   * @return A populated data class
   */
  public Data getMetricData(){
    Data compileMetrics = new Data();
    compileMetrics.addLongData("Total_Compile_Time", getTotalCompileTime());
    compileMetrics.addIntData("Number_of_Compile_Failed", getTotalCompileFail());
    compileMetrics.addIntData("Number_of_Compile_Successful", getTotalCompileSuccessful());
    compileMetrics.addIntData("Number_of_'Expected'_Errors",getExpectedErrorCount());
    compileMetrics.addIntData("Number_of_'Symbol'_Errors",getSymbolErrorCount());
    compileMetrics.addIntData("Number_of_'Unclosed'_Errors",getUnclodedErrorCount());
    compileMetrics.addIntData("Number_of_'Return'_Errors",getReturnErrorCount());
    compileMetrics.addIntData("Number_of_Other_Errors",getOtherErrorCount()); 
    compileMetrics.addIntData("Total Errors",getTotalErrorCount());

    if(getTotalCompileFail() != 0 || getTotalCompileSuccessful() != 0)
      compileMetrics.addIntData("Approximate_Average_Compile_Time",calculateAverageCompileTime(getTotalCompileTime(),(getTotalCompileFail() + getTotalCompileSuccessful())));

    if(totalCorrectionTime != 0 && totalNumError != 0)
      compileMetrics.addIntData("Approximate_Average_Correction_Time",calculateAverageCorrectionTime(getTotalCorretionTime(),getTotalCorrected()));
    
    return compileMetrics;
  }


  /**
   * This method is called when compilation starts.
   * Its start time is recorded.
   * @param event BlueJ compile event that occurred
   */
  @Override 
  public void compileStarted(CompileEvent event){
    setStartCompileTime(System.currentTimeMillis());
    System.out.println("Compile started successfully.");
    
  }

  /**
   * This method is called when a compilation fails.
   * The time it failed is recorded and the method to calculate
   * compilation time elapsed is called. A call to increment the number 
   * of failed compilations is also called.
   * @param event BlueJ compile event that occurred
   */
  @Override 
  public void compileFailed(CompileEvent event){
    setEndCompileTime(System.currentTimeMillis());
    calculateTotalCompileTime(getCompileStartedTime(),getCompileEndedTime());
    incrementNumCompileFailed();
    System.out.println("Compile failed.");
  }

  /**
   * This method stores the time at whicn an error was first recorded
   * using the setErrorFixedStartTime method. A call to the updateErrorCount 
   * method is also made as well as key information about the error is printed to 
   * the BlueJ debugger log
   * @param event BlueJ compile event that occurred
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
   * This method is called when a compilation succeeds .
   * The time it succeeded is recorded and the method to calculate
   * compilation time elapsed is called. If there was a pending error
   * that was resolved, the elapsed time to correct that error(s) is also
   * calculated. A call to increment the number of successful compilations is also made.
   * @param event BlueJ compile event that occurred
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
   * This method prints out the warning message to the BlueJ debugger log
   * @param event BlueJ compile event that occurred
   */
  @Override 
  public void compileWarning(CompileEvent event){
    System.out.println("Compile warning: "+event.getErrorMessage());
  }

  /**
   * This method keeps running counts of specific types of errors
   * encountered while BlueJ is in session. 
   * @param errorMessage The String variable containing the specific compile error message
   */
  public void updateErrorCount(String errorMessage){
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
   * This method keeps a running total of compile times for an
   * entire BlueJ session.
   * @param compileStartedTime System currentTimeMills when compilation started
   * @param compileEndedTime System currentTimeMills when compilation ended
   */
  public synchronized void calculateTotalCompileTime(long compileStartedTime, long compileEndedTime){
    setTotalCompileTime(compileEndedTime - compileStartedTime);
  }

  /**
   * This method keeps a running count of the total time taken to
   * correct all errors preventing a successful compilation. If there was an error,
   * (errorFixedStartTime is not zero), then a call to reset errorFixedStartTime 
   * and errorFixedEndTime is made as well as a call to increment the total number 
   * of errors corrected.
   * @param errorFixedStartTime System currentTimeMills when compilation error was was recognised
   * @param errorFixedEndTime System currentTimeMills when no error detected and compilation was successful
   */
  public void calaculateTotalErrorFixedTime(long errorFixedStartTime, long errorFixedEndTime){
    if(errorFixedStartTime != (long)0){
      setTotalCorrectionTime(errorFixedStartTime,errorFixedEndTime);
      setErrorFixedEndTime((long)0);
      setErrorFixedStartTime((long)0);
      incrementTotalCorrected();
    }
  }

  public void incrementNumCompileSuccessful(){this.numCompileSuccessful++;}
  public void incrementNumCompileFailed(){this.numCompileFailed++;}
  public void incrementTotalExpectedErrorsCount(){this.expectedErrors++;}
  public void incrementTotalSymbolErrorsCount(){this.symbolError++;}
  public void incrementTotalUnclosedErrorsCount(){this.unclosedError++;}
  public void incrementTotalReturnErrorsCount(){this.returnError++;}
  public void incrementTotalOtherErrorsCount(){this.returnError++;}
  public void incrementTotalCorrected(){this.totalCorrected++;}

  public void setTotalCompileTime(long totalCompileTime){this.totalCompileTime = this.totalCompileTime + totalCompileTime;}
  public void setErrorFixedEndTime(long errorFixedEndTime){this.errorFixEndTime = errorFixedEndTime;}
  public void setTotalCorrectionTime(long errorFixedStartTime, long errorFixedEndTime){
    this.totalCorrectionTime += (errorFixedEndTime - errorFixedStartTime);
  }
  
  /**
   * This method sets errorFixedStartTime to the value passed only if
   * both its current value and the value of errorFixedEndTime is zero, 
   * or the value being passed in is zero.
   * @param errorFixedStartTime A new value for errorFixedStartTime
   */
  public void setErrorFixedStartTime(long errorFixedStartTime){
    if(getErrorFixedStartTime() == (long)0 && getErrorFixedEndTime() == (long)0 || errorFixedStartTime == 0){
      this.errorFixStartTime = errorFixedStartTime;
    }
  }

  /**
   * This method calculates the average compile time 
   * @param totalCompileTime running total of compile times for BlueJ session
   * @param totalNumberOfCompiles running count of compilations from BlueJ session (failed and successful).
   * @return an approximated average compile time in ms
   */
  public int calculateAverageCompileTime(long totalCompileTime, int totalNumberOfCompiles){
    return (int)totalCompileTime/totalNumberOfCompiles;
  }

  /**
   * This method calculates the average correction time 
   * @param totalCorrectionTime running total of time taken to correct errors preventing a successful compilation
   * @param totalCorrected running count of the number of times an error was corrected
   * @return an approximated average correction time in ms
   */
  public int calculateAverageCorrectionTime(long totalCorrectionTime, int totalCorrected){
    return (int)totalCorrectionTime/totalCorrected;
  }

  public void setStartCompileTime(long startCompileTime){this.compileStartTime = startCompileTime;}
  public void setEndCompileTime(long endCompileTime){this.compileEndTime = endCompileTime;}


  public int getTotalCompileSuccessful(){return this.numCompileSuccessful;}
  public int getTotalCompileFail(){return this.numCompileFailed;}
  public int getExpectedErrorCount(){return this.expectedErrors;}
  public int getSymbolErrorCount(){return this.symbolError;}
  public int getUnclodedErrorCount(){return this.unclosedError;}
  public int getReturnErrorCount(){return this.returnError;}
  public int getOtherErrorCount(){return this.otherError;}
  public int getTotalCorrected(){return this.totalCorrected;}
  
  /**
   * This method sums up all the compilation error encountered during
   * the session time.
   * @return Total count of error that occurred in the session
   */
  public int getTotalErrorCount(){
    this.totalNumError = getExpectedErrorCount()+ getSymbolErrorCount()+getUnclodedErrorCount()
    +getReturnErrorCount()+getOtherErrorCount();
    return this.totalNumError;
  }

  public long getCompileStartedTime(){return this.compileStartTime;}
  public long getCompileEndedTime(){return this.compileEndTime;}
  public long getErrorFixedStartTime(){return this.errorFixStartTime;}
  public long getErrorFixedEndTime(){return this.errorFixEndTime;}
  public long getTotalCompileTime(){return this.totalCompileTime;}
  public long getTotalCorretionTime(){return this.totalCorrectionTime;}
}
