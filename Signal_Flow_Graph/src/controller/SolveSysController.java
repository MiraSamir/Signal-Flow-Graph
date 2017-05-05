package controller;

import java.util.ArrayList;
import java.util.List;

import model.ISignalFlowGraph;
import model.SignalFlowGraph;
import view.ResultPanel;

public class SolveSysController implements ISolveSysController {
    private ISignalFlowGraph SFG;
    private ISolveSysControllerHelper helper;
    
    public SolveSysController(ISolveSysControllerHelper helper) {
        this.helper = helper;
        SFG = new SignalFlowGraph(helper.getNodes());
        for (int i = 0; i < helper.getNodes(); i++) {
            for (int j = 0; j < helper.getNodes(); j++) {
                if (helper.getGraph()[i][j] != 0) {
                    SFG.addEdge(i, j, helper.getGraph()[i][j]);
                }
            }
        }
    }
    
    @Override
    public void showResults(ResultPanel resultView) {
        this.calculateTF();
        resultView.fillTable(new StringBuilder());
    }
    
    private void calculateTF() {
        List<ArrayList<Integer>> pathes = this.SFG.getForwardPathes(0, helper.getNodes() - 1);
        List<ArrayList<Integer>> loops = this.SFG.getLoops();
        this.helper.setForwardPaths(adaptArrayListToString(pathes));
        this.helper.setForwardPathsGain(this.calculateGain(pathes));
        this.helper.setLoops(adaptArrayListToString(loops));
        this.helper.setLoopsGain(this.calculateGain(loops));
        this.helper.setTF(this.SFG.calculateTransfereFunction());
        this.helper.setDeltas(this.SFG.getDeltas());
        List<ArrayList<Integer>> nonTouchingLoopsIndexes = this.SFG.getNonTouchingLoopsIndexes();
        if (nonTouchingLoopsIndexes.size() > 0)
            this.adaptNonTouchingLoops(nonTouchingLoopsIndexes, loops);
        
    }
    
    private Double[] calculateGain(List<ArrayList<Integer>> array) {
        Double[] gains = new Double[array.size()];
        for (int i = 0; i < array.size(); i++) {
            gains[i] = SFG.getGain(array.get(i));
        }
        return gains;
    }
    
    private String[] adaptArrayListToString(List<ArrayList<Integer>> array) {
        String[] result = new String[array.size()];
        for (int i = 0; i < array.size(); i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < array.get(i).size(); j++) {
                builder.append(array.get(i).get(j) + 1);
                if (j < array.get(i).size() - 1)
                    builder.append(",");
            }
            result[i] = builder.toString();
        }
        return result;
    }
    
    private void adaptNonTouchingLoops(List<ArrayList<Integer>> nonTouchingLoopsIndexes,
            List<ArrayList<Integer>> loops) {
        ArrayList<ArrayList<String>> nonTouchingLoops = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < nonTouchingLoopsIndexes.get(nonTouchingLoopsIndexes.size() - 1).size(); i++) {
            ArrayList<String> array = new ArrayList<String>();
            nonTouchingLoops.add(array);
        }
        Double[] nonTouchingLoopsGain = new Double[nonTouchingLoopsIndexes.size()];
        for (int i = 0; i < nonTouchingLoopsIndexes.size(); i++) {
            StringBuilder builder = new StringBuilder();
            double gain = 1;
            for (int j = 0; j < nonTouchingLoopsIndexes.get(i).size(); j++) {
                gain *= SFG.getGain(loops.get(nonTouchingLoopsIndexes.get(i).get(j)));
                for (int k = 0; k < loops.get(nonTouchingLoopsIndexes.get(i).get(j)).size(); k++) {
                    builder.append(loops.get(nonTouchingLoopsIndexes.get(i).get(j)).get(k) + 1);
                    if (k < loops.get(nonTouchingLoopsIndexes.get(i).get(j)).size() - 1)
                        builder.append(",");
                }
                if (j < nonTouchingLoopsIndexes.get(i).size() - 1)
                    builder.append(" / ");
            }
            nonTouchingLoops.get(nonTouchingLoopsIndexes.get(i).size() - 1).add(builder.toString());
            nonTouchingLoopsGain[i] = gain;
        }
        this.helper.setNonTouchingloops(nonTouchingLoops);
        this.helper.setNonTouchingloopsGain(nonTouchingLoopsGain);
    }
    
}