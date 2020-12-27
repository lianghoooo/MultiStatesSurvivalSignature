public class StateDefine {
    public int stateDefine(float max,Diagram diagram){
        int state=Integer.MAX_VALUE;
        if (max==0)
            return 0;

        if (max==1)
            return diagram.state-1;

        for (int i=0;i<diagram.state-2;i++){
            float lower = (float)(i)/(float)(diagram.state-2);
            float upper = (float)(i+1)/(float)(diagram.state-2);
            if (max>=lower&&max<upper){
                return i+1;
            }

        }
        System.out.println("error in stateDefine!!!");
        return state;
    }
}
