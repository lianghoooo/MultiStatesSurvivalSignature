import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Output {
    void output(List<float[]> survivalSignature,ConditionalTable conditionalTable,String fileName){
        File file = new File(fileName);
        try {
            FileWriter out = new FileWriter(file);
            String line = System.getProperty("line.separator");
            for (List<Integer> subConditionalTable : conditionalTable.table) {
                String lineString = "";
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
}
