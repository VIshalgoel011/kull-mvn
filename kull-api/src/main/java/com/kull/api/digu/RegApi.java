package com.kull.api.digu;

import java.text.MessageFormat;



import com.kull.api.DataType;

public class RegApi extends BaseDiguApi {

	public RegApi(DiguClient digu) {
		super(digu);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getApiRootPath() {
		// TODO Auto-generated method stub
		return "/reg";
	}
	
	//提交用户注册的相关信息，注册成功将返回该用户的信息，不成功，返回对应错误信息。
	//必须用Post 方法，不需要认证。
	public String reg(DataType edf,String username,String nickname,String pwd,String confirmPwd,String email) throws DiguException{
		String pattern=".{0}";
	    String url =MessageFormat.format(pattern,
	    edf		
	 	);
	    this.setUsername(username);
	    this.setNickname(nickname);
	    this.setPwd(pwd);
	    this.setConfirmPwd(confirmPwd);
	    this.setEmail(email);
	 	return this.doPost(edf,url);
	}
	
	//验证用户名是否已经被注册，Get 请求。
	public String check(DataType edf,String username) throws DiguException{
		String pattern="/check.{1}";
	    String url =MessageFormat.format(pattern,
	    		username
	    		,edf		
	 	);
	    this.setUsername(username);
	 	return this.doGet(edf,url);
	}
	
	public static void main(String[] args){
		String user="smartken";
		String pwd="chipchina";
		DiguClient digu=new DiguClient(user,pwd);
		RegApi api=new RegApi(digu);
		StringBuffer sbr=new StringBuffer("");
		try {
			
			sbr.append("reg:").append(api.reg(DataType.json,"lasysnake","龟爷未死",pwd,pwd,"249750689@qq.com")).append("\n");
			api.clear();
			sbr.append("check:").append(api.check(DataType.json,"hhlin")).append("\n");
			api.clear();
			//sbr.append("destroy:").append(api.destroy(DataType.json,"42746817")).append("\n");
			api.clear();

			//测试完成
			System.out.print(sbr.toString());
		} catch (DiguException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

}
