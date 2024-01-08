import jdk.jfr.Label;

import java.awt.font.TextHitInfo;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello world!");


    }
}
class Player{
    int maxHealth;
    int baseDamage;
    double currentDamageMulti;
    int currentHealth;
    int entitiesDefeated;
    double playerLevel;
    // TODO add equipped item and add sword class and maybe even an armour class and all related functions.
    HashMap<String,Integer> inventory = new HashMap<String,Integer>();




    public Player (int maxHealth,int baseDamage,double currentDamageMulti,int currentHealth,int entitiesDefeated,double playerLevel,HashMap<String,Integer> inventory){
        this.maxHealth = maxHealth;
        this.baseDamage = baseDamage;
        this.currentDamageMulti = currentDamageMulti;
        this.currentHealth = currentHealth;
        this.entitiesDefeated = entitiesDefeated;
        this.playerLevel = playerLevel;
        this.inventory = inventory;

    }
    //region getter functions
    private int getMaxHealth(){
        return maxHealth;
    }
    private int getBaseDamage() {
        return baseDamage;
    }
    private double getCurrentDamageMulti(){
        return currentDamageMulti;
    }
    private int getCurrentHealth(){
        return currentHealth;
    }
    private int getEntitiesDefeated(){
        return entitiesDefeated;
    }
    private double getPlayerLevel(){
        return playerLevel;
    }
    private HashMap<String,Integer> getInventory(){
        return inventory;
    }
    //endregion
    //region Setter functions
    private void setMaxHealth(int newMaxHealth){
        this.maxHealth = newMaxHealth;
    }
    private void setBaseDamage(int newBaseDamage){
        this.baseDamage = newBaseDamage;
    }
    private void setCurrentDamageMulti(double newDamageMulti){
        this.currentDamageMulti = newDamageMulti;
    }
    private void setCurrentHealth(int newHealth) {
        this.currentHealth = newHealth;
        // this function is to be used only for directly setting
        // health such as level ups and other occasions normal healing and damage will be done through the heal and damage functions.
    }
    private void setEntitiesDefeated(int newEntitiesDefeated){
        this.entitiesDefeated = newEntitiesDefeated;
        // not to be used for increment after battle success instead for setting directly if needed mostly likely to be depreciated.
    }
    private void setPlayerLevel(int newPlayerLevel){
        // not to be used for directly incrementing the player level after successful battle but instead for direct change of the players
        // level.
        this.playerLevel = newPlayerLevel;
    }
    private void setInventory(HashMap<String,Integer> newInventory){
        //NOTE FOR TESTING PURPOSES ONLY IN GAME INTERACTIONS WILL USE THE ADD/REMOVE FROM INVENTORY FUNCTIONS.
        this.inventory = newInventory;
    }
    //endregion
    //region player stat incremental change functions.
    private void damagePlayer(int damageTaken){
        // use this function to deal damage to the player as it will also check if the player is dead after while set health will not
        this.currentHealth -= damageTaken;
        // TODO call is player dead function.
    }
    private void healPlayer(int healthGain){
        int maxHealth = getMaxHealth();
        int mathCurrentHealth = getCurrentHealth();
        if ((mathCurrentHealth + healthGain) >= maxHealth){
            this.currentHealth = maxHealth;
        }
        else {
            this.currentHealth += healthGain;
        }
    }
    private void incrementEntitiesDefeated(){
        this.entitiesDefeated++;
    }
    private void addToInventory(String item,int quantity){
        if (inventory.containsKey(item)){
           int currentAmount = inventory.get(item);
           int newAmount = currentAmount + quantity;
           inventory.put(item,newAmount);
        }
        else {
            inventory.put(item,quantity);
        }

    }
    private void consumeItem(String item){
        int currentItemQuantity = inventory.get(item);
        int newItemQuantity = (currentItemQuantity - 1);
        if(newItemQuantity > 1){
            inventory.put(item,newItemQuantity);
            //player consumes an item.
        }
        else if(newItemQuantity == 0){
            inventory.remove(item);
            //player uses the last of an item.

        }
        else{
            System.out.println("Uh oh it looks like you tried to remove an item not in the players inventory something ain't right");
            // this would most likely be caused if a consume item was called without first checking has item.
        }

    }

    // TODO add equip function for equipping equipment



}