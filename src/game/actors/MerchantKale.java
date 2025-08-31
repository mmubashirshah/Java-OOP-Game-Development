package game.actors;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.SellOreAction;
import game.actions.BuyWeaponAction;
import game.actions.ListenAction;
import game.capabilities.Status;
import game.utils.SurroundingUtils;
import game.weapons.Broadsword;
import game.weapons.DragonslayerGreatsword;


import java.util.ArrayList;
import java.util.List;

/**
 * A wandering merchant NPC whose monologues depend on the player's
 * runes, inventory, and proximity to cursed entities.
 * @Author Muhammad Mubashir Shah (Modified by Khanh Le - Version 2)
 */
public class MerchantKale extends PeacefulCharacter implements Talkable {

    /** Constructs Kale with 200 HP. */
    public MerchantKale() {
        super("Merchant Kale", 'k', 200);
    }

    /**
     * Returns the list of actions that the player can perform when adjacent to Merchant Kale.
     *
     * If the otherActor has the IS_PLAYER capability, this method adds:
     * - A ListenAction to hear Kale speak.
     * - BuyWeaponActions for Broadsword and DragonslayerGreatsword.
     * - SellOreActions for each OrePiece in the player's inventory (price: 10 runes)
     *
     * @param otherActor The actor interacting with Kale (usually the Farmer).
     * @param direction  The direction from the actor to Kale.
     * @param map        The GameMap on which the interaction is taking place.
     * @return An ActionList containing all allowable actions for the player at this moment.
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = super.allowableActions(otherActor, direction, map);
        if (otherActor.hasCapability(Status.IS_PLAYER)) {
            actions.add(new ListenAction(this));
            actions.add(new BuyWeaponAction(new Broadsword(), 150, "Kale"));
            actions.add(new BuyWeaponAction(new DragonslayerGreatsword(), 1700, "Kale"));

            // For each OrePiece in the Farmer's inventory, allow selling for 10 runes:
            for (Item item : otherActor.getItemInventory()) {
                if (item.getClass().getSimpleName().equals("OrePiece")) {
                    actions.add(new SellOreAction(item, 10, "Kale"));
                }
            }
        }
        return actions;
    }


    /**
     * Builds Kale’s dynamic monologue pool:
     *  If listener has < 500 runes -> adds hardship line
     *  If listener inventory empty -> adds trinket line
     *  If any adjacent tile is cursed (ground or actor) -> adds weariness line
     *  If none of the above -> adds default line
     *
     * @param listener the actor listening (player)
     * @param map the game map
     * @return list of eligible lines
     */
    @Override
    public List<String> getTalkingLines(Actor listener, GameMap map) {
        List<String> lines = new ArrayList<>();
        Location currentLocation = map.locationOf(this);

        // 1) Runes check
        if (listener.getBalance() < 500) {
            lines.add("Ah, hard times, I see. Keep your head low and your blade sharp.");
        }
        // 2) Inventory empty?
        if (listener.getItemInventory().isEmpty()) {
            lines.add("Not a scrap to your name? Even a farmer should carry a trinket or two.");
        }
        // 3) Cursed surroundings? (used utils instead of helper function)
        if (SurroundingUtils.hasSurroundingCapability(currentLocation, Status.CURSED)) {
            lines.add("Rest by the flame when you can, friend. These lands will wear you thin.");
        }
        // 4) Default
        if (lines.isEmpty()) {
            lines.add("A merchant’s life is a lonely one. But the roads… they whisper secrets to those who listen.");
        }
        return lines;
    }
}
