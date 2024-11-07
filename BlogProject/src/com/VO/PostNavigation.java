package com.VO;

public class PostNavigation {
	private Integer prevIdx;
	private Integer nextIdx;
	private String prevTitle;
	private String nextTitle;
	
	public PostNavigation() {}
	
	public PostNavigation(Integer prevIdx, Integer nextIdx, String prevTitle, String nextTitle) {
		super();
		this.prevIdx = prevIdx;
		this.nextIdx = nextIdx;
		this.prevTitle = prevTitle;
		this.nextTitle = nextTitle;
	}

	public Integer getPrevIdx() {
		return prevIdx;
	}

	public void setPrevIdx(Integer prevIdx) {
		this.prevIdx = prevIdx;
	}

	public Integer getNextIdx() {
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
	
	
}
