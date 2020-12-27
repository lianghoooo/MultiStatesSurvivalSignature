import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComputeSurvivalSignature {
    List<Integer> sublist = new ArrayList<>();
    List<List<Integer>> singleStateList = new ArrayList<>();
    List<List<List<Integer>>> allStateList = new ArrayList<>();
    List<List<List<Integer>>> combinationsSingleType = new ArrayList<>();
    List<List<List<Integer>>> subCombinations = new ArrayList<>();

    Diagram diagram;
    public ComputeSurvivalSignature(Diagram diagram){
        this.diagram=diagram;
    }
    public List<float[]> computeSurvivalSignature(Diagram diagram,ConditionalTable conditionalTable){
        List<float[]> survivalSignature = new ArrayList<>();
        List<Integer> signatures= new ArrayList<>();

        for (int i=0;i<conditionalTable.table.size();i++){
            List<Integer> table = conditionalTable.table.get(i);
            List<List<List<Integer>>> subCombinations = iterateCombinations(table,diagram);//获得每一个条件下的所有的组合
            for (List<List<Integer>> subCombination:subCombinations){
                signatures.add(changeExistPathsState(diagram.existPaths,subCombination));//每个组合计算得到状态
            }
            //每个条件下各个状态的概率
            float[] stateFrequencies = stateFrequency(signatures);
            survivalSignature.add(stateFrequencies);
            subCombinations.clear();
            signatures.clear();
        }
        return survivalSignature;
    }
    public List<List<List<Integer>>> iterateCombinations(List<Integer> table,Diagram diagram){
        //分类进行遍历，获得每一类的组合情况
        //将不同类的组合进行组合

        // 内-外list：单个state的组合情况，多个state，多行，多type
        List<List<List<List<Integer>>>> combinationsSingleTypeCollection = new ArrayList<>();
        for (int i=0;i<diagram.typesNum.length;i++){
            List<List<List<Integer>>> combinationsSingleType = CombinationsSingleTypeGenerator(table.subList(i*diagram.state,(i+1)*diagram.state),i);//获得该条件下下每一类零件的组合
            combinationsSingleTypeCollection.add(new ArrayList<>(combinationsSingleType));
            allStateList.clear();
            combinationsSingleType.clear();
        }
        List<List<Integer>> tmp= new ArrayList<>();
        dfsForcombinationsSingleTypeCollection(0,combinationsSingleTypeCollection,tmp);
        return subCombinations;

    }

    public void dfsForcombinationsSingleTypeCollection(int index,List<List<List<List<Integer>>>> combinationsSingleTypeCollection,List<List<Integer>> tmp){
        if (index==combinationsSingleTypeCollection.size()){
            subCombinations.add(new ArrayList<>(tmp));
            return;
        }
            for (List<List<Integer>> combinationSingleType:combinationsSingleTypeCollection.get(index)){
                tmp.addAll(combinationSingleType);
                dfsForcombinationsSingleTypeCollection(index+1,combinationsSingleTypeCollection,tmp);
                for(int i=0;i<diagram.state;i++){
                    tmp.remove(tmp.size()-1);
                }
            }

    }


    public List<List<List<Integer>>> CombinationsSingleTypeGenerator(List<Integer> conditionalTableSingeType, int type) {
            for (int j = 0; j < diagram.state; j++){
                singleStateDfs(conditionalTableSingeType.get(j),0,type);
                allStateList.add(new ArrayList<>(singleStateList));
                singleStateList.clear();
            }

            boolean[] flag = new boolean[diagram.componentsNum+1];
            boolean signalNoDFS = false;
            allStateDfs(allStateList,0,flag,signalNoDFS);

        return combinationsSingleType;
    }

    public void singleStateDfs(int numsInState,int index,int type){//某个类型的零件在某个状态下的所有可能组合情况
        if (sublist.size()==numsInState){
            singleStateList.add(new ArrayList<>(sublist));
            return;
        }
        for (int i=index;i<diagram.components[type].length;i++){
            sublist.add(diagram.components[type][i]);
            singleStateDfs(numsInState,i+1,type);
            sublist.remove(sublist.size()-1);
        }
    }

    // 将之前每个状态对应的组合综合考虑进行dfs，然后剔除零件重复或缺少的组合
    public void allStateDfs( List<List<List<Integer>>> allStateList,int index,boolean[] flag,boolean signalNoDFS){
        if (index==diagram.state){
            combinationsSingleType.add(new ArrayList<>(singleStateList));
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




    public int changeExistPathsState(List<List<Integer>> existPaths,List<List<Integer>> combinationsAlltypes){
        float[] res = new  float[existPaths.size()];
        Arrays.fill(res,1f);
        float stateOfTheComponent=this.diagram.state-1;
        for (int i=0;i<combinationsAlltypes.size();i+=diagram.state)//来自哪个类型的零件
        {
            for (List<Integer> com : combinationsAlltypes.subList(i,i+diagram.state)){//来自哪个状态
                for (List<Integer> path:existPaths)//来自哪个path
                    for (Integer component:com){//某个状态下某个零件
                        if (path.contains(component)){
                            stateOfTheComponent = combinationsAlltypes.subList(i,i+diagram.state).indexOf(com);
                            res[existPaths.indexOf(path)]*= stateOfTheComponent/(float)(this.diagram.state-1);
                        }
                    }
            }
        }
        float max=0;
        for (float num:res)
            max=Math.max(num,max);
        return new StateDefine().stateDefine(max,diagram);
    }

    public float[] stateFrequency(List<Integer> signatures){
        float[] stateFrequencies = new float[diagram.state+1];
        int count=0;
        for (float signature:signatures){
            for (int i=0;i<this.diagram.state;i++){
                if (signature==i){
                    stateFrequencies[i]++;
                    count++;
                }
            }
        }
        stateFrequencies[diagram.state]=count;
        for (int i=0;i<diagram.state;i++){
            stateFrequencies[i]/=count;
        }
        return stateFrequencies;
    }
}
