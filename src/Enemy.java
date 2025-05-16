import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Enemy {
    private int xCoord;
    private int yCoord;
    private final int MOVE_AMT = 3;
    private BufferedImage image;

    public Enemy() {
        xCoord = 200; // starting position is (50, 435), right on top of ground
        yCoord = 470;
        try {
        image=ImageIO.read(new File("src\\goomba.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public BufferedImage getPlayerImage() {
        return image;
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public Rectangle enemyRect() {
        int imageHeight = getPlayerImage().getHeight();
        int imageWidth = getPlayerImage().getWidth();
        Rectangle rect = new Rectangle(xCoord, yCoord, imageWidth, imageHeight);
        return rect;
    }
    
    
    public void setxCoord (int x) {
        xCoord=x;
    }
}