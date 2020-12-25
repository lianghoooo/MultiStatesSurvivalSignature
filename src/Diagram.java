import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Diagram {
    int[][] components;
    int componentsNum=0;
    int[][] diagram;
    int[] typesNum;
    int state;
    List<CombinationsSingleType> combinations = new ArrayList<>();
    List<List<Integer>> existPaths = new ArrayList<>();
    List<Float> subSurvivalSignature = new ArrayList<>();
    List<float[]> survivalSignature = new ArrayList<>();
    public Diagram(int[][] components,int state){
        this.components=components;
        this.typesNum = new int[components.length];
        for (int i=0;i<components.length;i++){
            this.typesNum[i]=components[i].length;
            for (int j=0;j<components[i].length;j++){
                this.componentsNum++;
            }
        }

        this.diagram = new int[componentsNum+2][componentsNum+2];
        this.state = state;
    }
    public void link(int start, int end){
        this.diagram[start][end]=1;
    }

    public List<List<Integer>> getExistPaths(){
        List<Integer> path = new ArrayList<>();
        dfsForExistPaths(this,0,path,existPaths);
        return existPaths;
    }


    public  List<float[]> getSurvivalSinature(){

        this.getCombinations();
        List<List<List<Integer>>> combinationsAlltypes = new ArrayList<>();
        dfsAllTypesCombinations(0,combinationsAlltypes,subSurvivalSignature);
        Combinations com = new Combinations(this);
        int index=0;
        for(Integer num :com.combinationOfTheCondition){//多少组合对应着一种条件
            float[] stateCount = new float[state+1];
            for (int i=0;i<num;i++){//组合数量的多少
                for (int j=0;j<state;j++){//分状态统计
                    if (subSurvivalSignature.get(index)==(float)j/(float)(state-1))
                        stateCount[j]++;
                }
                index++;
            }
            for (int i=0;i<state;i++){
                stateCount[i]/=(float)num;
            }
            stateCount[state] = num;
            survivalSignature.add(stateCount);
        }
        return survivalSignature;
    }

    public void getCombinations(){
        for (int i=0;i<this.typesNum.length;i++){
            combinations.add(new CombinationsSingleType(this,i));
        }
    }

    public void dfsForExistPaths(Diagram diagram,int start,List<Integer> path,List<List<Integer>> existPaths){
        if (start==diagram.diagram.length-1){
            List<Integer> tmpPath = new ArrayList<>(path);
            tmpPath.remove(tmpPath.size()-1);
            existPaths.add(tmpPath);
        }
        for(int i=1;i<diagram.diagram.length;i++){
            if (diagram.diagram[start][i]==1){
                path.add(i);
                diagram.diagram[start][i]=-1;
                dfsForExistPaths(diagram,i,path,existPaths);
                path.remove(path.size()-1);
                diagram.diagram[start][i]=1;
            }
        }
    }

    public void dfsAllTypesCombinations(int index,List<List<List<Integer>>> combinationsAlltypes,List<Float> ans){
        if (index==this.typesNum.length){
            ans.add(changeExistPathsState(existPaths,combinationsAlltypes));
            return;
        }
            for(List<List<Integer>> subCombination:combinations.get(index).combinationSingleType){//每个类型的子组合
                combinationsAlltypes.add(subCombination);
                dfsAllTypesCombinations(index+1,combinationsAlltypes,ans);
                combinationsAlltypes.remove(combinationsAlltypes.size()-1);
            }
        }


    public float changeExistPathsState(List<List<Integer>> existPaths,List<List<List<Integer>>> combinationsAlltypes){
        float[] res = new  float[existPaths.size()];
        Arrays.fill(res,1f);
        for (int i=0;i<combinationsAlltypes.size();i++)//来自哪个类型的零件
        {
            for (List<Integer> com : combinationsAlltypes.get(i)){//来自哪个状态
                for (List<Integer> path:existPaths)//来自哪个path
                    for (Integer component:com){//某个状态下某个零件
                        if (path.contains(component))
                            res[existPaths.indexOf(path)]*= ((float)combinationsAlltypes.get(i).indexOf(com)/(float)(this.state-1));
                    }
            }
        }
        float max=0;
        for (float num:res)
            max=Math.max(num,max);
        return stateDefine(max);
    }

    public float stateDefine(float max){
        if (max>0&&max<1)
            max=0.5f;
        return max;
    }

}
