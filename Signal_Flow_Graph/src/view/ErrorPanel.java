package view;


import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class ErrorPanel extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel label;

	ErrorPanel(String error) {
		initializeErrorFrame(error);
	}

	private void initializeErrorFrame(String str) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(600, 300, 450, 150);
		setTitle("Error Message");
		setLayout(null);
		setResizable(false);

		label = new JLabel(str);
		label.setBounds(20, 5, 300, 100);
        Font font = new Font("Serif", Font.PLAIN, 20);
        label.setFont(font);
		getContentPane().add(label);

	}
}