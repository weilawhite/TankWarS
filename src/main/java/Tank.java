import object.Direction;
import object.GameObject;

import java.awt.*;

public class Tank extends GameObject {
    private int speed;
    private Direction direction;
    protected boolean dirs[] = new boolean[4];

    public Direction getDirection() {
        return direction;
    }

    protected boolean enemy;

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Tank(int x, int y, Direction direction, Image[] image) {
        this(x, y, direction, false, image);
    }

    public Tank(int x, int y, Direction direction, boolean enemy, Image[] image) {
        super(x, y, image);
        this.direction = direction;
        speed = 8;
        this.enemy = enemy;
    }

    public boolean isEnemy() {
        return enemy;
    }

    public boolean[] getDirs() {
        return dirs;
    }

    public void move() {
        oldX = x;
        oldY = y;

        switch (direction) {
            case UP:
                y -= speed;
                break;
            case DOWN:
                y += speed;
                break;
            case LEFT:
                x -= speed;
                break;
            case RIGHT:
                x += speed;
                break;
            case UP_LEFT:
                y -= speed;
                x -= speed;
                break;
            case UP_RIGHT:
                y -= speed;
                x += speed;
                break;
            case DOWN_LEFT:
                y += speed;
                x -= speed;
                break;
            case DOWN_RIGHT:
                y += speed;
                x += speed;
                break;
        }

        collision();
    }

    public void fire() {
        Bullet bullet = new Bullet(x + (width - TankGame.getGameClient().getBulletImage()[0].getWidth(null)) / 2
                , y + (height - TankGame.getGameClient().getBulletImage()[0].getHeight(null)) / 2, direction, enemy,
                TankGame.getGameClient().getBulletImage());

        TankGame.getGameClient().addGameObjects(bullet);

    }

    //邊界碰撞
    public boolean collisionBound() {
        boolean isCollision = false;
        if (x < 0) {
            x = 0;
            isCollision = true;
        } else if (x > TankGame.getGameClient().getScreenWidth() - width) {
            x = TankGame.getGameClient().getScreenWidth() - width;
            isCollision = true;
        }

        if (y < 0) {
            y = 0;
            isCollision = true;
        } else if (y > TankGame.getGameClient().getScreenHeight() - height) {
            y = TankGame.getGameClient().getScreenHeight() - height;
            isCollision = true;
        }

        return isCollision;
    }

    //碰撞偵測
    public boolean collision() {
        boolean isCollision=false;

        isCollision=collisionBound();
        //如果撞到邊 就不會進是否撞上物件的判斷
        if(!isCollision){
            isCollision=collisionObject();
        }

        return isCollision;
    }

    //碰撞物件
    public boolean collisionObject() {

        boolean isCollision=false;

        for (GameObject object : TankGame.getGameClient().getGameObjects()) {
            if (object != this && getRectangle().intersects(object.getRectangle())) {
                System.out.println("hit");
                x = oldX;
                y = oldY;
                isCollision=true;
            }
        }
        return isCollision;
    }


    //方向判定
    private void determineDirection() {
        if (dirs[0] && !dirs[1] && !dirs[2] && !dirs[3]) {
            direction = Direction.UP;
        } else if (!dirs[0] && dirs[1] && !dirs[2] && !dirs[3]) {
            direction = Direction.DOWN;
        } else if (!dirs[0] && !dirs[1] && dirs[2] && !dirs[3]) {
            direction = Direction.LEFT;
        } else if (!dirs[0] && !dirs[1] && !dirs[2] && dirs[3]) {
            direction = Direction.RIGHT;
        } else if (dirs[0] && !dirs[1] && dirs[2] && !dirs[3]) {
            direction = Direction.UP_LEFT;
        } else if (dirs[0] && !dirs[1] && !dirs[2] && dirs[3]) {
            direction = Direction.UP_RIGHT;
        } else if (!dirs[0] && dirs[1] && !dirs[2] && dirs[3]) {
            direction = Direction.DOWN_RIGHT;
        } else if (!dirs[0] && dirs[1] && dirs[2] && !dirs[3]) {
            direction = Direction.DOWN_LEFT;
        }
    }

    public void draw(Graphics g) {
        if (isRunning()) {
            determineDirection();
            move();
        }
        g.drawImage(image[direction.ordinal()], x, y, null);
    }

    public boolean isRunning() {
        for (int i = 0; i < dirs.length; i++) {
            if (dirs[i]) {
                return true;
            }
        }

        return false;
    }
}
