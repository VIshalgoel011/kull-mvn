package com.kull.digu;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import org.apache.http.NameValuePair;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.message.BasicNameValuePair;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;


import com.kull.DateTimeHelper;
import com.kull.DataType;
import com.kull.bean.HttpClientBean;
import net.sf.json.JSONObject;







public abstract class BaseDiguApi {

	final public static int TYPE_ALL=0;   //0、全部查询
	final public static int TYPE_TEXT=1;  //1、只查询文字嘀咕
	final public static int TYPE_IMAGE=2;   //2、只查询图片嘀咕(不带文字)
	final public static int TYPE_TEXT_AND_IMAGE=3;     //3查询图片+文字嘀咕
	final public static int TYPE_TEXT_OR_IMAGE=4;   //4、只查询带文字的，可能有图片（没有文字的图片嘀咕是查不出来
	final public static int TYPE_IMAGE_OR_TEXT=5;   //5 只查询带图片的，可能有文字（没有图片的文字嘀咕是查不出来的）

	final public static int MESSAGE_PRIVATE=0;  //0 表示悄悄话
	final public static int MESSAGE_SayHi=2;  //1 表示戳一下
	final public static int MESSAGE_UPDATE=2;  //2 表示升级通知
	final public static int MESSAGE_PROXY=3;  //3 表示代发通知
	final public static int MESSAGE_SYSTEM=4;  //4表示系统消息
	final public static int MESSAGE_ALL=100;  //100 表示不分类，都查询
	
	final public static String POSITION_LEFT="left";  //left ( 居左)
	final public static String POSITION_RIGHT="right";  //right ( 居右)
	final public static String POSITION_CENTER="center";  //center ( 居中)
	final public static String POSITION_TILE="tile";  //tile ( 平铺) 
	
	final public static String IS_FIXED_YES="yes";
	final public static String IS_FIXED_NO="no";
	
	final public static int GENDER_MAN=1; //1 表示男性
	final public static int GENDER_WOMAN=2;  //2表示女性
	
	final private DiguClient diguClient;
	final private HttpClientBean httpClientEntity;
	private List<NameValuePair> parmaPost=new LinkedList<NameValuePair>();
	private Map<String, String> parmaGet=new HashMap<String, String>();
	private Map<String, File> parmaPostFiles=new HashMap<String, File>();

	
	protected String id;
	
	protected String since_id;   
	//传递记录的id，表示要查询的结果的id 值在这个id 值之后，不包括这条记录的id 值
	
	protected int count=20;    
	//分页使用，取的数量，即取的条数。不能超过20 条，缺省为20。
	
	protected int page=1;   
	//默认为第一页，分页显示使用。只能是数字。否则为第一页.1 代表是第页，2 代表是第二
	
	protected String max_id ;  
	//可以和page 和count 结合使用。查询比这个id 小的那些记录
	
	protected Date since;   
	//取这个时间段后的嘀咕信息。注意，传递时间日期时，只能转换为日期的long 型数值才能传递
	
	protected String userIdOrName;   
	//返回指定用户的嘀咕信息
	
	protected boolean isBack;
	//true 或者false，默认为true，表示查询某个人时，显示认证用户回复别
	//人的信息，即带有@符号开头的也查询出来了。false，表示不查询认证用户回复别人的
	
	protected int type=TYPE_ALL;
	//；；；3、只
	//查询图片+文字嘀咕；4、只查询带文字的，可能有图片（没有文字的图片嘀咕是查不出来
	//的）； 5、只查询带图片的，可能有文字（没有图片的文字嘀咕是查不出来的）。默认为0
	
	protected String content;
	//更新的Digu 消息内容， 请确定必要时需要进行UrlEncode 编码，另外，
	//不超过140 个中文或者英文字。暂不支持图片
	
	protected String digu_id;
	//如果这个是回复功能使用的，如果是回复别人的嘀咕，那么，这个字段
	//是必须的。是被回复的嘀咕的信息的id。
	
	protected String source;
	//指明这条信息是通过什么发送的。需要我们工作人员配合才能凑效，请
	//联系工作人员或者客服
	
