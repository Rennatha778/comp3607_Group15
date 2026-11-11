package plugin;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class ActivityAnalyzerTest {


    @Test
    public void testActiveTime() {
        long actual = ActivityAnalyzer.activeTime(120000, 45000); // testing a 2min code session wiith 45 secs idle time

        Assertions.assertEquals(75000, actual);
    }

    @Test
    public void testIdleTimeBelowThreshold() {

        long actual = ActivityAnalyzer.idleBeyondThreshold(45000, 120000); //testing 45 secs of inactive with a 2 min idle tresh

        Assertions.assertEquals(0, actual);
    }

    @Test
    public void testIdleTimeAboveThreshold() {

        long actual = ActivityAnalyzer.idleBeyondThreshold(120000, 60000); // testing 2mins of inactive with 1min idle threshold

        Assertions.assertEquals(60000, actual);
    }

    @Test
    public void testCompileSuccessRate() {

        double actual = ActivityAnalyzer.compileSuccessRate(3, 2); // testing 3 success comp and 2 fail comp

        Assertions.assertEquals(60.0, actual, 0.001);
    }

    @Test
    public void testZeroCompiles() {

        double actual = ActivityAnalyzer.compileSuccessRate(0, 0); // testing no compilation attempts

        Assertions.assertEquals(0.0, actual, 0.001);
    }

}
