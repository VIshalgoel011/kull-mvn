package com.kull.digu;

import java.text.MessageFormat;

import com.kull.ObjectHelper.DataType;

public class FriendshipsApi extends BaseDiguApi {

	public FriendshipsApi(DiguClient digu) {
		super(digu);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getApiRootPath() {
		// TODO Auto-generated method stub
		return "/friendships";
	}
	
	//创建认证用户与给定的id 参数指定的用户之间的好友关系。返回结果为：0 表示你申请
	//者被该用户屏蔽，不能与他成为好友关系；-1 表示跟随失败；-2 表示跟随的人数超过了1000，
	//系统不允许再跟随其他人；-3 表示被跟随的人设置了隐私保护，提交申请失败；1 表示跟随
	//成功；2 表示已经跟随；3 表示被跟随的用户设置了隐私保护，已经提交了跟随申请。
	public String create(DataType edf,String userIdOrName) throws DiguException{
		String pattern="/create.{1}?userIdOrName={0}";
	    String url =MessageFormat.format(pattern,
	    		userIdOrName
	    		,edf		
	 	);
	 	return this.doGet(edf,url);
	}
	
	//用来注销同指定id 的用户的好友关系，当操作成功时，将返回被取消好友关系的用户，
	//当失败时，将会返回失败信息。
	public String destroy(DataType edf,String userIdOrName) throws DiguException{
		String pattern="/destroy.{0}";
	    String url =MessageFormat.format(pattern,
	    		edf		
	 	);
	    this.setUserIdOrName(userIdOrName);
	 	return this.doGet(edf,url);
	}
	
	
	//用来检验两个用户的关系是否是朋友关系或者跟随与被跟随的关系。返回相互跟随的关
	//系结果。比如： A 跟随了B 。B 没有跟随A
	public String verify(DataType edf,String userIdOrNameA,String userIdOrNameB ) throws DiguException{
		String pattern="/exists.{0}?userIdOrNameA={1}&userIdOrNameB={2}";
	    String url =MessageFormat.format(pattern,
	    	    edf
	    		,userIdOrNameA
	    		,userIdOrNameB
	 	);
	 	return this.doGet(edf,url);
	}
	
	
	public static void main(String[] args){
		String user="smartken";
		String pwd="chipchina";
		DiguClient digu=new DiguClient(user,pwd);
		FriendshipsApi api=new FriendshipsApi(digu);
		StringBuffer sbr=new StringBuffer("");
		try {
			
			sbr.append("create:").append(api.create(DataType.json,"10128552")).append("\n");
			api.clear();
			sbr.append("destroy:").append(api.destroy(DataType.json,"")).append("\n");
			api.clear();
			sbr.append("verify:").append(api.verify(DataType.json,user,"10010231")).append("\n");
			api.clear();

			//测试完成
			System.out.print(sbr.toString());
		} catch (DiguException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

}
