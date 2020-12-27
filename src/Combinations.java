import java.util.ArrayList;
import java.util.List;

public class Combinations {
    List<CombinationsSingleType> combinationsAllType = new ArrayList<>();
    List<List<List<Integer>>> combinations = new ArrayList<>();
    List<Integer> combinationOfTheCondition= new ArrayList<>();

    public Combinations(Diagram diagram){
        for (int i=0;i<diagram.typesNum.length;i++){
            combinationsAllType.add(new CombinationsSingleType(diagram,i));
        }
        combinations = combinationsGenerator();
        dfsForCombinationOfTheCondition(0,1,combinationsAllType);
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

     public void dfsForCombinationOfTheCondition(int index,int res,List<CombinationsSingleType> combinationsAllType){
        if (index==combinationsAllType.size()){
            combinationOfTheCondition.add(res);
            return;
        }
        for (int i=0;i<combinationsAllType.get(index).combinationOfTheCondition.size();i++){
            dfsForCombinationOfTheCondition(index+1,res*=combinationsAllType.get(index).combinationOfTheCondition.get(i),combinationsAllType);
            res/=combinationsAllType.get(index).combinationOfTheCondition.get(i);
        }
     }

    public static void main(String[] args) {
        int[][] components = new int[][] {{1,2,3},{4,5}};
        float[][] stateProbability={{0.01f,0.01f,0.98f},{0.03f,0.03f,0.94f}};
        int state= 3;
        Diagram diagram = new Diagram(components,stateProbability);
        Combinations com = new Combinations(diagram);
        System.out.println(com.combinationOfTheCondition.toString());
    }
}
