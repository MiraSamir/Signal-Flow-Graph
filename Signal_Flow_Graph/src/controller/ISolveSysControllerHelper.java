package controller;

import java.util.ArrayList;

public interface ISolveSysControllerHelper {
    
    public double[][] getGraph();
    
    public void setGraph(int x, int y, double gain);
    
    public int getNodes();
    
    public void setNodes(int nodes);
    
    public double getTF();
    
    public void setTF(double tF);
    
    public String[] getLoops();
    
    public void setLoops(String[] loops);
    
    public ArrayList<ArrayList<String>> getNonTouchingloops();
    
    public void setNonTouchingloops(ArrayList<ArrayList<String>> nonTouchingloops);
    
    public String[] getForwardPaths();
    
    public void setForwardPaths(String[] forwardPaths);
    
    public Double[] getLoopsGain();
    
    public void setLoopsGain(Double[] loopsGain);
    
    public Double[] getNonTouchingloopsGain();
    
    public void setNonTouchingloopsGain(Double[] nonTouchingloopsGain);
    
    public Double[] getForwardPathsGain();
    
    public void setForwardPathsGain(Double[] forwardPathsGain);
    
    public Double[] getDeltas();
    
    public void setDeltas(Double[] deltas);
    
}
