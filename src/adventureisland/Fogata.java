package adventureisland;

import java.awt.Graphics2D;


public class Fogata extends Obstaculo {
    private static final String FOGATA = "recursos\\Obstaculos\\Fogata.GIF";

    public Fogata(){
        super(FOGATA);
        this.visible = true;
    }
    
    @Override
    public void display(Graphics2D g){
        g.drawImage(imagen, (int) posX, (int) posY, this.width, this.height, null);
    }
}
