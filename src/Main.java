import java.lang.reflect.Field;
import java.util.*;


public class Main {

    public static void main(String[] args) {
        //TODO add game initializer thingymcdo
        Weapon fists = new Weapon("Fists",2,0,1,ItemRarity.TRASH);
        Armour leather = new Armour("Leather Apron",0,0,ItemRarity.TRASH);
        HashMap<Item,Integer> playerInv = new HashMap<>();
        Player player = new Player(58, 58,1,100, fists,leather, playerInv);
        System.out.println("Hello world!");
        WorldManager worldManager = new WorldManager();
        LootManager lootManager = new LootManager();
       Entity currentEntity = worldManager.GenerateNewEntity(player);
       currentEntity.PrintAllStats();
       Weapon weapon = lootManager.GenerateWeapon(player,currentEntity);
       weapon.PintWeaponStats();





    }
}
//region enumerations

enum ItemRarity{
    TRASH,COMMON,UNCOMMON,RARE,LEGENDARY,UNIQUE
}
enum PotionLevel {
    MINI,LESSER,NORMAL,GREATER,GRAND,OMEGA;
    public PotionLevel getNext() {
        return values()[(this.ordinal() + 1) % values().length];
}}
class LootManager{
    /*
    the loot manager is used for dynamically generating loot as well as determining what loot if any is dropped.
    the loot manager is not to be used to modify items after ones creation
     */
    private Random random = new Random();
    //region lists of loot modifier names
    private final List<String> PositiveWeaponNameModifiers = Arrays.asList("Razor-Sharp","Ethereal","Frost-Forged","Quantum-Tempered","Swift-Steel","Radiant","Adamantine","Runic","Noble","Iridescent","Astral","Verdant","Obsidian","Celestial","Vorpal","Gilded","Draconian","Ionized","Flaming","Destructive","Unstoppable");
    private final List<String> NegativeWeaponNameModifiers = Arrays.asList("Rusty","Damaged","Broken","Weak","Warped","Fractured","Dull","Feeble","Cracked","Dilapidated");
    private final List<String> WeaponNames = Arrays.asList("Claymore","Broad Sword","Reaper","Shadow's Bane","Ember Blade","Icy Talon","Glaive","Polearm","Night Fang","Trident","Dagger","Dirk","Ice-Pick","Gladius","Pike","War Hammer","Spear","Cleaver","Flail","Mace");
    private final List<String> PositiveArmourModifiers = Arrays.asList("Adamantine","Frost Bound","Glacial Steel","Polished","Sapphire","Timeless","Glistening","Impenetrable","Thundering","Dragon Scale");
    private final List<String> ArmourNames = Arrays.asList("Mail","Plate","Plate-Mail","Breastplate","Brigandine","Cloak","Tunic","Robes","Scale Mail");
    private final List<String> NegativeArmourModifiers = Arrays.asList("Broken","Weak","Rusty","Threadbare","Shoddy","Tattered","Shabby");
    //endregion
    LootManager(){
        //constructor
    }
    public void RollAllLootDrops(Player player,Entity entity){
        RollForHealthPotionDrop(player,entity);
        RollForItemDrop(player,entity);
        //TODO add one for gold but that also requires adding the gold infrastructure which can come later.
    }
    public void RollForHealthPotionDrop(Player player,Entity entity){
        boolean wasBoss = entity.isBoss();
        if(wasBoss) {
            player.addToInventory(GenerateHealthPotion(player,entity),1);
        }
        else {
            if(random.nextBoolean()){
                player.addToInventory(GenerateHealthPotion(player,entity),1);
            }
        }



    }
    public void RollForItemDrop(Player player,Entity entity){
        boolean wasBoss = entity.isBoss();
        if(wasBoss){
            //guarantee that bosses will drop loot
            player.addToInventory((GenerateArmour(player,entity)),1);
            player.addToInventory(GenerateWeapon(player,entity),1);
        }
        else {
            boolean itemType = random.nextBoolean();
            boolean rollOne = random.nextBoolean();
            boolean rollTwo = random.nextBoolean();
            if (rollOne || rollTwo){
                if (itemType) {
                    player.addToInventory(GenerateArmour(player,entity), 1);
                } else {
                    player.addToInventory(GenerateWeapon(player,entity), 1);
                }
            }
        }
    }
    public Weapon GenerateWeapon(Player player,Entity entity){
        //region player stats retrieved
       final double playerLevel = player.getPlayerLevel();
        //endregion
        // region entity stats retrieved.
        final boolean wasBoss = entity.isBoss();
        //endregion
        //region weapon stats to be dynamically generated
        //further documentation for the stats generated here can be found in the lootTable.txt file
        ItemRarity rarity = GenerateItemRarity(wasBoss);
        String weaponName = GenerateWeaponName(rarity);
        int baseDamage = GenerateWeaponBaseDamage(rarity,playerLevel);
        int criticalHitChance = GenerateWeaponCriticalChance(rarity);
        double criticalHitDamageMultiplier = GenerateWeaponCriticalDamageMultiplier(rarity);
        Weapon newWeapon = new Weapon(weaponName,baseDamage,criticalHitChance,criticalHitDamageMultiplier,rarity);
        return newWeapon;
        //endregion
    }
    public Armour GenerateArmour(Player player,Entity entity){
        double playerLevel = player.getPlayerLevel();
        boolean wasBoss = entity.isBoss();
        //region armour stats to be generated
        ItemRarity rarity = GenerateItemRarity(wasBoss);
        String itemName = GenerateArmourName(rarity);
        int defense = GenerateArmourDefense(playerLevel,rarity);
        int dodgeChance = GenerateArmourDodgeChance(rarity);
        //endregion
        Armour armour = new Armour(itemName,defense,dodgeChance,rarity);
        return armour;

    }
    public HealthPotion GenerateHealthPotion(Player player,Entity entity){
        boolean wasBoss = entity.isBoss();
        PotionLevel potionLevel = PotionLevel.MINI;
        String potionName = "Mini Health Potion";
        int randomChance = random.nextInt(0,101);
            if(randomChance <= 10){
                potionLevel = PotionLevel.MINI;
                potionName = "Mini Health Potion";
            } else if (randomChance <= 50) {
                potionLevel = PotionLevel.LESSER;
            }
            else if (randomChance <= 75 ){
                potionLevel = PotionLevel.NORMAL;
            } else if (randomChance <= 90) {
                potionLevel = PotionLevel.GREATER;

            } else if (randomChance <= 97) {
                potionLevel = PotionLevel.GRAND;

            } else if (randomChance <= 100) {
                potionLevel = PotionLevel.OMEGA;
            }
        if (wasBoss && potionLevel != PotionLevel.OMEGA) {
            potionLevel = potionLevel.getNext();
        }
        HealthPotion healthPotion = new HealthPotion(switch (potionLevel){
            case MINI -> "Mini Potion of Healing";
            case LESSER -> "Lesser Potion of Healing";
            case NORMAL -> "Potion of Healing";
            case GREATER -> "Greater Potion of Healing";
            case GRAND -> "Grand Potion of Healing";
            case OMEGA -> "Omega Potion of Healing";
        },potionLevel,ItemRarity.COMMON);
        return healthPotion;

        }

