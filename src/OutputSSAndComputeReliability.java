import java.util.List;

public class OutputSSAndComputeReliability {
    public void outputSSAndComputeReliability(Diagram diagram,String fileName,float[][] stateProbability){

        diagram.getExistPaths();
        diagram.getSurvivalSinature();
        ConditionalTable conditionalTable = new ConditionalTable(diagram);
        new Output().output(diagram.survivalSignature,conditionalTable,fileName);
        ComputeReliability computeReliability = new ComputeReliability(diagram,stateProbability);
        List<Float> reliability = computeReliability.reliability;
        float sum=0;
        for (int i=0;i<reliability.size();i++){
            System.out.println("state "+i+" : "+reliability.get(i));
            sum+=i;
        }
//        System.out.println(sum);
    }
}
