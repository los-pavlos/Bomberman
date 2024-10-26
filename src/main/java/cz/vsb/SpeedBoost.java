package cz.vsb;

public class SpeedBoost extends Boost {

    private final int speedMultiplier = 2;

    public SpeedBoost(int duration,GameMap map) {
        super(duration, "/Boosts/SpeedBoots.png", map);
    }

    @Override
    public void applyEffect(Player player) {
        //  player.setSpeed(player.getSpeed() * speedMultiplier);
        player.setSpeed(8);
    }

    @Override
    public void removeEffect(Player player) {
        player.setSpeed(player.getSpeed() / speedMultiplier);
    }
}