package plugin;

import bluej.extensions2.*;
import bluej.extensions2.event.*;
import java.net.URL;


public class TelDataCollection implements CompileListener {
  

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

}
