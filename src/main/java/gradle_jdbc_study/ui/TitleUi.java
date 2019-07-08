package gradle_jdbc_study.ui;

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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import gradle_jdbc_study.dao.TitleDao;
import gradle_jdbc_study.daoImpl.TitleDaoImpl;
import gradle_jdbc_study.dto.Title;
import gradle_jdbc_study.ui.content.PanelTitle;
import java.awt.GridLayout;

@SuppressWarnings("serial")
public class TitleUi extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JPanel panelTable;
	private PanelTitle panel;
	private JTable table;
	private List<Title> titleList;
	private TitleDao dao;
	private JPopupMenu popupMenu;
	private JMenuItem mntmPopUpdate;
	private JMenuItem mntmPopDelete;
	private JButton btnAdd;
	private JButton btnCancel;
	private ErpManagementUi parent;

	public TitleUi() {
		dao = new TitleDaoImpl();
		titleList = dao.selectTitleByAll();
		if(titleList==null) {
			titleList = new ArrayList<Title>();
		}
		initComponents();
		reloadData();
	}
	private void initComponents() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 430);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new PanelTitle();
		panel.setBounds(0, 0, 430, 118);
		panel.setTitleList(titleList);
		panel.setTf();
		contentPane.add(panel);
		
		btnAdd = new JButton("추가");
		btnAdd.addActionListener(this);
		btnAdd.setBounds(112, 128, 97, 23);
		contentPane.add(btnAdd);
		
		btnCancel = new JButton("취소");
		btnCancel.addActionListener(this);
		btnCancel.setBounds(226, 128, 97, 23);
		contentPane.add(btnCancel);
		
		panelTable = new JPanel();
		panelTable.setBounds(0, 169, 430, 216);
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
	
	public void reloadData() {
		table.setModel(new DefaultTableModel(getRows(), getColumnNames()));

		tableCellAlignment(SwingConstants.CENTER, 0, 1);
		
		tableSetWidth(100, 200);
	}

	private Object[][] getRows() {
		Object[][] rows = new Object[titleList.size()][];
		for (int i = 0; i < titleList.size(); i++) {
			rows[i] = titleList.get(i).toArray();
		}
		return rows;
	}

	private String[] getColumnNames() {
		return new String[] { "번호", "직책"};
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
		Title title = panel.getTitle();
		dao.updateTitle(title);
		JOptionPane.showMessageDialog(null, "수정되었습니다.");
		btnAdd.setText("추가");
		titleList = dao.selectTitleByAll();
		panel.clearText();
		panel.setTf();
		reloadData();
		
	}
	private void deleteUI() throws SQLException {
		int i = table.getSelectedRow();
		String titleNo1 = (String) table.getModel().getValueAt(i, 0);
		String titleNo2 = titleNo1.substring(1);
		int titleNo =  Integer.parseInt(titleNo2);
		Title title = new Title(titleNo);
		Title title2 = dao.selectTitleByNo(title);
		dao.deleteTitle(title2);
		JOptionPane.showMessageDialog(null, "삭제완료");
		titleList = dao.selectTitleByAll();
		panel.setTitleList(titleList);
		panel.setTf();
		panel.clearText();
		btnAdd.setText("추가");
		reloadData();
		
		
	}
	private void updateUI() throws SQLException {
		int i = table.getSelectedRow();
		String titleNo1 = (String) table.getModel().getValueAt(i, 0);
		String titleNo2 = titleNo1.substring(1);
		int titleNo = Integer.parseInt(titleNo2);
		Title title = new Title(titleNo);
		Title title2 = dao.selectTitleByNo(title);
		panel.setTilte(title2);
		btnAdd.setText("수정");
		
	}
	protected void actionPerformedBtnAdd(ActionEvent e) throws SQLException {
		Title title = panel.getTitle();
		dao.insertTitle(title);
		JOptionPane.showMessageDialog(null, "추가되었습니다.");
		titleList = dao.selectTitleByAll();
		panel.clearText();
		panel.setTitleList(titleList);
		panel.setTf();
		reloadData();
	
	}
	protected void actionPerformedBtnCancel(ActionEvent e) {
		panel.clearText();
		btnAdd.setText("추가");
		
	}
	public void setBtnAdd() {
		panel.clearText();
		btnAdd.setText("추가");
	}
	
	public void setParent(ErpManagementUi pr) {
		this.parent = pr;
	}
}

















