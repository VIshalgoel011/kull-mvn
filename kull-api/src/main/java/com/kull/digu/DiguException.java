package com.kull.digu;

import java.util.HashMap;
import java.util.Map;

public class DiguException extends Exception {

	final public static int ERROR_SERVER= -1  //: 服务器错误
	, ERROR_UNKNOW= 0 //：未知原因
	, ERROR_AHTH_NULL= 1 //：用户名或者密码为空
	, ERROR_AUTH_FAIL= 2 //：用户名或者密码错误
	, ERROR_URL= 3  //：访问的URL不正确
	, ERROR_NULL_REOCORD= 4//：id指定的记录信息不存在。
	, ERROR_REPEAT_SEND= 5 //：重复发送
	, ERROR_VIO_MSG= 6 //：包含敏感非法关键字，禁止发表
	, ERROR_VIO_MSG_VERIFY= 7  //：包含敏感信息进入后台审核
	, ERROR_MSG_FORBID= 8 //：认证帐号被关小黑屋，被禁言，不能够发表信息了。
	, ERROR_MSG_PRIVATE= 9 //：表示发送悄悄话失败
	, ERROR_AUTH_OPERATION= 10 //：没有操作权限（比如删除只能删除自己发的，或者自己收藏的，或者自己收到的信息）
	, ERROR_USER_NOEXOST= 11 //指定的用户不存在
	, ERROR_REG_USER_EXIST= 12  //注册的用户已经存在
	, ERROR_SERVER_PARMA_NULL_OR_COLOR= 13  //表单值是空值，或者没有合法的颜色值，没有修改。修改失败。
	, ERROR_SERVER_PARMA_FILE= 14  //上传文件异常，请检查是否符合要求
	, ERROR_USER_UPDATE= 15  //更新用户信息失败。
	, ERROR_DELETE_CATEGORY_NAME_NULL= 16  //删除失败，删除收藏夹分类时，分类的名字是必须的。
	, ERROR_DELETE_CATEGORY_NOEXIST= 17  //删除失败，删除收藏夹分类时,分类不存在
	, ERROR_SERVER_PARMA_NULL_OR_EX= 18  //传递过来的参数为空或者异常
	, ERROR_REPEAT_FAV= 19 //：重复收藏
	, ERROR_MSG_JUST_FOLLOWER= 20//只能给跟随自己的人发送信息
	, ERROR_FORMAT_AUTH= 21//用户名、昵称或者密码不合法，用户名、昵称或者密码必须是4-12位，只支持字母、数字、下划线
	, ERROR_AUTH_CONFIRMPWD= 22//两次输入的秘密不正确
	, ERROR_FORMAT_EMAIL= 23  //Email格式不正确。
	, ERROR_USED_ACCOUNT= 24  //这个的帐号已被占用
	, ERROR_REQUEST_HOURLY= 25  //发送太频繁，帐号暂时被封
	, ERROR_SERVER_BUSY= 26  //服务器繁忙或者你访问的频率太高，超出了规定的限制
	, ERROR_DESTROY_ID= 27  //对不起，你的ip被官方封掉了，请联系我们的工作人员，进行相关处理
	, ERROR_VIO_NICKNAME= 28//：对不起，用户昵称中包含非法关键字。
	, ERROR_VIO_ADDRESS= 29 //对不起，所在地包含非法关键字。
	, ERROR_VIO_INSTERSTE= 30  //：对不起，个人兴趣包含非法关键字。
	, ERROR_VIO_SINGATURE= 31 //：对不起，签名（个人简介）包含非法关键字。
	, ERROR_USED_NICKNAME= 32 //：对不起，昵称已经被占用
	, ERROR_HTTP_METHOD= 33//：对不起，Http请求方法不正确
	;
	
	
	private static Map<Integer ,String> mapErrorMsg;
	 
