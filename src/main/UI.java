package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

//ควบคุมการเเสดงผลที่เด้งขึ้นมา

public class UI {
    GamePanel gp;
    Font arial_40, arial_80B;//เก็บ font แบบ arial;
    BufferedImage keyImage; //เก็บภาพกุญแจ
    public boolean messageOn = false; //เช็คว่าจะให้ข้อความโชว์บนจอไหม
    public String message = ""; //ข้อความที่แสดงบนจอ
    public int messageCounter = 0;
    public boolean gameFinished = false;//เกมจบ

    public double playTime = 0;
    DecimalFormat decimalFormat = new DecimalFormat("#0.00"); //ทำให้เวลามีทศนิยมแค่ 2 ตำแหน่ง

    public  UI (GamePanel gp){
        this.gp = gp;
        arial_40 = new Font("Arial",Font.PLAIN,40);
        arial_80B = new Font("Arial",Font.BOLD,80);
        OBJ_Key key = new OBJ_Key(gp);
        keyImage = key.image;
    }

    public void showMessage(String text){
        message = text;
        messageOn = true;
        //แสดงข้อความทางหน้าจอ
    }

    public void draw(Graphics2D g2){

        if(gameFinished == true){ //Game done version 1

            g2.setFont(arial_40);
            g2.setColor(Color.white);

            String text;
            int textLength;
            int x;
            int y;

            text = "You found the treasure!";
            // ใช้ FontMetrics เพื่อหาความกว้างของข้อความ
            FontMetrics metrics = g2.getFontMetrics(arial_40);
            textLength = metrics.stringWidth(text);
            x = (gp.getScreenWidth() - textLength) / 2;
            y = gp.getScreenHeight()/2 - (gp.getTileSize()*3);
            g2.drawString(text, x, y);

            text = "Your Time is :" + decimalFormat.format(playTime) + "!";
            textLength = metrics.stringWidth(text);
            x = (gp.getScreenWidth() - textLength) / 2;
            y = gp.getScreenHeight()/2 + (gp.getTileSize()*4);
            g2.drawString(text, x, y);

            g2.setFont(arial_80B);
            g2.setColor(Color.YELLOW);
            text = "Congratulations!";
            metrics = g2.getFontMetrics(arial_80B);
            textLength=metrics.stringWidth(text);
            x = (gp.getScreenWidth() - textLength) / 2;
            y = gp.getScreenHeight()/2 + (gp.getTileSize()*2);
            g2.drawString(text, x, y);
            gp.gameThread = null;

        }else{
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            g2.drawImage(keyImage,gp.getTileSize()/2,gp.getTileSize()/2,gp.getTileSize(),gp.getTileSize(),null); //พื้นที่้ที่แสดงผลบนหน้าจอ
            g2.drawString("X " + gp.player.hasKey,74,65);

            //Time
            playTime +=(double)1/60;
            g2.drawString("Time: "+decimalFormat.format(playTime), gp.getTileSize()*11,65);

            //Message
            if(messageOn == true){
                g2.setFont(g2.getFont().deriveFont(30F)); //getFont แล้วเซตขนาดเป็น 30 แทน
                g2.drawString(message,gp.getTileSize()/2,gp.getTileSize()*5);

                messageCounter++;

                if(messageCounter > 120){ //ข้อความจะหายไปหลังจาก 2 วิ
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }


    }
}
