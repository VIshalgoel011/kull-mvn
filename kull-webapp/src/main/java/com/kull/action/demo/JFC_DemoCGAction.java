package com.kull.action.demo;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.kull.jfc.CategoryChartActionSupport;







public class JFC_DemoCGAction extends CategoryChartActionSupport{

/*	@Override
	protected CategoryDataset createDataset() {
		// TODO Auto-generated method stub
		
		DefaultCategoryDataset dataset=new DefaultCategoryDataset();
		for(int i=0;i<5;i++){
			String cat1="第"+(i+1)+"季度";
			for(int j=0;j<3;j++){
				String cat2="科目"+(j+1);
				dataset.addValue(i*j, cat2, cat1);
			}
		}
		return dataset;
	}*/

	@Override
	protected String toJson(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
