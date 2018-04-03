package be.enyed.zkboot.websocket;

import org.zkoss.chart.Charts;
import org.zkoss.chart.Point;
import org.zkoss.lang.Threads;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

/**
 * origin:
 * https://github.com/zkoss-demo/zkwebsocket-demo/blob/master/src/main/java/org/zkoss/blog/demo/zkwebsocketdemo/WebSocketDemoServerPush.java
 */
public class WebSocketDemoServerPush {

  public static void start(Charts chart) {
    final Desktop desktop = Executions.getCurrent().getDesktop();
    if (desktop.isServerPushEnabled()) {
      Messagebox.show("Already started");
    } else {
      desktop.enableServerPush(true);
      new WorkingThread(chart).start();
    }
  }

  private static class WorkingThread extends Thread {

    private final Desktop _desktop;

    private final Charts _chart;

    public WorkingThread(Charts chart) {
      _chart = chart;
      _desktop = chart.getDesktop();
    }
    @Override
    public void run() {
      while (_desktop.isServerPushEnabled()) {
        final long l = System.currentTimeMillis();
        Executions.schedule(_desktop,
            event -> _chart.getSeries().addPoint(new Point(l, Math.random()), true, true, true), null);
        Threads.sleep(1000);
      }
    }
  }
}
