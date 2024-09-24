package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player  extends Entity{

    GamePanel gp;
    KeyHandler keyH;



    public final int screenX; //คืออะไร
    public final int screenY; //คืออะไร
    public int hasKey = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = (gp.getScreenWidth()/2) - gp.getTileSize()/2;
        screenY = (gp.getScreenHeight()/2) -gp.getTileSize()/2;

        solidArea = new Rectangle(10,20,32,32); //x,y,width,height;

        //สำหรับ Object Detection
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;



        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.getTileSize() * 23;
        worldY= gp.getTileSize() * 21;
        speed = 4;
        direction = "down";
    }
    public void getPlayerImage() {
        try {
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void update() {

        if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
            if (keyH.upPressed == true) {
                direction = "up";

            } else if (keyH.downPressed == true) {
                direction = "down";

            } else if (keyH.leftPressed == true) {
                direction = "left";

            } else if (keyH.rightPressed == true) {
                direction = "right";

            }

            //Check tile collision
            collisionOn = false;
            gp.collisionChecker.checkTile(this);


            //Check object colliosion
            int objIndex = gp.collisionChecker.checkObject(this,true);
            pickUpObject(objIndex);

            // if clooisionOn is false player can move
            if(collisionOn == false){
                switch (direction){
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }
    public void draw(Graphics2D g2) {
//		g2.setColor(Color.white);
//		g2.fillRect(this.x, this.y, gp.getTileSize(),gp.getTileSize());
        BufferedImage image = null;

        switch(direction) {
            case "up":
                if(spriteNum ==1 ) {
                    image = up1;
                }
                if(spriteNum ==2 ) {
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum ==1 ) {
                    image = down1;
                }
                if(spriteNum ==2 ) {
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum ==1 ) {
                    image = left1;
                }
                if(spriteNum ==2 ) {
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum ==1 ) {
                    image = right1;
                }
                if(spriteNum ==2) {
                    image = right2;
                }
                break;
        }
        g2.drawImage(image,screenX,screenY,gp.getTileSize(),gp.getTileSize(),null);
    }

    public void pickUpObject(int i){
        if(i != 999){
            String objectName = gp.obj[i].name;

            switch (objectName){
                case "Key":
                    hasKey++;
                    gp.playMusicOneTime(1);
                    gp.obj[i] = null;
                    gp.ui.showMessage("You got a key !");
                    break;
                case "Door":
                    if(hasKey > 0){
                        gp.playMusicOneTime(3);
                        gp.obj[i] = null;
                        hasKey--;
                    }
                    System.out.println("Key : "+hasKey);
                    break;
                case "Boots":
                    gp.playMusicOneTime(2);
                    gp.player.speed +=2;
                    gp.obj[i] = null;
                    break;

            }
        }
    }
}