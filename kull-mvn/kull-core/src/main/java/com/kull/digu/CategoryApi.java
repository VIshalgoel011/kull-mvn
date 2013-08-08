package com.kull.digu;

import java.io.File;
import java.text.MessageFormat;

import com.kull.ObjectHelper.DataType;





public class CategoryApi extends BaseDiguApi {

	public CategoryApi(DiguClient digu) {
		super(digu);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getApiRootPath() {
		// TODO Auto-generated method stub
		return "/category";
	}

	
	//在授权用户的收藏夹中创建一个分组，必须采用POST 方法。长度不能超过10 个字符,
	//否则自动截断
	public String create(DataType edf,String name) throws DiguException{
		String pattern="/create.{0}";
	    String url =MessageFormat.format(pattern,
	    		edf		
	 	);
	    this.setName(name);
	 	return this.doPost(edf,url);
	}
	

	
	//在授权用户的收藏夹中重命名一个分组，必须采用POST 方法。长度不能超过10 个字
	//符，否则自动截断
	public String update(DataType edf,String oldName,String newName) throws DiguException{
		String pattern="/update.{0}";
	    String url =MessageFormat.format(pattern,
	    		edf		
	 	);
	    this.setOldName(oldName);
	    this.setNewName(newName);
	 	return this.doPost(edf,url);
	}
	
	
	//在授权用户的收藏夹中根据分组名字，删除这个分组，并且该分组的那些收藏信息，也
	//将被自动解除收藏关系。
	public String show(DataType edf,String category) throws DiguException{
		String pattern="/show.{0}";
	    String url =MessageFormat.format(pattern,
	    		edf		
	 	);
	    this.setCategory(category);
	 	return this.doGet(edf,url);
	}
	
	//在授权用户的收藏夹中根据分组名字，删除这个分组，并且该分组的那些收藏信息，也
	//将被自动解除收藏关系。
	public String destroy(DataType edf,String category) throws DiguException{
		String pattern="/destroy.{0}";
	    String url =MessageFormat.format(pattern,
	    		edf		
	 	);
	    this.setCategory(category);
	 	return this.doGet(edf,url);
	}
	
	
	public static void main(String[] args){
		String user="smartken";
		String pwd="chipchina";
		DiguClient digu=new DiguClient(user,pwd);
		CategoryApi categoryApi=new CategoryApi(digu);
		StringBuffer sbr=new StringBuffer("");
		try {
			
			sbr.append("create:").append(categoryApi.create(DataType.json, "test")).append("\n");
			categoryApi.clear();
			sbr.append("update:").append(categoryApi.update(DataType.json, "test","hhlin")).append("\n");
			categoryApi.clear();
			sbr.append("show:").append(categoryApi.show(DataType.json, null)).append("\n");
			categoryApi.clear();
			sbr.append("destroy:").append(categoryApi.destroy(DataType.json, "test")).append("\n");

			//测试完成
			System.out.print(sbr.toString());
		} catch (DiguException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	
	
}
