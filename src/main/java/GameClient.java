import object.Direction;
import object.GameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;


public class GameClient extends JComponent {

    private int screenWidth;
    private int screenHeight;
    private PlayerTank playerTank;
    private Image[] bulletImage;
    private CopyOnWriteArrayList<GameObject> gameObjects;
    //private ArrayList<GameObject> gameObjects;


    GameClient() {
        this(1024, 800);
    }

    public GameClient(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));

        init();

        new Thread(new Runnable() {

            public void run() {
                while (true) {
                    repaint();
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void addGameObjects(GameObject object) {
        gameObjects.add(object);
    }


    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public Image[] getBulletImage() {
        return bulletImage;
    }

    public CopyOnWriteArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void init() {
        gameObjects = new CopyOnWriteArrayList<>();
        bulletImage = new Image[8];
        Image[] brickImage = {Tools.getImage("brick.png")};
        Image[] playerImage = new Image[8];
        Image[] enemyImage = new Image[8];


        String[] sub = {"U", "D", "L", "R", "LU", "RU", "LD", "RD"};
        for (int i = 0; i < playerImage.length; i++) {
            playerImage[i] = Tools.getImage("itank" + sub[i] + ".png");
            enemyImage[i] = Tools.getImage("etank" + sub[i] + ".png");
            bulletImage[i] = Tools.getImage("missile" + sub[i] + ".png");
        }

        playerTank = new PlayerTank(250, 100, Direction.RIGHT, playerImage);

        gameObjects.add(playerTank);

        int posX = getScreenCenterPosX(screenWidth, 4 * 80);
        int posY = getScreenCenterPosY(screenHeight, 2 * 80);


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                gameObjects.add(new EnemyTank(
                        posX + j * 100, posY + screenHeight / 4 + i * 100, Direction.UP,
                        true, enemyImage));
            }
        }


        gameObjects.add(new Wall(100, 300, false, 15, brickImage));
        gameObjects.add(new Wall(800, 300, false, 15, brickImage));
        gameObjects.add(new Wall(200, 200, true, 17, brickImage));

    }

    public int getScreenCenterPosX(int screenWidth, int objectWidth) {
        return (screenWidth - objectWidth) / 2;
    }

    public int getScreenCenterPosY(int screenHeight, int objectHeight) {
        return (screenHeight - objectHeight) / 2;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, screenWidth, screenHeight);

        for (GameObject gameObject : gameObjects) {
            gameObject.draw(g);
        }

        for (GameObject gameobject : gameObjects) {
            if (!gameobject.isAlive()) {
                gameObjects.remove(gameobject);
            }

        }


        /*
        Iterator<GameObject> iterator = gameObjects.iterator();
        while (iterator.hasNext()) {
            if (!(iterator.next()).isAlive()) {
                iterator.remove();
            }
        }
        */
//確認勝利
        boolean gameWin = true;

        for (GameObject gameobject : gameObjects
        ) {
            if (gameobject instanceof EnemyTank) gameWin = false;
        }
//
        if (gameWin) {
            System.out.println("win");
            //此處加入遊戲重設


        } else {
            //System.out.println(gameObjects.size());
        }
    }

    public void keyPressed(KeyEvent e) {
        //上:0 下: 1 左：2 右:3
        boolean[] dirs = playerTank.getDirs();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                dirs[0] = true;
                break;
            case KeyEvent.VK_DOWN:
                dirs[1] = true;
                break;
            case KeyEvent.VK_LEFT:
                dirs[2] = true;
                break;
            case KeyEvent.VK_RIGHT:
                dirs[3] = true;
                break;
            case KeyEvent.VK_SPACE:
                playerTank.fire();
                break;
            case KeyEvent.VK_A:
                playerTank.superfire();
        }
    }

    public void keyReleased(KeyEvent e) {
        boolean[] dirs = playerTank.getDirs();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                dirs[0] = false;
                break;
            case KeyEvent.VK_DOWN:
                dirs[1] = false;
                break;
            case KeyEvent.VK_LEFT:
                dirs[2] = false;
                break;
            case KeyEvent.VK_RIGHT:
                dirs[3] = false;
                break;
        }
    }

}

