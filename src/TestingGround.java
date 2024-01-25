import java.util.*;

public class TestingGround {



    public static void main(String[] args) {

        int iterations = 1000;
        int maxInput = 40;
        double scaleIncrement = 0.5;

        for (double input = 0; input <= maxInput; input += scaleIncrement) {
            double[] results = testFunction(input, iterations);
            double average = calculateAverage(results);
            double min = findMin(results);
            double max = findMax(results);

            System.out.println("Input: " + input +
                    ", Average: " + average +
                    ", Min: " + min +
                    ", Max: " + max);
        }
    }

    private static double[] testFunction(double input, int iterations) {
        double[] results = new double[iterations];
        Arrays.fill(results, functionToTest(input));
        return results;
    }

    private static double calculateAverage(double[] array) {
        return Arrays.stream(array).average().orElse(0);
    }

    private static double findMin(double[] array) {
        return Arrays.stream(array).min().orElse(0);
    }

    private static double findMax(double[] array) {
        return Arrays.stream(array).max().orElse(0);
    }

    // Replace this with the actual function you want to test
    private static double functionToTest(double input) {
        return generateOutput(input);
    }
    public static int generateOutput(double input) {
        double mean = calculateMean(input);
        double standardDeviation = mean * 1.5;

        Random random = new Random();

        // Generate a random value with the specified mean and standard deviation
        double randomValue = random.nextGaussian() * standardDeviation + mean;

        // Ensure the generated value is positive and substantial
        int output = Math.max(1, (int) Math.round(randomValue));

        return output;
    }

    public static double calculateMean(double input) {
        // Define the desired mean values for specific inputs
        double[] inputValues = {0.5, 1, 3, 10, 23, 30};
        int[] meanValues = {199, 250, 1320, 197290, 9744023, 100000000};

        // Find the nearest input values
        double lowerInput = 0.5;
        double upperInput = 1;

        for (double value : inputValues) {
            if (input >= lowerInput && input <= upperInput) {
                int lowerMean = meanValues[(int) lowerInput];
                int upperMean = meanValues[(int) upperInput];

                // Interpolate the mean value based on the input
                return lowerMean + (upperMean - lowerMean) * (input - lowerInput) / (upperInput - lowerInput);
            }

            lowerInput = value;
            upperInput = value;
        }

        return 0; // Default case
    }






        }



//            int totalTrash = 0;
//            int totalCommon = 0;
//            int totalUncommon = 0;
//            int totalRare = 0;
//            int totalLegendary = 0;
//            int totalUnique = 0;
//            int totalOther = 0;
//            int totalIterations = 0;
//            int max = 0;
//            int min = 100000;
//            for( int i = 0; i < 1000000;i++){
//               ; // bound is exclusive hence why bound = 101 and origin is inclusive so origin = 1 to conserve a percent scale
//
//    }
//
//            double averageTrash = (double) totalTrash / totalIterations;
//            double averageCommon = (double) totalCommon / totalIterations;
//            double averageUncommon = (double) totalUncommon / totalIterations;
//            double averageRare = (double) totalRare / totalIterations;
//            double averageLegendary = (double) totalLegendary / totalIterations;
//            double averageUnique = (double) totalUnique / totalIterations;
//            double averageOthers = (double) totalOther / totalIterations;
//            System.out.println(totalIterations);
//            System.out.println(totalOther);








//            int average = total/100000;
//            System.out.println("Average: "+ average +" maximum: "+ max + " Minimum: " + min + " at level: " + playerLevel);
//            System.out.println(max);







//int randomMod1 = 10 + random.nextInt((int)(0.9*playerLevel),(int)(2*playerLevel));
////refine as health will normally be higher that desired
//int refinedHealth1 = (int) (randomMod1 - ((random.nextInt((int)(0.5*playerLevel),(int)(4*playerLevel)))% (playerLevel/random.nextInt(1,6))));
//            System.out.println(refinedHealth1+"With player level at "+ playerLevel);








/*
how i get the health
 x = random number that is between 2y and 8y
 z = (x - random number between 0.5y 8y)








 */












