package adventureisland;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 *
 * @author Ferreyra
 */
public class Huevo extends Obstaculo{
    private static final String HUEVO[] = {"recursos\\Huevo.png", "recursos\\HuevoRoto.png"};
    private boolean esta_roto = false;
    private int alturaVuelo=0;
    private Hacha hacha = null;
    private Carta carta = null;
    private Hada hada = null;
    private Patineta patineta = null;
    private boolean pausa=false;
    
    public Huevo() {
        super(HUEVO, 2, 0);
        this.visible = true;
    }
    
    public Huevo(Hacha hacha) {
        super(HUEVO, 2, 0);
        this.visible = true;
        this.hacha = hacha;
        this.desaparicion = 50;
    }
    
    public Huevo(Hada hada){
        super(HUEVO, 2, 0);
        this.visible = true;
        this.hada = hada;
        this.desaparicion = 50;
    }
    
    public Huevo(Carta carta){
        super(HUEVO, 2, 0);
        this.visible = true;
        this.carta = carta;
        this.desaparicion = 50;
    }
    
    public Huevo(Patineta patineta){
        super(HUEVO, 2, 0);
        this.visible = true;
        this.patineta = patineta;
        this.desaparicion = 50;
    }
    
    
    @Override
    public void display(Graphics2D g2){
        //tope 10
        if(this.fueChocado){
            if(this.desaparicion != 0){
                if(!pausa){this.desaparicion--;}
            } else {
                this.setVisible(false);
            }
            if(this.alturaVuelo < 10){
                this.alturaVuelo++;
            }
            if(this.alturaVuelo < 5){
               this.posX+=12;
               this.posY-=6;
            } else if (alturaVuelo < 9) {
                this.posX+=12;
                this.posY+=6;
            } else {
                if(!(esta_roto)){
                    this.cambiarImagen(1);
                    this.esta_roto = true;
                }
            }
        }
        if(this.visible){
            if((this.desaparicion < 15 && this.desaparicion%5 != 0) || (this.desaparicion > 15)){
                AffineTransform old = g2.getTransform();
                g2.drawImage(this.imagen,(int) this.posX,(int) this.posY, this.width, this.height, null);
                g2.setTransform(old);
            }
        } else {
            if(this.desaparicion == 0){
                if(hacha != null){
                    hacha.setPosition(posX,posY);
                    hacha.setVisible(true);
                    hacha = null;
                } else if(carta != null){
                    carta.setPosition(posX,posY);
                    carta.setVisible(true);
                    carta = null;
                } else if(hada != null){
                    hada.setPosition(posX,posY);
                    hada.setVisible(true);
                    hada = null;
                } else if(patineta != null){
                    patineta.setPosition(posX,Escenario.POSICION_Y_PISO-patineta.getHeight());
                    patineta.setVisible(true);
                    patineta = null;
                }
            }
        }
    }
    
    public Carta getCarta(){
        return this.carta;
    }

    public Hacha getHacha(){
        return this.hacha;
    }
    
    public void setPausa(boolean b){
        this.pausa = b;
    }
}

