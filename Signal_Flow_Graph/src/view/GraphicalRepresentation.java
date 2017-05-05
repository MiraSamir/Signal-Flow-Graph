package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

import javax.swing.JPanel;

import controller.ISolveSysControllerHelper;

public class GraphicalRepresentation extends JPanel {
    
    private static final long serialVersionUID = 1L;
    
    private int nodes;
    private double[][] gains;
    private int x_disp;
    private int center;
    private int nodeRadius;
    
    private int tanBaseUp;
    private int tanBaseDown;
    private float selfLoopC1;
    private float selfLoopC2;
    private Path2D.Double path;
    private Graphics2D g2;
    private ISolveSysControllerHelper solveSysHelper;
    private int width;
    private int height;
    
    public GraphicalRepresentation(ISolveSysControllerHelper solveSysHelper, int width, int height) {
        this.solveSysHelper = solveSysHelper;
        this.width = width;
        this.height = height;
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        nodes = solveSysHelper.getNodes();
        gains = solveSysHelper.getGraph();
        x_disp = this.width / (nodes + 1);
        center = (this.height - 120) / 2;
        nodeRadius = 25;
        
        tanBaseUp = (int) (center - nodeRadius);
        tanBaseDown = (int) (center + nodeRadius);
        selfLoopC1 = center - 4 * nodeRadius;
        selfLoopC2 = center + 4 * nodeRadius;
        path = new Path2D.Double();
        g2 = (Graphics2D) g;
        
        drawNodes(g, new Font("Courier New", Font.PLAIN, 24));
        
        int x;
        for (int source = 0; source < nodes; source++) {
            for (int sink = 0; sink < nodes; sink++) {
                if (gains[source][sink] != 0) {
                    if (source == sink) { //self loop
                        // drawing arc
                        g.setColor(Color.ORANGE);
                        path = new Path2D.Double();
                        path.moveTo(x_disp * (source + 1), tanBaseUp);
                        x = x_disp * (source + 1) - 3 * nodeRadius;
                        path.curveTo(x, selfLoopC1, x, selfLoopC2, x_disp * (source + 1), tanBaseDown);
                        g2.draw(path);
                        
                        x = x + nodeRadius - 5;
                        // drawing arrow
                        path = new Path2D.Double();
                        path.moveTo(x + 12, center - 10);
                        path.lineTo(x, center + 12);
                        path.lineTo(x - 12, center - 10);
                        g2.fill(path);
                        // drawing gain text
                        g.setColor(Color.black);
                        g.drawString(gains[source][sink] + "", x_disp * (source + 1) - 5, center + 3 * nodeRadius);
                        
                    } else if (sink - source == 1) { //a straight line between adjacent nodes
                        // drawing arc
                        g.setColor(Color.magenta);
                        g.drawLine((source + 1) * x_disp + nodeRadius, center, (sink + 1) * x_disp - nodeRadius,
                                center);
                        // drawing arrow
                        x = (sink + source + 2) * x_disp / 2;
                        path = new Path2D.Double();
                        path.moveTo(x, center - 12);
                        path.lineTo(x, center + 12);
                        path.lineTo(x + 24, center);
                        g2.fill(path);
                        // drawing gain text
                        g.setColor(Color.black);
                        g.setFont(new Font("Courier New", Font.PLAIN, 16));
                        g.drawString(gains[source][sink] + "", x, center - 20);
                        
                    } else if (source > sink) { //feedback  //drawn downwards
                        // drawing arc
                        g.setColor(Color.green);
                        path = new Path2D.Double();
                        path.moveTo(x_disp * (source + 1), tanBaseDown);
                        x = x_disp * (sink + source + 2) / 2;
                        path.quadTo(x, center + (source - sink) * x_disp / 2, x_disp * (sink + 1), tanBaseDown);
                        g2.draw(path);
                        
                        // drawing arrow
                        path = new Path2D.Double();
                        path.moveTo(x - 12, center + (source - sink) * x_disp / 4 + 12);
                        path.lineTo(x + 12, center + (source - sink) * x_disp / 4);
                        path.lineTo(x + 12, center + (source - sink) * x_disp / 4 + 24);
                        g2.fill(path);
                        
                        // drawing gain text
                        g.setColor(Color.black);
                        g.setFont(new Font("Courier New", Font.PLAIN, 16));
                        g.drawString(gains[source][sink] + "", x - 12, center + (source - sink) * x_disp / 4 - 6);
                        
                    } else { //upward
                        
                        // drawing arc
                        g.setColor(Color.BLUE);
                        path = new Path2D.Double();
                        path.moveTo(x_disp * (source + 1), tanBaseUp);
                        x = x_disp * (sink + source + 2) / 2;
                        path.quadTo(x, center - (sink - source) * x_disp / 2, x_disp * (sink + 1), tanBaseUp);
                        g2.draw(path);
                        
                        // drawing arrow
                        path = new Path2D.Double();
                        path.moveTo(x + 12, center - (sink - source) * x_disp / 4 - 12);
                        path.lineTo(x - 12, center - (sink - source) * x_disp / 4);
                        path.lineTo(x - 12, center - (sink - source) * x_disp / 4 - 24);
                        g2.fill(path);
                        // drawing gain text
                        g.setColor(Color.black);
                        g.setFont(new Font("Courier New", Font.PLAIN, 16));
                        g.drawString(gains[source][sink] + "", x - 12, center - (sink - source) * x_disp / 4 + 24);
                    }
                }
            }
            
        }
    }
    
    public void drawNodes(Graphics g, Font font) {
        g.setFont(font);
        if (nodes > 0) {
            
            //R(S)
            g.setColor(Color.yellow);
            g.fillOval(x_disp - nodeRadius, center - nodeRadius, nodeRadius * 2, nodeRadius * 2);
            
            g.setColor(Color.black);
            g.drawString("R(S)", x_disp - nodeRadius, center - 2 * nodeRadius);
            //C(S)
            if (nodes > 1) {
                g.setColor(Color.YELLOW);
                g.fillOval(x_disp * (nodes) - nodeRadius, center - nodeRadius, nodeRadius * 2, nodeRadius * 2);
                g.setColor(Color.black);
                g.drawString("C(S)", x_disp * (nodes) - nodeRadius, center - 2 * nodeRadius);
            }
            
        }
        g.setColor(Color.pink);
        for (int i = 1; i < nodes - 1; i++)
            g.fillOval(x_disp * (i + 1) - nodeRadius, center - nodeRadius, nodeRadius * 2, nodeRadius * 2);
        
        g.setColor(Color.black);
        for (int i = 0; i < nodes; i++)
            g.drawString("" + (i + 1), x_disp * (i + 1) - nodeRadius + 18, center + 8);
        
    }
}
