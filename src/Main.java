import java.util.*;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        //TODO add game initializer thingymcdo
          UserInterFace userInterFace = UserInterFace.getUserInterFace();
          LootManager lootManager = LootManager.getLootManager();
          Player player = Player.getPublicPlayer();
          FightManager fightManager = new FightManager();
          WorldManager worldManager = WorldManager.getPublicWorldManager();
          CoinManager coinManager = new CoinManager(0,0,0,1999999);
          coinManager.printBalance();

         System.out.println(coinManager.canAfford(12 ,17,190));
//          for(int i = 0; i < 100; i++) {
//              fightManager.MainBattleLoop(player, worldManager.GenerateNewEntity(player));
//
//          }
//        userInterFace.PrintGameInstructionsEnemy();
//        userInterFace.PrintGameInstructionsItemsAndLoot();


        System.out.println("Hello world!");

//       Entity currentEntity = worldManager.GenerateNewEntity(player);
//       currentEntity.PrintAllStats();
//       Weapon weapon = lootManager.GenerateWeapon(player,currentEntity);
//       weapon.PintWeaponStats();
//       Armour armour = lootManager.GenerateArmour(player,currentEntity);
//       armour.PrintArmourStats();





    }
}
//region enumerations
enum BattleOption{
    FIGHT,CHECK,FLEE,INVENTORY
        }

