import java.util.*;
public class TestingGround {



    public static void main(String[] args) {
        Random random = new Random();
        long startime = System.currentTimeMillis();

        for(double playerLevel = 1; playerLevel < 40; playerLevel += 0.5){
            int total = 0;
            int max = 0;
            int min = 100000;
            for (int i = 0;i !=100000;i++) {
                int startDamage = 2 + (int)(playerLevel/2);
                boolean modifier = random.nextBoolean();
                int damageAdjust = (int) ((playerLevel*10)/(15));
                int finalDamage = modifier ? startDamage + damageAdjust: (int) ((startDamage - damageAdjust) + playerLevel/1.3);
                total+= finalDamage;
                max = finalDamage > max ? finalDamage : max;
                min = finalDamage < min ? finalDamage : min;

            }
            int average = total/100000;
//            System.out.println("Average: "+ average +" maximum: "+ max + " Minimum: " + min + " at level: " + playerLevel);
            System.out.println(min);
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
