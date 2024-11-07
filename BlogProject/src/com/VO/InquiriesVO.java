package com.VO;

public class InquiriesVO {
	private int idx;
	private String type;
	private String email;
	private String nickname;
	private String idate;
	private int state;
	private String content;
	
	public InquiriesVO() {}

	public InquiriesVO(int idx, String type, String email, String nickname, String idate, int state, String content) {
		super();
		this.idx = idx;
		this.type = type;
		this.email = email;
		this.nickname = nickname;
		this.idate = idate;
		this.state = state;
		this.content = content;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getIdate() {
		return idate;
	}

	public void setIdate(String idate) {
		this.idate = idate;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
