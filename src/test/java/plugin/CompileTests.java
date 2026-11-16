package plugin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;
import org.w3c.dom.events.Event;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import bluej.extensions2.event.PackageEvent;
import bluej.extensions2.event.ClassEvent.EventType;
public class CompileTests {
  static CompileDataCollection testerCompileListener;
  @BeforeAll
  static void setup(){
    testerCompileListener = new CompileDataCollection();
  }

  @Test
  void TestForExpectedErrorCount(){
    testerCompileListener.incrementTotalExpectedErrorsCount();
    assertEquals(1,testerCompileListener.getExpectedErrorCount());
  }

  @Test
  void TestForSysmbolErrorCount(){
    testerCompileListener.incrementTotalSymbolErrorsCount();
    testerCompileListener.incrementTotalSymbolErrorsCount();
    assertEquals(2,testerCompileListener.getSymbolErrorCount());
  }

  @Test
  void TestForTotalErrorCount(){
    testerCompileListener.incrementTotalSymbolErrorsCount();
    testerCompileListener.incrementTotalSymbolErrorsCount();
    testerCompileListener.incrementTotalOtherErrorsCount();
    testerCompileListener.incrementTotalUnclosedErrorsCount();
    testerCompileListener.incrementTotalReturnErrorsCount();
    assertEquals(5,testerCompileListener.getTotalErrorCount());
  }

  @Test
  void TestForCompileStartTime(){
    testerCompileListener.setStartCompileTime((long)200);
    assertEquals((long)200,testerCompileListener.getCompileStartedTime());
  }

  @Test
  void TestForCompileEndTime(){
    testerCompileListener.setEndCompileTime((long)300);
    assertEquals((long)300,testerCompileListener.getCompileEndedTime());
  }

  @Test
  void TestForTotalCompileTime(){
    testerCompileListener.calculateTotalCompileTime((long)200,(long)300);
    assertEquals((long)100,testerCompileListener.getTotalCompileTime());
  }

  @Test
  void TestForNumberOfFailedCompiles(){
    testerCompileListener.incrementNumCompileFailed();
    testerCompileListener.incrementNumCompileFailed();
    testerCompileListener.incrementNumCompileFailed();
    assertEquals(3,testerCompileListener.getTotalCompileFail());
  }

  @Test
  void TestForNumberOfSuccessfulCompiles(){
    testerCompileListener.incrementNumCompileSuccessful();
    testerCompileListener.incrementNumCompileSuccessful();
    assertEquals(2,testerCompileListener.getTotalCompileSuccessful());
  }

  @Test
  void TestForSetErrorFixedStartTime(){
    testerCompileListener.setErrorFixedStartTime((long)1000);
    assertEquals(1000,testerCompileListener.getErrorFixedStartTime());
  }

   @Test
  void TestForSetErrorFixedStartTimeWhenAlreadySet(){
    testerCompileListener.setErrorFixedStartTime((long)2500);
    testerCompileListener.setErrorFixedStartTime((long)4500);
    assertEquals(2500,testerCompileListener.getErrorFixedStartTime());
  }

  @Test
  void TestForSetErrorFixedEndTime(){
    testerCompileListener.setErrorFixedEndTime((long)2000);
    assertEquals(2000,testerCompileListener.getErrorFixedEndTime());
  }
 

  @Test
  void TestForUpdateErrorCount(){
    testerCompileListener.updateErrorCount("expected");
    testerCompileListener.updateErrorCount("expected");
    assertEquals(2,testerCompileListener.getExpectedErrorCount());
  }

  @Test
  void TestForSimpleSetAverageCorrectionTime(){
    testerCompileListener.setErrorFixedStartTime((long)1000);
    testerCompileListener.setErrorFixedEndTime((long)2300);
    testerCompileListener.calaculateTotalErrorFixedTime(testerCompileListener.getErrorFixedStartTime(), testerCompileListener.getErrorFixedEndTime());
    System.out.println("Current Error Fixed End Time: " + testerCompileListener.getErrorFixedEndTime());
    assertEquals(1300,testerCompileListener.getTotalCorretionTime());
  }

