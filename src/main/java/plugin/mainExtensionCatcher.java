package plugin;

import bluej.extensions2.*;
import bluej.extensions2.event.*;
import java.net.URL;
public class mainExtensionCatcher extends Extension implements ExtensionEventListener {
  private long startTime;
  private BlueJ bluejInstance;
  
  @Override
  public void eventOccurred(ExtensionEvent event){
    System.out.println("Event occurred:"+event.getClass().getName());
  }

  @Override 
  public void startup(BlueJ bluej){
    this.bluejInstance = bluej;
    bluejInstance.addExtensionEventListener(this);
    //bluej.addCompileListener(this);
    System.out.println("Extension started successfully.");
  } 


  @Override 
  public void terminate(){
    /* if (bluejInstance != null){
      bluejInstance.removeCompileListener(this);
    } */
    System.out.println("Extension terminated.");
  }

  @Override 
  public boolean isCompatible(){
    return true;
  }

  @Override 
  public String getVersion(){
    return "1.0";
  }

  @Override 
  public String getName(){
    return "mainExtensionCatcher";
  } 
}
