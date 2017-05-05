package model;

import java.util.ArrayList;
import java.util.List;

public interface ISignalFlowGraph {
    /**
     * 
     * @param firstNode
     * @param secondNode
     * @param weight
     */
    
    void addEdge(int firstNode, int secondNode, double weight);
    
    /**
     * 
     * @param src
     * @param dest
     * @return
     */
    
    List<ArrayList<Integer>> getForwardPathes(int src, int dest);
    
    /**
     * 
     * @return
     */
    
    List<ArrayList<Integer>> getLoops();
    
    /**
     * 
     * @return
     */
    
    List<ArrayList<Integer>> getNonTouchingLoopsIndexes();
    
    /**
     * 
     * @param path
     * @return
     */
    double getGain(ArrayList<Integer> path);
    
    /**
     * 
     * @return
     */
    Double[] getDeltas();
    
    /**
     * 
     * @return
     */
    
    double calculateTransfereFunction();
    
}
