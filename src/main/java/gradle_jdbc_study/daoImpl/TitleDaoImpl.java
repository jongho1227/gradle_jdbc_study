package gradle_jdbc_study.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gradle_jdbc_study.dao.TitleDao;
import gradle_jdbc_study.dto.Title;
import gradle_jdbc_study.jdbc.ConnectionProvider;

public class TitleDaoImpl implements TitleDao {
	static final Logger log = LogManager.getLogger();
	
	@Override
	public List<Title> selectTitleByAll() {
		List<Title> lists = new ArrayList<Title>();
		String sql = "select tno, tname from title";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			log.trace(pstmt);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				lists.add(getTitle(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return lists;
	}
	
	private Title getTitle(ResultSet rs) throws SQLException{
		return new Title(rs.getInt("tno"), rs.getString("tname"));
	}

	@Override
	public Title selectTitleByNo(Title title) throws SQLException {
		String sql = "select tno, tname from title where tno=?";
		Title selTitle = null;
		
		try (Connection conn =ConnectionProvider.getConnection();
				 PreparedStatement pstmt = conn.prepareStatement(sql);){
				
				pstmt.setInt(1, title.getTno());
				log.trace(pstmt);
				try(ResultSet rs = pstmt.executeQuery();){
					if(rs.next()) {
						selTitle = getTitle(rs);
					}
				}
			} 		
			return selTitle;
	}

	@Override
	public int insertTitle(Title title) throws SQLException {
		String sql = "insert into title(tno, tname) values(?, ?)";
		int res = -1;
		
		try(Connection conn =  ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);){
			pstmt.setInt(1, title.getTno());
			pstmt.setString(2, title.getTname());
			log.trace(pstmt);
			res = pstmt.executeUpdate();
		}
		return res;
	}

	@Override
	public int deleteTitle(Title title) throws SQLException {
		String sql = "delete from title where tno=?";
		int res = -1;
		
		try(Connection conn = ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);){
			pstmt.setInt(1, title.getTno());
			log.trace(pstmt);
			res = pstmt.executeUpdate();
		}
		return res;
	}

	@Override
	public int updateTitle(Title title) throws SQLException {
		String sql = "update title set tname=? where tno=?;";
		int res = -1;
		
		try(Connection conn = ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);){
			pstmt.setString(1, title.getTname());
			pstmt.setInt(2, title.getTno());
		
			log.trace(pstmt);
			res = pstmt.executeUpdate();
		}
		return res;
	}

}
