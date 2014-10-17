package com.kull.web.struts.jfc;



import org.jfree.chart.ChartFactory;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;

import org.jfree.chart.plot.RingPlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import com.kull.StringHelper;


public abstract class PieChartActionSupport extends ChartActionSupport {

	
	protected String keys,vals;
	
	
	public enum Mode{
		pie,ring
	}
	
	protected Mode mode=Mode.pie;


	protected PieDataset createDataset(){
		DefaultPieDataset dataset=new DefaultPieDataset();
		if(StringHelper.isBlank(keys,vals))return dataset;
		String[] arrayKeys=keys.split(","),arrayVals=vals.split(",");
		if(arrayKeys==null||arrayVals==null||arrayKeys.length==0||arrayKeys.length!=arrayVals.length)return dataset;
		for(int i=0;i<arrayKeys.length;i++){
			Double d=Double.parseDouble(arrayVals[i]);
			dataset.setValue(arrayKeys[i],d );
		}
		return dataset;
	}
	
	protected final JFreeChart createChart(){
		JFreeChart jFreeChart=null;
		PieDataset dataset=createDataset();
		switch(mode){
		case pie:{
			if(is3D){
				jFreeChart=ChartFactory.createPieChart3D(title, dataset, legend, tooltips, urls);
				PiePlot3D plot=(PiePlot3D)jFreeChart.getPlot();
				preRender( plot);
				
			}else{
				jFreeChart=ChartFactory.createPieChart(title, dataset, legend, tooltips, urls);
				PiePlot plot=(PiePlot)jFreeChart.getPlot();
				preRender( plot);
			
			}
			break;
		  }
		case ring:{
			jFreeChart=ChartFactory.createRingChart(title, dataset, legend, tooltips, urls);
			RingPlot plot=(RingPlot)jFreeChart.getPlot();
			preRender( plot);
			break;
		 }
		}
		
		
		
		return jFreeChart; 
	}
	
	protected void preRender(PiePlot plot){}
	protected void preRender(PiePlot3D plot){}
	protected void preRender(RingPlot plot){}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public void setVals(String vals) {
		this.vals = vals;
	}
	
	
}
