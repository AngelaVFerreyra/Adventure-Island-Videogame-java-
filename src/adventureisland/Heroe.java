package adventureisland;

import java.awt.*;
import java.awt.geom.*;

public class Heroe extends Personaje{
    private boolean enPiso = false;
    
    public static final double DEFAULT_VELOCITY = 4;

    public static final int DIRECCION_DERECHA = 0;
    public static final int DIRECCION_IZQUIERDA = 1;

    public static final int ESTADO_QUIETO = -1;
    public static final int ESTADO_CAMINANDO = 0;
    public static final int ESTADO_TROPEZANDO = 1;
    public static final int ESTADO_ATACANDO = 2;
    public static final int ESTADO_CAYENDO = 4;
    public static final int ESTADO_MURIENDO = 5;
    public static final int ESTADO_MUERTO = 6;
    public static final int ESTADO_QUEMADO = 7;
    
    private int direccionActual = DIRECCION_DERECHA;
    private int estadoActual;
    private int estadoAnterior;
    private int direccionPrevia = DIRECCION_DERECHA;
    private int contadorCambioImagen = 20;

    private double velocityX = 0.0;//4.0;
    private double velocityY = 0.0;
    private double gravity = 1;
    private int contadorMultiple;
    private static final int DEFAULT_CONTADOR_MUERTE = 100;
    private static final int DEFAULT_CONTADOR_TROPIEZO = 20;
    private static final int DEFAULT_CONTADOR_DISPARO = 5;
    
    private int energia;
    private int direccionTropiezo = DIRECCION_DERECHA;
    private Hacha hacha = null;
    private CamptosaurioAzul dinoAzul = null;
    private PteranodonteVioleta dinoVioleta = null;
    private Hada hada = null;
    private int posXMod;
    private int posYMod;
    private int widthMod;
    private int heightMod; 
    
    private boolean invulnerable = false;

    private static String IMAGENES[] = {"recursos\\Heroe\\HeroeQuietoDerecha.GIF", //0
                                        "recursos\\Heroe\\HeroeQuietoIzquierda.GIF", //1
                                        "recursos\\Heroe\\HeroeCaminandoDerecha.GIF", //2
                                        "recursos\\Heroe\\HeroeCaminandoIzquierda.GIF",  //3
                                        "recursos\\Heroe\\HeroeCambioDireccionDerecha.png", //4
                                        "recursos\\Heroe\\HeroeCambioDireccionIzquierda.png", //5
                                        "recursos\\Heroe\\HeroeSaltoDerecha.png", //6
                                        "recursos\\Heroe\\HeroeSaltoIzquierda.png", //7
                                        "recursos\\Heroe\\HeroeTropezandoDerecha.png", //8
                                        "recursos\\Heroe\\HeroeTropezandoIzquierda.png", //9
                                        "recursos\\Heroe\\HeroeMuriendo.GIF", //10
                                        "recursos\\Heroe\\HeroeArrojandoDerecha.png", //11
                                        "recursos\\Heroe\\HeroeArrojandoIzquierda.png", //12
                                        "recursos\\Heroe\\HeroeQuemado.png"}; //13
    
    
    
    private Escenario esc;
    private AdventureIsland ai;
    
    private boolean sobreDinosaurio = false;

    public Heroe(Escenario esc, int energia){
        super(IMAGENES, 14, 0);
        this.esc = esc;
        this.contadorMultiple = DEFAULT_CONTADOR_TROPIEZO;
        this.estadoActual = ESTADO_QUIETO;
        this.energia = energia;
        this.visible = true;
    }

    public void jump(){
        if(!(movimientoBloqueado())){
            if(this.enPiso){
                esc.getAi().getAdmin_sound().playSonido("Saltar", esc.getAi().getAdmin_sound().getact_sonido());
                this.velocityY = -18.5;
                this.enPiso = false;
            }
        }
    }

    public void jumpEnd(){
        if(!(movimientoBloqueado())){
            this.velocityY = -14;
        }
    }
    
