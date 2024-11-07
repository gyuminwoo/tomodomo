package com.VO;

public class BlogProfile {
	private int idx;
	private int user_idx;
	private String blog_intro;
	private String profile_image;
	private String nickname;
	private boolean myPageCheck;
	
	public BlogProfile() {}
	
	public BlogProfile(int user_idx, String blog_intro, String profile_image) {
		super();
		this.user_idx = user_idx;
		this.blog_intro = blog_intro;
		this.profile_image = profile_image;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public int getUser_idx() {
		return user_idx;
	}

	public void setUser_idx(int user_idx) {
		this.user_idx = user_idx;
	}

	public String getBlog_intro() {
		return blog_intro;
	}

	public void setBlog_intro(String blog_intro) {
		this.blog_intro = blog_intro;
	}

	public String getProfile_image() {
		return profile_image;
	}

	public void setProfile_image(String profile_image) {
		this.profile_image = profile_image;
	}

	public boolean isMyPageCheck() {
		return myPageCheck;
	}

	public void setMyPageCheck(boolean myPageCheck) {
		this.myPageCheck = myPageCheck;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	
}
