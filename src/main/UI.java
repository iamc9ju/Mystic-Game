package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;

//ควบคุมการเเสดงผลที่เด้งขึ้นมา

public class UI {
    GamePanel gp;
    Font arial_40;//เก็บ font แบบ arial;
    BufferedImage keyImage; //เก็บภาพกุญแจ
    public boolean messageOn = false; //เช็คว่าจะให้ข้อความโชว์บนจอไหม
    public String message = ""; //ข้อความที่แสดงบนจอ

    public  UI (GamePanel gp){
        this.gp = gp;
        arial_40 = new Font("Arial",Font.PLAIN,40);
        OBJ_Key key = new OBJ_Key();
        keyImage = key.image;
    }

    public void showMessage(String text){

        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2){
        g2.setFont(arial_40);
        g2.setColor(Color.white);
        g2.drawImage(keyImage,gp.getTileSize()/2,gp.getTileSize()/2,gp.getTileSize(),gp.getTileSize(),null); //พื้นที่้ที่แสดงผลบนหน้าจอ
        g2.drawString("X " + gp.player.hasKey,74,65);

        //Message
        if(messageOn == true){
            g2.setFont(g2.getFont().deriveFont(30F)); //getFont แล้วเซตขนาดเป็น 30 แทน
            g2.drawString(message,gp.getTileSize()/2,gp.getTileSize()*5);
        }
    }
}
