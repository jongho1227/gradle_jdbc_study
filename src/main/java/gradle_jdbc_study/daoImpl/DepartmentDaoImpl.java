package gradle_jdbc_study.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gradle_jdbc_study.dao.DepartmentDao;
import gradle_jdbc_study.dto.Department;
import gradle_jdbc_study.jdbc.ConnectionProvider;

public class DepartmentDaoImpl implements DepartmentDao {
	static final Logger log = LogManager.getLogger();
	
	
	@Override
	public List<Department> selectDepartmentByAll() {
		List<Department> lists = new ArrayList<Department>();
		String sql = "select deptno, deptname, floor from department";
		
	try(Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery()) {
			log.trace(pstmt);
			
			while(rs.next()) {
				lists.add(getDepartment(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
			
		return lists;
		
	}
	
	private Department getDepartment(ResultSet rs) throws SQLException {
		return new Department(rs.getInt("deptno"), 
				              rs.getString("deptname"), 
				              rs.getInt("floor"));
	}

	@Override
	public Department selectDepartmentByNo(Department dept) throws SQLException {
		String sql = "select deptno, deptname, floor from department where deptno = ?";
		
		Department selDept = null;
		
		try (Connection conn =ConnectionProvider.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(sql);){
			
			pstmt.setInt(1, dept.getDeptNo());
			log.trace(pstmt);
			try(ResultSet rs = pstmt.executeQuery();){
				if(rs.next()) {
					selDept = getDepartment(rs);
				}
			}
		} 		
		return selDept;
	}

	@Override
	public int insertDepartment(Department dept) throws SQLException {
		String sql = "insert into department(deptno, deptname, floor) values(?, ?, ?)";
		int res = -1;
		
		try(Connection conn =  ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);){
			pstmt.setInt(1, dept.getDeptNo());
			pstmt.setString(2, dept.getDeptName());
			pstmt.setInt(3, dept.getFloor());
			log.trace(pstmt);
			res = pstmt.executeUpdate();
		}
		return res;
	}

	@Override
	public int deleteDepartment(Department dept) throws SQLException {
		String sql = "delete from department where deptno=?";
		int res = -1;
		
		try(Connection conn = ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);){
			pstmt.setInt(1, dept.getDeptNo());
			log.trace(pstmt);
			res = pstmt.executeUpdate();
		}
		return res;
	}

	@Override
	public int updateDepartment(Department dept) throws SQLException {
		String sql = "update department set deptname=?, floor=? where deptno=?;";
		int res = -1;
		
		try(Connection conn = ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);){
			pstmt.setString(1, dept.getDeptName());
			pstmt.setInt(2, dept.getFloor());
			pstmt.setInt(3, dept.getDeptNo());
			log.trace(pstmt);
			res = pstmt.executeUpdate();
		}
		return res;
	}

}
