package adventureisland;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Carta extends ObjetoGrafico{
    public static final int CORAZON = 0;
    public static final int DIAMANTE = 1;
    public static final int PICA = 2;
    public static final int TREBOL = 3;
    
    private static final String CARTAS[] = {"recursos\\Corazon.png","recursos\\Diamante.png",
                                            "recursos\\Pica.png","recursos\\Trebol.png"};
    private CamptosaurioAzul dinoAzul = null;
    private PteranodonteVioleta dinoVioleta = null;

    public Carta(int tipo_carta) {
        super(CARTAS[tipo_carta]);
        this.tipo = tipo_carta;
        this.desaparicion = 100;
        this.visible = false;
        switch(tipo_carta){
            case CORAZON:
                dinoAzul = new CamptosaurioAzul();
                break;
            case PICA:
                dinoVioleta = new PteranodonteVioleta();
                break;
        }
    }
    
    @Override
    public void display(Graphics2D g2) {
        if(this.visible){
            AffineTransform old = g2.getTransform();
            g2.drawImage(this.imagen,(int) this.posX,(int) this.posY, this.width, this.height, null);
            g2.setTransform(old);
        }
    }    

    public CamptosaurioAzul getDinoAzul() {
        return dinoAzul;
    }

    public PteranodonteVioleta getDinoVioleta() {
        return dinoVioleta;
    }

    public int getTipo(){
        return this.tipo;
    }
    
    @Override
    public void setFueChocado(boolean fueChocado){
        this.fueChocado = fueChocado;
        this.visible = false;
    }
}