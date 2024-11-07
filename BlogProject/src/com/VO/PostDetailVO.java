package com.VO;

public class PostDetailVO {
	private int pidx;
	private int user_idx;
	private String theme;
	private String title;
	private String content;
	private String image;
	private String nickname;
	private String postdate;
	private Integer prevIdx;
	private Integer nextIdx;
	private String prevTitle;
	private String nextTitle;
	private boolean myPage;
	private int viewcount;
	
	public PostDetailVO() {}
	
	public PostDetailVO(int pidx, int user_idx, String theme, String title, String content, String image, String nickname, String postdate,
			Integer prevIdx, Integer nextIdx, String prevTitle, String nextTitle) {
		super();
		this.pidx = pidx;
		this.user_idx = user_idx;
		this.theme = theme;
		this.title = title;
		this.content = content;
		this.image = image;
		this.nickname = nickname;
		this.postdate = postdate;
		this.prevIdx = prevIdx;
		this.nextIdx = nextIdx;
		this.prevTitle = prevTitle;
		this.nextTitle = nextTitle;
	}

	public int getPidx() {
		return pidx;
	}

	public void setPidx(int pidx) {
		this.pidx = pidx;
	}

	public int getUser_idx() {
		return user_idx;
	}

	public void setUser_idx(int user_idx) {
		this.user_idx = user_idx;
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPostdate() {
		return postdate;
	}

	public void setPostdate(String postdate) {
		this.postdate = postdate;
	}

	public int getPrevIdx() {
		return prevIdx;
	}

	public void setPrevIdx(Integer prevIdx) {
		this.prevIdx = prevIdx;
	}

	public int getNextIdx() {
		return nextIdx;
	}

	public void setNextIdx(Integer nextIdx) {
		this.nextIdx = nextIdx;
	}

	public String getPrevTitle() {
		return prevTitle;
	}

	public void setPrevTitle(String prevTitle) {
		this.prevTitle = prevTitle;
	}

	public String getNextTitle() {
		return nextTitle;
	}

	public void setNextTitle(String nextTitle) {
		this.nextTitle = nextTitle;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isMyPage() {
		return myPage;
	}

	public void setMyPage(boolean myPage) {
		this.myPage = myPage;
	}

	public int getViewcount() {
		return viewcount;
	}

	public void setViewcount(int viewcount) {
		this.viewcount = viewcount;
	}
	
	
}