enum ItemRarity{
    TRASH,COMMON,UNCOMMON,RARE,LEGENDARY,UNIQUE
}
enum PotionLevel {
    MINI,LESSER,NORMAL,GREATER,GRAND,OMEGA;
    public PotionLevel getNext() {
        return values()[(this.ordinal() + 1) % values().length];
    }
}
enum CoinLevels{
    COPPER,SILVER,GOLD,PLATINUM;
    public CoinLevels getNext() {
        return values()[(this.ordinal() + 1) % values().length];
    }
}
//endregion
class UserInterFace{
    //region string colors
    protected String red = "\u001B[31m";
    protected String green = "\u001B[32m";
    protected String yellow = "\u001B[33m";
    protected String blue = "\u001B[34m";
    protected String magenta = "\u001B[35m";
    protected String cyan = "\u001B[36m";
    protected String resetColor = "\u001B[0m";
    //endregion
    //region important instantiations
    protected WorldManager worldManager = new WorldManager();
    protected LootManager lootManager = new LootManager();
    protected Weapon weaponTester = new Weapon("Testing",0,0,1,ItemRarity.UNIQUE);
    protected Armour armourTester = new Armour("Testing",0,0,ItemRarity.UNIQUE);
    protected CoinManager coinTester = new CoinManager(0,0,0,0);
    protected Player playerTester = new Player(1,1,0,1,weaponTester,armourTester,coinTester);
    //endregion
    protected int threadInterrupts = 0;
    private final Scanner scanner = new Scanner(System.in);
    //only a single instance is needed to deal with UI;
    private static UserInterFace PublicUserInterFace = new UserInterFace();
   UserInterFace(){
    }
    static UserInterFace getUserInterFace(){
        return PublicUserInterFace;
    }
    public void pause(int milliseconds){
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            this.threadInterrupts++;
        }

    }
    public void pause(){
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            this.threadInterrupts++;
        }

    }
    public void PrintWelcomingMessage(){
        // welcome user to the game and let them read the rules if desired.
        final String AsteriskFrame = "\t\t***************************************************\t\t";
        boolean userUnderstands = false;
        //region print instructions to user
        System.out.println(AsteriskFrame);
        System.out.println("Welcome to the Game!");
        System.out.println("This game is by typing text into the output terminal.\nWhich conveniently happens to be the area you are reading this text from");
        System.out.println("Sometimes the terminal will prompt you with yes or no questions these will usually be designated with a Y/N");
        System.out.println("To answer these question please use Y or y for yes and N or n for no");
        System.out.println("Other times the terminal will prompt you to enter a number based off of what it prints to the screen.");
        System.out.println("To answer these prompt you will input the number on the keyboard if the desired number is multiple characters ensure that no spaces are in between them.");
        System.out.println("Do note that the enter key must be pressed before your input is read so if you wanted to enter the number 12 you would type the 1 key then the 2 key then enter.");
        System.out.println("Mistakes can be corrected before pressing enter by using the backspace key just make sure you don't end up with any whitespaces in front of your desired input.");
        System.out.println("Also if you do enter an option that is not available don't worry all user inputs are put through input validation to protect against human error.");
        System.out.println("Please do not type anything into the terminal if you have not been prompted to do so while it should not crash the program it might.");
        System.out.println("In the event that you do enter an option that is not available you will be asked to try again and the options will be presented to you again");
        System.out.println("Also do note that you can't truly break anything regardless of what you enter, as long as you stay out of powershell terminal, but the program might crash if it is not expecting it.");
        System.out.println("In the event that this happens you will unfortunately have to restart by executing the program.");
        System.out.println("Here is an example of a Yes or No menu that you could encounter");
        //endregion
        do {
            System.out.println("Do you understand Y/N");
            String userInput = scanner.nextLine();
            switch (userInput) {
                case "Y", "y" -> {
                    userUnderstands = true;
                    System.out.println("Great!\nWith that have fun.");
                    MainMenuMenuOutMostSelectionLogic(false);
                }
                case "N", "n" -> {
                    System.out.println("Oh no feel free to reread the text.");
                    PrintWelcomingMessage();
                }
                default -> System.out.println("Please try again as " + userInput + " is not a valid option.");
            }
        }while (!(userUnderstands));
    }
    public void MainMenuMenuOutMostSelectionLogic(boolean readInstructions){
        boolean hasChosen = false;
        final String MainMenu = readInstructions ? "1:Start Game\n2:View Full Game Instructions\n3:View Graphics Options\n4:Exit Game":"1:Start Game\n2:View Full Game Instructions(Recommended!!)\n3:View Graphics Options\n4:Exit Game";
        do {
            System.out.println(MainMenu);
            System.out.println("Please select the action you wish by entering the corresponding number");
            String userChoice = scanner.nextLine();
            switch (userChoice){
                case "1" ->{
                    hasChosen = true;
                    System.out.println("The adventure begins!");
                    //TODO call gameplay begin function.
                }
                case "2" ->{
                    hasChosen = true;
                    PrintGameInstructionsEnemy();
                }
                case "3" ->{
                    System.out.print("Loading");
                    try {
                        Thread.sleep(800);
                        System.out.print(".");
                        Thread.sleep(800);
                        System.out.print(".");
                        Thread.sleep(800);
                        System.out.print(".\n");
                        Thread.sleep(800);
                        System.out.println("NO");
                        Thread.sleep(800);
                        System.out.println("Just No");
                    }
                    catch (InterruptedException e){
                        System.out.println("Well the threads were busy so have this error");
                        System.out.println(e.getMessage());
                        System.out.println("also that is a no on the graphics settings");
                    }

                }
                case "4" ->{
                    System.out.println("Bye then, rude");
                    hasChosen = true;
                    System.exit(0);
                }
                default -> {
                    System.out.println(userChoice.concat(" Was not an option please try again"));
                }
            }
        }while(!hasChosen);
    }
    public void PrintGameInstructionsEnemy(){
        //accessed through the MainMenuOuterLoop;
        //region print statements
        System.out.println(green + "Welcome to the Enemy section of the Game Instructions." + resetColor);
        pause(1000);
        System.out.println("In this game you will battle many enemies and gather tons of loot.");
        System.out.println("Each enemy you encounter will have unique stats depending on the player's level.");
        System.out.println("There are a total of 11 enemy stats however you will only care about 6.");
        System.out.println("The First stat is the name of the enemy\n\tThe name tells two things the enemy, first is what type of creature it is, and the second is whether the creature is a boss or not ");
        System.out.println("\tThe only difference between a normal enemy and a boss is that bosses have inflated stats and always drop an armour piece and a weapon.(more on these later)");
        System.out.println("\tNot only do they always drop loot they also have a significantly higher chance to drop better loot and cannot drop trash rarity items.");
        System.out.println("The next two stats are based on the enemies health. One is current one is max.");
        System.out.println("\tYou can ignore the max health as monsters will always be at max health upon generation.");
        System.out.println("The next important stat is the Base damage.");
        System.out.println("\tThis stat shows how much damage (before modifications) the enemy will do.");
        System.out.println("\tCurrently there is no damage variance for each hit meaning the only modification is a Critical Hit.(more on this later)");
        System.out.println("\tThis means everytime you get hit you will be faced with the enemy's base damage which will go against your defense.(more on this later)");
        System.out.println("The next two important stats are critical hit chance and critical hit damage multiplier");
        System.out.println("\tCurrently all enemies have a fixed critical hit chance of 5%");
        System.out.println("\tCurrently all enemies also have a fixed critical hit damage multiplier of 2 times.");
        System.out.println("\tThe formula for final damage for enemies is: Final Damage = (Base Damage) or in the case of a critical hit: Final Damage = (Base Damage * 2)");
        System.out.println("The next important stat is the enemy defense.");
        System.out.println("\tDefense is simple as the damage a enemy takes is (Damage Dealt - Defense).");
        System.out.println("\tMeaning if you hit a enemy for 10 damage and they have 5 defense their health will go down by 5.");
        System.out.println("Finally each enemy has a secret value that which is the amount of experience the player gains among killing it");
        System.out.println("\tIn normal gameplay this value will be hidden from the player.");
        System.out.println("However now you will be allowed to generate creatures at different player levels and view their stats.");
        System.out.println("\nNote:\n"+red+"!!DO NOT SET PLAYER VALUE TO 0 OR A NEGATIVE NUMBER IT WILL BREAK THE PROGRAM!!\n"+resetColor);
        System.out.println("Do also note that there are a few other variables taken into account upon enemy creation so results may vary.");
        System.out.println("Also note that player level while has no upper bound, setting it to values over 100.0 will make the stats go to extreme levels.Feel free to try though as it wont break.");
        System.out.println("Finally note that the player level displayed in game may not  be directly the same as the player level input.");
        //endregion
        PlayerEntityGenerator();
    }
    public void PrintGameInstructionsItemsAndLoot(){
        System.out.println(green+"Welcome to the Items & Loot Section of the instructions"+ resetColor);
        pause(400);
        System.out.println("In the current state of the game there are three different types of loot");
        System.out.println("\t-Potions of Healing\n\t-Weapons\t-Armour\nI am aware i am using the British spelling but it is objectively better.");
        System.out.println("First we will go over healing potions");
        System.out.println("\tPotions are simple they come in a variety of sizes.");
        System.out.println("\tThese sizes are as follows in order of worst to best\n\tMini\n\tLesser\n\tNormal\n\tGreater\n\tGrand\n\tOMEGA");
        System.out.println("\tPotions are a common drop and can be consumed in your action phase for some health.");
        System.out.println("\tBosses will never drop mini health potions and have better rolls overall");
        System.out.println("\tHealing potions cannot be used to heal over you max health and consuming one that would heal more than the amount of health missing will bring you to full health.");
        System.out.println("Item Rarity");
        System.out.println("\tBefore we cover weapons and armour we have to go over rarity.");
        System.out.println("\tTechnically all items have a rarity, however they do not affect healing potions.");
        System.out.println("\tFor weapons and armour the rarity is the first stat that is rolled With the other stats being modified by those rarities.");
        System.out.println("\tThere are 6 levels of rarity which are as follows worst to best"+"\n\tTrash"+red+"\n\tCommon"+blue+"\n\tUncommon"+green+"\n\tRare"+cyan+"\n\tLegendary"+yellow+"\n\tUnique"+resetColor);
        System.out.println("\tBosses have a higher chance to drop better gear and will never drop Trash quality items.");
        System.out.println("Weapons");
        System.out.println("\tWeapons have 3 non-rarity stats and they are all important.");
        System.out.println("\tThe first is base damage which works the same way it does for enemies.");
        System.out.println("\tThe next stat is the critical hit chance which unlike in enemies is based on the rarity of the item.");
        System.out.println("\tFinally the critical hit damage multiplier is, like critical hit chance, tied to item rarity.");
        System.out.println("Armour");
        System.out.println("\tArmour only has two non-rarity stats.");
        System.out.println("\tThe first is defense which works the same way as it does for enemies so all incoming damage will be reduced by your defense.");
        System.out.println("\tIf your defense is higher that the incoming damage you will take no damage.");
        System.out.println("\tThe next stat is dodge chance.This is a stats that enemies do not have this is the chance to completely avoid all damage.");
        System.out.println("The way items work is you will chose an armour and a weapon to equip and can change them at any time during your action phase(more on that later)");
        System.out.println("Items will be dropped and added directly to your inventory upon successfully killing and enemy.");
        System.out.println("That is all to items feel free to use the item generator to generate some items,however unlike enemies which only use two player stats item generation is much more complex\nSo don't expect results to be identical to actual gameplay");
        System.out.println("Also you're almost done with the directions only one page to go!!");
        PlayerWeaponAndArmourGenerator();


    }
    public void PlayerWeaponAndArmourGenerator(){
        double playerLevel = playerTester.getPlayerLevel();
        final String LootGenerationMenu = ("1:Generate new Weapon at player level: " + playerLevel +"\n" +"2:Generate a new Armour piece at player level: " + playerLevel + "3:Change player level\n4:Return to Enemy section of Instructions\n" + "5:Return to Items & Loot section of Instructions\n6:Proceed to Player Stats and Gameplay steps\n7:Return to Main Menu");
        String userChoice;
        boolean again = true;
        System.out.println(LootGenerationMenu);
        userChoice = scanner.nextLine();


        switch (userChoice){
            case "1"->{
                lootManager.GenerateWeapon(playerTester,worldManager.GenerateNewEntity(playerTester)).PintWeaponStats();

            }
            case "2"->{
                lootManager.GenerateArmour(playerTester,worldManager.GenerateNewEntity(playerTester)).PrintArmourStats();
            }
            case "3"->{
                System.out.println("Please enter a value for player level(can include decimals).if you do a non number it will yell at you but it wont break.");
                try {
                    playerLevel = Double.parseDouble(scanner.nextLine());
                    System.out.println("Player level set.");
                }
                catch (Exception e){
                    System.out.println("um OH NOO AHHHHHHHH!!");
                    pause(1000);
                    e.printStackTrace();
                    System.out.println("Look what you did\n"+ e.getMessage() + "\nthis probably means you entered a non numerical character but I got you\n\n");
                }
                playerTester.setPlayerLevel(playerLevel);
                break;
            }
            case "4"->{
                again = false;
                PrintGameInstructionsEnemy();
                break;
            }
            case "5" ->{
                again = false;
                PrintGameInstructionsItemsAndLoot();
                break;
            }
            case "6"->{
                //TODO go to gameplay phase instructions
            }
            case "7" ->{
                again = false;
                MainMenuMenuOutMostSelectionLogic(true);

            }
        }
       if (again) PlayerWeaponAndArmourGenerator();
    }
    public void PlayerEntityGenerator(){
        double playerLevel = playerTester.getPlayerLevel();
        String userChoice;
        boolean again = true;
        final String EntityGenerationMenu = ("1:Generate new entity at player level: " + playerLevel +"\n" + "2:Change player level\n3:Return to Enemy section of Instructions\n" + "4:Proceed to Items & Loot section of Instructions\n5:Proceed to Player Stats and Gameplay steps\n6:Return to Main Menu");
        System.out.println(EntityGenerationMenu);
        userChoice = scanner.nextLine();
        switch (userChoice){
            case "1"->{
                worldManager.GenerateNewEntity(playerTester).PrintAllStats();
                break;
            }
            case "2"->{
                System.out.println("Please enter a value for player level(can include decimals).if you do a non number it will yell at you but it wont break.");
                try {
                    playerLevel = Double.parseDouble(scanner.nextLine());
                    System.out.println("Player level set.");
                }
                catch (Exception e){
                    System.out.println("um OH NOO AHHHHHHHH!!");
                    pause(1000);
                    e.printStackTrace();
                    System.out.println("Look what you did\n"+ e.getMessage() + "\nthis probably means you entered a non numerical character but I got you\n\n");
                }
                playerTester.setPlayerLevel(playerLevel);
                break;
            }
            case "3"->{
                again = false;
                PrintGameInstructionsEnemy();
                break;
            }
            case "4"->{
                again = false;
                PrintGameInstructionsItemsAndLoot();
                break;
            }
            case "5"->{
                //TODO add player stats and gameplay phases section of instructions;
            }
            case "6"->{
                again = false;
                MainMenuMenuOutMostSelectionLogic(true);
                break;
            }
            default -> {
                System.out.println(userChoice.concat(" Was not an option please try again"));
                break;
            }
        }
        if(again)PlayerEntityGenerator();
    }
    public BattleOption PlayerFightUi(){
        boolean hasChosen = false;
        BattleOption chosenOption = BattleOption.INVENTORY;
        final String PlayerOptionsMenu =  "1:Fight\n2:Check enemy stats\n3:Attempt to flee\n4:Inventory";
        String playerChoice;
        do {
        System.out.println(PlayerOptionsMenu);
        playerChoice = scanner.nextLine();
        switch (playerChoice){
            case "1"->{
                hasChosen = true;
                chosenOption = BattleOption.FIGHT;
            }
            case "2"->{
                hasChosen = true;
                chosenOption = BattleOption.CHECK;
            }
            case "3"->{
                hasChosen = true;
                chosenOption = BattleOption.FLEE;

            }
            case "4"->{
                hasChosen = true;
            }
            default -> {
                System.out.println(playerChoice.concat(" was not an option please try again."));
            }
        }
    }while (!hasChosen);
        return chosenOption;
    }
}
//region managers
class CoinManager{
    private int platinumCoins;
    private int goldCoins;
    private int silverCoins;
    private int copperCoins;
    CoinManager(int platinumCoins, int goldCoins, int silverCoins, int copperCoins){
        this.platinumCoins = platinumCoins;
        this.goldCoins = goldCoins;
        this.silverCoins = silverCoins;
        this.copperCoins = copperCoins;
    }

