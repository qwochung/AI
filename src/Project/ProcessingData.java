package Project;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ProcessingData {


    public static String[][] processingData(String fileName, int userId) throws FileNotFoundException {
        try {
            File file = new File(fileName);
            String[][] result;
            String[] recommend;
            String line = "";
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            List<String> list = new ArrayList<String>();
            AtomicInteger counter = new AtomicInteger();
            StringTokenizer st;

            String[] title = br.readLine().split(",");

            while ((line = br.readLine()) != null) {
                st = new StringTokenizer(line,",");
                if (Integer.parseInt(st.nextToken()) == userId) {
                    String s ="";
                    while (st.hasMoreTokens()) {

                        s += st.nextToken() + ",";
                    }
                    list.add(s);
                }

            }
            br.close();

            result = new String[list.size()+1] [list.getFirst().length()];
            result[counter.getAndIncrement()] = title;


            list.forEach(l -> {
                String[] temp = l.split(",");
                result[counter.getAndIncrement()] = temp;
            });
            return result;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




    public static void tree(String [][] data){
        String[] title = data[0];
        String[] decision = data[data.length-1];



        Map<String, Integer> counts = new HashMap<>();
        for (int i = 0; i < data.length - 1; i++) {

            String[] col = new String[data.length];
            for (int j = 1; j < data.length -1 ; j++) {
                col[j-1] = data[j][i];
                System.out.println(data[j][i] + "----" + data[j][data.length-2]);
                decision[j-1] = data[j][data.length-2];
            }

            double entropyJ =  (Entropy.calculateEntropy(decision));

//            int root = Gain.informationGain(data, entropyJ, decision);
            System.out.println(Gain.remainder(col, decision));

            break;




        }



        return;
    }














    public static void main(String[] args) throws FileNotFoundException {
        String [][]data= processingData("dataset/small_data.csv",1);
        tree(data);
    }
}
