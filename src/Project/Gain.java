package Project;

import java.util.*;

public class Gain {




    public static int informationGain(String[][] data, double entropy, String[] decision) {
        double gain = -1.0;
        int root = -1;
        for (int i = 0; i < data.length - 2; i++) {

            String[] col = new String[data.length];
            for (int j = 1; j < data.length -1 ; j++) {
                col[j-1] = data[j][i];
            }
            double IG = entropy - remainder(col, decision);
            if (IG > gain ) {
                gain = IG;
                root = i;
            }
        }
        return root ;
    }


    public static double remainder(String[] col , String[] decision) {
        double remainder = 0.0;
        int size = col.length;

        List<String> item = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (!item.contains(col[i])) {
                item.add(col[i]);
            }
        }


        for (String i : item) {
            int noOfYes = 0;
            int noOfNo = 0;

            for (int j = 0; j < decision.length-1; j++) {
                if (col[j].equals(i) && decision[j].equalsIgnoreCase("yes")) { noOfYes++; }
                if (col[j].equals(i) && decision[j].equalsIgnoreCase("no")) { noOfNo++; }
            }
            int total = noOfYes + noOfNo;
            double entropyI = Entropy.calculateEntropy(total, noOfYes, noOfNo);

            remainder += ((double)total / (double) size) * entropyI;
        }

        return remainder;
    }






    public static void main(String[] args) {

    }
}

