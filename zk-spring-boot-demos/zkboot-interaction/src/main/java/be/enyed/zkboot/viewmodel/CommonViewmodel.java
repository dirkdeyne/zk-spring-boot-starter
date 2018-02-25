package be.enyed.zkboot.viewmodel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

public class CommonViewmodel {
  
  @Command
  public void showAbout() {
   ((Window) Executions.createComponents("~./common/about.zul", null, null)).doHighlighted();
  }
  
}
