package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import controller.ISolveSysControllerHelper;

public class ResultPanel extends JFrame {
    
    private static final long serialVersionUID = 1L;
    private JTextPane nontouchLabeltxt;
    private JTextPane loopsLabeltxt;
    private JTextPane ForwardPathLabeltxt;
    private JTextPane ForwardGainLabeltxt;
    private JTextPane nonTouchGainLabeltxt;
    private JTextPane loopsGainLabeltxt;
    private JTextPane deltaGainLabeltxt;
    private JLabel loopsLabel;
    private JLabel ForwardPathLabel;
    private JLabel tfLabel;
    private JLabel nontouchLabel;
    private JLabel loopsGainLabel;
    private JLabel ForwardGainLabel;
    private JLabel nonTouchGainLabel, TFLabel;
    private JLabel deltaGainLabel;
    private JLabel delta_label;
    private JLabel delta_txt;
    private JPanel drawPanel;
    private ISolveSysControllerHelper helper;
    
    ResultPanel(ISolveSysControllerHelper helper) {
        this.helper = helper;
        initialize();
    }
    
    private void initialize() {
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth() - 120;
        int height = (int) screenSize.getHeight() - 100;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 50, width, height);
        setTitle("Results Table");
        setLayout(null);
        setResizable(true);
        
        ForwardPathLabel = new JLabel("FORWARD PATHS");
        ForwardGainLabel = new JLabel("W/ GAIN");
        ForwardGainLabeltxt = new JTextPane();
        ForwardPathLabeltxt = new JTextPane();
        ForwardGainLabeltxt.setBackground(Color.pink);
        
        deltaGainLabel = new JLabel("DELTA GAIN");
        deltaGainLabeltxt = new JTextPane();
        deltaGainLabeltxt.setBackground(Color.yellow);
        
        loopsLabel = new JLabel("LOOPS");
        loopsGainLabel = new JLabel("W/ GAIN");
        loopsGainLabeltxt = new JTextPane();
        loopsLabeltxt = new JTextPane();
        loopsGainLabeltxt.setBackground(Color.pink);
        
        nontouchLabel = new JLabel("NON TOUCHING LOOPS");
        nonTouchGainLabel = new JLabel("W/ GAIN");
        nonTouchGainLabeltxt = new JTextPane();
        nontouchLabeltxt = new JTextPane();
        nonTouchGainLabeltxt.setBackground(Color.pink);
        
        ///////////////////////////////////////////////////////////////////////////////////////////
        
        drawPanel = new GraphicalRepresentation(this.helper, width / 2, 600);
        //	drawPanel.setBounds(height - 120, 40, 690, height - 120);
        drawPanel.setBounds(0, height - 390, width - 450, 400);
        drawPanel.setBackground(Color.WHITE);
        
        tfLabel = new JLabel("TRANSFER FUNCTION");
        TFLabel = new JLabel();
        delta_label = new JLabel("BIG DELTA");
        delta_txt = new JLabel();
        delta_txt.setBackground(Color.yellow);
        
        Font font = new Font("Serif", Font.PLAIN, 32);
        tfLabel.setFont(font);
        TFLabel.setFont(font);
        
        ForwardPathLabel.setBounds(0, 0, 250, 40);
        ForwardPathLabeltxt.setBounds(0, 40, 250, height - 436);
        ForwardGainLabel.setBounds(250, 0, 150, 40);
        ForwardGainLabeltxt.setBounds(250, 40, 150, height - 436);
        
        ///////////////////////////////
        deltaGainLabel.setBounds(400, 0, 150, 40);
        deltaGainLabeltxt.setBounds(400, 40, 150, height - 436);
        
        //////////////////////////////
        
        loopsLabel.setBounds(550, 0, 250, 40);
        loopsLabeltxt.setBounds(550, 40, 250, height - 436);
        loopsGainLabel.setBounds(800, 0, 150, 40);
        loopsGainLabeltxt.setBounds(800, 40, 150, height - 436);
        
        nontouchLabel.setBounds(950, 0, 250, 40);
        nontouchLabeltxt.setBounds(950, 40, 250, height - 436);
        nonTouchGainLabel.setBounds(1200, 0, 150, 40);
        nonTouchGainLabeltxt.setBounds(1200, 40, 150, height - 436);
        
        delta_label.setBounds(1350, 0, 700, 40);
        delta_txt.setBounds(1350, 50, 400, 40);
        
        tfLabel.setBounds(1370, 800, 700, 40);
        TFLabel.setBounds(1370, 850, 400, 40);
        font = new Font("Serif", Font.PLAIN, 32);
        tfLabel.setFont(font);
        tfLabel.setBackground(Color.yellow);
        TFLabel.setFont(font);
        
        font = new Font("Serif", Font.BOLD, 16);
        
        loopsLabel.setFont(font);
        ForwardPathLabel.setFont(font);
        nontouchLabel.setFont(font);
        loopsGainLabel.setFont(font);
        ForwardGainLabel.setFont(font);
        delta_label.setFont(font);
        nonTouchGainLabel.setFont(font);
        
