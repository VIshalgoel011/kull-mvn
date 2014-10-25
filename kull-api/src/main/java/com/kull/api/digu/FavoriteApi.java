package com.kull.api.digu;

import java.text.MessageFormat;

import com.kull.api.DataType;



public class FavoriteApi extends BaseDiguApi {

	public FavoriteApi(DiguClient digu) {
		super(digu);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getApiRootPath() {
		// TODO Auto-generated method stub
		return "/favorites";
	}

	//返回授权用户的最新的20 条收藏的嘀咕信息。也可以通过id 或者用户名来指定一个用
	//户，查询他最新的20 条收藏的嘀咕信息。
	public String favorites(DataType edf,String userIdOrName) throws DiguException{
		String pattern=".{0}";
	    String url =MessageFormat.format(pattern,
	    	edf		
	 	);
	    this.setUserIdOrName(userIdOrName);
	 	return this.doGet(edf,url);
	}
	
	//收藏一条嘀咕信息POST 提交
	public String create(DataType edf,String id,String category) throws DiguException{
		String pattern="/create/{0}.{1}";
	    String url =MessageFormat.format(pattern,
	    		id
	    		,edf		
	 	);
	    this.setCategory(category);
	 	return this.doPost(edf,url);
	}
	
	
	//收藏一条嘀咕信息POST 提交
	public String destroy(DataType edf,String id) throws DiguException{
		String pattern="/destroy/{0}.{1}";
	    String url =MessageFormat.format(pattern,
	    		id
	    		,edf		
	 	);
	 	return this.doGet(edf,url);
	}
	
	public static void main(String[] args){
		String user="smartken";
		String pwd="chipchina";
		DiguClient digu=new DiguClient(user,pwd);
		FavoriteApi favoriteApi=new FavoriteApi(digu);
		StringBuffer sbr=new StringBuffer("");
		try {
			
			sbr.append("create:").append(favoriteApi.create(DataType.json,"85163388", "test")).append("\n");
			favoriteApi.clear();
			sbr.append("favorites:").append(favoriteApi.favorites(DataType.json,user)).append("\n");
			favoriteApi.clear();
			//sbr.append("destroy:").append(favoriteApi.destroy(DataType.json, "85173341")).append("\n");

			//测试完成
			System.out.print(sbr.toString());
		} catch (DiguException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	
}