    public void convertToHighestValue(){
        int convertedSilver;
        int convertedGold;
        int convertedPlatinum;
        if (copperCoins >= 100){
            convertedSilver = (int) (copperCoins/100.0);
            copperCoins = copperCoins - (100 * convertedSilver);
            silverCoins += convertedSilver;
        }
        if (silverCoins >= 100){
            convertedGold = (int) (silverCoins/100.0);
            silverCoins = silverCoins - (100 * convertedGold);
            goldCoins += convertedGold;
        }
        if(goldCoins >= 100){
            convertedPlatinum = (int) (goldCoins/100.0);
            goldCoins = goldCoins - (100 * convertedPlatinum);
            platinumCoins += convertedPlatinum;
        }
    }
    public void printBalance(){
        convertToHighestValue();
        System.out.println(copperCoins + " copper " + silverCoins + " silver " + goldCoins + " gold " + platinumCoins + " platinum ");
    }
    private void convertDownOne(CoinLevels type){
        switch (type){
            case COPPER -> {
                if(silverCoins >= 1) {
                    copperCoins += 100;
                    silverCoins --;
                }
                else if (goldCoins >= 1){
                    silverCoins += 100;
                    goldCoins --;
                    copperCoins += 100;
                    silverCoins --;
                } else if (platinumCoins >= 1) {
                    goldCoins += 100;
                    platinumCoins --;
                    silverCoins += 100;
                    goldCoins --;
                    copperCoins += 100;
                    silverCoins --;

                }
            }
            case SILVER -> {
                if (goldCoins >= 1){
                    silverCoins += 100;
                    goldCoins --;
                    copperCoins += 100;
                    silverCoins --;
                } else if (platinumCoins >= 1) {
                    goldCoins += 100;
                    platinumCoins --;
                    silverCoins += 100;
                    goldCoins --;
                    copperCoins += 100;
                    silverCoins --;}

            }
            case GOLD -> {
                if (platinumCoins >= 1) {
                    goldCoins += 100;
                    platinumCoins --;
                    silverCoins += 100;
                    goldCoins --;
                    copperCoins += 100;
                    silverCoins --;}
            }
            case PLATINUM -> {
            }
        }
    }
//region overloads for canAfford
    public boolean canAfford(int copperCost, int silverCost,int goldCost) {
        return canAfford(copperCost, silverCost, goldCost, 0);
    }

