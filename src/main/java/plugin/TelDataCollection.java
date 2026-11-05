package plugin;

import bluej.extensions2.*;
import bluej.extensions2.event.*;
import java.net.URL;


public class TelDataCollection implements CompileListener, ExtensionEvent {
  

  @Override 
  public void compileStarted(CompileEvent event){
    System.out.println("Compile started successfully.");
  }

  @Override 
  public void compileFailed(CompileEvent event){
    System.out.println("Compile failed.");
  }

  @Override 
  public void compileError(CompileEvent event){
     System.out.println("Compile error line number: " + event.getErrorLineNumber());
     System.out.println("Compile error message: " + event.getErrorMessage());
     System.out.println("Compile position: " + event.getErrorPosition());
  }

  @Override 
  public void compileSucceeded(CompileEvent event){
     System.out.println("Compile successful.");
  }

  @Override 
  public void compileWarning(CompileEvent event){
    System.out.println("Compile warning!");
  }

}
