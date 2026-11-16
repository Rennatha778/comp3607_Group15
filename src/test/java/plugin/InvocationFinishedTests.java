package plugin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;

public class InvocationFinishedTests {
  static InvocationFinishedDataCollection testerInvocationFinished;
  
  @BeforeAll
  static void setup(){
    testerInvocationFinished = new InvocationFinishedDataCollection();
  }

  @Test
  void TestForNumberOfMethodsRan(){
    testerInvocationFinished.incrmentNumberOfMethodRuns();
    testerInvocationFinished.incrmentNumberOfMethodRuns();
    assertEquals(2,testerInvocationFinished.getNumberOfMethodRuns());
  }
}
