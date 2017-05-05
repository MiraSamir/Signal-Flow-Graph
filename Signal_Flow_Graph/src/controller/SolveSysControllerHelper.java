package controller;

import java.util.ArrayList;

public class SolveSysControllerHelper implements ISolveSysControllerHelper {
    
    private double[][] graph;
    private int nodes;
    private double TF;
    private String[] loops;
    private ArrayList<ArrayList<String>> nonTouchingloops;
    private String[] forwardPaths;
    private Double[] loopsGain;
    private Double[] nonTouchingloopsGain;
    private Double[] forwardPathsGain;
    private Double[] deltas;
    
    public SolveSysControllerHelper(int n) {
        this.graph = new double[n][n];
    }
    
    public double[][] getGraph() {
        return graph;
    }
    
    public void setGraph(int x, int y, double gain) {
        this.graph[x][y] = gain;
    }
    
    public int getNodes() {
        return nodes;
    }
    
    public void setNodes(int nodes) {
        this.nodes = nodes;
    }
    
    public double getTF() {
        return TF;
    }
    
    public void setTF(double tF) {
        TF = tF;
    }
    
    public String[] getLoops() {
        return loops;
    }
    
    public void setLoops(String[] loops) {
        this.loops = loops;
    }
    
    public ArrayList<ArrayList<String>> getNonTouchingloops() {
        return nonTouchingloops;
    }
    
    public void setNonTouchingloops(ArrayList<ArrayList<String>> nonTouchingloops) {
        this.nonTouchingloops = nonTouchingloops;
    }
    
    public String[] getForwardPaths() {
        return forwardPaths;
    }
    
    public void setForwardPaths(String[] forwardPaths) {
        this.forwardPaths = forwardPaths;
    }
    
    public Double[] getLoopsGain() {
        return loopsGain;
    }
    
    public void setLoopsGain(Double[] loopsGain) {
        this.loopsGain = loopsGain;
    }
    
    public Double[] getNonTouchingloopsGain() {
        return nonTouchingloopsGain;
    }
    
    public void setNonTouchingloopsGain(Double[] nonTouchingloopsGain) {
        this.nonTouchingloopsGain = nonTouchingloopsGain;
    }
    
    public Double[] getForwardPathsGain() {
        return forwardPathsGain;
    }
    
    public void setForwardPathsGain(Double[] forwardPathsGain) {
        this.forwardPathsGain = forwardPathsGain;
    }
    
    public Double[] getDeltas() {
        return deltas;
    }
    
    public void setDeltas(Double[] deltas) {
        this.deltas = deltas;
    }
    
}
