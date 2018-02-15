package be.enyed.zkboot.websocket;

import org.zkoss.chart.Charts;
import org.zkoss.chart.Options;
import org.zkoss.chart.PlotLine;
import org.zkoss.chart.Point;
import org.zkoss.chart.Series;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;

public class WebSocketDemo extends SelectorComposer<Div> {

  private static final long serialVersionUID = 156579761025294059L;
  
  @Wire
  Charts chart;

  @Override
  public void doAfterCompose(Div comp) throws Exception {
    initializeChart(comp);
    WebSocketDemoServerPush.start(chart);
  }

  private void initializeChart(Div comp) throws Exception {
    super.doAfterCompose(comp);

    Options options = new Options();
    options.getGlobal().setUseUTC(false);
    chart.setOptions(options);
    chart.setAnimation(true);

    chart.getXAxis().setType("datetime");
    chart.getXAxis().setTickPixelInterval(150);

    chart.getYAxis().setTitle("Value");
    PlotLine plotLine = new PlotLine();
    plotLine.setValue(0);
    plotLine.setWidth(1);
    plotLine.setColor("#808080");
    chart.getYAxis().addPlotLine(plotLine);

    chart.getTooltip().setHeaderFormat("<b>{series.name}</b><br/>");
    chart.getTooltip().setPointFormat("{point.x:%Y-%m-%d %H:%M:%S}<br>{point.y}");

    chart.getLegend().setEnabled(false);
    chart.getExporting().setEnabled(false);

    Series series = chart.getSeries();
    series.setName("Random data");
    long l = System.currentTimeMillis();
    for (int i = -19; i < 0; i++) {
      Point point = new Point(l + i * 1000, Math.random());
      series.addPoint(point);
    }
  }
}