package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {
    GamePanel gp;

    public int worldX,worldY;
    public int speed;

    public BufferedImage up1,up2,down1,down2,left1,left2,right1,right2;
    public String direction;

    public int spriteCounter =0;
    public int spriteNum = 1;

    public int solidAreaDefaultX, solidAreaDefaultY; //setDefault
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public boolean collisionOn = false;
    public int actionLockCounter = 0;
    public String dialogues[] = new String[20];
    int dialogueIndex = 0;

    public  Entity (GamePanel gp){
        this.gp = gp;
    }

    public void setAction(){

    }

    public void speak(){

    }
    public void update(){
        setAction();

        collisionOn = false;
        gp.collisionChecker.checkTile(this);
        gp.collisionChecker.checkObject(this,false);
        gp.collisionChecker.checkPlayer(this);


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

    public void draw(Graphics2D g2){
        int screenX = worldX - gp.player.worldX + gp.player.screenX; //ค้นหาตำแหน่งของผู้เล่น
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.getTileSize() > gp.player.worldX - gp.player.screenX && //ถ้าอยู่ในขอบเขตของหน้าจอการเเสดงผลก็จะแสดง npc ขึ้นมา
                worldX - gp.getTileSize() < gp.player.worldX + gp.player.screenX &&
                worldY + gp.getTileSize() > gp.player.worldY - gp.player.screenY &&
                worldY - gp.getTileSize() < gp.player.worldY + gp.player.screenY){

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
}

//เป็น Abstract class ไม่มีอินสแตนซ์

