package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Chest extends  SuperObject {

    GamePanel gp;
    public OBJ_Chest(GamePanel gp){
        this.gp = gp;
        name = "Chest";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
            utilityTool.scaleImage(image,gp.getTileSize(),gp.getTileSize()); //ภาพ , กว้าง , สูง

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
