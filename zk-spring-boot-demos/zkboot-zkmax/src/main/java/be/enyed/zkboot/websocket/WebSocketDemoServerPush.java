package be.enyed.zkboot.websocket;

import org.zkoss.chart.Charts;
import org.zkoss.chart.Point;
import org.zkoss.lang.Threads;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;

import java.util.concurrent.TimeUnit;

/**
 * Created by wenninghsu on 14/06/2017.
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
    public void run() {
      while (_desktop.isServerPushEnabled()) {
        final long l = System.currentTimeMillis();
        Executions.schedule(_desktop,
            new EventListener() {
              public void onEvent(Event event) {
                _chart.getSeries().addPoint(new Point(l, Math.random()), true, true, true);
              }
            }, null);
        Threads.sleep(1000);
      }
    }
  }
}
