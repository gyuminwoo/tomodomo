package com.VO;

import java.util.Date;

public class Post {
	private int idx;
	private int userIdx;
	private String title;
	private String category;
	private int categoryIdx;
	private String content;
	private String fileName;
	private Date pdate;
	private UserDetails user;
	private String tname;
	
	private String addCate;
	
	public Post(int idx, int userIdx, String title, String category, int categoryIdx, String content, String fileName, Date pdate, UserDetails user) {
		super();
		this.idx = idx;
		this.userIdx = userIdx;
		this.title = title;
		this.category = category;
		this.categoryIdx = categoryIdx;
		this.content = content;
		this.fileName = fileName;
		this.pdate = pdate;
		this.user = user;
	}
	
	public Post(int userIdx, String title, String category, int categoryIdx, String content, String fileName) {
		super();
		this.userIdx = userIdx;
		this.title = title;
		this.category = category;
		this.categoryIdx = categoryIdx;
		this.content = content;
		this.fileName = fileName;
	}
	
	public Post(int userIdx, String addCate) {
		super();
		this.userIdx = userIdx;
		this.addCate = addCate;
	}

	public Post() {
		super();
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public int getUserIdx() {
		return userIdx;
	}

	public void setUserIdx(int userIdx) {
		this.userIdx = userIdx;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getCategoryIdx() {
		return categoryIdx;
	}

	public void setCategoryIdx(int categoryIdx) {
		this.categoryIdx = categoryIdx;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getPdate() {
		return pdate;
	}

	public void setPdate(Date pdate) {
		this.pdate = pdate;
	}

	public UserDetails getUser() {
		return user;
	}

	public void setUser(UserDetails user) {
		this.user = user;
	}

	public String getAddCate() {
		return addCate;
	}

	public void setAddCate(String addCate) {
		this.addCate = addCate;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}
	
	
}
