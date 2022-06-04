package adventureisland;

import static adventureisland.Ajustes.leerConfiguracion;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AdventureIsland extends JGame {
    private Camara cam;
    private Escenario esc;
    
    private static final String etapas[] = {"recursos\\Etapas\\Fondo_Inicio.png", 
                                "recursos\\Etapas\\Titulo_AdvIslandII.png",
                                "recursos\\Etapas\\Triangulo_Costado.png"};
    
    private static final String todas_islas[] = {"recursos\\Etapas\\Todas_las_Islas.png",
                                                "recursos\\Heroe\\HeroeEnBarquito.GIF"};
    
    private static final String isla_juego[] = {"recursos\\Etapas\\Isla.png",
                                                "recursos\\Etapas\\MonsterAnimacion.GIF",
                                                "recursos\\Heroe\\HeroeQuietoDerecha.GIF"};
    
    private Image imagen_heroequieto;
    private Image imagen_etapa;
    private Image imagen_etapa2;
    private Image imagen_etapa3;
    private Image imagen_islas;
    private Image imagen_heroe_barco;
    private Image imagen_isla;
    private Image imagen_monster;
    private Image imagen_heroe2;
    private Image imagen_vidas;
    private int etapaJuego; 
    Keyboard keyboard = this.getKeyboard();
    private int pos_y = 250;
    
    public final static int ETAPA_MENU = 0;
    public final static int ETAPA_ISLAS = 1;
    public final static int ETAPA_ISLA = 2;
    public final static int ETAPA_JUEGO = 3;
    public final static int ETAPA_INTERMEDIO = 4;
    public final static int ETAPA_GAMEOVER = 6;
    public final static int ETAPA_RANKING = 7;
    
    
    private int contadorMultiple;
    private int tempPosX = 187;
    private int tempPosY = 355;
    
    private int p_musica;
    private boolean p_csonido;
    private int p_sonido;
    private int p_pausar;
    private int p_iniciar;
    private int  p_izquierda;
    private int p_derecha;
    private int p_saltar;
    private int p_lanzar;
    private boolean pausa = false;
    private boolean act_Hachas = false;
    
    private byte contMusic = 0;
    
    private Image imagen_tiempo;
    private float tiempo = 16000;
    
    public AdventureIsland(String nombreJugador) {
        // call game constructor
        super("AdventureIsland ", 640, 480);
        esc = new Escenario(this, getJugador());
        cam = new Camara(this, 0, 0);
        this.etapaJuego = ETAPA_MENU;
        admin_sound.playSonido("Inicio", true);
        
        contadorMultiple = 0;
        jugador.setNombre(nombreJugador);
    }

    @Override
    public void gameStartup() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        imagen_heroequieto = toolkit.getImage("recursos\\Heroe\\HeroeQuietoDerecha.GIF");
        imagen_etapa = toolkit.getImage(etapas[0]);
        imagen_etapa2 = toolkit.getImage(etapas[1]);
        imagen_etapa3 = toolkit.getImage(etapas[2]);
        imagen_islas = toolkit.getImage(todas_islas[0]);
        imagen_heroe_barco = toolkit.getImage(todas_islas[1]);
            
        imagen_isla = toolkit.getImage(isla_juego[0]);
        imagen_monster = toolkit.getImage(isla_juego[1]);
        imagen_heroe2 = toolkit.getImage(isla_juego[2]);
        imagen_vidas = toolkit.getImage("recursos\\Heroe\\HeroeVidas.png");
        imagen_tiempo = toolkit.getImage("recursos\\Tiempo\\BarritaTiempo.png");
        
        
        esc.getHeroe().quieto();
        cam.setRegionVisible(640,480);
        
        appProperties = leerConfiguracion();
        p_csonido = Boolean.valueOf(appProperties.getProperty("sonidoChoice"));
        admin_sound.setact_sonido(p_csonido);
    }
    
    @Override
    public void gameUpdate(double delta) {
        appProperties = leerConfiguracion();
        
        p_sonido =(char)(appProperties.getProperty("teclaSonido").charAt(0)-32);
        p_musica =(char)(appProperties.getProperty("teclaMusica").charAt(0)-32);
        p_pausar =(char)(appProperties.getProperty("teclaPausar").charAt(0)-32);

        if(appProperties.getProperty("teclaIniciar").charAt(0) == 69){ //Caso de "Enter"
            p_iniciar=(char)(appProperties.getProperty("teclaIniciar").charAt(0)-59);
        }else{
            p_iniciar=(char)(appProperties.getProperty("teclaIniciar").charAt(0)-32);
        }
        if(appProperties.getProperty("teclaIzquierda").charAt(0) == 60){ //Caso de "<"
            p_izquierda=(char)(appProperties.getProperty("teclaIzquierda").charAt(0)-23);
        }else{
            p_izquierda=(char)(appProperties.getProperty("teclaIzquierda").charAt(0)-32);
        }
        if(appProperties.getProperty("teclaDerecha").charAt(0) == 62){ //Caso de ">"
            p_derecha=(char)(appProperties.getProperty("teclaDerecha").charAt(0)-23);
        }else{
            p_derecha=(char)(appProperties.getProperty("teclaDerecha").charAt(0)-32);
        }
        
        p_saltar=(char)(appProperties.getProperty("teclaSaltar").charAt(0)-32);
        p_lanzar=(char)(appProperties.getProperty("teclaLanzar").charAt(0)-32);
        
        
        if(keyboard.isKeyPressed(p_izquierda) && etapaJuego == ETAPA_JUEGO && !pausa){
            esc.getHeroe().left();
        }
        if(keyboard.isKeyPressed(p_derecha) && etapaJuego == ETAPA_JUEGO && !pausa){
            esc.getHeroe().right();
        }    
            
        // check the list of key events for a pressed escape key
        LinkedList < KeyEvent > keyEvents = keyboard.getEvents();
        for(KeyEvent event: keyEvents){
            if((event.getID() == KeyEvent.KEY_RELEASED) && etapaJuego == ETAPA_JUEGO){
                esc.getHeroe().quieto();
            } else if((event.getID() == KeyEvent.KEY_PRESSED) && (event.getKeyCode() == p_saltar) && etapaJuego == ETAPA_JUEGO && !pausa){
                esc.getHeroe().jump();
            } else if((event.getID() == KeyEvent.KEY_RELEASED) && (event.getKeyCode() == p_saltar) && etapaJuego == ETAPA_JUEGO && !pausa){
                esc.getHeroe().jumpEnd();
            } else if((event.getID() == KeyEvent.KEY_PRESSED) && (event.getKeyCode() == p_lanzar) && etapaJuego == ETAPA_JUEGO && !pausa){
                esc.getHeroe().atacar();
            } else if((event.getID() == KeyEvent.KEY_PRESSED) && (event.getKeyCode() == KeyEvent.VK_ESCAPE)){
                stop();  
            } else if((event.getID() == KeyEvent.KEY_PRESSED) && (event.getKeyCode() == p_sonido)){
                admin_sound.setact_sonido(!admin_sound.getact_sonido());
            } else if((event.getID() == KeyEvent.KEY_PRESSED) && (event.getKeyCode() == p_musica)){
                admin_sound.setact_musica(this.etapaJuego, esc.getNumeroMapa(), !admin_sound.getact_musica());
            } else if((event.getID() == KeyEvent.KEY_PRESSED) && (event.getKeyCode() == p_pausar)){
                pausa = !pausa; 
                admin_sound.setact_sonido(!admin_sound.getact_sonido());
                admin_sound.setact_musica(this.etapaJuego, esc.getNumeroMapa(), !admin_sound.getact_musica());
            }
        }
        
        if (!pausa){ esc.getHeroe().update(delta);}

        cam.seguirPersonaje(esc.getHeroe()); ///la camara sigue al Personaje
        
        if(etapaJuego == ETAPA_JUEGO){
                this.guardar_juego();
        }
    }
    
    public boolean getPausa(){
        return this.pausa;
    }
    
    public void guardar_juego(){
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter("src\\adventureisland\\Guardar_juego.txt"));
            bw.write(getJugador().getPuntajeJugador() + "\n"); //puntaje
            bw.write(esc.getEnergiaHeroe() + "\n"); //vidas
            bw.write(esc.getNumeroMapa() + "\n"); //nivel
            bw.write(tiempo + "\n"); //tiempo
            bw.write(esc.getHeroe().act_Hachas() + "\n"); //hacha
            bw.write(esc.getHeroe().act_Hada() + "\n"); //hada
            bw.write(esc.getHeroe().act_DinoAzul() + "\n"); //dinoAzul
            bw.write(esc.getHeroe().act_DinoVioleta() + "\n"); //dinoVioleta
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(AdventureIsland.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public boolean getact_Hacha(){
        return act_Hachas;
    }
    
    public void cargar_juego(){ 
        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new FileReader("src\\adventureisland\\Guardar_juego.txt"));
            String linea = br.readLine();
            String aux1 = null;
            String aux2 = null;
            String aux3 = null;
            while(linea != null){
               if (aux1 == null) {aux1 = linea;}
               else if (aux2 == null) {aux2 = linea;}
               else if (aux3 == null) {aux3 = linea;}
               linea = br.readLine();
               
            }
            if(Integer.parseInt(aux2) != 0){
                getJugador().setPuntajeJugador(Integer.parseInt(aux1));
                esc.setEnergiaHeroe(Integer.parseInt(aux2));
                esc.setNumeroMapa(Integer.parseInt(aux3));
            }else{
                getJugador().setPuntajeJugador(0);
                esc.setEnergiaHeroe(3);
                esc.setNumeroMapa(0);
            }
            
        }catch (FileNotFoundException e) {
            System.out.println("Error: Fichero no encontrado");
            System.out.println(e.getMessage());
        }
        catch(IOException e) {
            System.out.println("Error de lectura del fichero");
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if(br != null)
                    br.close();
            }
            catch (IOException e) {
                System.out.println("Error al cerrar el fichero");
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void keyReleased(KeyEvent event){
        if(this.etapaJuego == ETAPA_JUEGO){
            esc.getHeroe().setVelocityX(0);
            esc.getHeroe().setVelocityY(0);
        }
    }
    
    private void drawMenu(Graphics2D g){
        g.drawImage(imagen_etapa, 0, 0, imagen_etapa.getWidth(null)*2, imagen_etapa.getHeight(null), null);
        g.drawImage(imagen_etapa2, 82, 40, imagen_etapa2.getWidth(null), imagen_etapa2.getHeight(null), null);
        g.drawImage(imagen_heroequieto, 190, 250, imagen_heroequieto.getWidth(null)*2, imagen_heroequieto.getHeight(null)*2, null);
        Font f = new Font("Courier", Font.BOLD,20);
        g.setFont(f);
        g.setColor(Color.WHITE);
        g.drawString("Start", 285, 270);
        g.drawString("Continue", 285, 305);

        if(keyboard.isKeyPressed(KeyEvent.VK_UP)){
            pos_y = 250;
        } else if(keyboard.isKeyPressed(KeyEvent.VK_DOWN)){
            pos_y = 283;
        }
        g.drawImage(imagen_etapa3, 255, pos_y, imagen_etapa3.getWidth(null), imagen_etapa3.getHeight(null), null);
    }
    
    private void drawGame(Graphics2D g){
        if(esc.getEnergiaHeroe() >= 0){
            g.translate(cam.getX(),cam.getY());
            esc.display(g);
            g.drawImage(imagen_vidas, (int) (-cam.getX())+40, 60, imagen_vidas.getWidth(null)*3, imagen_vidas.getHeight(null)*3, null);
            drawText(g, Integer.toString(esc.getEnergiaHeroe()), (int) (-cam.getX())+imagen_vidas.getWidth(null)*3+55-1, 79-1, 23, Color.white);
            
            drawText(g, getJugador().getMPuntos(6, "0"), (int) (-cam.getX())+290, 79-1, 23, Color.white);
            
            drawTime(g);
            
            if(!pausa){decrementTime();}
        } else {
            this.etapaJuego = ETAPA_GAMEOVER;
            esc.setEnergiaHeroe(3);
            admin_sound.pararMusica("Area1");
            admin_sound.pararMusica("Area2");
            admin_sound.pararMusica("TodasIslas");
            admin_sound.playSonido("GameOver", admin_sound.getact_musica());
            
            tiempo = 12000;
        }
    }
    
    private void drawTime(Graphics2D g){
        if(esc.getHeroe().getEstadoActual() != Heroe.ESTADO_CAYENDO || esc.getHeroe().getEstadoActual() != Heroe.ESTADO_MURIENDO || 
            esc.getHeroe().getEstadoActual() != Heroe.ESTADO_QUEMADO || esc.getHeroe().getEstadoActual() != Heroe.ESTADO_MUERTO){
            if(tiempo > 0 && esc.getEnergiaHeroe()>=0){
                int posXTiempo = 240;
                int posYTiempo = 90;
                for(int i=0; i<(tiempo/1000); i++){
                    g.drawImage(imagen_tiempo, (int) (-cam.getX())+posXTiempo, posYTiempo, imagen_tiempo.getWidth(null)+4, imagen_tiempo.getHeight(null)*2, null);
                    posXTiempo += ((imagen_tiempo.getWidth(null)+4) +4); 
                }
            }else if(tiempo==0){
                esc.getHeroe().setEstadoActual(Heroe.ESTADO_MURIENDO);
                tiempo = 12000;
            }
        }
    }
    
    private void drawIslands(Graphics2D g){
        g.translate(0,5);
        g.drawImage(imagen_islas, 0, 0, imagen_islas.getWidth(null)-50+10, imagen_islas.getHeight(null)-50+15, null);
        if(this.contadorMultiple < 183){
            this.contadorMultiple+=1;
        }    
        g.drawImage(imagen_heroe_barco, 70, getHeight()+1-this.contadorMultiple,imagen_heroe_barco.getWidth(null)+5,imagen_heroe_barco.getHeight(null)+8,null);
        
    }
    
    private void drawIsland(Graphics2D g){
        g.translate(2,0);
        g.drawImage(imagen_isla, -2, -2, 644+10, 484+15, null);
        g.drawImage(imagen_monster, 420, 187, imagen_monster.getWidth(null)-17, imagen_monster.getHeight(null)-25, null);
        g.drawImage(imagen_heroe2, 187, 355, imagen_heroe2.getWidth(null)+7, imagen_heroe2.getHeight(null)+14, null);
    }
    
    private void drawGameOver(Graphics2D g){
        g.setBackground(Color.black);
        g.setColor(Color.white);
        g.setFont(new Font("COURIER", Font.BOLD, 20));
        g.drawString("GAME OVER", 270, 240);
    }
    
    private void drawIntermediate(Graphics2D g){
        if(contadorMultiple > 10){
            g.translate(2,0);
            g.drawImage(imagen_isla, -2, -2, 644+5, 484+15, null);
            g.drawImage(imagen_monster, 420, 187, imagen_monster.getWidth(null)-17, imagen_monster.getHeight(null)-25, null);
            g.drawImage(imagen_heroe2, tempPosX, tempPosY, imagen_heroe2.getWidth(null)+7, imagen_heroe2.getHeight(null)+14, null);
            if(contadorMultiple < 80){
                if(contadorMultiple > 40 && contadorMultiple%2 == 0){
                    tempPosX+=2;
                    tempPosY-=2;
                }
            }
        }
        contadorMultiple+=1;
    }
    
    @Override
    public void gameDraw(Graphics2D g){
        if(keyboard.isKeyPressed(KeyEvent.VK_ENTER)){
            switch(this.etapaJuego){
                case ETAPA_MENU:
                    if(pos_y == 250){
                        this.etapaJuego = ETAPA_ISLAS;
                        admin_sound.pararMusica("Inicio");
                        admin_sound.playSonido("SonidoPasaje", admin_sound.getact_musica());
                        admin_sound.pararMusica("SonidoPasaje");
                        admin_sound.playSonido("TodasIslas", admin_sound.getact_musica());
                    }else if (pos_y == 283){
                            this.etapaJuego = ETAPA_JUEGO; 
                            admin_sound.pararMusica("Inicio");
                            admin_sound.playSonido("Area1", admin_sound.getact_musica());
                            this.cargar_juego();
                    }
                    break;
                case ETAPA_ISLAS:
                        this.etapaJuego = ETAPA_ISLA;
                    break;
                case ETAPA_ISLA:
                    this.etapaJuego = ETAPA_JUEGO;
                    admin_sound.pararMusica("TodasIslas");
                    if(esc.getNumeroMapa() == 0){
                        admin_sound.playSonido("Area1", admin_sound.getact_musica());
                    }else if(esc.getNumeroMapa() == 1){
                        admin_sound.playSonido("Area2", admin_sound.getact_musica());
                    }
                    break;
                case ETAPA_INTERMEDIO: 
                    tiempo=12000;
                    this.etapaJuego = ETAPA_JUEGO;
                    if(esc.getNumeroMapa() == 0){
                        admin_sound.playSonido("Area1", admin_sound.getact_musica());
                    }else if(esc.getNumeroMapa() == 1){
                        admin_sound.playSonido("Area2", admin_sound.getact_musica());
                    }
                    break;
                case ETAPA_GAMEOVER:
                    admin_sound.pararMusica("GameOver");
                    this.etapaJuego = ETAPA_RANKING;
                    admin_sound.playSonido("Ranking", admin_sound.getact_musica());
                    ranking.cargar(jugador.getNombre(), jugador.getPuntajeJugador());
                    ranking.ordenar();
                    jugador.setPuntajeJugador(0);
                    break;
                case ETAPA_RANKING:
                    admin_sound.pararMusica("Ranking");
                    this.etapaJuego = ETAPA_MENU;
                    admin_sound.playSonido("Inicio", admin_sound.getact_musica());
                    esc = new Escenario(this,jugador);
                    break;
            }
            keyboard.releaseKey(KeyEvent.VK_ENTER);
        }
        switch(this.etapaJuego){
            case ETAPA_MENU:
//                drawGame(g);
                drawMenu(g);
                this.contMusic++; //Ver
                this.contadorMultiple = 0;
                break;
            case ETAPA_ISLAS:
                drawIslands(g);
                break;
            case ETAPA_ISLA:
                drawIsland(g);
                break;
            case ETAPA_JUEGO:
                admin_sound.pararMusica("TodasIslas");
                drawGame(g);
                this.contadorMultiple = 0;
                break;
            case ETAPA_INTERMEDIO:
                drawIntermediate(g);
                break;
            case ETAPA_GAMEOVER:
                admin_sound.pararMusica("Area2");
                drawGameOver(g);
                break;
            case ETAPA_RANKING:
                ranking.display(g);
                break;
            default:
                break;
        }
    }
    
    private void drawText(Graphics2D g2, String text, int posX, int posY, int size, Color color){
        Font f = new Font("Courier", Font.BOLD, size);
        g2.setFont(f);
        g2.setColor(Color.black);
        g2.drawString(text, posX-1, posY-1);
        g2.drawString(text, posX-1, posY+1);
        g2.drawString(text, posX+1, posY-1);
        g2.drawString(text, posX+1, posY+1);
        g2.setColor(color);
        g2.drawString(text, posX, posY);
    }

    @Override
    public void gameShutdown() {
       //Log.info(getClass().getSimpleName(), "Shutting down game");
    }

    public Camara getCam() {
       return cam;
    }

    public Escenario getEsc() {
        return esc;
    }
    
    public int getEtapaJuego() {
        return etapaJuego;
    }

    public void setEtapaJuego(int etapaJuego) {
        this.etapaJuego = etapaJuego;
    }

    public int getTiempo() {
        return (int) tiempo;
    }
    
    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }
    
    public void incrementTime(int segundos){
        if(tiempo+segundos*200<16000){
            tiempo=tiempo+(segundos*200);
        } else if(tiempo+segundos*200 > 16000) {
            tiempo = 16000;
        }
    }
    
    public void decrementTime(){
        tiempo-=5;
    }
 
}