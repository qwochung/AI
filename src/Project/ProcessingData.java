package Project;

import java.io.*;
import java.security.PublicKey;
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
                st = new StringTokenizer(line, ",");
                if (Integer.parseInt(st.nextToken()) == userId) {
                    String s = "";
                    while (st.hasMoreTokens()) {

                        s += st.nextToken() + ",";
                    }
                    list.add(s);
                }

            }
            br.close();

            result = new String[list.size() + 1][list.getFirst().length()];
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


    public static int root(String[][] data) {
        String[] title = data[0];
        String[] decision = new String[data.length - 1];
        int root = -1;

        Map<String, Integer> counts = new HashMap<>();
        for (int j = 1; j < data.length; j++) {
            decision[j - 1] = data[j][data.length - 2];
        }

        double entropyJ = (Entropy.calculateEntropy(decision));
        root = Gain.informationGain(data, entropyJ, decision);
        System.out.println("Root: " + root);
        return root;
    }


    public static void divideTree(String[][] data, int root) {
        String[] title = data[0];
        String[] col = getCol(data, root);
        Set<String> set = new HashSet<>();
        set.addAll(Arrays.asList(col));

        for (String i : set) {
            String[][] subBranch = getSubBranch(data, i, root);

        }


    }

    private static String[][] getSubBranch(String[][] data, String key, int index) {
        String[][] result;
        int len = 0;
        for (int i = 1; i < data.length; i++) {
            if (data[i][index].equalsIgnoreCase(key)) {
                len++;
            }
        }
        // Tính size của sub branch
        result = new String[len+1][data[0].length -1];

        for (int i = 0; i < data[0].length -1; i++) {
            if (i!=index) {
                result[0][i] = data[0][i];
            }
        }

        // Fill data cho sub Branch
        int pos = 1;
        boolean isUpdate = false;
        for (int j = 0; j < data[0].length ; j++) {

            if (data[j][index].equalsIgnoreCase(key)) {

                for (int k = 0; k < data[0].length -1; k++) {
                    if (k != index) {
                        result[pos][k] = data[j][k];
                        isUpdate = true;
                    }
                }

            }

            if (isUpdate) {
                pos++;
                isUpdate = false;
            }
        }
        return result;
    }


    public static String[] getCol(String[][] data, int index) {
        String[] result = new String[data.length - 1];
        for (int i = 1; i < data.length; i++) {
            result[i - 1] = data[i][index];
        }

        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String[][] data = processingData("dataset/small_data.csv", 1);

        divideTree(data, root(data));
        divideTree(data, root(data));
    }
}
