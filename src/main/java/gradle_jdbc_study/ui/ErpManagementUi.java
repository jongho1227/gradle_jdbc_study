package gradle_jdbc_study.ui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class ErpManagementUi extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JButton btnEmp;
	private JButton btnDept;
	private JButton btnTitle;
	private DepartmentUi deptFrame;
	private TitleUi titleFrame;
	private EmployeeUi empFrame;
	
	public ErpManagementUi() {
		
		initComponents();
	}
	private void initComponents() {
		setTitle("ERP관리프로그램");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 529, 142);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 3, 5, 5));
		
		btnEmp = new JButton("사원관리");
		btnEmp.addActionListener(this);
		contentPane.add(btnEmp);
		
		btnDept = new JButton("부서관리");
		btnDept.addActionListener(this);
		contentPane.add(btnDept);
		
		btnTitle = new JButton("직책관리");
		btnTitle.addActionListener(this);
		contentPane.add(btnTitle);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnEmp) {
			try {
				actionPerformedBtnEmp(e);
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
		}
		if (e.getSource() == btnTitle) {
			actionPerformedBtnTitle(e);
		}
		if (e.getSource() == btnDept) {
			actionPerformedBtnDept(e);
		}
	}
	protected void actionPerformedBtnDept(ActionEvent e) {
		if(deptFrame==null) {
			deptFrame = new DepartmentUi();
		}
		deptFrame.setParent(this);
		deptFrame.setBtnAdd();
		deptFrame.setVisible(true);
	}
	
	protected void actionPerformedBtnTitle(ActionEvent e) {
		if(titleFrame==null) {
			titleFrame = new TitleUi();
		}
		titleFrame.setParent(this);
		titleFrame.setBtnAdd();
		titleFrame.setVisible(true);
		
	}
	protected void actionPerformedBtnEmp(ActionEvent e) throws SQLException {
		if(empFrame==null) {
			empFrame = new EmployeeUi();
		}
		empFrame.setParent(this);
		empFrame.setBtn();
		empFrame.setVisible(true);
		
	}
	
	public void listReload() {
		
		if(empFrame!=null) {
			try {
				empFrame.makeList();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}







