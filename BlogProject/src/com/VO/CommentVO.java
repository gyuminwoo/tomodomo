package com.VO;

public class CommentVO {

	private int cidx;
	private int uidx;
	private String image;
	private String nickname;
	private String cdate;
	private String comment;
	private int cgroupnum;
	private int cclass;
	
	public CommentVO() {}

	public CommentVO(int cidx, int uidx, String image, String nickname, String cdate, String comment, int cgroupnum, int cclass) {
		super();
		this.cidx = cidx;
		this.uidx = uidx;
		this.image = image;
		this.nickname = nickname;
		this.cdate = cdate;
		this.comment = comment;
		this.cgroupnum = cgroupnum;
		this.cclass = cclass;
	}

	public int getUidx() {
		return uidx;
	}

	public void setUidx(int uidx) {
		this.uidx = uidx;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getCdate() {
		return cdate;
	}

	public void setCdate(String cdate) {
		this.cdate = cdate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getCgroupnum() {
		return cgroupnum;
	}

	public void setCgroupnum(int cgroupnum) {
		this.cgroupnum = cgroupnum;
	}

	public int getCclass() {
		return cclass;
	}

	public void setCclass(int cclass) {
		this.cclass = cclass;
	}

	public int getCidx() {
		return cidx;
	}

	public void setCidx(int cidx) {
		this.cidx = cidx;
	}
	
	
}
