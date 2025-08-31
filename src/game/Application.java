package game;

import java.util.Arrays;
import java.util.List;

import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.FancyGroundFactory;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.positions.World;

import game.actors.*;
import game.behaviours.PrioritisedBehaviourSelection;
import game.behaviours.RandomBehaviourSelection;
import game.displays.FancyMessage;
import game.grounds.*;
import game.grounds.cursed.Blight;
import game.items.*;
import game.items.eggs.*;
import game.seeds.*;


/**
 * The main class to setup and run the game.
 * @author Adrian Kristanto
 */
public class Application {

    /**
     * Generates the world and starts the game
     * Initialises the players and game objects
     * @param args
     */
    public static void main(String[] args) {

        World world = new World(new Display());

        FancyGroundFactory groundFactory = new FancyGroundFactory(new Blight(),
                new Wall(), new Floor(), new Soil());

        // ─── VALLEY OF THE INHERITREE MAP ───────────────────────────────────────
        List<String> map = Arrays.asList(
                "xxxx...xxxxxxxxxxxxxxxxxxxxxxx........xx",
                "xxx.....xxxxxxx..xxxxxxxxxxxxx.........x",
                "..........xxxx....xxxxxxxxxxxxxx.......x",
                "....xxx...........xxxxxxxxxxxxxxx.....xx",
                "...xxxxx...........xxxxxxxxxxxxxx.....xx",
                "...xxxxxxxxxx.......xxxxxxxx...xx......x",
                "....xxxxxxxxxx........xxxxxx...xxx......",
                "....xxxxxxxxxxx.........xxx....xxxx.....",
                "....xxxxxxxxxxx................xxxx.....",
                "...xxxx...xxxxxx.....#####.....xxx......",
                "...xxx....xxxxxxx....#___#.....xx.......",
                "..xxxx...xxxxxxxxx...#___#....xx........",
                "xxxxx...xxxxxxxxxx...##_##...xxx.......x",
                "xxxxx..xxxxxxxxxxx.........xxxxx......xx",
                "xxxxx..xxxxxxxxxxxx.......xxxxxx......xx");

        GameMap gameMap = new GameMap("Valley of the Inheritree", groundFactory, map);
        world.addGameMap(gameMap);

        // ─── LIMVELD WORLD MAP ───────────────────────────────────────────────────
        List<String> limveldMap = Arrays.asList(
                ".............xxxx",
                "..............xxx",
                "................x",
                ".................",
                "................x",
                "...............xx",
                "..............xxx",
                "..............xxx",
                "..............xxx",
                ".............xxxx",
                ".............xxxx",
                "....xxx.....xxxxx",
                "....xxxx...xxxxxx");

        GameMap limveld = new GameMap("The Limveld World", groundFactory, limveldMap);
        world.addGameMap(limveld);

        // ─── HOLLOW ROOTS MAP ────────────────────────────────────────────────────
        List<String> HollowRootsMap = Arrays.asList(
                ".................",
                ".................",
                ".................",
                ".................",
                ".................",
                ".................");

        GameMap hollowRoots = new GameMap("Hollow Roots", groundFactory, HollowRootsMap);
        world.addGameMap(hollowRoots);


        // ─── LIMVELD PORTALS BETWEEN VALLEY & LIMVELD ───────────────────────────
        // (SOLID: Single Responsibility Principle - LimveldPortal handles just teleportation)
        Location valleyTeleportLocation = gameMap.at(22, 13);
        Location limveldTeleportLocation = limveld.at(5, 5);

        // Create bidirectional portals
        // (Design Constraint: One portal can have multiple destinations by creating multiple LimveldPortal instances)
        gameMap.at(22, 13).setGround(new LimveldPortal(limveldTeleportLocation));
        limveld.at(5, 5).setGround(new LimveldPortal(valleyTeleportLocation));

            // ─── ADD BOSS TO LIMVELD ──────────
            limveld.at(8, 6).addActor(new BedOfChaosBoss());


        // ─── GLOWING SINKHOLES BETWEEN VALLEY & HOLLOW ROOTS ───────────────────
        Location vallySinkhole = gameMap.at(10, 3);
        Location rootSinkhole = hollowRoots.at(3, 4);

        vallySinkhole.setGround(new GlowingSinkhole(rootSinkhole));
        rootSinkhole.setGround(new GlowingSinkhole(vallySinkhole));

            // Initial Glyphs in Glowing Roots
            for (int i = 1; i < 8; i++) {
                hollowRoots.at(i, 1).setGround(new ShimmeringGlyph());
            }


        // ─── INITIAL SETUP ───────────────────────────────────────────
        // BEHOLD, ELDEN THING!
        for (String line : FancyMessage.TITLE.split("\n")) {
            new Display().println(line);
            try {
                Thread.sleep(200);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        // Valley of the Inheritree Setup
        Farmer farmer = new Farmer(); // player
        world.addPlayer(farmer, gameMap.at(23, 10));


        // ─── NPCs IN VALLEY ─────────────────────────────────────────────────────
        gameMap.at(19, 10).addActor(new GoldenBeetle());
        gameMap.at(15,  9).addActor(new Sellen());
        gameMap.at(15,  8).addActor(new MerchantKale());
        // gameMap.at(25, 13).addActor(new SpiritGoat());
        // gameMap.at(16,  6).addActor(new OmenSheep());
        // gameMap.at(20, 12).addActor(new Guts());


        // ─── ENVIRONMENT SETUP  ─────────────────────────────
        // gameMap.at(25,13).setGround(new Inheritree(new Blight(2)));


        // ─── PORTABLE ITEMS ──────────────────────────────────────
        gameMap.at(24, 11).addItem(new Talisman());


        // ─── INVENTORY SETUP ───────────────────────────────────────
        // farmer.addItemToInventory(new InheritreeSeed(new Blight()));
        // farmer.addItemToInventory(new BloodroseSeed());
        // farmer.addItemToInventory(new OmenSheepEgg());


        // ─── NON-PLAYER NPCs IN LIMVELD ─────────────────────────────────────────
        // Prioritised behaviour:
        // (Design Constraint: Creatures can choose behaviors differently)
        // (SOLID: Liskov Substitution - can substitute different behavior implementations)
        // (SOLID: Dependency Inversion - behaviors depend on abstractions)
        limveld.at( 9, 5).addActor(new OmenSheep( new PrioritisedBehaviourSelection() ));
        limveld.at( 3, 2).addActor(new SpiritGoat( new PrioritisedBehaviourSelection() ));
        limveld.at( 7,10).addActor(new GoldenBeetle(new PrioritisedBehaviourSelection()));

        // Random behaviour:
        limveld.at( 9, 6).addActor(new OmenSheep( new RandomBehaviourSelection() ));
        limveld.at( 3, 3).addActor(new SpiritGoat( new RandomBehaviourSelection() ));
        limveld.at( 7,11).addActor(new GoldenBeetle(new RandomBehaviourSelection()));


        // ─── TEST GROUND FOR REQ4 (commented examples) ──────────────────────────
        gameMap.at(20, 14).setGround(new ArcaneBench());
        // for (int i = 0; i < 10; i++) {
        //     farmer.addItemToInventory(new OrePiece());
        // }
        // for (int i = 0; i < 5; i++) {
        //     farmer.addItemToInventory(new CharmPiece());
        // }


        // ─── START GAME ─────────────────────────────────────────────────────────
        world.run();
    }
}