    private boolean movimientoBloqueado(){
        return ((this.estadoActual == ESTADO_TROPEZANDO) || 
                (this.estadoActual == ESTADO_MURIENDO) || 
                (this.estadoActual == ESTADO_QUEMADO) ||
                (this.estadoActual == ESTADO_MUERTO) || 
                (this.estadoActual == ESTADO_CAYENDO) ||
                (posX >= Escenario.ANCHO_MUNDO-150));
    }
    
    public void quieto() {
        if(!(movimientoBloqueado())){
            if(this.estadoActual != ESTADO_ATACANDO){
                this.estadoActual = ESTADO_QUIETO;
            }
        }        
    }
    
    public void left() {
        if(!(movimientoBloqueado())){
            this.velocityX = (hada == null) ? -DEFAULT_VELOCITY: -DEFAULT_VELOCITY-4; //-4.0;
            if((this.estadoActual != ESTADO_CAMINANDO) || (this.direccionActual != DIRECCION_IZQUIERDA)){
                cambiarImagen(2);
            }
            this.direccionActual = DIRECCION_IZQUIERDA;
            if(this.estadoActual != ESTADO_ATACANDO){
                this.estadoActual = ESTADO_CAMINANDO;
            }
        }
    }
    
    public void right(){
        if(!(movimientoBloqueado())){
            this.velocityX = (hada == null) ? DEFAULT_VELOCITY: DEFAULT_VELOCITY+4;
            if((this.estadoActual != ESTADO_CAMINANDO) || (this.direccionActual != DIRECCION_DERECHA)){
                cambiarImagen(1);
            }
            this.direccionActual = DIRECCION_DERECHA;
            if(this.estadoActual != ESTADO_ATACANDO){
                this.estadoActual = ESTADO_CAMINANDO;
            }
        }
    }
    
    @Override
    public void update(double delta) {
        if(hada != null && hada.getTiempoBonus() > 0){
            invulnerable = true;
        } else {
            invulnerable = false;
            hada = null;
        }
        switch(estadoActual){
            case ESTADO_CAYENDO:
            case ESTADO_QUEMADO:
            case ESTADO_MURIENDO:
                if(!sobreDinosaurio){
                    hacha = null;
                    if(this.estadoAnterior != this.estadoActual){
                        contadorMultiple = DEFAULT_CONTADOR_MUERTE;
                    }
                    posY += (this.contadorMultiple < DEFAULT_CONTADOR_MUERTE/2) ? 1 : -1;
                    this.velocityY = 0;
                    if(this.contadorMultiple == 0){
                        this.estadoActual = ESTADO_MUERTO;
                        esc.setEnergiaHeroe(esc.getEnergiaHeroe()-1);
                    }
                } else {
                    this.estadoActual = ESTADO_TROPEZANDO;
                    sobreDinosaurio = false;
                    dinoVioleta = null;
                    dinoAzul = null;
                }
                break;
            case ESTADO_TROPEZANDO:
                this.direccionActual = this.direccionTropiezo;
                this.velocityX = (direccionTropiezo == DIRECCION_DERECHA) ? 5.0 : -5.0;
                if(contadorMultiple > 10){
                    this.velocityY = -3;
                } else {
                    if(!enPiso){
                        this.velocityY = 3;
                    } else {
                        this.estadoActual = ESTADO_QUIETO;
                    } 
                }
                if(sobreDinosaurio){
                    sobreDinosaurio = false;
                    dinoAzul = null;
                    dinoVioleta = null;
                    posY = posYMod;
                }
            case ESTADO_ATACANDO:
            case ESTADO_CAMINANDO:
            case ESTADO_QUIETO:
                this.velocityY += this.gravity;
                this.posY += this.velocityY;
                this.posX += this.velocityX;
                /* Rebota contra los margenes X del mundo */
                if((this.posX+ (this.getWidth())) > Escenario.ANCHO_MUNDO){
                    this.posX = (int) Escenario.ANCHO_MUNDO - (this.getWidth());
                    this.velocityX *= -1;
                }
                /* Rebota contra la X=0 del mundo */
                if((this.posX) < esc.getLimiteIzqMundo()){
                    this.velocityX *= -1  ;
                    this.posX = esc.getLimiteIzqMundo();
                }
                if(this.posY > Escenario.POSICION_Y_PISO-this.getHeight()){
                    this.posY = Escenario.POSICION_Y_PISO-this.getHeight();
                    this.velocityY = 0.0;
                    this.enPiso = true;
                    /*ya toco el piso*/
                } 
                break;
        }
        this.velocityX=0;
        if(this.estadoActual != ESTADO_ATACANDO){
            this.estadoAnterior = this.estadoActual;
        }
    }
    