  @Test
  void TestForComplexSetAverageCorrectionTime1(){
    testerCompileListener.setErrorFixedStartTime((long)1000);
    testerCompileListener.setErrorFixedEndTime((long)2300);
    testerCompileListener.setErrorFixedEndTime((long)5000);
    testerCompileListener.setErrorFixedEndTime((long)6000);
    testerCompileListener.setErrorFixedEndTime((long)8800);
    testerCompileListener.calaculateTotalErrorFixedTime(testerCompileListener.getErrorFixedStartTime(), testerCompileListener.getErrorFixedEndTime());
    
    testerCompileListener.setErrorFixedStartTime((long)9000);
    testerCompileListener.setErrorFixedEndTime((long)10100);
    testerCompileListener.setErrorFixedEndTime((long)12000);
    testerCompileListener.calaculateTotalErrorFixedTime(testerCompileListener.getErrorFixedStartTime(),testerCompileListener.getErrorFixedEndTime());
    assertEquals(10800,testerCompileListener.getTotalCorretionTime());
  }

  @Test
  void TestForComplexSetAverageCorrectionTime2(){
    testerCompileListener.setErrorFixedStartTime((long)2500);
    testerCompileListener.setErrorFixedEndTime((long)9000);
    testerCompileListener.calaculateTotalErrorFixedTime(testerCompileListener.getErrorFixedStartTime(), testerCompileListener.getErrorFixedEndTime());
    
    testerCompileListener.setErrorFixedStartTime((long)9450);
    testerCompileListener.setErrorFixedEndTime((long)9600);
    testerCompileListener.setErrorFixedEndTime((long)9900);
    testerCompileListener.calaculateTotalErrorFixedTime(testerCompileListener.getErrorFixedStartTime(),testerCompileListener.getErrorFixedEndTime());
    
    testerCompileListener.setErrorFixedStartTime((long)20010);
    testerCompileListener.setErrorFixedEndTime((long)24100);
    testerCompileListener.setErrorFixedEndTime((long)24205);
    testerCompileListener.calaculateTotalErrorFixedTime(testerCompileListener.getErrorFixedStartTime(),testerCompileListener.getErrorFixedEndTime());
    
    assertEquals(11145,testerCompileListener.getTotalCorretionTime());
  }

  @Test
  void TestForCalculateAvarageCompileTimeSimple(){
    testerCompileListener.setStartCompileTime((long)100);
    testerCompileListener.setEndCompileTime((long)500);
    testerCompileListener.incrementNumCompileFailed();
    testerCompileListener.calculateTotalCompileTime(testerCompileListener.getCompileStartedTime(),testerCompileListener.getCompileEndedTime());
    assertEquals(400,testerCompileListener.calculateAverageCompileTime(testerCompileListener.getTotalCompileTime(),testerCompileListener.getTotalCompileSuccessful()+testerCompileListener.getTotalCompileFail()));
  }

  @Test
  void TestForCalculateAvarageCompileTimeComplex1(){
    testerCompileListener.setStartCompileTime((long)100);
    testerCompileListener.setEndCompileTime((long)500);
    testerCompileListener.incrementNumCompileFailed();
    testerCompileListener.calculateTotalCompileTime(testerCompileListener.getCompileStartedTime(),testerCompileListener.getCompileEndedTime());

    testerCompileListener.setStartCompileTime((long)900);
    testerCompileListener.setEndCompileTime((long)1200);
    testerCompileListener.incrementNumCompileFailed();
    testerCompileListener.calculateTotalCompileTime(testerCompileListener.getCompileStartedTime(),testerCompileListener.getCompileEndedTime());

    testerCompileListener.setStartCompileTime((long)1400);
    testerCompileListener.setEndCompileTime((long)1570);
    testerCompileListener.incrementNumCompileSuccessful();
    testerCompileListener.calculateTotalCompileTime(testerCompileListener.getCompileStartedTime(),testerCompileListener.getCompileEndedTime());

    assertEquals(290,testerCompileListener.calculateAverageCompileTime(testerCompileListener.getTotalCompileTime(),testerCompileListener.getTotalCompileSuccessful()+testerCompileListener.getTotalCompileFail()));
  }


