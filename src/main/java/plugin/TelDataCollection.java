package plugin;

import bluej.extensions2.*;
import bluej.extensions2.event.*;
import java.net.URL;


public class TelDataCollection extends Extension implements CompileListener {
  private long startTime;
  private BlueJ bluejInstance;

  @Override 
  public void startup(BlueJ bluej){
    this.bluejInstance = bluej;
    bluej.addCompileListener(this);
    System.out.println("CompileTimerExtension started successfully.");
  } 

  

  @Override 
  public void terminate(){
    if (bluejInstance != null){
      bluejInstance.removeCompileListener(this);
    }
    System.out.println("CompileTimerExtension terminated.");
  }

  @Override 
  public void compileStarted(CompileEvent event){
    System.out.println("CompileTimerExtension started successfully.");
  }

  @Override 
  public void compileFailed(CompileEvent event){

  }

  @Override 
  public void compileError(CompileEvent event){
    
  }

  @Override 
  public void compileSucceeded(CompileEvent event){

  }

  @Override 
  public void compileWarning(CompileEvent event){

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
    return "TelDataCollection";
  }
}
