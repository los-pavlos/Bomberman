package cz.vsb;

public class BombRangeBoost extends Boost {

    private final int rangeIncrease = 2;

    public BombRangeBoost(int duration,GameMap map) {
        super(duration, "/Boosts/BombRangeBoost.png", map);

    }

    @Override
    public void applyEffect(Player player) {
        SoundManager.playPickupSound();
        player.setBombRange(4);
        isUsed = true;
        this.player = player;
    }

    @Override
    public void removeEffect() {
        player.setBombRange(2);

    }
}