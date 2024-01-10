import java.util.*;
public class TestingGround {
    Random random = new Random();
    public int GenerateEntityDefense (int killCount){
        int result;
        if(killCount < 15){
            result = 0;
        } else if (killCount < 20) {
            result = random.nextInt(1, 2);
        } else if (killCount < 25) {
            result = random.nextInt(1, 4);

        } else if (killCount < 30) {
            result = random.nextInt(2, 6);
        } else if (killCount < 40) {
            result = random.nextInt(4, 8);
        }
        else {
            result = random.nextInt(0, 17);
        }
        return result;
    }


    public static void main(String[] args) {
        Random random = new Random();
        long startime = System.currentTimeMillis();

        for(int killCount = 1; killCount < 70; killCount ++){
            int total = 0;
            int max = 0;
            int min = 100000;
            for (int i = 0;i !=100000;i++) {
                int result;
                if(killCount < 15){
                    result = 0;
                } else if (killCount < 20) {
                    result = random.nextInt(1, 2);
                } else if (killCount < 25) {
                    result = random.nextInt(1, 4);

                } else if (killCount < 30) {
                    result = random.nextInt(2, 6);
                } else if (killCount < 40) {
                    result = random.nextInt(4, 8);
                }
                else {
                    result = random.nextInt(5, 17);
                }
                max = result > max ? result : max;
                min = result < min ? result : min;
                total += result;

            }
            int average = total/100000;
//            System.out.println("Average: "+ average +" maximum: "+ max + " Minimum: " + min + " at level: " + playerLevel);
            System.out.println(max);
        }
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startime;
        System.out.println(elapsedTime + " Milliseconds");


    }

}
//int randomMod1 = 10 + random.nextInt((int)(0.9*playerLevel),(int)(2*playerLevel));
////refine as health will normally be higher that desired
//int refinedHealth1 = (int) (randomMod1 - ((random.nextInt((int)(0.5*playerLevel),(int)(4*playerLevel)))% (playerLevel/random.nextInt(1,6))));
//            System.out.println(refinedHealth1+"With player level at "+ playerLevel);








/*
how i get the health
 x = random number that is between 2y and 8y
 z = (x - random number between 0.5y 8y)








 */












