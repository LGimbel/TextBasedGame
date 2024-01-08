import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello world!");


    }
}
enum PotionLevel {
    MINI,LESSER,NORMAL,GREATER,GRAND,OMEGA
}
class Item{
    private String itemName;
    public Item(String itemName){
        this.itemName = itemName;
    }
    public String getItemName(){
        return itemName;
    }

}
class Armour extends Item{
    private int defense;
    private float dodgeChance;
    public Armour(String itemName,int defense,float dodgeChance){
        super(itemName);
        this.defense = defense;
        this.dodgeChance = dodgeChance;
    }
}
class HealthPotion extends Item{
    private PotionLevel healingLevel;

    // levels small,lesser,normal,greater,grand,Omega.

    public HealthPotion(String itemName, PotionLevel level){
        super(itemName);
        this.healingLevel = level;
    }

}

class Weapon extends Item {

   private int weaponBaseDamage;
   private float weaponCriticalHitChance;
   private double weaponCriticalDamageMulti;
   private float weaponRarity;
   private boolean isConsumable;
    public Weapon(String itemName, int itemBaseDamage, float itemCritChance, double itemCritDamageMulti, float itemRarity){
        super(itemName);
        this.weaponBaseDamage = itemBaseDamage;
        this.weaponCriticalHitChance = itemCritChance;
        this.weaponCriticalDamageMulti = itemCritDamageMulti;
        this.weaponRarity = itemRarity;
        this.isConsumable = false;
    }
    //region getter functions
    public int getWeaponBaseDamage(){
        return this.weaponBaseDamage;
    }
    public float getWeaponCriticalHitChance(){
        return this.weaponCriticalHitChance;
    }
    public double getWeaponCriticalDamageMulti(){
        return this.weaponCriticalDamageMulti;
    }
    public float getItemRarity(){
        return this.weaponRarity;
    }
}

class Player{
   private int maxHealth;
   private int baseDamage;
   private double currentDamageMulti;
   private int currentHealth;
   private int entitiesDefeated;
   private double playerLevel;
    // TODO add equipped Weapon and add sword class and maybe even an armour class and all related functions.
   private HashMap<String,Integer> inventory = new HashMap<String,Integer>();




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
    public int getMaxHealth(){
        return maxHealth;
    }
    public int getBaseDamage() {
        return baseDamage;
    }
    public double getCurrentDamageMulti(){
        return currentDamageMulti;
    }
    public int getCurrentHealth(){
        return currentHealth;
    }
    public int getEntitiesDefeated(){
        return entitiesDefeated;
    }
    public double getPlayerLevel(){
        return playerLevel;
    }
    public HashMap<String,Integer> getInventory(){
        return inventory;
    }
    //endregion
    //region Setter functions
    public void setMaxHealth(int newMaxHealth){
        this.maxHealth = newMaxHealth;
    }
    public void setBaseDamage(int newBaseDamage){
        this.baseDamage = newBaseDamage;
    }
    public void setCurrentDamageMulti(double newDamageMulti){
        this.currentDamageMulti = newDamageMulti;
    }
    public void setCurrentHealth(int newHealth) {
        this.currentHealth = newHealth;
        // this function is to be used only for directly setting
        // health such as level ups and other occasions normal healing and damage will be done through the heal and damage functions.
    }
    public void setEntitiesDefeated(int newEntitiesDefeated){
        this.entitiesDefeated = newEntitiesDefeated;
        // not to be used for increment after battle success instead for setting directly if needed mostly likely to be depreciated.
    }
    public void setPlayerLevel(int newPlayerLevel){
        // not to be used for directly incrementing the player level after successful battle but instead for direct change of the players
        // level.
        this.playerLevel = newPlayerLevel;
    }
    public void setInventory(HashMap<String,Integer> newInventory){
        //NOTE FOR TESTING PURPOSES ONLY IN GAME INTERACTIONS WILL USE THE ADD/REMOVE FROM INVENTORY FUNCTIONS.
        this.inventory = newInventory;
    }
    //endregion
    //region player stat incremental change functions.
    public void damagePlayer(int damageTaken){
        // use this function to deal damage to the player as it will also check if the player is dead after while set health will not
        this.currentHealth -= damageTaken;
        if(isPlayerDead()){
            //TODO call game over function or otherwise deal with player death.
        }
    }
    public void healPlayer(int healthGain){
        int maxHealth = getMaxHealth();
        int mathCurrentHealth = getCurrentHealth();
        if ((mathCurrentHealth + healthGain) >= maxHealth){
            this.currentHealth = maxHealth;
        }
        else {
            this.currentHealth += healthGain;
        }
    }
    public void incrementEntitiesDefeated(){
        this.entitiesDefeated++;
    }
    public void addToInventory(String item, int quantity){
        if (inventory.containsKey(item)){
           int currentAmount = inventory.get(item);
           int newAmount = currentAmount + quantity;
           inventory.put(item,newAmount);
        }
        else {
            inventory.put(item,quantity);
        }

    }
    public void consumeItem(String item){
        int currentItemQuantity = inventory.get(item);
        int newItemQuantity = (currentItemQuantity - 1);
        if(newItemQuantity > 1){
            inventory.put(item,newItemQuantity);
            //player consumes an Weapon.
        }
        else if(newItemQuantity == 0){
            inventory.remove(item);
            //player uses the last of an Weapon.

        }
        else{
            System.out.println("Uh oh it looks like you tried to remove an Weapon not in the players inventory something ain't right");
            // this would most likely be caused if a consume Weapon was called without first checking has Weapon.
        }

    }
    // TODO add equip function for equipping equipment
    // endregion
    //region player checks
    public boolean isPlayerDead(){
        if (currentHealth <= 0){
            return true;
        }
        else {
            return false;
        }
    }
    public boolean playerHasItem(String item){
        return inventory.containsKey(item)? true:false;
    }



}