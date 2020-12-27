import java.util.ArrayList;
import java.util.List;

public class CombinationsSingleType {
            List<Integer> sublist = new ArrayList<>();
            List<List<Integer>> singleStateList = new ArrayList<>();
            List<List<List<Integer>>> allStateList = new ArrayList<>();
            List<List<List<Integer>>> combinationSingleType= new ArrayList<>();
            List<Integer> combinationOfTheCondition= new ArrayList<>();;
            Diagram diagram;
            int type;
    public CombinationsSingleType(Diagram diagram,int type){
                this.type = type;
                this.diagram = diagram;
                this.combinationSingleType=CombinationsSingleTypeGenerator();
            }

    public List<List<List<Integer>>> CombinationsSingleTypeGenerator() {
        int len;
        List<List<Integer>> conditionalTableSingeType = new ConditionalTableSingleType(diagram,type).table;
        for (int i = 0; i < conditionalTableSingeType.size(); i++){
            for (int j = 0; j < diagram.state; j++){
                singleStateDfs(conditionalTableSingeType.get(i).get(j),0);
                allStateList.add(new ArrayList<>(singleStateList));
                singleStateList.clear();
            }
        }

        for (int i=0;i<allStateList.size()/diagram.state;i++){
            boolean[] flag = new boolean[diagram.componentsNum+1];
            boolean signalNoDFS = false;
            len = combinationSingleType.size();
            allStateDfs(allStateList.subList(i*diagram.state,diagram.state*(i+1)),0,flag,signalNoDFS);
            combinationOfTheCondition.add(combinationSingleType.size()-len);
        }

            return combinationSingleType;
            }

    public void singleStateDfs(int numsInState,int index){
                if (sublist.size()==numsInState){
                    singleStateList.add(new ArrayList<>(sublist));
                    return;
                }
        for (int i=index;i<diagram.components[type].length;i++){
            sublist.add(diagram.components[type][i]);
            singleStateDfs(numsInState,i+1);
            sublist.remove(sublist.size()-1);
        }
    }

    public void allStateDfs( List<List<List<Integer>>> allStateList,int index,boolean[] flag,boolean signalNoDFS){
        if (index==diagram.state){
            combinationSingleType.add(new ArrayList<>(singleStateList));
            return;
        }
        List<List<Integer>> targetLine = allStateList.get(index);
        for (int i=0;i<targetLine.size();i++) {
            for (int j = 0; j < targetLine.get(i).size(); j++) {
                if (flag[targetLine.get(i).get(j)])
                    signalNoDFS = true;
            }
            if (!signalNoDFS) {
                for (int j = 0; j < targetLine.get(i).size(); j++)
                    flag[targetLine.get(i).get(j)] = true;
                singleStateList.add(targetLine.get(i));
                allStateDfs(allStateList, index + 1, flag, signalNoDFS);
                singleStateList.remove(singleStateList.size() - 1);
                for (int j = 0; j < targetLine.get(i).size(); j++) {
                    flag[targetLine.get(i).get(j)] = false;
                }
            }
            signalNoDFS = false;
        }

    }

    public static void main(String[] args) {
        int[][] components = new int[][] {{1,2,3},{4,5}};
        float[][] stateProbability={{0.01f,0.01f,0.98f},{0.03f,0.03f,0.94f}};
        int state= 3;
        Diagram diagram = new Diagram(components,stateProbability);
        CombinationsSingleType com = new CombinationsSingleType(diagram,1);
        System.out.println("over");
    }
}
