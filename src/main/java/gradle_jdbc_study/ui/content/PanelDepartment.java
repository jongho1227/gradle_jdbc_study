package gradle_jdbc_study.ui.content;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import gradle_jdbc_study.dto.Department;
import java.awt.BorderLayout;

@SuppressWarnings("serial")
public class PanelDepartment extends JPanel{
	private JTextField tfDeptNo;
	private JTextField tfDeptName;
	private JTextField tfFloor;
	private List<Department> list;

	public PanelDepartment() {
		initComponents();
	}
	
	
	private void initComponents() {
		setBorder(new TitledBorder(null, "부서정보", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblDeptNo = new JLabel("번호");
		panel.add(lblDeptNo);
		lblDeptNo.setHorizontalAlignment(SwingConstants.RIGHT);
		
		tfDeptNo = new JTextField();
		panel.add(tfDeptNo);
		tfDeptNo.setColumns(10);
		tfDeptNo.setEditable(false);
		
		JLabel lblDeptName = new JLabel("부서명");
		panel.add(lblDeptName);
		lblDeptName.setHorizontalAlignment(SwingConstants.RIGHT);
		
		tfDeptName = new JTextField();
		panel.add(tfDeptName);
		tfDeptName.setColumns(10);
		
		JLabel lblFloor = new JLabel("위치");
		panel.add(lblFloor);
		lblFloor.setHorizontalAlignment(SwingConstants.RIGHT);
		
		tfFloor = new JTextField();
		panel.add(tfFloor);
		tfFloor.setColumns(10);
	}
	
	public void setDepartment(Department dept) {
		tfDeptNo.setText(String.format("D%03d", dept.getDeptNo()));
		tfDeptName.setText(dept.getDeptName());
		tfFloor.setText(String.valueOf(dept.getFloor()));
	}
	
	public Department getDepartment() {
		String a = tfDeptNo.getText().trim();
		String b = a.substring(1);
		int deptNo = Integer.parseInt(b);
		String deptName = tfDeptName.getText().trim();
		int floor = Integer.parseInt(tfFloor.getText().trim());
		return new Department(deptNo, deptName, floor);
	}
	
	public void clearTextField() {
		tfDeptName.setText("");
		tfFloor.setText("");
		
	}
	
	public JTextField getTfDeptNo() {
		return tfDeptNo;
	}
	
	public void setTf() {
		int a = list.size()-1;
		Department d = list.get(a);
		tfDeptNo.setText(String.format("D%03d", d.getDeptNo()+1));
	}
	
	public void setList(List<Department> list) {
		this.list = list;
	}
	
}







