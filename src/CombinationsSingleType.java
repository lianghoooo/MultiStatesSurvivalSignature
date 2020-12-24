import java.util.ArrayList;
import java.util.List;

public class CombinationsSingleType {
            List<Integer> sublist = new ArrayList<>();
            List<List<Integer>> singleStateList = new ArrayList<>();
            List<List<List<Integer>>> allStateList = new ArrayList<>();
            List<List<List<Integer>>> combinationSingleType= new ArrayList<>();
            Diagram diagram;
            int type;
    public CombinationsSingleType(Diagram diagram,int type){
                this.type = type;
                this.diagram = diagram;
                this.combinationSingleType=CombinationsSingleTypeGenerator();
            }

    public List<List<List<Integer>>> CombinationsSingleTypeGenerator() {
        List<List<Integer>> conditionalTableSingeType = new ConditionalTableSingleType(diagram,type).table;
        for (int i = 0; i < conditionalTableSingeType.size(); i++){
            for (int j = 0; j < diagram.state; j++){
                boolean[] flag = new boolean[diagram.typesNum[type]];
                singleStateDfs(conditionalTableSingeType.get(i).get(j),0);
                allStateList.add(new ArrayList<>(singleStateList));
                singleStateList.clear();
            }
        }

        for (int i=0;i<allStateList.size()/3;i++){
            allStateDfs(allStateList.subList(i*diagram.state,diagram.state*(i+1)),0);
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

    public void allStateDfs( List<List<List<Integer>>> allStateList,int index){
        if (index==diagram.state){
            combinationSingleType.add(new ArrayList<>(singleStateList));
            return;
        }
        List<List<Integer>> targetLine = allStateList.get(index);
        for (int i=0;i<targetLine.size();i++){
            singleStateList.add(targetLine.get(i));
            allStateDfs(allStateList,index+1);
            singleStateList.remove(singleStateList.size()-1);
        }

    }

    public static void main(String[] args) {
        int[][] components = new int[][] {{1,2,3},{4,5}};
        int state= 3;
        Diagram diagram = new Diagram(components,state);
        CombinationsSingleType com = new CombinationsSingleType(diagram,0);
        System.out.println("over");
    }
}