    public boolean canAfford(int copperCost, int silverCost) {
        return canAfford(copperCost, silverCost, 0, 0);
    }

    public boolean canAfford(int copperCost) {
        return canAfford(copperCost, 0, 0, 0);
    }
//endregion
    public boolean canAfford(int copperCost, int silverCost, int goldCost, int platinumCost){
        convertToHighestValue();
        if (copperCost <= copperCoins && silverCost <= silverCoins && goldCost <= goldCoins && platinumCost <= platinumCoins){
            return true;
        }
        if (copperCost > copperCoins) {
            convertDownOne(CoinLevels.COPPER);
        }
        if (silverCost > silverCoins){
            convertDownOne(CoinLevels.SILVER);
        }
        if (goldCost > goldCoins){
            convertDownOne(CoinLevels.GOLD);
        }
        if (platinumCost > platinumCoins){
            return false;
        }
        if (copperCost <= copperCoins && silverCost <= silverCoins && goldCost <= goldCoins){
            return true;
    }
        else {
            convertToHighestValue();
            return false;
        }

}
    public void addCoins(CoinLevels type , int quantity){
        switch (type){
            case COPPER -> {
                copperCoins += quantity;
            }
            case SILVER -> {
                silverCoins += quantity;
            }
            case GOLD -> {
                goldCoins += quantity;
            }
            case PLATINUM -> {
                platinumCoins += quantity;
            }
        }
        convertToHighestValue();
    }
    public void removeCoins(CoinLevels type, int quantity){
        //only do this after checking if the player can afford item!!
            switch (type) {
                case COPPER -> {

                    if (copperCoins - quantity <= 0) {
                        convertDownOne(CoinLevels.COPPER);

                    }
                    copperCoins =- quantity;
                }
                case SILVER -> {
                    if (silverCoins - quantity <= 0){
                        convertDownOne(CoinLevels.SILVER);
                    }
                    silverCoins =- quantity;
                }
                case GOLD -> {
                    if (silverCoins - quantity <= 0){
                        convertDownOne(CoinLevels.GOLD);
                    }
                    goldCoins =- quantity;
                }
                case PLATINUM -> {
                    platinumCoins =- quantity;
                }
            }
        }
    }
