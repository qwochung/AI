package Project;

import java.io.*;
import java.util.StringTokenizer;

public class ProcessingData {

    public static void  processingData(String fileName) throws FileNotFoundException {
        try {
            File file = new File(fileName);

            String line ="";
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            PrintWriter pw = new PrintWriter("./dataset/movies.csv");
            StringTokenizer st;



            while ((line = br.readLine()) != null) {

                 st = new StringTokenizer(line,"::");
                st.nextToken();
                pw.println(st.nextToken());
            }

            br.close();
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public static void main(String[] args) throws FileNotFoundException {
        processingData("./dataset/movies.dat");
    }
}
