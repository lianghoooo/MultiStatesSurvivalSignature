public class CustomDiagram {
    float[][] stateProbability;
    public CustomDiagram(float[][] stateProbability){
        this.stateProbability = stateProbability;
    }
    public Diagram DiagramOf2Types5Components() {
        int[][] components = new int[][] {{1,3,4},{2,5}};
        Diagram diagram = new Diagram(components,stateProbability);
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
        return diagram;
    }

    public Diagram DiagramOf2Types2Components() {
        int[][] components = new int[][] {{1},{2}};
        int state= 3;
        Diagram diagram = new Diagram(components,stateProbability);
        diagram.link(0,1);
        diagram.link(1,2);
        diagram.link(2,3);
        return diagram;
    }
}