class FightManager{
    //todo add public version of fight manager.
    UserInterFace userInterFace = UserInterFace.getUserInterFace();
    LootManager lootManager = LootManager.getLootManager();
        //will contain all functions to deal with battles
        // only one instance needed.
    FightManager(){

    }
    public void MainBattleLoop(Player player,Entity entity){
        // this will be the main battle loop that will execute until the entity is dead the player is dead or the player flees
        //will be dependent on player input as it will call on things from the user interface class.
        // turn will go player Action, entity action during the player action they can either attack, use and item, flee or any combination,
        // a player may consume as many items as desired in one turn
        do{
            playerTurn(player,entity);
            if (!entity.isDead() && !entity.isAbandoned()){
                enemyTurn(player,entity);
                System.out.println("Current player health is: " + player.getCurrentHealth());
            }
        }while ((!entity.isDead() && !entity.isAbandoned() && !player.isPlayerDead()));
        if (entity.isDead()){
            lootManager.RollAllLootDrops(player,entity);
            player.AddExp(entity.getEntityExperience());

        }
        else if (entity.isAbandoned()){
            System.out.println(userInterFace.magenta+"You escape with your life"+userInterFace.resetColor);
        }

        if (!player.isPlayerDead()) {
            System.out.println("current health: "+ player.getCurrentHealth());
        }
        else {
            System.out.println(userInterFace.red+"You are dead"+userInterFace.resetColor);
        }

    }
    public void enemyTurn(Player player, Entity entity){
        int damage = entity.CalculateDamageOut();
        System.out.println(entity.getEntityName() + " tries to hit you for " + damage);
        userInterFace.pause(200);
        player.CalculateAndDealDamageTaken(damage);

    }
    public void playerTurn(Player player, Entity entity) {
        boolean spentAction = false;
        do {
            BattleOption chosenAction = userInterFace.PlayerFightUi();
            switch (chosenAction) {
                case FIGHT -> {
                    spentAction = true;
                    int damage = player.CalculateDamageDealt();
                    int defence = entity.getEntityDefense();
                    entity.DamageEntity(damage);
                    System.out.println("You hit the " + entity.getEntityName() + " for " + damage + " raw damage!");
                    userInterFace.pause(200);
                    System.out.println(entity.getEntityName() + " took " + (damage - defence) + " damage");
                    break;
                }
                case CHECK -> {
                    entity.PlayerCheck();


                }
                case FLEE -> {
                    spentAction = true;
                    boolean fleeSuccess = entity.AttemptToFlee();
                    if (fleeSuccess) {
                        entity.abandon();
                    }
                    else {
                        System.out.println("You fail to flee.");
                    }

                    break;
                }
                case INVENTORY -> {
                    //TODO inventory management hell
                }
            }
        }while (!spentAction);
    }

    }

