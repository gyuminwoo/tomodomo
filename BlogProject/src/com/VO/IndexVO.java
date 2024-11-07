package com.VO;

public class IndexVO {
	private int pidx;
	private String title;
	private String content;
	private String filename;
	private String image;
	private String intro;
	private String nickname;
	private String theme;
	private int viewcount;
	private int suggest;
	public IndexVO() {}
	public IndexVO(int pidx, String title, String content, String filename, String image, String intro,
			String nickname, String theme, int viewcount, int suggest) {
		super();
		this.pidx = pidx;
		this.title = title;
		this.content = content;
		this.filename = filename;
		this.image = image;
		this.intro = intro;
		this.nickname = nickname;
		this.theme = theme;
		this.viewcount = viewcount;
		this.suggest = suggest;
	}
	public int getPidx() {
		return pidx;
	}
	public void setPidx(int pidx) {
		this.pidx = pidx;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public int getViewcount() {
		return viewcount;
	}
	public void setViewcount(int viewcount) {
		this.viewcount = viewcount;
	}
	public int getSuggest() {
		return suggest;
	}
	public void setSuggest(int suggest) {
		this.suggest = suggest;
	}
	
	
	
}
