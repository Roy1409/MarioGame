import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GraphicsPanel extends JPanel implements ActionListener, KeyListener, MouseListener {
    private BufferedImage background;
    private Timer timer;
    private Player player;
    private Player1 player1;
    private Enemy goomba;
    private boolean[] pressedKeys;
    private ArrayList<Coin> coins;
    private boolean flip;

    public GraphicsPanel() {
        timer = new Timer(2, this);
        timer.start();
        try {
            background = ImageIO.read(new File("src/background.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        player = new Player();
        player1 = new Player1();
        goomba = new Enemy();
        coins = new ArrayList<>();
        pressedKeys = new boolean[128]; // 128 keys on keyboard, max keycode is 127
        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true); // this line of code + one below makes this panel active for keylistener events
        requestFocusInWindow(); // see comment above
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // the order that things get "painted" matter; we paint the background first
        g.drawImage(background, 0, 0, null);
        g.drawImage(player.getPlayerImage(), player.getxCoord(), player.getyCoord(), null);
        g.drawImage(player1.getPlayerImage(), player1.getxCoord(), player1.getyCoord(), null);
        g.drawImage(goomba.getPlayerImage(),goomba.getxCoord(),goomba.getyCoord(), null);


        // this loop does two things:  it draws each Coin that gets placed with mouse clicks,
        // and it also checks if the player has "intersected" (collided with) the Coin, and if so,
        // the score goes up and the Coin is removed from the arraylist
        for (int i = 0; i < coins.size(); i++) {
            Coin coin = coins.get(i);
            g.drawImage(coin.getImage(), coin.getxCoord(), coin.getyCoord(), null); // draw Coin
            if (player.playerRect().intersects(coin.coinRect())) { // check for collision
                player.collectCoin();
                coins.remove(i);
                i--;
            }
            if (player1.playerRect().intersects(coin.coinRect())) { // check for collision
                player1.collectCoin();
                coins.remove(i);
                i--;
            }
        }




        // draw score
        g.setFont(new Font("Courier New", Font.BOLD, 24));
        g.drawString("Mario's Score: " + player.getScore(), 20, 40);
        // draw score
        g.setFont(new Font("Courier New", Font.BOLD, 24));
        g.drawString("Luigi's Score: " + player1.getScore(), 600, 40);


        //left key
        if (pressedKeys[37]) {
            player1.faceLeft();
            player1.moveLeft();
        }

        //right key
        if (pressedKeys[39]) {
            player1.faceRight();
            player1.moveRight();
        }

        //up key
        if (pressedKeys[38]) {
            player1.moveUp();
        }

        //down key
        if (pressedKeys[40]) {
            player1.moveDown();
        }

        // player moves left (A)
        if (pressedKeys[65]) {
            player.faceLeft();
            player.moveLeft();
        }

        // player moves right (D)
        if (pressedKeys[68]) {
            player.faceRight();
            player.moveRight();
        }

        // player moves up (W)
        if (pressedKeys[87]) {
            player.moveUp();
        }

        // player moves down (S)
        if (pressedKeys[83]) {
            player.moveDown();
        }
    }

    // ActionListener interface method
    @Override
    public void actionPerformed(ActionEvent e) {

        if (goomba!=null) {
            if (player1.getScore()!=0) {
            if (player1.playerRect().intersects(goomba.enemyRect())) {
                player1.intersect();}
            }
            if (player.getScore()!=0) {
                if (player.playerRect().intersects(goomba.enemyRect())) {
                    player.intersect();
                }
            }
            if (!flip) {
                goomba.setxCoord(goomba.getxCoord() -1 );
            }
            if (goomba.getxCoord() == 0) {
                flip = true;
            }
            if (flip) {
                goomba.setxCoord(goomba.getxCoord() + 1);
            }
            if (goomba.getxCoord()==900) {
                flip=false;
            }


        }

        repaint();
    }

    // KeyListener interface methods
    @Override
    public void keyTyped(KeyEvent e) { } // unimplemented

    @Override
    public void keyPressed(KeyEvent e) {
        // see this for all keycodes: https://stackoverflow.com/questions/15313469/java-keyboard-keycodes-list
        // A = 65, D = 68, S = 83, W = 87, left = 37, up = 38, right = 39, down = 40, space = 32, enter = 10
        int key = e.getKeyCode();
        pressedKeys[key] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        pressedKeys[key] = false;
    }

    // MouseListener interface methods
    @Override
    public void mouseClicked(MouseEvent e) { }  // unimplemented because
            // if you move your mouse while clicking, this method isn't
            // called, so mouseReleased is best

    @Override
    public void mousePressed(MouseEvent e) { } // unimplemented

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {  // left mouse click
            Point mouseClickLocation = e.getPoint();
            Coin coin = new Coin(mouseClickLocation.x, mouseClickLocation.y);
            coins.add(coin);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) { } // unimplemented

    @Override
    public void mouseExited(MouseEvent e) { } // unimplemented
}
