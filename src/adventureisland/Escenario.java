package adventureisland;
import java.awt.*;

public class Escenario{
    public static final boolean TESTMODE = false;
    
    public static final int ANCHO_MUNDO=4100*2-20;
    private int limiteIzqMundo = 5;
    public static final int ALTO_MUNDO=480;
    
    public static final int POSICION_Y_PISO=420;
    
    private static final String AREAS[] = {"recursos\\Area1.png", "recursos\\Area2.png"};
    private static final String AREASTEST[] = {"recursos\\Area1conUbicacion.png", "recursos\\Area2conUbicacion.png"};
    private static final String PASO_INTERMEDIO = "recursos\\Etapas\\Paso_Intermedio.png";
    
    private Image imagen_fondo;
    
    private Heroe heroe = null;
    private Jugador jugador = null;
    
    private Fruta frutas[];
    private int cantidadFrutas = 0;
    
    private Huevo huevos[];
    private int cantidadHuevos = 0;
    
    private Piedra piedras[];
    private int cantidadPiedras = 0;
    
    private Hacha hachas[];
    private int cantidadHachas = 0;
    
    private Carta cartas[];
    private int cantidadCartas = 0;
    
    private Caracol caracoles[];
    private int cantidadCaracoles = 0;
    
    private Serpiente serpientes[];
    private int cantidadSerpientes = 0;
    
    private Pajaro pajaros[];
    private int cantidadPajaros = 0;
    
    private Fogata fogatas[];
    private int cantidadFogatas = 0;
    
    private Hada hadas[];
    private int cantidadHadas = 0;
    
    private Spider spiders[];
    private int cantidadSpiders = 0;
    
    private Patineta patinetas[];
    private int cantidadPatinetas = 0;
   
    private AdventureIsland ai;
    private Pozo pozo;
    private Hacha hachaHeroe = null;
    private CamptosaurioAzul dinoAzulHeroe = null;
    private PteranodonteVioleta dinoVioletaHeroe = null;
    
    private int numeroMapa;
    private int energiaHeroe;
    
    public Escenario(AdventureIsland ai, Jugador jugador){
        numeroMapa = 0;
        this.jugador = jugador;
        this.energiaHeroe = 3;
        this.ai = ai;
        generarAreas();
    }
    
    private void estaMuerto(){
        if(this.energiaHeroe >= 0){
            if(this.heroe.getEstadoActual() == Heroe.ESTADO_MUERTO || heroe.getPosX() >= Escenario.ANCHO_MUNDO-150){
                // reiniciar personaje
                if(heroe.getPosX() >= Escenario.ANCHO_MUNDO-150){
                    numeroMapa += 1;
                    this.jugador.setPuntajeJugador(this.jugador.getPuntajeJugador() + ai.getTiempo()/10);
                    if(numeroMapa>1){
                        this.energiaHeroe = -1;
                        numeroMapa = 0;
                    } else {
                        ai.setEtapaJuego(AdventureIsland.ETAPA_INTERMEDIO);
                    }
                }
                this.limiteIzqMundo = 5;
                ai.getAdmin_sound().playSonido("Area1", false);
                ai.getAdmin_sound().playSonido("Area2", false);
                
                generarAreas();
                
                if(numeroMapa == 0){
                    ai.getAdmin_sound().playSonido("Area1", ai.getAdmin_sound().getact_musica());
                }else if(numeroMapa == 1){
                    ai.getAdmin_sound().playSonido("Area2", ai.getAdmin_sound().getact_musica());
                }
            }
        } 
    }
    
