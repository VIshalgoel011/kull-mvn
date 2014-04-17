package com.kull.digu;

import java.text.MessageFormat;

import com.kull.DataType;

public class FriendApi extends BaseDiguApi {

	public FriendApi(DiguClient digu) {
		super(digu);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getApiRootPath() {
		// TODO Auto-generated method stub
		return "/friends";
	}

	//用来获取指定的用户的朋友用户id。即自己跟随的人的id
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
		FriendApi friendApi=new FriendApi(digu);
		StringBuffer sbr=new StringBuffer("");
		try {
			
			sbr.append("ids:").append(friendApi.ids(DataType.json,user)).append("\n");
			friendApi.clear();

			//测试完成
			System.out.print(sbr.toString());
		} catch (DiguException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	
}
