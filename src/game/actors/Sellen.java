package game.actors;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.BuyToolAction;
import game.items.Pickaxe;
import game.actions.BuyWeaponAction;
import game.actions.ListenAction;
import game.capabilities.Status;
import game.weapons.Broadsword;
import game.weapons.DragonslayerGreatsword;
import game.weapons.Katana;
import java.util.Arrays;
import java.util.List;

/**
 * Sellen is a peaceful NPC who wanders freely and has two fixed
 * monologue lines, always available when listened to.
 * @Author Muhammad Mubashir Shah (Modified by Khanh Le - Version 2)
 */
public class Sellen extends PeacefulCharacter implements Talkable {

    /** Constructs Sellen with 150 HP. */
    public Sellen() {
        super("Sellen", 's', 150);
    }

    /**
     * Allows the player (actor with IS_PLAYER) to add ListenAction and BuyWeaponAction.
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = super.allowableActions(otherActor, direction, map);
        if (otherActor.hasCapability(Status.IS_PLAYER)) {
            actions.add(new ListenAction(this));

            actions.add(new BuyWeaponAction(new Broadsword(), 100, "Sellen"));
            actions.add(new BuyWeaponAction(new DragonslayerGreatsword(), 1500, "Sellen"));
            actions.add(new BuyWeaponAction(new Katana(), 500, "Sellen")); // This weapon is only sold by Sellen

            // Allows sellen to sell a tool
            actions.add(new BuyToolAction(new Pickaxe(), 100));
        }
        return actions;
    }

    /**
     * Returns Sellen’s two fixed monologue lines.
     *
     * @param listener ignored (all lines always apply)
     * @param map ignored
     * @return a list containing both legacy lines
     */
    @Override
    public List<String> getTalkingLines(Actor listener, GameMap map) {
        return Arrays.asList(
                "The academy casts out those it fears. Yet knowledge, like the stars, cannot be bound forever.",
                "You sense it too, don’t you? The Glintstone hums, even now."
        );
    }
}
