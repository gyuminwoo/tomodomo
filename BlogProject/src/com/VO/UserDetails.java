package com.VO;

import java.time.LocalDate;

public class UserDetails {
	private int idx;
    private String id;
    private String password;
    private String nickname;
    private String email;
    private LocalDate birthdate;
    private String gender;
    private int auth;
    
    public UserDetails() {
        super();
    }

    public UserDetails(String id, String password, String nickname, String email, LocalDate birthdate, String gender) {
        super();
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.birthdate = birthdate;
        this.gender = gender;
    }
    
    public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) { 
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

	public int getAuth() {
		return auth;
	}

	public void setAuth(int auth) {
		this.auth = auth;
	}
    
    
}
