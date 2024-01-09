import java.util.*;

public class Main {

    public static void main(String[] args) {
        //TODO add game initilizer thingymcdo
        Weapon fists = new Weapon("Fists",2,0,1,ItemRarity.TRASH);
        Armour leather = new Armour("Leather Apron",0,0,ItemRarity.TRASH);
        HashMap<Item,Integer> playerInv = new HashMap<>();
        Player player = new Player(58, 58,0,1, fists,leather, playerInv);
        System.out.println("Hello world!");


    }
}
//region enumerations
enum ItemRarity{
    TRASH,COMMON,UNCOMMON,RARE,LEGENDARY,UNIQUE
}
enum PotionLevel {
    MINI,LESSER,NORMAL,GREATER,GRAND,OMEGA
}
//endregion
class WorldManager {
    //GOD to the world an instance should be made on game initialization.
    private final Random random = new Random();


    WorldManager() {
    }
    public void GenerateNewEntity(Player player){
         double playerLevel = player.getPlayerLevel();
         //region entity specs to be generated
         int entityHealth;
         int entityBaseDamage;
         // for now critical hit chance will be locked at 5 percent for all
         int entityCriticalHitChance = 5;
         // entity's will begin to get defense after 15 killed
        // it will compound slowly based on kills not on player level
         int entityDefense;
         //
         boolean isBoss;
         boolean isEscapable;
         boolean isDead;
         double entityExperience;
         double entityCriticalMultiplier;
         String entityName;
         //TODO add loot drop to entity and make it predetermined here for each entity
         //endregion
    }
    //region entity generation algorithms
    public int GenerateEntityHealth(double playerLevel){
        int randomMod1 = 10 + (random.nextInt((int) (2 * playerLevel), (int) (8 * playerLevel)));
        //refine as health will normally be higher that desired
        int refinedHealth = (int) (randomMod1 - ((random.nextInt((int) (0.5 * playerLevel), (int) (8 * playerLevel))) % (playerLevel / random.nextInt(1, 6))));
        return refinedHealth;
    }
    public int GenerateEntityBaseDamage(double playerLevel) {
        int startDamage = 2 + (int)(playerLevel/2);
        boolean modifier = random.nextBoolean();
        int damageAdjust = (int) ((playerLevel*10)/(15));
        int finalDamage = modifier ? startDamage + damageAdjust: (int) ((startDamage - damageAdjust) + playerLevel/1.3);
        return  finalDamage;
    }
    public int GenerateEntityDefense(int killCount){
       if(killCount < 15){
           return 0;
       } else if (killCount < 20) {
           return random.nextInt(1,2);
       } else if (killCount < ) {

       }

    }

}

class Entity {
    private int entityHealth;
    private final int entityBaseDamage;
    private final int entityCriticalHitChance;
    private final double entityCriticalMultiplier;
    private final int entityDefense;
    private final boolean isBoss;
    private final boolean isEscapable;
    private boolean isDead;
    private final String entityName;
    private final double entityExperience;

    Entity(boolean isBoss, boolean isEscapable, String entityName, double entityExperience, int entityHealth, int entityBaseDamage, int entityCriticalHitChance, double entityCriticalMultiplier, int entityDefense, boolean isDead) {
        this.isBoss = isBoss;
        this.isEscapable = isEscapable;
        this.entityName = entityName;
        this.entityExperience = entityExperience;
        this.entityHealth = entityHealth;
        this.entityBaseDamage = entityBaseDamage;
        this.entityCriticalHitChance = entityCriticalHitChance;
        this.entityCriticalMultiplier = entityCriticalMultiplier;
        this.entityDefense = entityDefense;
        this.isDead = isDead;
    }
    //region Getter Functions
    public double getEntityCriticalMultiplier() {
        return entityCriticalMultiplier;
    }
    public boolean isDead() {
        return isDead;
    }
    public boolean isBoss(){
        return isBoss;
    }
    public boolean isEscapable(){
        return isEscapable;
    }
    public String getEntityName(){
        return entityName;
    }
    public double getEntityExperience(){
        return entityExperience;
    }
    public int getEntityHealth(){
        return entityHealth;
    }
    public int getEntityBaseDamage(){
        return entityBaseDamage;
    }
    public int getEntityCriticalHitChance(){
        return entityCriticalHitChance;
    }
    public int getEntityDefense(){
        return entityDefense;
    }

    //endregion



