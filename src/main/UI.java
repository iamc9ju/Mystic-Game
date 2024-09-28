package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

//ควบคุมการเเสดงผลที่เด้งขึ้นมา

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B;//เก็บ font แบบ arial;
//    BufferedImage keyImage; //เก็บภาพกุญแจ
    public boolean messageOn = false; //เช็คว่าจะให้ข้อความโชว์บนจอไหม
    public String message = ""; //ข้อความที่แสดงบนจอ
    public int messageCounter = 0;
    public boolean gameFinished = false;//เกมจบ
    public String currentDialogue = "";



    public  UI (GamePanel gp){
        this.gp = gp;
        arial_40 = new Font("Arial",Font.PLAIN,40);
        arial_80B = new Font("Arial",Font.BOLD,80);
//        OBJ_Key key = new OBJ_Key(gp);
//        keyImage = key.image;
    }

    public void showMessage(String text){
        message = text;
        messageOn = true;
        //แสดงข้อความทางหน้าจอ
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);

        if(gp.gameState == gp.playState){
            //Do playState stuff later
        }
        if(gp.gameState == gp.pauseState){
            drawPauseScreen(g2);
        }
        if(gp.gameState == gp.dialoguesState){
            drawDialoguesScreen();
        }
    }

    public void drawPauseScreen(Graphics2D g2){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.getScreenHeight()/2;

        g2.drawString(text,x,y);
    }

    public void drawDialoguesScreen(){
        //WINDOW
        int x = gp.getTileSize()*2;
        int y = gp.getTileSize()/2;
        int width = gp.getScreenWidth() - (gp.getTileSize()*4);
        int height = gp.getTileSize() * 4;
        drawSubWindow(x,y,width,height);

        x += gp.getTileSize();
        y += gp.getTileSize();
        g2.drawString(currentDialogue,x,y);

    }

    public void drawSubWindow(int x,int y,int width,int height){
        Color color = new Color(0,0,0,220);
        g2.setColor(color);
        g2.fillRoundRect(x,y,width,height,35,35);

        color = new Color(255,255,255);
        g2.setColor(color);
        g2.setStroke(new BasicStroke(5)); //setความกว้างของกรอบสี่เหลี่ยม
        g2.drawRoundRect(x+5,y+5,width-10,height-10,25,25); //วาดกรอบ4เหลี่ยมสีขาว
    }

    public int getXforCenteredText(String text){
        int length ;
        FontMetrics fontMetrics = g2.getFontMetrics(arial_80B);
        length = fontMetrics.stringWidth(text);
        return gp.getScreenWidth()/2 - length/2;
    }
}
