package Project;

import java.util.*;

public class Gain {




    public static int informationGain(String[][] data, double entropy, String[] decision) {
        double gain = -1.0;
        int root = -1;

        // Duyệt qua các cột (trừ cột cuối cùng là 'decision')
        for (int i = 1; i < data[0].length - 1; i++) {
            String[] col = new String[data.length - 1];

            // Lấy dữ liệu của cột hiện tại
            for (int j = 1; j < data.length; j++) {
                col[j - 1] = data[j][i];
            }

            // Kiểm tra nếu cột này không có sự đa dạng
            Set<String> uniqueValues = new HashSet<>(Arrays.asList(col));
            if (uniqueValues.size() == 1) {  // Cột chỉ có 1 giá trị
                continue;  // Bỏ qua cột này vì không có sự phân chia
            }

            // Tính Information Gain
            double IG = entropy - remainder(col, decision);
            if (IG > gain) {
                gain = IG;
                root = i;
            }
        }
        return root;
    }




    public static double remainder(String[] col, String[] decision) {
        double remainder = 0.0;
        int size = col.length;

        List<String> item = new ArrayList<>();
        for (int i = 1; i < size; i++) {
            if (col[i] != null && !item.contains(col[i])) { // Kiểm tra null trước khi thêm vào danh sách
                item.add(col[i]);
            }
        }

        for (String i : item) {
            int noOfYes = 0;
            int noOfNo = 0;

            for (int j = 1; j < decision.length; j++) {
                if (col[j] != null) { // Kiểm tra null trước khi so sánh
                    if (col[j].equals(i) && decision[j].equalsIgnoreCase("yes")) {
                        noOfYes++;
                    } else if (col[j].equals(i) && decision[j].equalsIgnoreCase("no")) {
                        noOfNo++;
                    }
                }
            }

            int total = noOfYes + noOfNo;
            double entropyI = Entropy.calculateEntropy(total, noOfYes, noOfNo);

            remainder += ((double) total / (double) size) * entropyI;
        }

        return remainder;
    }







    public static void main(String[] args) {

    }
}

