package entity;

import main.GamePanel;

import java.util.Random;

public class NPC_OldMan extends Entity {

    public  NPC_OldMan(GamePanel gp){
        super(gp);

        direction = "down";
        speed = 1;
        getImage();
        setDialogue();
    }

    public void getImage() {
        down1 = setup("/npc/oldman_down_1");
        down2 = setup("/npc/oldman_down_2");
        right1 = setup("/npc/oldman_right_1");
        right2 = setup("/npc/oldman_right_2");
        left1 = setup("/npc/oldman_left_1");
        left2 = setup("/npc/oldman_left_2");
        up1 = setup("/npc/oldman_up_1");
        up2 = setup("/npc/oldman_up_2");
    }

    public void setDialogue(){
        dialogues[0] = "Hello, lad.";
        dialogues[1] = "So ypu 've come to this island to find the treasure?";
        dialogues[2] = "I used to be a great wizard but niw... I'm a bit too old for taking an adventure.";
        dialogues[3] = "Well, good luck on you.";
    }

    public void setAction(){
        //set character behavior
        actionLockCounter ++;
        if(actionLockCounter == 120){
            Random random = new Random();
            int i = random.nextInt(100)+1; //สุ่มตัวเลขจาก 1-100;
            if(i<25){
                direction = "up";
            }
            if(i>25 && i<50){
                direction = "down";
            }
            if(i>50&&i<75){
                direction = "left";
            }
            if(i>75&&i<100){
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }

    public void speak(){
        if(dialogues[dialogueIndex] == null){
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;
    }
}
