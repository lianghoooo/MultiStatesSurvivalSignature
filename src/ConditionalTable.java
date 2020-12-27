import java.util.ArrayList;
import java.util.List;

public class ConditionalTable {
    List<List<Integer>> table = new ArrayList<>();
    Diagram diagram;
    public ConditionalTable(Diagram diagram){
        this.diagram = diagram;
        this.table = conditionalTableGenerator();
    }
    public List<List<Integer>> conditionalTableGenerator(){
        List<ConditionalTableSingleType> listConditionalSingle = new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> subans = new ArrayList<>();
        for (int i=0;i<diagram.typesNum.length;i++)
            listConditionalSingle.add(new ConditionalTableSingleType(diagram,i));
        dfs(diagram,0,listConditionalSingle,ans,subans);
        return ans;
    }
    public void dfs(Diagram diagram,int index,List<ConditionalTableSingleType> listConditionalSingle,List<List<Integer>> ans, List<Integer> subans){
        if (index==diagram.typesNum.length){
            ans.add(new ArrayList<>(subans));
            return;
        }
        for (int i=0;i<listConditionalSingle.get(index).table.size();i++){
            for (int j=0;j<diagram.state;j++){
                subans.add(listConditionalSingle.get(index).table.get(i).get(j));
            }
            dfs(diagram,index+1,listConditionalSingle,ans,subans);
            for (int j=0;j<diagram.state;j++){
                subans.remove(subans.size()-1);
            }
        }
    }

    public static void main(String[] args) {
        int[][] components = new int[][] {{1,2,3},{4,5}};
        int state= 3;
        float[][] stateProbability={{0.01f,0.01f,0.98f},{0.03f,0.03f,0.94f}};
        Diagram diagram = new Diagram(components,stateProbability);
        ConditionalTable conditionalTable = new ConditionalTable(diagram);
        System.out.println(conditionalTable.table);
    }

}
