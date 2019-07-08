package gradle_jdbc_study.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Employee {
	private int empNo;
	private String empName;
	private Title title;
	private Employee manager;
	private int salary;
	private int gender;
	private Department dno;
	private Date hireDate;
	
	
	public Employee() {
		
	}
	
	public Employee(int empNo) {
		this.empNo = empNo;
	}
	
	
	public Employee(int empNo, String empName, Title title, Employee manager, int salary, int gender, Department dno,
			Date hireDate) {
		this.empNo = empNo;
		this.empName = empName;
		this.title = title;
		this.manager = manager;
		this.salary = salary;
		this.gender = gender;
		this.dno = dno;
		this.hireDate = hireDate;
	}

	public int getEmpNo() {
		return empNo;
	}
	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public Title getTitle() {
		return title;
	}
	public void setTitle(Title title) {
		this.title = title;
	}
	public Employee getManager() {
		return manager;
	}
	public void setManager(Employee manager) {
		this.manager = manager;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public Department getDno() {
		return dno;
	}
	public void setDno(Department dno) {
		this.dno = dno;
	}
	public Date getHireDate() {
		return hireDate;
	}
	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + empNo;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (empNo != other.empNo)
			return false;
		return true;
	}
	
	public Object[] toArray() {
		SimpleDateFormat sp = new SimpleDateFormat("yy");
		String a = sp.format(hireDate); // 년도만 뽑음.
		
		return new Object[]{String.format("E0%s%03d", a,empNo), empName, title, manager, salary, gender==0?"여":"남", dno, hireDate };
	}

	@Override
	public String toString() {
		SimpleDateFormat sp = new SimpleDateFormat("yy");
		String a = sp.format(hireDate); // 년도만 뽑음.
		return String.format("%s(%s)", empName,String.format("E0%s%03d", a,empNo));
	}
	
}











