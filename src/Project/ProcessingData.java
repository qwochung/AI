package Project;

import java.io.*;
import java.security.PublicKey;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ProcessingData {


    public static String[][] processingData(String fileName) throws FileNotFoundException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            List<String[]> list = new ArrayList<>();
            String line;

            // Đọc tiêu đề (header)
            String[] title = br.readLine().split(",");
            list.add(title);

            // Đọc dữ liệu
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                list.add(tokens); // Thêm tất cả các hàng vào danh sách
            }

            return list.toArray(new String[0][]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



//    public static int root(String[][] data) {
//        String[] title = data[0];
//        String[] decision = new String[data.length - 1];
//        int root = -1;
//
//        // Cột quyết định (ở cột cuối cùng)
//        int decisionColumnIndex = data[0].length - 1;
//
//        for (int j = 1; j < data.length; j++) {
//            decision[j - 1] = data[j][decisionColumnIndex];
//        }
//
//        double entropyJ = Entropy.calculateEntropy(decision);
//        root = Gain.informationGain(data, entropyJ, decision);
//        System.out.println("Root: " + root);
//        return root;
//    }

    public static int root(String[][] data) {

        if (data == null || data.length <= 1 || data[0].length == 0) {
            System.out.println("Dữ liệu không hợp lệ hoặc rỗng");
            return -1;  // Trả về -1 nếu dữ liệu không hợp lệ
        }

        String[] title = data[0];
        String[] decision = new String[data.length - 1];
        int root = -1;

        int decisionColumnIndex = data[0].length - 1;
        for (int j = 1; j < data.length; j++) {
            decision[j - 1] = data[j][decisionColumnIndex];
        }

        // In ra các giá trị decision và entropy để kiểm tra
        System.out.println("Decision values: " + Arrays.toString(decision));

        double entropyJ = Entropy.calculateEntropy(decision);
        System.out.println("Calculated entropy: " + entropyJ);

        root = Gain.informationGain(data, entropyJ, decision);
        System.out.println("Current root (calculated): " + root);
        return root;
    }


    public static void divideTree(String[][] data, int root) {

        if (data == null || data.length <= 1 || root == -1) {
            return;  // Nếu dữ liệu không hợp lệ, dừng lại
        }

        String[] title = data[0];
        String[] col = getCol(data, root);
        Set<String> set = new HashSet<>(Arrays.asList(col));

        // Nếu không có sự phân chia, dừng lại
        if (set.size() <= 1) {
            return;
        }

        for (String key : set) {
            String[][] subBranch = getSubBranch(data, key, root);

            int newRoot = root(subBranch);


            if (newRoot == -1 || subBranch.length <= 1) {
                System.out.println("No further division possible for key: " + key);
                continue;
            }

            System.out.println("Sub-branch root: " + newRoot);
            divideTree(subBranch, newRoot);
        }
    }



    private static String[][] getSubBranch(String[][] data, String key, int index) {
        String[][] result;
        int len = 0;

        for (int i = 1; i < data.length; i++) {
            if (data[i][index] != null && data[i][index].equalsIgnoreCase(key)) {
                len++;
            }
        }

        result = new String[len + 1][data[0].length - 1];


        for (int i = 0; i < data[0].length - 1; i++) {
            if (i != index) {
                result[0][i] = data[0][i];
            }
        }


        int pos = 1;
        for (int j = 1; j < data.length; j++) {
            if (data[j][index] != null && data[j][index].equalsIgnoreCase(key)) {
                for (int k = 0; k < data[0].length - 1; k++) {
                    if (k != index) {
                        result[pos][k] = data[j][k];
                    }
                }
                pos++;
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
        String[][] data = processingData("AI/dataset/small_data.csv");

        divideTree(data, root(data));

    }
}