package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import controller.ISolveSysControllerHelper;
import controller.SolveSysControllerHelper;
import model.Utility;

public class StartUpPanel extends JFrame {
    
    private static final long serialVersionUID = 1L;
    private JLabel main_label;
    private JTextField input_field;
    private JButton setNodesNumber;
    private ISolveSysControllerHelper solve;
    
    public void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(600, 200, 450, 300);
        setTitle("Signal Flow Graph");
        setLayout(null);
        setResizable(false);
        
        main_label = new JLabel("Total Number of Nodes: ");
        main_label.setBounds(70, 20, 500, 40);
        input_field = new JTextField();
        input_field.setBounds(200, 80, 50, 50);
        setNodesNumber = new JButton("Set Nodes");
        setNodesNumber.setBounds(130, 150, 200, 50);
        
        setNodesNumber.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
                
                if (Utility.isValidInt(input_field.getText()) && (Integer.parseInt(input_field.getText()) > 0)) {
                    int n = Integer.parseInt(input_field.getText());
                    solve = new SolveSysControllerHelper(n);
                    solve.setNodes(n);
                    SignalFlowPanel view = new SignalFlowPanel(solve);
                    view.setVisible(true);
                    dispose();
                } else {
                    ErrorPanel errView = new ErrorPanel("INVALID ENTRY!");
                    errView.setVisible(true);
                }
            }
        });
        
        Font font = new Font("Serif", Font.PLAIN, 30);
        main_label.setFont(font);
        input_field.setFont(font);
        setNodesNumber.setFont(font);
        
        getContentPane().add(main_label);
        getContentPane().add(input_field);
        getContentPane().add(setNodesNumber);
        
    }
    
}
