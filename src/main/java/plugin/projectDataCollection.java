package plugin;

import bluej.extensions2.event.PackageEvent;
import bluej.extensions2.event.PackageListener;
import bluej.pkgmgr.Project;

public class projectDataCollection implements PackageListener{

  @Override 
  public void packageOpened (PackageEvent packageEvent){
    System.out.println("PACKAGE OPENED: "+packageEvent.toString());
  }

  @Override 
  public void packageClosing(PackageEvent packageEvent){
    System.out.println("PACKAGE CLOSED: "+packageEvent.toString());
  }
  
}