     public void display(Graphics2D g2) {
        g2.drawImage(imagen_fondo, -1, -460, imagen_fondo.getWidth(null)*2, imagen_fondo.getHeight(null)*2, null);
        estaMuerto();
        colisiones();
        for(int i = 0; i < cantidadFrutas; i++){
            if(!(frutas[i].getFueChocado())){
                if(en_Rango((int)heroe.getPosX(), heroe.getWidth(),(int) frutas[i].getPosX(), 200)){
                    frutas[i].setVisible(true);
                }
            }
            frutas[i].display(g2);
        }
        for(int i = 0; i < cantidadHuevos; i++){
            huevos[i].display(g2);
        }
        for(int i = 0; i < cantidadPiedras; i++){
            piedras[i].display(g2);
        }
        for(int i = 0; i < cantidadHachas; i++){
            hachas[i].display(g2);
        }
        for(int i = 0; i < cantidadCartas; i++){
            cartas[i].display(g2);
        }
        for(int i = 0; i < cantidadPatinetas; i++){
            patinetas[i].display(g2);
        }
        for(int i = 0; i < cantidadCaracoles; i++){
            if(en_Rango((int)heroe.getPosX(), heroe.getWidth(),(int) caracoles[i].getPosX(), 400)){
                caracoles[i].setVisible(true);
                if(en_Rango((int)heroe.getPosX(), heroe.getWidth(),(int) caracoles[i].getPosX(), 300)){
                    caracoles[i].setEnMovimiento(!ai.getPausa());
                }
            }
            if(!(en_Rango((int)caracoles[i].getPosX(), caracoles[i].getWidth(),(int) heroe.getPosX(), 640))){
                caracoles[i].setVisible(false);
                caracoles[i].setEnMovimiento(false);
            }
            caracoles[i].display(g2);
        }
        for(int i = 0; i < cantidadSerpientes; i++){
            if(en_Rango((int)heroe.getPosX(), heroe.getWidth(),(int) serpientes[i].getPosX(), 400)){
                serpientes[i].setVisible(true);
                if(en_Rango((int)heroe.getPosX(), heroe.getWidth(),(int) serpientes[i].getPosX(), 300)){
                    serpientes[i].setEnMovimiento(!ai.getPausa());
                }
            }
            if(!(en_Rango((int)serpientes[i].getPosX(), serpientes[i].getWidth(),(int) heroe.getPosX(), 640))){
                serpientes[i].setVisible(false);
                serpientes[i].setEnMovimiento(false);
            }
            serpientes[i].display(g2);
        }
        for(int i = 0; i < cantidadPajaros; i++){
            if(en_Rango((int)heroe.getPosX(), heroe.getWidth(),(int) pajaros[i].getPosX(), 400)){
                pajaros[i].setVisible(true);
                if(en_Rango((int)heroe.getPosX(), heroe.getWidth(),(int) pajaros[i].getPosX(), 300)){
                    pajaros[i].setEnMovimiento(!ai.getPausa());
                }
            }
            if(!(en_Rango((int)pajaros[i].getPosX(), pajaros[i].getWidth(),(int) heroe.getPosX(), 640))){
                pajaros[i].setVisible(false);
                pajaros[i].setEnMovimiento(false);
            }
            pajaros[i].display(g2);
        }
        for(int i = 0; i < cantidadFogatas; i++){
            fogatas[i].display(g2);
        }
        for(int i = 0; i < cantidadHadas; i++){
            hadas[i].display(g2);
        }
        for(int i = 0; i < cantidadSpiders; i++){
            if(en_Rango((int)heroe.getPosX(), heroe.getWidth(),(int) spiders[i].getPosX(), 400)){
                spiders[i].setVisible(true);
                if(en_Rango((int)heroe.getPosX(), heroe.getWidth(),(int) spiders[i].getPosX(), 300)){
                    spiders[i].setEnMovimiento(!ai.getPausa());
                }
            }
            if(!(en_Rango((int)spiders[i].getPosX(), spiders[i].getWidth(),(int) heroe.getPosX(), 640))){
                spiders[i].setVisible(false);
                spiders[i].setEnMovimiento(false);
            }
            spiders[i].display(g2);
        }
        heroe.display(g2);
        
    }
    
    public boolean en_Rango(int Obj1_posX, int Obj1_ancho, int Obj2_posX, int rango){ //Cuando heroe se acerca a los objetos
        int aVerificar = (Obj2_posX - (Obj1_posX + Obj1_ancho));
        if(aVerificar < 0){
            aVerificar *= (-1);
        }
        return (aVerificar < rango);
    }
    
    public Heroe getHeroe(){
        return heroe;
    }
    
    public boolean colision(Rectangle r1, Rectangle r2){
        return r1.intersects(r2);
    }
    
