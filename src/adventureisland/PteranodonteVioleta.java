package adventureisland;

import java.awt.Graphics2D;

public class PteranodonteVioleta extends Dinosaurio{
    
    private static String IMAGENES[] = {"recursos\\Dinosaurio\\PteranodonteDerecha.gif", //0
                                        "recursos\\Dinosaurio\\PteranodonteIzquierda.gif", //
                                        "recursos\\Dinosaurio\\PteranodonteDerecha.gif", //1
                                        "recursos\\Dinosaurio\\PteranodonteIzquierda.gif", //5
                                        "recursos\\Dinosaurio\\PteranodonteDerecha.gif", //1
                                        "recursos\\Dinosaurio\\PteranodonteIzquierda.gif", //5
                                        "recursos\\Dinosaurio\\PteranodonteSaltoDerecha.png", //6
                                        "recursos\\Dinosaurio\\PteranodonteSaltoIzquierda.png", //7
                                        "", //8
                                        "", //9
                                        "", //10
                                        "recursos\\Dinosaurio\\PteranodonteAtacandoDerecha.png", //11
                                        "recursos\\Dinosaurio\\PteranodonteAtacandoIzquierda.png", //12
                                        "", // 13
                                        "recursos\\Dinosaurio\\PteranodonteProyectilDerecha.gif", // 14
                                        "recursos\\Dinosaurio\\PteranodonteProyectilIzquierda.gif"}; // 15
    
    public PteranodonteVioleta() {
        super(IMAGENES, 16, 0);
    }
    
    @Override
    public void display(Graphics2D g){
        if(delayDisparo != 0 && proyectil){
            if(heroe.getDireccionActual() == Heroe.DIRECCION_DERECHA){
                g.drawImage(imagenes[14], (int) posXDisparo, (int) posYDisparo, sizeImg[14][0], sizeImg[14][1], null);
            } else {
                g.drawImage(imagenes[15], (int) posXDisparo, (int) posYDisparo, sizeImg[15][0], sizeImg[15][1], null);
            }
            posXDisparo += direccionDisparo*10;
            delayDisparo -= 1;
        } else {
            proyectil = false;
            delayDisparo = DEFAULT_DELAY_DISPARO;
        }
    }
    
}
