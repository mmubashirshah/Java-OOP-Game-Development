package game.capabilities;

/**
 * Use this enum class to represent a status.
 * Example #1: if the player is sleeping, you can attack a Status.SLEEP to the player class
 * @author Riordan D. Alfredo (modified by CL_Applied33_GroupC)
 */
public enum Status {
    HOSTILE_TO_ENEMY,
    CURABLE,
    HAS_GRACE,
    EATABLE,
    CURSED,
    IS_PLAYER,
    CAN_BE_FOLLOWED,
    TELEPORTABLE,
    CRAFTING_STATION
}
