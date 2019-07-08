package gradle_jdbc_study;

import java.awt.EventQueue;

import gradle_jdbc_study.ui.ErpManagementUi;



public class ErpMain {

	public static void main(String[] args) {
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ErpManagementUi frame = new ErpManagementUi();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
}
