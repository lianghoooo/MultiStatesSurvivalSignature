public class Solution {
    public static void main(String[] args) {
        new Solution().DiagramOf2Types2Components();
    }
    public void DiagramOf2Types5Components() {
        int[][] components = new int[][] {{1,3,4},{2,5}};
        int state= 3;
        String fileName = "SSOfDiagramOf2Types5Components";
        Diagram diagram = new Diagram(components,state);
        diagram.link(0,2);
        diagram.link(0,1);
        diagram.link(1,3);
        diagram.link(1,4);
        diagram.link(2,3);
        diagram.link(2,5);
        diagram.link(3,4);
        diagram.link(3,5);
        diagram.link(5,6);
        diagram.link(4,6);
        diagram.getExistPaths();
        diagram.getSurvivalSinature();
        ConditionalTable conditionalTable = new ConditionalTable(diagram);
        new Output().output(diagram.survivalSignature,conditionalTable,fileName);
        System.out.println("");
    }

    public void DiagramOf2Types2Components() {
        String fileName = "SSOfDiagramOf2Types2Components";
        int[][] components = new int[][] {{1},{2}};
        int state= 3;
        Diagram diagram = new Diagram(components,state);
        diagram.link(0,1);
        diagram.link(1,2);
        diagram.link(2,3);
        diagram.getExistPaths();
        diagram.getSurvivalSinature();
        ConditionalTable conditionalTable = new ConditionalTable(diagram);
        new Output().output(diagram.survivalSignature,conditionalTable,fileName);
        System.out.println("");
    }
}