    private void generarAreas(){
        heroe = (heroe == null) ? new Heroe(this,this.energiaHeroe) : heroe;
        heroe.setEnergia(energiaHeroe);
        heroe.setPosition(76*2,-this.heroe.getHeight() + 435);
        heroe.setEstadoActual(Heroe.ESTADO_QUIETO);
        switch(this.numeroMapa){
            case 0:
                generarArea1();
                break;
            case 1: 
//                AdministradorSonido.pararMusica("TodasIslas");
                generarArea2();
                break;
        }
        if(ai.getCam() != null){
            ai.getCam().reiniciar();
            ai.getCam().seguirPersonaje(heroe);
        }
    }
    
    private void generarArea1(){
        setFondo(0);
        this.numeroMapa = 0;
        
        this.cantidadFrutas = 12;
        frutas = new Fruta[this.cantidadFrutas];
        frutas[0] = new Fruta(8);
        frutas[0].setPosition(333*2, POSICION_Y_PISO-2*(435-397));
        frutas[1] = new Fruta(12);
        frutas[1].setPosition(397*2, POSICION_Y_PISO-2*(435-381));
        frutas[2] = new Fruta(3);
        frutas[2].setPosition(651*2, POSICION_Y_PISO-2*(435-381));
        frutas[3] = new Fruta(2);
        frutas[3].setPosition(844*2, POSICION_Y_PISO-2*(435-372));
        frutas[4] = new Fruta(8);
        frutas[4].setPosition(1100*2, POSICION_Y_PISO-2*(435-397));
        frutas[5] = new Fruta(12);
        frutas[5].setPosition(1356*2, POSICION_Y_PISO-2*(435-397));
        frutas[6] = new Fruta(3);
        frutas[6].setPosition(1388*2, POSICION_Y_PISO-2*(435-373));
        frutas[7] = new Fruta(2);
        frutas[7].setPosition(1804*2, POSICION_Y_PISO-2*(435-380));
        frutas[8] = new Fruta(12);
        frutas[8].setPosition(2189*2, POSICION_Y_PISO-2*(435-373));
        frutas[9] = new Fruta(8);
        frutas[9].setPosition(2637*2, POSICION_Y_PISO-2*(435-381));
        frutas[10] = new Fruta(2);
        frutas[10].setPosition(3245*2, POSICION_Y_PISO-2*(435-356));
        frutas[11] = new Fruta(3);
        frutas[11].setPosition(3595*2, POSICION_Y_PISO-2*(435-357));
        
        this.cantidadHachas = 1;
        hachas = new Hacha[this.cantidadHachas];
        hachas[0] = new Hacha();
        hachas[0].setPosition(489*2+80, POSICION_Y_PISO-2*(438-421));
        
        this.cantidadCartas = 1;
        cartas = new Carta[this.cantidadCartas];
        cartas[0] = new Carta(Carta.CORAZON);
        
        this.cantidadPatinetas = 1;
        patinetas = new Patineta[this.cantidadPatinetas];
        patinetas[0] = new Patineta();
        
        this.cantidadHuevos = 3;
        huevos = new Huevo[cantidadHuevos];
        huevos[0] = new Huevo(hachas[0]);
        huevos[0].setPosition(489*2, POSICION_Y_PISO-2*(438-421));
        huevos[1] = new Huevo(patinetas[0]);
        huevos[1].setPosition(1225*2, POSICION_Y_PISO-2*(438-421));
        huevos[2] = new Huevo(cartas[0]);
        huevos[2].setPosition(3017*2, POSICION_Y_PISO-2*(438-421));
        
        this.cantidadPiedras = 2;
        piedras = new Piedra[this.cantidadPiedras];
        piedras[0] = new Piedra();
        piedras[0].setPosition(1643*2, POSICION_Y_PISO-2*(435-420));
        piedras[1] = new Piedra();
        piedras[1].setPosition(3243*2, POSICION_Y_PISO-2*(435-420));
        
        this.cantidadCaracoles = 5;
        caracoles = new Caracol[cantidadCaracoles];
        caracoles[0] = new Caracol();
        caracoles[0].setPosition(700*2,POSICION_Y_PISO-2*(435-412));
        caracoles[1] = new Caracol();
        caracoles[1].setPosition(830*2,POSICION_Y_PISO-2*(435-412));
        caracoles[2] = new Caracol();
        caracoles[2].setPosition(2250*2,POSICION_Y_PISO-2*(435-412));
        caracoles[3] = new Caracol();
        caracoles[3].setPosition(2500*2,POSICION_Y_PISO-2*(435-412));
        caracoles[4] = new Caracol();
        caracoles[4].setPosition(2700*2,POSICION_Y_PISO-2*(435-412));
        
        this.cantidadSerpientes = 3;
        serpientes = new Serpiente[cantidadSerpientes];
        serpientes[0] = new Serpiente(this);
        serpientes[0].setPosition(1970*2,POSICION_Y_PISO-2*(435-410));
        serpientes[1] = new Serpiente(this);
        serpientes[1].setPosition(2100*2,POSICION_Y_PISO-2*(435-410));
        serpientes[2] = new Serpiente(this);
        serpientes[2].setPosition(2400*2,POSICION_Y_PISO-2*(435-410));
        
        this.cantidadPajaros = 2;
        pajaros = new Pajaro[cantidadPajaros];
        pajaros[0] = new Pajaro();
        pajaros[0].setPosition(3250*2,POSICION_Y_PISO-2*(110));
        pajaros[1] = new Pajaro();
        pajaros[1].setPosition(3650*2,POSICION_Y_PISO-2*(110));
        
        this.cantidadFogatas = 2;
        fogatas = new Fogata[2];
        fogatas[0] = new Fogata();
        fogatas[0].setPosition(1000*2,POSICION_Y_PISO-2*(435-410));
        fogatas[1] = new Fogata();
        fogatas[1].setPosition(3700*2,POSICION_Y_PISO-2*(435-410));
    }
    
