package tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[50];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];


        getTileImage();
        loadMap("/maps/worldv2.txt");
    }

    public void getTileImage() {
        //Placeholder
        setUp(0,"grass00",false);
        setUp(1,"wall",true);
        setUp(2,"water00",true);
        setUp(3,"earth",false);
        setUp(4,"tree",true);
        setUp(5,"road00",false);
        setUp(6,"grass01",false);
        setUp(7,"grass01",false);
        setUp(8,"grass01",false);
        setUp(9,"grass01",false);
        //Placeholder
        setUp(10,"grass00",false);
        setUp(11,"grass01",false);
        setUp(12,"water00",true);
        setUp(13,"water01",true);
        setUp(14,"water02",true);
        setUp(15,"water03",true);
        setUp(16,"water04",true);
        setUp(17,"water05",true);
        setUp(18,"water06",true);
        setUp(19,"water07",true);
        setUp(20,"water08",true);
        setUp(21,"water09",true);
        setUp(22,"water10",true);
        setUp(23,"water11",true);
        setUp(24,"water12",true);
        setUp(25,"water13",true);
        setUp(26,"road00",false);
        setUp(27,"road01",false);
        setUp(28,"road02",false);
        setUp(29,"road03",false);
        setUp(30,"road04",false);
        setUp(31,"road05",false);
        setUp(32,"road06",false);
        setUp(33,"road07",false);
        setUp(34,"road08",false);
        setUp(35,"road09",false);
        setUp(36,"road10",false);
        setUp(37,"road11",false);
        setUp(38,"road12",false);
        setUp(39,"earth",false);
        setUp(40,"wall",true);
        setUp(41,"tree",true);


    }

    public void setUp(int index,String imageName,boolean collision){
        UtilityTool utilityTool = new UtilityTool();
        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName+ ".png"));
            tile[index].image = utilityTool.scaleImage(tile[index].image, gp.getTileSize(),gp.getTileSize()); //ทำให้ภาพมีขนาดใหญ่ขึ้น
            tile[index].collision = collision;
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath){
        //อ่านไฟส์ text ไปเก็บใน array 2D
        //array 2D ถูกใช้ Store data เพื่อในมาเช็นในการวาดเเผนที่อีกที
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow){
                String line = br.readLine();
                while(col < gp.maxWorldCol){
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col ++;
                }
                if(col == gp.maxWorldCol){
                    col =0;
                    row++;
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        };
    }

    public void draw(Graphics2D g2) {
       int worldCol = 0;
       int worldRow = 0;
       while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){
           int tileNum = mapTileNum[worldCol][worldRow];

           int worldX = worldCol * gp.getTileSize();
           int worldY = worldRow * gp.getTileSize();
           int screenX = worldX - gp.player.worldX + gp.player.screenX;
           int screenY = worldY - gp.player.worldY + gp.player.screenY;

           if(worldX + gp.getTileSize() > gp.player.worldX - gp.player.screenX &&
                   worldX - gp.getTileSize() < gp.player.worldX + gp.player.screenX &&
                   worldY + gp.getTileSize() > gp.player.worldY - gp.player.screenY &&
                   worldY - gp.getTileSize() < gp.player.worldY + gp.player.screenY){
               g2.drawImage(tile[tileNum].image,screenX,screenY,null);
           }
           worldCol++;

           if(worldCol == gp.maxWorldCol){
               worldCol = 0;
               worldRow ++;
           }
       }

    }
}