package com.kull.action.demo;

import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYDataset;

import com.kull.jfc.TSChartActionSupport;

public class JFC_DemoTSAction extends TSChartActionSupport {

	@Override
	protected XYDataset createDataSet() {
		// TODO Auto-generated method stub
		TimeSeries ts=new TimeSeries("java"),ts2=new TimeSeries("python");
		Day day=new Day(1,1,1990);
		Year year=new Year(2000);
		double d=100000D;
		for(int i=0;i<12;i++){
			try{
				d=(d+Math.random())-0.5d;
				ts.add(year,new Double(d));
				d=(d+Math.random())-0.5d;
				ts2.add(year,new Double(d));
				year=(Year)year.next();
			}catch(Exception ex){}
		}
		TimeSeriesCollection tsc=new TimeSeriesCollection();
		tsc.addSeries(ts);
		tsc.addSeries(ts2);
		return tsc;
	}

	@Override
	protected String toJson(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