    @Override
    public void display(Graphics2D g2){
        AffineTransform transform = new AffineTransform();
        AffineTransform old = g2.getTransform();
        int numImg = -1;
        switch(estadoActual){
            case ESTADO_QUIETO:
            case ESTADO_CAMINANDO:
                if(this.direccionPrevia != this.direccionActual && enPiso){
                    numImg = 4;
                } else {
                    this.contadorCambioImagen = 5;
                }
                if(numImg == -1){
                    numImg = (!(enPiso)) ? 6: (estadoActual == ESTADO_QUIETO) ? 0 : 2;
                }
                numImg += direccionActual;
                if(this.contadorCambioImagen == 0 || !(enPiso)){
                    this.direccionPrevia = this.direccionActual;
                } else if(this.contadorCambioImagen != 0) {
                    this.contadorCambioImagen--;
                }
                break;
            case ESTADO_TROPEZANDO:
                numImg = (numImg == -1) ? 8 + direccionActual: numImg; 
            case ESTADO_MURIENDO:
                numImg = (numImg == -1) ? 10 : numImg; 
            case ESTADO_QUEMADO:
                numImg = (numImg == -1) ? 13 : numImg; 
                contadorMultiple-=1;
                break;
            case ESTADO_CAYENDO:
                numImg = (direccionActual == DIRECCION_DERECHA) ? 0 : 1; 
                contadorMultiple-=1; 
                this.posY += 5;
                break;
            case ESTADO_ATACANDO:
                numImg = 11+direccionActual;
                if(contadorMultiple != 0){
                    contadorMultiple-=1;
                } else {
                    this.estadoActual = this.estadoAnterior;
                }
                break;
        }
        if(sobreDinosaurio){
            Image aDibujar = null;
            if(dinoAzul != null && dinoVioleta == null){
                aDibujar = dinoAzul.getImage(numImg);
                dinoAzul.display(g2);
                this.posYMod = ((int) this.posY) - (dinoAzul.getHeight() - this.height);
                this.widthMod = dinoAzul.getWidth();
                this.heightMod = dinoAzul.getHeight();
            } else if(dinoVioleta != null){
                aDibujar = dinoVioleta.getImage(numImg);
                dinoVioleta.display(g2);
                this.posYMod = ((int) this.posY) - (dinoVioleta.getHeight() - this.height);
                this.widthMod = dinoVioleta.getWidth();
                this.heightMod = dinoVioleta.getHeight();
            }
            this.posXMod = (int) this.posX;
            g2.drawImage(aDibujar, posXMod, posYMod, widthMod, heightMod, null);
        }
        else {
            this.width = sizeImg[numImg][0];
            this.height = sizeImg[numImg][1];
            g2.drawImage(imagenes[numImg],(int) this.posX,(int) this.posY, this.width, this.height, null);
        }
        g2.setTransform(old);
    }
    
    public void atacar(){
        if(!(movimientoBloqueado())){
            if(hacha != null && !(sobreDinosaurio)){
                boolean check = (this.direccionActual == DIRECCION_DERECHA) ? 
                                hacha.disparar((int) posX+width/2, (int) posY, 1) : 
                                    hacha.disparar((int) posX+width/2, (int) posY, -1);
                if(check){
                    contadorMultiple = DEFAULT_CONTADOR_DISPARO;
                    this.estadoActual = ESTADO_ATACANDO;
                }
            } else if(sobreDinosaurio){
                boolean check = false;
                int posXDisparo = (direccionActual == DIRECCION_DERECHA) ? posXMod + widthMod : posXMod;
                int posYDisparo = posYMod + heightMod/2;
                if(dinoAzul != null && dinoVioleta == null){
                    check = dinoAzul.disparar(posXDisparo, posYDisparo, (direccionActual == DIRECCION_DERECHA) ? 1 : -1);
                } else if (dinoVioleta != null){
                    check = dinoVioleta.disparar(posXDisparo, posYDisparo, (direccionActual == DIRECCION_DERECHA) ? 1 : -1);
                }
                if(check){
                    contadorMultiple = DEFAULT_CONTADOR_DISPARO;
                    this.estadoActual = ESTADO_ATACANDO;
                }
            }
    }
    }
 