    //region calculate and checker functions
    public int CalculateDamageOut(){
        Random rand = new Random();
        //TODO find final way for consistently variable damage output.
        int criticalHitTarget = 100 - entityCriticalHitChance;
        boolean criticalHit = (rand.nextInt(100) >= criticalHitTarget);
        return criticalHit ? (int) (entityBaseDamage * entityCriticalMultiplier) : entityBaseDamage;
    }
    public void DamageEntity(double rawDamageIn) {
        int health = getEntityHealth();
        int defense = getEntityDefense();
        this.entityHealth = (int) (health - (rawDamageIn - defense));
        DeathCheck();
    }
    public void DeathCheck(){
        if(entityHealth <= 0) this.isDead = true;
    }


    //endregion
}
class Item{
    private final ItemRarity rarity;
    private final String itemName;
    public Item(String itemName,ItemRarity rarity){
        this.itemName = itemName;
        this.rarity = rarity;
    }
    public String getItemName(){
        return itemName;
    }
    public ItemRarity getRarity(){
        return rarity;
    }

}
class Armour extends Item{
    private final int defense;
    private final int dodgeChance;

    public Armour(String itemName,int defense,int dodgeChance,ItemRarity rarity){
        super(itemName,rarity);
        this.defense = defense;
        this.dodgeChance = dodgeChance;
        boolean isConsumable = false;
    }
    public int getDefense(){
        return defense;
    }
    public int getDodgeChance(){
        return dodgeChance;
    }
}
class HealthPotion extends Item{
    private final PotionLevel healingLevel;

    // levels small,lesser,normal,greater,grand,Omega.

    public HealthPotion(String itemName, PotionLevel level,ItemRarity rarity){
        super(itemName,rarity);
        this.healingLevel = level;
        boolean isConsumable = false;
    }
    public PotionLevel getHealingLevel(){
        return healingLevel;
    }
}
class Weapon extends Item {

   private final int weaponBaseDamage;
   private final int weaponCriticalHitChance;
   private final double weaponCriticalDamageMulti;

    public Weapon(String itemName, int itemBaseDamage, int itemCritChance, double itemCritDamageMulti, ItemRarity rarity){
        super(itemName,rarity);
        this.weaponBaseDamage = itemBaseDamage;
        this.weaponCriticalHitChance = itemCritChance;
        this.weaponCriticalDamageMulti = itemCritDamageMulti;
        boolean isConsumable = false;
    }
    //region getter functions
    public int getWeaponBaseDamage(){
        return this.weaponBaseDamage;
    }
    public int getWeaponCriticalHitChance(){
        return this.weaponCriticalHitChance;
    }
    public double getWeaponCriticalDamageMulti(){
        return this.weaponCriticalDamageMulti;
    }

    //endregion
}
class Player{
    //region player attributes
   private int maxHealth;
   private int currentHealth;
   private int entitiesDefeated;
   private double playerLevel;
   private Weapon equippedWeapon;
   private Armour equippedArmour;
   private HashMap<Item,Integer> inventory;
   //endregion
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
    public Weapon getEquippedWeapon(){
        return equippedWeapon;
    }
    public Armour getEquippedArmour(){
        return equippedArmour;
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
        // do not use this function to deal damage to the player unless if that damage ignores dodge chance and defence as those are calculated separately
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
        return currentHealth <= 0;
    }
    public boolean playerHasItem(Item item){
        return inventory.containsKey(item);
    }
    //endregion
    //region player calculations
    public int CalculateDamageDealt(){
        Random random = new Random();
        Weapon weapon = getEquippedWeapon();
        int baseDamage = weapon.getWeaponBaseDamage();
        int criticalHitChance = weapon.getWeaponCriticalHitChance();
        double criticalHitDamageMultiplier = weapon.getWeaponCriticalDamageMulti();
      //TODO finalize damage stuff.
        boolean criticalHit = (criticalHitChance - 100) >= random.nextInt(100);
        double finalDamage = criticalHit ? baseDamage * criticalHitDamageMultiplier: baseDamage;
        return (int) finalDamage;
    }
    public void CalculateAndDealDamageTaken(int rawDamageIn){
        Armour currentArmour = getEquippedArmour();
        int defense = currentArmour.getDefense();
        int dodgeChance = currentArmour.getDodgeChance();
        if(!(dodgeChance == 0)){
            Random rand = new Random();
            int dodgeTarget = 100 - dodgeChance;
            int dodgeAttempt = rand.nextInt(100);
            if (dodgeAttempt >= dodgeTarget){
                damagePlayer(0);
            }
        }
        if (rawDamageIn - defense <= 0){
            damagePlayer(0);
        }
        else{
            damagePlayer((rawDamageIn - defense));
        }
    }
}