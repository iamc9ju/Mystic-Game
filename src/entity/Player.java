package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player  extends Entity{

    KeyHandler keyH;
    public final int screenX; //คืออะไร
    public final int screenY; //คืออะไร
    public int standCounter = 0; //เอาไว้หน่ววยเวลาตอนปล่อยปุ่ม
    boolean moving = false;
    int pixelCounter = 0;


    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;
        screenX = (gp.getScreenWidth()/2) - gp.getTileSize()/2;
        screenY = (gp.getScreenHeight()/2) -gp.getTileSize()/2;

        solidArea = new Rectangle(1,1,46,46); //x,y,width,height;

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

        down1 = setup("/player/sword_down_1");
        down2 = setup("/player/sword_down_2");
        right1 = setup("/player/sword_right_1");
        right2 = setup("/player/sword_right_2");
        left1 = setup("/player/sword_left_1");
        left2 = setup("/player/sword_left_2");
        up1 = setup("/player/sword_up_1");
        up2 = setup("/player/sword_up_2");
    }

    public BufferedImage setup(String imagePath){
        UtilityTool utilityTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = utilityTool.scaleImage(image,gp.getTileSize(),gp.getTileSize());
        }catch (IOException e){
            e.printStackTrace();
        }
        return  image;
    }
    public void update() {

        if (moving == false) {
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

                moving = true;

                //Check tile collision
                collisionOn = false;
                gp.collisionChecker.checkTile(this);


                //Check object colliosion
                int objIndex = gp.collisionChecker.checkObject(this,true);
                pickUpObject(objIndex);

                //CHECK NPC COLLISION
                int npcIndex = gp.collisionChecker.checkEntity(this,gp.npc);
                interactNPC(npcIndex);
            }
        }else{
//            standCounter++;
//            if(standCounter > 20){
//                spriteNum = 1; //เมื่อปล่อยปุ่มตัวละครจะอยู่ท่าหยุดนิ่ง
//                standCounter = 0;
//            }
        }

        if(moving == true){
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
            pixelCounter += speed;

            if(pixelCounter == 48){ //ทำใหตัวละครเคลื่อนที่ทีละ tile
                moving = false;
                pixelCounter = 0;
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
        g2.drawImage(image,screenX,screenY,null);
        g2.setColor(Color.red); // set solidArea color red
        g2.drawRect(screenX+solidArea.x,screenY+solidArea.y,solidArea.width,solidArea.height); //วาด solidArea
    }

    public void pickUpObject(int i){
        if( i != 999){

        }
    }

    public void interactNPC(int i){
        if(i != 999){
            System.out.println("You are hitting an npc!");
        }
    }
}