package com.kull.digu;

import java.text.MessageFormat;

import com.kull.DataType;

public class UserApi extends BaseDiguApi {

	public UserApi(DiguClient digu) {
		super(digu);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getApiRootPath() {
		// TODO Auto-generated method stub
		return "/users";
	}

	//显示指定用户的扩展信息，需要给定用户的id 或显示名称。扩展信息包括用户的页面
	//设置、Digu 次数等，因此第三方应用的开发者可以根据这些信息为用户提供合适的主题。
	//注意：本API 调用请求必须发自合法Digu 用户，不论请求自己/他人的扩展信息。
	public String show(DataType edf,String userIdOrName) throws DiguException{
		String pattern="/show/{0}.{1}";
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
		UserApi userApi=new UserApi(digu);
		StringBuffer sbr=new StringBuffer("");
		try {
			sbr.append("show:").append(userApi.show(DataType.json, user)).append("\n");
			
			System.out.print(sbr.toString());
		} catch (DiguException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	
}
