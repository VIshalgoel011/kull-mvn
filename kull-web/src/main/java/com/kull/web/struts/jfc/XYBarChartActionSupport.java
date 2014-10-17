package com.kull.web.struts.jfc;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.IntervalXYDataset;

public abstract class XYBarChartActionSupport extends ChartActionSupport {

	
	
	@Override
	protected final JFreeChart createChart() {
		// TODO Auto-generated method stub
		JFreeChart jFreeChart=null;
		IntervalXYDataset dataset=createDataset();
		
		jFreeChart=ChartFactory.createXYBarChart(title,xalabel, dateAxis, yalabel,dataset, por, legend, tooltips, urls);
		
		XYPlot plot=(XYPlot)jFreeChart.getPlot();
		preRender(plot);
		return jFreeChart; 
	}

	protected abstract IntervalXYDataset createDataset() ;
	
	protected void preRender(XYPlot plot){}

	

	

}
