package com.kull.api.digu;

import java.io.File;
import java.text.MessageFormat;
import java.util.Date;

import com.kull.api.DataType;






public class AccountApi extends BaseDiguApi {

	public AccountApi(DiguClient digu) {
		super(digu);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getApiRootPath() {
		// TODO Auto-generated method stub
		return "/account";
	}
	
	
	//用来验证用户账户密码是否匹配，用户账户密码信息必须通过AUTH_HTTP 方式提
	//供，返回信息中包含了用户登录名、OpenID(如果可用的话)等信息；
	public String verify(DataType edf,boolean isAllInfo) throws DiguException{
		String pattern="/verify.{1}?isAllInfo={0}";
	    String url =MessageFormat.format(pattern,
	    		isAllInfo
	    		,edf		
	 	);
	 	return this.doGet(edf,url);
	}
	
	//用来更新用户自己风格的设置。比如：背景颜色、文字颜色、链接颜色、边栏底色、边
	//栏文字颜色、边栏主菜单文字颜色、边栏主菜单背景颜色。必须使用POST 请求。注意，颜
	//色的值必须是以#开头，后面是描述颜色的字符或者六位数字的颜色
	public String update_profile_colors(DataType edf) throws DiguException{
		String pattern="/update_profile_colors.{0}";
	    String url =MessageFormat.format(pattern,
	    		edf		
	 	);
	 	return this.doGet(edf,url);
	}
	
	
	//更新授权用户的头像，必须采用POST 方法，而且不能POST 图片路径，POST 图片的
	//时候，图片的大小不能超过4M，而且，只支持".png"，".jpg"，".jpeg"，".gif"，".bmp"格式。
	//Form 类型为multipart data。如enctype="multipart data"
	public String update_profile_image(DataType edf,File image) throws DiguException{
		String pattern="/update_profile_image.{0}";
	    String url =MessageFormat.format(pattern,
	    		edf		
	 	);
	    this.setImage(image);
	    this.setUploadImg("xiexiezhici");
	 	return this.doPost(edf,url);
	}
	
	public String update_profile_bg_image(DataType edf,File image) throws DiguException{
		String pattern="/update_profile_bg_image.{0}";
	    String url =MessageFormat.format(pattern,
	    		edf		
	 	);
	 	return this.doGet(edf,url);
	}
	
	public String update_profile(DataType edf) throws DiguException{
		String pattern="/update_profile.{0}";
	    String url =MessageFormat.format(pattern,
	    		edf		
	 	);
	 	return this.doPost(edf,url);
	}

	
	public static void main(String[] args){
		String user="smartken";
		String pwd="chipchina";
		DiguClient digu=new DiguClient(user,pwd);
		AccountApi userApi=new AccountApi(digu);
		StringBuffer sbr=new StringBuffer("");
		try {
			sbr.append("show:").append(userApi.verify(DataType.json, false)).append("\n");
			
			//File image=new File("h:\\agentIdFrontImg.jpg");
			//userApi.update_profile_bg_image(DataType.json, image);
			
			//userApi.setNickname("我爱147");
			userApi.setGender(GENDER_WOMAN);
			userApi.setAddress("唔话你知");
			userApi.setEmail("smartken0824@gmail.com");
			userApi.setBirthday(new Date());
			userApi.setHomepage("www.google.com");
			userApi.setInterest("杀人防火");
			userApi.setSignature("java api 开发中");
			sbr.append("update_profile:").append(userApi.update_profile(DataType.json)).append("\n");

			System.out.print(sbr.toString());
		} catch (DiguException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	

}
