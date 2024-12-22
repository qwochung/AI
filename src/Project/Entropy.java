package Project;

public class Entropy {

    public static double calculateEntropy(int total, int noOfYes, int noOfNo) {
        if (total == 0) {
            return 0;
        }
        double entropy = 0.0;

        double yes = (double) noOfYes / total;
        double no = (double) noOfNo / total;


        if (yes > 0) {
            entropy -= yes * (Math.log(yes) / Math.log(2));
        }
        if (no > 0) {
            entropy -= no * (Math.log(no) / Math.log(2));
        }

        return entropy;
    }


    public static double calculateEntropy(String[] decision) {
        int noOfYes = 0;
        int noOfNo = 0;

        for (int i = 0; i < decision.length; i++) {
            if (decision[i] != null) { // Kiểm tra null
                if (decision[i].equalsIgnoreCase("yes")) {
                    noOfYes++;
                } else if (decision[i].equalsIgnoreCase("no")) {
                    noOfNo++;
                }
            }
        }

        double total = noOfYes + noOfNo;
        if (total == 0) {
            return 0.0; // Nếu tổng số là 0, entropy bằng 0
        }

        double entropy = 0.0;
        double yes = (double) noOfYes / total;
        double no = (double) noOfNo / total;

        if (yes > 0) {
            entropy -= yes * (Math.log(yes) / Math.log(2));
        }
        if (no > 0) {
            entropy -= no * (Math.log(no) / Math.log(2));
        }

        return entropy;
    }






    public static void main(String[] args) {
//        double entropy = calculateEntropy(10, 6, 4);
//        System.out.println(entropy);




    }

}
