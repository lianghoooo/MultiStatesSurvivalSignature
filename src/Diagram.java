import java.util.ArrayList;
import java.util.List;

public class Diagram {
    int[][] components;
    int componentsNum=0;
    int[] typesNum;
    int[][] diagram;
    int state;
    List<List<Integer>> existPaths;
    public Diagram(int[][] components,int state){
        this.components = components;
        for (int i=0;i<components.length;i++)
            for (int j=0;j<components[i].length;j++){
                this.componentsNum++;
            }
        this.typesNum = new int[components.length];
        this.diagram = new int[componentsNum+2][componentsNum+2];

        this.state = state;
        for (int i=0;i<components.length;i++){
            this.typesNum[i]=components[i].length;
        }

        existPaths = findExistPaths(this);
    }
    public void link(int start, int end){
        this.diagram[start][end]=1;
    }

    public List<List<Integer>> findExistPaths(Diagram diagram){
        int start=0;
        List<Integer> path = new ArrayList<>();
        List<List<Integer>> existPaths = new ArrayList<>();
        dfsForExistPaths(diagram,start,path,existPaths);
        return existPaths;
    }

    public void dfsForExistPaths(Diagram diagram,int start,List<Integer> path,List<List<Integer>> existPaths){
        if (start==diagram.diagram.length){
            existPaths.add(new ArrayList<>(path));
        }
        for(int i=1;i<diagram.diagram.length;i++){
            if (diagram.diagram[start][i]==1){
                path.add(diagram.diagram[start][i]);
                diagram.diagram[start][i]=-1;
                dfsForExistPaths(diagram,i+1,path,existPaths);
                path.remove(path.size()-1);
            }

        }
    }

    public static void main(String[] args) {
        int[][] components = new int[][] {{1,2,3},{4,5}};
        int state= 3;
        Diagram diagram = new Diagram(components,state);
        diagram.link(0,2);
        diagram.link(0,1);
        diagram.link(1,3);
        diagram.link(2,3);
        diagram.link(3,4);
        diagram.link(3,5);
        diagram.link(5,6);
        diagram.link(4,6);
        System.out.println("");
    }
}