	public static String ERROR_MSG(int error){
		if(mapErrorMsg==null){
			mapErrorMsg=new HashMap<Integer, String>();
			mapErrorMsg.put(ERROR_SERVER, "服务器错误");
			mapErrorMsg.put(ERROR_UNKNOW, "未知原因");
			mapErrorMsg.put(ERROR_AHTH_NULL,"用户名或者密码为空");
			mapErrorMsg.put(ERROR_AUTH_FAIL,"用户名或者密码错误");
			mapErrorMsg.put(ERROR_URL,"访问的URL不正确");
			mapErrorMsg.put(ERROR_NULL_REOCORD,"id指定的记录信息不存在");
			mapErrorMsg.put(ERROR_REPEAT_SEND,"重复发送");
			mapErrorMsg.put(ERROR_VIO_MSG,"包含敏感非法关键字，禁止发表");
			mapErrorMsg.put(ERROR_VIO_MSG_VERIFY,"包含敏感信息进入后台审核");
			mapErrorMsg.put(ERROR_MSG_FORBID,"认证帐号被关小黑屋，被禁言，不能够发表信息了");
			mapErrorMsg.put(ERROR_MSG_PRIVATE,"表示发送悄悄话失败");
			mapErrorMsg.put(ERROR_AUTH_OPERATION,"没有操作权限（比如删除只能删除自己发的，或者自己收藏的，或者自己收到的信息）");
			mapErrorMsg.put(ERROR_USER_NOEXOST,"指定的用户不存在");
			mapErrorMsg.put(ERROR_REG_USER_EXIST,"注册的用户已经存在");
			mapErrorMsg.put(ERROR_SERVER_PARMA_NULL_OR_COLOR,"表单值是空值，或者没有合法的颜色值，没有修改。修改失败");
			mapErrorMsg.put(ERROR_SERVER_PARMA_FILE,"上传文件异常，请检查是否符合要求");
			mapErrorMsg.put(ERROR_USER_UPDATE,"更新用户信息失败");
			mapErrorMsg.put(ERROR_DELETE_CATEGORY_NAME_NULL,"删除失败，删除收藏夹分类时，分类的名字是必须的");
			mapErrorMsg.put(ERROR_DELETE_CATEGORY_NOEXIST,"删除失败，删除收藏夹分类时,分类不存在");
			mapErrorMsg.put(ERROR_SERVER_PARMA_NULL_OR_EX,"传递过来的参数为空或者异常");
			mapErrorMsg.put(ERROR_REPEAT_FAV,"重复收藏");
			mapErrorMsg.put(ERROR_MSG_JUST_FOLLOWER,"只能给跟随自己的人发送信息");
			mapErrorMsg.put(ERROR_FORMAT_AUTH,"用户名、昵称或者密码不合法，用户名、昵称或者密码必须是4-12位，只支持字母、数字、下划线");
			mapErrorMsg.put(ERROR_AUTH_CONFIRMPWD,"两次输入的秘密不正确");
			mapErrorMsg.put(ERROR_FORMAT_EMAIL,"Email格式不正确");
			mapErrorMsg.put(ERROR_USED_ACCOUNT,"这个的帐号已被占用");
			mapErrorMsg.put(ERROR_REQUEST_HOURLY,"发送太频繁，帐号暂时被封");
			mapErrorMsg.put(ERROR_SERVER_BUSY,"服务器繁忙或者你访问的频率太高，超出了规定的限制");
			mapErrorMsg.put(ERROR_DESTROY_ID,"对不起，你的ip被官方封掉了，请联系我们的工作人员，进行相关处理");
			mapErrorMsg.put(ERROR_VIO_NICKNAME,"对不起，用户昵称中包含非法关键字");
			mapErrorMsg.put(ERROR_VIO_ADDRESS,"对不起，所在地包含非法关键字。");
			mapErrorMsg.put(ERROR_VIO_INSTERSTE,"对不起，个人兴趣包含非法关键字");
			mapErrorMsg.put(ERROR_VIO_SINGATURE,"对不起，签名（个人简介）包含非法关键字");
			mapErrorMsg.put(ERROR_USED_NICKNAME,"对不起，昵称已经被占用");
			mapErrorMsg.put(ERROR_HTTP_METHOD,"对不起，Http请求方法不正确");
		}
		return mapErrorMsg.get(error);
	}
	
	
	private int error;
	
	public DiguException(int error){
		this.error=error;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return this.ERROR_MSG(this.error);
	}
	
	
	
	
}