class LootManager{
    /*
    the loot manager is used for dynamically generating loot as well as determining what loot if any is dropped.
    the loot manager is not to be used to modify items after ones creation
     */
    private static LootManager PubliclootManager = new LootManager();
    protected final String red = "\u001B[31m";
    protected final String green = "\u001B[32m";
    protected final String yellow = "\u001B[33m";
    protected final String blue = "\u001B[34m";
    protected final String magenta = "\u001B[35m";
    protected final String cyan = "\u001B[36m";
    protected final String white = "\u001B[37m";
    protected final String resetColor = "\u001B[0m";
    private final Random random = new Random();
    //region lists of loot modifier names
    private final List<String> PositiveWeaponNameModifiers = Arrays.asList("Razor-Sharp","Ethereal","Frost-Forged","Quantum-Tempered","Swift-Steel","Radiant","Adamantine","Runic","Noble","Iridescent","Astral","Verdant","Obsidian","Celestial","Vorpal","Gilded","Draconian","Ionized","Flaming","Destructive","Unstoppable");
    private final List<String> NegativeWeaponNameModifiers = Arrays.asList("Rusty","Damaged","Broken","Weak","Warped","Fractured","Dull","Feeble","Cracked","Dilapidated");
    private final List<String> WeaponNames = Arrays.asList("Claymore","Broad Sword","Reaper","Shadow's Bane","Ember Blade","Icy Talon","Glaive","Polearm","Night Fang","Trident","Dagger","Dirk","Ice-Pick","Gladius","Pike","War Hammer","Spear","Cleaver","Flail","Mace");
    private final List<String> PositiveArmourModifiers = Arrays.asList("Adamantine","Frost Bound","Glacial Steel","Polished","Sapphire","Timeless","Glistening","Impenetrable","Thundering","Dragon Scale");
    private final List<String> ArmourNames = Arrays.asList("Mail","Plate","Plate-Mail","Breastplate","Brigandine","Cloak","Tunic","Robe","Scale Mail");
    private final List<String> NegativeArmourModifiers = Arrays.asList("Broken","Weak","Rusty","Threadbare","Shoddy","Tattered","Shabby");
    //endregion
    LootManager(){
        //constructor
    }

