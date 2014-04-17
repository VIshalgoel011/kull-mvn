package com.kull;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.omg.CORBA.OBJ_ADAPTER;

import com.kull.util.IModel;






import jxl.Cell;
import jxl.HeaderFooter;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCell;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelUtil {
	
	
	public static List<Cell> ToExcelRow(Object obj,List<String> fields,int rowIndex,int cellIndex){
		   List<Cell> listCell=new ArrayList<Cell>();
	       for(int i=0;i<fields.size();i++){
	    	   String field=fields.get(i);
	    	   try {
				Type type=ObjectHelper.fieldType(obj,field);
			    
			    
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	   
	       }
		   return listCell;
	   }
	
	public static String readExcel(File file){    
	    StringBuffer sb = new StringBuffer();    
	        
	    Workbook wb = null;    
	    
	    try {    
	        //构造Workbook（工作薄）对象    
	        wb=Workbook.getWorkbook(file);    
	    } catch (BiffException e) {    
	        e.printStackTrace();    
	    } catch (IOException e) {    
	        e.printStackTrace();    
	    }    
	        
	    if(wb==null)    
	        return null;    
	        
	    //获得了Workbook对象之后，就可以通过它得到Sheet（工作表）对象了    
	    Sheet[] sheet = wb.getSheets();    
	        
	    if(sheet!=null&&sheet.length>0){    
	        //对每个工作表进行循环    
	        for(int i=0;i<sheet.length;i++){
	            //得到当前工作表的行数    
	            int rowNum = sheet[i].getRows();    
	            for(int j=0;j<rowNum;j++){
	                //得到当前行的所有单元格    
	                Cell[] cells = sheet[i].getRow(j);    
	                if(cells!=null&&cells.length>0){    
	                    //对每个单元格进行循环    
	                    for(int k=0;k<cells.length;k++){
	                        //读取当前单元格的值    
	                        String cellValue = cells[k].getContents();    
	                        sb.append(cellValue+"\t");    
	                    }    
	                }    
	                sb.append("\r\n");    
	            }    
	            sb.append("\r\n");    
	        }    
	    }    
	    //最后关闭资源，释放内存    
	    wb.close();    
	    return sb.toString();    
	}   


	/**生成一个Excel文件   
     * @param fileName  要生成的Excel文件名   
     */   
    public static void writeExcel(String fileName,List dataModel) {    
        WritableWorkbook wwb = null;    
        try {    
            //首先要使用Workbook类的工厂方法创建一个可写入的工作薄(Workbook)对象    
            wwb = Workbook.createWorkbook(new File(fileName));    
        } catch (IOException e) {    
            e.printStackTrace();    
        }    
        if(wwb==null){return ;}    
            //创建一个可写入的工作表    
            //Workbook的createSheet方法有两个参数，第一个是工作表的名称，第二个是工作表在工作薄中的位置    
            WritableSheet ws = wwb.createSheet("sheet1", 0);    
            

   
            try {    
                //从内存中写入文件中    
                wwb.write();    
                //关闭资源，释放内存    
                wwb.close();    
            } catch (IOException e) {    
                e.printStackTrace();    
            } catch (WriteException e) {    
                e.printStackTrace();    
            }    
           
    }    

    /**搜索某一个文件中是否包含某个关键字   
     * @param file  待搜索的文件   
     * @param keyWord  要搜索的关键字   
     * @return   
     */   
    public static boolean searchKeyWord(File file,String keyWord){    
        boolean res = false;    
            
        Workbook wb = null;    
        try {    
            //构造Workbook（工作薄）对象    
            wb=Workbook.getWorkbook(file);    
        } catch (BiffException e) {    
            return res;    
        } catch (IOException e) {    
            return res;    
        }    
            
        if(wb==null)    
            return res;    
            
        //获得了Workbook对象之后，就可以通过它得到Sheet（工作表）对象了    
        Sheet[] sheet = wb.getSheets();    
            
        boolean breakSheet = false;    
            
        if(sheet!=null&&sheet.length>0){    
            //对每个工作表进行循环    
            for(int i=0;i<sheet.length;i++){ 
                if(breakSheet)    
                    break;    
                    
                //得到当前工作表的行数    
                int rowNum = sheet[i].getRows();    
                    
                boolean breakRow = false;    
                    
                for(int j=0;j<rowNum;j++){ 
                    if(breakRow)    
                        break;    
                    //得到当前行的所有单元格    
                    Cell[] cells = sheet[i].getRow(j);    
                    if(cells!=null&&cells.length>0){    
                        boolean breakCell = false;    
                        //对每个单元格进行循环    
                        for(int k=0;k<cells.length;k++){ 
                            if(breakCell)    
                                break;    
                            //读取当前单元格的值    
                            String cellValue = cells[k].getContents();    
                            if(cellValue==null)    
                                continue;    
                            if(cellValue.contains(keyWord)){    
                                res = true;    
                                breakCell = true;    
                                breakRow = true;    
                                breakSheet = true;    
                            }    
                        }    
                    }    
                }    
            }    
        }    
        //最后关闭资源，释放内存    
        wb.close();    
            
        return res;    
    }   

    public static void insertImg(WritableSheet dataSheet, int col, int row, int width,    
            int height, File imgFile){    
        WritableImage img = new WritableImage(col, row, width, height, imgFile);    
        dataSheet.addImage(img);    
    }    
    


    public static void setHeader(WritableSheet dataSheet,String left,String center,String right){    
        HeaderFooter hf = new HeaderFooter();    
        hf.getLeft().append(left);    
        hf.getCentre().append(center);    
        hf.getRight().append(right);    
        //加入页眉    
        dataSheet.getSettings().setHeader(hf);    
        //加入页脚    
        //dataSheet.getSettings().setFooter(hf);    
    }
    
   public static void setSheetData(WritableSheet dataSheet,List<? extends IModel> list,int rowStartIndex,int colStartIndex){
	   if(list.size()==0)return;
	   IModel bm=list.get(0);
	   Enum[] ens=bm.getFieldNames();
	   List<String> fields=new ArrayList<String>();
	   for(Enum en: ens ){
		   fields.add(en.name());
	   }
	   for(int i=0;i<list.size();i++){
		   int rowIndex=i+rowStartIndex;
		   IModel baseModel=list.get(i);
		   List<WritableCell> cells=createExcelRow(baseModel, rowIndex, colStartIndex,fields);
		   for(WritableCell cell :cells){
			   try {
				dataSheet.addCell(cell);
			} catch (RowsExceededException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   }
	   }
   }

   public static List<WritableCell> createExcelRow(Object obj,int rowIndex,int colStartIndex,List<String> fields){
	   List<WritableCell> cells=new ArrayList<WritableCell>();
	   WritableCell cell;
	   Type type;
	   Object value;
	   for(int i=0;i<fields.size();i++){
		   String field=fields.get(i);
		   int colIndex=i+colStartIndex;
		   try {
			type=ObjectHelper.fieldType(obj,field);
			value=ObjectHelper.attr(obj,field);
			if(LinqHelper.isIn(type, Integer.class,Double.class,BigDecimal.class,Float.class
			)){		   
			   cell=new Number(colIndex, rowIndex, (Double)value);
			}else if(LinqHelper.isIn(type, Date.class,Timestamp.class
			)){
				cell=new DateTime(colIndex, rowIndex, (Date)value);
			}else{
				cell=new Label(colIndex, rowIndex, value.toString());
			}
			cells.add(cell);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
	   return cells;
   } 
    
}
