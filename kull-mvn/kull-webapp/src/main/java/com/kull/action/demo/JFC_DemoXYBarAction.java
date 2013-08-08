package com.kull.action.demo;

import java.util.Random;

import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYBarDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.kull.jfc.XYBarChartActionSupport;

public class JFC_DemoXYBarAction extends XYBarChartActionSupport {

	@Override
	protected IntervalXYDataset createDataset() {
		// TODO Auto-generated method stub
		XYSeries xySeries=new XYSeries("java"),xySeries2=new XYSeries("PHP");
	    Random random=new Random();
		for(int i=0;i<12;i++){
	    	xySeries.add(i,random.nextDouble());
	    	xySeries2.add(i,random.nextDouble());
	    }
		XYSeriesCollection xyc=new XYSeriesCollection();
		xyc.addSeries(xySeries);
		xyc.addSeries(xySeries2);
		
		XYBarDataset dataset=new XYBarDataset(xyc, 0.9d);
	  return dataset;
	}

	@Override
	protected String toJson(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
