import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Output {
    public void output(List<float[]> survivalSignature,ConditionalTable conditionalTable,String fileName){
        File file = new File(fileName);
        try {
            FileWriter out = new FileWriter(file);
            String line = System.getProperty("line.separator");
            String lineString = "";

            /*
            首行
             */

            lineString ="tips:state-0-Failure,state-1-Mediate,state-2-Work,last column is the number of combinations of the condition";
            out.write(lineString);
            out.write(line);
            /*
            循环打印部分
             */
            for (List<Integer> subConditionalTable : conditionalTable.table) {
                lineString = "";
                for (Integer conditionNum : subConditionalTable)
                    lineString += conditionNum + " ";
                for (float signatureNum : survivalSignature.get(conditionalTable.table.indexOf(subConditionalTable)))
                    lineString += signatureNum + " ";
                lineString.trim();
                out.write(lineString);
                out.write(line);//该语句实现换行
            }
            out.close();
        }
        catch(IOException e) {
            System.out.println("Error"+e);
        }
    }

    public void output2DList(List<List<Integer>> list,String fileName){
        File file = new File(fileName);
        try {
            FileWriter out = new FileWriter(file);
            String line = System.getProperty("line.separator");
            for (List<Integer> sublist : list) {
                out.write( sublist.toString());
                out.write(line);//该语句实现换行
            }
            out.close();
        }
        catch(IOException e) {
            System.out.println("Error"+e);
        }
    }
}