        getContentPane().add(loopsLabel);
        getContentPane().add(ForwardPathLabel);
        getContentPane().add(tfLabel);
        getContentPane().add(nontouchLabel);
        getContentPane().add(loopsGainLabel);
        getContentPane().add(ForwardGainLabel);
        getContentPane().add(nonTouchGainLabel);
        
        getContentPane().add(loopsLabeltxt);
        getContentPane().add(ForwardPathLabeltxt);
        getContentPane().add(TFLabel);
        getContentPane().add(nontouchLabeltxt);
        getContentPane().add(loopsGainLabeltxt);
        getContentPane().add(ForwardGainLabeltxt);
        getContentPane().add(nonTouchGainLabeltxt);
        getContentPane().add(deltaGainLabel);
        getContentPane().add(deltaGainLabeltxt);
        getContentPane().add(delta_label);
        getContentPane().add(delta_txt);
        getContentPane().add(drawPanel);
        
        drawPanel.repaint();
        
    }
    
    public void fillTable(StringBuilder sb) {
        
        sb.append("<font size=\"5\">");
        String[] temp = helper.getForwardPaths();
        
        for (int i = 0; i < temp.length; i++)
            sb.append((i + 1) + ") " + temp[i] + "<br>");
        sb.append("</font>");
        
        ForwardPathLabeltxt.setContentType("text/html");
        ForwardPathLabeltxt.setEditable(false);
        ForwardPathLabeltxt.setText(sb.toString());
        ForwardPathLabeltxt.setForeground(Color.blue);
        
        sb = new StringBuilder();
        sb.append("<font size=\"5\">");
        temp = helper.getLoops();
        for (int i = 0; i < temp.length; i++)
            sb.append((i + 1) + ") " + temp[i] + "<br>");
        sb.append("</font>");
        loopsLabeltxt.setContentType("text/html");
        loopsLabeltxt.setEditable(false);
        loopsLabeltxt.setText(sb.toString());
        
        sb = new StringBuilder();
        sb.append("<font size=\"5\">");
        ArrayList<ArrayList<String>> temp1;
        temp1 = helper.getNonTouchingloops();
        
        StringBuilder sb1 = new StringBuilder();
        sb1.append("<font size=\"5\">");
        Double[] temp3 = helper.getNonTouchingloopsGain();
        
        if (temp1 != null) {
            int addSize = 0;
            for (int i = 0; i < temp1.size(); i++) {
                sb.append((i + 1) + " non-Touching Loops:<br>");
                sb1.append("<br>");
                if (i != 0) {
                    addSize += temp1.get(i - 1).size();
                }
                for (int j = 0; j < temp1.get(i).size(); j++) {
                    sb.append((j + 1) + ") " + temp1.get(i).get(j) + "<br>");
                    sb1.append((j + 1) + ") " + temp3[j + addSize].doubleValue() + "<br>");
                }
                
            }
        }
        sb1.append("</font>");
        sb.append("</font>");
        nontouchLabeltxt.setContentType("text/html");
        nontouchLabeltxt.setEditable(false);
        nontouchLabeltxt.setText(sb.toString());
        
        nonTouchGainLabeltxt.setContentType("text/html");
        nonTouchGainLabeltxt.setEditable(false);
        nonTouchGainLabeltxt.setText(sb1.toString());
        
        sb = new StringBuilder();
        sb.append("<font size=\"5\">");
        
        temp3 = helper.getForwardPathsGain();
        for (int i = 0; i < temp3.length; i++)
            sb.append((i + 1) + ") " + temp3[i].doubleValue() + "<br>");
        sb.append("</font>");
        
        ForwardGainLabeltxt.setContentType("text/html");
        ForwardGainLabeltxt.setEditable(false);
        ForwardGainLabeltxt.setText(sb.toString());
        
        sb = new StringBuilder();
        sb.append("<font size=\"5\">");
        temp3 = helper.getLoopsGain();
        for (int i = 0; i < temp3.length; i++)
            sb.append((i + 1) + ") " + temp3[i].doubleValue() + "<br>");
        sb.append("</font>");
        
        loopsGainLabeltxt.setContentType("text/html");
        loopsGainLabeltxt.setEditable(false);
        loopsGainLabeltxt.setText(sb.toString());
        
        sb = new StringBuilder();
        sb.append("<font size=\"5\">");
        sb.append(" " + helper.getDeltas()[0]);
        sb.append("</font>");
        
        delta_txt.setFont(new Font("Serif", Font.PLAIN, 20));
        delta_txt.setText(" " + helper.getDeltas()[0] + "");
        
        sb = new StringBuilder();
        sb.append("<font size=\"5\">");
        for (int i = 1; i < helper.getDeltas().length; i++) {
            sb.append("Delta " + i + ": ");
            sb.append(helper.getDeltas()[i] + "<br>");
        }
        sb.append("</font>");
        deltaGainLabeltxt.setContentType("text/html");
        deltaGainLabeltxt.setEditable(false);
        deltaGainLabeltxt.setText(sb.toString());
        
        TFLabel.setText(helper.getTF() + "");
    }
    
}