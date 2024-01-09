import java.util.*;

public class Main {

    public static void main(String[] args) {
        Weapon fists = new Weapon("Fists",2,0,1,1);
        Armour leather = new Armour("Leather Apron",0,0);
        HashMap<Item,Integer> playerInv = new HashMap<>();
        Player player = new Player(58, 58,0,1, fists,leather, playerInv);
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
    private boolean isConsumable;
    public Armour(String itemName,int defense,float dodgeChance){
        super(itemName);
        this.defense = defense;
        this.dodgeChance = dodgeChance;
        this.isConsumable = false;
    }
}
class HealthPotion extends Item{
    private PotionLevel healingLevel;
    private boolean isConsumable;

    // levels small,lesser,normal,greater,grand,Omega.

    public HealthPotion(String itemName, PotionLevel level){
        super(itemName);
        this.healingLevel = level;
        this.isConsumable = false;
    }
    public PotionLevel getHealingLevel(){
        return healingLevel;
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
   private int currentHealth;
   private int entitiesDefeated;
   private double playerLevel;
   private Weapon equippedWeapon;
   private Armour equippedArmour;
   private HashMap<Item,Integer> inventory = new HashMap<>();
   //TODO ensure that the player base damage is not used if the player has a weapon equipped




    public Player (int maxHealth,int currentHealth,int entitiesDefeated,double playerLevel,Weapon equippedWeapon,Armour equippedArmour,HashMap<Item,Integer> inventory){
        this.maxHealth = maxHealth;
        this.currentHealth = currentHealth;
        this.entitiesDefeated = entitiesDefeated;
        this.playerLevel = playerLevel;
        this.inventory = inventory;
        this.equippedWeapon = equippedWeapon;
        this.equippedArmour = equippedArmour;


    }
    //region getter functions
    public int getMaxHealth(){
        return maxHealth;
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
    public HashMap<Item,Integer> getInventory(){
        return inventory;
    }
    //endregion
    //region Setter functions
    public void setMaxHealth(int newMaxHealth){
        this.maxHealth = newMaxHealth;
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
    public void setInventory(HashMap<Item,Integer> newInventory){
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
    public void addToInventory(Item item, int quantity){
        if (inventory.containsKey(item)){
           int currentAmount = inventory.get(item);
           int newAmount = currentAmount + quantity;
           inventory.put(item,newAmount);
        }
        else {
            inventory.put(item,quantity);
        }

    }
    public void consumeItem(Item item){
        int currentItemQuantity = inventory.get(item);
        int newItemQuantity = (currentItemQuantity - 1);
        if(newItemQuantity > 1){
            inventory.put(item,newItemQuantity);
            //player consumes an item.
        }
        else if(newItemQuantity == 0){
            inventory.remove(item);
            //player uses the last of an Item.

        }
        else{
            System.out.println("Uh oh it looks like you tried to remove an Weapon not in the players inventory something ain't right");
            // this would most likely be caused if a consume Weapon was called without first checking has Weapon.
        }

    }
    public void EquipWeapon(Weapon newWeapon){
        if(inventory.containsKey(equippedWeapon)){
            inventory.put(equippedWeapon,(inventory.get(equippedWeapon)+1));
        }
        else{
            inventory.put(equippedWeapon,1);

        }
        equippedWeapon = newWeapon;

    }
    public void EquipArmour(Armour newArmour) {
        if (inventory.containsKey(equippedArmour)) {
            inventory.put(equippedArmour, (inventory.get(equippedArmour) + 1));
        } else {
            inventory.put(equippedArmour, 1);

        }
        equippedArmour = newArmour;
    }
    public void ConsumeHealthPotion(HealthPotion potion){
        int currentItemQuantity = inventory.get(potion);
        int newItemQuantity = (currentItemQuantity - 1);
        if(newItemQuantity > 1){
            inventory.put(potion,newItemQuantity);}
        //TODO find final determinate values for health potion healing current values are just placeholders.
            healPlayer(
        switch (potion.getHealingLevel()){
            case MINI -> 2;
            case LESSER -> 5;
            case NORMAL -> 7;
            case GREATER -> 10;
            case GRAND -> 15;
            case OMEGA -> 30;
        }
        );
    }
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