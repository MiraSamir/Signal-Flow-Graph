package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SignalFlowGraph implements ISignalFlowGraph {
    
    private List<HashMap<Integer, Double>> graph;
    private List<ArrayList<Integer>> forwardPathes;
    private List<ArrayList<Integer>> loops;
    private List<ArrayList<Integer>> nonTouchingLoopsIndexes;
    private Double[] deltas;
    
    public SignalFlowGraph(int noOfNodes) {
        graph = new ArrayList<HashMap<Integer, Double>>(noOfNodes);
        for (int i = 0; i < noOfNodes; i++) {
            HashMap<Integer, Double> array = new HashMap<Integer, Double>();
            graph.add(array);
        }
    }
    
    @Override
    public void addEdge(int firstNode, int secondNode, double weight) {
        this.graph.get(firstNode).put(secondNode, weight);
    }
    
    @Override
    public List<ArrayList<Integer>> getForwardPathes(int src, int dest) {
        List<ArrayList<Integer>> forwardPathes = new ArrayList<>();
        boolean visited[] = new boolean[graph.size()];
        ArrayList<Integer> path = new ArrayList<Integer>();
        dfs(-1, src, dest, visited, path, forwardPathes);
        return this.forwardPathes = forwardPathes;
    }
    
    @Override
    public List<ArrayList<Integer>> getLoops() {
        List<ArrayList<Integer>> loops = new ArrayList<>();
        boolean visited[] = new boolean[graph.size()];
        ArrayList<Integer> path = new ArrayList<Integer>();
        for (int i = 0; i < graph.size(); i++) {
            dfs(-1, i, i, visited, path, loops);
            visited[i] = true;
        }
        return this.loops = loops;
    }
    
    @Override
    public double getGain(ArrayList<Integer> path) {
        double gain = 1;
        for (int j = 0; j < path.size() - 1; j++) {
            gain *= graph.get(path.get(j)).get(path.get(j + 1));
        }
        return gain;
    }
    
    @Override
    public double calculateTransfereFunction() {
        if (this.forwardPathes == null)
            this.getForwardPathes(0, this.graph.size() - 1);
        if (this.loops == null)
            this.getLoops();
        deltas = new Double[1 + this.forwardPathes.size()];
        double delta = this.calculateDelta(true, loops);
        deltas[0] = delta;
        double TFNumerator = 0;
        for (int i = 0; i < this.forwardPathes.size(); i++) {
            List<ArrayList<Integer>> nonTouchingloops = new ArrayList<ArrayList<Integer>>();
            for (int j = 0; j < this.loops.size(); j++) {
                if (!areTouchingTwoLoops(this.forwardPathes.get(i), this.loops.get(j)))
                    nonTouchingloops.add(this.loops.get(j));
            }
            double currentDelta = calculateDelta(false, nonTouchingloops);
            deltas[i + 1] = currentDelta;
            TFNumerator = TFNumerator + (currentDelta * this.getGain(this.forwardPathes.get(i)));
        }
        return TFNumerator / delta;
    }
    
    @Override
    public List<ArrayList<Integer>> getNonTouchingLoopsIndexes() {
        return nonTouchingLoopsIndexes;
    }
    
    @Override
    public Double[] getDeltas() {
        return this.deltas;
    }
    
    private void dfs(int prev, int src, int dest, boolean visited[], ArrayList<Integer> path,
            List<ArrayList<Integer>> forwardPathes) {
        visited[src] = true;
        path.add(src);
        if (src == dest && prev != -1) {
            ArrayList<Integer> array = new ArrayList<>();
            for (Integer node : path)
                array.add(node);
            forwardPathes.add(array);
        } else {
            for (Integer key : graph.get(src).keySet()) {
                if (!visited[key] || key == dest) {
                    dfs(src, key, dest, visited, path, forwardPathes);
                }
            }
        }
        path.remove(path.size() - 1);
        visited[src] = false;
    }
    
    private double calculateDelta(boolean isBigDelta, List<ArrayList<Integer>> loops) {
        List<ArrayList<Integer>> indices = generateNonTouchingLoopsIndexes(isBigDelta, loops);
        double delta = 1;
        for (int i = 0; i < indices.size(); i++) {
            int currentSize = indices.get(i).size();
            double times = 1;
            for (int j = 0; j < currentSize; j++) {
                double d = this.getGain(loops.get(indices.get(i).get(j)));
                times = times * d;
            }
            delta = delta + Math.pow(-1, currentSize) * times;
        }
        return delta;
    }
    
    private List<ArrayList<Integer>> generateNonTouchingLoopsIndexes(boolean bigDelta, List<ArrayList<Integer>> loops) {
        List<ArrayList<Integer>> nonTouchingLoopsIndexes = new ArrayList<ArrayList<Integer>>();
        for (int k = 0; k < loops.size(); k++) {
            ArrayList<Integer> array = new ArrayList<>();
            array.add(k);
            nonTouchingLoopsIndexes.add(array);
        }
        boolean flag = true;
        int count = loops.size();
        while (flag) {// i = size of combination
            flag = false;
            for (int j = nonTouchingLoopsIndexes.size() - count; j < nonTouchingLoopsIndexes.size(); j++) {
                for (int k = 0; k < loops.size(); k++) { //loops 
                    if (k > nonTouchingLoopsIndexes.get(j).get(nonTouchingLoopsIndexes.get(j).size() - 1)
                            && !areTouchingLoops(k, nonTouchingLoopsIndexes.get(j), loops)) {
                        flag = true;
                        ArrayList<Integer> array = new ArrayList<>();
                        array.addAll(nonTouchingLoopsIndexes.get(j));
                        array.add(k);
                        nonTouchingLoopsIndexes.add(array);
                    }
                }
            }
            if (nonTouchingLoopsIndexes.size() > 0
                    && nonTouchingLoopsIndexes.get(nonTouchingLoopsIndexes.size() - 1).size() == loops.size())
                flag = false;
            count = nonTouchingLoopsIndexes.size() - count;
        }
        if (bigDelta)
            this.nonTouchingLoopsIndexes = nonTouchingLoopsIndexes;
        return nonTouchingLoopsIndexes;
    }
    
    private boolean areTouchingLoops(int index, ArrayList<Integer> loopIndices, List<ArrayList<Integer>> loops) {
        ArrayList<Integer> testedLoop = loops.get(index);
        for (int i = 0; i < loopIndices.size(); i++) {
            if (areTouchingTwoLoops(testedLoop, loops.get(loopIndices.get(i))))
                return true;
        }
        return false;
    }
    
    private boolean areTouchingTwoLoops(ArrayList<Integer> path1, ArrayList<Integer> path2) {
        ArrayList<Integer> temp = new ArrayList<>(path1);
        temp.retainAll(path2);
        return !(temp.size() == 0);
    }
    
}
