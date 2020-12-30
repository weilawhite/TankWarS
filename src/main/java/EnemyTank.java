import object.Direction;

import java.awt.*;
import java.util.Random;

public class EnemyTank extends Tank {
    public EnemyTank(int x, int y, Direction direction, boolean enemy, Image[] image) {
        super(x, y, direction, enemy, image);
    }

    public void ai() {
        Random random = new Random();
        //5%進入
        if (random.nextInt(20) == 1) {
            if (random.nextBoolean()) {
                // 50%機率不做事情
                return;
            }
            //50%機率換新方向
            getNewDirection();
        }
        if (random.nextInt(20) == 1) {
            fire();
        }
    }

    private void getNewDirection() {
        Random random = new Random();
//
//重置四方向為false
        for (int i = 0; i <= 3; i++) {
            dirs[i] = false;
        }

        int dir = random.nextInt(Direction.values().length);

        if (dir <= Direction.RIGHT.ordinal()) {
            dirs[dir] = true;
        } else {//dirs 0上 1下 2左 3右
            if (dir == Direction.UP_LEFT.ordinal()) {
                dirs[0] = true;
                dirs[2] = true;
            }
            if (dir == Direction.DOWN_LEFT.ordinal()) {
                dirs[1] = true;
                dirs[2] = true;
            }
            if (dir == Direction.UP_RIGHT.ordinal()) {
                dirs[0] = true;
                dirs[3] = true;
            }
            if (dir == Direction.DOWN_RIGHT.ordinal()) {
                dirs[1] = true;
                dirs[3] = true;
            }
        }
    }

    @Override
    public boolean collision() {
        if (super.collision()) {
            getNewDirection();
            return true;
        }
        return false;
    }

    @Override
    public void draw(Graphics g) {
        ai();
        super.draw(g);
    }
}
