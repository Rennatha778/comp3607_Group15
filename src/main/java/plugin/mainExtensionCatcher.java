package plugin;

import bluej.BlueJEventListener;
import bluej.editor.TextEditor;
import bluej.extensions2.*;
import bluej.extensions2.event.*;
import java.net.URL;
import java.net.http.WebSocket.Listener;
import java.util.ArrayList;
import java.util.TreeMap;

public  class mainExtensionCatcher extends Extension implements ExtensionEventListener, BluejListeners {
  private long startTime;
  private long endTime;
  private BlueJ bluejInstance;
  private TelDataCollection compileListner;
  private classDataCollection classListner;
  private InvocationFinishedDataCollection invokeListner;
  private projectDataCollection packageListner;
  private long totalIdleTime = 0;
  private long prevEvent = 0;
  private long currentEvent = 0;

  private int numCompileEvent = 0;
  private int numPackageEvent = 0;
  private int numClassEvent = 0;
  private int numInvokeFinishedEvent = 0;
  private int numApplicationEvent = 0;
  PackageEvent newPackageEvent;

  
  @Override 
  public void startup(BlueJ bluej){
    startTime = System.currentTimeMillis();
    prevEvent = startTime;
    this.bluejInstance = bluej;
  
    compileListner = new TelDataCollection();
    classListner = new classDataCollection();
    packageListner = new projectDataCollection();
    invokeListner = new InvocationFinishedDataCollection();
    
    
    bluejInstance.addExtensionEventListener(this);
    bluejInstance.addCompileListener(compileListner);
    bluejInstance.addClassListener(classListner);
    bluejInstance.addPackageListener(packageListner);
    bluejInstance.addInvocationFinishedListener(invokeListner);
    System.out.println("Extension started successfully.");
  } 

  @Override
  public void eventOccurred(ExtensionEvent event){
    currentEvent = System.currentTimeMillis();
    System.out.println("Event occurred:"+event.getClass().getSimpleName());
    if((currentEvent - prevEvent) > 60000){
      totalIdleTime = totalIdleTime + (currentEvent - prevEvent);
    }
    prevEvent = currentEvent;

    if(event instanceof ApplicationEvent){
      numApplicationEvent++;
    }else if(event instanceof CompileEvent){
      numCompileEvent++;
    }else if (event instanceof ClassEvent){
      if(this.bluejInstance.getCurrentPackage() != null){
        classListner.getPackageInfo(this.bluejInstance.getCurrentPackage());
        System.out.println("PackageInfo sent");
      }
      
      numClassEvent++;
    }else if (event instanceof PackageEvent){
      numPackageEvent++;
      newPackageEvent = (PackageEvent) event;
      if(newPackageEvent != null){
        classListner.getPackageInfo(newPackageEvent.getPackage());
      }
      
    }else if( event instanceof InvocationFinishedEvent){
      numInvokeFinishedEvent++;
    }
  }


  @Override 
  public void terminate(){
    endTime = System.currentTimeMillis();
    
    TreeMap<String,Data> tr = new TreeMap<String,Data>();
    tr.put("packageListner",packageListner.getData());
    tr.put("classListner",classListner.getData());
    tr.put("compileListner",compileListner.getData());
    tr.put("invokeListner",invokeListner.getData());
    tr.put("main extension listener",this.getData());


    generateReport(tr);

    if (bluejInstance != null){
      //bluejInstance.removeExtensionEventListener(this);
      bluejInstance.removePackageListener(packageListner);
      bluejInstance.removeClassListener(classListner);
      bluejInstance.removeCompileListener(compileListner);
      bluejInstance.removeInvocationFinishedListener(invokeListner);
    }

    System.out.println("Extension terminated.");
    System.out.println("Total sesstion time: "+ (endTime-startTime));
    System.out.println("Total Idle time: "+totalIdleTime);
    System.out.println("Total active time: "+((endTime-startTime) - totalIdleTime));
    //generateReport();
  }

  public void generateReport(TreeMap<String,Data> tr){
    ReportPublisher rp = new CreatingJSONFile();
    ReportPublisher jp = new CreatingPDFFile();

    rp.generateReport(tr);
    jp.generateReport(tr);

    System.out.println("Report generated");

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

  public long getTotalSessionTime(){
    if(this.endTime != 0 && this.startTime != 0)
      return this.endTime - this.startTime;
    return 0;
  }

  public Data getData(){
    Data d = new Data();
    d.addLongData("totalSessionTime",getTotalSessionTime());
    d.addLongData("totalActiveTime",(getTotalSessionTime() - totalIdleTime));
    d.addLongData("totalIdleTime",totalIdleTime);

    d.addIntData("Application Events", numApplicationEvent);
    d.addIntData("Class Events", numClassEvent);
    d.addIntData("Compile Events", numCompileEvent);
    d.addIntData("Invoke Finished Events", numInvokeFinishedEvent);
    d.addIntData("Package Events", numPackageEvent);

    return d;
  }
}
