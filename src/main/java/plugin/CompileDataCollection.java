package plugin;

import bluej.extensions2.event.*;

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

    //totalNumError = expectedErrors+sysmbolError+unclosedError+returnError+otherError;

    compileMetrics.addIntData("Total Errors",getTotalErrorCount());

    if(getTotalCompileFail() != 0 || getTotalCompileSuccessful() != 0)
      compileMetrics.addIntData("Approximate_Average_Compile_Time",calculateAverageCompileTime(getTotalCompileTime(),(getTotalCompileFail() + getTotalCompileSuccessful())));

    if(totalCorrectionTime != 0 && totalNumError != 0)
      compileMetrics.addIntData("Approximate_Average_Correction_Time",calculateAverageCorrectionTime(getTotalCorretionTime(),getTotalCorrected()));
    
    return compileMetrics;
  }



  @Override 
  public void compileStarted(CompileEvent event){
    //compileStartTime = System.currentTimeMillis();
    setStartCompileTime(System.currentTimeMillis());
    System.out.println("Compile started successfully.");
    
  }

  @Override 
  public void compileFailed(CompileEvent event){
    setEndCompileTime(System.currentTimeMillis());
    System.out.println("Compile failed.");
    //compileEndTime = System.currentTimeMillis();
    calculateTotalCompileTime(getCompileStartedTime(),getCompileEndedTime());
    incrementNumCompileFailed();
  }

  @Override 
  public void compileError(CompileEvent event){
    setErrorFixedStartTime(System.currentTimeMillis());
    /* if(errorFixStartTime != 0)
      errorFixStartTime = System.currentTimeMillis(); */
    updateErrorCount(event.getErrorMessage());
     System.out.println("Compile error line number: " + event.getErrorLineNumber());
     System.out.println("Compile error message: " + event.getErrorMessage());
     System.out.println("Compile position: " + event.getErrorPosition());

  }

  @Override 
  public void compileSucceeded(CompileEvent event){
    setErrorFixedEndTime(System.currentTimeMillis());
    //errorFixEndTime = System.currentTimeMillis();
    setEndCompileTime(getErrorFixedEndTime());

   /*  if(errorFixStartTime != 0){
      averageCorrectionTime = averageCorrectionTime + (errorFixEndTime - errorFixStartTime);
      errorFixStartTime = 0;
      errorFixEndTime = 0;
    } */

    calaculateTotalErrorFixedTime(getErrorFixedStartTime(),getErrorFixedEndTime());

     System.out.println("Compile successful.");
     //compileEndTime = System.currentTimeMillis();
     calculateTotalCompileTime(getCompileStartedTime(),getCompileEndedTime());
     incrementNumCompileSuccessful();
  }

  @Override 
  public void compileWarning(CompileEvent event){
    System.out.println("Compile warning: "+event.getErrorMessage());
  }

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

  protected synchronized void calculateTotalCompileTime(long compileStartedTime, long compileEndedTime){
    setTotalCompileTime(compileEndedTime - compileStartedTime);
    //return getTotalCompileTime();
  }

  protected void calaculateTotalErrorFixedTime(long errorFixedStartTime, long errorFixedEndTime){
    if(errorFixedStartTime != (long)0){
      //averageCorrectionTime = averageCorrectionTime + (errorFixEndTime - errorFixStartTime);
      setTotalCorrectionTime(errorFixedStartTime,errorFixedEndTime);

      //errorFixStartTime = 0;
      //errorFixEndTime = 0;
      
      setErrorFixedEndTime((long)0);
      setErrorFixedStartTime((long)0);
      incrementTotalCorrected();
    }
    //return this.averageCorrectionTime;
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
