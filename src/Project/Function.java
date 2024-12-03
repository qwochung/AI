package Project;

public class Function {

    public static double entropy(int noOfYes, int noOfNo) {
        double entropy;
        int total = noOfYes+ noOfNo;
        double yes = (double) noOfYes /total;
        double no = (double) noOfNo /total;
        entropy = -( (yes) * (Math.log(yes) / Math.log(2)) )   - ( (no) * (Math.log(no) / Math.log(2)) )  ;

        return entropy;
    }


    public static double remaining(int total, int noOfYes) {

        return  0;
    }


    public static void main(String[] args) {
        double entropy = entropy( 6, 4);
        System.out.println(entropy);




    }

}
