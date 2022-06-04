package adventureisland;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Hada extends BonusHabilidad{
    private static final String HADA[] = {"recursos\\HadaDerecha.GIF", "recursos\\HadaIzquierda.GIF"};
    private int tiempoBonus = 2000;
    private Heroe heroe;
    private boolean pausa = false;
    
    public Hada() {
        super(HADA, 2, 0);
        this.visible = false;
    }
    
    @Override
    public void display(Graphics2D g){
        AffineTransform transform = new AffineTransform();
        AffineTransform old = g.getTransform();
        if(!(fueChocado)){
            g.drawImage(imagen,(int) this.posX,(int) this.posY, this.width, this.height, null);
        } else if(fueChocado && tiempoBonus > 0) {
            posX = (heroe.getDireccionActual() == Heroe.DIRECCION_DERECHA) ? heroe.getPosX()-sizeImg[0][0] - 4 : 
                                                                            heroe.getPosX()+heroe.getWidth() + 4;
            posY = heroe.getPosY() - sizeImg[0][1]/2;
            g.drawImage((heroe.getDireccionActual() == Heroe.DIRECCION_DERECHA) ? imagenes[0] : imagenes[1],(int) this.posX,(int) this.posY, this.width, this.height, null);
            if(!pausa){tiempoBonus -= 5;}
        }    
        g.setTransform(old);
    }
    
    public boolean isVisible(){
        return visible;
    }
    
    public void setHeroe(Heroe heroe){
        this.heroe = heroe;
    }
    
    public int getTiempoBonus(){
        return tiempoBonus;
    }
    
    public void setPausa(boolean b){
        pausa = b;
    }
}