    public Rectangle[] getProyectiles(){
        Rectangle rProyectiles[] = new Rectangle[3];
        Rectangle hachas[] = (hacha != null) ? hacha.getHachas() : null;
        rProyectiles[0] = (hacha != null) ? hachas[0] : null;
        rProyectiles[1] = (hacha != null) ? hachas[1] : null;
        rProyectiles[2] = (dinoAzul != null && dinoVioleta == null) ? dinoAzul.getProyectil() : (dinoVioleta != null) ? dinoVioleta.getProyectil() : null;
        return rProyectiles;
    }
    
    @Override
    public Rectangle getBordes(){
        return (!(sobreDinosaurio)) ? new Rectangle((int) posX, (int) posY, width, height) : new Rectangle((int) posXMod, (int) posYMod, widthMod, heightMod);
    }
    
    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public int getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(int estadoActual) {
        this.estadoActual = estadoActual;
        switch(estadoActual){
            case ESTADO_TROPEZANDO:
                contadorMultiple = DEFAULT_CONTADOR_TROPIEZO;
                break;
        }
    }

    public boolean isEnPiso() {
        return enPiso;
    }

    public void setEnPiso(boolean enPiso) {
        this.enPiso = enPiso;
    }

    public int getDireccionActual() {
        return direccionActual;
    }

    public void setDireccionActual(int direccionActual) {
        this.direccionActual = direccionActual;
    }

    public int getDireccionTropiezo() {
        return direccionTropiezo;
    }

    public void setDireccionTropiezo(int direccionTropiezo) {
        this.direccionTropiezo = direccionTropiezo;
    }
    
    public void setHachas(Hacha hacha){
        this.hacha = hacha;
    }

    public void setDinoAzul(CamptosaurioAzul dinoAzul) {
        if(dinoAzul != null){
            this.dinoAzul = dinoAzul;
            this.sobreDinosaurio = true;
            this.dinoVioleta = null;
        }
    }

    public void setDinoVioleta(PteranodonteVioleta dinoVioleta) {
        if(dinoVioleta != null){
            this.dinoVioleta = dinoVioleta;
            this.sobreDinosaurio = true;
            this.dinoAzul = null;
        }
    }
    
    public boolean getSobreDinosaurio(){
        return sobreDinosaurio;
    }
    
    public Hacha getHacha(){
        return hacha;
    }
    
    public void setHacha(Hacha hacha){
        this.hacha = hacha;
    }
    
    public CamptosaurioAzul getDinoAzul(){
        return dinoAzul;
    }
    
    public PteranodonteVioleta getDinoVioleta(){
        return dinoVioleta;
    }

    public boolean isInvulnerable() {
        return invulnerable;
    }

    public void setInvulnerable(boolean invulnerable) {
        this.invulnerable = invulnerable;
    }
    
    public Hada getHada(){
        if(hada!=null){return hada;} else{ return null;}
    }
    
    public void setHada(Hada hada){
        this.hada = hada;
    }

    public int getEnergia() {
        return energia;
    }

    public void setEnergia(int energia) {
        this.energia = energia;
    }
    
    public boolean act_Hachas(){
        return this.hacha != null;
    }
    
    public boolean act_Hada(){
        if (this.hada != null){
            return hada.isVisible();
        }else
            return false;
    }
    
    public boolean act_DinoVioleta(){
        return this.dinoVioleta != null;
    }
    
    public boolean act_DinoAzul(){
        return this.dinoAzul != null;
    }
}
