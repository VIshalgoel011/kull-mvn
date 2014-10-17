package com.kull.jfc;

import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;

import com.kull.struts.AwareActionSupport;

public abstract class ChartActionSupport extends AwareActionSupport {
	protected String title=this.getClass().getName();
	protected int width=400,height=300;
	protected boolean legend=true,tooltips=true,is3D=true,urls=false;
	protected String calabel="calabel",valabel="valabel";
	protected PlotOrientation por=PlotOrientation.VERTICAL;
	
	protected String xalabel="xalabel",yalabel="yalabel",theme="CN";
	
	protected boolean gridline=true,dateAxis=false;
	
	
	
public void png() throws IOException{
		
		this.response.setContentType("image/png");
		StandardChartTheme standardChartTheme=new StandardChartTheme(theme);
		setChartTheme(standardChartTheme);
		JFreeChart jFreeChart=createChart();
		preRender(jFreeChart);
		ChartUtilities.writeChartAsPNG(this.response.getOutputStream(), jFreeChart, width, height);
	}
	
   protected abstract JFreeChart createChart();

    public void jpeg() throws IOException{
		
		this.response.setContentType("image/jpeg");
		StandardChartTheme standardChartTheme=new StandardChartTheme(theme);
		setChartTheme(standardChartTheme);
		JFreeChart jFreeChart=createChart();
		preRender(jFreeChart);
		ChartUtilities.writeChartAsJPEG(this.response.getOutputStream(), jFreeChart, width, height);
	}

    public void preRender(JFreeChart chart){
    	
    }
    
    protected void setChartTheme(StandardChartTheme standardChartTheme){   	
    	   ChartFactory.setChartTheme(standardChartTheme); 
    }
    
	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}


	
	public void setTitle(String title){
	      this.title=title;
	}
	
	
	
	public void setLegend(boolean legend) {
		this.legend = legend;
	}



	public void setTooltips(boolean tooltips) {
		this.tooltips = tooltips;
	}



	public void setIs3D(boolean is3d) {
		is3D = is3d;
	}



	public void setUrls(boolean urls) {
		this.urls = urls;
	}

	public void setCalabel(String calabel) {
		this.calabel = calabel;
	}

	public void setValabel(String valabel) {
		this.valabel = valabel;
	}

	public void setPor(String por) {
		this.por="h".equals(por)?PlotOrientation.HORIZONTAL:PlotOrientation.VERTICAL;
	}
	
	


	public void setXalabel(String xalabel) {
		this.xalabel = xalabel;
	}

	public void setYalabel(String yalabel) {
		this.yalabel = yalabel;
	}

	

	public void setGridline(boolean gridline) {
		this.gridline = gridline;
	}

	public void setDateAxis(boolean dateAxis) {
		this.dateAxis = dateAxis;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}
	
	
}
