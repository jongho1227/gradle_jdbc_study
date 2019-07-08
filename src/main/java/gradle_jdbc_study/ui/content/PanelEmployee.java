package gradle_jdbc_study.ui.content;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import gradle_jdbc_study.dto.Department;
import gradle_jdbc_study.dto.Employee;
import gradle_jdbc_study.dto.Title;

@SuppressWarnings("serial")
public class PanelEmployee extends JPanel {
	private JTextField tfNo;
	private JTextField tfName;
	private JTextField tfHire;
	private JComboBox<Title> cbTitle;
	private DefaultComboBoxModel<Title> empCbTitle;
	private JSpinner spSalary;
	private JPanel panelRadio;
	private JRadioButton radioMan;
	private JRadioButton radioWoman;
	private JComboBox<Department> cbDno;
	private DefaultComboBoxModel<Department> deptCbDno;
	private ButtonGroup group;
	private JLabel lblManager;
	private JComboBox<Employee> cbManager;
	private DefaultComboBoxModel<Employee> empCbManager;
	private List<Employee> list;
	
	public PanelEmployee() {

		initComponents();
	}
	private void initComponents() {
		setLayout(new GridLayout(0, 2, 10, 5));
		
		JLabel lblNo = new JLabel("번호");
		lblNo.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblNo);
		
		tfNo = new JTextField();
		add(tfNo);
		tfNo.setColumns(10);
		tfNo.setEditable(false);
		
		JLabel lblName = new JLabel("사원명");
		lblName.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblName);
		
		tfName = new JTextField();
		add(tfName);
		tfName.setColumns(10);
		
		JLabel lblTitle = new JLabel("직책");
		lblTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblTitle);
		
		cbTitle = new JComboBox<Title>();
		add(cbTitle);
		
		lblManager = new JLabel("직속 상사");
		lblManager.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblManager);
		
		cbManager = new JComboBox<Employee>();
		
		add(cbManager);
		
		JLabel lblSalary = new JLabel("급여");
		lblSalary.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblSalary);
		
		SpinnerModel spModel = new SpinnerNumberModel(1500000, 1000000, 5000000, 100000);
		spSalary = new JSpinner(spModel);
		
		add(spSalary);
		
		JLabel lblGender = new JLabel("성별");
		lblGender.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblGender);
		
		panelRadio = new JPanel();
		add(panelRadio);
		panelRadio.setLayout(new GridLayout(0, 2, 0, 0));
		
		group = new ButtonGroup();
		
		radioMan = new JRadioButton("남");
		radioMan.setHorizontalAlignment(SwingConstants.RIGHT);
		radioMan.setSelected(true);
		panelRadio.add(radioMan);
		
		radioWoman = new JRadioButton("여");
		panelRadio.add(radioWoman);
		
		group.add(radioMan);
		group.add(radioWoman);
		panelRadio.add(radioMan);
		panelRadio.add(radioWoman);
		
		
		JLabel lblDno = new JLabel("부서");
		lblDno.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblDno);
		
		cbDno = new JComboBox<Department>();
		add(cbDno);
		
		JLabel lblHire = new JLabel("입사일");
		lblHire.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblHire);
		
		tfHire = new JTextField();
		add(tfHire);
		tfHire.setColumns(10);
	}
	
	public Employee getEmp() throws ParseException {
		String empNo1 = tfNo.getText();
		String empNo2 = empNo1.substring(4);
			
		int empNo =  Integer.parseInt(empNo2);
		String empName = tfName.getText();
		Title title = (Title)cbTitle.getSelectedItem();
		
		Employee empManager = (Employee)cbManager.getSelectedItem();
		int empSalary = (int)spSalary.getValue();
		int empGender = 0;
		if(radioMan.isSelected()) {
			empGender = 1;
		}
		Department empDno = (Department)cbDno.getSelectedItem();
		
		String empHire1 = tfHire.getText();
		SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
		Date empHire = sp.parse(empHire1);
		
		return new Employee(empNo, empName, title, empManager, empSalary, empGender, empDno, empHire);
	}
	
	public void setEmployee(Employee emp) {
		tfName.setText(emp.getEmpName());
		cbTitle.setSelectedItem(emp.getTitle());
		cbManager.setSelectedItem(emp.getManager());
		spSalary.setValue(emp.getSalary());
		if(emp.getGender()==0) {
			radioWoman.setSelected(true);
		}else {
			radioMan.setSelected(true);
		}
		cbDno.setSelectedItem(emp.getDno());
		Date d = emp.getHireDate();
		SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
		tfHire.setText(sp.format(d));
		
		SimpleDateFormat sp1 = new SimpleDateFormat("yy");
		String a = sp1.format(d); // 년도만 뽑음.
		tfNo.setText(String.format("E0%s%03d", a,emp.getEmpNo()));
	}
	public void clear() {
		Employee e = new Employee();
		int number = 0;
		Date d = new Date();
		SimpleDateFormat sp1 = new SimpleDateFormat("yy");
		String a = sp1.format(d); // 년도만 뽑음.
		if(list.size()>0) {
		int i = list.size()-1;
		e = list.get(i);
		number = e.getEmpNo()+1;
		}else {
		number = 1;	
		}
		tfNo.setText(String.format("E0%s%03d", a,number));
		tfName.setText("");
		cbTitle.setSelectedIndex(-1);
		cbManager.setSelectedIndex(-1);
		spSalary.setValue(1500000);
		radioMan.setSelected(true);
		cbDno.setSelectedIndex(-1);
		SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
		String a1 = sp.format(d);
		tfHire.setText(a1);
		
	}
	
	public void setCbTitleModel(List<Title> titlelist) {
		empCbTitle = new DefaultComboBoxModel<Title>(new Vector<Title>(titlelist));
		cbTitle.setModel(empCbTitle);
		System.out.println(cbTitle);
		
	}
	
	public void setCbDnoModel(List<Department> deptlist) {
		deptCbDno = new DefaultComboBoxModel<Department>(new Vector<Department>(deptlist));
		cbDno.setModel(deptCbDno);
	}
	public void setCbManagerModel(List<Employee> emplist) {
		empCbManager = new DefaultComboBoxModel<Employee>(new Vector<Employee>(emplist));
		cbManager.setModel(empCbManager);
	}
	
	public void setEmpList(List<Employee> emplist) {
		this.list = emplist;
	}
}




























