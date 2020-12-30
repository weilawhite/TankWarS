import object.Direction;

import java.awt.*;

public class PlayerTank extends Tank implements SuperFire{
    public PlayerTank(int x, int y, Direction direction, Image[] image) {
        super(x, y, direction, image);
    }

    @Override
    public void superfire() {
        for (Direction dir:Direction.values()
             ) {
            Bullet bullet = new Bullet(x + (width - TankGame.getGameClient().getBulletImage()[0].getWidth(null)) / 2
                    , y + (height - TankGame.getGameClient().getBulletImage()[0].getHeight(null)) / 2, dir, enemy,
                    TankGame.getGameClient().getBulletImage());

            TankGame.getGameClient().addGameObjects(bullet);
        }
    }
}