	protected String reply_user_id;
	//指明要回复的用户的id。
	
	protected File image,image0,image1,image2;
	//发送图片。如果要发送图片，这个不能为
	
	protected String uploadImg="xiexiezhici";
	//隐含授权码的字段。如果用户想上传图片，需要需要传递此参数，参数值为：xiexiezhichi
	
	protected String friendUserId;
	//跟随的朋友的userId，只获取该朋友的信息
	
	protected String friendUsername;
	//朋友的登录名，根据登录名获取朋友的信息
	
	protected String friendUserIdOrName;
	//朋友的id 或者登录名，均可。
	
	
	protected int message=MESSAGE_ALL;
	//0 表示悄悄话，1 表示戳一下，2 表示升级通知，3 表示代发通知，4表示系统消息。100 表示不分类，都查询。
	
	protected boolean isAllInfo;
	//值只能是boolean 型，默认为boolean 型false。false 表示仅仅验证用户
	//名和密码是否正确，返回用户名和昵称，别的用户信息不返回。true 表示，不仅验证用户名
	//和密码是否正确，返回验证通过的用户的具体详细信息。说明：如果不需要用户的具体信息，
	//就不要用true，否则，会增加服务器的压力，可能对你开发的插件也有一定的影响。
	
	
	protected String bg_color;
	//背景色
	
	protected String text_color;
	//文本颜色
	
	protected String link_color;
	//链接颜色
	
	protected String sidebar_color;
	//边栏色
	
	protected String sidebar_text_color;
	//边栏文字颜色
	
	protected String sidebar_menu_color;
	//边栏主菜单颜色
	
	
	protected String position=POSITION_LEFT;
	//默认为"left"：居左放；可选的值为left ( 居左)，right ( 居右)，center ( 居中)，tile ( 平铺)
	
	protected String isFixed;
	//值为yes 或者no，表示固定与否，与网站上的固定选项对应。默认为no。
	
	protected String nickname;
	//[可选]： 用户的昵称，不是用户名哦。用户名不可修改。
	
	protected int gender;
	//[可选]： 用户的性别，2 表示女性，1 表示男性。只能为这两个值
	
	protected String address;
	//[可选]： 用户的所在地
	
	protected String email;
	//[可选]： 用户的联系email
	
	protected Date birthday;
	//[可选]： 生日(格式必须为："2009-03-13")
	
	protected String homepage;
	//[可选]： 个人主页
	
	protected String interest;
	//[可选]： 个人兴趣
	
	protected String signature;
	//[可选]： 个人签名
	
	
	protected String username;
	//[必须]：要注册用户的登录名。
	protected String pwd;
	//[必须]：用户注册密码。
	protected String confirmPwd;
	//[必须]：确认密码，保证用户记得密码，没有输错。
	
	protected String focusUserId;
	//[可选]：注册成功后要跟随的人的id。类似网站的注册并跟随。
	
	protected String inviterUserId;
	//[可选]：注册后，相互跟随的用户id。类似网站的邀请链接。
	
	protected String category;
	//[可选]：如果不为空，则返回这一条分类数据。如果为空，表示查询授权用户
	//的所有分类信息。
	
	protected String name;
	//授权用户收藏夹中的分类名字
	
	protected String oldName;
	//授权用户收藏夹中的分类名字
	
	protected String newName;
	//授权用户收藏夹中的新分类名
	
	protected String receiveUserId;
	//接收方的用户id
	
	protected String q;
	//查询关键字
	
	public BaseDiguApi(DiguClient digu){
		this.diguClient=digu;
		this.httpClientEntity=new HttpClientBean(diguClient);
	}
	