    private void generarArea2(){
        setFondo(1);
        this.numeroMapa = 1;
        //heroe.setPosition(3430*2, POSICION_Y_PISO-2*(435-420));
        this.cantidadFrutas = 8;
        frutas = new Fruta[this.cantidadFrutas];
        frutas[0] = new Fruta(12);
        frutas[0].setPosition(268*2, POSICION_Y_PISO-2*(435-403));
        frutas[1] = new Fruta(2);
        frutas[1].setPosition(363*2, POSICION_Y_PISO-2*(435-379));
        frutas[2] = new Fruta(12);
        frutas[2].setPosition(781*2, POSICION_Y_PISO-2*(435-396));
        frutas[3] = new Fruta(8);
        frutas[3].setPosition(812*2, POSICION_Y_PISO-2*(435-372));
        frutas[4] = new Fruta(3);
        frutas[4].setPosition(940*2, POSICION_Y_PISO-2*(435-371));
        frutas[5] = new Fruta(3);
        frutas[5].setPosition(1259*2, POSICION_Y_PISO-2*(435-380));
        frutas[6] = new Fruta(12);
        frutas[6].setPosition(1804*2, POSICION_Y_PISO-2*(435-388));
        frutas[7] = new Fruta(3);
        frutas[7].setPosition(2155*2, POSICION_Y_PISO-2*(435-371));
        
        this.cantidadHachas = 1;
        hachas = new Hacha[this.cantidadHachas];
        hachas[0] = new Hacha();
        
        this.cantidadCartas = 1;
        cartas = new Carta[this.cantidadCartas];
        cartas[0] = new Carta(Carta.PICA);
        
        this.cantidadHadas = 1;
        hadas = new Hada[this.cantidadHadas];
        hadas[0] = new Hada();
        
        this.cantidadHuevos = 3;
        huevos = new Huevo[cantidadHuevos];
        huevos[0] = new Huevo(hachas[0]);
        huevos[0].setPosition(200*2, POSICION_Y_PISO-2*(438-421));
        huevos[1] = new Huevo(hadas[0]);
        huevos[1].setPosition(1416*2, POSICION_Y_PISO-2*(438-421));
        huevos[2] = new Huevo(cartas[0]);
        huevos[2].setPosition(3080*2, POSICION_Y_PISO-2*(438-421));
        
        this.cantidadPiedras = 3;
        piedras = new Piedra[this.cantidadPiedras];
        piedras[0] = new Piedra();
        piedras[0].setPosition(938*2, POSICION_Y_PISO-2*(435-420));
        piedras[1] = new Piedra();
        piedras[1].setPosition(1995*2, POSICION_Y_PISO-2*(435-420));
        piedras[2] = new Piedra();
        piedras[2].setPosition(2923*2, POSICION_Y_PISO-2*(435-420));
        
        this.cantidadSpiders = 5;
        spiders = new Spider[cantidadSpiders];
        spiders[0] = new Spider();
        spiders[0].setPosition(700*2,POSICION_Y_PISO-2*(110));
        spiders[1] = new Spider();
        spiders[1].setPosition(830*2,POSICION_Y_PISO-2*(110));
        spiders[2] = new Spider();
        spiders[2].setPosition(2250*2,POSICION_Y_PISO-2*(110));
        spiders[3] = new Spider();
        spiders[3].setPosition(2500*2,POSICION_Y_PISO-2*(110));
        spiders[4] = new Spider();
        spiders[4].setPosition(2700*2,POSICION_Y_PISO-2*(110));
        
        pozo = new Pozo();
        pozo.setPosition(3472*2, POSICION_Y_PISO-2);//3458*2, POSICION_Y_PISO-2*(435-420));
        pozo.width = 36*2;
        pozo.height = 10;
        
        this.cantidadFogatas = 2;
        fogatas = new Fogata[2];
        fogatas[0] = new Fogata();
        fogatas[0].setPosition(1700*2,POSICION_Y_PISO-2*(435-410));
        fogatas[1] = new Fogata();
        fogatas[1].setPosition(2450*2,POSICION_Y_PISO-2*(435-410));
        
        this.cantidadSerpientes = 2;
        serpientes = new Serpiente[cantidadSerpientes];
        serpientes[0] = new Serpiente(this);
        serpientes[0].setPosition(1259*2,POSICION_Y_PISO-2*(435-410));
        serpientes[1] = new Serpiente(this);
        serpientes[1].setPosition(2730*2,POSICION_Y_PISO-2*(435-410));
        
        this.cantidadPajaros = 2;
        pajaros = new Pajaro[cantidadPajaros];
        pajaros[0] = new Pajaro();
        pajaros[0].setPosition(3530*2,POSICION_Y_PISO-2*(110));
        pajaros[1] = new Pajaro();
        pajaros[1].setPosition(3700*2,POSICION_Y_PISO-2*(110));
    }
    
