import java.util.List;

public class Solution {
    public static void main(String[] args) {
//        -----------------------------------------------
//        1.在customDiagram类中设置好需要计算的系统结构图。
//        2.StateDefine中设置好状态的定义方法。
//        3.在本类中设置输出的生存签名文件名。
//        4.调用自定义结构图中对应的方法。
//        5.设置状态概率。
//        6.run
//        ---------------------------------------------------
        String fileName = "SSOfDiagramOf2Types5Components";
//        float[][] stateProbability={{0.01f,0.01f,0.98f},{0.03f,0.03f,0.94f}};
        float[][] stateProbability={{0.01f,0.01f,0.01f,0.01f,0.01f,0.95f},{0.03f,0.03f,0.01f,0.01f,0.01f,0.91f}};
        Diagram diagram = new CustomDiagram(stateProbability).DiagramOf2Types5Components();
//        ----------------------------------------------------
        new OutputSSAndComputeReliability().outputSSAndComputeReliability(diagram,fileName,stateProbability);
    }


}
