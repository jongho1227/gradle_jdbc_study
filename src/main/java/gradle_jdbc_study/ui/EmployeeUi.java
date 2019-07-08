package gradle_jdbc_study.ui;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import gradle_jdbc_study.dao.DepartmentDao;
import gradle_jdbc_study.dao.EmployeeDao;
import gradle_jdbc_study.dao.TitleDao;
import gradle_jdbc_study.daoImpl.DepartmentDaoImpl;
import gradle_jdbc_study.daoImpl.EmployeeDaoImpl;
import gradle_jdbc_study.daoImpl.TitleDaoImpl;
import gradle_jdbc_study.dto.Department;
import gradle_jdbc_study.dto.Employee;
import gradle_jdbc_study.dto.Title;

import javax.swing.JButton;
import gradle_jdbc_study.ui.content.PanelEmployee;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class EmployeeUi extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JPanel panelTable;
	private JButton btnAdd;
	private JButton btnCancel;
	private JTable table;
	private EmployeeDao dao;
	private TitleDao tDao;
	private DepartmentDao dDao;
	private List<Employee> emplist;
	private List<Title> titlelist;
	private List<Department> deptlist;
	private PanelEmployee panel;
	private JPopupMenu popupMenu;
	private JMenuItem mntmPopUpdate;
	private JMenuItem mntmPopDelete;
	private ErpManagementUi parent;
	
	public EmployeeUi() throws SQLException {
		
		dao = new EmployeeDaoImpl();
		tDao = new TitleDaoImpl();
		dDao = new DepartmentDaoImpl();
		
		makeList();
		
		initComponents();
		panel.setEmpList(emplist);
		panel.clear();
		reloadData();
	}
	public void makeList() throws SQLException {
		
		emplist = dao.selectEmployeeByAll();
		if(emplist==null) {
			emplist = new ArrayList<Employee>();
		}
		titlelist = tDao.selectTitleByAll();
		if(titlelist==null) {
			titlelist = new ArrayList<Title>();
		}
		deptlist = dDao.selectDepartmentByAll();
		if(deptlist==null) {
			deptlist = new ArrayList<Department>();
		}
		if(panel != null) {
		setCbModel();
		}
	}
	private void initComponents() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 666, 798);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new PanelEmployee();
		panel.setBounds(0, 0, 650, 394);
		setCbModel();
		contentPane.add(panel);
		
		btnAdd = new JButton("추가");
		btnAdd.addActionListener(this);
		btnAdd.setBounds(219, 404, 97, 23);
		contentPane.add(btnAdd);
		
		btnCancel = new JButton("취소");
		btnCancel.addActionListener(this);
		btnCancel.setBounds(328, 404, 97, 23);
		contentPane.add(btnCancel);
		
		panelTable = new JPanel();
		panelTable.setBounds(0, 443, 650, 316);
		contentPane.add(panelTable);
		panelTable.setLayout(new GridLayout(0, 1, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panelTable.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);		
		
		popupMenu = new JPopupMenu();

		mntmPopUpdate = new JMenuItem("수정");
		mntmPopUpdate.addActionListener(this);
		popupMenu.add(mntmPopUpdate);

		mntmPopDelete = new JMenuItem("삭제");
		mntmPopDelete.addActionListener(this);
		popupMenu.add(mntmPopDelete);

		table.setComponentPopupMenu(popupMenu);
		scrollPane.setComponentPopupMenu(popupMenu);
		
	}
	private void setCbModel() {
		panel.setCbTitleModel(titlelist);
		panel.setCbManagerModel(emplist);	
		panel.setCbDnoModel(deptlist);
	
	}
	
	public void reloadData() {
		table.setModel(new DefaultTableModel(getRows(), getColumnNames()));

		tableCellAlignment(SwingConstants.CENTER, 0, 1, 2, 3, 5, 6, 7);
		tableCellAlignment(SwingConstants.RIGHT, 4);
		
		tableSetWidth(100,80,50,100,120,60,100,150);
	}

	private Object[][] getRows() {
		Object[][] rows = new Object[emplist.size()][];
		for (int i = 0; i < emplist.size(); i++) {
			rows[i] = emplist.get(i).toArray();
		}
		return rows;
	}

	private String[] getColumnNames() {
		return new String[] { "번호", "사원명", "직책", "직속 상사", "급여","성별","부서","입사일" };
	}

	// 테이블 셀 내용의 정렬
	protected void tableCellAlignment(int align, int... idx) {
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(align);

		TableColumnModel model = table.getColumnModel();
		for (int i = 0; i < idx.length; i++) {
			model.getColumn(idx[i]).setCellRenderer(dtcr);
		}
	}

	// 테이블 셀의 폭 설정
	protected void tableSetWidth(int... width) {
		TableColumnModel cModel = table.getColumnModel();

		for (int i = 0; i < width.length; i++) {
			cModel.getColumn(i).setPreferredWidth(width[i]);
		}
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancel) {
			actionPerformedBtnCancel(e);
		}
		if (e.getSource() == btnAdd) {
			if(btnAdd.getText()=="추가") {
			try {
				try {
					actionPerformedBtnAdd(e);
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			}else if(btnAdd.getText()=="수정") {
				try {
					actionPerformedBtnUpdate(e);
				} catch (ParseException e1) {
					
					e1.printStackTrace();
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			}
		}
		
		if (e.getSource() == mntmPopUpdate) {
			try {
				updateUI();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		if (e.getSource() == mntmPopDelete) {
			try {
				deleteUI();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		}
	
	}
	private void actionPerformedBtnUpdate(ActionEvent e) throws ParseException, SQLException {
		Employee emp = panel.getEmp();
		dao.updateEmployee(emp);
		JOptionPane.showMessageDialog(null, "수정완료");
		btnAdd.setText("추가");
		makeList();
		setCbModel();
		reloadData();
		
		panel.clear();
		
	}
	private void deleteUI() throws SQLException {
		int i = table.getSelectedRow();
		String empNo1 = (String) table.getModel().getValueAt(i, 0);
		String empNo2 = empNo1.substring(4);
		int empNo = Integer.parseInt(empNo2);
		System.out.println(empNo);
		dao.deleteEmployee(new Employee(empNo));
		JOptionPane.showMessageDialog(null, "삭제완료");
		makeList();
		setCbModel();
		reloadData();
		panel.setEmpList(emplist);
		btnAdd.setText("추가");
		panel.clear();
		
	}
	private void updateUI() throws SQLException {
		int i = table.getSelectedRow();
		
		String empNo1 = (String) table.getModel().getValueAt(i, 0);
		String empNo2 = empNo1.substring(4);
		int empNo = Integer.parseInt(empNo2);
		Employee emp = new Employee(empNo);
		Employee emp1 = dao.selectEmployeeByNo(emp);
		panel.setEmployee(emp1);
		btnAdd.setText("수정");
		
	}
	protected void actionPerformedBtnAdd(ActionEvent e) throws ParseException, SQLException {
		Employee emp = panel.getEmp();
		
		if(emp.getEmpName().equals("")) {
			JOptionPane.showMessageDialog(null, "이름을 입력하시오");
			return;
		}
		dao.insertEmployee(emp);
		JOptionPane.showMessageDialog(null, "사원 추가");
		makeList();
		setCbModel();
		reloadData();
		panel.setEmpList(emplist);
		panel.clear();
		
		
	}
	protected void actionPerformedBtnCancel(ActionEvent e) {
		panel.clear();
		btnAdd.setText("추가");
	}
	
	public void setBtn() {
		btnAdd.setText("추가");
		panel.setEmpList(emplist);
		panel.clear();
	}
	
	public void setParent(ErpManagementUi pr) {
		this.parent = pr;
	}
}



