    public void colisiones(){
        if(this.heroe.getEstadoActual() != Heroe.ESTADO_MURIENDO && this.heroe.getEstadoActual() != Heroe.ESTADO_QUEMADO){
            for(int i = 0; i < this.cantidadFrutas; i++){
                if(frutas[i].isVisible()){
                    if(this.heroe.getBordes().intersects(frutas[i].getBordes())){
                        ai.incrementTime(frutas[i].getSegundosExtra());
                        frutas[i].setFueChocado(true);
                        jugador.sumarPuntajeJugador(frutas[i].getPuntos());
                        ai.getAdmin_sound().playSonido("Fruta_HuevoMartillo", ai.getAdmin_sound().getact_sonido());
                    }
                }
            } 
            for(int i = 0; i < this.cantidadPatinetas; i++){
                if(patinetas[i].isVisible()){
                    if(this.heroe.getBordes().intersects(patinetas[i].getBordes())){
                        patinetas[i].setFueChocado(true);
                        jugador.sumarPuntajeJugador(patinetas[i].getPuntos());
                        ai.getAdmin_sound().playSonido("Fruta_HuevoMartillo", ai.getAdmin_sound().getact_sonido());
                    }
                }
            } 
            for(int i = 0; i < this.cantidadPiedras; i++){
                if(piedras[i].isVisible() && (!(piedras[i].getFueChocado()))){
                    if(this.heroe.getBordes().intersects(piedras[i].getBordes()) && (!(heroe.isInvulnerable()))){
                        if(this.heroe.getEstadoActual() != Heroe.ESTADO_TROPEZANDO){
                            if(this.piedras[i].getNumeroColisiones() != 0){
                                this.heroe.setDireccionTropiezo(this.heroe.getDireccionActual());
                                this.piedras[i].setNumeroColisiones(this.piedras[i].getNumeroColisiones()-1);
                                this.heroe.setEstadoActual(Heroe.ESTADO_TROPEZANDO);
                                ai.getAdmin_sound().playSonido("Fruta_HuevoMartillo", ai.getAdmin_sound().getact_sonido());
                            } else {
                                this.heroe.setEstadoActual(Heroe.ESTADO_MURIENDO);
                                ai.getAdmin_sound().playSonido("Area1", false);
                                ai.getAdmin_sound().playSonido("Area2", false);
                                ai.getAdmin_sound().playSonido("HeroeMuere", ai.getAdmin_sound().getact_sonido());
                            }
                            if(heroe.getSobreDinosaurio()){
                                piedras[i].setFueChocado(true);
                                jugador.sumarPuntajeJugador(piedras[i].getPuntos());
                            }
                        }
                    } else if(this.heroe.getBordes().intersects(piedras[i].getBordes()) && (heroe.isInvulnerable())){
                        piedras[i].setFueChocado(true);
                    }
                    if(ataqueHeroe(piedras[i].getBordes()) && (heroe.getSobreDinosaurio())){
                        piedras[i].setFueChocado(true);
                        jugador.sumarPuntajeJugador(piedras[i].getPuntos());
                    }
                }
            }
            for(int i = 0; i < this.cantidadHuevos; i++){
                if(huevos[i].isVisible()){
                    if(this.heroe.getBordes().intersects(huevos[i].getBordes())){
                        huevos[i].setFueChocado(true);
                    }
                }
            }
            for(int i = 0; i < this.cantidadHachas; i++){
                if(hachas[i].isVisible()){
                    if(this.heroe.getBordes().intersects(hachas[i].getBordes())){
                        hachas[i].setFueChocado(true);
                        this.heroe.setHachas(hachas[i]);
                        ai.getAdmin_sound().playSonido("Fruta_HuevoMartillo", ai.getAdmin_sound().getact_sonido());
                    }
                }
            }
            for(int i = 0; i < this.cantidadCaracoles; i++){
                if(caracoles[i].isVisible() && (!(caracoles[i].getFueChocado()))){
                    if(this.heroe.getBordes().intersects(caracoles[i].getBordes()) && (!(heroe.isInvulnerable()))){
                        if(!this.heroe.getSobreDinosaurio()){
                            this.heroe.setEstadoActual(Heroe.ESTADO_MURIENDO);
                            ai.getAdmin_sound().playSonido("Area1", false);
                            ai.getAdmin_sound().playSonido("Area2", false);
                            ai.getAdmin_sound().playSonido("HeroeMuere", ai.getAdmin_sound().getact_sonido());
                        } else {
                            this.heroe.setEstadoActual(Heroe.ESTADO_TROPEZANDO);
                            this.heroe.setDinoAzul(null);
                            this.heroe.setDinoVioleta(null);
                            caracoles[i].setFueChocado(true);
                            jugador.sumarPuntajeJugador(caracoles[i].puntos);
                        }
                    } else if(this.heroe.getBordes().intersects(caracoles[i].getBordes()) && (heroe.isInvulnerable())){
                        caracoles[i].setFueChocado(true);
                        jugador.sumarPuntajeJugador(caracoles[i].puntos);
                        ai.getAdmin_sound().playSonido("MuerteEnemigo", ai.getAdmin_sound().getact_sonido());
                    }
                    if(ataqueHeroe(caracoles[i].getBordes())){
                        caracoles[i].setFueChocado(true);
                        jugador.sumarPuntajeJugador(caracoles[i].puntos);
                        ai.getAdmin_sound().playSonido("MuerteEnemigo", ai.getAdmin_sound().getact_sonido());
                    }
                }
            }
            for(int i = 0; i < this.cantidadSerpientes; i++){
                if(serpientes[i].isVisible() && (!(serpientes[i].getFueChocado()))){
                    if(this.heroe.getBordes().intersects(serpientes[i].getBordes()) && (!(heroe.isInvulnerable()))){
                        if(!this.heroe.getSobreDinosaurio()){
                            this.heroe.setEstadoActual(Heroe.ESTADO_MURIENDO);
                            ai.getAdmin_sound().playSonido("Area1", false);
                            ai.getAdmin_sound().playSonido("Area2", false);
                            ai.getAdmin_sound().playSonido("HeroeMuere", ai.getAdmin_sound().getact_sonido());
                        } else {
                            this.heroe.setEstadoActual(Heroe.ESTADO_TROPEZANDO);
                            this.heroe.setDinoAzul(null);
                            this.heroe.setDinoVioleta(null);
                            serpientes[i].setFueChocado(true);
                            jugador.sumarPuntajeJugador(serpientes[i].puntos);
                        }
                    } else if(this.heroe.getBordes().intersects(serpientes[i].getBordes()) && (heroe.isInvulnerable())){
                        serpientes[i].setFueChocado(true);
                        jugador.sumarPuntajeJugador(serpientes[i].puntos);
                        ai.getAdmin_sound().playSonido("MuerteEnemigo", ai.getAdmin_sound().getact_sonido());
                    }
                    if(ataqueHeroe(serpientes[i].getBordes())){
                        serpientes[i].setFueChocado(true);
                        jugador.sumarPuntajeJugador(serpientes[i].puntos);
                        ai.getAdmin_sound().playSonido("MuerteEnemigo", ai.getAdmin_sound().getact_sonido());
                    }
                }
            }
            for(int i = 0; i < this.cantidadPajaros; i++){
                if(pajaros[i].isVisible() && (!(pajaros[i].getFueChocado()))){
                    if(this.heroe.getBordes().intersects(pajaros[i].getBordes())  && (!(heroe.isInvulnerable()))){
                        if(!this.heroe.getSobreDinosaurio()){
                            this.heroe.setEstadoActual(Heroe.ESTADO_MURIENDO);
                            ai.getAdmin_sound().playSonido("Area1", false);
                            ai.getAdmin_sound().playSonido("Area2", false);
                            ai.getAdmin_sound().playSonido("HeroeMuere", ai.getAdmin_sound().getact_sonido());
                        } else {
                            this.heroe.setEstadoActual(Heroe.ESTADO_TROPEZANDO);
                            this.heroe.setDinoAzul(null);
                            this.heroe.setDinoVioleta(null);
                            pajaros[i].setFueChocado(true);
                            jugador.sumarPuntajeJugador(pajaros[i].puntos);
                        }
                    } else if(this.heroe.getBordes().intersects(pajaros[i].getBordes()) && (heroe.isInvulnerable())){
                        pajaros[i].setFueChocado(true);
                        jugador.sumarPuntajeJugador(pajaros[i].puntos);
                        ai.getAdmin_sound().playSonido("MuerteEnemigo", ai.getAdmin_sound().getact_sonido());
                    }
                    if(ataqueHeroe(pajaros[i].getBordes())){
                        pajaros[i].setFueChocado(true);
                        jugador.sumarPuntajeJugador(pajaros[i].puntos);
                        ai.getAdmin_sound().playSonido("MuerteEnemigo", ai.getAdmin_sound().getact_sonido());
                    }
                }
            }
            for(int i = 0; i < this.cantidadSpiders; i++){
                if(spiders[i].isVisible() && (!(spiders[i].getFueChocado()))){
                    if(this.heroe.getBordes().intersects(spiders[i].getBordes())  && (!(heroe.isInvulnerable()))){
                        if(!this.heroe.getSobreDinosaurio()){
                            this.heroe.setEstadoActual(Heroe.ESTADO_MURIENDO);
                            ai.getAdmin_sound().playSonido("Area1", false);
                            ai.getAdmin_sound().playSonido("Area2", false);
                            ai.getAdmin_sound().playSonido("HeroeMuere", ai.getAdmin_sound().getact_sonido());
                        } else {
                            this.heroe.setEstadoActual(Heroe.ESTADO_TROPEZANDO);
                            this.heroe.setDinoAzul(null);
                            this.heroe.setDinoVioleta(null);
                            spiders[i].setFueChocado(true);
                            jugador.sumarPuntajeJugador(spiders[i].puntos);
                        }
                    } else if(this.heroe.getBordes().intersects(spiders[i].getBordes()) && (heroe.isInvulnerable())){
                        spiders[i].setFueChocado(true);
                        jugador.sumarPuntajeJugador(spiders[i].puntos);
                        ai.getAdmin_sound().playSonido("MuerteEnemigo", ai.getAdmin_sound().getact_sonido());
                    }
                    if(ataqueHeroe(spiders[i].getBordes())){
                        spiders[i].setFueChocado(true);
                        jugador.sumarPuntajeJugador(spiders[i].puntos);
                        ai.getAdmin_sound().playSonido("MuerteEnemigo", ai.getAdmin_sound().getact_sonido());
                    }
                }
            }
            for(int i = 0; i < this.cantidadFogatas; i++){
                if(fogatas[i].isVisible()){
                    if(this.heroe.getBordes().intersects(fogatas[i].getBordes())){
                        if(this.heroe.getEstadoActual() != Heroe.ESTADO_QUEMADO && (!(heroe.isInvulnerable()))){
                            this.heroe.setEstadoActual(Heroe.ESTADO_QUEMADO);
                            ai.getAdmin_sound().playSonido("Area1", false);
                            ai.getAdmin_sound().playSonido("Area2", false);
                            ai.getAdmin_sound().playSonido("HeroeMuere", ai.getAdmin_sound().getact_sonido());
                        }
                    } 
                }
            }
            for(int i = 0; i < this.cantidadCartas; i++){
                if(cartas[i].isVisible()){
                    if(this.heroe.getBordes().intersects(cartas[i].getBordes())){
                        cartas[i].setFueChocado(true);
                        switch(cartas[i].getTipo()){
                            case Carta.CORAZON:
                                ai.getAdmin_sound().playSonido("ObtenerDinosaurioAzul",ai.getAdmin_sound().getact_sonido());
                                cartas[i].getDinoAzul().setHeroe(heroe);
                                heroe.setDinoAzul(cartas[i].getDinoAzul());
                                break;
                            case Carta.PICA:
                                ai.getAdmin_sound().playSonido("ObtenerDinosaurioVioleta",ai.getAdmin_sound().getact_sonido());
                                cartas[i].getDinoVioleta().setHeroe(heroe);
                                heroe.setDinoVioleta(cartas[i].getDinoVioleta());
                                break;
                        }
                    }
                }
            }
            if(pozo != null){
                if(this.heroe.getBordes().intersects(pozo.getBordes())){
                    if(this.heroe.getEstadoActual() != Heroe.ESTADO_CAYENDO){
                        this.heroe.setEstadoActual(Heroe.ESTADO_CAYENDO);
                        ai.getAdmin_sound().playSonido("Area1", false);
                        ai.getAdmin_sound().playSonido("Area2", false);
                        ai.getAdmin_sound().playSonido("HeroeMuere", ai.getAdmin_sound().getact_sonido());
                    }
                }
            }
            for(int i = 0; i < this.cantidadHadas; i++){
                if(hadas[i].isVisible()){
                    if(this.heroe.getBordes().intersects(hadas[i].getBordes())){
                        hadas[i].setFueChocado(true);
                        hadas[i].setHeroe(heroe);
                        this.heroe.setHada(hadas[i]);
                        ai.getAdmin_sound().playSonido("Fruta_HuevoMartillo", ai.getAdmin_sound().getact_sonido());
                    }
                }
            }
        }
    }
   
    
    private boolean ataqueHeroe(Rectangle rEntidad){
        Rectangle rProyectiles[] = heroe.getProyectiles();
        for(int k = 0; k < 3; k++){
            if(rProyectiles[k] != null){
                if(rProyectiles[k].intersects(rEntidad)){
                    return true;
                }
            } 
        }
        return false;
    }
    
    public int getLimiteIzqMundo() {
        return limiteIzqMundo;
    }

    public void setLimiteIzqMundo(int limiteIzqMundo) {
        this.limiteIzqMundo = limiteIzqMundo;
    }
    
    private void setFondo(int numeroMapa){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        if(!(TESTMODE)){
            this.imagen_fondo = toolkit.getImage(AREAS[numeroMapa]);
        } else {
            this.imagen_fondo = toolkit.getImage(AREASTEST[numeroMapa]);
        }
    }

    public int getEnergiaHeroe() {
        return energiaHeroe;
    }

    public void setEnergiaHeroe(int energiaHeroe) {
        this.energiaHeroe = energiaHeroe;
    }

    public int getNumeroMapa() {
        return numeroMapa;
    }
    
    public void setNumeroMapa(int numeroMapa) {
        this.numeroMapa = numeroMapa;
    }
    
    public Jugador getJugador() {
        return jugador;
    }

    public AdventureIsland getAi() {
        return ai;
    }
    
   
}