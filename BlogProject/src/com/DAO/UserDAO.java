package com.DAO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

import com.VO.IndexVO;
import com.VO.InquiriesVO;
import com.VO.UserDetails;

public class UserDAO {
	private Connection conn;

	public UserDAO(Connection conn) {
		super();
		this.conn = conn;
	}
	
	public boolean addUser(UserDetails us) {
		boolean f = false;
		
		try {
			String sql = "insert into bloguser (idx, id, password, nickname, email, birthdate, gender) values (user_seq.NEXTVAL, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, us.getId());
			pstmt.setString(2, hashPassword(us.getPassword()));
			pstmt.setNString(3, us.getNickname());
			pstmt.setString(4, us.getEmail());
			pstmt.setDate(5, Date.valueOf(us.getBirthdate()));
			pstmt.setString(6, us.getGender());
			
			int i = pstmt.executeUpdate();
			if(i==1) {
				f=true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return f;
	}
	
	public UserDetails loginUser(UserDetails us) {
		UserDetails user = null;
		try {
			String sql = "select * from bloguser where id = ? and password = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, us.getId());
			pstmt.setString(2, hashPassword(us.getPassword()));
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				user = new UserDetails();
				user.setIdx(rs.getInt("idx"));
				user.setId(rs.getString("id"));
				user.setPassword(rs.getString("password"));
				user.setNickname(rs.getNString("nickname"));
				user.setEmail(rs.getString("email"));
				user.setAuth(rs.getInt("auth"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	public boolean isIdExists(String id) {
		String sql = "select count(*) from bloguser where id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return rs.getInt(1) > 0;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean isNicknameExists(String nickname) {
		String sql = "select count(*) from bloguser where nickname = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setNString(1, nickname);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void nicknameChange(int idx, String newNickname) {
		
		String sql = "update bloguser set nickname = ? where idx = ?";
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setNString(1, newNickname);
			pstmt.setInt(2, idx);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void passwordChange(int idx, String newPassword) {
		
		String sql = "update bloguser set password = ? where idx = ?";
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, hashPassword(newPassword));
			pstmt.setInt(2, idx);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void deleteAccount(int userIdx) {
		try {
			String sql = "delete from bloguser where idx = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userIdx);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	private String hashPassword(String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hashedBytes = digest.digest(password.getBytes());
			StringBuilder hexString = new StringBuilder();
			
			for(byte b : hashedBytes) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			
			return hexString.toString();
			
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<IndexVO> getBlog() {
		
		List<IndexVO> blogList = new ArrayList<IndexVO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "select a.*, rownum from (select rownum as rnum, bi.profile_image as image, bi.blog_intro as intro, b.nickname, b.auth from bloguser b left outer join blog_introduction bi on b.idx = bi.user_idx order by b.visitcount desc) a where rownum < 6";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				if(rs.getInt("auth") == 1) {
					continue;
				}
				IndexVO bvo = new IndexVO();
				bvo.setImage(rs.getNString("image"));
				bvo.setIntro(rs.getNString("intro"));
				bvo.setNickname(rs.getNString("nickname"));
				blogList.add(bvo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
		
		return blogList;
	}
	
	public List<IndexVO> getPost() {
		
		List<IndexVO> postList = new ArrayList<IndexVO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "select * from (select rownum as rnum, p.idx, p.suggest, p.viewcount, p.title, p.content, p.category, p.file_name, nickname from (select posts.idx, title, content, suggest, viewcount, category, file_name, nickname from posts join bloguser u on posts.user_idx = u.idx order by suggest desc) p) where rnum <= 5";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				IndexVO pvo = new IndexVO();
				pvo.setPidx(rs.getInt("idx"));
				pvo.setTitle(rs.getNString("title"));
				String content = rs.getNString("content");
				pvo.setTheme(rs.getNString("category"));
	            String base64Pattern = "data:image/(.*?);base64,([^\"']*)";
	            Pattern pattern = Pattern.compile(base64Pattern);
	            Matcher matcher = pattern.matcher(content);

	            StringBuilder base64Images = new StringBuilder();

	            if (matcher.find()) {
	                String mimeType = matcher.group(1);
	                String base64Data = matcher.group(2);
	                String imgSrc = "data:image/" + mimeType + ";base64," + base64Data;
	                base64Images.append(imgSrc).append("\n");
	            }

	            if (base64Images.length() > 0) {
	                pvo.setFilename(base64Images.toString().trim());
	            } else {
	                pvo.setFilename(rs.getNString("file_name"));
	            }
	            content = whitelist(content);
	            
	            
	            
	            if (content.length() > 100) {
	                content = content.substring(0, 99) + "...";
	            }
	            
	            pvo.setContent(content);
	            pvo.setNickname(rs.getNString("nickname"));
	            pvo.setViewcount(rs.getInt("viewcount"));
	            pvo.setSuggest(rs.getInt("suggest"));
	            postList.add(pvo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
		
		return postList;
	}
	
	public void setInquiry(int idx, String email, String nickname, String type, String content) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "insert into inquiries (idx, user_idx, email, nickname, inquiry_type, message) values (inquiry_seq.NEXTVAL, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.setString(2, email);
			pstmt.setNString(3, nickname);
			pstmt.setString(4, type);
			pstmt.setNString(5, content);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
	}
	
	public List<InquiriesVO> getInquiries() {
		List<InquiriesVO> ilist = new ArrayList<InquiriesVO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "select idx, email, nickname, inquiry_type as type, message as content, solvestate, '20' || substr(created_at, 0, 17) as idate from inquiries order by solvestate asc, created_at asc";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				InquiriesVO ivo = new InquiriesVO();
				ivo.setIdx(rs.getInt("idx"));
				ivo.setEmail(rs.getString("email"));
				ivo.setNickname(rs.getNString("nickname"));
				String inquiryType = rs.getString("type");
	            switch (inquiryType) {
	                case "advertising":
	                    ivo.setType("広告問い合わせ");
	                    break;
	                case "report":
	                    ivo.setType("ユーザー報告");
	                    break;
	                case "general":
	                    ivo.setType("その他お問い合わせ");
	                    break;
	                default:
	                    ivo.setType(inquiryType);
	                    break;
	            }
	            ivo.setContent(rs.getNString("content"));
	            ivo.setState(rs.getInt("solvestate"));
	            ivo.setIdate(rs.getString("idate"));
	            ilist.add(ivo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
		
		return ilist;
	}
	
	public void setSolve(int idx) {
		try {
			String sql = "update inquiries set solvestate = 1 where idx = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getUserInfo(String email) {
		String id = "そのメールアドレスで登録された情報はありません。";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select id from bloguser where email = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				id = rs.getString("id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
		
		return id;
	}
	
	public void modifyPassword(String id, String password) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "update bloguser set password = ? where id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, hashPassword(password));
			pstmt.setString(2, id);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
	}
	
	private void close(ResultSet rs, PreparedStatement pstmt) {
		try {
			if(rs != null) {
				rs.close();
			}
			if(pstmt != null) {
				pstmt.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String whitelist(String content) {
		return Jsoup.clean(content, Safelist.none());
	}
	
}
