package gradle_jdbc_study.ui;

import java.awt.BorderLayout;
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

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gradle_jdbc_study.dao.DepartmentDao;
import gradle_jdbc_study.daoImpl.DepartmentDaoImpl;
import gradle_jdbc_study.dto.Department;
import gradle_jdbc_study.ui.content.PanelDepartment;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class DepartmentUi extends JFrame implements ActionListener{

	private JPanel contentPane;
	private PanelDepartment panel;
	private JPanel panelTable;
	private JTable table;
	private List<Department> deptList;
	private DepartmentDao dao;
	private JPopupMenu popupMenu;
	private JMenuItem mntmPopUpdate;
	private JMenuItem mntmPopDelete;
	private JPanel panel_1;
	private JButton btnAdd;
	private JButton btnCancel;
	private ErpManagementUi parent;
	
	public DepartmentUi() {
		dao = new DepartmentDaoImpl();
		deptList = dao.selectDepartmentByAll();
		if(deptList==null) {
			deptList = new ArrayList<Department>();
		}
		initComponents();
		reloadData();
		
	}
	
	public void setDeptDao(DepartmentDao deptdao) {
		this.dao = deptdao;
	}
	
	private void initComponents() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 405, 456);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 10));
		
		panel = new PanelDepartment();
		contentPane.add(panel);
		
		panel_1 = new JPanel();
		contentPane.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));
		
		btnAdd = new JButton("추가");
		btnAdd.addActionListener(this);
		panel_1.add(btnAdd);
		
		btnCancel = new JButton("취소");
		btnCancel.addActionListener(this);
		panel_1.add(btnCancel);
		
		panelTable = new JPanel();
		contentPane.add(panelTable);
		panelTable.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panelTable.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		panel.setList(deptList);
		panel.setTf();
		
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
	
	public void reloadData() {
		table.setModel(new DefaultTableModel(getRows(), getColumnNames()));

		tableCellAlignment(SwingConstants.CENTER, 0, 1, 2);
		
		tableSetWidth(100, 200, 70);
	}

	private Object[][] getRows() {
		Object[][] rows = new Object[deptList.size()][];
		for (int i = 0; i < deptList.size(); i++) {
			rows[i] = deptList.get(i).toArray();
		}
		return rows;
	}

	private String[] getColumnNames() {
		return new String[] { "부서번호", "부서명", "위치(층)" };
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancel) {
			actionPerformedBtnCancel(e);
		}
		if (e.getSource() == btnAdd) {
			if(btnAdd.getText()=="추가") {
				try {
					actionPerformedBtnAdd(e);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}else if(btnAdd.getText()=="수정") {
					try {
						actionPerformedBtnUpdate(e);
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
		
		parent.listReload();
		
	}
	
	
	
	private void actionPerformedBtnUpdate(ActionEvent e) throws SQLException {
		Department dept = panel.getDepartment();
		dao.updateDepartment(dept);
		JOptionPane.showMessageDialog(null, "수정완료");
		setList(dao.selectDepartmentByAll());
		panel.setList(deptList);
		panel.clearTextField();
		panel.setTf();
		btnAdd.setText("추가");
		
		reloadData();
	}

	protected void actionPerformedBtnAdd(ActionEvent e) throws SQLException {
		Department dept = panel.getDepartment();
		dao.insertDepartment(dept);
		JOptionPane.showMessageDialog(null, "부서추가");
		setList(dao.selectDepartmentByAll());
		panel.setList(deptList);
		panel.setTf();
		panel.clearTextField();
		reloadData();
	
	}
	
	
	protected void actionPerformedBtnCancel(ActionEvent e) {
		panel.setList(deptList);
		panel.clearTextField();
		panel.setTf();
		btnAdd.setText("추가");
	}
	
	private void deleteUI() throws SQLException {
		int i = table.getSelectedRow();
		String a = (String) table.getModel().getValueAt(i, 0);
		String b = a.substring(1);
		int deptNo = Integer.parseInt(b);
		dao.deleteDepartment(new Department(deptNo));
		JOptionPane.showMessageDialog(null, "삭제완료");
		deptList = dao.selectDepartmentByAll();
		panel.setList(deptList);
		panel.clearTextField();
		panel.setTf();
		btnAdd.setText("추가");
		reloadData();
		
		
	}

	private void updateUI() throws SQLException {
		int i = table.getSelectedRow();
		String a = (String) table.getModel().getValueAt(i, 0);
		String b = a.substring(1);
		int deptNo = Integer.parseInt(b);
		Department dp = new Department(deptNo);
		Department dept = dao.selectDepartmentByNo(dp);
		panel.setDepartment(dept);
		btnAdd.setText("수정");
	}
	
	public void setList(List<Department> list) {
		this.deptList = list;
	}
	public void setBtnAdd() {
		panel.setTf();
		panel.clearTextField();
		btnAdd.setText("추가");
	}
	
	public void setParent(ErpManagementUi pr) {
		this.parent = pr;
	}
}
























