package cz.vsb;

public class SpeedBoost extends Boost {

    private final int speedMultiplier = 2;

    public SpeedBoost(int duration,GameMap map) {
        super(duration, "/Boosts/SpeedBoots.png", map);
    }

    @Override
    public void applyEffect(Player player) {
        player.setX((player.getX()-player.getX()%8));
        player.setY(player.getY()-player.getY()%8);

        player.setSpeed(8);

        isUsed = true;
        this.player = player;
    }

    @Override
    public void removeEffect() {
        player.setSpeed(4);
    }


}