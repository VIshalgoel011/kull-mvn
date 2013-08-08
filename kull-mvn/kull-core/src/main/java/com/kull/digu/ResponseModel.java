package com.kull.digu;

import java.util.Date;



public class ResponseModel {

	

	
	private Date created_at;
	//这个元素创建的UTC 时间戳。例如： Sat Jan 24 22:14:29 +0000 2009
	
	private String description;
	//用户的个人简介
	
	private boolean favorited;
	//该条嘀咕信息是否被收藏，被收藏，值为true，否则为false。
	
	private long favorites_count;
	//用户收藏的总数量
	
	private long followers_count;
	//跟随该用户的数量
	
	private long friends_count;
	//用户跟随的数量，即用户的朋友数
	
	private String name;
	//用户的登录名
	
	private String id;
	//用户或者嘀咕信息的id
	
	private String in_reply_to_screen_name;
	//接收回复信息的那个人的昵称
	
	private String in_reply_to_status_id;
	//被回复嘀咕信息的id
	
	private String in_reply_to_user_id;
	//接受回复信息的那个用户的id
	
	private String in_reply_to_user_name;
	//接收回复的用户名
	
	private String location;
	//用户的所在地，地址

	private String profile_background_color;
	//用户自定义背景颜色值。
	
	private String profile_background_image_url;
	//用户背景图片路径
	
	private String profile_background;
	//用户背景图片位置。（title、left、center、right）。
	
	private String profile_image_url;
	//用户头像路径
	
	private String profile_link_color;
	//用户自定义链接的颜色
	
	private String profile_sidebar_border_color;
	//用户自定义的边栏颜色
	
	private String profile_sidebar_menu_color;
	//用户自定义边栏的菜单颜色
	
	private String profile_sidebar_text_color;
	//用户自定义边栏的链接颜色
	
	private String profile_text_color;
	//用户自定义的文本颜色
	
	private boolean Protected;
	//我们的含义是：false，主页显示@回复。true 不显示。
	
	private String recipient_id;
	//接受者的id
	
	private String recipient_screen_name;
	//接受者的昵称
	
	private String reset_time_in_seconds;
	//重设时间，值为long 型的秒数。
	
	private String screen_name;
	//用户的昵称
	 
	private String sender_id;
	//发送者的用户id
	
	private String sender_screen_name;
	//发送者的昵称
	private String source;
	//消息来源
	
	private String statuses_count;
	//除了发送悄悄话的数量，用户更新嘀咕的总数量。
	
	private String text;
	//嘀咕信息的内容
	
	private String url;
	//用户的个人主页
	
	private String utc_offset;
	//用户注册的时区时间和世界商定的时间之间的差。Long 型数字如: -21600 (Default), 36000
	
	private String verify;
	//授权验证
	
	private boolean authorized;
	//验证帐户是否成功，成功为true。
	
	private String message;
	//悄悄话或者戳一下或者错误信息。
	
	private boolean AFollowB;
	//用户A 是否跟随了用户B。True 跟随了，false 没有跟随。
	
	private boolean BFollowA;
	//用户B 是否跟随了用户A。True 跟随了，false 没有跟随。
	
	private String result;
	//返回结果。
	
	private String category;
	//表示是悄悄话还是戳一下。0 表示悄悄话，1 表示戳一下。或者收藏夹中的分组
	
	private String privacy;
	//表示该用户是否设置了隐私。0 表示没有设置隐私，1 表示设置了隐私。

