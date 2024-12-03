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


    public static double remaining(int total, int noOfYes) {

        return  0;
    }


    public static void main(String[] args) {
        double entropy = calculateEntropy(10, 6, 4);
        System.out.println(entropy);




    }

}