	public String doGet(DataType edf,String url) throws DiguException{
		String responseText=""; 
		String requestUrl=getApiRootPath()+url;
		StringBuffer requestParma=null;
		if(requestUrl.endsWith("?")){
			requestParma=new StringBuffer("");
		}else{
			requestParma=new StringBuffer("?");
		}
		for(Iterator<String> it=this.parmaGet.keySet().iterator();it.hasNext();){
			String key=it.next();
			String value=this.parmaGet.get(key);
			requestParma.append(key).append("=").append(value);
			if(it.hasNext()){
				requestParma.append("&");
			}
		}

		try {
			
			responseText=httpClientEntity.get(requestUrl+requestParma.toString());

		} catch (MalformedChallengeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int re=isError(edf, responseText);
		if(re!=-2){
			throw new DiguException(re);
		}
		return responseText;
	}
	
	public String doPost(DataType edf,String url) throws DiguException{
		return this.doPost(edf, url, false);
	}
	
	public String doPost(DataType edf,String url,boolean isMultipart) throws DiguException{
		String responseText="";
		String requestUrl=getApiRootPath()+url;
		try {
			if(isMultipart){
				responseText= httpClientEntity.post(requestUrl,this.parmaPost,this.parmaPostFiles);

			}else{
				responseText= httpClientEntity.post(requestUrl,this.parmaPost);

			}
		} catch (MalformedChallengeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int re=isError(edf, responseText);
		if(re!=-2){
			throw new DiguException(re);
		}
		return responseText;
	}
	
	public abstract String getApiRootPath();
	
	private int isError(DataType edf ,String responseText){
		int re=-2;
		switch (edf) {
		case json:{
			try {
				JSONObject json=JSONObject.fromObject(responseText);
				Object error= json.get("error");
				if(error!=null){
					re=Integer.parseInt(error.toString());
				}
				break;
			}catch(NumberFormatException nfe){
				nfe.printStackTrace();
			}
			break;
		}
		case rss:
		case atom:
		case xml:{
			   try {
				Document document=DocumentHelper.parseText(responseText);
				Node error=  document.selectSingleNode("/hash/error");
				if(error!=null){
					re=Integer.parseInt(((Element)error).getTextTrim());
				}
			} catch (DocumentException e) {
			}catch(NumberFormatException nfe){
				nfe.printStackTrace();
			}
			break;
		}
		default:
			break;
		}
		return re;
	}
	
	public void clear(){
		this.parmaGet=new HashMap<String, String>();
		this.parmaPost=new LinkedList<NameValuePair>();
		this.parmaPostFiles=new HashMap<String, File>();
	}
	
	public void addParma(String key,Object value){
		if(value==null)return;
		String formatVal="";
		if(value instanceof String || value instanceof Number || value instanceof Boolean){
			formatVal=value.toString();
		}else if(value instanceof Date){
			Date d=(Date)value;
			formatVal=String.valueOf(d.getTime());
		}else if(value instanceof File){
		    File file=(File)value;
		    this.parmaPostFiles.put(key, file);
		    return;
		}else{
			return;
		}
		this.parmaGet.put(key, formatVal);
		this.parmaPost.add(new BasicNameValuePair(key, formatVal));
		
	}

	public String getSince_id() {
		return since_id;
	}

	public void setSince_id(String sinceId) {
		since_id = sinceId;
		this.parmaGet.put("since_id", sinceId);
		this.parmaPost.add(new BasicNameValuePair("since_id", sinceId));
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
		this.addParma("count",count);
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getMax_id() {
		return max_id;
	}

	public void setMax_id(String maxId) {
		max_id = maxId;
	}

	public Date getSince() {
		return since;
	}

	public void setSince(Date since) {
		this.since = since;
	}

	public String getUserIdOrName() {
		return userIdOrName;
	}

	public void setUserIdOrName(String userIdOrName) {
		this.userIdOrName = userIdOrName;
		this.addParma("userIdOrName", userIdOrName);
	}

	public boolean isBack() {
		return isBack;
	}

	public void setBack(boolean isBack) {
		this.isBack = isBack;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
		this.parmaPost.add(new BasicNameValuePair("content", content));
	}

	public String getDigu_id() {
		return digu_id;
	}

	public void setDigu_id(String diguId) {
		digu_id = diguId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getReply_user_id() {
		return reply_user_id;
	}

	public void setReply_user_id(String replyUserId) {
		reply_user_id = replyUserId;
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
		this.addParma("image", image);
	}

	public File getImage0() {
		return image0;
	}

	public void setImage0(File image0) {
		this.image0 = image0;
		this.addParma("image0", image0);
	}

	public File getImage1() {
		return image1;
	}

	public void setImage1(File image1) {
		this.image1 = image1;
		this.addParma("image1", image1);
	}

	public File getImage2() {
		return image2;
	}

	public void setImage2(File image2) {
		this.image2 = image2;
		this.addParma("image2", image2);
	}

	public String getUploadImg() {
		return uploadImg;
	}

	public void setUploadImg(String uploadImg) {
		this.uploadImg = uploadImg;
		this.addParma("uploadImg", uploadImg);
	}

	public String getFriendUserId() {
		return friendUserId;
	}

	public void setFriendUserId(String friendUserId) {
		this.friendUserId = friendUserId;
	}

	public String getFriendUsername() {
		return friendUsername;
	}

	public void setFriendUsername(String friendUsername) {
		this.friendUsername = friendUsername;
	}

	public String getFriendUserIdOrName() {
		return friendUserIdOrName;
	}

	public void setFriendUserIdOrName(String friendUserIdOrName) {
		this.friendUserIdOrName = friendUserIdOrName;
	}

	public int getMessage() {
		return message;
	}

	public void setMessage(int message) {
		this.message = message;
	}

	public boolean isAllInfo() {
		return isAllInfo;
	}

	public void setAllInfo(boolean isAllInfo) {
		this.isAllInfo = isAllInfo;
	}

	public String getBg_color() {
		return bg_color;
	}

	public void setBg_color(String bgColor) {
		bg_color = bgColor;
	}

	public String getText_color() {
		return text_color;
	}

	public void setText_color(String textColor) {
		text_color = textColor;
	}

	public String getLink_color() {
		return link_color;
	}

	public void setLink_color(String linkColor) {
		link_color = linkColor;
	}

	public String getSidebar_color() {
		return sidebar_color;
	}

	public void setSidebar_color(String sidebarColor) {
		sidebar_color = sidebarColor;
	}

	public String getSidebar_text_color() {
		return sidebar_text_color;
	}

	public void setSidebar_text_color(String sidebarTextColor) {
		sidebar_text_color = sidebarTextColor;
	}

	public String getSidebar_menu_color() {
		return sidebar_menu_color;
	}

	public void setSidebar_menu_color(String sidebarMenuColor) {
		sidebar_menu_color = sidebarMenuColor;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getIsFixed() {
		return isFixed;
	}

	public void setIsFixed(String isFixed) {
		this.isFixed = isFixed;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
		this.addParma("nickname", nickname);
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
		this.addParma("gender", gender);
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
		this.addParma("address", address);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
		this.addParma("email", email);
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
		String str=DateTimeHelper.Formatter.DATE_FORMAT_DB.format(birthday);
		this.addParma("birthday", str);
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
		this.addParma("homepage", homepage);
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
		this.addParma("interest", interest);
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
		this.addParma("signature", signature);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
		this.addParma("username", username);
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
		this.addParma("pwd", pwd);
	}

	public String getConfirmPwd() {
		return confirmPwd;
	}

	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
		this.addParma("confirmPwd", confirmPwd);
	}

	public String getFocusUserId() {
		return focusUserId;
	}

	public void setFocusUserId(String focusUserId) {
		this.focusUserId = focusUserId;
	}

	public String getInviterUserId() {
		return inviterUserId;
	}

	public void setInviterUserId(String inviterUserId) {
		this.inviterUserId = inviterUserId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
		this.addParma("category", category);
	}

	public DiguClient getDiguClient() {
		return diguClient;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.addParma("name", name);
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
		this.addParma("oldName", oldName);
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
		this.addParma("newName", newName);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
		this.addParma("id", id);
	}

	public String getReceiveUserId() {
		return receiveUserId;
	}

	public void setReceiveUserId(String receiveUserId) {
		this.receiveUserId = receiveUserId;
		this.addParma("receiveUserId", receiveUserId);
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
		this.addParma("q", q);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
