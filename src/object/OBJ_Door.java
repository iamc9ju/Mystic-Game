package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Door extends  SuperObject{

    GamePanel gp;
    public OBJ_Door(GamePanel gp){
        this.gp = gp;
        name = "Door";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
            utilityTool.scaleImage(image,gp.getTileSize(),gp.getTileSize()); //ภาพ , กว้าง , สูง

        }catch (IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}
