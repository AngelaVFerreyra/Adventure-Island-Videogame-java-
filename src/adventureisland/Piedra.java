package adventureisland;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;


public class Piedra extends Obstaculo {
    private static final String PIEDRA[] = {"recursos\\Obstaculos\\Piedra.png", 
                                            "recursos\\Obstaculos\\PiedraRompiendose1.png", 
                                            "recursos\\Obstaculos\\PiedraRompiendose2.png", 
                                            "recursos\\Obstaculos\\PiedraRompiendose3.png"};
    private int numeroColisiones;
    private int contadorMultiple = 0;
    private int puntos;

    public Piedra() {
        super(PIEDRA, 4, 0);
        this.visible = true;
        this.numeroColisiones = 5;
        this.puntos = 200;
    }

    public int getNumeroColisiones() {
        return numeroColisiones;
    }

    public void setNumeroColisiones(int numeroColisiones) {
        this.numeroColisiones = numeroColisiones;
    }
    
    @Override
    public void display(Graphics2D g){
        if(this.visible){
            AffineTransform transform = new AffineTransform();
            AffineTransform old = g.getTransform();
            if(!(fueChocado)){
                g.drawImage(imagen,(int) this.posX,(int) this.posY, this.width, this.height, null);
            } else {
                if(contadorMultiple < 25){
                    if(contadorMultiple < 5){
                        g.drawImage(imagen,(int) this.posX,(int) this.posY, this.width, this.height, null);
                    } else if(contadorMultiple < 10){
                        g.drawImage(imagenes[1],(int) this.posX,(int) this.posY, sizeImg[0][0], sizeImg[0][1], null);
                    } else if(contadorMultiple < 15){
                        g.drawImage(imagenes[2],(int) this.posX,(int) this.posY, sizeImg[0][0], sizeImg[0][1], null);
                    } else if(contadorMultiple < 20){
                        g.drawImage(imagenes[3],(int) this.posX,(int) this.posY, sizeImg[0][0], sizeImg[0][1], null);
                    } else if(contadorMultiple < 25){
                        g.drawImage(imagenes[1],(int) this.posX,(int) this.posY, sizeImg[0][0], sizeImg[0][1], null);
                    }
                    contadorMultiple+=1;
                } else {
                    this.visible = false; 
                }
            }
            g.setTransform(old);
        }
    }

    public int getPuntos() {
        return puntos;
    }
    
    
}

