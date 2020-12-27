import java.util.ArrayList;
import java.util.List;
public class ComputeReliability {
    Diagram diagram;
    Combinations com;
    List<Float> reliability = new ArrayList<>();
    List<Float> subreliability = new ArrayList<>();
    List<Float> conditionalProbability;
    float[][] stateProbability;
    public ComputeReliability(Diagram diagram,float[][] stateProbability){
        this.diagram = diagram;
        this.stateProbability = stateProbability;
        com = new Combinations(diagram);
        conditionalProbability = this.conditionalProbabilityCompute();

        for (int s=0;s<diagram.state;s++){
            subreliability = new ArrayList<>();
            for (int i=0;i<conditionalProbability.size();i++) {
                    subreliability.add(diagram.survivalSignature.get(i)[s]*conditionalProbability.get(i));
            }
            float sum=0;
            for (Float j : subreliability){
                sum+=j;
                }
            reliability.add(sum);
        }

        }
    public List<Float> conditionalProbabilityCompute(){
        int sum=0;
        ConditionalTable conditionalTable = new ConditionalTable(diagram);
        List<Float> list= new ArrayList<>();
        for (List<Integer> condition:conditionalTable.table){//行遍历
            float conditionalProbability=1f;
            for (int i=0;i<diagram.typesNum.length;i++){//类型遍历
                int combinationLower = diagram.typesNum[i];
                int combinationUpper=0;
                for (int s=0;s<diagram.state;s++){//状态遍历
                    combinationLower-=combinationUpper;
                    combinationUpper = condition.get(i*diagram.state+s);
                    conditionalProbability*=(float)C(combinationLower,combinationUpper)*(float)Math.pow(stateProbability[i][s],condition.get(i*diagram.state+s));
                }
            }
            list.add(conditionalProbability);
        }
        return list;
        }

    public static int A(int n, int m)
    {
        int result = 1;
        // 循环m次,如A(6,2)需要循环2次，6*5
        for (int i = m; i > 0; i--)
        {
            result *= n;
            n--;// 下一次减一
        }
        return result;
    }

    public static int C(int n, int m)// 应用组合数的互补率简化计算量
    {
        int helf = n / 2;
        if (m > helf)
        {
            m = n - m;
        }
        // 分子的排列数
        int numerator = A(n, m);
        // 分母的排列数
        int denominator = A(m, m);
        return numerator / denominator;
    }

//    public static void main(String[] args) {
//        System.out.println(C(5,1));
//    }
    }
