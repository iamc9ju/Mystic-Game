package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    //SCREEN SETTING
    final int originalTileSize = 16; //16*16
    final int scale = 3;

    final int tileSize = originalTileSize * scale; //48*48
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol; //หน้าจอ 768 pixels
    final int screenHeight = tileSize * maxScreenRow; //576 pixels

    // WORLD SETTINGS

    public final int maxWorldCol = 50; //เเมพกว้างสุดกี่คอลัม
    public final int maxWorldRow = 50;

    //FPS
    int FPS = 60;

    //System
    TileManager tileManager = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;

    Sound music = new Sound(); //ต้องใช้ 2 อินสแตนเพื่อจัดการกับระบบเสียง
    Sound oneTimeSound = new Sound();

    public CollisionChecker collisionChecker = new CollisionChecker(this); //เช็ตการชนกัน
    public AssetSetter assetSetter = new AssetSetter(this); // Set แมพ
    public UI ui = new UI(this); //ข้อความ , ui ที่จะแสดงขึ้นมาตาม event ต่างๆ

    //Entity and object
    public Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[10]; //แสดง obj ได้พร้อมกัน 10 Object
    public Entity npc[] = new Entity[10]; //แสดง npc , monster บนmap


    //Game State
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight) ); //กำหนดขนาด GamePanel
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); //เปิดการใช้ Double Buffered เพื่อลดการกระพิบของหน้าจอ
        this.addKeyListener(keyH); //สามารถรับ input จากคีย์บอร์ดได้
        this.setFocusable(true);  //สามารถรับ Focus ได้ เช่น การรับอินพุตจากคีย์บอร์ด
        //ถ้าไม่เซต true จะรับค่าจากคีย์บอร์ดไม่ได้
    }

    public void setUpGame(){
        assetSetter.setNpc();
        assetSetter.setObject(); //set object บน map
        playMusic(0); //เล่นเสียงพื้นหลัง
        stopMusic();
        gameState = playState;
    }

    public void startGameThread() {
        //การใช้ Thread ช่วยให้การทำงานของเกม ดำเนินไปอย่างต่อเนื่อง
        gameThread = new Thread(this);
        gameThread.start();
    }

    //get-method
    public int getTileSize() {
        return this.tileSize;
    }
    public int getMaxScreenCol(){
        return  this.maxScreenCol;
    }
    public int getMaxScreenRow(){
        return this.maxScreenRow;
    }
    public int getScreenWidth(){return this.screenWidth;}
    public int getScreenHeight(){return this.screenHeight;}


    @Override
    public void run() { //ยืดหยุ่นมากกว่า
        double  drawInterval = 1000000000 / FPS; // นาโนวินาทีต่อเฟรม
        double delta = 0;
        long lastTime = System.nanoTime(); // เวลาปัจจุบัน
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval; // ตัวแปร delta จะใช้ในการติดตามว่าเวลาที่ผ่านไปมากพอที่จะวาดเฟรมใหม่หรือยัง (มีค่า >= 1).
            timer +=(currentTime - lastTime);
            lastTime = currentTime;
            // อัปเดตและวาดเมื่อ delta >= 1 (เฟรมใหม่)
            if (delta >= 1) {
                update(); // ฟังก์ชันอัปเดตการทำงานของเกม
                repaint(); // ฟังก์ชันวาดบนจอ
                delta--; // ลด delta เพื่อรอเฟรมถัดไป
                drawCount++;
            }
            if(timer >= 1000000000) {
                System.out.println("FPS : " + drawCount);
                drawCount =0;
                timer =0;
            }
            try {
                // ทำให้ Thread หยุดพักเพื่อให้ CPU ทำงานเบาลง
                Thread.sleep(1); // พัก 1 มิลลิวินาที
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //
    public void update() {
        if(gameState == playState){
            //PLAYER
            player.update();
            //NPC
            for(int i=0;i<npc.length;i++){
                if(npc[i] != null){
                    npc[i].update();
                }
            }

        }
        if(gameState == pauseState){
            //nothing
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g); //พื้นหลังของ JPanel ถูกเคลียรก่อนการวาดใหม่
        Graphics2D g2 = (Graphics2D)g; //เพื่อเรีนกใช้ฟังก์ชั่นที่เยอะกว่า

        //DEBUG
        long drawStart = 0;
        if(keyH.checkDrawTime == true){
            drawStart = System.nanoTime();
        }



        //TILE
        tileManager.draw(g2);
        //OBJECT
        for(int i = 0; i< obj.length; i++){
            if(obj[i] != null){
                obj[i].draw(g2,this);
            }
        }
        //NPC
        for(int i=0; i<npc.length;i++){
            if(npc[i] != null){
                npc[i].draw(g2);
            }
        }

        //PLAYER
        player.draw(g2);
        // UI
        ui.draw(g2);

        if(keyH.checkDrawTime == true){
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Drawtime : "+ passed,10,400);
            System.out.println("Draw Time : " + passed);
        }

        g2.dispose(); //คืนทรัพยากร
    }

    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic(){
        music.stop();
    }

    public void playMusicOneTime(int i){
        oneTimeSound.setFile(i);
        oneTimeSound.play();
    }
}