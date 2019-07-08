package gradle_jdbc_study.ui.content;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import gradle_jdbc_study.dto.Title;

@SuppressWarnings("serial")
public class PanelTitle extends JPanel {
	private JTextField tfNumber;
	private JTextField tfTitle;
	private List<Title> list;
	
	public PanelTitle() {

		initComponents();
	}
	private void initComponents() {
		setLayout(new GridLayout(0, 2, 5, 5));
		
		JLabel lblNumber = new JLabel("번호");
		lblNumber.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblNumber);
		
		tfNumber = new JTextField();
		add(tfNumber);
		tfNumber.setColumns(10);
		tfNumber.setEditable(false);
		
		JLabel lblTitle = new JLabel("직책명");
		lblTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblTitle);
		
		tfTitle = new JTextField();
		add(tfTitle);
		tfTitle.setColumns(10);
	}

	public void clearText() {
		int a = list.size()-1;
		Title t = list.get(a);
		tfNumber.setText(String.format("D%03d", t.getTno()+1));
		tfTitle.setText("");
	}
	public Title getTitle() {
		String a = tfNumber.getText().trim();
		String b = a.substring(1);
		int tno = Integer.parseInt(b);
		String tname = tfTitle.getText().trim();
		return new Title(tno, tname);
	}
	public JTextField getTfNumber() {
		return tfNumber;
	}
	public void setTf() {
		int a = list.size()-1;
		Title t = list.get(a);
		tfNumber.setText(String.format("D%03d", t.getTno()+1));
	}
	public void setTilte(Title title) {
		tfNumber.setText(String.format("D%03d", title.getTno()));
		tfTitle.setText(title.getTname());
	}
	public void setTitleList(List<Title> list) {
		this.list = list;
	}
}