    public static LootManager getLootManager() {
        return PubliclootManager;
        }
    public void RollAllLootDrops(Player player,Entity entity){
        RollForHealthPotionDrop(player,entity);
        RollForItemDrop(player,entity);
        //TODO add one for gold but that also requires adding the gold infrastructure which can come later.
    }
    public void RollForHealthPotionDrop(Player player,Entity entity){
        boolean wasBoss = entity.isBoss();
        if(wasBoss) {
            player.addToInventory(GenerateHealthPotion(entity),1);
        }
        else {
            if(random.nextBoolean()){
                player.addToInventory(GenerateHealthPotion(entity),1);
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
        return new Weapon(weaponName,baseDamage,criticalHitChance,criticalHitDamageMultiplier,rarity);
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
        return new Armour(itemName,defense,dodgeChance,rarity);

    }
    public HealthPotion GenerateHealthPotion(Entity entity){
        boolean wasBoss = entity.isBoss();
        PotionLevel potionLevel = PotionLevel.MINI;
        int randomChance = random.nextInt(0,101);
            if(randomChance <= 10){
                potionLevel = PotionLevel.MINI;
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
            case MINI -> white.concat("Mini Potion of Healing").concat(resetColor);
            case LESSER -> red.concat("Lesser Potion of Healing").concat(resetColor);
            case NORMAL -> blue.concat("Potion of Healing").concat(resetColor);
            case GREATER -> green.concat("Greater Potion of Healing").concat(resetColor);
            case GRAND -> cyan.concat("Grand Potion of Healing").concat(resetColor);
            case OMEGA -> yellow.concat("Omega Potion of Healing").concat(resetColor);
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
        String coloredName;
        if(rarity == ItemRarity.TRASH){
            modifier = NegativeWeaponNameModifiers.get(random.nextInt(0,NegativeWeaponNameModifiers.size()));
        }
        else {
            modifier = PositiveWeaponNameModifiers.get(random.nextInt(0,PositiveWeaponNameModifiers.size()));
        }
        weapon = WeaponNames.get(random.nextInt(0,WeaponNames.size()));
        finalName = modifier.concat(" ").concat(weapon);
        coloredName = switch (rarity){
            case TRASH -> white.concat(finalName).concat(resetColor);
            case COMMON -> red.concat(finalName).concat(resetColor);
            case UNCOMMON -> blue.concat(finalName).concat(resetColor);
            case RARE -> green.concat(finalName).concat(resetColor);
            case LEGENDARY -> cyan.concat(finalName).concat(resetColor);
            case UNIQUE -> yellow.concat(finalName).concat(resetColor);
        };
        return coloredName;

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
        return (int) augmentedDamage;
    }
    public int GenerateWeaponCriticalChance(ItemRarity rarity){
        return switch (rarity){
            case TRASH -> 1;
            case COMMON -> 5;
            case UNCOMMON -> 10;
            case RARE -> 15;
            case LEGENDARY -> 20;
            case UNIQUE -> 25;
        };
    }
    public double GenerateWeaponCriticalDamageMultiplier(ItemRarity rarity){
        return switch (rarity){
            case TRASH -> 1;
            case COMMON -> 1.5;
            case UNCOMMON -> 1.7;
            case RARE -> 1.9;
            case LEGENDARY -> 2.2;
            case UNIQUE -> 2.5;
        };
    }
    //endregion
    //region armour specific generations
    public int GenerateArmourDefense(double playerLevel,ItemRarity rarity){
        int baseDefenseLevel = (int) playerLevel;
        int randomModifier = random.nextInt(0,Math.max(baseDefenseLevel/4,2));
        int preAugmentDefense = baseDefenseLevel + randomModifier;
        int augmentedDefense = (int)switch (rarity){
            case TRASH -> preAugmentDefense * 0.5;
            case COMMON -> preAugmentDefense;
            case UNCOMMON -> preAugmentDefense * 1.5;
            case RARE -> preAugmentDefense * 2;
            case LEGENDARY -> preAugmentDefense * 2.5;
            case UNIQUE -> preAugmentDefense * 3.5;
        };
        return Math.max(augmentedDefense - baseDefenseLevel,0);
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
        String coloredName;
        if(rarity == ItemRarity.TRASH){
            modifier = NegativeArmourModifiers.get(random.nextInt(0,NegativeArmourModifiers.size()));
        }
        else {
            modifier = PositiveArmourModifiers.get(random.nextInt(0,PositiveArmourModifiers.size()));
        }
        armourType = ArmourNames.get(random.nextInt(0,ArmourNames.size()));
        finalName = modifier.concat(" ").concat(armourType);
        coloredName = switch (rarity){
            case TRASH -> white.concat(finalName).concat(resetColor);
            case COMMON -> red.concat(finalName).concat(resetColor);
            case UNCOMMON -> blue.concat(finalName).concat(resetColor);
            case RARE -> green.concat(finalName).concat(resetColor);
            case LEGENDARY -> cyan.concat(finalName).concat(resetColor);
            case UNIQUE -> yellow.concat(finalName).concat(resetColor);
        };
        return coloredName;
    }
    //endregion

}
class WorldManager {
    //GOD to the world an instance should be made on game initialization.
    //region creatureNaming
   private final List<String> attributes = Arrays.asList("Oscillating","Romantic","Clumsy","Ferocious","Persnickety","Goopy","Pounding","Dramatic","Bumbling","Slithering");
   private final List<String> Creatures = Arrays.asList("Phoenix","Shark","Bear","Penguin","Kitten","Alien","Unicorn","Eel","Raptor","Dragon");
    //endregion
    private static final WorldManager PublicWorldManager = new WorldManager();
    private final Random random = new Random();
    WorldManager() {
    }

    public static WorldManager getPublicWorldManager() {
        return PublicWorldManager;
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
        return new Entity(isBoss,isEscapable,trueName,entityExperience,entityHealth,entityBaseDamage,entityCriticalHitChance,entityCriticalMultiplier,entityDefense,isDead);
         //endregion

    }
    //region entity generation algorithms
    public int GenerateEntityHealth(double playerLevel){
        int randomMod1 = 10 + (random.nextInt((int) (2 * playerLevel), (int) (8 * playerLevel)));
        //refine as health will normally be higher that desired
        return (int) (randomMod1 - ((random.nextInt((int) (0.5 * playerLevel), (int) (8 * playerLevel))) % (playerLevel / random.nextInt(1, 6))));
    }
    public int GenerateEntityBaseDamage(double playerLevel) {
        int startDamage = 2 + (int)(playerLevel/2);
        boolean modifier = random.nextBoolean();
        int damageAdjust = (int) ((playerLevel*10)/(15));
        return modifier ? startDamage + damageAdjust: (int) ((startDamage - damageAdjust) + playerLevel/1.3);
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
        int[] bossHits = new int[20];
        bossHits[0] = 42;
        if ( killCount % 3 == 0){
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
        return attribute.concat(" ").concat(creature);
    }
    public double CalculateExperienceDrop(int health,double playerLevel){
        return health/(60 + playerLevel*3.2);
    }



    //endregion
}
//endregion
class Entity {
    private final Random rand = new Random();
    private int entityHealth;
    private final int entityBaseDamage;
    private final int entityMaxHealth;
    private final int entityCriticalHitChance;
    private final double entityCriticalMultiplier;
    private final int entityDefense;
    private final boolean isBoss;
    private final boolean isEscapable;
    private boolean isAbandoned;
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
        this.isAbandoned = false;
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
    public boolean isAbandoned() {
        return isAbandoned;
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
    public void abandon(){
        this.isAbandoned = true;
    }
    //endregion
    //region calculate and checker functions
    public int CalculateDamageOut(){
        //TODO find final way for consistently variable damage output.
        int criticalHitTarget = 100 - entityCriticalHitChance;
        boolean criticalHit = (rand.nextInt(100) >= criticalHitTarget);
        return criticalHit ? (int) (entityBaseDamage * entityCriticalMultiplier) : entityBaseDamage;
    }
    public void DamageEntity(int rawDamageIn) {
        int health = getEntityHealth();
        int defense = getEntityDefense();
        this.entityHealth = (health - (rawDamageIn - defense));
        DeathCheck();
    }
    public void DeathCheck(){
        if(entityHealth <= 0) this.isDead = true;
    }


    public boolean AttemptToFlee(){
        if(isBoss){
            return false;
        }
        else {
            int escapeChance =  (100 - (int)(entityHealth/(double)entityMaxHealth * 100));
            int escapeRoll = rand.nextInt(100)+1;
            return escapeRoll <= escapeChance;

        }
    }
    public void AbandonEntity(){
        this.isAbandoned = true;
    }
    //endregion
    //region utilities
    public void PrintAllStats(){
        System.out.println("Name is: " + entityName);
        System.out.println("Health: " + entityHealth);
        System.out.println("Max Health is: " + entityMaxHealth);
        System.out.println("Base Damage is: " + entityBaseDamage);
        System.out.println("defense is: " + entityDefense);
        System.out.println("experience is: " + entityExperience);
    }
    public void PlayerCheck(){
        System.out.println("Name is: " + entityName);
        System.out.println("Health: " + entityHealth);
        System.out.println("Max Health: " + entityMaxHealth);
    }


    //endregion
}
//region items
class Item{
    private final ItemRarity rarity;
    private final String itemName;
    public Item(String itemName,ItemRarity rarity){
        this.itemName = itemName;
        this.rarity = rarity;
    }
    public String getItemName(){
        return itemName.stripLeading();
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
    public void PrintArmourStats(){
        System.out.println(getItemName() + "\nDefense: " + getDefense() + "\nDodge Chance: " + getDodgeChance() + "\nRarity: " + getRarity() );
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

//endregion
class Player{
    //TODO add view inventory and inventory sort options;
    /**
     *use this to set the player default values
     * ensure that player level is not 0 or negative
     * must start at 0.5 or more
     */
    private static HashMap<Item,Integer> playerInv = new HashMap<>();
    public CoinManager coinManager;
    private static  Player  publicPlayer = new Player(50,50,0,0.5,new Weapon("Fists",10,0,1,ItemRarity.TRASH),new Armour("Leather Apron",0,0,ItemRarity.TRASH),new CoinManager(0,0,0,0));

    //region player attributes
    private int maxHealth;
    private int currentHealth;
    private int entitiesDefeated;
    private double playerLevel;
    private Weapon equippedWeapon;
    private Armour equippedArmour;
   private ArrayList<Weapon> armory;
   private ArrayList<Armour> closet;

   private HashMap<Item,Integer> inventory;
   //endregion-
    public Player (int maxHealth,int currentHealth,int entitiesDefeated,double playerLevel,Weapon equippedWeapon,Armour equippedArmour, CoinManager coinManager){
        this.maxHealth = maxHealth;
        this.currentHealth = currentHealth;
        this.entitiesDefeated = entitiesDefeated;
        this.playerLevel = playerLevel;
        this.coinManager = coinManager;
        this.equippedWeapon = equippedWeapon;
        this.equippedArmour = equippedArmour;


    }

    public static Player getPublicPlayer() {
        return publicPlayer;
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
    public void setPlayerLevel(double newPlayerLevel){
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
        char firstCharOfItemName = item.getItemName().toLowerCase().charAt(5);
        boolean presentationModifier = firstCharOfItemName =='a'||firstCharOfItemName =='e'||firstCharOfItemName =='i'||firstCharOfItemName == 'o'|| firstCharOfItemName == 'u';
       System.out.println((presentationModifier ? "an " : "a ") + item.getItemName() + " was added to your inventory.");
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
    public void AddExp(double amount){
        playerLevel += amount;
        //todo add thing that checks level up for healing but that can come later.
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
        damagePlayer(Math.max(rawDamageIn - defense, 0));
    }
}