  @Test
  void TestForCalculateAvarageCompileTimeComplex2(){
    testerCompileListener.setStartCompileTime((long)100);
    testerCompileListener.setEndCompileTime((long)500);
    testerCompileListener.incrementNumCompileFailed();
    testerCompileListener.calculateTotalCompileTime(testerCompileListener.getCompileStartedTime(),testerCompileListener.getCompileEndedTime());

    testerCompileListener.setStartCompileTime((long)900);
    testerCompileListener.setEndCompileTime((long)1200);
    testerCompileListener.incrementNumCompileFailed();
    testerCompileListener.calculateTotalCompileTime(testerCompileListener.getCompileStartedTime(),testerCompileListener.getCompileEndedTime());

    testerCompileListener.setStartCompileTime((long)1400);
    testerCompileListener.setEndCompileTime((long)1570);
    testerCompileListener.incrementNumCompileSuccessful();
    testerCompileListener.calculateTotalCompileTime(testerCompileListener.getCompileStartedTime(),testerCompileListener.getCompileEndedTime());

    testerCompileListener.setStartCompileTime((long)1900);
    testerCompileListener.setEndCompileTime((long)2533);
    testerCompileListener.incrementNumCompileFailed();
    testerCompileListener.calculateTotalCompileTime(testerCompileListener.getCompileStartedTime(),testerCompileListener.getCompileEndedTime());

    assertEquals(375,testerCompileListener.calculateAverageCompileTime(testerCompileListener.getTotalCompileTime(),testerCompileListener.getTotalCompileSuccessful()+testerCompileListener.getTotalCompileFail()));
  }

  @Test
  void TestForCalculateAverageCorrectionTimeSimple(){
    testerCompileListener.setErrorFixedStartTime((long)100);
    testerCompileListener.setErrorFixedEndTime((long)900);
    testerCompileListener.setTotalCorrectionTime(testerCompileListener.getErrorFixedStartTime(),testerCompileListener.getErrorFixedEndTime());
    testerCompileListener.incrementTotalCorrected();

    assertEquals(800,testerCompileListener.calculateAverageCorrectionTime(testerCompileListener.getTotalCorretionTime(), testerCompileListener.getTotalCorrected()));
  }

  @Test
  void TestForCalculateAverageCorrectionTimeComplex(){
    testerCompileListener.setErrorFixedStartTime((long)100);
    testerCompileListener.setErrorFixedEndTime((long)900);
    testerCompileListener.calaculateTotalErrorFixedTime(testerCompileListener.getErrorFixedStartTime(),testerCompileListener.getErrorFixedEndTime());

    testerCompileListener.setErrorFixedStartTime((long)2053);
    testerCompileListener.setErrorFixedEndTime((long)3021);
    testerCompileListener.calaculateTotalErrorFixedTime(testerCompileListener.getErrorFixedStartTime(),testerCompileListener.getErrorFixedEndTime());
    
    testerCompileListener.setErrorFixedStartTime((long)170700);
    testerCompileListener.setErrorFixedEndTime((long)170812);
    testerCompileListener.calaculateTotalErrorFixedTime(testerCompileListener.getErrorFixedStartTime(),testerCompileListener.getErrorFixedEndTime());

    assertEquals(626,testerCompileListener.calculateAverageCorrectionTime(testerCompileListener.getTotalCorretionTime(), testerCompileListener.getTotalCorrected()));
  }
}
