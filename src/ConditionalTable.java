import java.util.ArrayList;
import java.util.List;

public class ConditionalTable {
//    List<Integer> subtable = new ArrayList<>();
    List<ConditionalTableSingleType> table = new ArrayList<>();
    public ConditionalTable(Diagram diagram){
        this.table = conditionalTableGenerator(diagram);
    }
    public  List<ConditionalTableSingleType> conditionalTableGenerator(Diagram diagram){
        dfs(diagram,0);
        return table;
    }
    public void dfs(Diagram diagram,int index){
        if (index==diagram.typesNum.length){
            return;
        }
        table.add(new ConditionalTableSingleType(diagram,index));
        dfs(diagram,index+1);
    }

//    public static void main(String[] args) {
//        ConditionalTable conditionalTable =  new ConditionalTable(new int[] {3,2},3);
//        System.out.println(conditionalTable.table);
//    }

}
