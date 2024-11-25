package Project;

public class Entropy {

    public static double Entropy(int total, int noOfYes, int noOfNo) {
        double entropy;
        double yes = (double) noOfYes /total;
        double no = (double) noOfNo /total;
        entropy = -( (yes) * (Math.log(yes) / Math.log(2)) )   - ( (no) * (Math.log(no) / Math.log(2)) )  ;

        return entropy;
    }


    public static void main(String[] args) {
        double entropy = Entropy(10, 6, 4);
        System.out.println(entropy);
    }

}