	private String gender;
	//性别，0 表示没有设置性别，1 表示男性，2 表示女性，3 表示设置了隐私，不可以查看。

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date createdAt) {
		created_at = createdAt;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isFavorited() {
		return favorited;
	}

	public void setFavorited(boolean favorited) {
		this.favorited = favorited;
	}

	public long getFavorites_count() {
		return favorites_count;
	}

	public void setFavorites_count(long favoritesCount) {
		favorites_count = favoritesCount;
	}

	public long getFollowers_count() {
		return followers_count;
	}

	public void setFollowers_count(long followersCount) {
		followers_count = followersCount;
	}

	public long getFriends_count() {
		return friends_count;
	}

	public void setFriends_count(long friendsCount) {
		friends_count = friendsCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIn_reply_to_screen_name() {
		return in_reply_to_screen_name;
	}

	public void setIn_reply_to_screen_name(String inReplyToScreenName) {
		in_reply_to_screen_name = inReplyToScreenName;
	}

	public String getIn_reply_to_status_id() {
		return in_reply_to_status_id;
	}

	public void setIn_reply_to_status_id(String inReplyToStatusId) {
		in_reply_to_status_id = inReplyToStatusId;
	}

	public String getIn_reply_to_user_id() {
		return in_reply_to_user_id;
	}

	public void setIn_reply_to_user_id(String inReplyToUserId) {
		in_reply_to_user_id = inReplyToUserId;
	}

	public String getIn_reply_to_user_name() {
		return in_reply_to_user_name;
	}

	public void setIn_reply_to_user_name(String inReplyToUserName) {
		in_reply_to_user_name = inReplyToUserName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getProfile_background_color() {
		return profile_background_color;
	}

	public void setProfile_background_color(String profileBackgroundColor) {
		profile_background_color = profileBackgroundColor;
	}

	public String getProfile_background_image_url() {
		return profile_background_image_url;
	}

	public void setProfile_background_image_url(String profileBackgroundImageUrl) {
		profile_background_image_url = profileBackgroundImageUrl;
	}

	public String getProfile_background() {
		return profile_background;
	}

	public void setProfile_background(String profileBackground) {
		profile_background = profileBackground;
	}

	public String getProfile_image_url() {
		return profile_image_url;
	}

	public void setProfile_image_url(String profileImageUrl) {
		profile_image_url = profileImageUrl;
	}

	public String getProfile_link_color() {
		return profile_link_color;
	}

	public void setProfile_link_color(String profileLinkColor) {
		profile_link_color = profileLinkColor;
	}

	public String getProfile_sidebar_border_color() {
		return profile_sidebar_border_color;
	}

	public void setProfile_sidebar_border_color(String profileSidebarBorderColor) {
		profile_sidebar_border_color = profileSidebarBorderColor;
	}

	public String getProfile_sidebar_menu_color() {
		return profile_sidebar_menu_color;
	}

	public void setProfile_sidebar_menu_color(String profileSidebarMenuColor) {
		profile_sidebar_menu_color = profileSidebarMenuColor;
	}

	public String getProfile_sidebar_text_color() {
		return profile_sidebar_text_color;
	}

	public void setProfile_sidebar_text_color(String profileSidebarTextColor) {
		profile_sidebar_text_color = profileSidebarTextColor;
	}

	public String getProfile_text_color() {
		return profile_text_color;
	}

	public void setProfile_text_color(String profileTextColor) {
		profile_text_color = profileTextColor;
	}

	public boolean isProtected() {
		return Protected;
	}

	public void setProtected(boolean protected1) {
		Protected = protected1;
	}

	public String getRecipient_id() {
		return recipient_id;
	}

	public void setRecipient_id(String recipientId) {
		recipient_id = recipientId;
	}

	public String getRecipient_screen_name() {
		return recipient_screen_name;
	}

	public void setRecipient_screen_name(String recipientScreenName) {
		recipient_screen_name = recipientScreenName;
	}

	public String getReset_time_in_seconds() {
		return reset_time_in_seconds;
	}

	public void setReset_time_in_seconds(String resetTimeInSeconds) {
		reset_time_in_seconds = resetTimeInSeconds;
	}

	public String getScreen_name() {
		return screen_name;
	}

	public void setScreen_name(String screenName) {
		screen_name = screenName;
	}

	public String getSender_id() {
		return sender_id;
	}

	public void setSender_id(String senderId) {
		sender_id = senderId;
	}

	public String getSender_screen_name() {
		return sender_screen_name;
	}

	public void setSender_screen_name(String senderScreenName) {
		sender_screen_name = senderScreenName;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getStatuses_count() {
		return statuses_count;
	}

	public void setStatuses_count(String statusesCount) {
		statuses_count = statusesCount;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUtc_offset() {
		return utc_offset;
	}

	public void setUtc_offset(String utcOffset) {
		utc_offset = utcOffset;
	}

	public String getVerify() {
		return verify;
	}

	public void setVerify(String verify) {
		this.verify = verify;
	}

	public boolean isAuthorized() {
		return authorized;
	}

	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isAFollowB() {
		return AFollowB;
	}

	public void setAFollowB(boolean aFollowB) {
		AFollowB = aFollowB;
	}

	public boolean isBFollowA() {
		return BFollowA;
	}

	public void setBFollowA(boolean bFollowA) {
		BFollowA = bFollowA;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPrivacy() {
		return privacy;
	}

	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}



	
	
	
	
}
