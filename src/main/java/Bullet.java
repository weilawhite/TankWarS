import object.Direction;
import object.GameObject;

import java.awt.*;

public class Bullet extends Tank {
    public Bullet(int x, int y, Direction direction, boolean enemy, Image[] image) {
        super(x, y, direction, enemy, image);
        setSpeed(15);
    }


    @Override
    public void draw(Graphics g) {

        if (!alive) {
            return;
        }

        move();
        collision();
        g.drawImage(image[getDirection().ordinal()], x, y, null);
    }

    public boolean collision() {
        boolean isCollision = false;

        isCollision = collisionBound();
        //如果撞到邊 就不會進是否撞上物件的判斷
        if (!isCollision) {
            isCollision = collisionObject();
        }
        //不管撞上什麼 都要進
        if (isCollision) {
            alive = false;
            return true;
        }

        return false;
    }


    @Override
    public boolean collisionObject() {
        boolean isCollision = false;

        for (GameObject object : TankGame.getGameClient().getGameObjects()) {
            //如果是自己本身
            if (object == this) {
                continue;
            }

            //是否是本身的子彈
            if (object instanceof Tank && isEnemy() == ((Tank) object).isEnemy()) {
                continue;
            }

            //進行碰撞
            if (getRectangle().intersects(object.getRectangle())) {
                if (object instanceof Tank) {
                    object.setAlive(false);
                }
                isCollision = true;

            }
        }
        return isCollision;
    }
}
