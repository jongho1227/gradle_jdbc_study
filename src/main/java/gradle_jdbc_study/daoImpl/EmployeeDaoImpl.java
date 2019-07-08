package gradle_jdbc_study.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gradle_jdbc_study.dao.DepartmentDao;
import gradle_jdbc_study.dao.EmployeeDao;
import gradle_jdbc_study.dao.TitleDao;
import gradle_jdbc_study.dto.Department;
import gradle_jdbc_study.dto.Employee;
import gradle_jdbc_study.dto.Title;
import gradle_jdbc_study.jdbc.ConnectionProvider;

public class EmployeeDaoImpl implements EmployeeDao {
	static final Logger log = LogManager.getLogger();
	private DepartmentDao dao;
	private TitleDao tDao;
	
	@Override
	public List<Employee> selectEmployeeByAll() throws SQLException {
		List<Employee> lists = new ArrayList<Employee>();
		String sql = "SELECT empno, empname, title, manager, salary, gender, dno, hire_date FROM ncs_erp.employee";
		
		try(Connection conn = ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) { // executeQuery : ResultSet타입을 리턴함. select할때 쓰임
				log.trace(pstmt);
				
				while(rs.next()) {
					lists.add(getEmployee(rs));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} 
				
			return lists;
	}

	private Employee getEmployee(ResultSet rs) throws SQLException {
		dao = new DepartmentDaoImpl();
		tDao = new TitleDaoImpl();
		Employee emp = new Employee(rs.getInt("manager"));
		Employee emp1 = selectEmployeeByNo(emp);
		Department dept = new Department(rs.getInt("dno"));
		Department dept1 = dao.selectDepartmentByNo(dept);
		Title title = new Title( rs.getInt("title"));
		Title title2 = tDao.selectTitleByNo(title);
		
		return new Employee(rs.getInt("empno"), rs.getString("empname"),title2,emp1,
							rs.getInt("salary"), rs.getInt("gender"), dept1 , rs.getDate("hire_date"));
	}

	@Override
	public Employee selectEmployeeByNo(Employee employee) throws SQLException {
		String sql = "select * from employee where empno=?";
		Employee selEmp = null;
		
		try (Connection conn =ConnectionProvider.getConnection();
				 PreparedStatement pstmt = conn.prepareStatement(sql);){
				
				pstmt.setInt(1, employee.getEmpNo());
				log.trace(pstmt);
				try(ResultSet rs = pstmt.executeQuery();){
					if(rs.next()) {
						selEmp = getEmployee(rs);
					}
				}
			} 		
			return selEmp;
	}

	@Override
	public int insertEmployee(Employee employee) throws SQLException {
		String sql =null;
		int res = -1;
		if(employee.getManager()==null) {
			sql = "insert into employee values(?,?,?,null,?,?,?,?)";
			try(Connection conn =  ConnectionProvider.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(sql);){
				pstmt.setInt(1, employee.getEmpNo());
				pstmt.setString(2, employee.getEmpName());
				pstmt.setInt(3, employee.getTitle().getTno());
				pstmt.setInt(4, employee.getSalary());
				pstmt.setInt(5, employee.getGender());
				pstmt.setInt(6, employee.getDno().getDeptNo());
				Date d = employee.getHireDate();
				SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
				pstmt.setString(7, sp.format(d));
				log.trace(pstmt);
				res = pstmt.executeUpdate(); // executeUpdate : int타입을 반환함(쿼리 적용 갯수를 리턴). 삽입,업데이트나 삭제할 때 쓰임. 
			}
		}else {
			sql = "insert into employee values(?,?,?,?,?,?,?,?)";
			try(Connection conn =  ConnectionProvider.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(sql);){
				pstmt.setInt(1, employee.getEmpNo());
				pstmt.setString(2, employee.getEmpName());
				pstmt.setInt(3, employee.getTitle().getTno());
				pstmt.setInt(4, employee.getManager().getEmpNo());
				pstmt.setInt(5, employee.getSalary());
				pstmt.setInt(6, employee.getGender());
				pstmt.setInt(7, employee.getDno().getDeptNo());
				Date d = employee.getHireDate();
				SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
				pstmt.setString(8, sp.format(d));
				log.trace(pstmt);
				res = pstmt.executeUpdate();
			}
		}
		
		
		
		
		
		return res;
	}

	@Override
	public int deleteEmployee(Employee employee) throws SQLException {
		String sql = "delete from employee where empno = ?";
		try(Connection conn = ConnectionProvider.getConnection(); // 디비에 연결
				PreparedStatement pstmt = conn.prepareStatement(sql)){ // 연결한 디비에 쿼리를 던질 준비를 함.
			pstmt.setInt(1, employee.getEmpNo()); // 첫번째 물음표에  받아온 employee객체의 사원번호를 뽑아서 적용준비.
			log.trace(pstmt);
			return pstmt.executeUpdate(); // 위의 쿼리를 적용 후 결과 리턴.
		}
	}

	@Override
	public int updateEmployee(Employee employee) throws SQLException {
		log.trace("updateEmployee()");
		String sql=null;
		int res = -1;
		if (employee.getManager()==null) {
				sql = "update employee "
				       + "set empname=?, title=?, manager=null, salary=?, gender=?, dno=?, hire_date=? "
					   + "where empno=?";
				try(Connection conn = ConnectionProvider.getConnection();// 디비 연결
						PreparedStatement pstmt = conn.prepareStatement(sql)){// 연결한 디비에 쿼리를 던질 준비를 함.
					pstmt.setString(1, employee.getEmpName());
					pstmt.setInt(2, employee.getTitle().getTno());
					pstmt.setInt(3, employee.getSalary());
					pstmt.setInt(4,  employee.getGender());
					pstmt.setInt(5, employee.getDno().getDeptNo());
					Date d = employee.getHireDate();
					SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
					pstmt.setString(6, sp.format(d));
					pstmt.setInt(7, employee.getEmpNo());
					log.trace(pstmt);
					res = pstmt.executeUpdate();// 위의 쿼리를 적용 후 결과 리턴.
				}
		}else {
			 sql = "update employee "
				       + "set empname=?, title=?, manager=?, salary=?, gender=?, dno=?, hire_date=? "
					   + "where empno=?";
			 try(Connection conn = ConnectionProvider.getConnection();// 디비 연결
						PreparedStatement pstmt = conn.prepareStatement(sql)){// 연결한 디비에 쿼리를 던질 준비를 함.
					pstmt.setString(1, employee.getEmpName());
					pstmt.setInt(2, employee.getTitle().getTno());
					pstmt.setInt(3, employee.getManager().getEmpNo());
					pstmt.setInt(4, employee.getSalary());
					pstmt.setInt(5,  employee.getGender());
					pstmt.setInt(6, employee.getDno().getDeptNo());
					Date d = employee.getHireDate();
					SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
					pstmt.setString(7, sp.format(d));
					pstmt.setInt(8, employee.getEmpNo());
					log.trace(pstmt);
					res = pstmt.executeUpdate();// 위의 쿼리를 적용 후 결과 리턴.
				}
		}
		
		
		return res;
	}

}


















