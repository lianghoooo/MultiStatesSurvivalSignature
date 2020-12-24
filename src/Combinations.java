import java.util.ArrayList;
import java.util.List;

public class Combinations {
    List<CombinationsSingleType> combinationsAllType = new ArrayList<>();
    List<List<List<Integer>>> combinations = new ArrayList<>();

    public Combinations(Diagram diagram){
        for (int i=0;i<diagram.typesNum.length;i++){
            combinationsAllType.add(new CombinationsSingleType(diagram,i));
        }
        combinations = combinationsGenerator();
    }

    public List<List<List<Integer>>> combinationsGenerator(){
        dfs(0,combinationsAllType);
        return combinations;
    }
     public void dfs(int index,List<CombinationsSingleType> combinationsAllType){
        if (index==combinationsAllType.size()){
            return;
        }
        for (int i=0;i<combinationsAllType.get(index).combinationSingleType.size();i++){
            combinations.add(combinationsAllType.get(index).combinationSingleType.get(i));
            dfs(index+1,combinationsAllType);
        }
     }

    public static void main(String[] args) {
        int[][] components = new int[][] {{1,2,3},{4,5}};
        int state= 3;
        Diagram diagram = new Diagram(components,state);
        Combinations com = new Combinations(diagram);
        System.out.println("");
    }
}
