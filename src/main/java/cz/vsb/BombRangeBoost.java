package cz.vsb;

public class BombRangeBoost extends Boost {

    private final int rangeIncrease = 2;

    public BombRangeBoost(int duration,GameMap map) {
        super(duration, "/Boosts/BombRangeBoost.png", map);

    }

    @Override
    public void applyEffect(Player player) {
       // player.setBombRange(player.getBombRange() + rangeIncrease);
        player.setBombRange(4);
    }

    @Override
    public void removeEffect(Player player) {
        player.setBombRange(player.getBombRange() - rangeIncrease);
    }
}