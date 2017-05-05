package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.ISolveSysController;
import controller.ISolveSysControllerHelper;
import controller.SolveSysController;
import model.Utility;

public class SignalFlowPanel extends JFrame {
    
    private static final long serialVersionUID = 1L;
    private JLabel from_label, to_label, gain_label;
    private JTextField from_text, to_text, gain_text;
    private JButton solveButton, submitButton, undoButton, redoButton;
    private JPanel drawPanel;
    private int width, height;
    private ISolveSysControllerHelper graph;
    private Stack<Point> undoStack, redoStack;
    private Stack<Double> undogains, redogains;
    
    SignalFlowPanel(ISolveSysControllerHelper graph) {
        this.graph = graph;
        initialize();
    }
    
    private void initialize() {
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) screenSize.width;
        height = (int) screenSize.height - 50;
        undoStack = new Stack<Point>();
        redoStack = new Stack<Point>();
        undogains = new Stack<Double>();
        redogains = new Stack<Double>();
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(0, 0, width, height);
        setTitle("Signal Flow Graph");
        setLayout(null);
        setResizable(true);
        
        from_label = new JLabel("From: ");
        from_label.setBounds(60, height - 120, 100, 50);
        from_text = new JTextField();
        from_text.setBounds(120, height - 120, 60, 50);
        
        to_label = new JLabel("To: ");
        to_label.setBounds(220, height - 120, 100, 50);
        to_text = new JTextField();
        to_text.setBounds(255, height - 120, 60, 50);
        
        gain_label = new JLabel("With Gain: ");
        gain_label.setBounds(340, height - 120, 160, 50);
        gain_text = new JTextField();
        gain_text.setBounds(450, height - 120, 70, 50);
        
        drawPanel = new GraphicalRepresentation(this.graph, width, height);
        drawPanel.setBounds(0, 0, width, height - 120);
        drawPanel.setBackground(Color.WHITE);
        
        submitButton = new JButton("Submit");
        submitButton.setBounds(560, height - 110, 130, 50);
        
        undoButton = new JButton("Undo");
        undoButton.setBounds(720, height - 110, 130, 50);
        
        redoButton = new JButton("Redo");
        redoButton.setBounds(880, height - 110, 130, 50);
        
        solveButton = new JButton("Solve System");
        solveButton.setBounds(1500, height - 110, 300, 70);
        
        solveButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
                ISolveSysController controller = new SolveSysController(graph);
                
                ResultPanel resultView = new ResultPanel(graph);
                resultView.setVisible(true);
                controller.showResults(resultView);
            }
            
        });
        submitButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (!Utility.isValidInt(from_text.getText())) {
                    ErrorPanel errView = new ErrorPanel("Invalid Entry!");
                    errView.setVisible(true);
                } else if (!Utility.isValidInt(to_text.getText())) {
                    ErrorPanel errView = new ErrorPanel("Invalid Entry!");
                    errView.setVisible(true);
                } else if (!Utility.isValidDouble(gain_text.getText())) {
                    ErrorPanel errView = new ErrorPanel("Invalid Entry!");
                    errView.setVisible(true);
                } else {
                    int to = Integer.parseInt(from_text.getText());
                    int from = Integer.parseInt(to_text.getText());
                    if (to > graph.getNodes() || from > graph.getNodes()) {
                        ErrorPanel errView = new ErrorPanel("Node number is Invalid!");
                        errView.setVisible(true);
                    } else if (to < 1 || from < 1) {
                        ErrorPanel errView = new ErrorPanel("Node number is Invalid!");
                        errView.setVisible(true);
                    } else if (from == 1) {
                        ErrorPanel errView = new ErrorPanel("Input Node cannot have a Feedback!");
                        errView.setVisible(true);
                    } else {
                        double g = Double.parseDouble(gain_text.getText());
                        graph.setGraph(to - 1, from - 1, graph.getGraph()[to - 1][from - 1] + g);
                        Point temp = new Point();
                        temp.x = to - 1;
                        temp.y = from - 1;
                        undoStack.push(temp);
                        undogains.push(g);
                        drawPanel.repaint();
                        to_text.setText("");
                        from_text.setText("");
                        gain_text.setText("");
                    }
                }
            }
        });
        
        undoButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Point undo = new Point();
                if (!undoStack.isEmpty()) {
                    undo = undoStack.pop();
                    graph.setGraph(undo.x, undo.y, graph.getGraph()[undo.x][undo.y] - undogains.peek());
                    redoStack.push(undo);
                    redogains.push(undogains.pop());
                    drawPanel.repaint();
                } else {
                    ErrorPanel errView = new ErrorPanel("No more undo's");
                    errView.setVisible(true);
                }
            }
            
        });
        
        redoButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Point redo = new Point();
                if (!redoStack.isEmpty()) {
                    redo = redoStack.pop();
                    graph.setGraph(redo.x, redo.y, graph.getGraph()[redo.x][redo.y] + redogains.peek());
                    undoStack.push(redo);
                    undogains.push(redogains.pop());
                    drawPanel.repaint();
                } else {
                    ErrorPanel errView = new ErrorPanel("No more redo's");
                    errView.setVisible(true);
                }
            }
            
        });
        
        Font font = new Font("Serif", Font.PLAIN, 24);
        from_label.setFont(font);
        to_label.setFont(font);
        gain_label.setFont(font);
        from_text.setFont(font);
        to_text.setFont(font);
        gain_text.setFont(font);
        submitButton.setFont(font);
        solveButton.setFont(font);
        undoButton.setFont(font);
        redoButton.setFont(font);
        
        getContentPane().add(from_label);
        getContentPane().add(to_label);
        getContentPane().add(gain_label);
        getContentPane().add(from_text);
        getContentPane().add(to_text);
        getContentPane().add(gain_text);
        getContentPane().add(drawPanel);
        getContentPane().add(solveButton);
        getContentPane().add(submitButton);
        getContentPane().add(undoButton);
        getContentPane().add(redoButton);
        
    }
    
}
