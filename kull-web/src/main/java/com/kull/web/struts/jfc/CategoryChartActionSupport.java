package com.kull.web.struts.jfc;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;

import org.jfree.chart.plot.CategoryPlot;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;


import com.kull.Stringz;


public abstract class CategoryChartActionSupport extends ChartActionSupport{

	public enum Mode {
		bar,area,line
	}
	
	protected Mode mode=Mode.bar;
	protected String ccats,vcats,vals;
	@Override
	protected final JFreeChart createChart() {
		// TODO Auto-generated method stub
		JFreeChart jFreeChart=null;
		CategoryDataset dataset=createDataset();
		switch(mode){
		case bar:{
			if(is3D){
				jFreeChart=ChartFactory.createBarChart3D(title, calabel, valabel, dataset, por, legend, tooltips, urls);
			}else{
				jFreeChart=ChartFactory.createBarChart(title, calabel, valabel, dataset, por, legend, tooltips, urls);
			}
			break;
		}
		case area:{
			jFreeChart=ChartFactory.createAreaChart(title, calabel, valabel, dataset, this.por, legend, tooltips, urls);
			}
		break;
		case line:{
			if(is3D){
				jFreeChart=ChartFactory.createLineChart3D(title, calabel, valabel, dataset, por, legend, tooltips, urls);
			}else{
				jFreeChart=ChartFactory.createLineChart(title, calabel, valabel, dataset, por, legend, tooltips, urls);
			}
			break;
		}
		}
		
		CategoryPlot plot=jFreeChart.getCategoryPlot();
		preRender(plot);
		return jFreeChart; 
	}

	protected CategoryDataset createDataset(){
		DefaultCategoryDataset dataset=new DefaultCategoryDataset();
		if(Stringz.isBlank(ccats,vcats,vals))return dataset;
		String[] arrayCcats=ccats.split(","),arrayVcats=vcats.split(","),arrayVals=vals.split(",");
		int index=0;
		for(int i=0;i<arrayCcats.length;i++){
			for(int j=0;j<arrayVcats.length;j++){
				
				Double d=Double.parseDouble(arrayVals[index++]);
				dataset.addValue(d,arrayCcats[i] , arrayVcats[j]);
			}
		}
		return dataset;
	} 
	
	protected void preRender(CategoryPlot plot){}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	public void setCcats(String ccats) {
		this.ccats = ccats;
	}

	public void setVcats(String vcats) {
		this.vcats = vcats;
	}

	public void setVals(String vals) {
		this.vals = vals;
	}

	

	

}
