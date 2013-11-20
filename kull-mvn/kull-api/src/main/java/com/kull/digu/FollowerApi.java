package com.kull.digu;

import java.text.MessageFormat;

import com.kull.DataType;



public class FollowerApi extends BaseDiguApi {

	public FollowerApi(DiguClient digu) {
		super(digu);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getApiRootPath() {
		// TODO Auto-generated method stub
		return "/followers";
	}
	
	
	//用来获取指定的用户被跟随的用户id。
	public String ids(DataType edf,String userIdOrName) throws DiguException{
		String pattern="/ids/{0}.{1}";
	    String url =MessageFormat.format(pattern,
	 	       userIdOrName
	    		,edf		
	 	);
	 	return this.doGet(edf,url);
	}

	
	public static void main(String[] args){
		String user="smartken";
		String pwd="chipchina";
		DiguClient digu=new DiguClient(user,pwd);
		FollowerApi followerApi=new FollowerApi(digu);
		StringBuffer sbr=new StringBuffer("");
		try {
			
			sbr.append("ids:").append(followerApi.ids(DataType.json,user)).append("\n");
			followerApi.clear();

			//测试完成
			System.out.print(sbr.toString());
		} catch (DiguException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	
}
