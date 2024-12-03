package Project;

import java.util.*;

public class Gain {

    public static double remainder(List<String> dataset, List<List<String>> subsets) {
        double remainder = 0.0;
        int totalSize = dataset.size();

        // Tính trọng số của mỗi subset dựa trên kích thước của nó
        for (List<String> subset : subsets) {
            int noOfYes = Collections.frequency(subset, "Yes");
            int noOfNo = Collections.frequency(subset, "No");
            double subsetEntropy = Entropy.calculateEntropy(subset.size(), noOfYes, noOfNo);

            double subsetProbability = (double) subset.size() / totalSize;
            remainder += subsetProbability * subsetEntropy;
        }

        return remainder;
    }


    public static double informationGain(List<String> dataset, List<List<String>> subsets) {
        int noOfYes = Collections.frequency(dataset, "Yes");
        int noOfNo = Collections.frequency(dataset, "No");


        double totalEntropy =  Entropy.calculateEntropy(dataset.size(), noOfYes, noOfNo);
        double remainderValue = remainder(dataset, subsets);

        return totalEntropy - remainderValue;
    }



    public static void main(String[] args) {
        List<String> dataset = Arrays.asList("Yes", "No", "Yes", "No", "Yes");

        List<List<String>> subsets = new ArrayList<>();
        subsets.add(Arrays.asList("Yes", "Yes"));
        subsets.add(Arrays.asList("No", "No"));

        double ig = informationGain(dataset, subsets);
        System.out.println("Information Gain: " + ig);

    }
}

