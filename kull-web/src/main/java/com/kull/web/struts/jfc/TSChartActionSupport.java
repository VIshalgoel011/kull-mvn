package com.kull.jfc;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;

public abstract class TSChartActionSupport extends ChartActionSupport{

	protected String talabel="talabel",valabel="valabel";
	@Override
	protected final JFreeChart createChart() {
		// TODO Auto-generated method stub
		XYDataset dataset=createDataSet();
		JFreeChart chart=ChartFactory.createTimeSeriesChart(title, talabel, valabel, dataset, legend, tooltips, urls);
		XYPlot plot=chart.getXYPlot();
		preRender(plot);
		return chart;
	}

	protected abstract XYDataset createDataSet();

	protected void preRender(XYPlot plot){
		
	}
}