    public ItemRarity GenerateItemRarity(boolean wasBoss){
        int lootRoll = random.nextInt(1,101); // bound is exclusive hence why bound = 101 and origin is inclusive so origin = 1 to conserve a percent scale
        ItemRarity rarity = ItemRarity.COMMON;
        if (wasBoss){
            //use this for rolling boss loot will have a higher chance of better gear for rarity chances see lootTable.txt
            if(lootRoll <= 10){
                // 10%  common drop chance from boss
                rarity = ItemRarity.COMMON;
            } else if (lootRoll <= 40) {
                // 30% uncommon chance from bosses
                rarity = ItemRarity.UNCOMMON;
            } else if (lootRoll <= 70){
                // 30% rare drop from bosses
                rarity = ItemRarity.RARE;
            }else if (lootRoll <= 90){
                // 20% legendary drop from bosses
                rarity = ItemRarity.LEGENDARY;
            } else if (lootRoll <=100) {
                //10% percent 
                rarity = ItemRarity.UNIQUE;
            }
        }
        else {
            //use this for rolling normal loot will have a lower chance of rolling high level gear for rarity chances see lootTable.txt
            if(lootRoll <= 19){
                rarity = ItemRarity.TRASH;
            } else if (lootRoll <= 69) {
                rarity =ItemRarity.COMMON;
            } else if (lootRoll <= 84) {
                rarity = ItemRarity.UNCOMMON;
            } else if (lootRoll <= 94){
                rarity = ItemRarity.RARE;
            }else if (lootRoll <= 99){
                rarity = ItemRarity.LEGENDARY;
            } else if (lootRoll == 100) {
                rarity = ItemRarity.UNIQUE;
            }
        }
        return rarity;
    }
    //region weapon specific generations
    public String GenerateWeaponName(ItemRarity rarity){
        String modifier;
        String weapon;
        String finalName;
        if(rarity == ItemRarity.TRASH){
            modifier = NegativeWeaponNameModifiers.get(random.nextInt(0,NegativeWeaponNameModifiers.size()));
        }
        else {
            modifier = PositiveWeaponNameModifiers.get(random.nextInt(0,PositiveWeaponNameModifiers.size()));
        }
        weapon = WeaponNames.get(random.nextInt(0,WeaponNames.size()));
        finalName = modifier.concat(" ").concat(weapon);
        return finalName;

    }
    public int GenerateWeaponBaseDamage(ItemRarity rarity,double playerLevel){
        int startDamage = 2 + (int)(playerLevel/2);
        boolean modifier = random.nextBoolean();
        int damageAdjust = (int) ((playerLevel*10)/(15));
        int preAugmentDamage = modifier ? startDamage + damageAdjust: (int) ((startDamage - damageAdjust) + playerLevel/1.3);
        double augmentedDamage = switch (rarity){
            case TRASH -> preAugmentDamage * 0.7;
            case COMMON -> preAugmentDamage;
            case UNCOMMON -> preAugmentDamage * 1.5;
            case RARE -> preAugmentDamage * 1.9;
            case LEGENDARY -> preAugmentDamage * 2.3;
            case UNIQUE -> preAugmentDamage * 3;
        };
        int finalDamage = (int) augmentedDamage;
        return finalDamage;
    }
    public int GenerateWeaponCriticalChance(ItemRarity rarity){
        int criticalHitChance = switch (rarity){
            case TRASH -> 1;
            case COMMON -> 5;
            case UNCOMMON -> 10;
            case RARE -> 15;
            case LEGENDARY -> 20;
            case UNIQUE -> 25;
        };
        return criticalHitChance;
    }
    public double GenerateWeaponCriticalDamageMultiplier(ItemRarity rarity){
        double critMultiplier = switch (rarity){
            case TRASH -> 1;
            case COMMON -> 1.5;
            case UNCOMMON -> 1.7;
            case RARE -> 1.9;
            case LEGENDARY -> 2.2;
            case UNIQUE -> 2.5;
        };
        return critMultiplier;
    }
    //endregion
    //region armour specific generations
    public int GenerateArmourDefense(double playerLevel,ItemRarity rarity){
        int baseDefenseLevel = (int) playerLevel;
        int randomModifier = random.nextInt(0,baseDefenseLevel);
        int preAugmentDefense = baseDefenseLevel + randomModifier;
        int augmentedDefense = (int)switch (rarity){
            case TRASH -> preAugmentDefense * 0.5;
            case COMMON -> preAugmentDefense;
            case UNCOMMON -> preAugmentDefense * 1.5;
            case RARE -> preAugmentDefense * 2;
            case LEGENDARY -> preAugmentDefense * 2.5;
            case UNIQUE -> preAugmentDefense * 3.5;
        };
        return augmentedDefense;
    }
    public int GenerateArmourDodgeChance(ItemRarity rarity){
        int baseRandom = random.nextInt(0,11);
        int finalDodgeChance = switch (rarity){
            case TRASH -> baseRandom - 5;
            case COMMON -> baseRandom;
            case UNCOMMON -> baseRandom + 1;
            case RARE -> baseRandom + 3;
            case LEGENDARY -> baseRandom + 5;
            case UNIQUE -> baseRandom + 7;
        };
        return Math.max(finalDodgeChance, 0);

    }
    public String GenerateArmourName(ItemRarity rarity){
        String modifier;
        String armourType;
        String finalName;
        if(rarity == ItemRarity.TRASH){
            modifier = NegativeArmourModifiers.get(random.nextInt(0,NegativeArmourModifiers.size()));
        }
        else {
            modifier = PositiveArmourModifiers.get(random.nextInt(0,PositiveArmourModifiers.size()));
        }
        armourType = ArmourNames.get(random.nextInt(0,ArmourNames.size()));
        finalName = modifier.concat(" ").concat(armourType);
        return finalName;
    }
    //endregion

}
class WorldManager {
    //GOD to the world an instance should be made on game initialization.
    //region creatureNaming
   private final List<String> attributes = Arrays.asList("Oscillating","Romantic","Clumsy","Ferocious","Persnickety","Goopy","Pounding","Dramatic","Bumbling","Slithering");
   private final List<String> Creatures = Arrays.asList("Phoenix","Shark","Bear","Penguin","Kitten","Alien","Unicorn","Eel","Raptor","Dragon");
    //endregion
    private final Random random = new Random();
    WorldManager() {
    }
    public Entity GenerateNewEntity(Player player){
         double playerLevel = player.getPlayerLevel();
         int playerKillCount = player.getEntitiesDefeated();
         //region entity specs to be generated
         int entityHealth = GenerateEntityHealth(playerLevel);
         int entityBaseDamage = GenerateEntityBaseDamage(playerLevel);
         // for now critical hit chance will be locked at 5 percent for all
         int entityCriticalHitChance = 5;
         // entity's will begin to get defense after 15 killed
        // it will compound slowly based on kills not on player level
         int entityDefense = GenerateEntityDefense(playerKillCount);
         boolean isBoss = GenerateBossStatus(playerLevel,playerKillCount);
         boolean isEscapable = !isBoss;
         boolean isDead = false;
         double entityExperience = CalculateExperienceDrop(entityHealth,playerLevel);
         double entityCriticalMultiplier = 2.0;
         String entityName = GenerateCreatureName() ;
         String trueName = isBoss ? "Boss ".concat(entityName): entityName;
         Entity entity = new Entity(isBoss,isEscapable,trueName,entityExperience,entityHealth,entityBaseDamage,entityCriticalHitChance,entityCriticalMultiplier,entityDefense,isDead);
         return entity;
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
       } else if (killCount < 25) {
           return random.nextInt(1,4);

       } else if (killCount < 30) {
           return random.nextInt(2,6);
       } else if (killCount < 40) {
           return random.nextInt(4,8);
       }
       else {
           return random.nextInt(0,17);
       }
    }
    public boolean GenerateBossStatus(double playerLevel,int killCount){
        int bossHits[] = new int[20];
        bossHits[0] = 42;
        if ( killCount % 3 == 0);{
            bossHits[1] = 100;
            bossHits[2] = 99;
            bossHits[3] = 98;
            bossHits[4] = 97;
        }
        if(playerLevel >= 15){
            bossHits[5] = 96;
            bossHits[6] = 95;
            bossHits[7] = 94;
            bossHits[8] = 93;
            bossHits[9] = 92;
        }
        int bossMaker = random.nextInt(0,101);
        for(int i: bossHits){
            if (i == bossMaker){
                return true;
            }
        }
        if(killCount % 10 == 0 && killCount != 0){
            boolean bossCheck1 = random.nextBoolean();
            boolean bossCheck2 = random.nextBoolean();
            boolean bossSummon = bossCheck1 && bossCheck2;
        }
        return false;
    }
    public String GenerateCreatureName(){
        String attribute = attributes.get(random.nextInt(0,attributes.size()));
        String creature = Creatures.get(random.nextInt(0,Creatures.size()));
        String totalName = attribute.concat(" ").concat(creature);
        return totalName;
    }
    public double CalculateExperienceDrop(int health,double playerLevel){
        double experienceDrop = health/(60 + playerLevel*3);
        return experienceDrop;
    }



    //endregion
}
class Entity {
    private int entityHealth;
    private final int entityBaseDamage;
    private final int entityMaxHealth;
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
        this.entityMaxHealth = entityHealth;
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
    public int getEntityMaxHealth(){
        return entityMaxHealth;
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
    //region utilities
    public void PrintAllStats(){
        System.out.println("Health: " + entityHealth);
        System.out.println("Base Damage is: " + entityBaseDamage);
        System.out.println("defense is: " + entityDefense);
        System.out.println("experience is: " + entityExperience);
        System.out.println("Name is: " + entityName);
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
    public void PintWeaponStats(){
        System.out.println(getItemName() +"\nRarity: " + getRarity() + "\nBase Damage: " + weaponBaseDamage + "\nCritical Hit Chance: " + weaponCriticalHitChance + "\nCritical Hit Damage Multiplier: " + weaponCriticalDamageMulti);
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