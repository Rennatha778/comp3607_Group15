package plugin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;
import org.w3c.dom.events.Event;

import bluej.extensions2.event.PackageEvent;
import bluej.extensions2.event.ClassEvent.EventType;

public class ClassTests {
  @Test
  void classListenerCreated(){
    PackageDataCollection packageListener = new PackageDataCollection();
  }
}
