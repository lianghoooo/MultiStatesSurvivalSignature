import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConditionalTableSingleType {
    List<Integer> subtable = new ArrayList<>();
    List<List<Integer>> table = new ArrayList<>();
    List<Float> conditionalProbabilitySingleType = new ArrayList<>();
    int type;
    public ConditionalTableSingleType(Diagram diagram,int type){
        this.type=type;
        this.table = tableGenrator(diagram,type);
    }

    public List<List<Integer>> tableGenrator(Diagram diagram,int type){
        int[] nums = new int[diagram.components[type].length+1];
        for (int i=0;i<=diagram.components[type].length;i++){
            nums[i]=i;
        }
        dfs(nums,diagram,0);
        return table;
    }

    public void dfs(int[] nums,Diagram diagram, int index){
        if (index==diagram.state){
            int sum=0;
            for (int num:subtable){
                sum+=num;
            }
            if (sum==nums[nums.length-1])
                table.add(new ArrayList<>(subtable));
            return;
        }

        for (int i=0;i<nums.length;i++){
            subtable.add(nums[i]);
            dfs(nums,diagram,index+1);
            subtable.remove(subtable.size()-1);
        }

    }
    public static void main(String[] args) {
        int[][] components = new int[][] {{1,3,4},{2,5}};
        float[][] stateProbability={{0.01f,0.01f,0.98f},{0.03f,0.03f,0.94f}};
        int state= 3;
        Diagram diagram = new Diagram(components,stateProbability);
        int type=1;
        ConditionalTableSingleType con = new ConditionalTableSingleType(diagram,type);
        new Output().output2DList(con.table,"ConditonalTableType"+type);
        System.out.println("");
    }
}
