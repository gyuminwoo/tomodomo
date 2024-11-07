package com.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

import com.VO.BlogProfile;
import com.VO.CommentVO;
import com.VO.Post;
import com.VO.PostDetailVO;
import com.VO.PostNavigation;

public class BlogDAO {
	private Connection conn;
	
	public BlogDAO(Connection conn) {
		super();
		this.conn = conn;
	}
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	public boolean addPost(Post pvo) {
		
		boolean f = false;
		
		try {
			String sql = "insert into posts (idx, user_idx, title, category, content, file_name, category_idx) values (post_seq.NEXTVAL, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pvo.getUserIdx());
			pstmt.setNString(2, pvo.getTitle());
			pstmt.setNString(3, pvo.getCategory());
			pstmt.setNString(4, pvo.getContent());
			pstmt.setNString(5, pvo.getFileName());
			pstmt.setInt(6, pvo.getCategoryIdx());
			
			int i = pstmt.executeUpdate();
			if(i == 1) { f = true; }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
		
		return f;
		
	}
	
	public void addTheme(Post pvo) {
		
		try {
			String sql = "insert into categories (idx, user_idx, cate_name) values (category_seq.NEXTVAL, ?, ?)";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pvo.getUserIdx());
			pstmt.setNString(2, pvo.getAddCate());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
	}
	
	public List<Post> getTheme(int userIdx) {
		
		List<Post> list = new ArrayList<Post>();
		
		try {
			String sql = "select idx, cate_name from categories where user_idx = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userIdx);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Post pvo = new Post();
				pvo.setCategoryIdx(rs.getInt("idx"));
				pvo.setCategory(rs.getNString("cate_name"));
				list.add(pvo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
		
		return list;
	}
	
	public List<Post> getPosts(int userIdx, int startIndex, int pageSize, String tname) {
	    List<Post> list = new ArrayList<>();

	    try {
	    	String sql = "";
	    	if(tname == null) {
	    		sql = "SELECT * FROM (SELECT a.*, ROWNUM rnum FROM (SELECT * FROM posts WHERE user_idx = ? ORDER BY idx DESC) a WHERE ROWNUM <= ?) WHERE rnum > ?";
	    	} else {
	    		sql = "select * from (select a.*, rownum rnum from (select * from posts where user_idx = ? and category = ? order by idx desc) a where rownum <= ?) where rnum > ?";
	    	}
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        if(tname == null) {
		        pstmt.setInt(1, userIdx);
		        pstmt.setInt(2, startIndex + pageSize);
		        pstmt.setInt(3, startIndex);
	        } else {
	        	pstmt.setInt(1, userIdx);
	        	pstmt.setNString(2, tname);
	        	pstmt.setInt(3, startIndex + pageSize);
	        	pstmt.setInt(4, startIndex);
	        }

	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	            Post pvo = new Post();
	            pvo.setIdx(rs.getInt("idx"));
	            pvo.setTitle(rs.getNString("title"));
	            String content = rs.getNString("content");
	            
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
	                pvo.setFileName(base64Images.toString().trim());
	            } else {
	                pvo.setFileName(rs.getNString("file_name"));
	            }
	            content = whitelist(content);
	            
	            
	            
	            if (content.length() > 100) {
	                content = content.substring(0, 99) + "...";
	            }
	            pvo.setContent(content);
	            pvo.setCategory(rs.getNString("category"));
	            pvo.setCategoryIdx(rs.getInt("category_idx"));
	            pvo.setPdate(rs.getDate("created_at"));
	            list.add(pvo);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	    	close(rs, pstmt);
	    }

	    return list;
	}
	
	public List<BlogProfile> getMainBlogs(int startIndex, int pageSize) {
		List<BlogProfile> list = new ArrayList<>();
		
		try {
			String sql = "select * from (select a.*, rownum rnum from (select b.idx as idx, b.user_idx as uidx, b.profile_image as img, b.blog_intro as intro, u.nickname as nickname from blog_introduction b join bloguser u on b.user_idx = u.idx order by b.updated_at desc) a where rownum <= ?) where rnum > ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startIndex + pageSize);
			pstmt.setInt(2, startIndex);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				BlogProfile bp = new BlogProfile();
				bp.setNickname(rs.getNString("nickname"));
				bp.setProfile_image(rs.getNString("img"));
				String intro = whitelist(rs.getNString("intro"));
				bp.setBlog_intro(intro);
				list.add(bp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
		
		return list;
	}
	
	public int totalPost(int user_idx) {
		int totalPost = 0;
		try {
			String sql = "select count(*) as c from posts where user_idx = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user_idx);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			totalPost = rs.getInt("c");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
		return totalPost;
	}
	
	public int themeSelectTotalPost(int idx, String tname) {
		int total = 0;
		
		try {
			String sql ="select count(*) as c from posts where user_idx = ? and category = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.setNString(2, tname);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			total = rs.getInt("c");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
		
		return total;
	}
	
	public void blogProfile(BlogProfile bpvo) {
		try {
			String sql;
			boolean ox = isBlogProfile(bpvo.getUser_idx());
			if(ox == true) {
				sql = "insert into blog_introduction (idx, user_idx, blog_intro, profile_image) values (blog_intro_seq.NEXTVAL, ?, ?, ?)";
			} else {
				sql = "update blog_introduction set profile_image = ?, blog_intro = ? where user_idx = ?";
			}
			PreparedStatement pstmt = conn.prepareStatement(sql);
			String intro = brokWhitelist(bpvo.getBlog_intro());
			System.out.println(intro);
			System.out.println(bpvo.getProfile_image());
			if(ox == true) {
				pstmt.setInt(1, bpvo.getUser_idx());
				pstmt.setNString(2, intro);
				pstmt.setNString(3, bpvo.getProfile_image());
			} else {
				if(bpvo.getProfile_image() == null) {
					pstmt.setNString(1, notChangeImg(bpvo.getUser_idx()));
				} else {
					pstmt.setNString(1, bpvo.getProfile_image());
				}
				pstmt.setNString(2, intro);
				pstmt.setInt(3, bpvo.getUser_idx());
			}
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
		
	}
	
	private boolean isBlogProfile(int user_idx) {
		boolean f = false;
		
		try {
			String sql = "select count(*) as c from blog_introduction where user_idx = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user_idx);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			if(rs.getInt("c") == 0) {
				f = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return f;
	}
	
	public BlogProfile getBlogProfile(int user_idx) {
		BlogProfile bpvo = new BlogProfile();
		try {
			String sql = "select * from blog_introduction where user_idx = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user_idx);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next() == false) {
				bpvo.setProfile_image("/blogprofileimg/user.png");
				bpvo.setBlog_intro("");
			} else {
				bpvo.setProfile_image(rs.getNString("profile_image"));
				
				bpvo.setBlog_intro(rs.getNString("blog_intro").replace("<br>", "").replaceFirst("\n", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
		return bpvo;
	}
	
	private String notChangeImg(int user_idx) {
		String img = "";
		
		try {
			String sql = "select profile_image from blog_introduction where user_idx = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user_idx);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			img = rs.getNString("profile_image");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
		
		return img;
	}
	
	public BlogProfile getBlogData(int user_idx, Integer checkIdx) {
		BlogProfile bp = new BlogProfile();
		try {
			String sql = "";
			boolean isProfile = isProfile(user_idx);
			if(isProfile == true) {
				sql = "select b.*, u.nickname as nickname from blog_introduction b, bloguser u where b.user_idx = u.idx and u.idx = ?";
			} else {
				sql = "select nickname from bloguser where idx = ?";
			}
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, user_idx);
			ResultSet rs = pstmt.executeQuery();
			boolean check = false;
			if(checkIdx != null) {
				check = myPageCheck(user_idx, checkIdx);
			}
			bp.setMyPageCheck(check);
			System.out.println(bp.isMyPageCheck());
			if(rs.next() == false) {
				bp = null;
			} else {
				if(isProfile == true) {
					bp.setBlog_intro(rs.getNString("blog_intro"));
					bp.setProfile_image(rs.getNString("profile_image"));
					bp.setNickname(rs.getNString("nickname"));
				} else {
					bp.setNickname(rs.getNString("nickname"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
		return bp;
	}
	
	public int getUserIdx(String nick) {
		int user_idx = 0;
		
		try {
			String sql = "select idx from bloguser where nickname = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setNString(1, nick);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				user_idx = rs.getInt("idx");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
		
		return user_idx;
	}
	
	public boolean isValidUrl(String nick) {
		boolean check = false;
		
		try {
			String sql = "select count(*) from bloguser where nickname = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setNString(1, nick);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			if(rs.getInt("count(*)") == 1) {
				check = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
		
		return check;
	}
	
	private boolean isProfile(int user_idx) {
		boolean check = false;
		
		try {
			String sql = "select count(*) as c from blog_introduction where user_idx = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user_idx);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			if(rs.getInt("c") == 1) {
				check = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return check;
	}
	
	public String getNickname(int user_idx) {
		String nickname = "";
		
		try {
			String sql = "select nickname from bloguser where idx = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user_idx);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			nickname = rs.getNString("nickname");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
		
		return nickname;
	}
	
	public int getuidx(int pidx) {
		int uidx = 0;
		
		try {
			String sql = "select user_idx from posts where idx = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pidx);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				uidx = rs.getInt("user_idx");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
		
		return uidx;
	}
	
	public int getTotalBlogProfile() {
		int total = 0;
		
		try {
			String sql = "select count(*) as total from blog_introduction";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			total = rs.getInt("total");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
		
		return total;
	}
	
	public PostDetailVO getPostDetail(int pidx) {
		PostDetailVO pvo = null;
		try {
			String sql = "select p.idx, p.viewcount, p.category, p.title, p.user_idx, p.content, bi.profile_image as image, b.nickname, substr(p.updated_at, 0, 2) || '-' || substr(p.updated_at, 4, 2) || '-' || substr(p.updated_at, 7, 2) as postdate from posts p left outer join blog_introduction bi on p.user_idx = bi.user_idx left outer join bloguser b on b.idx = p.user_idx where p.idx = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pidx);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				pvo = new PostDetailVO();
				pvo.setPidx(rs.getInt("idx"));
				pvo.setViewcount(rs.getInt("viewcount"));
				pvo.setUser_idx(rs.getInt("user_idx"));
				pvo.setTheme(rs.getNString("category"));
				pvo.setTitle(rs.getNString("title"));
				pvo.setContent(rs.getNString("content"));
				pvo.setImage(rs.getNString("image"));
				pvo.setNickname(rs.getNString("nickname"));
				pvo.setPostdate(rs.getNString("postdate"));
				PostNavigation nav = new PostNavigation();
				getNP(pvo.getPidx(), pvo.getUser_idx(), nav);
				pvo.setPrevIdx(nav.getPrevIdx());
				pvo.setPrevTitle(nav.getPrevTitle());
				pvo.setNextIdx(nav.getNextIdx());
				pvo.setNextTitle(nav.getNextTitle());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
		
		
		return pvo;
	}
	
	public void modifyViewCount(int pidx) {
		try {
			String sql = "update posts set viewcount = viewcount + 1 where idx = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pidx);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
	}
	
	public void modifyVisitCount(int userIdx) {
		try {
			String sql = "update bloguser set visitcount = visitcount + 1 where idx = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userIdx);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
	}
	
	public void insertComment(String content, int uidx, int pidx) {
		try {
			String escapedContent = StringEscapeUtils.escapeHtml4(content);
			
			String sql = "insert into comments (idx, post_idx, user_idx, comments, commentgroupnum) values (comment_seq.NEXTVAL, ?, ?, ?, (select case (select count(*) as c from comments where post_idx = ?) when 0 then 0 else (select max(commentgroupnum) from comments where post_idx = ?) end from dual) + 1)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pidx);
			pstmt.setInt(2, uidx);
			pstmt.setNString(3, escapedContent);
			pstmt.setInt(4, pidx);
			pstmt.setInt(5, pidx);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
	}
	
	public void insertReply(String content, int uidx, int pidx, int groupNum) {
		try {
			String escapedContent = StringEscapeUtils.escapeHtml4(content);
			String sql = "insert into comments (idx, post_idx, user_idx, comments, commentclass, commentgroupnum) values (comment_seq.NEXTVAL, ?, ?, ?, 1, (select commentgroupnum from comments where post_idx = ? and commentgroupnum = ? and rownum = 1))";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pidx);
			pstmt.setInt(2, uidx);
			pstmt.setNString(3, escapedContent);
			pstmt.setInt(4, pidx);
			pstmt.setInt(5, groupNum);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
	}
	
	public List<CommentVO> getCommentList(int pidx, int pageSize, int startindex) {
		List<CommentVO> list = new ArrayList<CommentVO>();
		
		try {
			String sql = "select * from (select a.*, rownum rnum from (select bi.profile_image, b.idx, b.nickname, c.idx as cidx, '20' || substr(c.created_at, 0, 8) as cdate, c.comments, c.commentgroupnum as cgroupnum, c.commentclass as cclass from comments c left outer join blog_introduction bi on c.user_idx = bi.user_idx left outer join bloguser b on c.user_idx = b.idx left outer join posts p on c.post_idx = p.idx where c.post_idx = ? order by c.commentgroupnum desc, c.commentclass asc, c.created_at asc) a where rownum <= ?) where rnum > ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pidx);
			pstmt.setInt(2, pageSize + startindex);
			pstmt.setInt(3, startindex);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CommentVO cvo = new CommentVO();
				cvo.setCidx(rs.getInt("cidx"));
				cvo.setUidx(rs.getInt("idx"));
				cvo.setNickname(rs.getNString("nickname"));
				cvo.setComment(rs.getNString("comments"));
				cvo.setImage(rs.getNString("profile_image"));
				cvo.setCdate(rs.getString("cdate"));
				cvo.setCgroupnum(rs.getInt("cgroupnum"));
				cvo.setCclass(rs.getInt("cclass"));
				list.add(cvo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
		
		
		return list;
	}
	
	public int getCommentTotal(int pidx) {
		int total = 0;
		
		try {
			String sql = "select count(*) as c from comments where post_idx = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pidx);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				total = rs.getInt("c");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
		return total;
	}
	
	public List<Post> getSearchData(String keyword, String type, int startIndex, int pageSize) {
		List<Post> list = null;
		if(keyword == null || type == null) {
			return list;
		}
		
		try {
			String sql = null;
			if(type.equals("titlecontent")) {
				sql = "select * from (select a.*, rownum rnum from (select p.idx, p.title, b.nickname from posts p join bloguser b on p.user_idx = b.idx where p.title like ? or p.content like ? order by idx desc) a where rownum <= ?) where rnum > ?";
			}
			if(type.equals("author")) {
				sql = "SELECT * FROM (SELECT a.*, ROWNUM rnum FROM (SELECT p.idx, p.title, b.nickname FROM posts p join bloguser b on p.user_idx = b.idx WHERE b.nickname = ? ORDER BY idx DESC) a WHERE ROWNUM <= ?) WHERE rnum > ?";
			}
			PreparedStatement pstmt = conn.prepareStatement(sql);
			if(type.equals("titlecontent")) {
				pstmt.setNString(1, "%"+keyword+"%");
				pstmt.setNString(2, "%"+keyword+"%");
				pstmt.setInt(3, pageSize + startIndex);
				pstmt.setInt(4, startIndex);
			}
			if(type.equals("author")) {
				pstmt.setNString(1, keyword);
				pstmt.setInt(2, pageSize + startIndex);
				pstmt.setInt(3, startIndex);
			}
			ResultSet rs = pstmt.executeQuery();
			list = new ArrayList<Post>();
			while(rs.next()) {
				Post pvo = new Post();
				pvo.setIdx(rs.getInt("idx"));
				pvo.setTitle(rs.getNString("title"));
				pvo.setTname(rs.getNString("nickname"));
				list.add(pvo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
		
		return list;
	}
	
	public int getSearchTotalCount(String keyword, String type) {
		int totalCount = 0;
		
		try {
			String sql = null;
			
			if(type.equals("titlecontent")) {
				sql = "select count(*) as c from posts p join bloguser b on p.user_idx = b.idx where p.title like ? or p.content like ?";
			}
			if(type.equals("author")) {
				sql = "select count(*) as c from posts p join bloguser b on p.user_idx = b.idx where b.nickname = ?";
			} 
			PreparedStatement pstmt = conn.prepareStatement(sql);
			if(type.equals("titlecontent")) {
				pstmt.setNString(1, "%"+keyword+"%");
				pstmt.setNString(2, "%"+keyword+"%");
			}
			if(type.equals("author")) {
				pstmt.setNString(1, keyword);
			}
			ResultSet rs = pstmt.executeQuery();
			
			rs.next();
			
			totalCount = rs.getInt("c");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
		
		return totalCount;
	}
	
	private boolean myPageCheck(int user_idx, int blog_idx) {
		boolean check = false;
		
		try {
			String sql = "select idx from bloguser where idx = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user_idx);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			if(rs.getInt("idx") == blog_idx) {
				check = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
		System.out.println(check);
		return check;
	}
	
	private void getNP(int pIdx, int userIdx, PostNavigation nav) {
		try {
			String sql = "select (select max(idx) from posts where idx < ? and user_idx = ?) as previdx, (select min(idx) from posts where idx > ? and user_idx = ?) as nextidx, (select title from posts where idx = (select max(idx) from posts where idx < ? and user_idx = ?)) as prevtitle, (select title from posts where idx = (select min(idx) from posts where idx > ? and user_idx = ?)) as nexttitle from dual";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pIdx);
			pstmt.setInt(2, userIdx);
			pstmt.setInt(3, pIdx);
			pstmt.setInt(4, userIdx);
			pstmt.setInt(5, pIdx);
			pstmt.setInt(6, userIdx);
			pstmt.setInt(7, pIdx);
			pstmt.setInt(8, userIdx);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				nav.setPrevIdx(rs.getInt("previdx"));
				nav.setNextIdx(rs.getInt("nextidx"));
				nav.setPrevTitle(rs.getNString("prevtitle"));
				nav.setNextTitle(rs.getNString("nexttitle"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
	}
	
	public boolean deletePost(int pIdx, int userIdx) {
		boolean check = false;
		check = postUserCheck(userIdx, pIdx);
		
		if(check == true) {
			try {
				String sql = "delete from posts where idx = ?";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, pIdx);
				pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				close(rs, pstmt);
			}
		}
		return check;
	}
	
	public boolean postUserCheck(int userIdx, int postIdx) {
		boolean check = false;
		
		try {
			String sql = "select count(*) as c from posts where idx = ? and user_idx = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, postIdx);
			pstmt.setInt(2, userIdx);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			if(rs.getInt("c") == 1) {
				check = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
		
		return check;
	}
	
	public Post getPostModifyData(int pidx) {
		Post pvo = new Post();
		
		try {
			String sql = "select title, content, idx from posts where idx = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pidx);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				pvo.setIdx(rs.getInt("idx"));
				pvo.setTitle(rs.getNString("title"));
				pvo.setContent(rs.getNString("content"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
		
		return pvo;
	}
	
	public void setModifyPost(int pidx, String title, String content) {
		try {
			String sql = "update posts set title = ?, content = ? where idx = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setNString(1, title);
			pstmt.setNString(2, content);
			pstmt.setInt(3, pidx);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
	}
	
	public void commentDeleteClass0(int pidx, int cgroupnum) {
		try {
			String sql = "delete from comments where post_idx = ? and commentgroupnum = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pidx);
			pstmt.setInt(2, cgroupnum);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
	}
	
	public void commentDeleteClass1(int cidx) {
		try {
			String sql = "delete from comments where idx = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cidx);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
	}
	
	public void setLike(int like, int uidx, int pidx) {
		boolean check = checkFirst(pidx, uidx);
		
		try {
			String sql = "";
			if(check == true) {
				sql = "update likes set liked = ? where post_idx = ? and user_idx = ?";
			} else {
				sql = "insert into likes values (likes_seq.NEXTVAL, ?, ?, ?)";
			}
			PreparedStatement pstmt = conn.prepareStatement(sql);
			if(check == true) {
				pstmt.setInt(1, like);
				pstmt.setInt(2, pidx);
				pstmt.setInt(3, uidx);
			} else {
				pstmt.setInt(1, pidx);
				pstmt.setInt(2, uidx);
				pstmt.setInt(3, like);
			}
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
		
	}
	
	public void updateSuggestCount(int pidx) {
		try {
			String sql = "update posts set suggest = (select sum(liked) from likes where post_idx = ?) where idx = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pidx);
			pstmt.setInt(2, pidx);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
	}
	
	public int checkLikeState(int pidx, int uidx) {
		int likeState = 516;
		
		try {
			String sql = "select liked from likes where post_idx = ? and user_idx = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pidx);
			pstmt.setInt(2, uidx);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				likeState = rs.getInt("liked");
			} else {
				likeState = 0;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt);
		}
		
		return likeState;
	}
	
	private boolean checkFirst(int pidx, int uidx) {
		boolean f = false;
		
		try {
			String sql = "select count(*) as c from likes where post_idx = ? and user_idx = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pidx);
			pstmt.setInt(2, uidx);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			if(rs.getInt("c") > 0) {
				f = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return f;
	}
	
	private String whitelist(String content) {
		return Jsoup.clean(content, Safelist.none());
	}
	
	private String brokWhitelist(String intro) {
		Safelist safelist = Safelist.basic();
		safelist.addTags("br");
		
		return Jsoup.clean(intro, safelist);
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
	
}
