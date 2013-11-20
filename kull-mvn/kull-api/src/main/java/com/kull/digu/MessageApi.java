package com.kull.digu;

import java.text.MessageFormat;

import com.kull.DataType;

public class MessageApi extends BaseDiguApi {

	public MessageApi(DiguClient digu) {
		super(digu);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getApiRootPath() {
		// TODO Auto-generated method stub
		return "/messages";
	}
	
	//返回发送给认证用户的消息列表
	public String messages(DataType edf,int message) throws DiguException{
		String pattern="/{0}.{1}";
	    String url =MessageFormat.format(pattern,
	    		message
	    		,edf		
	 	);
	 	return this.doGet(edf,url);
	}
	
	//以认证用户的身份向指定的其他用户发送一条有向消息，下面列出的参数content 和
	//receiveUserId 都是必须的，本API 必须使用POST 方法提交。当提交成功时，返回提交的
	public String New(DataType edf,String contend,int message,String receiveUserId) throws DiguException{
		String pattern="/handle/new.{0}";
	    String url =MessageFormat.format(pattern,
	    		edf		
	 	);
	    this.setContent(contend);
	    this.setMessage(MESSAGE_SayHi);
	    this.setReceiveUserId(receiveUserId);
	 	return this.doPost(edf,url);
	}
	
	public String destroy(DataType edf,String id) throws DiguException{
		String pattern="/handle/destroy/{0}.{1}";
	    String url =MessageFormat.format(pattern,
	    		id
	    		,edf		
	 	);
	 	return this.doPost(edf,url);
	}
	
	public static void main(String[] args){
		String user="smartken";
		String pwd="chipchina";
		DiguClient digu=new DiguClient(user,pwd);
		MessageApi api=new MessageApi(digu);
		StringBuffer sbr=new StringBuffer("");
		try {
			
			sbr.append("messages:").append(api.messages(DataType.json,MESSAGE_PRIVATE)).append("\n");
			api.clear();
			sbr.append("New:").append(api.New(DataType.json,"hello world",MESSAGE_SayHi,"13980354")).append("\n");
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
